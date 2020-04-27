package com.vpu.mp.service.shop.order;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.order.OrderRepurchaseParam;
import com.vpu.mp.service.pojo.shop.order.OrderRepurchaseVo;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkVo;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppAddGoodsToCartParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.GoodsAndOrderInfoBo;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.user.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

/**
 * 订单普通写操作
 * 
 * @author 王帅 2019/7/22
 */
@Service
public class OrderWriteService extends ShopBaseService {

    @Autowired
    public ReturnMethodService returnMethodService;
	@Autowired
	private OrderGoodsService orderGoods;
	@Autowired
	private CartService cartService;
	@Autowired
	private GoodsSpecProductService goodsSpecProductService;

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

	/**
	 * 再次购买
	 * @param param
	 * @return
	 */
	public OrderRepurchaseVo orderRepurchase(OrderRepurchaseParam param) {
		OrderRepurchaseVo vo=new OrderRepurchaseVo();

		List<GoodsAndOrderInfoBo> goodsInfos = orderGoods.getGoodsInfoAndOrderInfo(param.getOrderSn());
		List<GoodsAndOrderInfoBo> invalidGoods = goodsInfos.stream().filter(goods -> goods.getDelFlag().equals(DelFlag.DISABLE_VALUE)
				|| goods.getIsOnSale().equals(GoodsConstant.OFF_SALE)
				|| goods.getPrdNumber().equals(0)).collect(Collectors.toList());
		if (invalidGoods.size()>0){
			vo.setContent("包含"+invalidGoods.size()+"件已失效/已售罄商品，无法再次购买！其他商品已为您加入购物车");
			goodsInfos.removeAll(invalidGoods);
		}
		for (GoodsAndOrderInfoBo next : goodsInfos) {
			WxAppAddGoodsToCartParam addParam = new WxAppAddGoodsToCartParam();
			addParam.setUserId(param.getUserId());
			addParam.setGoodsNumber(next.getGoodsNumber());
			addParam.setPrdId(next.getProductId());
			ResultMessage resultMessage = cartService.addGoodsToCart(addParam);
			if (!resultMessage.getFlag()) {
				vo.setResultMessage(resultMessage);
				return vo;
			}
		}
		return vo;
	}
}
