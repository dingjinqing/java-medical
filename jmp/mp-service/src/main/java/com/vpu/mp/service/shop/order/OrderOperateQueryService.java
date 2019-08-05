package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipVo;

@Service
public class OrderOperateQueryService extends ShopBaseService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 发货查询
	 * @param SellerRemarkParam
	 * @return ShipVo :
	 */
	public ShipVo shipGoodsList(OrderOperateQueryParam param) {
		ShipVo shipVo = null;
		logger.info("获取可发货信息参数为:" + param.toString());
		// 订单信息
		shipVo = db().select(ORDER_INFO.CONSIGNEE, ORDER_INFO.MOBILE, ORDER_INFO.COMPLETE_ADDRESS).from(ORDER_INFO)
				.where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn()).and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY))).fetchOneInto(ShipVo.class);
		if(shipVo == null) {
			return null;
		}
		// 设置可发货信息
		shipVo.setOrderGoodsVo(canBeShipped(param.getOrderSn()));
		logger.info("获取可发货信息完成");
		return shipVo;
	}

	/**
	 * 获取该订单下可发货商品列表
	 */
	public List<OrderGoodsVo> canBeShipped(String orderSn) {
		// TODO 修改select*
		// 正常商品行
		List<OrderGoodsVo> orderGoods = db().select(ORDER_GOODS.asterisk()).from(ORDER_GOODS)
				.where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetchInto(OrderGoodsVo.class);
		// 退货信息
		Map<Integer, List<OrderReturnGoodsVo>> returnOrderGoods = db().select(RETURN_ORDER_GOODS.asterisk()).select()
				.from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn),
						RETURN_ORDER_GOODS.SUCCESS.eq(OrderConstant.SUCCESS_RETURNING))
				.fetchGroups(RETURN_ORDER_GOODS.REC_ID, OrderReturnGoodsVo.class);
		Iterator<OrderGoodsVo> iterator = orderGoods.iterator();
		while (iterator.hasNext()) {
			OrderGoodsVo vo = (OrderGoodsVo) iterator.next();
			// 可发货数量=总数-退货(退货完成)-发货-退货(退货中)
			int numTemp;
			List<OrderReturnGoodsVo> orgTemp = returnOrderGoods.get(vo.getRecId());
			int sum = orgTemp == null ? 0 : orgTemp.stream().mapToInt(OrderReturnGoodsVo::getGoodsNumber).sum();
			if ((numTemp = vo.getGoodsNumber() - vo.getReturnNumber() - vo.getSendNumber() - sum) > 0) {
				vo.setGoodsNumber(numTemp);
			} else {
				iterator.remove();
			}
		}
		return orderGoods;
	}
	
	/**
	 * 退款、退货查询
	 * @param SellerRemarkParam
	 * @return ShipVo :
	 */
	public RefundVo refundGoodsList(OrderOperateQueryParam param) {
		RefundVo vo = new RefundVo();
		logger.info("获取可退款、退货信息参数为:" + param.toString());
		// 订单信息
		List<OrderGoodsVo> goods = db().select(ORDER_INFO.ORDER_SN).from(ORDER_INFO).
				where(ORDER_INFO.MAIN_ORDER_SN.eq(param.getOrderSn()).and(ORDER_INFO.RETURN_TYPE_CFG.eq(OrderConstant.CFG_RETURN_TYPE_N)))
		 .fetchInto(OrderGoodsVo.class);
		if(goods == null) {
			return vo;
		}
		List<String> orderSns = goods.stream().map(OrderGoodsVo::getOrderSn).collect(Collectors.toList());
		Map<String, List<OrderConciseRefundInfoVo>> returnGoods = db().select(RETURN_ORDER_GOODS.asterisk()).from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.in(orderSns).and(RETURN_ORDER_GOODS.SUCCESS.in(OrderConstant.SUCCESS_COMPLETE,OrderConstant.SUCCESS_RETURNING)))
				.fetchGroups(RETURN_ORDER_GOODS.ORDER_SN,OrderConciseRefundInfoVo.class);
		
		logger.info("获取可发货信息完成");
		return null;
	}
}
