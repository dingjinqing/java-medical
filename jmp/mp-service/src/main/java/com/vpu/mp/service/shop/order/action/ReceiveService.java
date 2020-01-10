package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 	收货
 * @author 王帅
 *
 */

@Component
public class ReceiveService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, OrderOperateQueryParam>{
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private ShipInfoService ship;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Autowired
	private OrderActionService orderAction;

	@Autowired
    private ReturnOrderGoodsService returnGoods;

	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.RECEIVE;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	/**
	 * 	订单收货目前只支持已发货状态下商品全部收货（不支持部分收货）
	 */
	@Override
	public ExecuteResult execute(OrderOperateQueryParam param) {
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		if(order == null) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
		}
		if(!OrderOperationJudgment.isReceive(order)) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED, null);
		}
		
		transaction(()->{
			ship.receive(order.getOrderSn());
			orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_RECEIVED);
		});
		//TODO 发送通知
		//订单状态记录
		orderAction.addRecord(order, param, order.getOrderStatus() , "订单收货");
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_RECEIVE.code }), new String[] {param.getOrderSn()});
		return null;
	}

    /**
     * 自动任务收货
     */
    public void autoReceiveOrders(){
        Result<OrderInfoRecord> orders = orderInfo.getCanAutoReceiveOrders();
        for (OrderInfoRecord order : orders) {
            if(order.getDeliverType().equals(OrderConstant.CITY_EXPRESS_SERVICE) && order.getShippingId() > 0){
                //TODO 同城配送
                continue;
            }
            if(CollectionUtils.isNotEmpty(returnGoods.getRefundGoodsByStatus(order.getOrderSn(), OrderConstant.SUCCESS_RETURNING))){
                //存在退款不可自动收货
                continue;
            }
            OrderOperateQueryParam param = new OrderOperateQueryParam();
            param.setAction(Integer.valueOf(OrderServiceCode.RECEIVE.ordinal()).byteValue());
            param.setIsMp(OrderConstant.IS_MP_AUTO);
            param.setOrderId(order.getOrderId());
            param.setOrderSn(order.getOrderSn());
            ExecuteResult result = execute(param);
            if(result == null || result.isSuccess()) {
                logger().info("订单自动任务,收货成功,orderSn:{}", order.getOrderSn());
            }else {
                logger().error("订单自动任务,收货失败,orderSn:{},错误信息{}{}", order.getOrderSn(), result.getErrorCode().toString() , result.getErrorParam());
            }
        }
    }
}
