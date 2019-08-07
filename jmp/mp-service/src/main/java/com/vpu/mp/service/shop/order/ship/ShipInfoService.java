package com.vpu.mp.service.shop.order.ship;

import static com.vpu.mp.db.shop.tables.PartOrderGoodsShip.PART_ORDER_GOODS_SHIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.PartOrderGoodsShip;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo.Goods;

/**
 * Table:part_order_goods_ship
 * 
 * @author 王帅
 *
 */
@Service
public class ShipInfoService extends ShopBaseService {

	public final PartOrderGoodsShip TABLE = PART_ORDER_GOODS_SHIP;
	
	/**
	 * 	通过订单sn[]查询其下配送信息，已通过相同的物流号进行聚合
	 * @param arrayToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<String,List<ShippingInfoVo>> getShippingByOrderSn(String... arrayToSearch) {
		if(arrayToSearch.length == 0) {
			return new HashMap<String, List<ShippingInfoVo>>(0);
		}
		Map<String, List<ShippingInfoVo>> goods = db().select(TABLE.asterisk())
				.from(TABLE)
				.where(TABLE.ORDER_SN.in(arrayToSearch))
				.orderBy(TABLE.REC_ID.desc())
				.fetchGroups(TABLE.ORDER_SN,ShippingInfoVo.class);
		//聚合List<ShippingInfoVo>>相同物流单号的对象合并它在数组第一次出现的位置
		for(Entry<String, List<ShippingInfoVo>> temp : goods.entrySet()) {
			List<ShippingInfoVo> voList = temp.getValue();
			Iterator<ShippingInfoVo> iterator = voList.iterator();
			while(iterator.hasNext()) {
				ShippingInfoVo next = iterator.next();
				//如果物流单号相同则聚合
				int indexOf = voList.indexOf(next);
				//该物流单号不是第一次出现的位置,将该记录信息合并到第一次出现的位置并删除
				if(voList.get(indexOf).getGoods() != null) {
					voList.get(indexOf).getGoods().add(new Goods(next.getRecId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber()));
					iterator.remove();		
				}else {
					//第一次出现的位置,初始化goodlist,并将该记录的商品行信息转移到Goods上
					ArrayList<Goods> firstGoodsList = new ArrayList<Goods>();
					firstGoodsList.add(new Goods(next.getRecId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber()));
					voList.get(indexOf).setGoods(firstGoodsList);
				}		
			}
		}
		return goods;	
	}
}
