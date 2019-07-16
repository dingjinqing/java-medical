package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ReturnOrder.RETURN_ORDER;
import static com.vpu.mp.db.shop.tables.PinGroupList.PIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserTag.USER_TAG;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.types.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.OrderReturnListVo;

/**
 * 
 * @author 常乐 2019年6月27日;王帅 2019/7/10
 */
public class OrderService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 订单查询
	 * @param OrderPageListQueryParam
	 * @return PageResult
	 */
	public PageResult<? extends OrderListInfoVo> getPageList(OrderPageListQueryParam param) {
		logger.info("订单综合查询开始");
		//退款退货订单查询(其主表不同，所以走分支逻辑)
		if(param.searchType != null && param.searchType == 1) {
			return getReturnPageList(param);
		}
		PageResult<OrderListInfoVo> pageResult = new PageResult<>();
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
		Map<String, List<OrderListInfoVo>> allOrder = db().selectDistinct(ORDER_INFO.ORDER_ID.as("Id") , ORDER_INFO.asterisk())
				.from(ORDER_INFO).where(ORDER_INFO.MAIN_ORDER_SN.in(mainOrderResult.getDataList()))
				.orderBy(ORDER_INFO.ORDER_ID)
				.fetchGroups(ORDER_INFO.MAIN_ORDER_SN,OrderListInfoVo.class);
		//构造展示商品的订单:MainOrderCount.count=1的可能为正常订单或处于未子订单未被拆分,>1的为已经拆分
		Map<Integer,OrderListInfoVo> goodsList = new HashMap<Integer,OrderListInfoVo>();
		//主订单
		ArrayList<OrderListInfoVo> mainOrderList = new ArrayList<OrderListInfoVo>(mainOrderResult.getDataList().size());
		//现子订单数>0的主订单
		ArrayList<Integer> orderCountMoreZero = new ArrayList<Integer>();
		for (String moc : mainOrderResult.getDataList()) {
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

	/**
	  * 构造综合查询条件
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
			select.innerJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
			select.where(USER.USERNAME.like(likeValue(param.userName)));
		}
		if(!StringUtils.isEmpty(param.source)){
			select.where(ORDER_INFO.SOURCE.eq(param.source));
		}
		//会员标签tag
		if(param.tagIds != null){
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
		if(param.pinStatus != null){
			select.innerJoin(PIN_GROUP_LIST).on(ORDER_INFO.ORDER_SN.eq(PIN_GROUP_LIST.ORDER_SN));
			select.where(PIN_GROUP_LIST.STATUS.in(param.pinStatus));
		}
		return select;
	 }
	/**
	 * 订单详情
	 * @param mainOrderSn
	 * @return
	 */
	public OrderInfoVo get(String mainOrderSn) {
		List<OrderInfoVo> result = db().select(ORDER_INFO.asterisk())
			.from(ORDER_INFO)
			.where(ORDER_INFO.MAIN_ORDER_SN.eq(mainOrderSn))
			.orderBy(ORDER_INFO.ORDER_ID)
			.fetchInto(OrderInfoVo.class);
		int size = result.size();
		OrderInfoVo order = null;		
		if(size == 1) {
			order = result.get(0);
			Map<Integer, List<OrderGoodsVo>> goods = getGoodsByOrderId(new Integer[] {order.getOrderId()});
			order.setGoods(goods.get(order.getOrderId()));
		}else if(size > 1){
			//拆单下过滤主订单
			List<OrderInfoVo> childOrders = result.stream().filter(o -> !mainOrderSn.equals(o.getOrderSn())).collect(Collectors.toList());
			//根据排序规则[0]为主订单
			order = result.get(0);
			order.setChildOrders(childOrders);
		}
		return order;
	}
	
	/**
	 * 通过订单[]查询其下商品
	 * @param goodsListToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<Integer, List<OrderGoodsVo>> getGoodsByOrderId(Integer[] goodsListToSearch) {
		Map<Integer, List<OrderGoodsVo>> goods = db().select(ORDER_GOODS.ORDER_ID,ORDER_GOODS.ORDER_SN,ORDER_GOODS.GOODS_ID,ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_SN,ORDER_GOODS.GOODS_NUMBER,ORDER_GOODS.GOODS_PRICE,ORDER_GOODS.GOODS_ATTR,ORDER_GOODS.PRODUCT_ID).from(ORDER_GOODS)
				.where(ORDER_GOODS.ORDER_ID.in(goodsListToSearch))
				.orderBy(ORDER_GOODS.ORDER_ID.desc())
				.fetchGroups(ORDER_GOODS.ORDER_ID,OrderGoodsVo.class);
		return goods;
		
	}
	
	/**
	 * 过滤子订单数量>0时,过滤主订单下已被拆除的商品行（通过减小数量为0则不展示）
	 * @param goodsListToSearch
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
					orderOoods.setGoodsNumber(UShort.valueOf(finalCount));
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
		PageResult<OrderReturnListVo> result = getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows(),OrderReturnListVo.class);
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
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptionsReturn(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		//自增id排序
		select.orderBy(RETURN_ORDER.RET_ID);
		
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(RETURN_ORDER.ORDER_SN.eq(param.getOrderSn()));
		}
		if(!StringUtils.isEmpty(param.getReturnOrderSn())) {
			select.where(RETURN_ORDER.RETURN_ORDER_SN.eq(param.getReturnOrderSn()));
		}
		if(param.getRefundStatus() != null ) {
			select.where(RETURN_ORDER.REFUND_STATUS.in(param.getRefundStatus()));
		}
		if(param.getReturnType() != null ) {
			select.where(RETURN_ORDER.RETURN_TYPE.in(param.getReturnType()));
		}
		if(param.getReturnStart() != null ) {
			select.where(RETURN_ORDER.APPLY_TIME.ge(param.getReturnStart()));
		}
		if(param.getReturnEnd() != null ) {
			select.where(RETURN_ORDER.APPLY_TIME.le(param.getReturnStart()));
		}
		return select; 
	 }
}
