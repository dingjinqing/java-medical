package com.vpu.mp.service.shop.order;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.db.shop.tables.records.ReturnStatusChangeRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupOrderVo;
import com.vpu.mp.service.pojo.shop.member.InviteSourceConstant;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.OrderQueryVo;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportQueryParam;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.must.OrderMustVo;
import com.vpu.mp.service.pojo.shop.order.refund.OperatorRecord;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.shipping.BaseShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.wxapp.comment.CommentListVo;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintDayVo;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyUserInfo;
import com.vpu.mp.service.pojo.wxapp.order.OrderInfoMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsMpVo;
import com.vpu.mp.service.pojo.wxapp.order.refund.AfterSaleServiceVo;
import com.vpu.mp.service.pojo.wxapp.order.refund.ReturnOrderListMp;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.ShopReturnConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.goods.FootPrintService;
import com.vpu.mp.service.shop.goods.GoodsCommentService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyListService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.order.action.ReturnService;
import com.vpu.mp.service.shop.order.action.ShipService;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.AdminMarketOrderInfoService;
import com.vpu.mp.service.shop.order.info.MpOrderInfoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.invoice.InvoiceService;
import com.vpu.mp.service.shop.order.must.OrderMustService;
import com.vpu.mp.service.shop.order.record.ReturnStatusChangeService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
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

import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.STATUS_WAIT_PAY;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.NOT_GET;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.SRC_BACK_STAGE;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.NO;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.YES;

/**
 * 	订单模块普通查询service
 * @author 常乐 2019年6月27日;王帅 2019/7/10
 */
