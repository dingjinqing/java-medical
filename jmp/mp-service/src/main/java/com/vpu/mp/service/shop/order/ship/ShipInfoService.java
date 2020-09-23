package com.vpu.mp.service.shop.order.ship;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.db.shop.tables.PartOrderGoodsShip;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PartOrderGoodsShipRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.shipping.BaseShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo.Goods;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.vpu.mp.db.shop.tables.PartOrderGoodsShip.PART_ORDER_GOODS_SHIP;

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
	 * @param sOrderSns
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<String,List<ShippingInfoVo>> getShippingByOrderSn(List<String> sOrderSns) {
		if(sOrderSns.size() == 0) {
			return new HashMap<>(0);
		}
		Map<String, List<ShippingInfoVo>> goods = db().select(TABLE.asterisk())
				.from(TABLE)
				.where(TABLE.ORDER_SN.in(sOrderSns))
				.orderBy(TABLE.REC_ID.desc())
				.fetchGroups(TABLE.ORDER_SN,ShippingInfoVo.class);
		//聚合List<ShippingInfoVo>>相同批次号的对象合并它放在在数组第一次出现的位置
		for(Entry<String, List<ShippingInfoVo>> temp : goods.entrySet()) {
			List<ShippingInfoVo> voList = temp.getValue();
			Iterator<ShippingInfoVo> iterator = voList.iterator();
			while(iterator.hasNext()) {
				ShippingInfoVo next = iterator.next();
				//如果批次号相同则聚合
				int indexOf = voList.indexOf(next);
				//该批次号不是第一次出现的位置,将该记录信息合并到第一次出现的位置并删除
				if(voList.get(indexOf).getGoods() != null) {
					voList.get(indexOf).getGoods().add(new Goods(next.getOrderGoodsId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber() ,null, null,next.getGoodsId(),next.getProductId()));
					iterator.remove();
				}else {
					//第一次出现的位置,初始化goodlist,并将该记录的商品行信息转移到Goods上
					ArrayList<Goods> firstGoodsList = new ArrayList<Goods>();
					firstGoodsList.add(new Goods(next.getOrderGoodsId(), next.getGoodsName(), next.getGoodsAttr(), next.getSendNumber(), null, null,next.getGoodsId(),next.getProductId()));
					voList.get(indexOf).setGoods(firstGoodsList);
				}
			}
		}
		return goods;
	}

	public PartOrderGoodsShipRecord addRecord(OrderGoodsRecord orderGoodsVo, OrderInfoRecord orderRecord, String batchNo, ShipParam param, Integer sendNumber) {
		PartOrderGoodsShipRecord record = new PartOrderGoodsShipRecord();
		record.setShopId(getShopId());
		record.setOrderGoodsId(orderGoodsVo.getRecId());
		record.setOrderSn(orderGoodsVo.getOrderSn());
		record.setBatchNo(batchNo);
		record.setGoodsId(orderGoodsVo.getGoodsId());
		record.setGoodsName(orderGoodsVo.getGoodsName());
		record.setProductId(orderGoodsVo.getProductId());
		record.setSendNumber(sendNumber.shortValue());
		record.setGoodsAttr(orderGoodsVo.getGoodsAttr());
		record.setShippingType(orderRecord.getDeliverType());
		record.setShippingAccountId(param.getShipAccountId());
		record.setShippingPlatform(param.getPlatform());
		//核销时不设置
		record.setShippingId(param.getShippingId());
		record.setShippingNo(param.getShippingNo());
		if (param.getShipUserId()!=null){
			record.setShippingUserId(param.getShipUserId());
		}
		if (orderRecord.getDeliverType().equals(OrderConstant.DELIVER_TYPE_SELF)){
			//自提订单核销
			Timestamp temp = DateUtils.getSqlTimestamp();
			record.setConfirmTime(temp);
		}
		return record;
	}

	public void receive(String orderSn,Byte platform,Integer userId,Integer accountId) {
		db().update(TABLE).set(TABLE.CONFIRM_TIME, DateUtils.getSqlTimestamp())
				.set(TABLE.CONFIRM_PLATFORM, platform)
				.set(TABLE.CONFIRM_ACCOUNT_ID, accountId)
				.set(TABLE.CONFIRM_USER_ID, userId)
				.where(TABLE.ORDER_SN.eq(orderSn)).execute();
	}

    /**
     * 	通过order_sn和product_id查单条订单行的发货信息
     * @return  BaseShippingInfoVo
     */
    public BaseShippingInfoVo getOrderGoodsShipping(String orderSn,Integer recId) {
        Record record = (Record) db().select(PART_ORDER_GOODS_SHIP.ORDER_SN,PART_ORDER_GOODS_SHIP.SHIPPING_ID,PART_ORDER_GOODS_SHIP.SHIPPING_NAME,PART_ORDER_GOODS_SHIP.SHIPPING_NO,PART_ORDER_GOODS_SHIP.SHIPPING_TIME,PART_ORDER_GOODS_SHIP.CONFIRM_TIME).from(PART_ORDER_GOODS_SHIP).where(PART_ORDER_GOODS_SHIP.ORDER_SN.eq(orderSn).and(PART_ORDER_GOODS_SHIP.ORDER_GOODS_ID.eq(recId))).fetchOne();
        if(record != null){
            return record.into(BaseShippingInfoVo.class);
        }else {
            return null;
        }
    }

    /**
     * 查询完成数
     * @param accountId
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer getCountFinishedNumByAccountIdUserId(Integer accountId,Integer userId, Timestamp startTime,Timestamp endTime){
        SelectConditionStep<? extends Record> select=db().selectCount().from(PART_ORDER_GOODS_SHIP).where(PART_ORDER_GOODS_SHIP.CONFIRM_ACCOUNT_ID.eq(accountId))
            .and(PART_ORDER_GOODS_SHIP.CONFIRM_USER_ID.eq(userId));
        if(startTime!=null){
            select.and(PART_ORDER_GOODS_SHIP.CONFIRM_TIME.ge(startTime));
        }
        if(endTime!=null){
            select.and(PART_ORDER_GOODS_SHIP.CONFIRM_TIME.le(endTime));
        }
        return select.fetchAnyInto(Integer.class);
    }
}
