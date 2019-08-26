package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
/**
 * 
 * @author 王帅
 *
 */
public interface IorderOperate extends IOrderBase{
	/**
	 * 	操作查询
	 * @param param
	 * @return
	 * @throws MpException
	 */
	public Object query(OrderOperateQueryParam param) throws MpException;
	/**
	 * 	操作执行
	 * @param obj
	 * @return
	 * @throws MpException
	 */
	public JsonResultCode execute(Object obj)throws MpException;
}
