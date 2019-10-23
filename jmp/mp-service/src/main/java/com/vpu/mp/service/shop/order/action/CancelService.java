package com.vpu.mp.service.shop.order.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordMemberTradeService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;

/**
 * mp取消订单
 * @author 王帅
 *
 */
@Component
public class CancelService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private RecordMemberTradeService recordMemberTrade;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Autowired
	private OrderActionService orderAction;
	
	@Autowired
	private OrderGoodsService orderGoods;
	
	@Autowired
	private GoodsService goods;
	
	@Autowired
	private GoodsSpecProductService goodsSpecProduct;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.CANCEL;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	@Override
	public JsonResultCode execute(OrderOperateQueryParam param) {

		
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		if(order == null) {
			return JsonResultCode.CODE_ORDER_NOT_EXIST;
		}
		if(!OrderOperationJudgment.mpIsCancel(order)) {
			return JsonResultCode.CODE_ORDER_CANCEL_NOT_CANCEL;
		}
		try {
			transaction(()->{
				//退支付金额
				returnOrder(order);
				orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_CANCELLED);
				//TODO 退优惠卷后续
				if(BigDecimalUtil.compareTo(order.getDiscount() , null) > 0) {
					
				}
				//库存更新
			});
		} catch (Exception e) {
			return JsonResultCode.CODE_ORDER_CANCEL_FAIL;
		}
		//订单状态记录
		orderAction.addRecord(order, param, order.getOrderStatus() , "买家取消订单");
		return null;
	}
	
	public void returnOrder(OrderInfoVo order) throws MpException {
		if(BigDecimalUtil.compareTo(order.getScoreDiscount(), null) > 0) {
			//积分
			refundUseAccount(order, order.getScoreDiscount());
		}
		if(BigDecimalUtil.compareTo(order.getUseAccount(), null) > 0) {
			refundUseAccount(order, order.getUseAccount());
			//余额
		}
		if(BigDecimalUtil.compareTo(order.getMemberCardBalance(), null) > 0) {
			//卡余额
			refundMemberCardBalance(order, order.getMemberCardBalance());
		}
	}
	/**
	 * 	退会员卡余额
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @throws MpException 
	 */
	public void refundMemberCardBalance(OrderInfoVo order , BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		UserCardData userCardData = UserCardData.newBuilder().
		userId(order.getUserId()).
		cardId(order.getCardId()).
		cardNo(order.getCardNo()).
		money(money).
		reason("订单取消，订单会员卡余额支付退款").
		//普通会员卡
		type(CardConstant.NORMAL_TYPE).
		orderSn(order.getOrderSn()).
		//后台处理时为操作人id为0
		adminUser(0).
		//用户会员卡余额退款
		tradeType(RecordTradeEnum.MEMBER_CARD_ACCOUNT_REFUND.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).build();
		//调用退会员卡接口
		recordMemberTrade.updateUserEconomicData(userCardData);
	}
	
	/**
	 * 	退余额
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @throws MpException 
	 */
	public void refundUseAccount(OrderInfoVo order , BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		AccountData accountData = AccountData.newBuilder().
		userId(order.getUserId()).
		orderSn(order.getOrderSn()).
		//退款金额
		amount(money).
		remark("订单取消："+order.getOrderSn()+"余额退款").
		payment(order.getPayCode()).
		//支付类型
		isPaid(RecordTradeEnum.RECHARGE.getValue()).
		//后台处理时为操作人id为0
		adminUser(0).
		//用户余额退款
		tradeType(RecordTradeEnum.MEMBER_ACCOUNT_REFUND.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).build();
		//调用退余额接口
		recordMemberTrade.updateUserEconomicData(accountData);
	}
	
	/**
	 * 	积分退款
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundScoreDiscount(OrderInfoVo order , BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		//金额换算成积分
		Integer score = BigDecimalUtil.multiplyOrDivide(
				BigDecimalPlus.create(new BigDecimal(OrderConstant.TUAN_TO_FEN), Operator.multiply),
				BigDecimalPlus.create(money,null)
				).intValue();
				
		ScoreData scoreData = ScoreData.newBuilder().
		userId(order.getUserId()).
		orderSn(order.getOrderSn()).
		//退款积分
		score(score).
		remark("订单取消："+order.getOrderSn()+"退款，退积分：score").
		//后台处理时为操作人id为0
		adminUser(0).
		//用户余额充值 
		tradeType(RecordTradeEnum.POWER_MEMBER_ACCOUNT.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).
		//积分变动是否来自退款
		isFromRefund(RecordTradeEnum.IS_FROM_REFUND_Y.getValue()).build();
		//调用退积分接口
		recordMemberTrade.updateUserEconomicData(scoreData);
	}
	
	/**
	 * 	TODO 需要问一下   更新库存和销量
	 * @param returnGoods
	 * @param order
	 * @param goodsType
	 */
	public void updateStockAndSales(OrderInfoVo order) {
		//TODO 对接pos erp未完成
		boolean isErpPos = false;
		//订单类型
		List<Integer> orderType = Util.splitValueToList(order.getGoodsType());
		if(order.getPayCode() == OrderConstant.PAY_CODE_COD) {
			Result<OrderGoodsRecord> oGoods = orderGoods.getByOrderId(order.getOrderId());
			List<Integer> goodsIds = oGoods.stream().map(OrderGoodsRecord::getGoodsId).collect(Collectors.toList());
			List<Integer> proIds = oGoods.stream().map(OrderGoodsRecord::getProductId).collect(Collectors.toList());
			//查询规格
			Map<Integer, GoodsSpecProductRecord> products = goodsSpecProduct.selectSpecByProIds(proIds);
			//查询商品
			Map<Integer, GoodsRecord> normalGoods = goods.getGoodsByIds(goodsIds);
			for (OrderGoodsRecord record : oGoods) {
				Integer saleNum = normalGoods.get(record.getGoodsId()).getGoodsSaleNum() - record.getGoodsNumber();
				Integer prdNum = products.get(record.getProductId()).getPrdNumber() + record.getGoodsNumber();
				//自提或者对接外系统
				if(order.getDeliverType() == OrderConstant.DELIVER_TYPE_SELF || isErpPos) {
					//原操作还更新商品库存
					normalGoods.get(record.getGoodsId()).setGoodsSaleNum(saleNum);
					products.get(record.getProductId()).setPrdNumber(prdNum);
				}
				if(orderType.contains(Integer.valueOf(OrderConstant.GOODS_TYPE_PIN_GROUP))) {
					//拼团库存修改
				}
				
				if(orderType.contains(Integer.valueOf(OrderConstant.GOODS_TYPE_SECKILL))) {
					//秒杀
				}
			}
		}else if(orderType.contains(Integer.valueOf(OrderConstant.GOODS_TYPE_SECKILL))){
			//秒杀库存
		}
	}
}
