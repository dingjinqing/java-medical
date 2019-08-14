package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
/**
 * 
 * @author 王帅
 *
 */
public interface IorderOperate extends IOrderBase{
	public Object query(OrderOperateQueryParam param);
	public JsonResultCode execute(Object obj);
}
