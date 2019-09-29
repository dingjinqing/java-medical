package com.vpu.mp.service.shop.order;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.db.shop.tables.records.ReturnStatusChangeRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OperatorRecord;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.shop.config.ShopReturnConfigService;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.ReturnStatusChangeService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;
import com.vpu.mp.service.shop.user.user.UserService;

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
	@Autowired
	private UserService user;
	@Autowired
	private RefundAmountRecordService refundAmountRecord;
	@Autowired
	private ReturnStatusChangeService returnStatusChange;
	@Autowired
	private ShopReturnConfigService shopReturnConfig;
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
		//得到订单号(包含主订单和正常订单)
		PageResult<String> orderSn = orderInfo.getOrderSns(param);
		pageResult.setPage(orderSn.getPage());
		if(orderSn.getDataList().size() < 1) {
			return pageResult;
		}
		//查询出全部订单按照主订单分组，正常订单的key为orderSn
		Map<String, List<OrderListInfoVo>> allOrder = orderInfo.getOrders(orderSn.getDataList());
		//构造展示商品的订单:MainOrderCount.count=1的可能为正常订单或处于未子订单未被拆分,>1的为已经拆分
		Map<Integer,OrderListInfoVo> goodsList = new HashMap<Integer,OrderListInfoVo>();
		//主订单或正常订单
		ArrayList<OrderListInfoVo> mainOrderList = new ArrayList<OrderListInfoVo>(orderSn.getDataList().size());
		//现子订单数>0的主订单
		ArrayList<Integer> orderCountMoreZero = new ArrayList<Integer>();
		//TODO 查询订单是否为活动奖品
		List<String> prizesSns = Collections.emptyList();
		for (String moc : orderSn.getDataList()) {
			List<OrderListInfoVo> list = allOrder.get(moc);
			int size = list.size();
			OrderListInfoVo mOrder = null;
			List<OrderListInfoVo> cList = size > 1 ? new ArrayList<OrderListInfoVo>(size - 1) : null;
			for (OrderListInfoVo order : list) {
				//将所有订单id放入goodsList,在后续向订单添加商品时增加过滤主订单下与子订单重复的商品
				goodsList.put(order.getOrderId(),order);
				if(order.getOrderSn().equals(moc)) {
					//设置订单支付方式（无子单）
					orderInfo.setPayCodeList(order,prizesSns);
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
		Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderIds(goodsListToSearch).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
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
	public OrderInfoVo get(String orderSn) {
		List<OrderInfoVo> orders = orderInfo.getOrdersByCondition(orderInfo.TABLE.MAIN_ORDER_SN.eq(orderSn).or(orderInfo.TABLE.ORDER_SN.eq(orderSn)) , OrderInfoVo.class);
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
		//主订单(正常订单mainOrder=正常订单)
		OrderInfoVo mainOrder = null;
		//构造参数
		for (OrderInfoVo order : orders) {
			if(orderInfo.isMainOrder(order) || StringUtils.isBlank(order.getMainOrderSn())) {
				mainOrder = order;
			}else{
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
		//查询商品行
		Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderIds(orderIds.toArray(new Integer[orderIds.size()])).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
		//查询配送信息
		Map<String, List<ShippingInfoVo>> shippingByOrderSn = shipInfo.getShippingByOrderSn(sOrderSns.toArray(new String[sOrderSns.size()]));
		//查询退款订单信息
		Map<String, List<OrderConciseRefundInfoVo>> refundByOrderSn = returnOrder.getRefundByOrderSn(rOrderSns.toArray(new String[rOrderSns.size()])).intoGroups(returnOrder.TABLE.ORDER_SN,OrderConciseRefundInfoVo.class);
		//查询退货款商品信息
		Map<Integer, List<OrderReturnGoodsVo>> refundGoodsByOrderSn = returnOrderGoods.getByOrderSn(rOrderSns.toArray(new String[rOrderSns.size()])).intoGroups(returnOrderGoods.TABLE.RET_ID,OrderReturnGoodsVo.class);
		//TODO 查询订单是否为活动奖品
		List<String> prizesSns = Collections.emptyList();
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
		//设置订单支付方式（无子单）
		orderInfo.setPayCodeList(mainOrder,prizesSns);
		//设置核销员
		if(mainOrder.getVerifierId() > 0) {
			mainOrder.setVerifierName(user.getUserByUserId(mainOrder.getVerifierId()).getUsername());
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
	 * 	退款订单详情
	 * @param param
	 * @return
	 * @throws MpException 
	 */
	public ReturnOrderInfoVo getReturnOrder(ReturnOrderParam param) throws MpException{
		ReturnOrderRecord rOrder = returnOrder.getByReturnOrderSn(param.getReturnOrderSn());
		if(Objects.isNull(rOrder)) {
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_RETURN_ORDER_NOT_EXIST);
		}
		OrderInfoRecord order = orderInfo.getOrderByOrderSn(rOrder.getOrderSn());
		if(Objects.isNull(order)) {
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		//init vo
		ReturnOrderInfoVo vo = rOrder.into(ReturnOrderInfoVo.class);
		//vo set order
		vo.setOrderInfo(order.into(OrderInfoVo.class));
		//set can return shipping fee
		//获取已退运费
		BigDecimal returnShipingFee = returnOrder.getReturnShipingFee(rOrder.getOrderSn());
		//退运费校验
		if(OrderOperationJudgment.adminIsReturnShipingFee(vo.getOrderInfo() , returnShipingFee)){
			vo.setCanReturnShippingFee(order.getShippingFee().subtract(returnShipingFee));
		}
		//退款商品
		if(rOrder.getReturnType() != OrderConstant.RT_ONLY_SHIPPING_FEE) {
			List<OrderReturnGoodsVo> goods = returnOrderGoods.getReturnGoods(rOrder.getOrderSn(),rOrder.getRetId()).into(OrderReturnGoodsVo.class);
			vo.setReturnGoods(goods);
		}
		//快递code
		vo.setShippingCode(returnOrder.getShippingCode(rOrder));
		//金额计算
		setCalculateMoney(vo);
		//获取该退款订单操作记录
		List<OperatorRecord> operatorRecord = returnStatusChange.getOperatorRecord(rOrder.getRetId());
		vo.setOperatorRecord(operatorRecord);
		//获取最后一次操作此订单type
		if(operatorRecord.size() != 0) {
			vo.setOperatorLastType(operatorRecord.get(operatorRecord.size() - 1).getType());
		}
		//设置自动处理时间
		setReturnCfg(vo, rOrder);
		return vo;
	}
	
	/**
	 * 	设置自动处理时间
	 * @param vo
	 * @param rOrder
	 */
	public void setReturnCfg(ReturnOrderInfoVo vo , ReturnOrderRecord rOrder) {
		if(shopReturnConfig.getAutoReturn() == null || shopReturnConfig.getAutoReturn() == 0) {
			return;
		}
		if (shopReturnConfig.getAutoReturnTime() == null
				|| shopReturnConfig.getAutoReturnTime().after(DateUtil.getSqlTimestamp())) {
			return;
		}

		if (rOrder.getReturnType() != OrderConstant.RT_GOODS
				&& rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING
				&& shopReturnConfig.getReturnMoneyDays() != null) {
			//买家发起仅退款申请后，商家在return_money_days日内未处理，系统将自动退款
			vo.setReturnMoneyDays(rOrder.getShippingOrRefundTime().toInstant()
					.plus(Duration.ofDays(shopReturnConfig.getReturnMoneyDays())).toEpochMilli());
			return;
		}
		if (rOrder.getReturnType() == OrderConstant.RT_GOODS) {
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDITING 
					&& shopReturnConfig.getReturnAddressDays() != null
					) {
				//商家已发货，买家发起退款退货申请，商家在return_address_days日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址
				vo.setReturnAddressDays(rOrder.getApplyTime().toInstant()
					.plus(Duration.ofDays(shopReturnConfig.getReturnAddressDays())).toEpochMilli());
				return;
			}
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING 
					&& shopReturnConfig.getReturnShoppingDays() != null
					) {
				//买家已提交物流信息，商家在return_shopping_days日内未处理，系统将默认同意退款退货，并自动退款给买家。
				vo.setReturnShoppingDays(rOrder.getShippingOrRefundTime().toInstant()
						.plus(Duration.ofDays(shopReturnConfig.getReturnShoppingDays())).toEpochMilli());
				return;
			}
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDIT_PASS
					) {
				//商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
				vo.setReturnAuditPassNotShoppingDays(rOrder.getApplyPassTime().toInstant()
						.plus(Duration.ofDays(7)).toEpochMilli());
				return;
			}
		}
		
	}
	/**
	 * 	金额计算
	 * @param vo
	 * @param rOrder
	 */
	public void setCalculateMoney (ReturnOrderInfoVo vo) {
		if(vo.getRefundStatus() == OrderConstant.REFUND_STATUS_FINISH) {
			//成功状态查此次退款记录
			vo.setCalculateMoney(refundAmountRecord.getReturnAmountMap(Arrays.asList(vo.getOrderSn()),vo.getRetId()));
			return;
		}
		if(vo.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDIT_PASS || vo.getRefundStatus() == OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING) {
			//查询可退金额
			LinkedHashMap<String, BigDecimal> returnAmountMap = refundAmountRecord.getReturnAmountMap(Arrays.asList(vo.getOrderSn()),null);
			final BigDecimal amount = orderInfo.getOrderFinalAmount(vo.getOrderInfo() , Boolean.TRUE);
			vo.setCalculateMoney(orderInfo.getCanReturn(vo.getOrderInfo() , amount , returnAmountMap));
		}
		//其他情况不用查询
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
	 * 买单订单详情
	 * @param param
	 * @return
	 */
	 public StoreOrderInfoVo getStoreOrder(String orderSn) {
		return storeOrder.get(orderSn);
	}

	/**
	 * 分裂营销活动的活动数据分析的订单部分数据
	 * @param param
	 * @return
	 */
	 public Map<Date,Integer> getMarketOrderAnalysis(MarketAnalysisParam param){
		 return orderInfo.getMarketOrderAnalysis(param);
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
		return orderInfo.getActiveDiscountMoney(goodType, activityId, startTime, endTime);
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
		return orderInfo.getActiveOrderList(goodType, activityId, startTime, endTime);
	}

	 /**
     * 营销活动订单查询
     *
     * @param param
      * @param goodsType 参考OrderConstant类中的常量
     * @return
     */
    public PageResult<MarketOrderListVo> getMarketOrderList(MarketOrderListParam param, byte goodsType) {
        PageResult<MarketOrderListVo> res = orderInfo.getMarketOrderList(param,goodsType);

        /** 填充商品行 */
        for(MarketOrderListVo order : res.dataList){
            order.setGoods(orderGoods.getMarketOrderGoodsByOrderSn(order.getOrderSn()));
        }

        return res;
    }

}
