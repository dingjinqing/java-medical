package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.GIVE_VOUCHER;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jooq.Record5;
import org.jooq.SelectLimitStep;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListConditionVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListVo;

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
	 * @return coupenGiveListVo
	 */
	public PageResult<CouponGiveListVo> getCouponGiveList(CouponGiveListParam param) {
		try {
		SelectLimitStep<Record5<String, Timestamp, String, Byte, Byte>> couponGiveListVo = db()
				.select(GIVE_VOUCHER.ACT_NAME,
						GIVE_VOUCHER.CREATE_TIME, 
						GIVE_VOUCHER.SEND_CONDITION,
						GIVE_VOUCHER.SEND_ACTION, 
						GIVE_VOUCHER.SEND_STATUS)
				.from(GIVE_VOUCHER)
				.where(GIVE_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName())));
		
		PageResult<CouponGiveListVo> listVo = this.getPageResult(couponGiveListVo, param.getCurrentPage(),
				param.getPageRows(), CouponGiveListVo.class);

		for(CouponGiveListVo vo : listVo.getDataList())
		{
			String dataList = vo.getSendCondition();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode;
			
				jsonNode = objectMapper.readTree(dataList);
			
			String voucherId =  jsonNode.get("coupon_ids").toString();
			String id=voucherId.replace("\"", "");
			String[] idArray = id.split(",");
			
			List<CouponGiveListConditionVo> tempListVo = new ArrayList<CouponGiveListConditionVo>();
			
			for(String selectId : idArray) {
			Optional<CouponGiveListConditionVo> couponVo = 
					db().select(MRKING_VOUCHER.ACT_NAME.as("coupon_name"),
					MRKING_VOUCHER.LEAST_CONSUME,
					MRKING_VOUCHER.DENOMINATION,
					MRKING_VOUCHER.VALIDITY)
					.from(MRKING_VOUCHER)
					.where(MRKING_VOUCHER.ID.eq(Integer.valueOf(selectId)))
					.fetchOptionalInto(CouponGiveListConditionVo.class);
			
			tempListVo.add(couponVo.isPresent() ? couponVo.get() : null);
			
			}
			vo.setCouponGiveListConditionVo(tempListVo);
		}

		return listVo;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
