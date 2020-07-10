package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.base.IOrderBase;
import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
/**
 * 
 * @author 王帅
 *
 */
public interface IorderOperate<T extends AbstractOrderOperateQueryParam,E extends AbstractOrderOperateQueryParam> extends IOrderBase {
	/**
	 * 	操作查询
	 * @param param 参数
	 * @return 查询结果
	 * @throws MpException 异常
	 */
	Object query(T param) throws MpException;
	/**
	 * 	操作执行
	 * @param obj 参数
	 * @return 执行结果
	 */
    ExecuteResult execute(E obj);

}
