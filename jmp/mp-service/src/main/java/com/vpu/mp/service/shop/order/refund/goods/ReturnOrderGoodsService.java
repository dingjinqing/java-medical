package com.vpu.mp.service.shop.order.refund.goods;

import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnOrderGoods;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam.ReturnGoods;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;

/**
 * Table:return_order_goods
 * @author 王帅
 *
 */
@Service
public class ReturnOrderGoodsService extends ShopBaseService{
	
	public final ReturnOrderGoods TABLE = RETURN_ORDER_GOODS;
	
	/**
	 * 	通过订单sn[]查询其下退货商品信息
	 * @param arrayToSearch 
	 * @return  Result<Record>
	 */
	public Result<Record> getByOrderSn(String... arrayToSearch) {
		Result<Record> goods = db().select(TABLE.asterisk()).from(TABLE)
				.where(TABLE.ORDER_SN.in(arrayToSearch))
				.orderBy(TABLE.ID)
				.fetch();
		return goods;	
	}
	
	public Result<ReturnOrderGoodsRecord> getReturnGoods(String orderSn,Integer retId) {
		 return db().selectFrom(TABLE).where(TABLE.ORDER_SN.eq(orderSn).and(TABLE.RET_ID.eq(retId))).fetch();
	}
	
	public Result<Record> getRefundGoodsByStatus(String orderSn, Byte... successStatus) {
		// 查询退货中信息
		return db().select(RETURN_ORDER_GOODS.asterisk()).from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn), RETURN_ORDER_GOODS.SUCCESS.in(successStatus))
				.fetch();
	}
	
	/**
	 * 通过订单orderSn查询不同退款退货状态的商品数量
	 * @param orderSn
	 * @return Map<Integer(规格号), Integer(数量)>
	 */
	public Map<Integer, Integer> getRefundingGoodsNumberMap(String orderSn , Byte... successStatus) {
		List<OrderReturnGoodsVo> returnOrderGoods = getRefundGoodsByStatus(orderSn, successStatus).into(OrderReturnGoodsVo.class);
		//构造Map<Integer(子订单规格号), Integer(数量)>
		HashMap<Integer, Integer> productNum = new HashMap<Integer, Integer>();
		for (OrderReturnGoodsVo goods : returnOrderGoods) {
			if(productNum.get(goods.getRecId()) == null) {
				//第一次初始化该规格商品数量
				productNum.put(goods.getRecId(), goods.getGoodsNumber());
			}else {
				//第二次数量相加
				productNum.put(goods.getRecId(), productNum.get(goods.getRecId()) + goods.getGoodsNumber());
			}
		}
		return productNum;
	}
	
	/**
	 * 更新Success字段
	 * 
	 * @param returnGoods
	 * @param successStatus
	 */
	public void updateSuccess(List<ReturnOrderGoodsRecord> returnGoods, byte successStatus) {
		returnGoods.forEach(rGoods -> {
			rGoods.setSuccess(successStatus);
		});
		db().batchUpdate(returnGoods).execute();
	}
	
	/**
	 * 获取已退商品的退款金额
	 * @param orderSn
	 * @return
	 */
	public HashMap<Integer, BigDecimal> getManualReturnMoney(String orderSn) {
		Map<Integer, List<OrderReturnGoodsVo>> returnGoods = getRefundGoodsByStatus(orderSn, OrderConstant.SUCCESS_COMPLETE).intoGroups(TABLE.REC_ID,OrderReturnGoodsVo.class);
		//构造Map<Integer(子订单规格号), Integer(数量)>
		HashMap<Integer, BigDecimal> result = new HashMap<Integer, BigDecimal>(returnGoods.size());
		for (Entry<Integer, List<OrderReturnGoodsVo>> entry : returnGoods.entrySet()) {
			result.put(entry.getKey(), entry.getValue().stream().map(OrderReturnGoodsVo::getReturnMoney).reduce(BigDecimal.ZERO,BigDecimal::add));
		}
		return result;
	}
	
	/**
	 * 添加退货退款商品信息
	 * @param param 参数
	 * @param returnOrder 退款订单
	 * @param check 调用查询退款信息接口得到可退款信息
	 */
	public List<ReturnOrderGoodsRecord> add(RefundParam param , ReturnOrderRecord returnOrder , RefundVo check) {
		//构造商品list
		List<ReturnOrderGoodsRecord> batchList = new ArrayList<ReturnOrderGoodsRecord>(param.getReturnGoods().size());
		//可退商品信息转map方便获取
		Map<Integer, RefundVoGoods> cbsMap = check.getRefundGoods().stream().collect(Collectors.toMap(RefundVoGoods::getRecId, Function.identity()));
		for (ReturnGoods paramGoods : param.getReturnGoods()) {
			RefundVoGoods goods = cbsMap.get(paramGoods.getRecId());
			ReturnOrderGoodsRecord returnGoods = db().newRecord(TABLE);
			returnGoods.setShopId(getShopId());
			returnGoods.setRecId(paramGoods.getRecId());
			returnGoods.setRetId(returnOrder.getRetId());
			returnGoods.setOrderSn(param.getOrderSn());
			returnGoods.setGoodsId(goods.getGoodsId());
			returnGoods.setGoodsName(goods.getGoodsName());
			returnGoods.setProductId(goods.getProductId());
			//TODO 拆单主订单没有特殊处理，如需去除子单取goods.getTotal()
			returnGoods.setGoodsNumber(goods.getGoodsNumber().shortValue());
			returnGoods.setMarketPrice(goods.getMarketPrice());
			returnGoods.setGoodsPrice(goods.getGoodsPrice());
			returnGoods.setGoodsAttr(goods.getGoodsAttr());
			returnGoods.setSendNumber(goods.getSendNumber());
			returnGoods.setGoodsImg(goods.getGoodsImg());
			returnGoods.setDiscountedGoodsPrice(goods.getDiscountedGoodsPrice());
			batchList.add(returnGoods);
		}
		db().batchInsert(batchList).execute();
		return batchList;
	}
	
	/**
	 * 通过退款订单状态更新退款商品状态
	 * @param orderStatus
	 * @param returnGoods
	 */
	public void updateSucess(Byte orderStatus , List<ReturnOrderGoodsRecord> returnGoods){
		Byte success = null;
		//若取消订单需更新returnOrderGoods的success状态
		if(orderStatus == OrderConstant.REFUND_STATUS_CLOSE) {
			success = OrderConstant.SUCCESS_REVOKE;
		}
		//若拒绝订单需更新returnOrderGoods的success状态
		if(orderStatus == OrderConstant.REFUND_STATUS_REFUSE) {
			success = OrderConstant.SUCCESS_REFUSE;
		}
		//若拒绝订单需 更新returnOrderGoods的success状态
		if(orderStatus == OrderConstant.REFUND_STATUS_FINISH) {
			success = OrderConstant.SUCCESS_COMPLETE;
		}
		
		if(success != null) {
			updateSuccess(returnGoods, success);
		}
	}
	
	/**
	 * 	退款商品可退最大金额
	 * @param returnGoods
	 * @return
	 */
	public BigDecimal getTotalCanReturnMoney(List<ReturnOrderGoodsRecord> returnGoods){
		BigDecimal[] sum = {BigDecimal.ZERO};
		returnGoods.forEach(goods->{
			sum[0] = sum[0].add(BigDecimalUtil.multiplyOrDivide(
					BigDecimalPlus.create(goods.getDiscountedGoodsPrice(),Operator.multiply),
					BigDecimalPlus.create(BigDecimal.valueOf(goods.getGoodsNumber()),null)
					));
		});
		return sum[0];
	}
	
	/**
	 * 	计算退款商品行退款金额
	 * @param returnOrder
	 * @param returnGoods 前端传的退货商品
	 * @throws MpException 
	 */
	public void calculateGoodsReturnMoney(ReturnOrderRecord returnOrder,List<ReturnGoods> returnGoods) throws MpException {
		//手动退款金额map
		Map<Integer, BigDecimal> returnMoneyMap = null;
		//手工退款
		if(OrderConstant.RT_MANUAL == returnOrder.getReturnType()) {
			returnMoneyMap = new HashMap<Integer, BigDecimal>(returnGoods.size());
			for (ReturnGoods goods : returnGoods) {
				returnMoneyMap.put(goods.getRecId(), goods.getMoney());
			}
		}
		//此次退款商品
		List<ReturnOrderGoodsRecord> currentReturnGoods = getReturnGoods(returnOrder.getOrderSn(),returnOrder.getRetId());
		//获取此次退款商品可退最大金额
		BigDecimal totalCanReturnMoney = getTotalCanReturnMoney(currentReturnGoods);
		Iterator<ReturnOrderGoodsRecord> iterator = currentReturnGoods.iterator();
		while (iterator.hasNext()) {
			ReturnOrderGoodsRecord goods = iterator.next();
			BigDecimal currentGoodsReturnMoney = null;
			if(OrderConstant.RT_MANUAL == returnOrder.getReturnType()) {
				//因为数据库默认金额为0.00，所以退款金额大于0时更新数据
				if(BigDecimalUtil.compareTo(returnMoneyMap.get(goods.getRecId()), BigDecimal.ZERO) < 1) {
					iterator.remove();
					continue;
				}else {
					currentGoodsReturnMoney = returnMoneyMap.get(goods.getRecId());
				}
			}else {
				//totalCanReturnMoney=0存在，eg赠品,除数不为零
				if(BigDecimalUtil.compareTo(totalCanReturnMoney, null) > 0 ) {
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
		//权重分摊校验与差额分摊,此时currentReturnGoods已经去除金额为0的
		balanceRefundMoney(currentReturnGoods, returnOrder.getMoney());
		db().batchUpdate(currentReturnGoods).execute();
	}
	
	/**
	 * 平衡退款商品行的退款金额
	 * @param returnGoods
	 * @param sum
	 */
	public void balanceRefundMoney(List<ReturnOrderGoodsRecord> returnGoods, BigDecimal sum){
		//只有0、1件商品return
		if(returnGoods.size() <= 1) {
			return;
		}
		//商品行所退金额和
		BigDecimal goodsSum = returnGoods.stream().map(ReturnOrderGoodsRecord::getReturnMoney).reduce(BigDecimal.ZERO , BigDecimal::add);
		//无差额return
		if(BigDecimalUtil.compareTo(goodsSum, sum) == 0) {
			return;
		}
		//差额
		BigDecimalPlus difference = BigDecimalPlus.create(BigDecimalUtil.subtrac(sum, goodsSum).abs() , null);
		//差额为正还是负
		if(BigDecimalUtil.compareTo(goodsSum, sum) < 0) {
			//商品分配金额少
			difference.setOperator(Operator.add);
		}else {
			//商品分配金额多
			difference.setOperator(Operator.subtrac);
		}
		/**
		 * 误差可能的最大值 = 商品行数 * 0.01元
		 * so: 误差 / 0.01 = 份数;
		 */
		int num = BigDecimalUtil.divide(difference.getValue(), OrderConstant.CENT).intValue();
		while(true) {
			for (ReturnOrderGoodsRecord oneGoods : returnGoods) {
				//最多可退
				BigDecimal returnMax = BigDecimalUtil.multiply(oneGoods.getDiscountedGoodsPrice() , new BigDecimal(oneGoods.getGoodsNumber()));
				if(BigDecimalUtil.compareTo(oneGoods.getReturnMoney() , returnMax) < 1) {
					
				}
				//当前商品假如加上（减去）一分的退款金额
				BigDecimal future = BigDecimalUtil.addOrSubtrac(
						BigDecimalPlus.create(oneGoods.getReturnMoney(), difference.getOperator()),
						BigDecimalPlus.create(OrderConstant.CENT, null)
						);
				//是否超限()
				if(BigDecimalUtil.compareTo(future, returnMax) < 1) {
					//不超限，设置future
					oneGoods.setReturnMoney(future);
					//分数-1
					--num;
				}
				//差额
				if(num == 0) {
					break;
				}
			}
			if(num == 0) {
				break;
			}
		}
	}
}

















