package com.vpu.mp.service.shop.order.virtual;

import com.vpu.mp.db.shop.tables.records.VirtualOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderRefundParam;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;

import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.tables.VirtualOrder.VIRTUAL_ORDER;
import static com.vpu.mp.db.shop.tables.VirtualOrderRefundRecord.VIRTUAL_ORDER_REFUND_RECORD;

/**
 * @author: 王兵兵
 * @create: 2019-08-22 11:17
 **/
@Service
public class VirtualOrderService extends ShopBaseService {
	
	@Autowired
	private RecordTradeService recordMemberTrade;
	
	@Autowired
	private ReturnMethodService returnMethod;
    /** 虚拟商品类型：会员卡订单 */
    public static final Byte GOODS_TYPE_MEMBER_CARD = 0;
    /** 虚拟商品类型：优惠券礼包订单 */
    public static final Byte GOODS_TYPE_COUPON_PACK = 1;


    /** 订单状态：待付款 */
    public static final Byte ORDER_STATUS_WAIT_PAY = 0;
    /** 订单状态：已完成 */
    public static final Byte ORDER_STATUS_FINISHED = 1;

    /** 订单和退款记录表退款状态值相同 */
    /** 退款状态：未退款 **/
    public static final Byte REFUND_STATUS_NORMAL = 0;
    /** 退款状态：退款失败 **/
    public static final Byte REFUND_STATUS_FAILED = 1;
    /** 退款状态：退款成功 **/
    public static final Byte REFUND_STATUS_SUCCESS = 2;

