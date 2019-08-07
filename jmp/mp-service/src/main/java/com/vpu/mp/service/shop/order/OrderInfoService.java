package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.GroupBuyList.GROUP_BUY_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserTag.USER_TAG;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jooq.Condition;
import org.jooq.Record2;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Data;

/**
 * Table:order_info
 * 
 * @author 王帅
 *
 */
@Service
public class OrderInfoService extends ShopBaseService {

	public final OrderInfo TABLE = ORDER_INFO;
	/**
	 * 	订单综合查询:通过条件获得主订单号（因为前端显示以主订单为主）
	 * @param param
	 * @return
	 */
	public PageResult<MainOrderResult> getMainOrders(OrderPageListQueryParam param){
		SelectJoinStep<Record2<String, Integer>> mainOrder = db().select(TABLE.MAIN_ORDER_SN,DSL.min(TABLE.ORDER_ID).as("orderId")).from(TABLE);
		//分组聚合主订单
		mainOrder.groupBy(TABLE.MAIN_ORDER_SN);
		buildOptions(mainOrder, param);
		//得到主订单号		
		return getPageResult(mainOrder,param.getCurrentPage(),param.getPageRows(),MainOrderResult.class);
	}
	
	@Data
	static class MainOrderResult {
		String mainOrderSn;
		Integer orderId;
	}
	
	/**
	  * 构造综合查询条件
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		select.orderBy(ORDER_INFO.ORDER_ID.as("orderId").desc());
		//输入商品名称需要join order_goods表
		if(!StringUtils.isEmpty(param.goodsName)){
			select.innerJoin(ORDER_GOODS).on(ORDER_INFO.ORDER_ID.eq(ORDER_GOODS.ORDER_ID));
			select.where(ORDER_GOODS.GOODS_NAME.like(likeValue(param.goodsName)));
		}
		if(!StringUtils.isEmpty(param.orderSn)){
			 select.where(ORDER_INFO.ORDER_SN.eq(param.orderSn));
		}
		if(param.orderStatus != null && param.orderStatus.length != 0){
			 select.where(ORDER_INFO.ORDER_STATUS.in(param.orderStatus));
		}
		if(param.goodsType != null) {
			select.where(DSL.sql("FIND_IN_SET("+param.goodsType+", "+ORDER_INFO.getName() +"."+ ORDER_INFO.GOODS_TYPE.getName()+")"));
		}
		if(param.deliverType != null){
			select.where(ORDER_INFO.DELIVER_TYPE.eq(param.deliverType));
		}
		//昵称需要连表查询
		if(!StringUtils.isEmpty(param.userName)){
			select.innerJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
			select.where(USER.USERNAME.like(likeValue(param.userName)));
		}
		if(!StringUtils.isEmpty(param.source)){
			select.where(ORDER_INFO.SOURCE.eq(param.source));
		}
		//会员标签tag
		if(param.tagIds != null && param.tagIds.length != 0){
			if(StringUtils.isEmpty(param.userName)) {
				select.innerJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
			}
			select.innerJoin(USER_TAG).on(USER_TAG.USER_ID.eq(USER.USER_ID));
			select.where(USER_TAG.TAG_ID.in(param.tagIds));
		}
		if(param.storeId != null){
			select.where(ORDER_INFO.STORE_ID.eq(param.storeId));
		}
		if(!StringUtils.isEmpty(param.verifyCode)){
			select.where(ORDER_INFO.VERIFY_CODE.eq(param.verifyCode));
		}
		if(!StringUtils.isEmpty(param.consignee)){
			select.where(ORDER_INFO.CONSIGNEE.eq(param.consignee));
		}
		if(param.countryCode != null){
			select.where(ORDER_INFO.COUNTRY_CODE.eq(param.countryCode));
		}
		if(param.provinceCode != null){
			select.where(ORDER_INFO.PROVINCE_CODE.eq(param.provinceCode));
		}
		if(param.cityCode != null){
			select.where(ORDER_INFO.CITY_CODE.eq(param.cityCode));
		}
		if(param.districtCode != null){
			select.where(ORDER_INFO.DISTRICT_CODE.eq(param.districtCode));
		}
		if(param.createTimeStart != null){
			select.where(ORDER_INFO.CREATE_TIME.ge(param.createTimeStart));
		}
		if(param.createTimeEnd != null){
			select.where(ORDER_INFO.CREATE_TIME.le(param.createTimeEnd));
		}
		if(param.finishedTimeStart != null){
			select.where(ORDER_INFO.FINISHED_TIME.ge(param.finishedTimeStart));
		}
		if(param.finishedTimeEnd != null){
			select.where(ORDER_INFO.FINISHED_TIME.le(param.finishedTimeEnd));
		}
		//拼团退款失败订单
		if(param.pinStatus != null && param.pinStatus.length != 0){
			select.innerJoin(GROUP_BUY_LIST).on(ORDER_INFO.ORDER_SN.eq(GROUP_BUY_LIST.ORDER_SN));
			select.where(GROUP_BUY_LIST.STATUS.in(param.pinStatus));
		}
		//构造营销活动查询条件
		activeBuildOptions(select, param);
		return select;
	 }

	/**
	 *	 构造营销货订查询条件
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<?> activeBuildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		if (param.activityId != null) {
			select.where(ORDER_INFO.ACTIVITY_ID.le(param.activityId));
		}
		return select;
	}
	 
	/**
	 * TODO 去* 通过传入的field获取对应记录
	 * 
	 * @param <T>
	 * @param userId
	 * @param field
	 * @return
	 */
	public <T> List<T> getOrdersByCondition(Condition where, Class<T> clz) {
		return db().select(TABLE.asterisk()).from(TABLE).where(where).fetchInto(clz);
	}
	
