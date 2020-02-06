package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnBusinessAddressParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam.ReturnGoods;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;
import com.vpu.mp.service.shop.activity.factory.OrderCreateMpProcessorFactory;
import com.vpu.mp.service.shop.config.ShopReturnConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.record.ReturnStatusChangeService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
/**
 * 
 * @author 王帅
 *
 */
@Component
public class ReturnService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, RefundParam> {

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
	@Autowired
	private ReturnStatusChangeService returnStatusChange;
	@Autowired
	public RecordAdminActionService record;
    @Autowired
    public ShopReturnConfigService shopReturncfg;
    @Autowired
    public OrderCreateMpProcessorFactory orderCreateMpProcessorFactory;

	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.RETURN;
	}
	
	/**
	 * 退款、退货查询
	 * @param param param
	 * @return ExecuteResult RefundVo
	 * @throws MpException 见代码
	 */
	@Override
	public ExecuteResult execute(RefundParam param) {
        logger.info("退款退货执行start(ReturnService)");
        //校验
        if(!Byte.valueOf(OrderConstant.RETURN_OPERATE_MP_REVOKE).equals(param.getReturnOperate()) &&
            !Byte.valueOf(OrderConstant.RETURN_OPERATE_MP_SUBMIT_SHIPPING).equals(param.getReturnOperate()) &&
            !Byte.valueOf(OrderConstant.RETURN_OPERATE_ADMIN_REFUSE).equals(param.getReturnOperate()) &&
            !Byte.valueOf(OrderConstant.RETURN_OPERATE_ADMIN_REFUSE_RETURN_GOODS_APPLY).equals(param.getReturnOperate())){
           //非提交物流、非撤销校验
            if(param.getReturnMoney() == null){
                ExecuteResult.create(JsonResultCode.CODE_ORDER_RETURN_NOT_NULL_RETURNMONEY, null);
            }
            if(param.getShippingFee() == null){
                ExecuteResult.create(JsonResultCode.CODE_ORDER_RETURN_NOT_NULL_SHIPPINGFEE, null);
            }
        }
		//获取订单详情
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		if(order == null) {
            logger.error("退款退货执行异常，订单不存在");
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
		}
		//result
        ExecuteResult result = ExecuteResult.create();
        try {
			transaction(()->{
				try {
					//退款订单
					ReturnOrderRecord rOrder = null;
					//退款商品
					List<ReturnOrderGoodsRecord> returnGoods = null;
					if(param.getRetId() != null) {
						rOrder = returnOrder.getByRetId(param.getRetId());
						if(rOrder == null) {
                            logger.error("退款退货执行异常，退订单不存在");
							throw new MpException(JsonResultCode.CODE_ORDER_NOT_EXIST);
						}
					}
					if(rOrder == null) {
						//订单状态记录
						orderAction.addRecord(order, param, order.getOrderStatus() , "保存"+OrderConstant.RETURN_TYPE_CN[param.getReturnType()]+"之前订单状态");
						//if仅退运费 else非仅退运费
						if(OrderConstant.RT_ONLY_SHIPPING_FEE == param.getReturnType()) {
							//生成退款订单
							rOrder = returnShippingFee(param,order);
						} else {
							//通过退款查询获取可退信息
							RefundVo check = (RefundVo)query(param);
							//校验  生成退款订单
							rOrder = notOnlyReturnShippingFee(param , order , check);
							//生成退款商品
							returnGoods = returnOrderGoods.add(param, rOrder, check);
							//更新orderGoods表
							orderGoods.updateInReturn(order.getOrderSn(), returnGoods, rOrder);
						}
						// 更新订单信息
						orderInfo.updateInReturn(rOrder, null, null);
						//退款订单记录
						returnStatusChange.addRecord(rOrder, param.getIsMp(), "生成退款退货订单信息："+OrderConstant.RETURN_TYPE_CN[param.getReturnType()]);
					    //返回退款订单号
                        result.setResult(rOrder.getReturnOrderSn());
					}
					//退款商品为空则初始化
					if(CollectionUtils.isEmpty(returnGoods)) {
                        logger.info("退款商品为空则初始化");
						returnGoods = returnOrderGoods.getReturnGoods(order.getOrderSn(), rOrder.getRetId());	
					}
					/**
					 * 买家发起：
					 * 		不需走退款逻辑：0退货提交物流、1撤销退款
					 * 商家发起：
					 * 		不需走退款逻辑：2拒绝仅退款请求与买家提交物流商家拒绝退款、3同意退货请求、4拒绝退货申请、
					 * 		走退款逻辑（param.returnOperate == null）：商家同意买家退款、商家确认收货并退款、后台手动退款
                     * 自动任务：
                     *      买家发起仅退款申请后，商家在returnMoneyDays日内未处理，系统将自动退款。
                     *      商家已发货，买家发起退款退货申请，商家在 returnAddressDays日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收获地址
                     *      买家已提交物流信息，商家在 returnShoppingDays 日内未处理，系统将默认同意退款退货，并自动退款给买家
                     *      商家同意退款退货，买家在 returnPassDays 日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
                     *
					 */
					if(param.getReturnOperate() != null) {
						//响应订单操作
						returnOrder.responseReturnOperate(param, rOrder);
						//订单状态记录
						orderAction.addRecord(order, param, order.getOrderStatus() , "保存订单操作之前状态，"+OrderConstant.RETURN_TYPE_CN[param.getReturnType()]+"_"+OrderConstant.RETURN_OPERATE[param.getReturnOperate()]+"之前订单状态");
						// 更新订单信息
						orderInfo.updateInReturn(rOrder, null, null);
						//更新orderGoods表
						orderGoods.updateInReturn(order.getOrderSn(), returnGoods , rOrder);
						//更新退款商品行success状态
						returnOrderGoods.updateSucess(rOrder.getRefundStatus(),returnGoods);
						//退款订单记录
						returnStatusChange.addRecord(rOrder, param.getIsMp(), OrderConstant.RETURN_OPERATE[param.getReturnOperate()]);
						return;
					}
					if(param.getIsMp() == OrderConstant.IS_MP_Y) {
                        logger.info("买家操作完成");
						return;
					}
					//退款逻辑
					boolean isExecute = refundLogic(order, rOrder, BigDecimalUtil.compareTo(param.getReturnMoney(), null) > 0 ? param.getReturnMoney() : BigDecimal.ZERO, param);
					if(isExecute) {
						//需要执行 完成后更新信息
						finishUpdateInfo(order , rOrder , param);
					}
				} catch (MpException e) {
				    //TODO 处理异常状态，判断是否需要回滚
					throw new MpException(e.getErrorCode());
				} catch (DataAccessException e) {
					Throwable cause = e.getCause();
					if (cause instanceof MpException) {
						throw cause;
					} else {
						throw new MpException(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION, e.getMessage());
					}
				}
			});
		} catch (DataAccessException e) {
			Throwable cause = e.getCause();
			if (cause instanceof MpException) {
				return ExecuteResult.create(((MpException) cause).getErrorCode(), ((MpException) cause).getCodeParam());
			} else {
				return ExecuteResult.create(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION, e.getMessage());
			}
		} catch (Exception e) {
			logger.error("退款捕获mp异常", e);
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION, null);
		}
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_RETURN.code }), new String[] {param.getOrderSn()});
		return result;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		logger.info("获取可退款、退货信息参数为:" + param.toString());
		Byte isMp = param.getIsMp();
		RefundVo vo = new RefundVo();
		//获取当前订单
		OrderListInfoVo currentOrder = orderInfo.getByOrderId(param.getOrderId(),OrderListInfoVo.class);
		if(currentOrder == null) {
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		vo.setOrderType(OrderInfoService.orderTypeToArray(currentOrder.getGoodsType()));
		//退款校验
		if(OrderOperationJudgment.isReturnMoney(currentOrder, isMp)) {
			vo.getReturnType()[OrderConstant.RT_ONLY_MONEY] = true;
		}
		//退货校验
		if(OrderOperationJudgment.isReturnGoods(currentOrder , isMp)) {
			vo.getReturnType()[OrderConstant.RT_GOODS] = true;
		}
		//获取已退运费
		BigDecimal returnShipingFee = returnOrder.getReturnShippingFee(currentOrder.getOrderSn());
		//退运费校验
		if(OrderOperationJudgment.adminIsReturnShipingFee(currentOrder.getShippingFee(), returnShipingFee, true)){
			vo.getReturnType()[OrderConstant.RT_ONLY_SHIPPING_FEE] = true;
			//设置
			vo.setReturnShippingFee(currentOrder.getShippingFee().subtract(returnShipingFee));
		}else {
			vo.setReturnShippingFee(BigDecimal.ZERO);
		}
		//手动退款校验,已退金额<sum(已退R商品数量*折后单价)
		if((isMp != OrderConstant.IS_MP_Y)) {
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
					vo.getReturnType()[OrderConstant.RT_MANUAL] = true;
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
		LinkedHashMap<String, BigDecimal> returnAmountMap = refundAmountRecord.getReturnAmountMap(sns,null);	
		//构造优先级退款信息
		Map<String, BigDecimal> canReturn = orderInfo.getCanReturn(currentOrder , amount , returnAmountMap);
		//查询订单下商品(如果为主订单则包含子订单商品)
		Map<String, List<RefundVoGoods>> goods = orderGoods.getByOrderSns(sns);
		//计算子订单商品数量(主订单返回的map->size=0)
		HashMap<Integer, Integer> subOrderGoodsSum = orderGoods.countSubOrderGoods(goods,currentOrder,isMain);
		//查询该订单退货中的商品
		Map<Integer, Integer> refundingGoods = returnOrderGoods.getRefundingGoodsNumberMap(param.getOrderSn(),OrderConstant.SUCCESS_RETURNING);
		//设置可退商品行信息
		Iterator<RefundVoGoods> currentGoods = goods.get(currentOrder.getOrderSn()).iterator();
		//如果后台查询且满足手动退款则需查询已退金额
		Map<Integer, BigDecimal> manualReturnMoney = null;
		if((isMp != OrderConstant.IS_MP_Y) && vo.getReturnType()[OrderConstant.RT_MANUAL] == true) {
			manualReturnMoney = returnOrderGoods.getManualReturnMoney(param.getOrderSn());
		}
		while (currentGoods.hasNext()) {
			RefundVoGoods oneGoods = (RefundVoGoods) currentGoods.next();
			//主订单需要减去子订单的商品数量
			if(isMain) {
				//总数 = 总数 - 子订单数量
				oneGoods.setTotal((oneGoods.getGoodsNumber() - ((subOrderGoodsSum.get(oneGoods.getRecId())) == null ? 0 : subOrderGoodsSum.get(oneGoods.getRecId()))));
			}else {
				//总数 = 总数
				oneGoods.setTotal(oneGoods.getGoodsNumber());
			}
			//已提交=退中+退完成
			Integer submitted = (refundingGoods.get(oneGoods.getRecId()) == null ? 0 : refundingGoods.get(oneGoods.getRecId())) + oneGoods.getReturnNumber();
			oneGoods.setSubmitted(submitted);
			// 可退
			Integer returnable = oneGoods.getTotal() - oneGoods.getSubmitted();
			oneGoods.setReturnable(returnable);
			//处理前后端不同逻辑
			if(isMp == OrderConstant.IS_MP_Y) {
				if(oneGoods.getReturnable() <= 0) {
					//前端可退数量为0不展示
					currentGoods.remove();
					continue;
				}
			}else {
				//后台商家配置的不可退款的可退
				oneGoods.setIsCanReturn(OrderConstant.IS_CAN_RETURN_Y);
			}
			//如果后台查询且满足手动退款则需查询已退金额
			if(isMp != OrderConstant.IS_MP_Y && vo.getReturnType()[3] == true) {
				oneGoods.setReturnMoney(manualReturnMoney.get(oneGoods.getRecId()) == null ? BigDecimal.ZERO : manualReturnMoney.get(oneGoods.getRecId()));
			}
		}
		checkReturnType(goods.get(currentOrder.getOrderSn()) , vo.getReturnType());
		vo.setRefundGoods(goods.get(currentOrder.getOrderSn()));
		vo.setReturnAmountMap(canReturn);
		logger.info("获取可退货信息完成");
		return vo;
	}
	
	/**
	 * 	仅退运费生成退款订单及校验
	 * @param param
	 * @param order
	 * @return
	 * @throws MpException
	 */
	private ReturnOrderRecord returnShippingFee(RefundParam param , OrderInfoVo order) throws MpException{
		// 只退运费必须金额>0
		if (BigDecimalUtil.compareTo(param.getShippingFee(), BigDecimal.ZERO) < 1) {
			logger.error("订单sn:{}，仅退运费时退运费金额必须大于0",param.getOrderSn());
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_NOT_ZERO);
		}
		// 获取已退运费
		BigDecimal returnShipingFee = returnOrder.getReturnShippingFee(param.getOrderSn());
		if (!OrderOperationJudgment.adminIsReturnShipingFee(order.getShippingFee(), returnShipingFee.add(param.getShippingFee()), false)) {
			logger.error("订单sn:{}，该订单运费已经退完或超额",param.getOrderSn());
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS);
		}
		logger.info("退运费创建退款订单开始");
		// 增加退款/退货记录，形成货退款订单
		ReturnOrderRecord rOrder = returnOrder.addRecord(param, order, null);
		logger.info("退运费创建退款订单结束");
		return rOrder;
	}
	
	/**
	 * 退款方法集合
	 * @param order
	 * @param returnOrder
	 * @param currentMoney
	 * @param param 
	 * @return 是否需要后续处理状态改变（好友代付存在微信退款不需要）
	 * @throws MpException
	 */
	public boolean refundLogic(OrderInfoVo order , ReturnOrderRecord returnOrder , BigDecimal currentMoney , RefundParam param) throws MpException {
        logger.info("退款refundLogic start");
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
		boolean priorityRefund = priorityRefund(order,returnOrder);
		//计算退款商品行退款金额
		returnOrderGoods.calculateGoodsReturnMoney(returnOrder,param.getReturnGoods());
		//TODO 好友代付且存在微信退款队列处理
		if(OrderConstant.PAY_WAY_FRIEND_PAYMENT == order.getOrderPayWay() && priorityRefund) {
			return false;
		}
        logger.info("退款refundLogic end");
		return true;
	}
	
	/**
	 * 优先级退款
	 * @param order
	 * @param returnOrder
	 * @return 是否需要后续处理状态改变（好友代付存在微信退款不需要）
	 * @throws MpException
	 */
	public boolean priorityRefund(OrderInfoVo order, ReturnOrderRecord returnOrder) throws MpException {
        logger.info("优先级退款start");
		//是否微信退款（好友代付没有补款）
		boolean flag = true;
		//此次退款金额
		BigDecimal returnAmount = returnOrder.getMoney().add(returnOrder.getShippingFee());
        logger.info("此次退款金额{}",returnAmount);
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
		LinkedHashMap<String, BigDecimal> returnAmountMap = refundAmountRecord.getReturnAmountMap(sns,null);
		for (Entry<String, BigDecimal> entry : returnAmountMap.entrySet()) {
            logger.info("{}优先级退款", entry.getKey());
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
            logger.info("{}优先级退款执行", entry.getKey());
			boolean result = returnMethod.refundMethods(entry.getKey(), order, returnOrder.getRetId(), currentReturn);
			if(!result) {
				logger.error("优先级退款调用refundMethods失败,orderSn:{},retId:{},优先级为:{}",order.getOrderSn(),returnOrder.getRetId(),key);
				throw new MpException(JsonResultCode.CODE_ORDER_RETURNING_RETURN_METHOD_ERROR);
			}
			//微信退款后续处理标识
			if(key.equals(orderInfo.PS_MONEY_PAID)){
				flag = false;
			}
			if(returnAmount.compareTo(BigDecimal.ZERO) < 1) {
				//此次退款金额已经退完
				break;
			}
		}
		if(returnAmount.compareTo(BigDecimal.ZERO) > 0) {
			 logger.error("优先级退款完成后本次退款金额>0,orderSn:{},retId:{}",order.getOrderSn(),returnOrder.getRetId());
			 throw new MpException(JsonResultCode.CODE_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO);
		}
        logger.info("优先级退款end");
		return flag;
	}
	
	
	/**
	 * 退款完成变更相关信息
	 * @param order
	 * @param returnOrderRecord
	 * @param param
	 * @return
	 * @throws MpException
	 */
	public void finishUpdateInfo(OrderInfoVo order , ReturnOrderRecord returnOrderRecord , RefundParam param) throws MpException{
        logger.info("退款完成变更相关信息start");
        Result<ReturnOrderGoodsRecord> returnGoodsRecord = returnOrderGoods.getReturnGoods(returnOrderRecord.getOrderSn(),returnOrderRecord.getRetId());
        List<OrderReturnGoodsVo> returnGoods = returnGoodsRecord.into(OrderReturnGoodsVo.class);
        returnGoods.forEach(g->g.setIsGift(orderGoods.isGift(g.getRecId())));

        updateStockAndSales(order, returnGoods);
        //退款退货订单完成更新
		returnOrder.finishReturn(returnOrderRecord);
		//更新ReturnOrderGoods-success
		returnOrderGoods.updateSucess(returnOrderRecord.getRefundStatus(), returnGoodsRecord);
		//更新orderGoods表
		orderGoods.updateInReturn(order.getOrderSn(), returnGoodsRecord, returnOrderRecord);
		//可退款退货商品数量是否为0(有状态依赖于ordergoods表的商品数量与已经退货退款数量)
		boolean canReturnGoodsNumber = orderGoods.canReturnGoodsNumber(order.getOrderSn());
		//更新orderinfo主表信息
		orderInfo.updateInReturn(returnOrderRecord,order,canReturnGoodsNumber);
		//TODO 拆单逻辑特殊处理
		
		//部分发货退款完成,检查是否需要设置状态为已发货
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && order.getPartShipFlag() == OrderConstant.PART_SHIP && canReturnGoodsNumber) {
			//TODO 目前发货只支持按商品行发货，若支持数量发货需要修改
			if(!orderGoods.isCanDeliverOrder(order.getOrderSn())) {
				orderInfo.setOrderstatus(order.getOrderSn(),OrderConstant.ORDER_SHIPPED);
			}
		}
		//TODO 做到了这里
		//返利金额重新计算
		// 发送退款成功模板消息
		// 自动同步订单微信购物单
		//TODO
        returnStatusChange.addRecord(returnOrderRecord, param.getIsMp(), "当前退款订单正常结束："+OrderConstant.RETURN_TYPE_CN[param.getReturnType()]);
        logger.info("退款完成变更相关信息end");
	}

    private void updateStockAndSales(OrderInfoVo order, List<OrderReturnGoodsVo> returnGoods) {
        List<Byte> goodsType = Arrays.asList(OrderInfoService.orderTypeToByte(order.getGoodsType()));
        //非货到付款 非拼团抽奖
        if(!OrderConstant.PAY_CODE_COD.equals(order.getPayCode()) && !goodsType.contains(Byte.toString(BaseConstant.ACTIVITY_TYPE_GROUP_BUY))) {
            //修改商品库存-销量
            updateNormalStockAndSales(returnGoods,order);
        }
        //获取退款活动(goodsType.retainAll后最多会出现一个单一营销+赠品活动)
        goodsType.retainAll(OrderCreateMpProcessorFactory.RETURN_ACTIVITY);
        for (Byte type : goodsType) {
            if(BaseConstant.ACTIVITY_TYPE_GIFT.equals(type)){
                //赠品修改活动库存
                orderCreateMpProcessorFactory.processReturnOrder(BaseConstant.ACTIVITY_TYPE_GIFT,null,returnGoods.stream().filter(x->OrderConstant.IS_GIFT_Y.equals(x.getIsGift())).collect(Collectors.toList()));
            }else {
                //修改活动库存
                orderCreateMpProcessorFactory.processReturnOrder(type, order.getActivityId(), returnGoods.stream().filter(x->OrderConstant.IS_GIFT_N.equals(x.getIsGift())).collect(Collectors.toList()));
            }
        }
    }

    /**
	 * 	更新库存和销量
	 * @param returnGoods
	 * @param order
	 */
	public void updateNormalStockAndSales(List<OrderReturnGoodsVo> returnGoods , OrderInfoVo order) {
		//TODO 对接pos erp未完成
		
		List<Integer> goodsIds = returnGoods.stream().map(OrderReturnGoodsVo::getGoodsId).collect(Collectors.toList());
		List<Integer> proIds = returnGoods.stream().map(OrderReturnGoodsVo::getProductId).collect(Collectors.toList());
		//查询规格
		Map<Integer, GoodsSpecProductRecord> products = null;
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY) {
			//待发货涉及恢复规格库存
			products = goodsSpecProduct.selectSpecByProIds(proIds);
		}
		//更新规格数组
		ArrayList<GoodsSpecProductRecord> updateProducts = new ArrayList<GoodsSpecProductRecord>();
		//查询商品
		Map<Integer, GoodsRecord> normalGoods = goods.getGoodsByIds(goodsIds);
		//更新商品数组
		ArrayList<GoodsRecord> updateNormalGoods = new ArrayList<GoodsRecord>(normalGoods.size());
		for (OrderReturnGoodsVo rGoods : returnGoods) {
			if(rGoods.getGoodsNumber() == 0 ) {
				continue;
			}
			if(OrderConstant.DELIVER_TYPE_COURIER == order.getDeliverType()) {
				//待发货
				if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && products.get(rGoods.getProductId()) != null) {
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
                if(goods != null){
                    goods.setGoodsSaleNum(goods.getGoodsSaleNum() - rGoods.getGoodsNumber());
                    updateNormalGoods.add(goods);
                }
			}
			//订单类型为拼团 且存在拼团id
			/*if(goodsType.contains(Byte.toString(BaseConstant.ACTIVITY_TYPE_GROUP_BUY)) && order.getActivityId() != null) {
				//TODO 拼团修改库存和销量
			}
            //订单类型为秒杀 且存在秒杀id 且不是赠品行
            if(goodsType.contains(Byte.toString(BaseConstant.ACTIVITY_TYPE_SEC_KILL)) && order.getActivityId() != null && rGoods.getIsGift().equals(OrderConstant.IS_GIFT_N)) {
                saas.getShopApp(getShopId()).seckill.updateSeckillStock(order.getActivityId(),rGoods.getProductId(),- rGoods.getGoodsNumber());
            }
            //订单类型为砍价 且存在砍价id
            if(goodsType.contains(Byte.toString(BaseConstant.ACTIVITY_TYPE_BARGAIN)) && order.getActivityId() != null) {
                saas.getShopApp(getShopId()).bargain.updateBargainStock(order.getActivityId(),- rGoods.getGoodsNumber());
            }*/
		}
		if(updateProducts.size() > 0) {
			db().batchUpdate(updateProducts);
		}
		if(updateNormalGoods.size() > 0) {
			db().batchUpdate(updateNormalGoods);
		}
	}

	/**
	 * 	非仅退运费生成退款订单及校验
	 * @param param
	 * @param order
	 * @return
	 * @throws MpException
	 */
	private ReturnOrderRecord notOnlyReturnShippingFee(RefundParam param , OrderInfoVo order , RefundVo check) throws MpException{
		//通用是否支持该退款退货类型
		if(!check.getReturnType()[param.getReturnType()]) {
			logger.error("订单sn:{},{}时通用校验失败，不支持该类型退款退货",param.getOrderSn(),OrderConstant.RETURN_TYPE_CN[param.getReturnType()]);
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_NOT_SUPPORT_RETURN_TYPE);
		}
		//查询参数校验
		if(CollectionUtils.isEmpty(check.getRefundGoods())) {
			logger.error("订单sn:{},{}时，已无可退商品",param.getOrderSn(),OrderConstant.RETURN_TYPE_CN[param.getReturnType()]);
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_GOODS_RETURN_COMPLETED);
		}
		
		//输入商品简单校验
		if (CollectionUtils.isEmpty(param.getReturnGoods())) {
			logger.error("订单sn:{},{}时，未选择商品",param.getOrderSn(),OrderConstant.RETURN_TYPE_CN[param.getReturnType()]);
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_NO_SELECT_GOODS);
		}
		// 退运费判断
		if (BigDecimalUtil.compareTo(param.getShippingFee(), null) > 0 ) {
			BigDecimal returnShipingFee = returnOrder.getReturnShippingFee(param.getOrderSn());
			if(!OrderOperationJudgment.adminIsReturnShipingFee(order.getShippingFee(), returnShipingFee.add(param.getShippingFee()), false)) {
				logger.error("订单sn:{}，该订单运费已经退完或超额",param.getOrderSn());
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS);
			}
		}
		//手动退款商品行金额和与输入金额比较
		if(param.getReturnType() == OrderConstant.RT_MANUAL) {
			BigDecimal sum = param.getReturnGoods().stream().map(ReturnGoods::getMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
			if(BigDecimalUtil.compareTo(sum, param.getReturnMoney()) != 0) {
				logger.error("订单sn:{}，手动退款商品行金额和与输入金额不一致",param.getOrderSn());
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_MANUAL_INCONSISTENT_AMOUNT);
			}
		}
		//构造退款订单及复杂校验
		ReturnOrderRecord rOrder = returnOrder.checkAndCreateOrder(param, order, check);
		return rOrder;
	}
	/**
	 * 根据实际退款数量确定是否支持退款类型（退款、退货）
	 * @param refundVoGoods
	 * @param returnTypes
	 */
	public void checkReturnType(List<RefundVoGoods> refundVoGoods , boolean[] returnTypes) {
		if(returnTypes[0] || returnTypes[1]) {
			if(refundVoGoods.stream().mapToInt(RefundVoGoods::getReturnNumber).sum() == refundVoGoods.stream().mapToInt(RefundVoGoods::getTotal).sum()) {
				returnTypes[0] = false;
				returnTypes[1] = false;
			}
		}
	}

    /**
     * 自动处理退货退款订单
     */
    public void autoReturnOrder(){
        if(OrderConstant.YES == shopReturncfg.getAutoReturn()){
            //自动退款退货设置开关开启时间
            Timestamp autoReturnTime = shopReturncfg.getAutoReturnTime();
            //买家发起仅退款申请后，商家在returnMoneyDays日内未处理，系统将自动退款。
            Byte returnMoneyDays = shopReturncfg.getReturnMoneyDays();
            //商家已发货，买家发起退款退货申请，商家在 returnAddressDays日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收获地址
            Byte returnAddressDays = shopReturncfg.getReturnAddressDays();
            //买家已提交物流信息，商家在 returnShoppingDays 日内未处理，系统将默认同意退款退货，并自动退款给买家
            Byte returnShoppingDays = shopReturncfg.getReturnShippingDays();
            //商家同意退款退货，买家在 returnPassDays 日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
            Byte returnPassDays = shopReturncfg.getReturnPassDays();
            Result<ReturnOrderRecord> autoReturnOrder = returnOrder.getAutoReturnOrder(autoReturnTime, returnMoneyDays, returnAddressDays, returnShoppingDays, returnPassDays);
            autoReturnOrder.forEach(order->{
                RefundParam param = new RefundParam();
                param.setAction(Integer.valueOf(OrderServiceCode.RETURN.ordinal()).byteValue());
                param.setIsMp(OrderConstant.IS_MP_AUTO);
                param.setOrderId(order.getOrderId());
                param.setOrderSn(order.getOrderSn());
                param.setRetId(order.getRetId());
                param.setReturnType(order.getReturnType());
                param.setReturnMoney(order.getMoney());
                param.setShippingFee(order.getShippingFee());
                if(order.getRefundStatus().equals(OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING) && order.getReturnType().equals(OrderConstant.RT_ONLY_MONEY)) {
                    //买家发起退款申请后，商家在 returnMoneyDays 日内未处理，系统将自动退款
                    param.setReturnOperate(null);
                }else if(order.getRefundStatus().equals(OrderConstant.REFUND_STATUS_AUDITING) && (order.getReturnType().equals(OrderConstant.RT_GOODS) || order.getReturnType().equals(OrderConstant.RT_CHANGE))) {
                    //商家已发货，买家发起退款退货申请，商家在 ? 日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收获地址
                    param.setReturnOperate(OrderConstant.RETURN_OPERATE_ADMIN_AGREE_RETURN);
                    ReturnBusinessAddressParam defaultAddress = shopReturncfg.getDefaultAddress();
                    if(defaultAddress != null) {
                        param.setConsignee(defaultAddress.getConsignee());
                        param.setReturnAddress(defaultAddress.getReturnAddress());
                        param.setMerchantTelephone(defaultAddress.getMerchantTelephone());
                        param.setZipCode(defaultAddress.getZipCode());
                    }
                }else if(order.getRefundStatus().equals(OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING) && order.getReturnType().equals(OrderConstant.RT_GOODS)) {
                    //买家已提交物流信息，商家在 ? 日内未处理，系统将默认同意退款退货，并自动退款给买家
                    param.setReturnOperate(null);
                }else if(order.getRefundStatus().equals(OrderConstant.REFUND_STATUS_AUDIT_PASS) && order.getReturnType().equals(OrderConstant.RT_GOODS)) {
                    //商家同意退款退货，买家在 ? 日内未提交物流信息，且商家未确认收货并退款，退款申请将自动撤销。
                    param.setReturnOperate(OrderConstant.RETURN_OPERATE_MP_REVOKE);
                }
                ExecuteResult result = execute(param);
                if(result == null || result.isSuccess()) {
                    logger().info("订单自动任务,完成成功,orderSn:{}", order.getOrderSn());
                }else {
                    logger().error("订单自动任务,完成失败,orderSn:{},错误信息{}{}", order.getOrderSn(), result.getErrorCode().toString() , result.getErrorParam());
                }
            });
        }
    }
}
