package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.service.foundation.data.JsonResultCode;
/**
 * 
 * @author 王帅
 *
 */
public interface IorderOperate extends IOrderBase{
	public JsonResultCode execute(Object obj);
}
