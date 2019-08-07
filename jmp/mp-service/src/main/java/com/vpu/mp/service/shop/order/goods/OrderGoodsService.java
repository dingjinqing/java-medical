package com.vpu.mp.service.shop.order.goods;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * Table:order_goods
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
	public Result<?> getByOrderId(Integer... arrayToSearch) {
		Result<?> goods = db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.GOODS_ID,TABLE.GOODS_NAME,TABLE.GOODS_SN,TABLE.GOODS_NUMBER,TABLE.GOODS_PRICE,TABLE.GOODS_ATTR,TABLE.PRODUCT_ID).from(TABLE)
			.where(TABLE.ORDER_ID.in(arrayToSearch))
			.orderBy(TABLE.ORDER_ID.desc())
			.fetch();
		return goods;	
	}
}
