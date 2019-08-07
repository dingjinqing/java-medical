package com.vpu.mp.service.shop.order.refund.goods;

import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnOrderGoods;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * Table:order_goods
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
}