	/**
	 * TODO查询订单按照MAIN_ORDER_SN分组
	 * 
	 * @param <T>
	 * @param arrayToSearch
	 * @return
	 */
	public Map<String, List<OrderListInfoVo>> getOrdersGorupByMainSn(String... arrayToSearch) {
		return db().selectDistinct(ORDER_INFO.asterisk()).from(ORDER_INFO)
				.where(ORDER_INFO.MAIN_ORDER_SN.in(arrayToSearch)).orderBy(ORDER_INFO.ORDER_ID)
				.fetchGroups(ORDER_INFO.MAIN_ORDER_SN, OrderListInfoVo.class);
	}
	
	/**
	 * 过滤子订单数量>0时,过滤主订单下已被拆除的商品行（通过减小数量为0则不展示）
	 * @param OrderListInfoVo主订单（已经在主订单下添加了子订单及其商品）, goods主订单商品行
	 * @return  OrderListInfoVo 订单
	 */
	public OrderListInfoVo filterMainOrderGoods(OrderListInfoVo order , List<OrderGoodsVo> goods) {
		List<? extends OrderListInfoVo> cOrders = order.getChildOrders();
		//构造Map<Integer(子订单规格号), Integer(数量)>
		Map<Integer, Integer> childGoodsCount = new HashMap<>(goods.size());
		for (OrderListInfoVo oneOrder : cOrders) {
			for (OrderGoodsVo tempGoods : oneOrder.getGoods()) {
				Integer tempCount = childGoodsCount.get(tempGoods.getProductId());
				if(tempCount == null) {
					//第一次goodsNumber
					childGoodsCount.put(tempGoods.getProductId(),tempGoods.getGoodsNumber().intValue());
				}else {
					//后续加goodsNumber
					childGoodsCount.put(tempGoods.getProductId(),tempCount + tempGoods.getGoodsNumber().intValue());
				}
			}
		}
		//主订单减去子订单相应商品(数量为0不展示)
		Iterator<OrderGoodsVo> iter = goods.iterator();
		while(iter.hasNext()) {
			OrderGoodsVo orderOoods = iter.next();
			Integer count = childGoodsCount.get(orderOoods.getProductId());
			if(count != null){
				int finalCount = orderOoods.getGoodsNumber().intValue() - count;
				if(finalCount > 0 ) {
					orderOoods.setGoodsNumber(finalCount);
				}else {
					iter.remove();
				}
			}
		}
		order.setGoods(goods);
		return order;
	}
}
