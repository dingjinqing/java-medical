package com.vpu.mp.service.shop.order.refund.goods;

import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.vpu.mp.service.foundation.service.ShopBaseService;
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
			if(productNum.get(goods.getProductId()) == null) {
				//第一次初始化该规格商品数量
				productNum.put(goods.getProductId(), goods.getGoodsNumber());
			}else {
				//第二次数量相加
				productNum.put(goods.getProductId(), productNum.get(goods.getProductId()) + goods.getGoodsNumber());
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
	public void updateSucessByRefundStatus(Byte orderStatus , List<ReturnOrderGoodsRecord> returnGoods){
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
}
