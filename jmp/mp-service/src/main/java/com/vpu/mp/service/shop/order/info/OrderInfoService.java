package com.vpu.mp.service.shop.order.info;

import static com.vpu.mp.db.shop.Tables.GIVE_GIFT_ACTIVITY;
import static com.vpu.mp.db.shop.Tables.GIVE_GIFT_CART;
import static com.vpu.mp.db.shop.tables.GroupBuyList.GROUP_BUY_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserTag.USER_TAG;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.DELETE_NO;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_REFUND_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_RETURN_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.PAY_CODE_BALANCE_PAY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.PAY_CODE_WX_PAY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_DEFAULT_STATUS;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_STATUS_FINISH;
import static com.vpu.mp.service.pojo.shop.order.store.StoreOrderConstant.STORE_ORDER_PAID;
import static com.vpu.mp.service.shop.store.service.ServiceOrderService.ORDER_STATUS_FINISHED;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sql;
import static org.jooq.impl.DSL.sum;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.givegift.record.GiveGiftRecordListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Data;

/**
 * Table:order_info
 * 
 * @author 王帅
 * @param <V>
 *
 */
@Service
public class OrderInfoService extends ShopBaseService {

	public final OrderInfo TABLE = ORDER_INFO;

	/**
	 * 	支付种类（细分）PAY_SUBDIVISION
	 */
	/**积分抵扣金额*/
	public final String PS_SCORE_DISCOUNT = TABLE.SCORE_DISCOUNT.getName();
	/**用户消费余额*/
	public final String PS_USE_ACCOUNT = TABLE.USE_ACCOUNT.getName();
	/**会员卡消费金额*/
	public final String PS_MEMBER_CARD_BALANCE = TABLE.MEMBER_CARD_BALANCE.getName();
	/**TODO 数据库没字段子单金额*/
	public final String PS_MONEY_PAID= TABLE.MONEY_PAID.getName();
	/**补款金额*/
	public final String PS_BK_ORDER_MONEY= TABLE.BK_ORDER_MONEY.getName();
	
	public final String[] PAY_SUBDIVISION = {PS_BK_ORDER_MONEY,PS_MEMBER_CARD_BALANCE,PS_USE_ACCOUNT,PS_SCORE_DISCOUNT,PS_MONEY_PAID};
	
	/**
	 * @param <T> <? extends OrderListVo>
	 * @param orderId 订单id
	 * @param clz 
	 * @return oneOrder
	 */
	public <T> T getByOrderId(Integer orderId , Class<T> clz){
		T order = db().select(TABLE.asterisk()).from(TABLE).where(TABLE.ORDER_ID.eq(orderId)).fetchOneInto(clz);
		return order;
	}
	
	public OrderInfoRecord getOrderByOrderSn(String orderSn) {
		return db().fetchAny(TABLE,TABLE.ORDER_SN.eq(orderSn));
	}
	
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
	public static class MainOrderResult {
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
	
	/**
	 * 	得到实际支付金额（判读是否包含运费）
	 * @param order
	 * @param isIncludeShipingFee 是否包含运费
	 * @return
	 */
	public BigDecimal getOrderFinalAmount(OrderListInfoVo order , boolean isIncludeShipingFee) {
		return order.getBkOrderMoney().add(order.getMoneyPaid())
		.add(order.getScoreDiscount())
		.add(order.getMemberCardBalance())
		.add(order.getUseAccount())
		//TODO 少这个字段
		//.add(order.getSubGoodsPrice())
		.subtract(isIncludeShipingFee ? BigDecimal.ZERO : order.getShippingFee());
	}
	
