package com.vpu.mp.service.shop.order.action.base;

import java.util.HashMap;
import java.util.Map;

import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;

/**
 * 获取订单service
 * 
 * @author 王帅
 *
 */
@Component
public class OrderOperateFactory implements ApplicationContextAware {

	private static Map<OrderServiceCode, IorderOperate> orderOperateMap;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 获取实现IorderOperate接口并加入ioc管理的实例
		Map<String, IorderOperate> map = applicationContext.getBeansOfType(IorderOperate.class);
		orderOperateMap = new HashMap<OrderServiceCode, IorderOperate>();
		map.forEach((key, value) -> {
			//防止实现IorderOperate接口的类的ServiceCode重复
			assert(orderOperateMap.get(value.getServiceCode()) == null);
			orderOperateMap.put(value.getServiceCode(), value);
		});
	}

	/**
	 * 通过传出param取其ServiceCode并调用execute
	 * 
	 * @param IOrderBase
	 * @return 执行结果
	 * @throws MpException 
	 */
	public JsonResultCode orderOperate(AbstractOrderOperateQueryParam info) {
		return getService(info.getServiceCode()).execute(info);
	}
	
	/**
	 * 通过传出param取其ServiceCode并调用query
	 * 
	 * @param OrderOperateQueryParam
	 * @return 执行结果
	 * @throws MpException 
	 */
	public Object orderQuery(AbstractOrderOperateQueryParam param) throws MpException {
		return getService(param.getServiceCode()).query(param);
	}

	/**
	 * 根据传入的code获得处理该业务的service
	 * 
	 * @param OrderServiceCode
	 * @return IorderOperate的实现类
	 */
	public IorderOperate getService(OrderServiceCode code) {
		return orderOperateMap.get(code);
	}
}