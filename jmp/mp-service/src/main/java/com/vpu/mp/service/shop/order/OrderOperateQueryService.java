package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;
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
		// 查询退货中信息
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
	 * @param SellerRemarkParam,isMp(T小程序端/F后台手工退款)
	 * @return RefundVo
	 */
	public RefundVo refundGoodsList(OrderOperateQueryParam param , Boolean isMp) {
		RefundVo vo = new RefundVo();
		logger.info("获取可退款、退货信息参数为:" + param.toString());
		// 订单信息
		List<OrderListInfoVo> orders = db().select(ORDER_INFO.ORDER_SN,ORDER_INFO.MAIN_ORDER_SN,ORDER_INFO.RETURN_TYPE_CFG).from(ORDER_INFO).
				where(ORDER_INFO.MAIN_ORDER_SN.eq(param.getOrderSn())).orderBy(ORDER_INFO.ORDER_ID)
		 .fetchInto(OrderListInfoVo.class);
		//过滤null、小程序端无法退货、退款拆单;区分前后台
		if(orders == null || orders.size() == 0 || mpCanRefund(orders,isMp)) {
			return vo;
		}
		//全部订单id
		ArrayList<Integer> orderIds = new ArrayList<Integer>(orders.size());
		//全部订单sn
		ArrayList<String> orderSns = new ArrayList<String>(orders.size());
		orders.forEach(o->{orderIds.add(o.getOrderId());orderSns.add(o.getOrderSn());});
		//查询商品,区分前后台(后台权限大，前台有些退不了)
		Map<Integer, List<RefundVoGoods>> goodsByOrderId = getGoods(isMp , orderIds.toArray(new Integer[orderIds.size()]));
		//查询该订单退货中的商品
		Map<Integer, Integer> refundingGoods = getRefundingGoods(param.getOrderSn());
		//构造主单下子订单及其商品信息(兼容普通订单、拆单订单)
		if(orders.size() > 1) {
			//判断当前订单是否为主订单
			for (OrderListInfoVo order : orders) {
				if(order.getOrderSn().equals(order.getMainOrderSn()) && param.getOrderSn().equals(order.getOrderSn())) {
					//构造Map<Integer(子订单规格号), Integer(数量)>
					HashMap<Integer, Integer> productNum = new HashMap<Integer, Integer>();
					for(Entry<Integer, List<RefundVoGoods>> subGoods : goodsByOrderId.entrySet()) {
						if(!subGoods.getKey().equals(order.getOrderId())) {
							for (RefundVoGoods subOneGoods : subGoods.getValue()) {
								if(productNum.get(subOneGoods.getProductId()) == null) {
									//第一次初始化该规格商品数量
									productNum.put(subOneGoods.getProductId(), subOneGoods.getGoodsNumber());
								}else {
									//第二次数量相加
									productNum.put(subOneGoods.getProductId(), productNum.get(subOneGoods.getProductId()) + subOneGoods.getGoodsNumber());
								}
							}
							
						}
						
					}
					//是否存在可退商品标识
					boolean isRefund = false;
					//设置可退详情
					Iterator<RefundVoGoods> iterator = goodsByOrderId.get(order.getOrderId()).iterator();
					while (iterator.hasNext()) {
						RefundVoGoods orderGoodsVo = (RefundVoGoods) iterator.next();
						//总数 = 总数 - 子订单数量
						orderGoodsVo.setTotal(orderGoodsVo.getGoodsNumber() - (productNum.get(orderGoodsVo.getProductId()) == null ? 0 : productNum.get(orderGoodsVo.getProductId())));
						//已提交=退中+退完成
						Integer submitted = (refundingGoods.get(orderGoodsVo.getProductId()) == null ? 0 : refundingGoods.get(orderGoodsVo.getProductId())) + orderGoodsVo.getReturnNumber();
						orderGoodsVo.setSubmitted(submitted);
						//可退
						Integer returnable = orderGoodsVo.getTotal() - orderGoodsVo.getSubmitted();
						if(returnable > 0 ) {
							isRefund = true;
						}else if(returnable == 0){
							//小程序端不展示
							if(isMp) {
								iterator.remove();
								continue;
							}
						}
						orderGoodsVo.setReturnable(returnable);
					}
					if(!isRefund && isMp) {
						//如果不存在可退且为小程序发起的则
						return vo;
					}
					vo.setRefundGoods(goodsByOrderId.get(order.getOrderId()));
					//根据当前状态判断
					/**0支持退款，1支持退货、退款(admin后台增加只退运费)*/
					Byte returnType;
					logger.info("获取可发货信息完成,且订单为已拆单的主订单");
					return vo;
				}	
			}
		}
		//是否存在可退商品标识
		boolean isRefund = false;
		//未被拆分主订单、普通订单
		Iterator<RefundVoGoods> goods = goodsByOrderId.get(orderIds.get(0)).iterator();
		while (goods.hasNext()) {
			RefundVoGoods oneGoods = (RefundVoGoods) goods.next();
			//总数 = 总数 - 子订单数量
			oneGoods.setTotal(oneGoods.getGoodsNumber());
			//已提交=退中+退完成
			Integer submitted = (refundingGoods.get(oneGoods.getProductId()) == null ? 0 : refundingGoods.get(oneGoods.getProductId())) + oneGoods.getReturnNumber();
			oneGoods.setSubmitted(submitted);
			//可退
			Integer returnable = oneGoods.getTotal() - oneGoods.getSubmitted();
			if(returnable > 0 ) {
				isRefund = true;
			}else if(returnable == 0){
				//小程序端不展示
				if(isMp) {
					goods.remove();
					continue;
				}
			}
			oneGoods.setReturnable(returnable);
		}
		if(!isRefund && isMp) {
			//如果不存在可退且为小程序发起的则
			return vo;
		}
		vo.setRefundGoods(goodsByOrderId.get(orderIds.get(0)));
		//根据当前状态判断
		
		
		logger.info("获取可发货信息完成");
		return null;
	}
	/**
	 * 过滤送礼订单、区分前后台权限
	 * @param orders
	 * @param isMp
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	public Boolean mpCanRefund(List<OrderListInfoVo> orders , Boolean isMp) {
		if(isMp) {
			for (OrderListInfoVo order : orders) {
				if (!StringUtils.isBlank(order.getGoodsType()) && order.getOrderSn().equals(order.getMainOrderSn())) {
					// 送礼订单、订单不支持退 小程序端无法退款
					if (Arrays.asList((order.getGoodsType().split(","))).contains(OrderConstant.GOODS_TYPE_GIVE_GIFT) || OrderConstant.CFG_RETURN_TYPE_N == order.getReturnTypeCfg()) {
						//不能退货
						return Boolean.TRUE;
					}
				}
				return Boolean.FALSE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 通过订单id[]查询其下商品
	 * @param goodsListToSearch
	 * @return  Map<Integer, List<OrderGoods>>
	 */
	public Map<Integer, List<RefundVoGoods>> getGoods(Boolean isMp , Integer... arrayToSearch) {
		if(arrayToSearch.length == 0) {
			return new HashMap<Integer, List<RefundVoGoods>>(0);
		}
		SelectConditionStep<?> where = db().select(ORDER_GOODS.ORDER_ID,ORDER_GOODS.ORDER_SN,ORDER_GOODS.REC_ID,ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_NUMBER,ORDER_GOODS.RETURN_NUMBER,ORDER_GOODS.GOODS_PRICE,ORDER_GOODS.GOODS_ATTR,ORDER_GOODS.DISCOUNTED_GOODS_PRICE,ORDER_GOODS.PRODUCT_ID).from(ORDER_GOODS)
		.where(ORDER_GOODS.ORDER_ID.in(arrayToSearch));
		if(isMp) {
			where = where.and(ORDER_GOODS.IS_CAN_RETURN.eq(OrderConstant.IS_CAN_RETURN_Y));
		}
		Map<Integer, List<RefundVoGoods>> goods = where.orderBy(ORDER_GOODS.ORDER_ID.desc()).fetchGroups(ORDER_GOODS.ORDER_ID,RefundVoGoods.class);
		return goods;	
	}
	
	/**
	 * 通过订单orderSn[]查询退货中的商品
	 * @param goodsListToSearch
	 * @return Map<Integer(子订单规格号), Integer(数量)>
	 */
	public Map<Integer, Integer> getRefundingGoods(String orderSn) {
		// 查询退货中信息
		List<OrderReturnGoodsVo> returnOrderGoods = db().select(RETURN_ORDER_GOODS.asterisk())
				.from(RETURN_ORDER_GOODS)
				.where(RETURN_ORDER_GOODS.ORDER_SN.eq(orderSn),
						RETURN_ORDER_GOODS.SUCCESS.eq(OrderConstant.SUCCESS_RETURNING))
				.fetchInto(OrderReturnGoodsVo.class);
		//构造Map<Integer(子订单规格号), Integer(数量)>
		HashMap<Integer, Integer> productNum = new HashMap<Integer, Integer>();
		for (OrderReturnGoodsVo goods : returnOrderGoods) {
			if(productNum.get(goods.getProductId()) == null) {
				//第一次初始化该规格商品数量
				productNum.put(goods.getProductId(), goods.getGoodsNumber());
			}else {
				//第二次数量相加
				productNum.put(goods.getProductId(), productNum.get(goods.getProductId()) + goods.getGoodsNumber());
			}
		}
		return productNum;
	}
	
	/**
	 * 判断该当前订单
	 */
}