	/**
	 * 	当前订单为子订单需要替换支付信息与用户信息(子订单无补款信息,不需复制)
	 * @param currentOrder
	 */
	public void replaceOrderInfo(OrderListInfoVo currentOrder) {
		if(isSubOrder(currentOrder)) {
			OrderListInfoVo mainOrder = db().select(TABLE.asterisk()).from(TABLE).where(TABLE.ORDER_SN.eq(currentOrder.getMainOrderSn())).fetchOneInto(OrderListInfoVo.class);
			currentOrder.setMemberCardBalance(mainOrder.getMemberCardBalance());
			currentOrder.setUseAccount(mainOrder.getUseAccount());
			currentOrder.setScoreDiscount(mainOrder.getScoreDiscount());
			currentOrder.setMoneyPaid(mainOrder.getMoneyPaid());
			currentOrder.setUserId(mainOrder.getUserId());
			currentOrder.setCardNo(mainOrder.getCardNo());
			currentOrder.setMemberCardId(mainOrder.getMemberCardId());
		}
	}
	/**
	 * 	是否为拆单逻辑下的主订单
	 * @param currentOrder
	 * @return
	 */
	public Boolean isMainOrder(OrderListInfoVo currentOrder) {
		if(!StringUtils.isBlank(currentOrder.getMainOrderSn()) && currentOrder.getOrderSn().equals(currentOrder.getMainOrderSn())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 	是否为拆单逻辑下的子订单
	 * @param currentOrder
	 * @return
	 */
	public Boolean isSubOrder(OrderListInfoVo currentOrder) {
		if(!StringUtils.isBlank(currentOrder.getMainOrderSn()) && !currentOrder.getOrderSn().equals(currentOrder.getMainOrderSn())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	/**
	 * 	通过主订单号获取orders
	 * @param MainOrderSn
	 * @param isMain
	 * @return List<OrderListInfoVo>
	 */
	public List<OrderListInfoVo> getChildOrders(OrderListInfoVo currentOrder , Boolean isMain){
		//是否为主订单
		if(isMain) {
			return db().select(TABLE.asterisk()).from(TABLE).
					where(TABLE.MAIN_ORDER_SN.eq(currentOrder.getMainOrderSn()).and(TABLE.MAIN_ORDER_SN.notEqual(TABLE.ORDER_SN))).fetchInto(OrderListInfoVo.class);
		}
		return new ArrayList<OrderListInfoVo>(0);
	}
	
	/**
	 * 	将order支付信息构造成map方便计算
	 * @param currentOrder
	 * @return Map<String, BigDecimal>
	 */
	public Map<String , BigDecimal> getPayInfoMap(OrderListInfoVo currentOrder){
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>(PAY_SUBDIVISION.length);
		map.put(PS_BK_ORDER_MONEY,currentOrder.getBkOrderMoney());
		map.put(PS_MEMBER_CARD_BALANCE,currentOrder.getMemberCardBalance());
		map.put(PS_USE_ACCOUNT,currentOrder.getUseAccount());
		map.put(PS_SCORE_DISCOUNT,currentOrder.getScoreDiscount());
		map.put(PS_MONEY_PAID,currentOrder.getMoneyPaid());
		return map;
	}
	/**
	 * 
	 * @param currentOrder
	 * @param amount 当前订单最终支付金额(包含运费)
	 * @param map 退款数据汇总
	 * @return Map<String , BigDecimal> 
	 */
	public Map<String , BigDecimal> getCanReturn(OrderListInfoVo currentOrder , BigDecimal amount , LinkedHashMap<String , BigDecimal> map) {
		//将order支付信息构造成map方便计算
		Map<String, BigDecimal> payInfoMap = getPayInfoMap(currentOrder);
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
			//某key已支付-已退=可退
			BigDecimal currentKeyCanReturn = payInfoMap.get(entry.getKey()).subtract(entry.getValue());
			//可退金额<=0
			if(BigDecimalUtil.compareTo(currentKeyCanReturn, BigDecimal.ZERO) < 1) {
				entry.setValue(BigDecimal.ZERO);
				continue;
			}
			//设置当前key的可退金额（增加双重校验，实际上实际退款时已经判断，不会出现amount<currentKeyCanReturn）
			entry.setValue(BigDecimalUtil.compareTo(amount, currentKeyCanReturn) > 0 ? entry.getValue() : amount);
			//设置下次循环的amount
			amount = amount.subtract(BigDecimalUtil.compareTo(amount, currentKeyCanReturn) > 0 ? amount.subtract(entry.getValue()) : BigDecimal.ZERO);
		}
		return map;
	}
	
	/**
	 *	 退*时更新订单信息
	 * @param returnOrder
	 * @param order ==null时不参与returnOrder==REFUND_STATUS_FINISH分支
	 * @param canReturnGoodsNumber 是否存在可退商品数量；==null时不参与returnOrder==REFUND_STATUS_FINISH分支
	 */
	public void updateOrderInfoInReturn(ReturnOrderRecord returnOrder , OrderInfoVo order , Boolean canReturnGoodsNumber) {
		UpdateSetMoreStep<OrderInfoRecord> set = db().update(TABLE).set(TABLE.REFUND_STATUS, returnOrder.getRefundStatus());
		//退款退货订单处与 1买家仅退款 2买家提交物流 3仅退运费 4手动退款
		if(OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING == returnOrder.getRefundStatus()) {
			if(OrderConstant.RT_ONLY_MONEY == returnOrder.getReturnType()) {
				set.set(TABLE.REFUND_TIME, DateUtil.getSqlTimestamp());
			}else {
				set.set(TABLE.RETURN_TIME, DateUtil.getSqlTimestamp());
			}
		
		}else if(OrderConstant.REFUND_STATUS_FINISH == returnOrder.getRefundStatus()) {
			if(canReturnGoodsNumber) {
				//完成状态且存在可退数量跳出
				return;
			}
			//退款退货订单完成时更新orderinfo订单信息
			if(OrderConstant.ORDER_WAIT_DELIVERY == order.getOrderStatus()) {
				set.set(TABLE.ORDER_STATUS,OrderConstant.ORDER_REFUND_FINISHED);
				set.set(TABLE.REFUND_FINISH_TIME, DateUtil.getSqlTimestamp());
			}else {
				set.set(TABLE.ORDER_STATUS,OrderConstant.ORDER_RETURN_FINISHED);
				set.set(TABLE.RETURN_FINISH_TIME, DateUtil.getSqlTimestamp());
			}
			//TODO 返利更新逻辑未知
			if(OrderConstant.FANLI_TYPE_DISTRIBUTION_ORDER == order.getFanliType()) {
				set.set(TABLE.SETTLEMENT_FLAG,OrderConstant.SETTLEMENT_NOT);
				set.set(TABLE.FANLI_MONEY, BigDecimal.ZERO);
			}
		}
		set.where(TABLE.ORDER_SN.eq(returnOrder.getOrderSn())).execute();
	}
	
	public void setOrderstatus(String orderSn , byte orderStatus){
		OrderInfoRecord order = getOrderByOrderSn(orderSn);
		switch (orderStatus) {
		case OrderConstant.ORDER_SHIPPED:
			order.setOrderStatus(OrderConstant.ORDER_SHIPPED);
			order.setShippingTime(DateUtil.getSqlTimestamp());
			break;
		default:
			break;
		}
		order.update();
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
				.orderBy(ORDER_INFO.CREATE_TIME.asc())
				.fetch(ORDER_INFO.USER_ID);
		//查新用户活动前下过订单——老用户
		List<Integer> oldUserIdList= db().select(ORDER_INFO.USER_ID)
				.from(ORDER_INFO)
				.where(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.lt(startTime))
				.and(ORDER_INFO.USER_ID.in(userIdList))
				.orderBy(ORDER_INFO.CREATE_TIME.asc())
				.fetch(ORDER_INFO.USER_ID);
		// 老用户订单数据
		List<OrderActivityUserNum> oldList= db().select(DslPlus.dateFormatDay(ORDER_INFO.CREATE_TIME).as("date"),count(ORDER_INFO.CREATE_TIME))
				.from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				.and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
				.and(DslPlus.findInSet(goodType.toString(), ORDER_INFO.GOODS_TYPE))
				.and(ORDER_INFO.USER_ID.in(oldUserIdList))
				.groupBy(ORDER_INFO.CREATE_TIME)
				.orderBy(ORDER_INFO.CREATE_TIME.asc())
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
				.orderBy(ORDER_INFO.CREATE_TIME.asc())
				.fetchInto(OrderActivityUserNum.class);
		ActiveOrderList activeOrderList=new ActiveOrderList();
		activeOrderList.setNewUserNum(newList);
		activeOrderList.setOldUserNum(oldList);
		return activeOrderList;
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
	 *	活动实付总金额 活动优惠总金额
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
				.orderBy(ORDER_INFO.CREATE_TIME)
				.fetchInto(ActiveDiscountMoney.class);
		return record;
	}
	
	/**
	 * 查找一口价活动 已购商品数量
	 * @param activityId
	 * @return
	 */
	public Integer getPackageSaleGoodsNum(Integer activityId) {
		Integer goodsNum = db().select(DSL.sum(ORDER_INFO.GOODS_AMOUNT)).from(ORDER_INFO)
			.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
			.and(DSL.sql("FIND_IN_SET("+OrderConstant.GOODS_TYPE_PACKAGE_SALE+", "+ORDER_INFO.getName() +"."+ ORDER_INFO.GOODS_TYPE.getName()+")"))
			.fetchOneInto(Integer.class);
		return goodsNum;
	}
	/**
	 * 查找一口价活动下单用户数量
	 * @param activityId
	 * @return
	 */
	public Integer getPackageSaleUserNum(Integer activityId) {
		Integer userNum = db().select(DSL.countDistinct(ORDER_INFO.USER_ID)).from(ORDER_INFO)
			.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
			.and(DSL.sql("FIND_IN_SET("+OrderConstant.GOODS_TYPE_PACKAGE_SALE+", "+ORDER_INFO.getName() +"."+ ORDER_INFO.GOODS_TYPE.getName()+")"))
			.fetchOneInto(Integer.class);
		return userNum;
	}
	/**
	 * 查找一口价活动订单数量
	 * @param activityId
	 * @return
	 */
	public Integer getPackageSaleOrderNum(Integer activityId) {
		Integer goodsNum = db().select(DSL.count()).from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(activityId))
				.and(DSL.sql("FIND_IN_SET("+OrderConstant.GOODS_TYPE_PACKAGE_SALE+", "+ORDER_INFO.getName() +"."+ ORDER_INFO.GOODS_TYPE.getName()+")"))
				.fetchOneInto(Integer.class);
			return goodsNum;
	}

	
	/**
	 * 根据用户id获取累计消费金额
	 */
	public BigDecimal getAllConsumpAmount(Integer userId) {
		BigDecimal totalConsumpAmount = new BigDecimal(0);
		logger().info("计算会员 "+userId+" 的累积消费金额");
		
		/** 会员卡消费金额 */
		BigDecimal memberCardBalance = db().select(sum(ORDER_INFO.MEMBER_CARD_BALANCE))
										   .from(ORDER_INFO)
										   .where(ORDER_INFO.USER_ID.eq(userId))
										   .and(ORDER_INFO.ORDER_STATUS.eq(ORDER_FINISHED))
										   .fetchOne()
										   .into(BigDecimal.class);
		logger().info("会员卡消费金额: " + memberCardBalance);
		if(memberCardBalance != null) {
			totalConsumpAmount = totalConsumpAmount.add(memberCardBalance);
		}
		
		/** 余额，微信订单应付金额*/
		BigDecimal moneyPaid = db().select(sum(ORDER_INFO.MONEY_PAID))
									.from(ORDER_INFO)
									.where(ORDER_INFO.USER_ID.eq(userId))
									.and(ORDER_INFO.ORDER_STATUS.eq(ORDER_FINISHED))
									.and(ORDER_INFO.PAY_CODE.in(PAY_CODE_BALANCE_PAY,PAY_CODE_WX_PAY))
									.fetchOne()
									.into(BigDecimal.class);
		logger().info("余额，微信订单应付金额: " + moneyPaid);
		if(moneyPaid != null) {
			totalConsumpAmount = totalConsumpAmount.add(moneyPaid);
		}
		
		
		/** 用户消费余额 */
		BigDecimal useAccount = db().select(sum(ORDER_INFO.USE_ACCOUNT))
				.from(ORDER_INFO)
				.where(ORDER_INFO.USER_ID.eq(userId))
				.and(ORDER_INFO.ORDER_STATUS.eq(ORDER_FINISHED))
				.fetchOne()
				.into(BigDecimal.class);
		logger().info("用户消费余额 "+useAccount);
		if(useAccount != null) {
			totalConsumpAmount = totalConsumpAmount.add(useAccount);
		}
		
		
		/** 门店-会员卡消费金额  */
		BigDecimal storeMemberCardBalance = db().select(sum(STORE_ORDER.MEMBER_CARD_BALANCE))
												.from(STORE_ORDER)
												.where(STORE_ORDER.USER_ID.eq(userId))
												.and(STORE_ORDER.ORDER_STATUS.eq(STORE_ORDER_PAID))
												.fetchOne()
												.into(BigDecimal.class);
		logger().info("门店-会员卡消费金额"+storeMemberCardBalance);
		if(storeMemberCardBalance != null) {
			totalConsumpAmount = totalConsumpAmount.add(storeMemberCardBalance);
		}
		
		/** 门店-订单应付金额  */ 
		BigDecimal storeMoneyPaid = db().select(sum(STORE_ORDER.MONEY_PAID))
			.from(STORE_ORDER)
			.where(STORE_ORDER.USER_ID.eq(userId))
			.and(STORE_ORDER.ORDER_STATUS.eq(STORE_ORDER_PAID))
			.fetchOne()
			.into(BigDecimal.class);
		
		logger().info("门店-订单应付金额"+storeMoneyPaid);
		if(storeMoneyPaid != null) {
			totalConsumpAmount = totalConsumpAmount.add(storeMoneyPaid);
		}
		
		/** 门店-用户消费余额*/
		BigDecimal storeUserAccount = db().select(sum(STORE_ORDER.USE_ACCOUNT))
											.from(STORE_ORDER)
											.where(STORE_ORDER.USER_ID.eq(userId))
											.and(STORE_ORDER.ORDER_STATUS.eq(STORE_ORDER_PAID))
											.fetchOne()
											.into(BigDecimal.class);
		logger().info("门店-用户消费余额"+storeUserAccount);
		if(storeUserAccount != null) {
			totalConsumpAmount = totalConsumpAmount.add(storeUserAccount);
		}
		
		/** 服务订单表 订单应付金额 */
		BigDecimal serviceMoneyPaid = db().select(sum(SERVICE_ORDER.MONEY_PAID))
									.from(SERVICE_ORDER)
									.where(SERVICE_ORDER.USER_ID.eq(userId))
									.and(SERVICE_ORDER.ORDER_STATUS.eq(ORDER_STATUS_FINISHED))
									.and(SERVICE_ORDER.PAY_CODE.in(PAY_CODE_BALANCE_PAY,PAY_CODE_WX_PAY))
									.fetchOne()
									.into(BigDecimal.class);
		
		logger().info("服务订单表 订单应付金额"+serviceMoneyPaid);
		if(serviceMoneyPaid != null) {
			totalConsumpAmount = totalConsumpAmount.add(serviceMoneyPaid);
		}
		
		return totalConsumpAmount;
	}
	
	/**
	 * 获取最近用户下单的订单时间
	 * @param userId
	 * @return 
	 * @return 
	 * @return 
	 */
	public Timestamp getRecentOrderInfoByUserId(Integer userId) {
		return db().select(ORDER_INFO.CREATE_TIME)
				.from(ORDER_INFO)
			.where(ORDER_INFO.USER_ID.eq(userId))
			.and(ORDER_INFO.ORDER_STATUS.in(ORDER_FINISHED,ORDER_RETURN_FINISHED,ORDER_REFUND_FINISHED))
			.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_DEFAULT_STATUS))
			.and(ORDER_INFO.DEL_FLAG.eq(DELETE_NO))
			.orderBy(ORDER_INFO.CREATE_TIME.desc())
			.fetchOne().into(Timestamp.class);
	}
	
