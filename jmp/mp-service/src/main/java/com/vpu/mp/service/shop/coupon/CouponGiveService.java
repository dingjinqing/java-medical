package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.GIVE_VOUCHER;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jooq.Record11;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectWhereStep;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveGrantParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListConditionVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopVo;

import org.springframework.stereotype.Service;

/**
 * 优惠券管理
 * 
 * @author liangchen
 * @date 2019年7月29日
 */
@Service

public class CouponGiveService extends ShopBaseService {

	/**
	 * 优惠券发放情况分页列表
	 * 
	 * @param param
	 * @return listVo
	 */
	public PageResult<CouponGiveListVo> getCouponGiveList(CouponGiveListParam param) {
		try {
			/* 查询活动信息 */
			SelectLimitStep<Record5<String, Timestamp, String, Byte, Byte>> couponGiveListVo = db()
					.select(GIVE_VOUCHER.ACT_NAME, GIVE_VOUCHER.CREATE_TIME, GIVE_VOUCHER.SEND_CONDITION,
							GIVE_VOUCHER.SEND_ACTION, GIVE_VOUCHER.SEND_STATUS)
					.from(GIVE_VOUCHER);
			/* 模糊查询 */
			if (!StringUtils.isNullOrEmpty(param.getActName())) {
				couponGiveListVo = ((SelectWhereStep<Record5<String, Timestamp, String, Byte, Byte>>) couponGiveListVo)
						.where(GIVE_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName())));
			}
			/* 整合分页信息 */
			PageResult<CouponGiveListVo> listVo = this.getPageResult(couponGiveListVo, param.getCurrentPage(),
					param.getPageRows(), CouponGiveListVo.class);
			/* 整合活动对应优惠券信息 */
			for (CouponGiveListVo vo : listVo.getDataList()) {
				/* 解析得到活动中包含的优惠券 */
				String dataList = vo.getSendCondition();
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode;
				jsonNode = objectMapper.readTree(dataList);
				String voucherId = jsonNode.get("coupon_ids").toString();
				String id = voucherId.replace("\"", "");
				String[] idArray = id.split(",");
				/* 优惠券信息 */
				List<CouponGiveListConditionVo> tempListVo = new ArrayList<CouponGiveListConditionVo>();
				for (String selectId : idArray) {
					Optional<CouponGiveListConditionVo> couponVo = db()
							.select(MRKING_VOUCHER.ACT_NAME.as("coupon_name"), MRKING_VOUCHER.LEAST_CONSUME,
									MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.VALIDITY)
							.from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(Integer.valueOf(selectId)))
							.fetchOptionalInto(CouponGiveListConditionVo.class);
					tempListVo.add(couponVo.isPresent() ? couponVo.get() : null);
				}
				/* 完善某一活动对应的优惠券信息 */
				vo.setCouponGiveListConditionVo(tempListVo);
			}

			return listVo;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 优惠券明细
	 * 
	 * @param param
	 * @return 
	 */
	public PageResult<CouponGiveDetailVo> getDetail(CouponGiveDetailParam param) {
		SelectConditionStep<Record11<String, String, String, Byte, Byte, String, Timestamp, Timestamp, Timestamp, Timestamp, Byte>> 
		detailVo = db()
				.select(USER.USERNAME, USER.MOBILE, MRKING_VOUCHER.ACT_NAME.as("coupon_name"),
						CUSTOMER_AVAIL_COUPONS.ACCESS_MODE, CUSTOMER_AVAIL_COUPONS.IS_USED,
						CUSTOMER_AVAIL_COUPONS.ORDER_SN, CUSTOMER_AVAIL_COUPONS.START_TIME,
						CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.CREATE_TIME,
						CUSTOMER_AVAIL_COUPONS.USED_TIME, CUSTOMER_AVAIL_COUPONS.DEL_FLAG)
				.from(USER, MRKING_VOUCHER, CUSTOMER_AVAIL_COUPONS)
				.where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(param.getActId()))
				.and(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(USER.USER_ID)).and(MRKING_VOUCHER.ID.eq(param.getCouponId()));
		if(!StringUtils.isNullOrEmpty(param.getMobile())) {
			detailVo = detailVo.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if(!StringUtils.isNullOrEmpty(param.getUsername())) {
			detailVo = detailVo.and(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (param.getIsUsed() != CouponGiveDetailParam.ISUSED_DEFAULT_VALUE) {
			detailVo = detailVo.and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte)param.getIsUsed()));
		}
		/* 整合分页信息 */
		PageResult<CouponGiveDetailVo> listVo = this.getPageResult(detailVo, param.getCurrentPage(),
				param.getPageRows(), CouponGiveDetailVo.class);
		return listVo;
	}

	/**
	 * 发放优惠券
	 * 
	 * @param param
	 * @return
	 */
	public void insertGrant(CouponGiveGrantParam param) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String condition = objectMapper.writeValueAsString(param.getCouponGiveGrantInfoParams());

			db().insertInto(GIVE_VOUCHER, GIVE_VOUCHER.ACT_NAME, GIVE_VOUCHER.SEND_CONDITION, GIVE_VOUCHER.CARD_ID,
					GIVE_VOUCHER.TAG_ID, GIVE_VOUCHER.USER, GIVE_VOUCHER.HAVE_PAY, GIVE_VOUCHER.NO_PAY,
					GIVE_VOUCHER.MAX_COUNT, GIVE_VOUCHER.MIN_COUNT, GIVE_VOUCHER.MAX_AVE_PRICE,
					GIVE_VOUCHER.MIN_AVE_PRICE, GIVE_VOUCHER.SEND_ACTION, GIVE_VOUCHER.START_TIME)
					.values(param.getActName(), condition, param.getCardId(), param.getTagId(), param.getUser(),
							param.getHavePay(), param.getNoPay(), param.getMaxCount(), param.getMinCount(),
							param.getMaxAvePrice(), param.getMinAvePrice(), param.getSendAction(), param.getStartTime())
					.execute();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 优惠券弹窗
	 *
	 * @param param
	 * @return popListVo
	 */
	public List<CouponGivePopVo> popWindows(CouponGivePopParam param) {
		/* 添加模糊查询条件 */
		if (StringUtils.isNullOrEmpty(param.getActName())) {
			param.setActName("");
		}
		/* 查询，并筛选出正确的使用限制条件 */
		List<CouponGivePopVo> popVo = db()
				.select(MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.LEAST_CONSUME,
						MRKING_VOUCHER.REMAIN_AMOUNT, MRKING_VOUCHER.USE_CONSUME_RESTRICT)
				.from(MRKING_VOUCHER).where(MRKING_VOUCHER.USE_CONSUME_RESTRICT.eq((byte) 1)
						.and(MRKING_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName()))))
				.or(MRKING_VOUCHER.USE_CONSUME_RESTRICT.eq((byte) 0).and(MRKING_VOUCHER.LEAST_CONSUME.eq(0))
						.and(MRKING_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName()))))
				.fetchInto(CouponGivePopVo.class);

		return popVo;

	}
}
