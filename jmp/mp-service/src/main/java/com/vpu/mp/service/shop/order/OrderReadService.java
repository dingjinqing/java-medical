package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.GroupBuyList.GROUP_BUY_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.PartOrderGoodsShip.PART_ORDER_GOODS_SHIP;
import static com.vpu.mp.db.shop.tables.ReturnOrder.RETURN_ORDER;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserTag.USER_TAG;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sql;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo.Goods;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;

import lombok.Data;

/**
 * 	订单模块普通查询service
 * @author 常乐 2019年6月27日;王帅 2019/7/10
 */
@Service
public class OrderReadService extends ShopBaseService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 订单查询
	 * @param OrderPageListQueryParam
	 * @return PageResult
	 */
	public PageResult<? extends OrderListInfoVo> getPageList(OrderPageListQueryParam param) {
		logger.info("订单综合查询开始");
		//退款退货订单查询(其主表不同,所以走分支逻辑)
		if(param.searchType != null && param.searchType == 1) {
			return getReturnPageList(param);
		}
		PageResult<OrderListInfoVo> pageResult = new PageResult<>();
		SelectJoinStep<Record2<String, Integer>> mainOrder = db().select(ORDER_INFO.MAIN_ORDER_SN,DSL.min(ORDER_INFO.ORDER_ID).as("orderId")).from(ORDER_INFO);
		//分组聚合主订单
		mainOrder.groupBy(ORDER_INFO.MAIN_ORDER_SN);
		buildOptions(mainOrder, param);
		//得到主订单号
		PageResult<MainOrderResult> mainOrderResult = getPageResult(mainOrder,param.getCurrentPage(),param.getPageRows(),MainOrderResult.class);
		pageResult.setPage(mainOrderResult.getPage());
		if(mainOrderResult.getDataList().size() < 1) {
			return pageResult;
		}
		List<String> collect = mainOrderResult.getDataList().stream().map(MainOrderResult::getMainOrderSn).collect(Collectors.toList());
		//查询出全部订单按照MAIN_ORDER_SN分组
		Map<String, List<OrderListInfoVo>> allOrder = db().selectDistinct(ORDER_INFO.ORDER_ID.as("Id") , ORDER_INFO.asterisk())
				.from(ORDER_INFO).where(ORDER_INFO.MAIN_ORDER_SN.in(collect))
				.orderBy(ORDER_INFO.ORDER_ID)
				.fetchGroups(ORDER_INFO.MAIN_ORDER_SN,OrderListInfoVo.class);
		//构造展示商品的订单:MainOrderCount.count=1的可能为正常订单或处于未子订单未被拆分,>1的为已经拆分
		Map<Integer,OrderListInfoVo> goodsList = new HashMap<Integer,OrderListInfoVo>();
		//主订单
		ArrayList<OrderListInfoVo> mainOrderList = new ArrayList<OrderListInfoVo>(mainOrderResult.getDataList().size());
		//现子订单数>0的主订单
		ArrayList<Integer> orderCountMoreZero = new ArrayList<Integer>();
		for (String moc : collect) {
			List<OrderListInfoVo> list = allOrder.get(moc);
			int size = list.size();
			OrderListInfoVo mOrder = null;
			List<OrderListInfoVo> cList = size > 1 ? new ArrayList<OrderListInfoVo>(size - 1) : null;
			for (OrderListInfoVo order : list) {
				//将所有订单id放入goodsList,在后续向订单添加商品时增加过滤主订单下与子订单重复的商品
				goodsList.put(order.getOrderId(),order);
				if(order.getOrderSn().equals(moc)) {
					mOrder = order;
					if(size ==1) {		
						break;
					}
				}else {
					cList.add(order);
				}
			}
			if(cList != null) {
				orderCountMoreZero.add(mOrder.getOrderId());
			}
			mOrder.setChildOrders(cList);
			mainOrderList.add(mOrder);	
		}
		//需要查询商品的订单
		Integer[] goodsListToSearch = goodsList.keySet().toArray(new Integer[0]);
		//key为order_id,v为其下商品
		Map<Integer, List<OrderGoodsVo>> goods = getGoodsByOrderId(goodsListToSearch);
		Set<Entry<Integer, List<OrderGoodsVo>>> entrySet = goods.entrySet();
		for (Entry<Integer, List<OrderGoodsVo>> entry : entrySet) {
			//过滤主订单中已经拆到子订单的商品(依赖于orderinfo表自增id,当循环到主订单时其子订单下的商品都已插入到childOrders.goods里)
			if(orderCountMoreZero.contains(entry.getKey())) {
				filterMainOrderGoods(goodsList.get(entry.getKey()),entry.getValue());
				continue;
			}
			goodsList.get(entry.getKey()).setGoods(entry.getValue());
		}
		pageResult.setDataList(mainOrderList);
		logger.info("订单综合查询结束");
		return pageResult;
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
	  * 构造营销货订查询条件
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> activeBuildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
         if(param.activityId != null){
             select.where(ORDER_INFO.ACTIVITY_ID.le(param.activityId));
         }
		return select;
	 }
	 
	/**
	 * 订单详情
	 * @param mainOrderSn
	 * @return
	 */
	public OrderInfoVo get(String mainOrderSn) {
		//TODO 删除
		List<OrderInfoVo> orders = db().select(ORDER_INFO.asterisk())
			.from(ORDER_INFO)
			.where(ORDER_INFO.MAIN_ORDER_SN.eq(mainOrderSn))
			.orderBy(ORDER_INFO.ORDER_ID)
			.fetchInto(OrderInfoVo.class);
		int size = orders.size();
		if(size == 0) {
			return null;
		}
		//OrderIds
		List<Integer> orderIds = new ArrayList<Integer>(size);
		//配送信息orderSn
		List<String> sOrderSns = new ArrayList<String>();
		//退货款信息ids
		List<String> rOrderSns = new ArrayList<String>();
		//子订单
		List<OrderInfoVo> childOrders = size <=1 ? null : new ArrayList<OrderInfoVo>(size -1);
		//构造参数
		for (OrderInfoVo order : orders) {
			//子订单
			if(!order.getOrderSn().equals(order.getMainOrderSn())) {
				childOrders.add(order);
			}
			//所有订单sn
			orderIds.add(order.getOrderId());
			//add配送信息
			if(order.getDeliverType() == OrderConstant.DELIVER_TYPE_COURIER && !StringUtils.isEmpty(order.getShippingNo())) {
				sOrderSns.add(order.getOrderSn());
			}
			//add退货款信息
			if(order.getRefundStatus() != (byte)0 ) {
				rOrderSns.add(order.getOrderSn());
			}
		}
		OrderInfoVo mainOrder = orders.get(0);
		//查询商品行
		Map<Integer, List<OrderGoodsVo>> goods = getGoodsByOrderId(orderIds.toArray(new Integer[orderIds.size()]));
		//查询配送信息
		Map<String, List<ShippingInfoVo>> shippingByOrderSn = getShippingByOrderSn(sOrderSns.toArray(new String[sOrderSns.size()]));
		//查询退货款信息
		Map<String, List<OrderConciseRefundInfoVo>> refundByOrderSn = getRefundByOrderSn(rOrderSns.toArray(new String[rOrderSns.size()]));
		//构造order
		for (OrderInfoVo vo : orders) {
			vo.setShippingList(shippingByOrderSn.get(vo.getOrderSn()));
			vo.setRefundList(refundByOrderSn.get(vo.getOrderSn()));
			vo.setGoods(goods.get(vo.getOrderId()));
		}
		//设置
		mainOrder.setChildOrders(childOrders);
		if(size > 1) {
			//过滤主订单下被拆出的goods
			filterMainOrderGoods(mainOrder, goods.get(mainOrder.getOrderId()));
		}
		return mainOrder;
	}
	
	/**
	 * 通过订单[]查询其下商品
	 * @param goodsListToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<Integer, List<OrderGoodsVo>> getGoodsByOrderId(Integer... arrayToSearch) {
		if(arrayToSearch.length == 0) {
			return new HashMap<Integer, List<OrderGoodsVo>>(0);
		}
		Map<Integer, List<OrderGoodsVo>> goods = db().select(ORDER_GOODS.ORDER_ID,ORDER_GOODS.ORDER_SN,ORDER_GOODS.GOODS_ID,ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_SN,ORDER_GOODS.GOODS_NUMBER,ORDER_GOODS.GOODS_PRICE,ORDER_GOODS.GOODS_ATTR,ORDER_GOODS.PRODUCT_ID).from(ORDER_GOODS)
				.where(ORDER_GOODS.ORDER_ID.in(arrayToSearch))
				.orderBy(ORDER_GOODS.ORDER_ID.desc())
				.fetchGroups(ORDER_GOODS.ORDER_ID,OrderGoodsVo.class);
		return goods;	
	}
	
	/**
	 * 通过订单[]查询其下配送信息
	 * @param arrayToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<String,List<ShippingInfoVo>> getShippingByOrderSn(String... arrayToSearch) {
		if(arrayToSearch.length == 0) {
			return new HashMap<String, List<ShippingInfoVo>>(0);
		}
		Map<String, List<ShippingInfoVo>> goods = db().select(PART_ORDER_GOODS_SHIP.asterisk())
				.from(PART_ORDER_GOODS_SHIP)
				.where(PART_ORDER_GOODS_SHIP.ORDER_SN.in(arrayToSearch))
				.orderBy(PART_ORDER_GOODS_SHIP.REC_ID.desc())
				.fetchGroups(PART_ORDER_GOODS_SHIP.ORDER_SN,ShippingInfoVo.class);
		//聚合List<ShippingInfoVo>>相同物流单号的对象合并它在数组第一次出现的位置
		for(Entry<String, List<ShippingInfoVo>> temp : goods.entrySet()) {
			List<ShippingInfoVo> voList = temp.getValue();
			Iterator<ShippingInfoVo> iterator = voList.iterator();
			while(iterator.hasNext()) {
				ShippingInfoVo next = iterator.next();
				//如果物流单号相同则聚合
				int indexOf = voList.indexOf(next);
				//该物流单号不是第一次出现的位置，将该记录信息合并到第一次出现的位置并删除
				if(voList.get(indexOf).getGoods() != null) {
					voList.get(indexOf).getGoods().add(new Goods(next.getRecId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber()));
					iterator.remove();		
				}else {
					//第一次出现的位置，初始化goodlist,并将该记录的商品行信息转移到Goods上
					ArrayList<Goods> firstGoodsList = new ArrayList<Goods>();
					firstGoodsList.add(new Goods(next.getRecId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber()));
					voList.get(indexOf).setGoods(firstGoodsList);
				}		
			}
		}
		return goods;	
	}
	
	/**
	 * 通过订单[]查询其下退货
	 * @param arrayToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<String, List<OrderConciseRefundInfoVo>> getRefundByOrderSn(String... arrayToSearch) {
		if(arrayToSearch.length == 0) {
			return new HashMap<String, List<OrderConciseRefundInfoVo>>(0);
		}
		Map<String, List<OrderConciseRefundInfoVo>> goods = db().select(RETURN_ORDER_GOODS.asterisk()).from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.in(arrayToSearch))
				.orderBy(RETURN_ORDER_GOODS.ID)
				.fetchGroups(RETURN_ORDER_GOODS.ORDER_SN,OrderConciseRefundInfoVo.class);
		return goods;	
	}
	
	/**
	 * 过滤子订单数量>0时,过滤主订单下已被拆除的商品行（通过减小数量为0则不展示）
	 * @param OrderListInfoVo主订单 goods主订单商品行
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
	
	/**
	 * 退货、款订单
	 * @return
	 */
	public PageResult<OrderReturnListVo> getReturnPageList(OrderPageListQueryParam param) {
		SelectJoinStep<Record> select = db().select().from(RETURN_ORDER);
		buildOptionsReturn(select,param);	
		PageResult<OrderReturnListVo> result = getPageResult(select,param.getCurrentPage(),param.getPageRows(),OrderReturnListVo.class);
		List<String> collect;
		List<OrderReturnListVo> dataList = result.dataList;
		if(dataList != null && dataList.size() > 0 ) {
			collect = dataList.stream().map(OrderReturnListVo::getOrderSn).collect(Collectors.toList());
		}else {
			return result;
		}
		Map<String, List<OrderReturnGoodsVo>> goods = db().select(RETURN_ORDER_GOODS.asterisk()).from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.in(collect))
				.fetchGroups(RETURN_ORDER_GOODS.ORDER_SN,OrderReturnGoodsVo.class);
		for (OrderReturnListVo order : dataList) {
			order.setGoods(goods.get(order.getOrderSn()));
		}
		return result;
	}
	
	/**
	 * 构造退货、款查询条件
	 *
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<?> buildOptionsReturn(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		// 自增id排序
		select.orderBy(RETURN_ORDER.RET_ID);

		if (!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(RETURN_ORDER.ORDER_SN.eq(param.getOrderSn()));
		}
		if (!StringUtils.isEmpty(param.getReturnOrderSn())) {
			select.where(RETURN_ORDER.RETURN_ORDER_SN.eq(param.getReturnOrderSn()));
		}
		if (param.getRefundStatus() != null && param.getRefundStatus().length != 0) {
			select.where(RETURN_ORDER.REFUND_STATUS.in(param.getRefundStatus()));
		}
		if (param.getReturnType() != null && param.getReturnType().length != 0) {
			select.where(RETURN_ORDER.RETURN_TYPE.in(param.getReturnType()));
		}
		if (param.getReturnStart() != null) {
			select.where(RETURN_ORDER.APPLY_TIME.ge(param.getReturnStart()));
		}
		if (param.getReturnEnd() != null) {
			select.where(RETURN_ORDER.APPLY_TIME.le(param.getReturnStart()));
		}
		return select;
	}
	 
	 /**
	  * 买单订单查询
	  * @param param
	  * @return
	  */
	 public PageResult<StoreOrderListInfoVo> getPageList(StoreOrderPageListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(STORE_ORDER.ORDER_ID,STORE_ORDER.ORDER_SN,STORE_ORDER.ORDER_STATUS,STORE_ORDER.STORE_ID,STORE_ORDER.PAY_TIME,STORE_ORDER.MONEY_PAID,STORE_ORDER.PAY_CODE,STORE_ORDER.PAY_NAME,USER.USERNAME)
				.from(STORE_ORDER).leftJoin(USER)
				.on(USER.USER_ID.eq(STORE_ORDER.USER_ID));
		buildOptionsStore(select,param);
		PageResult<StoreOrderListInfoVo> result = getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows(),StoreOrderListInfoVo.class);
		return result;
	 }
	 
	 /**
	  * 构造买单订单查询条件
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptionsStore(SelectWhereStep<?> select, StoreOrderPageListQueryParam param) {
		//自增id排序
		select.orderBy(STORE_ORDER.ORDER_ID);
		
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(STORE_ORDER.ORDER_SN.eq(param.getOrderSn()));
		}
		if(param.getUserName() != null) {
			select.where(USER.USERNAME.like(likeValue(param.getUserName())));
		}
		if(param.getPayTimeStart() != null ) {
			select.where(STORE_ORDER.PAY_TIME.ge(param.getPayTimeStart()));
		}
		if(param.getPayTimeEnd() != null ) {
			select.where(STORE_ORDER.PAY_TIME.le(param.getPayTimeEnd()));
		}
		if(param.getStoreId() != null ) {
			select.where(STORE_ORDER.STORE_ID.eq(param.getStoreId()));
		}
		if(param.getOrderStatus()!= null && param.getOrderStatus().length != 0) {
			select.where(STORE_ORDER.ORDER_STATUS.in(param.getOrderStatus()));
		}
		return select; 
	 }

	/**
	 * 分裂营销活动的活动数据分析的订单部分数据
	 * @param param
	 * @return
	 */
	 public Map<Date,Integer> getMarketOrderAnalysis(MarketAnalysisParam param){
		 Map<Date,Integer> map =  db().select(date(ORDER_INFO.CREATE_TIME).as("date"),count().as("number")).from(ORDER_INFO).
				 where(ORDER_INFO.ACTIVITY_ID.eq(param.getActId())).
				 and(ORDER_INFO.CREATE_TIME.between(param.getStartTime(),param.getEndTime())).
				 and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED)).
				 and(sql("FIND_IN_SET("+OrderConstant.GOODS_TYPE_BARGAIN+", "+ORDER_INFO.getName() +"."+ ORDER_INFO.GOODS_TYPE.getName()+")")).
				 groupBy(date(ORDER_INFO.CREATE_TIME)).fetch().intoMap(date(ORDER_INFO.CREATE_TIME).as("date"),count().as("number"));
		 return map;
	 }


	/**
	 *
     *
	 * @param goodType
	 * @param activityId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	 public List<ActiveDiscountMoney> getActiveDiscountMoney(Integer goodType, Integer activityId, Timestamp startTime,Timestamp  endTime){
         List<ActiveDiscountMoney> record = db().select(
		         DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME),
                 DSL.sum(ORDER_GOODS.MARKET_PRICE),
                 DSL.sum(ORDER_GOODS.GOODS_PRICE),
                 DSL.sum(ORDER_GOODS.DISCOUNTED_TOTAL_PRICE))
				 .from(ORDER_INFO)
				 .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
				 .where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				 .and(DslPlus.findInSet(goodType.toString(), ORDER_INFO.GOODS_TYPE))
				 .and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				 .and(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
				 .groupBy(DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME))
				 .fetchInto(ActiveDiscountMoney.class);
		 return record;
	 }

	/**
	 *
	 *  活动新用户订单
     *
	 *  @param goodType
	 * @param activityId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ActiveOrderList getActiveOrderList(Integer goodType, Integer activityId, Timestamp startTime, Timestamp  endTime) {
		//查询该活动下过单的用户——所有用户
	 	List<Integer> userIdList = db().select(ORDER_INFO.USER_ID)
				.from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				.and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
				.and(DslPlus.findInSet(goodType.toString(), ORDER_INFO.GOODS_TYPE))
				.groupBy(ORDER_INFO.USER_ID)
				.fetch(ORDER_INFO.USER_ID);
	 	//查新用户活动前下过订单——老用户
		List<Integer> oldUserIdList= db().select(ORDER_INFO.USER_ID)
				.from(ORDER_INFO)
				.where(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.lt(startTime))
				.and(ORDER_INFO.USER_ID.in(userIdList))
				.orderBy(ORDER_INFO.USER_ID).fetch(ORDER_INFO.USER_ID);
		// 老用户订单数据
		List<OrderActivityUserNum> oldList= db().select(DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME).as("date"),count(ORDER_INFO.CREATE_TIME))
				.from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				.and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
				.and(DslPlus.findInSet(goodType.toString(), ORDER_INFO.GOODS_TYPE))
				.and(ORDER_INFO.USER_ID.in(oldUserIdList))
				.groupBy(ORDER_INFO.CREATE_TIME)
				.fetchInto(OrderActivityUserNum.class);
		//新用户订单数据
		userIdList.removeAll(oldUserIdList);
		List<OrderActivityUserNum> newList = db().select(DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME).as("date"), count(ORDER_INFO.CREATE_TIME))
				.from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				.and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
				.and(DslPlus.findInSet(goodType.toString(), ORDER_INFO.GOODS_TYPE))
				.and(ORDER_INFO.USER_ID.in(userIdList))
				.groupBy(DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME))
				.fetchInto(OrderActivityUserNum.class);
		ActiveOrderList activeOrderList=new ActiveOrderList();
		activeOrderList.setNewUserNum(newList);
		activeOrderList.setOldUserNum(oldList);
		return activeOrderList;
	}


}