    /**
     * 手动退款
     * @throws MpException 
     */
    public void virtualOrderRefund(VirtualOrderRefundParam param) throws MpException {
    	//订单
    	VirtualOrderPayInfo payInfo = getOrderPayInfo(param.getOrderId());
    	
        /** 是否退款成功 */
        boolean successFlag = false;
        try {
        	transaction(() -> {
    			if (param.getAccount().compareTo(BigDecimal.ZERO) > 0) {
    				/** TODO 余额退款服务 */
    				AccountData accountData = AccountData.newBuilder().userId(payInfo.getUserId())
    						.orderSn(payInfo.getOrderSn()).
    				// 退款金额
    				amount(param.getAccount())
    						.remarkCode(RemarkTemplate.ORDER_VIRTUAL_RETURN.code)
    						.remarkData(String.format(payInfo.getOrderSn()))
    						.payment(OrderConstant.PAY_CODE_BALANCE_PAY).
    				// 支付类型
    				isPaid(RecordTradeEnum.UACCOUNT_RECHARGE.val()).
    				// 后台处理时为操作人id为0
    				adminUser(0).
    				// 用户余额退款
    				tradeType(RecordTradeEnum.TYPE_CRASH_MACCOUNT_REFUND.val()).
    				// 资金流量-支出
    				tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).build();
    				// 调用退余额接口
    				recordMemberTrade.updateUserEconomicData(accountData);
    			}

    			if (param.getMemberCardBalance().compareTo(BigDecimal.ZERO) > 0) {
    				
    				/**
    				 * 交易记录信息
    				 */
    				TradeOptParam tradeOpt = TradeOptParam
    						.builder()
    						.adminUserId(0)
    						.tradeType(RecordTradeEnum.TYPE_CRASH_MCARD_ACCOUNT_REFUND.val())
    						.tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val())
    						.build();
    				
    				/** TODO 会员卡余额退款服务 */
    				UserCardData userCardData = UserCardData.newBuilder().userId(payInfo.getUserId())
    						.cardNo(payInfo.getCardNo()).money(param.getMemberCardBalance()).reasonId(RemarkTemplate.ORDER_VIRTUAL_RETURN_DEFAULT.code).
    				// 普通会员卡
    				type(CardConstant.MCARD_TP_NORMAL).orderSn(param.getOrderSn()).
    				tradeOpt(tradeOpt).build();
    				// 调用退会员卡接口
    				recordMemberTrade.updateUserEconomicData(userCardData);
    			}

    			if (param.getScore() > 0) {
    				/** TODO 积分退款服务 */
    				ScoreData scoreData = ScoreData.newBuilder().userId(payInfo.getUserId()).orderSn(payInfo.getOrderSn()).
    				// 退款积分
    				score(param.getScore()).
    				remarkCode(RemarkTemplate.ORDER_VIRTUAL_RETURN_SCORE_ACCOUNT.code).
    				remarkData(payInfo.getOrderSn()+","+param.getScore()).
    				//remark(String.format("虚拟订单退款:%s，退积分：%s", payInfo.getOrderSn(), param.getScore())).
    				// 后台处理时为操作人id为0
    				adminUser(0).
    				// 用户余额充值
    				tradeType(RecordTradeEnum.TYPE_CRASH_POWER_MACCOUNT.val()).
    				// 资金流量-支出
    				tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).
    				// 积分变动是否来自退款
    				isFromRefund(NumberUtils.BYTE_ONE).build();
    				// 调用退积分接口
    				recordMemberTrade.updateUserEconomicData(scoreData);
    			}

    			if (param.getMoney().compareTo(BigDecimal.ZERO) > 0) {
    				/** TODO 微信退款服务 */
    				returnMethod.refundVirtualWx(payInfo, param.getMoney());
    			}

    		});

		}  catch (DataAccessException e) {
			logger().error("退款捕获DataAccessException异常", e);
			Throwable cause = e.getCause();
			if (cause instanceof MpException) {
				throw (MpException)cause;
			} else {
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION);
			}
		} catch (Exception e) {
			logger().error("退款捕获mp异常", e);
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION);
		}
	
        BigDecimal finalReturnMoney = payInfo.getReturnMoney().add(param.getMoney());
        BigDecimal finalReturnAccount = payInfo.getReturnAccount().add(param.getAccount());
        BigDecimal finalReturnMemberCardBalance = payInfo.getReturnCardBalance().add(param.getMemberCardBalance());
        Integer finalReturnScore = payInfo.getReturnScore() + param.getScore();

        byte isSuccess = successFlag ? REFUND_STATUS_SUCCESS : REFUND_STATUS_FAILED;

        // 记录退款信息
        db().insertInto(VIRTUAL_ORDER_REFUND_RECORD, VIRTUAL_ORDER_REFUND_RECORD.ORDER_SN, VIRTUAL_ORDER_REFUND_RECORD.USER_ID,
            VIRTUAL_ORDER_REFUND_RECORD.MONEY_PAID, VIRTUAL_ORDER_REFUND_RECORD.USE_ACCOUNT, VIRTUAL_ORDER_REFUND_RECORD.USE_SCORE,
            VIRTUAL_ORDER_REFUND_RECORD.MEMBER_CARD_BALANCE,
            VIRTUAL_ORDER_REFUND_RECORD.IS_SUCCESS)
            .values(payInfo.getOrderSn(),payInfo.getUserId(),param.getMoney(),param.getAccount(),param.getScore(),param.getMemberCardBalance(),isSuccess).execute();

        if(successFlag){
            // 更新订单状态
            db().update(VIRTUAL_ORDER).set(VIRTUAL_ORDER.RETURN_FLAG, REFUND_STATUS_SUCCESS).set(VIRTUAL_ORDER.RETURN_MONEY,
                finalReturnMoney).set(VIRTUAL_ORDER.RETURN_ACCOUNT, finalReturnAccount).set(VIRTUAL_ORDER.RETURN_CARD_BALANCE, finalReturnMemberCardBalance).set(VIRTUAL_ORDER.RETURN_SCORE, finalReturnScore).execute();
        }
        
    }

    private VirtualOrderPayInfo getOrderPayInfo(Integer orderId){
        return db().select(VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.USER_ID,VIRTUAL_ORDER.PAY_CODE,VIRTUAL_ORDER.PAY_SN,VIRTUAL_ORDER.MONEY_PAID,VIRTUAL_ORDER.USE_ACCOUNT,VIRTUAL_ORDER.MEMBER_CARD_BALANCE,VIRTUAL_ORDER.USE_SCORE,VIRTUAL_ORDER.ORDER_AMOUNT,VIRTUAL_ORDER.PAY_TIME,VIRTUAL_ORDER.RETURN_FLAG,VIRTUAL_ORDER.RETURN_MONEY,VIRTUAL_ORDER.RETURN_ACCOUNT,VIRTUAL_ORDER.RETURN_CARD_BALANCE,VIRTUAL_ORDER.RETURN_SCORE,VIRTUAL_ORDER.CARD_NO,VIRTUAL_ORDER.RETURN_TIME).from(VIRTUAL_ORDER).where(VIRTUAL_ORDER.ORDER_ID.eq(orderId)).and(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne().into(VirtualOrderPayInfo.class);
    }

    /**
     * 校验申请退款金额是否大于可退金额
     * @param param
     * @return
     */
    public Boolean checkVirtualOrderRefundParam(VirtualOrderRefundParam param){
        VirtualOrderPayInfo payInfo = getOrderPayInfo(param.getOrderId());
        if(param.getAccount().compareTo(payInfo.getUseAccount().subtract(payInfo.getReturnAccount())) > 0 || param.getMoney().compareTo(payInfo.getMoneyPaid().subtract(payInfo.getReturnMoney())) > 0 || param.getMemberCardBalance().compareTo(payInfo.getMemberCardBalance().subtract(payInfo.getReturnCardBalance())) > 0 || param.getScore() > (payInfo.getUseScore() - payInfo.getReturnScore())){
            return false;
        }else{
            return true;
        }
    }
    
    public VirtualOrderRecord getInfoByNo(String cardNo) {
    	return  db().selectFrom(VIRTUAL_ORDER).where(VIRTUAL_ORDER.CARD_NO.eq(cardNo)).fetchAny();
    }
    
}
