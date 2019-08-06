package com.vpu.mp.service.shop.order.card;

import static com.vpu.mp.db.shop.tables.CardOrder.CARD_ORDER;
import static com.vpu.mp.db.shop.tables.CouponPack.COUPON_PACK;
import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static com.vpu.mp.db.shop.tables.CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.tables.RefundCardRecord.REFUND_CARD_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderVo;
import com.vpu.mp.service.pojo.shop.order.virtual.RefundStatus;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualGoodsOrderType;
import com.vpu.mp.service.shop.member.ScoreService;
/**
 * @author huangronggang
 * @date 2019年8月1日
 */

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CouponPackOrderService extends ShopBaseService {
	/** 发放优惠劵的获取方式，0：发放，1：领取，2：礼包*/
	public static final Byte CUSTOMER_AVAIL_COUPONS_ACCESSMODE_PACK=2;
	
	/** 退积分 交易类型 */
	public static final Byte REFUND_SCORE_TRADE_TYPE=9;
	
	/** 退积分 资金流向 收入*/
	public static final Byte REFUND_SCORE_TRADE_FLOW=1;

	@Autowired public ScoreService scoreService;
	/**
	 * 分页查询优惠劵礼包订单 
	 * @param param
	 * @return
	 */
	public PageResult<CouponPackOrderVo> getPageList(CouponPackOrderPageParam param){
		SelectWhereStep<? extends Record> selectFrom = db()
			.select(CARD_ORDER.ORDER_ID,CARD_ORDER.VIRTUAL_GOODS_ID,COUPON_PACK.PACK_NAME,
					CARD_ORDER.ORDER_SN,CARD_ORDER.USER_ID,USER.USERNAME,USER.MOBILE,CARD_ORDER.MONEY_PAID,CARD_ORDER.USE_ACCOUNT,CARD_ORDER.USE_SCORE,CARD_ORDER.MEMBER_CARD_BALANCE,CARD_ORDER.CARD_NO,CARD_ORDER.PAY_CODE,CARD_ORDER.PAY_NAME,CARD_ORDER.PREPAY_ID,CARD_ORDER.PAY_SN,CARD_ORDER.ORDER_AMOUNT,
					CARD_ORDER.CREATE_TIME,CARD_ORDER.RETURN_FLAG,CARD_ORDER.RETURN_SCORE,CARD_ORDER.RETURN_ACCOUNT,CARD_ORDER.RETURN_MONEY,CARD_ORDER.RETURN_CARD_BALANCE,
					CARD_ORDER.RETURN_TIME)
			.from(CARD_ORDER)
			.leftJoin(COUPON_PACK).on(CARD_ORDER.VIRTUAL_GOODS_ID.eq(COUPON_PACK.ID))
			.leftJoin(USER).on(CARD_ORDER.USER_ID.eq(USER.USER_ID));
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
		SelectConditionStep<? extends Record> condition = select.where(CARD_ORDER.GOODS_TYPE.eq(VirtualGoodsOrderType.COUPONPACK))
			  .and(CARD_ORDER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		if(!StringUtils.isBlank(param.getPackName())) {
			condition.and(COUPON_PACK.PACK_NAME.like(this.likeValue(param.getPackName())));
		}
		if(!StringUtils.isBlank(param.getOrderSn())) {
			condition.and(CARD_ORDER.ORDER_SN.like(this.likeValue(param.getOrderSn())));
		}
		if(!StringUtils.isBlank(param.getUserInfo())) {
			condition.and(USER.USERNAME.like(likeValue(param.getUserInfo())));
			condition.and(USER.MOBILE.like(likeValue(param.getUserInfo())));
		}
		if(param.getStartTime() != null) {
			condition.and(CARD_ORDER.CREATE_TIME.gt(param.getStartTime()));
		}
		if(param.getEndTime() != null) {
			condition.and(CARD_ORDER.CREATE_TIME.le(param.getEndTime()));
		}
		condition.orderBy(CARD_ORDER.CREATE_TIME);
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
	private int getVoucherAccessCount(String orderSn) {
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
	 * @param orderRefund
	 * @return
	 */
	public int refundCouponPackOrder(CouponPackOrderRefundParam orderRefund,Integer subAccountId) {
		log.info("begin refund couponpack order :orderRefund:{}",Util.toJson(orderRefund));
		try {
			if(orderRefund.getRefundScore()!=null) {
				refundScore(orderRefund,orderRefund.getRefundScore(),subAccountId);
			}
			if(orderRefund.getRefundMoney() != null) {
				refundMoney(orderRefund,orderRefund.getRefundMoney());
			}
			if(orderRefund.getRefundAccount() != null) {
				refundAccount(orderRefund,orderRefund.getRefundAccount());
			}
			if(orderRefund.getRefundBalance() != null) {
				refundBalance(orderRefund,orderRefund.getRefundBalance());
			}
		}catch(Exception e) {
			log.error("refund couponpack order execute error , error:{} ",e);
			throw e;
		}
		db().insertInto(REFUND_CARD_RECORD, REFUND_CARD_RECORD.ORDER_SN, REFUND_CARD_RECORD.USER_ID,
		            REFUND_CARD_RECORD.MONEY_PAID, REFUND_CARD_RECORD.USE_ACCOUNT, REFUND_CARD_RECORD.USE_SCORE,REFUND_CARD_RECORD.MEMBER_CARD_BALANCE,
		            REFUND_CARD_RECORD.IS_SUCCESS)
		            .values(orderRefund.getOrderSn(), orderRefund.getUserId(), orderRefund.getRefundMoney(), orderRefund.getRefundAccount(),orderRefund.getRefundScore(),orderRefund.getRefundBalance(), RefundStatus.SUCCESS).execute();
		log.info("refund couponpack order success");
		saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_SHIP.code }), new String[] {Util.toJson(orderRefund)});
		return 0;
	}

	/**
	 * 退会员卡余额
	 * @param orderRefund
	 * @param refundBalance
	 */
	private void refundBalance(CouponPackOrderRefundParam orderRefund, BigDecimal refundBalance) {
		if(BigDecimal.ZERO.compareTo(refundBalance) > 0) {
			return ;
		}
		this.transaction(()->{
			int execute = db().update(CARD_ORDER)
					.set(CARD_ORDER.RETURN_CARD_BALANCE,CARD_ORDER.RETURN_CARD_BALANCE.add(refundBalance))
					.set(CARD_ORDER.RETURN_FLAG,RefundStatus.SUCCESS)
					.where(CARD_ORDER.ORDER_ID.eq(orderRefund.getOrderId()))
					.and(CARD_ORDER.MEMBER_CARD_BALANCE.ge(CARD_ORDER.RETURN_CARD_BALANCE.add(refundBalance)))
					.execute();
			if(execute>0) {
				execute = db().update(USER_CARD)
						.set(USER_CARD.MONEY,USER_CARD.MONEY.add(refundBalance))
						.where(USER_CARD.CARD_NO.eq(orderRefund.getCardNo()))
						.execute();
				log.info("refund balance,execute result:{},cardNo :{},add acount:{}",execute,orderRefund.getCardNo(),refundBalance);
				if(execute == 0) {
					throw new RuntimeException("Refill Balance Failed!");
				}
			}
		});
	}

	/**
	 * 退账户余额
	 * @param orderRefund
	 * @param refundAccount
	 * 
	 */
	private void refundAccount(CouponPackOrderRefundParam orderRefund, BigDecimal refundAccount) {
		if(BigDecimal.ZERO.compareTo(refundAccount) > 0) {
			return ;
		}
		this.transaction(()->{
			int execute = db().update(CARD_ORDER)
					.set(CARD_ORDER.RETURN_ACCOUNT,CARD_ORDER.RETURN_ACCOUNT.add(refundAccount))				
					.set(CARD_ORDER.RETURN_FLAG,RefundStatus.SUCCESS)
					.where(CARD_ORDER.ORDER_ID.eq(orderRefund.getOrderId()))
					.and(CARD_ORDER.USE_ACCOUNT.ge(CARD_ORDER.RETURN_ACCOUNT.add(refundAccount)))
					.execute();
			if(execute >0) {
				execute = db().update(USER)
						.set(USER.ACCOUNT,USER.ACCOUNT.add(refundAccount))
						.where(USER.USER_ID.eq(orderRefund.getUserId()))
						.execute();
				log.info("refund account,execute result:{},userId :{},add account:{}",execute,orderRefund.getUserId(),refundAccount);
				if(execute == 0) {
					throw new RuntimeException("Refill Account Failed!");
				}
			}
		});
	}

	/**
	 * @param orderRefund
	 * @param refundMoney
	 */
	private void refundMoney(CouponPackOrderRefundParam orderRefund, BigDecimal refundMoney) {
		// TODO 退现金
		
	}

	/**
	 * @param orderRefund
	 * @param refundScore
	 * @param subAccountId 
	 */
	private void refundScore(CouponPackOrderRefundParam orderRefund, BigDecimal refundScore, Integer subAccountId) {
		if(BigDecimal.ZERO.compareTo(refundScore)>=0) {
			return ;
		}
		this.transaction(()->{
			
			int execute = db().update(CARD_ORDER)
					.set(CARD_ORDER.RETURN_SCORE,CARD_ORDER.RETURN_SCORE.add(refundScore))
					.set(CARD_ORDER.RETURN_FLAG,RefundStatus.SUCCESS)
					.where(CARD_ORDER.ORDER_ID.eq(orderRefund.getOrderId()))
					.and(CARD_ORDER.USE_SCORE.ge(CARD_ORDER.RETURN_SCORE.add(refundScore)))
					.execute();
			if(execute == 0) {
				return ;
			}
			ScoreParam scoreParam = new ScoreParam();
			scoreParam.setOrderSn(orderRefund.getOrderSn());
			scoreParam.setScore(refundScore.intValue());
			scoreParam.setScoreDis(0);
			scoreParam.setRemark(JsonResultMessage.ORDER_VIRTUAL_COUPONPACK_REFUND_SCORE);
			JsonResultCode resultCode = scoreService.updateMemberScore(scoreParam, subAccountId, orderRefund.getUserId(), REFUND_SCORE_TRADE_TYPE, REFUND_SCORE_TRADE_FLOW);
			if(!JsonResultCode.CODE_SUCCESS.equals(resultCode)) {
				throw new RuntimeException("refund score failed");
			}
		});
	}
	
	
	public void updateSendFlag(Byte sendFlag,Integer orderId) {
		db().update(CARD_ORDER)
			.set(CARD_ORDER.STILL_SEND_FLAG,sendFlag)
			.where(CARD_ORDER.ORDER_ID.eq(orderId)).execute();
	}
	
}

