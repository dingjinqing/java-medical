package com.vpu.mp.service.shop.order.virtual;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderVo;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.vpu.mp.db.shop.tables.CouponPack.COUPON_PACK;
import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static com.vpu.mp.db.shop.tables.CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.VirtualOrder.VIRTUAL_ORDER;

/**
 * @author huangronggang
 * @date 2019年8月1日
 */
@Service
public class CouponPackOrderService extends VirtualOrderService {
	/** 发放优惠劵的获取方式，0：发放，1：领取，2：礼包*/
	public static final Byte CUSTOMER_AVAIL_COUPONS_ACCESSMODE_PACK=2;

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
	    return db().selectCount().from(VIRTUAL_ORDER).where(VIRTUAL_ORDER.GOODS_TYPE.eq(GOODS_TYPE_COUPON_PACK)).and(VIRTUAL_ORDER.ORDER_STATUS.eq(ORDER_STATUS_FINISHED)).and(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(couponPackId)).fetchOneInto(Integer.class);
    }


}

