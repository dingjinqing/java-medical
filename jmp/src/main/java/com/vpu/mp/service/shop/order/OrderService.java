package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jooq.Record1;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.UserTag;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderGoods;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoOutput;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;

/**
 * 
 * @author 常乐 2019年6月27日
 */
public class OrderService extends BaseService {

	/**
	 * 订单查询
	 * @param OrderPageListQueryParam
	 * @return PageResult
	 */
	public PageResult<OrderListInfoOutput> getPageList(OrderPageListQueryParam param) {
		// 提交需删除
		shopId=6797286;
		PageResult<OrderListInfoOutput> pageResult = new PageResult<>();
		Page page = param.getPage();
		SelectJoinStep<Record1<String>> mainOrder = db().select(ORDER_INFO.MAIN_ORDER_SN).from(ORDER_INFO);
		//分组聚合主订单
		mainOrder.groupBy(ORDER_INFO.MAIN_ORDER_SN);
		buildOptions(mainOrder, param);
		//得到主订单号
		PageResult<String> mainOrderResult = getPageResult(mainOrder,page.getCurrentPage(),page.getPageRows(),String.class);
		pageResult.setPage(mainOrderResult.getPage());
		if(mainOrderResult.getDataList().size() < 1) {
			return pageResult;
		}
		//查询出全部订单按照MAIN_ORDER_SN分组
		Map<String, List<OrderListInfoOutput>> allOrder = db().selectDistinct(ORDER_INFO.ORDER_ID.as("Id") , ORDER_INFO.asterisk())
				.from(ORDER_INFO).where(ORDER_INFO.MAIN_ORDER_SN.in(mainOrderResult.getDataList()))
				.orderBy(ORDER_INFO.ORDER_ID)
				.fetchGroups(ORDER_INFO.MAIN_ORDER_SN,OrderListInfoOutput.class);
		//构造展示商品的订单(被拆分订单只需查询子订单的商品,未被拆分直接查主订单)
		Map<Integer,OrderListInfoOutput> goodsList = new HashMap<Integer,OrderListInfoOutput>();
		//主订单
		ArrayList<OrderListInfoOutput> arrayList = new ArrayList<OrderListInfoOutput>(mainOrderResult.getDataList().size());
		for (String str : mainOrderResult.getDataList()) {
			List<OrderListInfoOutput> list = allOrder.get(str);
			int size = list.size();
			OrderListInfoOutput mOrder = null;
			List<OrderListInfoOutput> cList = size > 1 ? new ArrayList<OrderListInfoOutput>(size - 1) : null;
			for (OrderListInfoOutput order : list) {
				if(order.getOrderSn().equals(str)) { 
					mOrder = order;
					if(size ==1) {
						goodsList.put(order.getOrderId(),order);
					}
				}else {
					cList.add(order);
					//添加展示商品的订单
					goodsList.put(order.getOrderId(),order);
				}
			}
			mOrder.setChildOrders(cList);
			arrayList.add(mOrder);	
			
		}
		//需要查询商品的订单
		Integer[] goodsListToSearch = goodsList.keySet().toArray(new Integer[0]);
		//key为order_id,v为其下商品
		Map<Integer, List<OrderGoods>> goods = getGoodsByOrderId(goodsListToSearch);
		Set<Entry<Integer, List<OrderGoods>>> entrySet = goods.entrySet();
		for (Entry<Integer, List<OrderGoods>> entry : entrySet) {
			goodsList.get(entry.getKey()).setGoods(entry.getValue());
		}
		pageResult.setDataList(arrayList);
		return pageResult;
	}

	/**
	  * 条件查询
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		select.orderBy(ORDER_INFO.ORDER_ID.desc());
		//输入商品名称需要join order_goods表
		if(!StringUtils.isEmpty(param.goodsName)){
			select.innerJoin(ORDER_GOODS).on(ORDER_INFO.ORDER_ID.eq(ORDER_GOODS.ORDER_ID));
			select.where(ORDER_GOODS.GOODS_NAME.like(likeValue(param.goodsName)));
		}
		if(!StringUtils.isEmpty(param.orderSn)){
			 select.where(ORDER_INFO.ORDER_SN.eq(param.orderSn));
		}
		if(param.orderStatus != null){
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
			select.innerJoin(User.USER).on(ORDER_INFO.USER_ID.eq(User.USER.USER_ID));
			select.where(User.USER.USERNAME.eq(param.userName));
		}
		if(!StringUtils.isEmpty(param.source)){
			select.where(ORDER_INFO.SOURCE.eq(param.source));
		}
		//会员标签tag
		if(param.tagIds != null){
			if(StringUtils.isEmpty(param.userName)) {
				select.innerJoin(User.USER).on(ORDER_INFO.USER_ID.eq(User.USER.USER_ID));
			}
			select.innerJoin(UserTag.USER_TAG).on(UserTag.USER_TAG.USER_ID.eq(User.USER.USER_ID));
			select.where(UserTag.USER_TAG.TAG_ID.in(param.tagIds));
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
		return select;
	 }

	// /**
	// * 订单数量
	// * @return
	// */
	// public int getPageCount() {
	// return db().fetchCount(ORDER_INFO);
	// }
	public OrderListInfoOutput get(String orderSn) {
		List<OrderListInfoOutput> result = db().select(ORDER_INFO.asterisk())
			.from(ORDER_INFO)
			.where(ORDER_INFO.MAIN_ORDER_SN.eq(orderSn))
			.orderBy(ORDER_INFO.ORDER_ID)
			.fetchInto(OrderListInfoOutput.class);
		int size = result.size();
		if(size == 1) {
			getGoodsByOrderId(new Integer[] {result.get(0).getOrderId()});
			//todo
		}
		return null;
	}
	/**
	 * 通过订单[]查询其下商品
	 * @param goodsListToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<Integer, List<OrderGoods>> getGoodsByOrderId(Integer[] goodsListToSearch) {
		Map<Integer, List<OrderGoods>> goods = db().select(ORDER_GOODS.ORDER_ID,ORDER_GOODS.ORDER_SN,ORDER_GOODS.GOODS_NAME).from(ORDER_GOODS).where(ORDER_GOODS.ORDER_ID.in(goodsListToSearch))
				.fetchGroups(ORDER_GOODS.ORDER_ID,OrderGoods.class);
		return goods;
		
	}
}
