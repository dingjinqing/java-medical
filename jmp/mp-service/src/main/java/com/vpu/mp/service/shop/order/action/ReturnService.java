package com.vpu.mp.service.shop.order.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam.ReturnGoods;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
/**
 * 
 * @author 王帅
 *
 */
@Service
public class ReturnService extends ShopBaseService implements IorderOperate {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderInfoService orderInfo;
	@Autowired
	private RefundAmountRecordService refundAmountRecord;
	@Autowired
	private ReturnOrderService returnOrder;
	@Autowired
	private OrderGoodsService orderGoods;
	@Autowired
	private ReturnOrderGoodsService returnOrderGoods;
	@Autowired
	private OrderActionService orderAction;
	@Autowired
	private ReturnMethodService returnMethod;
	@Autowired
	private GoodsService goods;
	@Autowired
	private GoodsSpecProductService goodsSpecProduct;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.RETURN;
	}
	
	/**
	 * 退款、退货查询
	 * @param SellerRemarkParam,isMp(T小程序端/F后台手工退款)
	 * @return RefundVo
	 * @throws MpException 
	 */
	@Override
	public JsonResultCode execute(Object obj) throws MpException {
		if(!(obj instanceof RefundParam)) {
			return JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF;
		}
		RefundParam param = (RefundParam)obj;	
		//获取订单详情
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		if(order == null) {
			return JsonResultCode.CODE_ORDER_NOT_EXIST;
		}
		transaction(()->{
			try {
				ReturnOrderRecord rOrder = null;
				//仅退运费
				if(OrderConstant.RT_ONLY_SHIPPING_FEE == param.getReturnType()) {
					rOrder = returnShippingFee(param,order);
				}
				// 更新订单信息
				orderInfo.updateOrderInfoInReturn(rOrder, null, null);
				// 退款
				finishReturn(order, rOrder, BigDecimal.ZERO, param);
			} catch (DataAccessException e) {
				Throwable cause = e.getCause();
				if (cause instanceof MpException) {
					throw new MpException(((MpException) cause).getErrorCode(), e.getMessage());
				} else {
					throw new MpException(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION, e.getMessage());
				}
			}
		});
		return null;
	}

	@Override
	public Object query(OrderOperateQueryParam param) {
		logger.info("获取可退款、退货信息参数为:" + param.toString());
		Boolean isMp = param.getIsMp();
		RefundVo vo = new RefundVo();
		//获取当前订单
		OrderListInfoVo currentOrder = orderInfo.getByOrderId(param.getOrderId(),OrderListInfoVo.class);
		//退款校验
		if(OrderOperationJudgment.isReturnMoney(currentOrder, isMp)) {
			vo.getReturnType()[0] = true;
		}
		//退货校验
		if(OrderOperationJudgment.isReturnGoods(currentOrder , isMp)) {
			vo.getReturnType()[1] = true;
		}
		//获取已退运费
		BigDecimal returnShipingFee = returnOrder.getReturnShipingFee(currentOrder.getOrderSn());
		//退运费校验
		if(OrderOperationJudgment.adminIsReturnShipingFee(currentOrder , returnShipingFee)){
			vo.getReturnType()[2] = true;
			//设置
			vo.setReturnShippingFee(currentOrder.getShippingFee().subtract(returnShipingFee));
		}
		//手动退款校验,已退金额<sum(已退R商品数量*折后单价)
		if(!isMp) {
			//是否退过商品（数量角度）
			List<OrderGoodsVo> returnGoods = orderGoods.getReturnGoods(currentOrder.getOrderSn());
			//判断金额
			if(returnGoods.size() != 0) {
				BigDecimal returnMoney = returnOrder.getReturnMoney(currentOrder.getOrderSn());
				//已退商品可退最大金额
				BigDecimal returnGoodsMaxMoney = BigDecimal.ZERO;
				for (OrderGoodsVo goods : returnGoods) {
					returnGoodsMaxMoney = returnGoodsMaxMoney.add(
							BigDecimalUtil.multiplyOrDivide(
									BigDecimalPlus.create(new BigDecimal(goods.getReturnNumber()),Operator.multiply),
									BigDecimalPlus.create(goods.getDiscountedGoodsPrice(),null)));
				}
				if(BigDecimalUtil.compareTo(returnGoodsMaxMoney , returnMoney) > 0) {
					vo.getReturnType()[3] = true;
				}
			}
		}
		
		//无可退类型则返回
		if(vo.isReturn(isMp).equals(Boolean.FALSE)) {
			return vo;
		}
		//是否为拆单下的主订单
		Boolean isMain = orderInfo.isMainOrder(currentOrder);
		//如果当前订单为子订单,把订单中金额与用户信息替换为主订单的信息
		orderInfo.replaceOrderInfo(currentOrder);	
		//当前订单最终支付金额(包含运费)
		final BigDecimal amount = orderInfo.getOrderFinalAmount(currentOrder , Boolean.TRUE);
		//如果该订单为主订单则查询子订单
		List<OrderListInfoVo> subOrders = orderInfo.getChildOrders(currentOrder , isMain);
		List<String> cOrderSns = subOrders.stream().map(OrderListInfoVo::getOrderSn).collect(Collectors.toList());
		//初始化订单sn数组
		List<String> sns = new ArrayList<String>(subOrders.size() + 1);
		sns.add(currentOrder.getOrderSn());
		if(subOrders.size() > 0) {
			sns.addAll(cOrderSns);
		}
		//退款数据汇总(该汇总信息会在'构造优先级退款信息'复用)
		LinkedHashMap<String, BigDecimal> returnAmountMap = refundAmountRecord.getReturnAmountMap(sns);	
		//构造优先级退款信息
		Map<String, BigDecimal> canReturn = orderInfo.getCanReturn(currentOrder , amount , returnAmountMap);
		//查询订单下商品(如果为主订单则包含子订单商品)
		Map<String, List<RefundVoGoods>> goods = orderGoods.getByOrderSns(sns);
		//计算子订单商品数量(主订单返回的map->size=0)
		HashMap<Integer, Integer> subOrderGoodsSum = orderGoods.countSubOrderGoods(goods,currentOrder,isMain);
		//查询该订单退货中的商品
		Map<Integer, Integer> refundingGoods = returnOrderGoods.getRefundingGoods(param.getOrderSn(),OrderConstant.SUCCESS_RETURNING);
		//设置可退商品行信息
		Iterator<RefundVoGoods> currentGoods = goods.get(currentOrder.getOrderSn()).iterator();
		while (currentGoods.hasNext()) {
			RefundVoGoods oneGoods = (RefundVoGoods) currentGoods.next();
			//主订单需要减去子订单的商品数量
			if(isMain) {
				//总数 = 总数 - 子订单数量
				oneGoods.setTotal((oneGoods.getGoodsNumber() - ((subOrderGoodsSum.get(oneGoods.getProductId())) == null ? 0 : subOrderGoodsSum.get(oneGoods.getProductId()))));
			}else {
				//总数 = 总数
				oneGoods.setTotal(oneGoods.getGoodsNumber());
			}
			//已提交=退中+退完成
			Integer submitted = (refundingGoods.get(oneGoods.getProductId()) == null ? 0 : refundingGoods.get(oneGoods.getProductId())) + oneGoods.getReturnNumber();
			oneGoods.setSubmitted(submitted);
			// 可退
			Integer returnable = oneGoods.getTotal() - oneGoods.getSubmitted();
			oneGoods.setReturnable(returnable);
			//处理前后端不同逻辑
			if(isMp) {
				if(oneGoods.getReturnable() <= 0) {
					//前端可退数量为0不展示
					currentGoods.remove();
				}
			}else {
				//后台商家配置的不可退款的可退
				oneGoods.setIsCanReturn(OrderConstant.IS_CAN_RETURN_Y);
			}
		}
		vo.setRefundGoods(goods.get(currentOrder.getOrderSn()));
		vo.setReturnAmountMap(canReturn);
		logger.info("获取可发货信息完成");
		return vo;
	}
	private ReturnOrderRecord returnShippingFee(RefundParam param , OrderInfoVo order) throws MpException{
		// 只退运费必须金额>0
		if (BigDecimalUtil.compareTo(param.getShippingFee(), BigDecimal.ZERO) < 1) {
			logger.error("订单sn:"+param.getOrderSn()+"仅退运费时退运费金额必须大于0");
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_RETURN_SHIPPING_FEE_NOT_ZERO);
		}
		// 获取已退运费
		BigDecimal returnShipingFee = returnOrder.getReturnShipingFee(param.getOrderSn());
		if (!OrderOperationJudgment.adminIsReturnShipingFee(order, returnShipingFee)) {
			logger.error("订单sn:"+param.getOrderSn()+"，该订单运费已经退完");
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_RETURN_SHIPPING_FEE);
		}
		//订单状态记录
		orderAction.addRecord(order, param, order.getOrderStatus() , "保存仅退运费之前状态记录");
		logger.info("退运费创建退款订单开始");
		// 增加退款/退货记录，形成货退款订单
		ReturnOrderRecord rOrder = returnOrder.addRecord(param, order);
		logger.info("退运费创建退款订单结束");
		return rOrder;
	}
	
	/**
	 * 	退款方法
	 * @param order
	 * @param returnOrder
	 * @param currentMoney
	 * @param shippingFee
	 * @throws MpException
	 */
	public boolean finishReturn(OrderInfoVo order , ReturnOrderRecord returnOrder , BigDecimal currentMoney , RefundParam param) throws MpException {
		if(OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING != returnOrder.getRefundStatus()) {
			logger().info("退款订单sn:"+returnOrder.getReturnOrderSn()+",refundStatus"+returnOrder.getRefundStatus()+"不符合完成退款条件。");
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_STATUS_NOT_SATISFIED);
		}
		if(BigDecimalUtil.compareTo(returnOrder.getMoney(), currentMoney) < 0) {
			logger().info("退款订单sn:"+returnOrder.getReturnOrderSn()+",退款金额超过该退款订单最大金额");
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_MONEY_EXCEEDED);
		}
		//当前退款金额大于等于零,进行退款金额参数构造
		if(BigDecimalUtil.compareTo(currentMoney, BigDecimal.ZERO) > -1 || BigDecimalUtil.compareTo(param.getShippingFee(), currentMoney) > -1) {
			returnOrder.setMoney(currentMoney);
			returnOrder.setShippingFee(param.getShippingFee());
			returnOrder.update();
		}
		//当前订单为子订单需要替换支付信息与用户信息(子订单无补款信息,不需复制)
		orderInfo.replaceOrderInfo(order);
		boolean refund = refund(order,returnOrder);
		//计算退款商品行退款金额
		calculateGoodsReturnMoney(returnOrder,param.getReturnGoods());
		//TODO php order_pay_way==2 && money_paid退款返回逻辑 php returnordertrait 321行
		finishRefundChange(order , returnOrder , param);
		return refund;
	}
	
	/**
	 * 	优先级退款
	 * @param order
	 * @param returnOrder
	 * @throws MpException 
	 */
	public boolean refund(OrderInfoVo order, ReturnOrderRecord returnOrder) throws MpException {
		//是否money_paid
		boolean flag = false;
		//此次退款金额
		BigDecimal returnAmount = returnOrder.getMoney().add(returnOrder.getShippingFee());
		// 是否为主订单
		Boolean isMain = orderInfo.isMainOrder(order);
		// 如果该订单为主订单则查询子订单
		List<OrderListInfoVo> subOrders = orderInfo.getChildOrders(order, isMain);
		List<String> cOrderSns = subOrders.stream().map(OrderListInfoVo::getOrderSn).collect(Collectors.toList());
		// 初始化订单sn数组
		List<String> sns = new ArrayList<String>(subOrders.size() + 1);
		sns.add(order.getOrderSn());
		if(subOrders.size() > 0) {
			sns.addAll(cOrderSns);
		}
		//优先级退款数据汇总
		LinkedHashMap<String, BigDecimal> returnAmountMap = refundAmountRecord.getReturnAmountMap(sns);
		for (Entry<String, BigDecimal> entry : returnAmountMap.entrySet()) {
			//当前优先级名称
			String key = entry.getKey();
			//有补款支付才退
			if(key.equals(orderInfo.PS_BK_ORDER_MONEY) && BigDecimalUtil.compareTo(order.getBkOrderMoney(), BigDecimal.ZERO) < 1) {
				continue;
			}
			//当前优先级已退款金额
			BigDecimal value = entry.getValue();
			BigDecimal keyCanReturn = FieldsUtil.getFieldValueByFieldName(key,order,BigDecimal.class).subtract(value);
			if(BigDecimalUtil.compareTo(keyCanReturn, BigDecimal.ZERO) < 1) {
				//跳出可退<=0
				continue;
			}
			//此优先级此次退款金额
			BigDecimal currentReturn;
			if(BigDecimalUtil.compareTo(returnAmount, keyCanReturn) < 0 ) {
				currentReturn = returnAmount;
				returnAmount = BigDecimal.ZERO;
			}else {
				currentReturn = keyCanReturn;
				returnAmount = returnAmount.subtract(keyCanReturn);
			}
			
			boolean result = returnMethod.refundMethods(entry.getKey(), order, returnOrder, currentReturn);
			if(!result) {
				logger.error("优先级退款调用refundMethods失败,orderSn:"+order.getOrderSn()+",retId:"+returnOrder.getRetId()+",优先级为："+key);
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_ING_RETURNMETHOD_ERROR);
			}
			//微信退款后续处理标识
			if(key.equals(orderInfo.PS_MONEY_PAID)){
				flag = true;
			}
			if(returnAmount.compareTo(BigDecimal.ZERO) < 1) {
				//此次退款金额已经退完
				break;
			}
		}
		if(returnAmount.compareTo(BigDecimal.ZERO) > 0) {
			 logger.error("优先级退款完成后本次退款金额>0,orderSn:"+order.getOrderSn()+",retId:"+returnOrder.getRetId());
			 throw new MpException(JsonResultCode.CODE_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO);
		}
		return flag;
	}
	
	/**
	 * 	计算退款商品行退款金额
	 * @param returnOrder
	 * @param returnGoods
	 * @throws MpException 
	 */
	public void calculateGoodsReturnMoney(ReturnOrderRecord returnOrder,ReturnGoods[] returnGoods) throws MpException {
		//手动退款金额map
		Map<Integer, BigDecimal> returnMoneyMap = null;
		if(OrderConstant.RT_MANUAL == returnOrder.getReturnType()) {
			returnMoneyMap = new HashMap<Integer, BigDecimal>(returnGoods.length);
			BigDecimal sum = BigDecimal.ZERO;
			for (ReturnGoods goods : returnGoods) {
				returnMoneyMap.put(goods.getRecId(), goods.getMoney());
				sum = sum.add(goods.getMoney());
			}
			if(BigDecimalUtil.compareTo(returnOrder.getMoney(), sum) != 0) {
				throw new MpException(JsonResultCode.CODE_ORDER_MANUAL_INCONSISTENT_AMOUNT);
			}
		}
		//此次退款商品
		List<ReturnOrderGoodsRecord> currentReturnGoods = returnOrderGoods.getReturnGoods(returnOrder.getOrderSn(),returnOrder.getRetId());
		//获取此次退款商品可退最大金额
		BigDecimal totalCanReturnMoney = getTotalCanReturnMoney(currentReturnGoods);
		Iterator<ReturnOrderGoodsRecord> iterator = currentReturnGoods.iterator();
		while (iterator.hasNext()) {
			ReturnOrderGoodsRecord goods = (ReturnOrderGoodsRecord) iterator.next();
			BigDecimal currentGoodsReturnMoney = null;
			if(OrderConstant.RT_MANUAL == returnOrder.getReturnType()) {
				//因为数据库默认金额为0.00，所以退款金额大于0时更新数据
				if(BigDecimalUtil.compareTo(returnMoneyMap.get(goods.getRecId()), BigDecimal.ZERO) < 1) {
					iterator.remove();
				}else {
					currentGoodsReturnMoney = returnMoneyMap.get(goods.getRecId());
				}
			}else {
				//totalCanReturnMoney=0存在，eg赠品,除数不为零
				if(BigDecimalUtil.compareTo(totalCanReturnMoney, null) > 0) {
					//非手工退款商品退款金额(先乘后除)=订单退款金额*折后单价*退款数量/totalCanReturnMoney(!=0)
					currentGoodsReturnMoney = BigDecimalUtil.multiplyOrDivide(
							BigDecimalPlus.create(returnOrder.getMoney(),Operator.multiply),
							BigDecimalPlus.create(goods.getDiscountedGoodsPrice(),Operator.multiply),
							BigDecimalPlus.create(BigDecimal.valueOf(goods.getGoodsNumber()),Operator.Divide),
							BigDecimalPlus.create(totalCanReturnMoney,null));
				}
			}
			if(BigDecimalUtil.compareTo(currentGoodsReturnMoney, null) < 1) {
				//此次退款金额为0不进行更新
				iterator.remove();
			}else {
				goods.setReturnMoney(currentGoodsReturnMoney);
			}
		}
		db().batchUpdate(currentReturnGoods).execute();
	}
	
	/**
	 * 	退款商品可退最大金额
	 * @param returnGoods
	 * @return
	 */
	public BigDecimal getTotalCanReturnMoney(List<ReturnOrderGoodsRecord> returnGoods){
		BigDecimal[] sum = {BigDecimal.ZERO};
		returnGoods.forEach(goods->{
			sum[0].add(BigDecimalUtil.multiplyOrDivide(
					BigDecimalPlus.create(goods.getDiscountedGoodsPrice(),Operator.multiply),
					BigDecimalPlus.create(BigDecimal.valueOf(goods.getGoodsNumber()),null)
					));
		});
		return sum[0];
	}
	/**
	 * 
	 * 退款完成变更相关状态 
	 * @throws MpException 
	 */
	public boolean finishRefundChange(OrderInfoVo order , ReturnOrderRecord returnOrderRecord , RefundParam param) throws MpException{
		
		Result<ReturnOrderGoodsRecord> returnGoods = returnOrderGoods.getReturnGoods(returnOrderRecord.getOrderSn(),returnOrderRecord.getRetId());
		List<String> goodsType = Arrays.asList(order.getGoodsType().split(","));
		//非货到付款 非拼团抽奖
		if(!OrderConstant.PAY_CODE_COD.equals(order.getPayCode()) && !goodsType.contains(Byte.toString(OrderConstant.GOODS_TYPE_GROUP_DRAW))) {
			//修改库存-销量
			updateStockAndSales(returnGoods,order,goodsType);
		}
		//退款退货订单完成更新
		returnOrder.finishReturn(returnOrderRecord);
		//更新ReturnOrderGoods-success为2
		returnOrderGoods.finishReturn(returnGoods, OrderConstant.SUCCESS_COMPLETE);
		//更新orderGoods表
		orderGoods.finishReturn(order.getOrderSn(), returnGoods, returnOrderRecord);
		//可退款退货商品数量是否为0(有状态依赖于ordergoods表的商品数量与已经退货退款数量)
		boolean canReturnGoodsNumber = orderGoods.canReturnGoodsNumber(order.getOrderSn());
		//更新orderinfo主表信息
		orderInfo.updateOrderInfoInReturn(returnOrderRecord,order,canReturnGoodsNumber);
		//TODO 拆单逻辑特殊处理
		
		//部分发货退款完成,检查需要设置状态为已发货
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && order.getPartShipFlag() == OrderConstant.PART_SHIP) {
			//TODO 目前发货只支持按商品行发货，若支持数量发货需要修改
			if(!orderGoods.isCanDeliverOrder(order.getOrderSn())) {
				orderInfo.setOrderstatus(order.getOrderSn(),OrderConstant.ORDER_SHIPPED);
			}
		}
		//TODO 做到了这里
		//返利金额重新计算
		// 发送退款成功模板消息
		// 自动同步订单微信购物单
		//添加记录-----不用都放到这里
		return true;
	}
	
	/**
	 * 	更新库存和销量
	 * @param returnGoods
	 * @param order
	 * @param goodsType
	 */
	public void updateStockAndSales(Result<ReturnOrderGoodsRecord> returnGoods , OrderInfoVo order , List<String> goodsType) {
		//TODO 对接pos erp未完成
		if(OrderConstant.DELIVER_TYPE_SELF == order.getDeliverType()) {
			//自提订单不修改库存 销量
			return;
		}
		List<Integer> goodsIds = returnGoods.stream().map(ReturnOrderGoodsRecord::getGoodsId).collect(Collectors.toList());
		List<Integer> proIds = returnGoods.stream().map(ReturnOrderGoodsRecord::getProductId).collect(Collectors.toList());
		//查询规格
		Map<Integer, GoodsSpecProductRecord> products = null;
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY) {
			//待发货涉及恢复规格库存
			products = goodsSpecProduct.selectSpecByProIds(proIds);
		}
		//更新规格数组
		ArrayList<GoodsSpecProductRecord> updateProducts = new ArrayList<GoodsSpecProductRecord>(products.size());
		//查询商品
		Map<Integer, GoodsRecord> normalGoods = goods.getGoodsByIds(goodsIds);
		//更新商品数组
		ArrayList<GoodsRecord> updateNormalGoods = new ArrayList<GoodsRecord>(normalGoods.size());
		for (ReturnOrderGoodsRecord rGoods : returnGoods) {
			//待发货
			if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY) {
				//待发货+规格库存
				GoodsSpecProductRecord product = products.get(rGoods.getProductId());
				product.setPrdNumber(product.getPrdNumber() + rGoods.getGoodsNumber());
				//规格库存加入更新数组
				updateProducts.add(product);
				//待发货+商品库存
				GoodsRecord goods = normalGoods.get(rGoods.getGoodsId());
				goods.setGoodsNumber(goods.getGoodsNumber() + rGoods.getGoodsNumber());
			}
			//销量修改
			GoodsRecord goods = normalGoods.get(rGoods.getGoodsId());
			goods.setGoodsSaleNum(goods.getGoodsSaleNum() + rGoods.getGoodsNumber());
			updateNormalGoods.add(goods);
			//订单类型为拼团 且存在拼团id
			if(goodsType.contains(Byte.toString(OrderConstant.GOODS_TYPE_PIN_GROUP)) && order.getActivityId() != null) {
				//TODO 拼团修改库存和销量
			}
		}
		db().batchUpdate(updateProducts);
		db().batchUpdate(updateNormalGoods);
	}
	
	
	
}