@Service
public class OrderReadService extends ShopBaseService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public OrderInfoService orderInfo;
    @Autowired
    private AdminMarketOrderInfoService marketOrderInfo;
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
	@Autowired
	private ShipService ship;
	@Autowired
	private MpOrderInfoService mpOrderInfo;
	@Autowired
	private TradeService trade;
	@Autowired
	private GroupBuyListService groupBuyList;
	@Autowired
	private GroupBuyService groupBuyService;
	@Autowired
	private PreSaleService preSale;
	@Autowired
	private StoreService store;
	@Autowired
	private InvoiceService invoice;
    @Autowired
    private OrderMustService orderMust;
    @Autowired
    private GoodsCommentService goodsComment;
    @Autowired
    private GoodsMpService goodsMpService;
    @Autowired
    private FootPrintService footPrintService;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private ConfigService configService;
	/**
	 * 订单查询
	 * @param param
	 * @return PageResult
	 */
	public OrderQueryVo getPageList(OrderPageListQueryParam param) {
		logger.info("订单综合查询开始");
		OrderQueryVo result = new OrderQueryVo();
		//退款退货订单查询(其主表不同,所以走分支逻辑)
		if(param.searchType != null && param.searchType == 1) {
            result.setList(getReturnPageList(param));
			return result;
		}

		PageResult<OrderListInfoVo> pageResult = new PageResult<>();
        result.setList(pageResult);
		//得到订单号(包含主订单和正常订单)
		PageResult<String> orderSn = orderInfo.getOrderSns(param, result);
		pageResult.setPage(orderSn.getPage());
		if(orderSn.getDataList().size() < 1) {
			return result;
		}
		//查询出全部订单按照主订单分组，正常订单的key为orderSn
		Map<String, List<OrderListInfoVo>> allOrder = orderInfo.getOrders(orderSn.getDataList());
		logger.error(allOrder.toString());
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
		Integer[] allOrderSn = goodsList.keySet().toArray(new Integer[0]);
		//key为order_id,v为其下商品
		Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderIds(allOrderSn).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
		Set<Entry<Integer, List<OrderGoodsVo>>> entrySet = goods.entrySet();
		for (Entry<Integer, List<OrderGoodsVo>> entry : entrySet) {
			//过滤主订单中已经拆到子订单的商品(依赖于orderinfo表自增id,当循环到主订单时其子订单下的商品都已插入到childOrders.goods里)
			if(orderCountMoreZero.contains(entry.getKey())) {
				orderInfo.filterMainOrderGoods(goodsList.get(entry.getKey()),entry.getValue());
				continue;
			}
			goodsList.get(entry.getKey()).setGoods(entry.getValue());
		}
		//查询订单订单是否存在退款中订单
		Map<Integer, Integer> returningCount = returnOrder.getOrderCount(allOrderSn, OrderConstant.REFUND_STATUS_AUDITING , OrderConstant.REFUND_STATUS_AUDIT_PASS , OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);
		//设置订单操作
		for (List<OrderListInfoVo> orderList: allOrder.values()) {
			for(OrderListInfoVo order : orderList) {
				OrderOperationJudgment.operationSet(order,returningCount.get(order.getOrderId()),ship.canBeShipped(order.getOrderSn()));
			}
		}
		pageResult.setDataList(mainOrderList);
		logger.info("订单综合查询结束");
		return result;
	}


	/**
	 * 订单详情
	 * @param orderSn
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
			if(order.getRefundStatus() != OrderConstant.REFUND_DEFAULT_STATUS) {
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
		refundByOrderSn.forEach((k,v)->
			v.forEach(rOrder->
				rOrder.setOrderReturnGoodsVo(refundGoodsByOrderSn.get(rOrder.getRetId()))
			)
		);
		//查询订单订单是否存在退款中订单
		Map<Integer, Integer> returningCount = returnOrder.getOrderCount(orderIds.toArray(new Integer[orderIds.size()]), OrderConstant.REFUND_STATUS_AUDITING , OrderConstant.REFUND_STATUS_AUDIT_PASS , OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);
		//构造order
		for (OrderInfoVo vo : orders) {
			vo.setShippingList(shippingByOrderSn.get(vo.getOrderSn()));
			vo.setRefundList(refundByOrderSn.get(vo.getOrderSn()));
			vo.setGoods(goods.get(vo.getOrderId()));
			//设置订单操作
			OrderOperationJudgment.operationSet(vo,returningCount.get(vo.getOrderId()),ship.canBeShipped(vo.getOrderSn()));
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
		Map<Integer, List<OrderReturnGoodsVo>> goods = returnOrderGoods.getByOrderSn(collect.toArray(new String[collect.size()])).intoGroups(returnOrderGoods.TABLE.RET_ID,OrderReturnGoodsVo.class);;
		for (OrderReturnListVo order : dataList) {
			order.setGoods(goods.get(order.getRetId()));
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
		BigDecimal returnShipingFee = returnOrder.getReturnShippingFee(rOrder.getOrderSn());
		//退运费校验
		if(OrderOperationJudgment.adminIsReturnShipingFee(vo.getOrderInfo().getShippingFee(), returnShipingFee, true)){
			vo.setCanReturnShippingFee(order.getShippingFee().subtract(returnShipingFee));
		} else {
            vo.setCanReturnShippingFee(BigDecimal.ZERO);
        }
		//退款商品
		if(rOrder.getReturnType() != OrderConstant.RT_ONLY_SHIPPING_FEE) {
			List<OrderReturnGoodsVo> goods = returnOrderGoods.getReturnGoods(rOrder.getOrderSn(),rOrder.getRetId()).into(OrderReturnGoodsVo.class);
            Map<Integer, OrderGoodsMpVo> keyMapByIds = orderGoods.getKeyMapByIds(order.getOrderId());
            goods.forEach(x->x.setIsGift(keyMapByIds.get(x.getRecId()).getIsGift()));
            vo.setReturnGoods(goods);
		}
		//快递消息
        ExpressVo shippingInfo = getShippingInfo(rOrder);
		if(shippingInfo != null) {
            vo.setShippingCode(shippingInfo.getShippingCode());
            vo.setShippingName(shippingInfo.getShippingName());
        }
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
		//设置订单类型
        vo.setOrderType(OrderInfoService.orderTypeToArray(order.getGoodsType()));
		return vo;
	}

	/**
	 * 	设置自动处理时间
	 * @param vo
	 * @param rOrder
	 */
	public void setReturnCfg(ReturnOrderInfoVo vo , ReturnOrderRecord rOrder) {
		if(shopReturnConfig.getAutoReturn() == 0) {
			return;
		}
		if (shopReturnConfig.getAutoReturn() == 1 && shopReturnConfig.getAutoReturnTime().after(rOrder.getCreateTime())) {
			return;
		}
        long currentTimeMillis = System.currentTimeMillis();
		//以下自动处理时间为时间间隔单位毫秒
        if (rOrder.getReturnType() != OrderConstant.RT_GOODS
				&& rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING
				&& shopReturnConfig.getReturnMoneyDays() != null) {
			//买家发起仅退款申请后，商家在return_money_days日内未处理，系统将自动退款
			vo.setReturnMoneyDays(rOrder.getShippingOrRefundTime().toInstant()
					.plus(Duration.ofDays(shopReturnConfig.getReturnMoneyDays())).toEpochMilli() - currentTimeMillis);
			return;
		}
		if (rOrder.getReturnType() == OrderConstant.RT_GOODS) {
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDITING
					&& shopReturnConfig.getReturnAddressDays() != null) {
				//商家已发货，买家发起退款退货申请，商家在return_address_days日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址
				vo.setReturnAddressDays(rOrder.getApplyTime().toInstant()
					.plus(Duration.ofDays(shopReturnConfig.getReturnAddressDays())).toEpochMilli() - currentTimeMillis);
				return;
			}
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING
                && shopReturnConfig.getReturnShippingDays() != null) {
				//买家已提交物流信息，商家在return_shopping_days日内未处理，系统将默认同意退款退货，并自动退款给买家。
                vo.setReturnShippingDays(rOrder.getShippingOrRefundTime().toInstant()
                    .plus(Duration.ofDays(shopReturnConfig.getReturnShippingDays())).toEpochMilli() - currentTimeMillis);
				return;
			}
			if(rOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDIT_PASS) {
				//商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
				vo.setReturnAuditPassNotShoppingDays(rOrder.getApplyPassTime().toInstant()
						.plus(Duration.ofDays(shopReturnConfig.getReturnPassDays())).toEpochMilli() - currentTimeMillis);
				return;
			}
		}

	}
	/**
	 * 	金额计算
	 * @param vo
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
	 * @param orderSn
	 * @return
	 */
	 public StoreOrderInfoVo getStoreOrder(String orderSn) {
		return storeOrder.get(orderSn);
	}

	/**
	 * mp端查询订单
	 * @param param
	 */
	public PageResult<OrderListMpVo> getPageList(OrderListParam param) {
		logger.info("mp订单列表查询"+param.toString());
		PageResult<OrderListMpVo> result = mpOrderInfo.getPageList(param);
		if(CollectionUtils.isEmpty(result.dataList)) {
		 return result;
		}
		List<Integer> ids = result.dataList.stream().map(OrderListMpVo::getOrderId).collect(Collectors.toList());
		//商品
		Map<Integer, List<OrderGoodsMpVo>> goods = orderGoods.getByOrderIds(ids.toArray(new Integer[ids.size()])).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsMpVo.class);
		for(OrderListMpVo order : result.dataList) {
			//订单类型
			order.setOrderType(Arrays.asList(OrderInfoService.orderTypeToArray(order.getGoodsType())));
			//奖品订单判断
			order.setIsLotteryGift(isAwardOrder(order.getOrderType()) ? YES : NO);
			//设置商品
			order.setGoods(goods.get(order.getOrderId()));
			//订单操作设置（商品订单类型需要提前计算好）
			setMpOrderOperation(order);
			for (OrderGoodsMpVo temp : order.getGoods()) {
				if(StringUtils.isBlank(temp.getGoodsImg())) {
					//默认图片
					temp.setGoodsImg("image/default.jpg");
				}
				temp.setIsGift(order.getIsLotteryGift().intValue());

			}
			//拼团
			if(true) {
				order.setGroupBuyInfo(groupBuyList.getByOrder(order.getOrderSn()));
			}
			//补款设置时间
			if(order.getBkOrderPaid() == OrderConstant.BK_PAY_FRONT) {
				setBkPayOperation(order);
			}
			//是否退过款
			order.setIsReturn(order.getRefundStatus() != OrderConstant.REFUND_DEFAULT_STATUS ? YES : NO);
		}
		return result;
	}


	private void setBkPayOperation(OrderListMpVo order) {
		//有效时间区间
		Record2<Timestamp, Timestamp> timeInterval = preSale.getTimeInterval(order.getActivityId());
		order.setPreSaleTimeInterval(new Timestamp[] {timeInterval.value1(),timeInterval.value2()});
		long currenTmilliseconds  = Instant.now().toEpochMilli();
		if(timeInterval.value1().getTime() < currenTmilliseconds && currenTmilliseconds < timeInterval.value2().getTime() ) {
			order.setIsPayEndPayment(NumberUtils.BYTE_ONE);
		}else {
			order.setIsPayEndPayment(NumberUtils.BYTE_ZERO);
		}
	}

	/**
	 * mp订单详情
	 * @param param
	 * @return
	 * @throws MpException
	 */
	public OrderInfoMpVo mpGet(OrderParam param) throws MpException {
		List<OrderInfoMpVo> orders = orderInfo.getByOrderSn(param.getOrderSn() , OrderInfoMpVo.class);
		if(CollectionUtils.isEmpty(orders)) {
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		OrderInfoMpVo order = orders.get(0);
        List<String> orderType = Arrays.asList(OrderInfoService.orderTypeToArray(order.getGoodsType()));
		//商品
		Map<Integer, OrderGoodsMpVo> goods = orderGoods.getKeyMapByIds(order.getOrderId());
		//set orderType
		order.setOrderType(orderType);
		List<OrderGoodsMpVo> goodsList = new ArrayList<OrderGoodsMpVo>(goods.values());
		//set goods
		order.setGoods(goodsList);
		//奖品订单判断
		order.setIsLotteryGift(isAwardOrder(order.getOrderType()) ? YES : NO);
		//订单操作设置
		setMpOrderOperation(order);
		//是否退过款
		order.setIsReturn(order.getRefundStatus() != OrderConstant.REFUND_DEFAULT_STATUS ? YES : NO);
		//门店信息
		order.setStoreInfo(order.getStoreId() > 0 ? store.getStore(order.getOrderId()) : null);
		//发票
		order.setInvoiceInfo(order.getInvoiceId() > 0 ? invoice.get(order.getInvoiceId()) : null);

		//当前订单配送信息
		order.setShippingInfo(getMpOrderShippingInfo(order.getOrderSn(), goods));
		//核销员信息
		if(order.getVerifierId() > 0) {
			UserRecord userInfo = user.getUserByUserId(order.getVerifierId());
			order.setVerifierInfo(userInfo.getUsername(), userInfo.getMobile());
		}
		//子单
		if(orderType.indexOf(Byte.valueOf(BaseConstant.ACTIVITY_TYPE_GIVE_GIFT).toString()) != -1 && order.getOrderSn().equals(order.getMainOrderSn()) && orders.size() > 1) {
			//只显示生成订单的子订单
			order.setSubOrder(getSubOrder(orders.subList(1, orders.size())));
		}
		//好物圈

		// 拼团
		if(orderType.indexOf(Byte.valueOf(BaseConstant.ACTIVITY_TYPE_GROUP_BUY).toString()) != -1){
			GroupOrderVo groupOrder = groupBuyList.getByOrder(order.getOrderSn());
			//未退款
			if (!groupOrder.getStatus().equals(GroupBuyConstant.STATUS_FAILED)&&!groupOrder.getStatus().equals(STATUS_WAIT_PAY)){
                Integer groupBuyLimitAmout = groupBuyService.getGroupBuyLimitAmout(groupOrder.getActivityId());
                List<GroupBuyUserInfo> pinUserList = groupBuyList.getGroupUserList(groupOrder.getGroupId());
                order.setGroupBuyUserInfos(pinUserList);
                order.setGroupId(groupOrder.getGroupId());
				GroupOrderVo groupOrderVo =new GroupOrderVo();
				groupOrderVo.setStatus(groupOrder.getStatus());
				groupOrderVo.setGroupBuyLimitAmout(groupBuyLimitAmout);
                order.setGroupBuyInfo(groupOrderVo);
            }
		}else if(orderType.indexOf(Byte.valueOf(BaseConstant.ACTIVITY_TYPE_GROUP_DRAW).toString()) != -1) {

		}
		//拼团抽奖

		//优惠卷


		return order;

	}

	/**
	 * mp订单操作设置
	 * @param order
	 */
	private void setMpOrderOperation(OrderListMpVo order) {
		//1.延长收货
		order.setIsExtendReceive(OrderOperationJudgment.isExtendReceive(order, getExtendReceiveDays()) ? YES : NO);
		//2.确认收货(order_status==4可以判断)
		//3.好友代付（order_pay_way == 2）
		//4.待支付状态处理order_status==0 => 去付尾款(bk_order_paid == 1) 、 去付款
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_PAY) {
			setPayOperation(order);
		}
		//5.(退货中心-退款 退货) 6. 取消 7删除
		OrderOperationJudgment.operationSet(order);
		//8.再次购买
		order.setIsShowAgainBuy(OrderOperationJudgment.isShowAgainBuy(order) ? YES : NO);
		//9.提醒发货
		order.setIsRemindShip(OrderOperationJudgment.isShowRemindShip(order) ? YES : NO);
		//10.评价（查看评价、评价有礼/商品评价）
		order.setIsShowCommentType(getCommentType(order));
		//TODO 幸运大抽奖 分享优惠卷。。。。
		/**按钮-end*/
	}

	/**
	 * 奖品订单
	 * @param orderType
	 */
	private boolean isAwardOrder(List<String> orderType) {
		for (String type : orderType) {
			if(OrderConstant.AWARD_ORDER.contains(Byte.valueOf(type))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 订单待支付状态处理时间（包括预售、定金）
	 * @param order
	 */
	private void setPayOperation(OrderListMpVo order) {
		long currenTmilliseconds  = Instant.now().toEpochMilli();
		if(order.getBkOrderPaid() == OrderConstant.BK_PAY_FRONT) {
			//预售、定金订单
			Record2<Timestamp, Timestamp> timeInterval = preSale.getTimeInterval(order.getActivityId());
			if(timeInterval.value1().getTime() < currenTmilliseconds && currenTmilliseconds < timeInterval.value2().getTime() ) {
				order.setPayOperationTime(timeInterval.value2().getTime() - currenTmilliseconds);
			}else {
				order.setPayOperationTime(Long.valueOf(0));
				order.setPreSaleTimeInterval(new Timestamp[] {timeInterval.value1() , timeInterval.value2()});
			}
		}else {
			//普通订单待支付取消时间
			order.setPayOperationTime(order.getExpireTime().getTime() - currenTmilliseconds);
		}
		order.setIsShowPay(order.getPayOperationTime() > 0 ? YES : NO);
	}

	/**
	 * 当前订单配送信息
	 * @param orderSn
	 * @param goods
	 * @return
	 */
	public List<ShippingInfoVo> getMpOrderShippingInfo(String orderSn , Map<Integer, OrderGoodsMpVo> goods){
		Map<String, List<ShippingInfoVo>> shippingMap = shipInfo.getShippingByOrderSn(orderSn);
		List<ShippingInfoVo> result = shippingMap.get(orderSn);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}else {
            List<Byte> collect = result.stream().map(ShippingInfoVo::getShippingId).collect(Collectors.toList());
            Map<Byte, ExpressVo> express = expressService.gets(collect);
            result.forEach(x -> {
                if(express.get(x.getShippingId()) != null && !StringUtils.isBlank(express.get(x.getShippingId()).getShippingName())){
                    x.setShippingName(express.get(x.getShippingId()).getShippingName());
                }else{
                    //正常情况不会出现这种情况
                    x.setShippingName(StringUtils.EMPTY);
                }
				x.getGoods().forEach(y->{
					y.setGoodsImg(goods.get(y.getOrderGoodsId()).getGoodsImg());
					y.setGoodsPrice(goods.get(y.getOrderGoodsId()).getGoodsPrice());
				});
			});
		}
		return result;
	}

	/**
	 * 延长收货
	 * @return
	 */
	public int getExtendReceiveDays() {
		Byte switchFlag = trade.getExtendReceiveGoods();
		if(switchFlag == 0) {
			return 0;
		}
		Integer days = trade.getExtendReceiveDays();
		return days.intValue();
	}

	/**
	 * 子订单
	 * @param subOrder
	 * @return
	 */
	public List<OrderInfoMpVo> getSubOrder(List<OrderInfoMpVo> subOrder) {
		List<Integer> ids = subOrder.stream().map(OrderListMpVo::getOrderId).collect(Collectors.toList());
		Map<Integer, List<OrderGoodsMpVo>> subOrderGoods = orderGoods.getByOrderIds(ids.toArray(new Integer[ids.size()])).intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsMpVo.class);
		subOrder.forEach(x->x.setGoods(subOrderGoods.get(x.getOrderId())));
		return subOrder;

	}

	/**
	 * 小程序展示评价相关
	 * @param order
	 * @return 0不展示 1查看评价 2评价有礼 3商品评价
	 */
	public Byte getCommentType(OrderListMpVo order){
		if(order.getOrderStatus() != OrderConstant.ORDER_RECEIVED &&  order.getOrderStatus() != OrderConstant.ORDER_FINISHED) {
			//0不展示
			return NO;
		}
		if(order.getCommentFlag() > 0) {
			//1查看评价
			return 1;
		}
		List<OrderGoodsMpVo> goods = order.getGoods();
		List<CommentListVo> converGoods = new ArrayList<CommentListVo>();
		//转化类型
		goods.forEach(x->converGoods.add(new CommentListVo(x.getGoodsId())));
		if(goodsComment.orderIsCommentAward(converGoods)) {
			//2评价有礼
			return 2;
		}
		//3商品评价
		return 3;
	}

	/**
	 * 统计订单各个状态的数量
	 * @param param
	 * @return
	 */
	public Map<Byte, Integer> statistic(OrderListParam param) {
		return mpOrderInfo.getOrderStatusNum(param.getWxUserInfo().getUserId(), false);
	}

    /**
     * 小程序端点击售后中心展示数据(曾经退过)
     * @param param
     */
    public AfterSaleServiceVo mpReturnList(OrderParam param) throws MpException {
        AfterSaleServiceVo vo = new AfterSaleServiceVo();
        OrderInfoRecord order = orderInfo.getOrderByOrderSn(param.getOrderSn());
        vo.setOrderSn(order.getOrderSn());
        vo.setCreateTime(order.getCreateTime());
        //展示退款操作页面
        OrderOperateQueryParam query = new OrderOperateQueryParam();
        query.setIsMp(OrderConstant.IS_MP_Y);
        query.setOrderSn(order.getOrderSn());
        query.setOrderId(order.getOrderId());
        RefundVo operateInfo = (RefundVo) returnService.query(query);
        if(operateInfo != null && CollectionUtils.isNotEmpty(operateInfo.getRefundGoods())){
            vo.setReturnFlag(YES);
        }else {
            vo.setReturnFlag(NO);
        }
        //获取已退运费
        BigDecimal returnShipingFee = returnOrder.getReturnShippingFee(param.getOrderSn());
        //退运费校验
        if(OrderOperationJudgment.adminIsReturnShipingFee(order.getShippingFee(), returnShipingFee, true)){
            vo.setCanReturnShippingFee(order.getShippingFee().subtract(returnShipingFee));
        }else {
            vo.setCanReturnShippingFee(BigDecimal.ZERO);
        }
        //退款记录
        Result<ReturnOrderRecord> rOrders = returnOrder.getRefundByOrderSn(param.getOrderSn());
        //买家提交物流快递名称

        vo.setReturnOrderlist(new ArrayList<>(rOrders.size()));
        rOrders.forEach(rOrder->{
            ReturnOrderListMp returnOrderListMp = rOrder.into(ReturnOrderListMp.class);
            //买家；商家（包含定时任务）
                ReturnStatusChangeRecord lastOperator = returnStatusChange.getLastOperator(rOrder.getRetId());
                returnOrderListMp.setRole(OrderConstant.IS_MP_Y == lastOperator.getType() ? OrderConstant.IS_MP_Y : OrderConstant.IS_MP_ADMIN);
                returnOrderListMp.setFinishTime(lastOperator.getCreateTime());
                if(!StringUtils.isBlank(returnOrderListMp.getShippingType())) {
                    ExpressVo expressVo = expressService.get(Byte.valueOf(returnOrderListMp.getShippingType()));
                    returnOrderListMp.setShippingName(expressVo == null ? null : expressVo.getShippingName());
                }
                vo.getReturnOrderlist().add(returnOrderListMp);
        });
        return vo;
    }

    /**
     * 获取赠品订单数
     * @param giftId 赠品id
     * @param isIncludeReturn 是否包含退款赠品
     */
    public Integer getGiftOrderCount(Integer giftId, boolean isIncludeReturn){
        List<String> giftOrderSns = orderGoods.getGiftOrderSns(giftId, isIncludeReturn);
        return orderInfo.getGiftOrderCount(giftOrderSns);
    }

    /**
     * 	获取该退款订单物流code(快递100对应code)
     * @param returnOrder
     * @return
     */
    private ExpressVo getShippingInfo(ReturnOrderRecord returnOrder) {
        if(!StringUtils.isBlank(returnOrder.getShippingType())) {
            return expressService.get(Byte.valueOf(returnOrder.getShippingType()));
        }else {
            return null;
        }
    }

    /*********************************************************************************************************/

	/**
	 * 分裂营销活动的活动数据分析的订单部分数据
	 * @param param
	 * @return
	 */
	 public Map<Date,Integer> getMarketOrderAnalysis(MarketAnalysisParam param){
		 return marketOrderInfo.getMarketOrderAnalysis(param);
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
	public List<ActiveDiscountMoney> getActiveDiscountMoney(Byte goodType, Integer activityId, Timestamp startTime, Timestamp  endTime){
		return marketOrderInfo.getActiveDiscountMoney(goodType, activityId, startTime, endTime);
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
	public ActiveOrderList getActiveOrderList(Byte goodType, Integer activityId, Timestamp startTime, Timestamp  endTime) {
		return marketOrderInfo.getActiveOrderList(goodType, activityId, startTime, endTime);
	}

	 /**
     * 营销活动订单查询
     *
     * @param param
      * @param goodsType 参考OrderConstant类中的常量
     * @return
     */
    public PageResult<MarketOrderListVo> getMarketOrderList(MarketOrderListParam param, byte goodsType) {
        PageResult<MarketOrderListVo> res = marketOrderInfo.getMarketOrderList(param,goodsType);

        /** 填充商品行 */
        for(MarketOrderListVo order : res.dataList){
            order.setGoods(orderGoods.getMarketOrderGoodsByOrderSn(order.getOrderSn()));
        }

        return res;
    }

    /**
     * 订单导出数据的条数
     * @param param
     * @return
     */
    public int getExportOrderListSize(OrderExportQueryParam param) {
        return orderInfo.getExportOrderListSize(param);
    }

    /**
     *	 订单列表导出
     * @param param
     * @param lang
     * @return workbook
     */
    public Workbook exportOrderList(OrderExportQueryParam param,List<String> columns, String lang) {
        List<OrderExportVo> orderList = orderInfo.getExportOrderList(param);

        //循环处理需要处理的列
        for(OrderExportVo order : orderList){
            if(columns.contains(OrderExportVo.IS_NEW)){
                //是否新用户
                if(orderInfo.getUserOrderNumber(order.getUserId(),null,order.getCreateTime()) > 0){
                    order.setIsNew(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_REGULAR_USER ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                }else {
                    order.setIsNew(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_NEW_USER ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                }
            }
            if(columns.contains(OrderExportVo.PAY_NAMES)){
                //支付方式
                StringBuffer payNames = new StringBuffer();
                if(order.getIsCod() > 0){
                    //货到付款
                    if(payNames.capacity() == 0){
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_COD ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }else{
                        payNames.append(",");
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_COD ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }
                }
                if(order.getMoneyPaid().compareTo(BigDecimal.ZERO) > 0){
                    //微信支付
                    if(payNames.capacity() == 0){
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_WXPAY ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }else{
                        payNames.append(",");
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_WXPAY ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }
                }
                if(order.getUseAccount().compareTo(BigDecimal.ZERO) > 0){
                    //余额支付
                    if(payNames.capacity() == 0){
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_BALANCE ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }else{
                        payNames.append(",");
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_BALANCE ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }
                }
                if(order.getScoreDiscount().compareTo(BigDecimal.ZERO) > 0){
                    //积分支付
                    if(payNames.capacity() == 0){
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_SCORE ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }else{
                        payNames.append(",");
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_SCORE ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }
                }
                if(order.getMemberCardReduce().compareTo(BigDecimal.ZERO) > 0){
                    //会员卡支付
                    if(payNames.capacity() == 0){
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_MEMBER_CARD ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }else{
                        payNames.append(",");
                        payNames.append(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_PAY_TYPE_MEMBER_CARD ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                    }
                }
            }
            if(columns.contains(OrderExportVo.PRD_COST_PRICE)){
                //成本价
                order.setPrdCostPrice(saas.getShopApp(getShopId()).goods.goodsPrice.getCostPrice(order.getProductId()));
            }
            if(columns.contains(OrderExportVo.PRD_WEIGHT)){
                //规格重量（暂时取商品重量）
                order.setPrdWeight(saas.getShopApp(getShopId()).goods.getGoodsWeightById(order.getGoodsId()));
            }
            if(columns.contains(OrderExportVo.ORDER_STATUS_NAME)){
                //订单状态
                order.setOrderStatusName(OrderConstant.getOrderStatusName(order.getOrderStatus(),lang));
            }
            if(order.getPartShipFlag() == OrderConstant.PART_SHIP){
                //部分发货
                BaseShippingInfoVo shipping = shipInfo.getOrderGoodsShipping(order.getOrderSn(),order.getRecId());
                if(shipping != null){
                    if(shipping.getConfirmTime() != null){
                        order.setOrderStatusName(OrderConstant.getOrderStatusName(OrderConstant.ORDER_RECEIVED,lang));
                    }else{
                        order.setOrderStatusName(OrderConstant.getOrderStatusName(OrderConstant.ORDER_WAIT_DELIVERY,lang));
                    }
                    order.setShippingTime(shipping.getShippingTime());
                    order.setShippingName(shipping.getShippingName());
                    order.setShippingNo(shipping.getShippingNo());
                }else{
                    order.setShippingTime(null);
                    order.setShippingName("");
                    order.setShippingNo("");
                }
            }
            if(columns.contains(OrderExportVo.RETURN_SHIPPING_FEE)){
                //退运费
                order.setReturnShippingFee(returnOrder.getReturnShippingFee(order.getOrderSn()));
            }
            if(columns.contains(OrderExportVo.RETURN_TIME) || columns.contains(OrderExportVo.RETURN_FINISH_TIME) || columns.contains(OrderExportVo.RETURN_ORDER_MONEY)){
                //退货退款信息
                OrderConciseRefundInfoVo returnInfo = returnOrderGoods.getOrderGoodsReturnInfo(order.getRecId());
                if(returnInfo != null){
                    order.setReturnTime(OrderConstant.RT_ONLY_MONEY == returnInfo.getReturnType() ? returnInfo.getApplyTime() : returnInfo.getShippingOrRefundTime());
                    order.setReturnFinishTime(returnInfo.getRefundSuccessTime());
                    order.setReturnOrderMoney(returnOrderGoods.getReturnGoodsMoney(order.getRecId()));
                }
            }
            if(columns.contains(OrderExportVo.IS_COD)){
                //是否货到付款
                order.setIsCodString(OrderConstant.IS_COD_YES.equals(order.getIsCod()) ? Util.translateMessage(lang, JsonResultMessage.YES ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL) : Util.translateMessage(lang, JsonResultMessage.NO ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
            }
            if(columns.contains(OrderExportVo.SOURCE)){
                //商品来源
                GoodsRecord goods= saas.getShopApp(getShopId()).goods.getGoodsById(order.getGoodsId()).get();
                order.setSource(goods.getSource() > 0 ? Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_GOODS_SOURCE_SELF_OPERATED ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL) : Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_GOODS_SOURCE_PLATFORM ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
            }
            if(columns.contains(OrderExportVo.CUSTOM)){
                //下单必填信息
                OrderMustVo orderMustVo = orderMust.getOrderMustByOrderSn(order.getOrderSn());
                if(orderMustVo != null){
                    orderMustVo.setLang(lang);
                    order.setCustom(orderMustVo.toString());
                }
            }
            if(columns.contains(OrderExportVo.USER_SOURCE)){
                //下单用户来源
                if(SRC_BACK_STAGE.getCode().equals(order.getUserSource())){
                    order.setUserSourceString(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_USER_SOURCE_ADMIN ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                }
                if(NOT_GET.getCode().equals(order.getUserSource()) && order.getInviteSource() != null && !order.getInviteSource().equals(InviteSourceConstant.INVITE_SOURCE_CHANNEL)){
                    order.setUserSourceString(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_USER_SOURCE_UNKNOWN ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL));
                }
                if(order.getUserSource() != null && order.getUserSource() > SRC_BACK_STAGE.getCode()){
                    order.setUserSourceString(saas.getShopApp(getShopId()).store.getStoreName(order.getUserSource()));
                }
                if(order.getInviteSource() != null && order.getInviteSource().equals(InviteSourceConstant.INVITE_SOURCE_CHANNEL)){
                    String channelName = saas.getShopApp(getShopId()).channelService.selectChannelName(order.getInviteActId());
                    order.setUserSourceString(Util.translateMessage(lang, JsonResultMessage.ORDER_EXPORT_USER_SOURCE_CHANNEL ,OrderExportVo.LANGUAGE_TYPE_EXCEL,OrderExportVo.LANGUAGE_TYPE_EXCEL,channelName));
                }
            }
            if(columns.contains(OrderExportVo.USER_TAG)){
                List<TagVo> tagList = saas.getShopApp(getShopId()).member.getTagForMember(order.getUserId());
                StringBuffer tags = new StringBuffer();
                for(TagVo tag : tagList){
                    tags.append(tag.getTagName()).append(";");
                }
                order.setUserTag(tags.toString());
            }

            //TODO
        }

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(orderList, OrderExportVo.class,columns);
        return workbook;
    }
	/**
	 *  购买商品记录(三个月内)
	 * @param userId  用户ID
	 * @param keyWord 关键字
	 * @param currentPages 当前页
	 * @param pageRows 每页行数
	 * @return OrderGoodsHistoryBo
	 * @author kdc
	 */
	public FootprintListVo buyingHistoryGoodsList(Integer userId, String keyWord, Integer currentPages, Integer pageRows){
		FootprintListVo footprintListVo =new FootprintListVo();
		List<FootprintDayVo> footprintDaylist =new ArrayList<>();
		footprintListVo.setDay(footprintDaylist);
		Result<? extends Record> records = orderGoods.buyingHistoryGoodsList(userId, keyWord, currentPages, pageRows);
		Integer totalRows = orderGoods.buyingHistoryGoodsCount(userId, keyWord);
		List<Integer> goodsIdList = Arrays.asList(records.intoArray(ORDER_GOODS.GOODS_ID));
		List<FootprintDayVo> orderGoodsHistoryVos =records.into(FootprintDayVo.class);
		Page page = Page.getPage(totalRows, currentPages, pageRows);
		footprintListVo.setPage(page);
		List<? extends GoodsListMpVo> goodsListMpVos = goodsMpService.getGoodsListNormal(goodsIdList, userId);
		Map<Integer, GoodsListMpVo> goodsListMpVoMap = goodsListMpVos.stream().collect(Collectors.toMap(GoodsListMpVo::getGoodsId, goods->goods));
		orderGoodsHistoryVos.forEach(orderGoods->{
			GoodsListMpVo goodsListMpVo = goodsListMpVoMap.get(orderGoods.getGoodsId());
			orderGoods.getGoodsList().add(goodsListMpVo);
		});
		// 安装日期分组
		footPrintService.byDateGroup(orderGoodsHistoryVos,footprintDaylist);
		//是否显示划线价开关
		Byte delMarket = configService.shopCommonConfigService.getDelMarket();
		//是否显示购买按钮
		ShowCartConfig showCart = configService.shopCommonConfigService.getShowCart();
		footprintListVo.setShowCart(showCart);
		footprintListVo.setDelMarket(delMarket);
		return footprintListVo;
	}

	public void  getOrderDiscountedAmount(String orderSn){

	}
}
