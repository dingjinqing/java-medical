package com.vpu.mp.service.shop.order.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.PartOrderGoodsShipRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.verify.verifyParam;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;

/**
 * @author 王帅
 */
@Component
public class VerifyService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, verifyParam> {
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	public OrderGoodsService orderGoods;
	
	@Autowired
	private ShipInfoService shipInfo;
	
	@Autowired
	OrderActionService orderAction;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.VERIFY;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	@Override
	public ExecuteResult execute(verifyParam param) {
		
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		
		if (!OrderOperationJudgment.isVerify(order)) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_VERIFY_OPERATION_NOT_SUPPORTED, null);
		}
		
		if(!order.getVerifyCode().equals(param.getVerifyCode()) && param.getIsCheck()) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_VERIFY_CODE_ERROR, null);
		}
		//发货批次号,同一批次为同一快递
		String batchNo = order.getOrderSn() + "_" + DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE);
		
		//order goods
		Result<OrderGoodsRecord> goods = orderGoods.getByOrderId(order.getOrderId());
		
		//构造_添加部分发货信息 b2c_part_order_goods_ship
		List<PartOrderGoodsShipRecord> shipInfoList = new ArrayList<PartOrderGoodsShipRecord>(goods.size());
		
		for (OrderGoodsRecord temp : goods) {
			temp.setSendNumber(temp.getGoodsNumber());
			shipInfo.addRecord(shipInfoList, temp, batchNo, null, temp.getGoodsNumber().intValue());
		}
		
		transaction(()->{
			orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_RECEIVED);
			//添加（部分）发货信息 b2c_part_order_goods_ship
			db().batchInsert(shipInfoList).execute();
			//更新发货数量 b2c_order_goods
			db().batchUpdate(goods).execute();
		});
		//action操作
		orderAction.addRecord(order, param, OrderConstant.ORDER_WAIT_DELIVERY, param.getIsCheck() ? "核销" : "强制核销");
		//TODO 操作记录 b2c_record_admin_action  需要测试记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_VERIFY.code }), new String[] {param.getOrderSn()});
		return null;
	}

}
