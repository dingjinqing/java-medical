package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkVo;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;

/**
 * 订单普通写操作
 * 
 * @author 王帅 2019/7/22
 */
@Service
public class OrderWriteService extends ShopBaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 订单标星切换
	 * 
	 * @param
	 * @return void
	 */
	public void switchStar(StarParam param) {
		logger.info("订单标星切换参数为:" + param.toString());
		db().update(param.getTable()).set(param.getField(), param.getStarFlag()).where(param.getWhere()).execute();
		logger.info("订单标星切换完成");
	}

	/**
	 * 	卖家备注
	 * 
	 * @param param
	 * @return boolean
	 */
	public SellerRemarkVo sellerRemark(SellerRemarkParam param) {
		SellerRemarkVo vo = null;
		switch (param.getType()) {
		case SellerRemarkParam.TYPE_QUERY:
			logger.info("获取卖家备注参数为:" + param.toString());
			vo = db().select(ORDER_INFO.ORDER_SN, ORDER_INFO.SELLER_REMARK).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn())).fetchAnyInto(SellerRemarkVo.class);
			logger.info("获取卖家备注完成");
			break;
		case SellerRemarkParam.TYPE_UPDATE:
			logger.info("更新卖家备注参数为:" + param.toString());
			db().update(ORDER_INFO).set(ORDER_INFO.SELLER_REMARK, param.getRemark())
					.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn())).execute();
			logger.info("更新卖家备注完成");
			break;
		default :
			logger.error("卖家备注switch_default");
		}
		return vo;
	}
}