	/**
	 * 累计下单金额
	 * @param userId
	 */
	public BigDecimal getAllOrderMoney(Integer userId) {
		BigDecimal orderMoney = db().select(sum(ORDER_INFO.ORDER_AMOUNT))
			.from(ORDER_INFO)
			.where(ORDER_INFO.USER_ID.eq(userId))
			.and(ORDER_INFO.ORDER_STATUS.in(ORDER_FINISHED,ORDER_RETURN_FINISHED,ORDER_REFUND_FINISHED))
			.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_DEFAULT_STATUS))
			.and(ORDER_INFO.DEL_FLAG.eq(DELETE_NO))
			.fetchOne()
			.into(BigDecimal.class);
		return orderMoney;
	}
	
	/**
	 * 累计消费订单数 
	 * @param userId
	 * @return 
	 */
	public Integer getAllOrderNum(Integer userId) {
		return db().select(count())
				.from(ORDER_INFO)
				.where(ORDER_INFO.USER_ID.eq(userId))
				.and(ORDER_INFO.ORDER_STATUS.in(ORDER_FINISHED,ORDER_RETURN_FINISHED,ORDER_REFUND_FINISHED))
				.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_DEFAULT_STATUS))
				.and(ORDER_INFO.DEL_FLAG.eq(DELETE_NO))
				.fetchOne()
				.into(Integer.class);
		}
	
	/**
	 * 累计退款金额
	 * @param userId
	 * @return 
	 */
	public BigDecimal getAllReturnOrderMoney(Integer userId) {
		OrderInfoRecord record = db().select(sum(ORDER_INFO.MONEY_PAID).as(ORDER_INFO.MONEY_PAID),sum(ORDER_INFO.SHIPPING_FEE).as(ORDER_INFO.SHIPPING_FEE))
			.from(ORDER_INFO)
			.where(ORDER_INFO.USER_ID.eq(userId))
			.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_STATUS_FINISH))
			.fetchOne()
			.into(OrderInfoRecord.class);
		
		BigDecimal result = record.getMoneyPaid().add(record.getShippingFee());
		return result;
	}
	
	/**
	 * 累计退款订单数
	 * @param userId
	 * @return 
	 */
	public Integer getAllReturnOrderNum(Integer userId) {
		return db().select(count())
				.from(ORDER_INFO)
				.where(ORDER_INFO.USER_ID.eq(userId))
				.and(ORDER_INFO.ORDER_STATUS.notIn(ORDER_FINISHED,ORDER_RETURN_FINISHED,ORDER_REFUND_FINISHED))
				.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_STATUS_FINISH))
				.and(ORDER_INFO.DEL_FLAG.eq(DELETE_NO))
				.fetchOne()
				.into(Integer.class);
	}




	/**
	 *  我要送礼
	 * @param activityId 活动id
	 * @return  送礼的礼物车数量
	 */
	public Integer getSendGiftOrderCt(Integer activityId){
		return db().selectCount()
				.from(TABLE)
				.leftJoin(GIVE_GIFT_CART).on(TABLE.ACTIVITY_ID.eq(GIVE_GIFT_CART.ID))
				.where(GIVE_GIFT_CART.GIVE_GIFT_ID.eq(activityId))
				.and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(TABLE.ORDER_STATUS.gt((byte) 2))
				.and(DslPlus.findInSet(OrderConstant.GOODS_TYPE_GIVE_GIFT,TABLE.GOODS_TYPE))
				.and(TABLE.ORDER_SN.eq(TABLE.MAIN_ORDER_SN))
				.fetchOne().component1();
	}

	/**
	 * 收礼明细
	 * @param param GiveGiftReceiveListParam
	 * @return SelectConditionStep<? extends Record>
	 */
	public SelectConditionStep<? extends Record> giveGiftRecordList(GiveGiftRecordListParam param) {
		SelectConditionStep<? extends Record> select = db()
				.select(TABLE.MAIN_ORDER_SN,
						TABLE.ORDER_AMOUNT,
						TABLE.CREATE_TIME,
						TABLE.PAY_TIME,
						TABLE.ORDER_STATUS,
						TABLE.ORDER_STATUS_NAME,
						GIVE_GIFT_CART.GIFT_TYPE,
						GIVE_GIFT_ACTIVITY.RECOMMEND_GOODS_ID,
						USER.USER_ID,
						USER.USERNAME,
						USER.MOBILE
				)
				.from(TABLE)
				.leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_SN.eq(TABLE.ORDER_SN))
				.leftJoin(GIVE_GIFT_CART).on(GIVE_GIFT_CART.ID.eq(TABLE.ACTIVITY_ID))
				.leftJoin(GIVE_GIFT_ACTIVITY).on(GIVE_GIFT_ACTIVITY.ID.eq(GIVE_GIFT_CART.GIVE_GIFT_ID))
				.leftJoin(USER).on(USER.USER_ID.eq(GIVE_GIFT_CART.USER_ID))
				.where(GIVE_GIFT_CART.GIVE_GIFT_ID.eq(param.getActivityId()))
				.and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(TABLE.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED))
				.and(DslPlus.findInSet(OrderConstant.GOODS_TYPE_GIVE_GIFT, TABLE.GOODS_TYPE))
				.and(TABLE.ORDER_SN.eq(TABLE.MAIN_ORDER_SN));

		if (param.getGoodsName()!=null){
			select.and(USER.USER_ID.like(prefixLikeValue(param.getGoodsName())));
		}
		if (param.getMobile()!=null){
			select.and(USER.MOBILE.like(prefixLikeValue(param.getMobile())));
		}
		if (param.getGoodsName()!=null){
			select.and(ORDER_GOODS.GOODS_NAME.like(likeValue(param.getGoodsName())));
		}
		if (param.getGoodsSn()!=null){
			select.and(ORDER_GOODS.ORDER_SN.like(likeValue(param.getGoodsSn())));
		}
		if (param.getOrderStatus()!=null&&param.getOrderStatus()>=OrderConstant.ORDER_WAIT_PAY){
			if (param.getOrderStatus()==OrderConstant.ORDER_GIVE_GIFT_FINISHED){
				select.and(TABLE.ORDER_STATUS.eq(param.getOrderStatus()));
			}else {
				select.and(TABLE.ORDER_STATUS.lt(OrderConstant.ORDER_GIVE_GIFT_FINISHED));
			}
		}
		return select;
	}

}
