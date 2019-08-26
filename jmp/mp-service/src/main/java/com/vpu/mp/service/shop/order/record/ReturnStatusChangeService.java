package com.vpu.mp.service.shop.order.record;

import static com.vpu.mp.db.shop.tables.ReturnStatusChange.RETURN_STATUS_CHANGE;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnStatusChange;;

/**
 * 	订单退货/退款状态变化记录
 * @author 王帅
 *
 */
@Service
public class ReturnStatusChangeService {
	public final ReturnStatusChange TABLE = RETURN_STATUS_CHANGE;
}
