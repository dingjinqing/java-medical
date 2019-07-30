package com.vpu.mp.service.shop.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;

/**
 * 订单写操作
 * @author 王帅
 * 2019/7/22
 */
@Service
public class OrderWriteService extends ShopBaseService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 订单标星切换
	 * @param 
	 * @return void
	 */
	public void switchStar(StarParam param) {
		logger.info("订单标星切换参数为:"+param.toString());
		db().update(param.getTable()).set(param.getField(), param.getStarFlag()).where(param.getWhere()).execute();
		logger.info("订单标星切换完成");
	}
}
