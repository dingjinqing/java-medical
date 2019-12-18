
package com.vpu.mp.service.shop.order.action;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
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

import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PartOrderGoodsShipRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam.ShipGoods;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipVo;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;
/**
 * 	发货
 * @author 王帅
 *
 */
@Component
public class ShipService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, ShipParam> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShipInfoService shipInfo;
	@Autowired
	OrderActionService orderAction;
	@Autowired
	public RecordAdminActionService record;
	@Autowired
	public OrderGoodsService orderGoods;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.ADMIN_SHIP;
	}

	@Override
	public ExecuteResult execute(ShipParam param) {
		logger.info("发货参数为:"+param.toString());
		//是否存在可发货数>当前发货数的标识
		boolean flag = false;
		//可发货商品
		List<OrderGoodsVo> canBeShipped = canBeShipped(param.getOrderSn());
		if(canBeShipped == null || canBeShipped.size() == 0) {
			//无可发货信息
			logger.error("发货时无可发货商品");
			return ExecuteResult.create(JsonResultCode.CODE_ORDER, null);
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
		List<PartOrderGoodsShipRecord> shipInfoList = new ArrayList<PartOrderGoodsShipRecord>(shipGoods.length);
		//声明存放OrderGoodsRecord的list
		ArrayList<OrderGoodsRecord> recordList = new ArrayList<OrderGoodsRecord>(shipGoods.length);
		//发货批次号,同一批次为同一快递
		String batchNo = canBeShipped.get(0).getOrderSn() + "_" + DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE);
		for (ShipGoods oneGoods : shipGoods) {
			Integer sendNumber = oneGoods.getSendNumber();
			//校验_商品发货数量
			Integer recId = oneGoods.getRecId();
			if(cbsMap.get(recId) == null) {
				logger.error("商品不可发货或已发货,order_goods_rec_id:"+recId);
				//该商品不可发货或已发货
				return ExecuteResult.create(JsonResultCode.CODE_ORDER, null);
			}else if(cbsMap.get(recId).getGoodsNumber() < sendNumber ) {
				logger.error("商品发货数量大于可发货数量,order_goods_rec_id:"+recId);
				//发货数量大于可发货数量
				return ExecuteResult.create(JsonResultCode.CODE_ORDER, null);
			}else if(cbsMap.get(recId).getGoodsNumber() > sendNumber ) {
				flag = true;
			}
			//下次可发货数 = 可发货数-当前发货数,计算是否可以切换状态为已发货
			cbsMap.get(recId).setGoodsNumber(cbsMap.get(recId).getGoodsNumber() - sendNumber);
			//构造参数
			OrderGoodsRecord orderGoodsVo = goods.get(oneGoods.getRecId());
			orderGoodsVo.setSendNumber((orderGoodsVo.getSendNumber().intValue() + sendNumber.intValue()));
			recordList.add(orderGoodsVo);
			shipInfo.addRecord(shipInfoList, orderGoodsVo, batchNo, param, sendNumber);
		}
		//判断此次发货是否为部分发货
		byte partShipFlag = OrderConstant.NO_PART_SHIP;
		if(canBeShipped.size() > shipGoods.length || flag) {
			partShipFlag = OrderConstant.PART_SHIP;
		}
		//构造主表基本信息 b2c_order_info
		OrderInfoRecord orderRecord = db().fetchOne(ORDER_INFO, ORDER_INFO.ORDER_SN.eq(param.getOrderSn()));
		//设置是否部分发货
		if(partShipFlag == OrderConstant.PART_SHIP) {
			orderRecord.setPartShipFlag(OrderConstant.PART_SHIP);
		}
		orderRecord.setShippingTime(Timestamp.from(Instant.now()));
		orderRecord.setShippingNo(param.getShippingNo());
		orderRecord.setShippingId(param.getShippingId());
		transaction(()->{
			//添加（部分）发货信息 b2c_part_order_goods_ship
			db().batchInsert(shipInfoList).execute();
			//更新发货数量 b2c_order_goods
			db().batchUpdate(recordList).execute();
			//判断此次发货是否全部发货
			if(setOrderStatus(orderRecord)) {
				orderRecord.setOrderStatus(OrderConstant.ORDER_SHIPPED);
			}
			//更新主表基本信息 b2c_order_info
			db().executeUpdate(orderRecord, ORDER_INFO.ORDER_SN.eq(param.getOrderSn()));
			
		});
		//action操作
		orderAction.addRecord(orderRecord, param, OrderConstant.ORDER_WAIT_DELIVERY, orderRecord.getOrderStatus() == OrderConstant.ORDER_SHIPPED ? "全部发货 " : "部分发货");
		//TODO 操作记录 b2c_record_admin_action  需要测试记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_SHIP.code }), new String[] {param.getOrderSn()});
		logger.info("发货完成");
		return null;
	}
	
	/**
	 * 发货查询
	 * @param SellerRemarkParam
	 * @return ShipVo :
	 */
	@Override
	public Object query(OrderOperateQueryParam param) {
		ShipVo shipVo = null;
		logger.info("获取可发货信息参数为:" + param.toString());
		// 订单信息
		shipVo = db().select(ORDER_INFO.ORDER_SN,ORDER_INFO.MAIN_ORDER_SN,ORDER_INFO.CONSIGNEE, ORDER_INFO.MOBILE, ORDER_INFO.COMPLETE_ADDRESS).from(ORDER_INFO)
				.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn()).and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY))).fetchOneInto(ShipVo.class);
		
		if(shipVo == null || shipVo.getOrderSn().equals(shipVo.getMainOrderSn())) {
			return null;
		}
		// 设置可发货信息
		shipVo.setOrderGoodsVo(canBeShipped(param.getOrderSn()));
		logger.info("获取可发货信息完成");
		return shipVo;
	}
	
	/**
	 * 获取该订单下可发货商品列表
	 */
	public List<OrderGoodsVo> canBeShipped(String orderSn) {
		// TODO 修改select*
		//该单是否支持发货
		
		//TODO Short.valueOf("0")正常商品行
		List<OrderGoodsVo> orderGoods = db().select(ORDER_GOODS.asterisk()).from(ORDER_GOODS)
				.where(ORDER_GOODS.ORDER_SN.eq(orderSn).and(ORDER_GOODS.SEND_NUMBER.eq(0))).fetchInto(OrderGoodsVo.class);
		// 查询退货中信息
		Map<Integer, List<OrderReturnGoodsVo>> returnOrderGoods = db().select(RETURN_ORDER_GOODS.asterisk()).select()
				.from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn),
						RETURN_ORDER_GOODS.SUCCESS.eq(OrderConstant.SUCCESS_RETURNING))
				.fetchGroups(RETURN_ORDER_GOODS.REC_ID, OrderReturnGoodsVo.class);
		Iterator<OrderGoodsVo> iterator = orderGoods.iterator();
		while (iterator.hasNext()) {
			OrderGoodsVo vo = (OrderGoodsVo) iterator.next();
			// 可发货数量=总数-退货(退货完成)-发货-退货(退货中)
			int numTemp;
			List<OrderReturnGoodsVo> orgTemp = returnOrderGoods.get(vo.getRecId());
			int sum = orgTemp == null ? 0 : orgTemp.stream().mapToInt(OrderReturnGoodsVo::getGoodsNumber).sum();
			if ((numTemp = vo.getGoodsNumber() - vo.getReturnNumber() - vo.getSendNumber() - sum) > 0) {
				vo.setGoodsNumber(numTemp);
			} else {
				iterator.remove();
			}
		}
		return orderGoods;
	}
	
	public boolean setOrderStatus(OrderInfoRecord order) {
		Result<OrderGoodsRecord> goods = orderGoods.getByOrderId(order.getOrderId());
		for (OrderGoodsRecord goodsRecord : goods) {
			if(goodsRecord.getGoodsNumber() > goodsRecord.getSendNumber() + goodsRecord.getReturnNumber()) {
				return false;
			}
		}
		return true;
	}

}
