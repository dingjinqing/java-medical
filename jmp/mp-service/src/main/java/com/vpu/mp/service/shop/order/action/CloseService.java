package com.vpu.mp.service.shop.order.action;

import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;

/**
 * 	订单关闭、
 * @author 王帅
 *
 */
@Component
public class CloseService extends ShopBaseService implements IorderOperate {@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.CANCEL;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	@Override
	public JsonResultCode execute(Object obj) {
		return null;
	}
	
}
