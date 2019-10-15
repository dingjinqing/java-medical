package com.vpu.mp.service.shop.order.info;

import java.util.Map;

import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;

/**
 * mp端订单业务
 * @author 王帅
 * return 不可变map,key为all,waitPay,waitDelivery,shipped,finished,returning
 *        value默认为0
 * 
 */
@Service
public class MpOrderInfoService extends OrderInfoService{
	/**
	 * 个人中心订单状态数量展示
	 * @param userId
	 * @param isContainSubOrder
	 * @return
	 */
	public Map<String,Integer> getOrderStatusNum(Integer userId , boolean isContainSubOrder) {
		//基础状态数量
		Map<Byte, Integer> countMap = db().select(DSL.count() , TABLE.ORDER_STATUS).
		from(TABLE).
		where(setIsContainSubOrder(
				TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).
				and(TABLE.REFUND_STATUS.eq(OrderConstant.REFUND_DEFAULT_STATUS)).
				and(TABLE.USER_ID.eq(userId))
				, isContainSubOrder)).
		groupBy(TABLE.ORDER_STATUS).
		fetch().
		intoMap(TABLE.ORDER_STATUS , DSL.count());
		//退款退货中
		Integer returning = db().select(DSL.count()).
		from(TABLE).
		where(setIsContainSubOrder(
				TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).
				and(TABLE.REFUND_STATUS.eq(OrderConstant.REFUND_STATUS_FINISH)).
				and(TABLE.USER_ID.eq(userId))
				,isContainSubOrder)).
		fetchOneInto(Integer.class);
		//退款退货完成
		Integer returnFinish = db().select(DSL.count()).
		from(TABLE).
		where(setIsContainSubOrder(
				TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).
				and(TABLE.REFUND_STATUS.in(OrderConstant.REFUND_STATUS_AUDITING , OrderConstant.REFUND_STATUS_AUDIT_PASS, OrderConstant.REFUND_STATUS_AUDIT_NOT_PASS , OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING , OrderConstant.REFUND_STATUS_FINISH , OrderConstant.REFUND_STATUS_REFUSE)).
				and(TABLE.USER_ID.eq(userId)).
				and(TABLE.ORDER_STATUS.notIn(OrderConstant.ORDER_FINISHED , OrderConstant.ORDER_RETURN_FINISHED , OrderConstant.ORDER_REFUND_FINISHED))
				,isContainSubOrder)).
		fetchOneInto(Integer.class);
		//初始化不可变map
		Map<String,Integer> result = ImmutableMap.<String,Integer>builder()
				.put(OrderConstant.All,
						countMap.values().stream().reduce(0, Integer::sum) + (returning == null ? Integer.valueOf(0) : returning) + (returnFinish == null ? Integer.valueOf(0) : returnFinish))
				.put(OrderConstant.WAIT_PAY,
						mapDefaultValue(countMap , OrderConstant.ORDER_WAIT_PAY))
				.put(OrderConstant.WAIT_DELIVERY,
						mapDefaultValue(countMap , OrderConstant.ORDER_WAIT_DELIVERY))
				.put(OrderConstant.SHIPPED,
						mapDefaultValue(countMap , OrderConstant.ORDER_SHIPPED))
				.put(OrderConstant.FINISHED,
						(mapDefaultValue(countMap , OrderConstant.ORDER_FINISHED)) + mapDefaultValue(countMap , OrderConstant.ORDER_RECEIVED))
				.put(OrderConstant.RETURNING,
						returning == null ? Integer.valueOf(0) : returning)
				.build();
		return result;
	}
	
	/**
	 * map value type is Integer,default is 0
	 * @param countMap
	 * @param status
	 * @return
	 */
	private Integer mapDefaultValue(Map<Byte, Integer> countMap , Byte status) {
		Integer temp = countMap.get(status);
		if(temp == null) {
			return Integer.valueOf(0);
		}
		return temp;
	} 
	
	/**
	 * 是否包含子单
	 * @param condition
	 * @param isContainSubOrder
	 * @return
	 */
	private Condition setIsContainSubOrder(Condition condition , boolean isContainSubOrder) {
		return isContainSubOrder ? condition : condition.and(TABLE.MAIN_ORDER_SN.eq(StringUtils.EMPTY).or(TABLE.MAIN_ORDER_SN.eq(TABLE.ORDER_SN)));
		
	}
}
