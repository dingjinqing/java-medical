package com.vpu.mp.service.shop.order.virtual;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderRefundParam;
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
     */
    public void virtualOrderRefund(VirtualOrderRefundParam param) {
        /** 是否退款成功 */
        boolean successFlag = false;

        /** TODO 是否需要事务控制 */

        if(param.getMoney().compareTo(BigDecimal.ZERO) > 0){
            /** TODO 微信退款服务 */
        }

        if(param.getAccount().compareTo(BigDecimal.ZERO) > 0){
            /** TODO 余额退款服务 */
        }

        if(param.getMemberCardBalance().compareTo(BigDecimal.ZERO) > 0){
            /** TODO 会员卡余额退款服务 */
        }

        if(param.getScore() > 0){
            /** TODO 积分退款服务 */
        }

        VirtualOrderPayInfo payInfo = getOrderPayInfo(param.getOrderId());

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
        return db().select(VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.USER_ID,VIRTUAL_ORDER.PAY_CODE,VIRTUAL_ORDER.PAY_SN,VIRTUAL_ORDER.MONEY_PAID,VIRTUAL_ORDER.USE_ACCOUNT,VIRTUAL_ORDER.MEMBER_CARD_BALANCE,VIRTUAL_ORDER.USE_SCORE,VIRTUAL_ORDER.ORDER_AMOUNT,VIRTUAL_ORDER.PAY_TIME,VIRTUAL_ORDER.RETURN_FLAG,VIRTUAL_ORDER.RETURN_MONEY,VIRTUAL_ORDER.RETURN_ACCOUNT,VIRTUAL_ORDER.RETURN_CARD_BALANCE,VIRTUAL_ORDER.RETURN_SCORE,VIRTUAL_ORDER.RETURN_TIME).from(VIRTUAL_ORDER).where(VIRTUAL_ORDER.ORDER_ID.eq(orderId)).and(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne().into(VirtualOrderPayInfo.class);
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
}
