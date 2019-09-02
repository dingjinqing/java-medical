package com.vpu.mp.service.shop.order.record;

import static com.vpu.mp.db.shop.tables.ReturnStatusChange.RETURN_STATUS_CHANGE;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnStatusChange;
import com.vpu.mp.db.shop.tables.records.OrderActionRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.db.shop.tables.records.ReturnStatusChangeRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;;

/**
 * 	订单退货/退款状态变化记录
 * @author 王帅
 *
 */
@Service
public class ReturnStatusChangeService extends ShopBaseService{
	public final ReturnStatusChange TABLE = RETURN_STATUS_CHANGE;
	
	/**
	 * 记录
	 * @param returnOrder
	 * @param isMp
	 * @param desc
	 */
	public void addRecord(ReturnOrderRecord returnOrder , boolean isMp , String desc) {
		ReturnStatusChangeRecord record = db().newRecord(TABLE);
		record.setRetId(returnOrder.getRetId());
		record.setUserId(returnOrder.getUserId());
		record.setType(isMp ? OrderConstant.IS_MP_Y : OrderConstant.IS_MP_N);
		record.setStatus(returnOrder.getRefundStatus());
		record.setOrderSn(returnOrder.getOrderSn());
		record.setDesc(desc);
		record.insert();
	}
}
