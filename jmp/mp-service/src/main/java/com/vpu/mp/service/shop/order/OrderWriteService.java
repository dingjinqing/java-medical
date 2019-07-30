package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.PartOrderGoodsShip.PART_ORDER_GOODS_SHIP;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.BatchBindStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.OrderConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkVo;
import com.vpu.mp.service.pojo.shop.order.write.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.order.write.ship.ShipParam.ShipGoods;
import com.vpu.mp.service.pojo.shop.order.write.ship.ShipVo;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;

/**
 * 订单写操作
 * 
 * @author 王帅 2019/7/22
 * @param <E>
 */
@Service
public class OrderWriteService extends ShopBaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** return_order_goods表success字段,退货中1 */
	private final byte RETURNING = 1;

	/**
	 * 订单标星切换
	 * 
	 * @param
	 * @return void
	 */
	public void switchStar(StarParam param) {
		logger.info("订单标星切换参数为:" + param.toString());
		db().update(param.getTable()).set(param.getField(), param.getStarFlag()).where(param.getWhere()).execute();
		logger.info("订单标星切换完成");
	}

	/**
	 * 卖家备注
	 * 
	 * @param SellerRemarkParam
	 * @return boolean
	 */
	public SellerRemarkVo sellerRemark(SellerRemarkParam param) {
		SellerRemarkVo vo = null;
		switch (param.getType()) {
		case SellerRemarkParam.TYPE_QUERY:
			logger.info("获取卖家备注参数为:" + param.toString());
			vo = db().select(ORDER_INFO.BK_ORDER_SN, ORDER_INFO.SELLER_REMARK).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn())).fetchAnyInto(SellerRemarkVo.class);
			logger.info("获取卖家备注完成");
			break;
		case SellerRemarkParam.TYPE_UPDATE:
			logger.info("更新卖家备注参数为:" + param.toString());
			db().update(ORDER_INFO).set(ORDER_INFO.SELLER_REMARK, param.getRemark())
					.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn())).execute();
			logger.info("更新卖家备注完成");
			break;
		}
		return vo;
	}

	/**
	 * 发货
	 * 
	 * @param SellerRemarkParam
	 * @return ShipVo :
	 */
	public ShipVo shipGoodsList(ShipParam param) {
		ShipVo shipVo = null;
		logger.info("获取可发货信息参数为:" + param.toString());
		// 订单信息
		shipVo = db().select(ORDER_INFO.CONSIGNEE, ORDER_INFO.MOBILE, ORDER_INFO.COMPLETE_ADDRESS).from(ORDER_INFO)
				.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn())).fetchOneInto(ShipVo.class);
		// 设置可发货信息
		shipVo.setOrderGoodsVo(canBeShipped(param.getOrderSn()));
		logger.info("获取可发货信息完成");
		return shipVo;
	}

	/**
	 * 获取该订单下可发货商品列表
	 */
	public List<OrderGoodsVo> canBeShipped(String orderSn) {
		// 正常商品行
		List<OrderGoodsVo> orderGoods = db().select().from(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn))
				.fetchInto(OrderGoodsVo.class);
		// 退货信息
		Map<Integer, List<OrderReturnGoodsVo>> returnOrderGoods = db().select().from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn), RETURN_ORDER_GOODS.SUCCESS.eq(RETURNING))
				.fetchGroups(RETURN_ORDER_GOODS.REC_ID, OrderReturnGoodsVo.class);
		Iterator<OrderGoodsVo> iterator = orderGoods.iterator();
		while (iterator.hasNext()) {
			OrderGoodsVo vo = (OrderGoodsVo) iterator.next();
			// 可发货数量=总数-退货(退货完成)-发货-退货(退货中)
			int numTemp;
			List<OrderReturnGoodsVo> orgTemp = returnOrderGoods.get(vo.getRecId());
			int sum = orgTemp.stream().mapToInt(OrderReturnGoodsVo::getGoodsNumber).sum();
			if ((numTemp = vo.getGoodsNumber() - vo.getReturnNumber() - vo.getSendNumber() - sum) > 0) {
				vo.setGoodsNumber(numTemp);
			} else {
				iterator.remove();
			}
		}
		return orderGoods;
	}

	public JsonResultCode ship(ShipParam param) {
		logger.info("发货参数为:"+param.toString());
		//是否存在可发货数>当前发货数的标识
		boolean flag = false;
		//可发货商品
		List<OrderGoodsVo> canBeShipped = canBeShipped(param.getOrderSn());
		if(canBeShipped == null || canBeShipped.size() == 0) {
			//无可发货信息
			return JsonResultCode.CODE_ORDER;
		}
		Map<Integer, OrderGoodsVo> cbsMap = canBeShipped.stream().collect(Collectors.toMap(OrderGoodsVo::getRecId, Function.identity()));
		ShipGoods[] shipGoods = param.getShipGoods();
		//构建商品行查询条件
		ArrayList<Integer> recIds = new ArrayList<Integer>(shipGoods.length);
		for (ShipGoods sTemp : shipGoods) {
			recIds.add(sTemp.getRecId());
		}
		//查询商品行,返回的OrderGoodsRecord
		Map<Integer, OrderGoodsRecord> goods = db().fetch(ORDER_GOODS,ORDER_GOODS.REC_ID.in(recIds)).intoMap(OrderGoodsRecord::getRecId);
		//构造_添加部分发货信息 b2c_part_order_goods_ship
		BatchBindStep pogh = db().batch(db().insertInto(PART_ORDER_GOODS_SHIP).columns(PART_ORDER_GOODS_SHIP.SHOP_ID,PART_ORDER_GOODS_SHIP.ORDER_GOODS_ID,PART_ORDER_GOODS_SHIP.ORDER_SN,PART_ORDER_GOODS_SHIP.GOODS_ID,PART_ORDER_GOODS_SHIP.GOODS_NAME,PART_ORDER_GOODS_SHIP.PRODUCT_ID,PART_ORDER_GOODS_SHIP.SEND_NUMBER,PART_ORDER_GOODS_SHIP.GOODS_ATTR,PART_ORDER_GOODS_SHIP.SHIPPING_NO,PART_ORDER_GOODS_SHIP.SHIPPING_ID));
		//声明存放OrderGoodsRecord的list
		ArrayList<OrderGoodsRecord> recordList = new ArrayList<OrderGoodsRecord>(shipGoods.length);
		for (ShipGoods oneGoods : shipGoods) {
			Integer sendNumber = oneGoods.getSendNumber();
			//校验_商品发货数量
			Integer recId = oneGoods.getRecId();
			if(cbsMap.get(recId) == null) {
				//该商品不可发货或已发货
				return JsonResultCode.CODE_ORDER;
			}else if(cbsMap.get(recId).getGoodsNumber() < sendNumber ) {
				//发货数量大于可发货数量
				return JsonResultCode.CODE_ORDER;
			}else if(cbsMap.get(recId).getGoodsNumber() > sendNumber ) {
				flag = true;
			}
			//下次可发货数 = 可发货数-当前发货数,计算是否可以切换状态为已发货
			cbsMap.get(recId).setGoodsNumber(cbsMap.get(recId).getGoodsNumber() - sendNumber);
			//构造参数
			OrderGoodsRecord orderGoodsVo = goods.get(oneGoods.getRecId());
			orderGoodsVo.setSendNumber((short) (orderGoodsVo.getSendNumber().intValue() + sendNumber.intValue()));
			recordList.add(orderGoodsVo);
			pogh.bind(getShopId(),goods.get(orderGoodsVo.getRecId()),orderGoodsVo.getOrderSn(),orderGoodsVo.getGoodsId(),orderGoodsVo.getGoodsName(),orderGoodsVo.getProductId(),sendNumber,orderGoodsVo.getGoodsAttr(),param.getShippingNo(),param.getShippingId());
		}
		//判断是否为部分发货
		byte partShipFlag = OrderConstant.NO_PART_SHIP;
		if(canBeShipped.size() > shipGoods.length || flag) {
			partShipFlag = OrderConstant.PART_SHIP;
		}
		//构造主表基本信息 b2c_order_info
		OrderInfoRecord orderRecord = db().fetchOne(ORDER_INFO, ORDER_INFO.ORDER_SN.eq(param.getOrderSn()));
		if(orderRecord.getPartShipFlag() == 0 && partShipFlag == OrderConstant.PART_SHIP) {//设置是否部分发货
			orderRecord.setPartShipFlag(OrderConstant.PART_SHIP);
		}
		if(cbsMap.values().stream().mapToInt(OrderGoodsVo::getGoodsNumber).sum() > 0 ) {//判断此次发货是否全部发货
			orderRecord.setOrderStatus(OrderConstant.ORDER_SHIPPED);
		}
		orderRecord.setShippingTime(Timestamp.from(Instant.now()));
		orderRecord.setShippingNo(param.getShippingNo());
		orderRecord.setShippingId(param.getShippingId());
		transaction(()->{
			//添加部分发货信息 b2c_part_order_goods_ship
			pogh.execute();
			//更新发货数量 b2c_order_goods
			db().batchUpdate(recordList);
			//更新主表基本信息 b2c_order_info
			db().executeUpdate(orderRecord, ORDER_INFO.ORDER_SN.eq(param.getOrderSn()));
		});
		//操作记录 b2c_record_admin_action
		Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_SHIP.code });
		saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_SHIP.code }), new String[] {param.getOrderSn()});
		//RecordAdminActionService.in
		logger.info("发货完成");
		return null;
	}
}
