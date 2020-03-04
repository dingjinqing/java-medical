package com.vpu.mp.service.shop.order.virtual;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderVo;
import com.vpu.mp.service.pojo.wxapp.coupon.pack.CouponPackOrderParam;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.vpu.mp.db.shop.tables.CouponPack.COUPON_PACK;
import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static com.vpu.mp.db.shop.tables.CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.VirtualOrder.VIRTUAL_ORDER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_NORMAL;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.*;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * @author huangronggang
 * @date 2019年8月1日
 */
@Service
public class CouponPackOrderService extends VirtualOrderService {
	/** 发放优惠劵的获取方式，0：发放，1：领取，2：礼包*/
	public static final Byte CUSTOMER_AVAIL_COUPONS_ACCESSMODE_PACK=2;

	public static final String COUPON_PACK_ORDER_SN_PREFIX = "Y";

    @Autowired
    public MemberService memberService;
    @Autowired
    public MpPaymentService mpPaymentService;

	/**
	 * 分页查询优惠劵礼包订单 
	 * @param param
	 * @return
	 */
	public PageResult<CouponPackOrderVo> getPageList(CouponPackOrderPageParam param){
		SelectWhereStep<? extends Record> selectFrom = db()
			.select(VIRTUAL_ORDER.ORDER_ID,VIRTUAL_ORDER.VIRTUAL_GOODS_ID,COUPON_PACK.PACK_NAME,
					VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.USER_ID,USER.USERNAME,USER.MOBILE,VIRTUAL_ORDER.MONEY_PAID,VIRTUAL_ORDER.USE_ACCOUNT,VIRTUAL_ORDER.USE_SCORE,VIRTUAL_ORDER.MEMBER_CARD_BALANCE,VIRTUAL_ORDER.CARD_NO,VIRTUAL_ORDER.PAY_CODE,VIRTUAL_ORDER.PAY_NAME,VIRTUAL_ORDER.PREPAY_ID,VIRTUAL_ORDER.PAY_SN,VIRTUAL_ORDER.ORDER_AMOUNT,
					VIRTUAL_ORDER.CREATE_TIME,VIRTUAL_ORDER.RETURN_FLAG,VIRTUAL_ORDER.RETURN_SCORE,VIRTUAL_ORDER.RETURN_ACCOUNT,VIRTUAL_ORDER.RETURN_MONEY,VIRTUAL_ORDER.RETURN_CARD_BALANCE,
					VIRTUAL_ORDER.RETURN_TIME,VIRTUAL_ORDER.CURRENCY)
			.from(VIRTUAL_ORDER)
			.leftJoin(COUPON_PACK).on(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(COUPON_PACK.ID))
			.leftJoin(USER).on(VIRTUAL_ORDER.USER_ID.eq(USER.USER_ID));
		SelectConditionStep<? extends Record> select = buildOptions(selectFrom,param);
		PageResult<CouponPackOrderVo> pageResult = getPageResult(select,param.getCurrentPage(),param.getPageRows(), CouponPackOrderVo.class);
		List<CouponPackOrderVo> dataList = pageResult.dataList;	
		if(dataList==null) {
			return pageResult;
		}
		for (CouponPackOrderVo couponPackOrderVo : dataList) {
			couponPackOrderVo.setSurplusAmount(getSurplusAmount(couponPackOrderVo.getVirtualGoodsId(), couponPackOrderVo.getOrderSn()));
		}
		return pageResult;
	}

