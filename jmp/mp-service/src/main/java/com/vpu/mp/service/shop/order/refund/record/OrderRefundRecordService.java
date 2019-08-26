package com.vpu.mp.service.shop.order.refund.record;

import static com.vpu.mp.db.shop.tables.OrderRefundRecord.ORDER_REFUND_RECORD;


import com.vpu.mp.db.shop.tables.OrderRefundRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * 	退款记录表
 * @author 王帅
 *
 */

public class OrderRefundRecordService extends ShopBaseService{
	public final OrderRefundRecord TABLE = ORDER_REFUND_RECORD;
	
	
}
