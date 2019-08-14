package com.vpu.mp.service.shop.order.goods;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;

/**
 * Table:TABLE
 * @author 王帅
 *
 */
@Service
public class OrderGoodsService extends ShopBaseService{
	
	public final OrderGoods TABLE = ORDER_GOODS;
	
	/**
	 * 	通过订单id[]查询其下商品
	 * @param arrayToSearch
	 * @return  Result<?>
	 */
	public Result<?> getByOrderIds(Integer... arrayToSearch) {
		Result<?> goods = db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.GOODS_ID,TABLE.GOODS_NAME,TABLE.GOODS_SN,TABLE.GOODS_NUMBER,TABLE.GOODS_PRICE,TABLE.GOODS_ATTR,TABLE.PRODUCT_ID).from(TABLE)
			.where(TABLE.ORDER_ID.in(arrayToSearch))
			.orderBy(TABLE.ORDER_ID.desc())
			.fetch();
		return goods;	
	}
	
	/**
	 * 	通过订单sn[]查询其下商品
	 * @param orderSns
	 * @return Map<String, List<RefundVoGoods>>
	 */
	public Map<String, List<RefundVoGoods>> getByOrderSns(List<String> orderSns) {
		return db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.REC_ID,TABLE.GOODS_NAME,TABLE.GOODS_NUMBER,TABLE.RETURN_NUMBER,TABLE.GOODS_PRICE,TABLE.GOODS_ATTR,TABLE.DISCOUNTED_GOODS_PRICE,TABLE.PRODUCT_ID,TABLE.IS_CAN_RETURN).from(TABLE)
			.where(TABLE.ORDER_SN.in(orderSns))
			.fetchGroups(TABLE.ORDER_SN,RefundVoGoods.class);
	}
	/**
	 *	计算子订单商品数量(主订单返回的map->size=0)
	 * @param goods
	 * @param currentOrder
	 * @param isMain
	 * @return HashMap<Integer, Integer>
	 */
	public HashMap<Integer, Integer> countSubOrderGoods(Map<String, List<RefundVoGoods>> goods , OrderListInfoVo currentOrder , Boolean isMain) {
		if(isMain) {
			HashMap<Integer, Integer> count = new HashMap<Integer,Integer>();
			for (Entry<String, List<RefundVoGoods>> entry : goods.entrySet()) {
				if(!currentOrder.getOrderSn().equals(entry.getKey())) {
					for (RefundVoGoods oneGoods : entry.getValue()) {
						if(count.get(oneGoods.getProductId()) == null) {
							count.put(oneGoods.getProductId(), oneGoods.getGoodsNumber());
						}else {
							count.put(oneGoods.getProductId(), count.get(oneGoods.getProductId()) + oneGoods.getGoodsNumber());
						}
					}
				}
			}
		}
		return new HashMap<Integer,Integer>(0);
	}
	
}