	/**
	 * @param select
	 * @param param
	 * @return 
	 */
	private  SelectConditionStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select, CouponPackOrderPageParam param) {
		SelectConditionStep<? extends Record> condition = select.where(VIRTUAL_ORDER.GOODS_TYPE.eq(GOODS_TYPE_COUPON_PACK))
			  .and(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		if(!StringUtils.isBlank(param.getPackName())) {
			condition.and(COUPON_PACK.PACK_NAME.like(this.likeValue(param.getPackName())));
		}
		if(!StringUtils.isBlank(param.getOrderSn())) {
			condition.and(VIRTUAL_ORDER.ORDER_SN.like(this.likeValue(param.getOrderSn())));
		}
		if(!StringUtils.isBlank(param.getUserInfo())) {
			condition.and(USER.USERNAME.like(likeValue(param.getUserInfo())).or(USER.MOBILE.like(likeValue(param.getUserInfo()))));
		}
		if(param.getStartTime() != null) {
			condition.and(VIRTUAL_ORDER.CREATE_TIME.gt(param.getStartTime()));
		}
		if(param.getEndTime() != null) {
			condition.and(VIRTUAL_ORDER.CREATE_TIME.le(param.getEndTime()));
		}
        if (null != param.getRefund()) {
            if (param.getRefund()) {
                condition.and(VIRTUAL_ORDER.RETURN_FLAG.eq(REFUND_STATUS_SUCCESS).or(VIRTUAL_ORDER.RETURN_FLAG.eq(REFUND_STATUS_FAILED)));
            }
        }
		condition.orderBy(VIRTUAL_ORDER.CREATE_TIME);
		return condition;
	}
	
	/**
	 * 返回一个优惠劵包里有多少个优惠劵
	 * @param couponPackId
	 * @return
	 */
	private int getTotalCouponNum(Integer couponPackId) {
		Record1<BigDecimal> fetchOne = db().select(DSL.sum(COUPON_PACK_VOUCHER.TOTAL_AMOUNT)).from(COUPON_PACK_VOUCHER)
					.where(COUPON_PACK_VOUCHER.ACT_ID.eq(couponPackId))
					.and(COUPON_PACK_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne();
		return fetchOne.value1() == null ?0:fetchOne.value1().intValue();
	}
	/**
	 * 返回用户已经领取的优惠劵数量
	 * @param orderSn
	 * @return
	 */
	public int getVoucherAccessCount(String orderSn) {
		Record1<Integer> record = db().select(DSL.count()).from(CUSTOMER_AVAIL_COUPONS)
			.where(CUSTOMER_AVAIL_COUPONS.COUPON_SN.eq(orderSn))
			.and(CUSTOMER_AVAIL_COUPONS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.and(CUSTOMER_AVAIL_COUPONS.ACCESS_MODE.eq(CUSTOMER_AVAIL_COUPONS_ACCESSMODE_PACK)).fetchOne();
		return record.value1();
	}
	/**
	 * 获取某一个订单 用户还剩余多少优惠劵未发放
	 * @param couponPackId
	 * @param orderSn
	 * @return
	 */
	private int getSurplusAmount(Integer couponPackId, String orderSn) {
		if(couponPackId== null||orderSn == null) {
			return 0;
		}
		return getTotalCouponNum(couponPackId)-getVoucherAccessCount(orderSn);
	}
	
	/**
	 * 手动退款
	 * @param
	 * @return
	 */
	public JsonResultCode refundCouponPackOrder(CouponPackOrderRefundParam param) {
        try {
			this.virtualOrderRefund(param.getVirtualOrderRefundParam());
		} catch (MpException e) {
			return e.getErrorCode();
		}
        this.updateSendFlag(param.getStillSendFlag(), param.getOrderId());

        /** 操作记录 */
        saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_COUPON_PACK_ORDER_REFUND.code }), new String[] {param.getOrderSn()});
        return null;
	}

	public void updateSendFlag(Byte sendFlag,Integer orderId) {
		db().update(VIRTUAL_ORDER)
			.set(VIRTUAL_ORDER.STILL_SEND_FLAG,sendFlag)
			.where(VIRTUAL_ORDER.ORDER_ID.eq(orderId)).execute();
	}

    /**
     * 某个优惠券礼包活动的礼包销量
     * @param couponPackId
     * @return
     */
	public int getCouponPackIssueAmount(int couponPackId){
	    return db().selectCount().from(VIRTUAL_ORDER).
            where(VIRTUAL_ORDER.GOODS_TYPE.eq(GOODS_TYPE_COUPON_PACK)).
            and(VIRTUAL_ORDER.ORDER_STATUS.eq(ORDER_STATUS_FINISHED)).
            and(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(couponPackId)).
            fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 获得用户某个礼包购买数
     * @param packId
     * @param userId
     * @return
     */
    public int getUserCouponPackBuyCount(int packId,int userId){
        return db().selectCount().from(VIRTUAL_ORDER).where(VIRTUAL_ORDER.GOODS_TYPE.eq(GOODS_TYPE_COUPON_PACK)).and(VIRTUAL_ORDER.ORDER_STATUS.eq(ORDER_STATUS_FINISHED)).and(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(packId)).and(VIRTUAL_ORDER.USER_ID.eq(userId)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 优惠券礼包下单
     * @param param
     * @param userId
     * @return
     */
    public String createOrder(CouponPackOrderParam param,Integer userId,String clientIp){
        //用户的余额和积分
        UserRecord user = memberService.getUserRecordById(userId);
        if(param.getAccountDiscount() != null && param.getAccountDiscount().compareTo(user.getAccount()) > 0){
            throw new BusinessException(JsonResultCode.CODE_BALANCE_INSUFFICIENT);
        }
        if(param.getScoreDiscount() != null && param.getScoreDiscount() > 0 && param.getScoreDiscount() > user.getScore()){
            throw new BusinessException(JsonResultCode.CODE_SCORE_INSUFFICIENT);
        }
        if(param.getMemberCardBalance() != null && param.getMemberCardBalance().compareTo(BigDecimal.ZERO) > 0 && StringUtil.isNotBlank(param.getCardNo())){
            UserCardRecord userCard = memberService.card.getUserCardInfoByCardNo(param.getCardNo());
            if(userCard == null){
                throw new BusinessException(JsonResultCode.CODE_USER_CARD_NONE);
            }
            if(userCard.getMoney().compareTo(param.getMemberCardBalance()) < 0){
                throw new BusinessException(JsonResultCode.CODE_USER_CARD_BALANCE_INSUFFICIENT);
            }
        }

        CouponPackRecord couponPackRecord = db().fetchAny(COUPON_PACK,COUPON_PACK.ID.eq(param.getPackId()));

        BigDecimal moneyPaid = param.getOrderAmount() == null ? BigDecimal.ZERO : (param.getOrderAmount().subtract(param.getAccountDiscount() == null ? BigDecimal.ZERO : param.getAccountDiscount()).subtract(param.getMemberCardBalance() == null ? BigDecimal.ZERO : param.getMemberCardBalance()).setScale(2,BigDecimal.ROUND_HALF_UP));
        String payCode = moneyPaid.compareTo(BigDecimal.ZERO) > 0 ? OrderConstant.PAY_CODE_WX_PAY : (param.getScoreDiscount() > 0 ? OrderConstant.PAY_CODE_SCORE_PAY : OrderConstant.PAY_CODE_BALANCE_PAY);
        String orderSn = IncrSequenceUtil.generateOrderSn("M");


        VirtualOrderRecord insertVirtualOrderRecord = db().newRecord(VIRTUAL_ORDER);
        insertVirtualOrderRecord.setOrderSn(orderSn);
        insertVirtualOrderRecord.setUserId(userId);
        insertVirtualOrderRecord.setVirtualGoodsId(param.getPackId());
        insertVirtualOrderRecord.setOrderStatus(ORDER_STATUS_WAIT_PAY);
        insertVirtualOrderRecord.setInvoiceId(param.getInvoiceId());
        insertVirtualOrderRecord.setInvoiceDetail(param.getInvoiceDetail());
        insertVirtualOrderRecord.setPayCode(payCode);
        insertVirtualOrderRecord.setMoneyPaid(moneyPaid);
        insertVirtualOrderRecord.setUseAccount(param.getAccountDiscount());
        insertVirtualOrderRecord.setUseScore(param.getScoreDiscount());
        insertVirtualOrderRecord.setMemberCardBalance(param.getMemberCardBalance());
        insertVirtualOrderRecord.setCardNo(param.getCardNo());
        insertVirtualOrderRecord.setOrderAmount(param.getOrderAmount() == null ? BigDecimal.ZERO : param.getOrderAmount());
        insertVirtualOrderRecord.setCurrency("CNY");//TODO 币种
        insertVirtualOrderRecord.setGoodsType(GOODS_TYPE_COUPON_PACK);
        insertVirtualOrderRecord.setAccessMode(couponPackRecord.getAccessMode());

        insertVirtualOrderRecord.insert();
        if(moneyPaid.compareTo(BigDecimal.ZERO) <= 0){
            this.finishPayCallback(insertVirtualOrderRecord,null);
        }else{
            AtomicReference<WebPayVo> webPayVo = new AtomicReference<>();

            //微信支付接口
            try {
                webPayVo.set(mpPaymentService.wxUnitOrder(clientIp, couponPackRecord.getPackName(), orderSn, moneyPaid.multiply(BigDecimal.valueOf(100)), user.getWxOpenid()));
            } catch (WxPayException e) {
                logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", orderSn, e);
                throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }catch (Exception e) {
                logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", orderSn, e.getMessage());
                throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }
            logger().debug("优惠券礼包-微信支付接口调用结果：{}", webPayVo.get());
            // 更新记录微信预支付id：prepayid
            this.updatePrepayId(orderSn,webPayVo.get().getResult().getPrepayId());


        }
        return insertVirtualOrderRecord.getOrderSn();
    }

    /**
     * 支付完成的回调
     * @param orderRecord
     * @param paymentRecord
     * @return
     */
    public VirtualOrderRecord finishPayCallback(VirtualOrderRecord orderRecord, PaymentRecordRecord paymentRecord){
        db().update(VIRTUAL_ORDER).
            set(VIRTUAL_ORDER.ORDER_STATUS,ORDER_STATUS_FINISHED).
            set(VIRTUAL_ORDER.PAY_SN,(paymentRecord == null ? "" : paymentRecord.getPaySn())).
            set(VIRTUAL_ORDER.PAY_TIME, DateUtil.getLocalDateTime()).
            where(VIRTUAL_ORDER.ORDER_SN.eq(orderRecord.getOrderSn())).
            execute();
        orderRecord.refresh();

        if(orderRecord.getUseScore() != null && orderRecord.getUseScore() > 0){
            ScoreParam scoreParam = new ScoreParam();
            scoreParam.setScore(- orderRecord.getUseScore());
            scoreParam.setUserId(orderRecord.getUserId());
            scoreParam.setOrderSn(orderRecord.getOrderSn());
            scoreParam.setRemarkCode(RemarkTemplate.ORDER_MAKE.code);
            try {
                memberService.score.updateMemberScore(scoreParam, INTEGER_ZERO, TYPE_SCORE_PAY.val(), TRADE_FLOW_OUT.val());
            } catch (MpException e) {
                e.printStackTrace();
            }
        }
        if(BigDecimalUtil.greaterThanZero(orderRecord.getUseAccount())){
            AccountParam accountParam = new AccountParam() {{
                setUserId(orderRecord.getUserId());
                setAmount(orderRecord.getUseAccount().negate());
                setOrderSn(orderRecord.getOrderSn());
                setPayment(PAY_CODE_BALANCE_PAY);
                setIsPaid(UACCOUNT_CONSUMPTION.val());
                setRemarkId(RemarkTemplate.ORDER_MAKE.code);
            }};
            TradeOptParam tradeOptParam = TradeOptParam.builder()
                .tradeType(TYPE_CRASH_ACCOUNT_PAY.val())
                .tradeFlow(TRADE_FLOW_OUT.val())
                .build();
            try {
                memberService.account.updateUserAccount(accountParam,tradeOptParam);
            } catch (MpException e) {
                e.printStackTrace();
            }
        }
        if(BigDecimalUtil.greaterThanZero(orderRecord.getMemberCardBalance()) && StringUtil.isNotBlank(orderRecord.getCardNo())){
            CardConsumpData cardConsumpData = new CardConsumpData()
                .setUserId(orderRecord.getUserId())
                // 会员卡更新金额，区分正负号，这里是负号，意为扣减
                .setMoney(orderRecord.getMemberCardBalance().negate())
                .setCardNo(orderRecord.getCardNo())
                .setReason(orderRecord.getOrderSn())
                // 消费类型 :只支持普通卡0
                .setType(MCARD_TP_NORMAL);
            TradeOptParam tradeOpt = TradeOptParam
                .builder()
                .tradeFlow(TYPE_CRASH_MEMBER_CARD_PAY.val())
                .tradeFlow(TRADE_FLOW_OUT.val())
                .build();
            try {
                memberService.card.updateMemberCardAccount(cardConsumpData,tradeOpt);
            } catch (MpException e) {
                e.printStackTrace();
            }
        }

        //TODO 按照活动规则发放优惠券

        return orderRecord;
    }

    /**
     *
     * @param orderSn
     * @param prepayId
     */
    public void updatePrepayId(String orderSn,String prepayId){
        db().update(VIRTUAL_ORDER).set(VIRTUAL_ORDER.PREPAY_ID,prepayId).where(VIRTUAL_ORDER.ORDER_SN.eq(orderSn)).execute();
    }

    public VirtualOrderRecord getRecord(String orderSn){
        return db().fetchAny(VIRTUAL_ORDER,VIRTUAL_ORDER.ORDER_SN.eq(orderSn));
    }


}

