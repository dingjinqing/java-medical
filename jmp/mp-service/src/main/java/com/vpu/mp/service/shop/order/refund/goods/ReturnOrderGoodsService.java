package com.vpu.mp.service.shop.order.refund.goods;

import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnOrderGoods;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;

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
	
	/**
	 * 	通过订单orderSn查询不同退款退货状态的商品数量
	 * @param orderSn
	 * @return Map<Integer(规格号), Integer(数量)>
	 */
	public Map<Integer, Integer> getRefundingGoods(String orderSn , byte successStatus) {
		// 查询退货中信息
		List<OrderReturnGoodsVo> returnOrderGoods = db().select(RETURN_ORDER_GOODS.asterisk())
				.from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn),
						RETURN_ORDER_GOODS.SUCCESS.eq(successStatus))
				.fetchInto(OrderReturnGoodsVo.class);
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
	 * @param returnGoods
	 * @param successStatus
	 */
	public void finishReturn(Result<ReturnOrderGoodsRecord> returnGoods , byte successStatus){
		returnGoods.forEach(rGoods->{rGoods.setSuccess(successStatus);});
		db().batchUpdate(returnGoods).execute();
	}
}
