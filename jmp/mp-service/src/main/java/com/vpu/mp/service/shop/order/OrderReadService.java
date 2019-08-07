package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sql;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.shop.order.OrderInfoService.MainOrderResult;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;

/**
 * 	订单模块普通查询service
 * @author 常乐 2019年6月27日;王帅 2019/7/10
 */
@Service
public class OrderReadService extends ShopBaseService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderInfoService orderInfo;
	@Autowired
	private OrderGoodsService orderGoods;
	@Autowired
	private ShipInfoService shipInfo;
	@Autowired
	private ReturnOrderService returnOrder;
	@Autowired
	private ReturnOrderGoodsService returnOrderGoods;
	@Autowired
	private StoreOrderService storeOrder;
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
		//得到主订单号
		PageResult<MainOrderResult> mainOrderResult = orderInfo.getMainOrders(param);
		pageResult.setPage(mainOrderResult.getPage());
		if(mainOrderResult.getDataList().size() < 1) {
			return pageResult;
		}
		String[] collect = mainOrderResult.getDataList().stream().map(MainOrderResult::getMainOrderSn).collect(Collectors.toList()).toArray(new String[0]);
		//查询出全部订单按照MAIN_ORDER_SN分组
		Map<String, List<OrderListInfoVo>> allOrder = orderInfo.getOrdersGorupByMainSn(collect);
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
		Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderId(goodsListToSearch).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
		Set<Entry<Integer, List<OrderGoodsVo>>> entrySet = goods.entrySet();
		for (Entry<Integer, List<OrderGoodsVo>> entry : entrySet) {
			//过滤主订单中已经拆到子订单的商品(依赖于orderinfo表自增id,当循环到主订单时其子订单下的商品都已插入到childOrders.goods里)
			if(orderCountMoreZero.contains(entry.getKey())) {
				orderInfo.filterMainOrderGoods(goodsList.get(entry.getKey()),entry.getValue());
				continue;
			}
			goodsList.get(entry.getKey()).setGoods(entry.getValue());
		}
		pageResult.setDataList(mainOrderList);
		logger.info("订单综合查询结束");
		return pageResult;
	}
	
	 
	/**
	 * 订单详情
	 * @param mainOrderSn
	 * @return
	 */
	public OrderInfoVo get(String mainOrderSn) {
		List<OrderInfoVo> orders = orderInfo.getOrdersByCondition(orderInfo.TABLE.MAIN_ORDER_SN.eq(mainOrderSn) , OrderInfoVo.class);
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
		Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderId(orderIds.toArray(new Integer[orderIds.size()])).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
		//查询配送信息
		Map<String, List<ShippingInfoVo>> shippingByOrderSn = shipInfo.getShippingByOrderSn(sOrderSns.toArray(new String[sOrderSns.size()]));
		//查询退款订单信息
		Map<String, List<OrderConciseRefundInfoVo>> refundByOrderSn = returnOrder.getRefundByOrderSn(rOrderSns.toArray(new String[rOrderSns.size()])).intoGroups(returnOrder.TABLE.ORDER_SN,OrderConciseRefundInfoVo.class);
		//查询退货款商品信息
		Map<Integer, List<OrderReturnGoodsVo>> refundGoodsByOrderSn = returnOrderGoods.getByOrderSn(rOrderSns.toArray(new String[rOrderSns.size()])).intoGroups(returnOrderGoods.TABLE.RET_ID,OrderReturnGoodsVo.class);
		//把退*商品信息插入退*订单信息中
		refundByOrderSn.forEach((k,v)->{
			v.forEach(rOrder->{
				rOrder.setOrderReturnGoodsVo(refundGoodsByOrderSn.get(rOrder.getRetId()));
			});
		});
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
			orderInfo.filterMainOrderGoods(mainOrder, goods.get(mainOrder.getOrderId()));
		}
		return mainOrder;
	}
	
	/**
	 * 退货、款订单
	 * @return
	 */
	public PageResult<OrderReturnListVo> getReturnPageList(OrderPageListQueryParam param) {
		PageResult<OrderReturnListVo> result = returnOrder.getPageList(param);
		List<String> collect;
		List<OrderReturnListVo> dataList = result.dataList;
		if(dataList != null && dataList.size() > 0 ) {
			collect = dataList.stream().map(OrderReturnListVo::getOrderSn).collect(Collectors.toList());
		}else {
			return result;
		}
		//获取订单再分组
		Map<String, List<OrderReturnGoodsVo>> goods = returnOrderGoods.getByOrderSn(collect.toArray(new String[collect.size()])).intoGroups(returnOrderGoods.TABLE.ORDER_SN,OrderReturnGoodsVo.class);;
		for (OrderReturnListVo order : dataList) {
			order.setGoods(goods.get(order.getOrderSn()));
		}
		return result;
	}

	/**
	 * 	买单订单查询
	 * 
	 * @param param
	 * @return
	 */
	 public PageResult<StoreOrderListInfoVo> getPageList(StoreOrderPageListQueryParam param) {
		PageResult<StoreOrderListInfoVo> result = storeOrder.getPageList(param);
		return result;
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
	public List<ActiveDiscountMoney> getActiveDiscountMoney(Integer goodType, Integer activityId, Timestamp startTime, Timestamp  endTime){
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
