package com.vpu.mp.service.shop.order.refund;

import static com.vpu.mp.db.shop.tables.ReturnOrder.RETURN_ORDER;

import java.math.BigDecimal;
import java.util.Random;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnOrder;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;

/**
 * Table:return_order
 * @author 王帅
 *
 */
@Service
public class ReturnOrderService extends ShopBaseService{
	
	public final ReturnOrder TABLE = RETURN_ORDER;
	
	public final static int ORDER_SN_SUFFIX_MAX_RANDOM = 9999;
	
	public final static int ORDER_SN_SUFFIX_MIN_RANDOM = 1000;
	
	/**
	 * 	通过订单[]查询其下退货订单信息
	 * @param arrayToSearch
	 * @return Result<?>
	 */
	public Result<?> getRefundByOrderSn(String... arrayToSearch) {
		Result<?> goods = db().select(TABLE.asterisk()).from(TABLE)
				.where(TABLE.ORDER_SN.in(arrayToSearch))
				.orderBy(TABLE.RET_ID.desc())
				.fetch();
		return goods;	
	}
	
	/**
	 * 	综合查询退订单
	 * @param param
	 * @return
	 */
	public PageResult<OrderReturnListVo> getPageList(OrderPageListQueryParam param) {
		SelectJoinStep<Record> select = db().select().from(TABLE);
		buildOptionsReturn(select,param);	
		PageResult<OrderReturnListVo> result = getPageResult(select,param.getCurrentPage(),param.getPageRows(),OrderReturnListVo.class);
		return result;
	}
	
	/**
	 * 构造退货、款查询条件
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<?> buildOptionsReturn(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		// 自增id排序
		select.orderBy(TABLE.RET_ID);

		if (!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(TABLE.ORDER_SN.eq(param.getOrderSn()));
		}
		if (!StringUtils.isEmpty(param.getReturnOrderSn())) {
			select.where(TABLE.RETURN_ORDER_SN.eq(param.getReturnOrderSn()));
		}
		if (param.getRefundStatus() != null && param.getRefundStatus().length != 0) {
			select.where(TABLE.REFUND_STATUS.in(param.getRefundStatus()));
		}
		if (param.getReturnType() != null && param.getReturnType().length != 0) {
			select.where(TABLE.RETURN_TYPE.in(param.getReturnType()));
		}
		if (param.getReturnStart() != null) {
			select.where(TABLE.APPLY_TIME.ge(param.getReturnStart()));
		}
		if (param.getReturnEnd() != null) {
			select.where(TABLE.APPLY_TIME.le(param.getReturnStart()));
		}
		return select;
	}
	
	/**
	 * 	获取该订单的退成功运费记录（送礼订单无运费）
	 * @param orderSn 订单号
	 * @return 存在退运费成功则返回金额，否则为0
	 */
	public BigDecimal getReturnShipingFee(String orderSn) {
		Record1<BigDecimal> fetchOne = db().select(DSL.sum(TABLE.SHIPPING_FEE)).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn).and(TABLE.REFUND_STATUS.eq(OrderConstant.REFUND_STATUS_FINISH))).fetchOne();
		return fetchOne.value1() == null ? BigDecimal.ZERO : fetchOne.value1();
	}
	
	/**
	 * 	获取该订单的退成功商品金额
	 * @param orderSn 订单号
	 * @return 存在则返回金额，否则为0
	 */
	public BigDecimal getReturnMoney(String orderSn) {
		Record1<BigDecimal> fetchOne = db().select(DSL.sum(TABLE.MONEY)).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn).and(TABLE.REFUND_STATUS.eq(OrderConstant.REFUND_STATUS_FINISH))).fetchOne();
		return fetchOne.value1() == null ? BigDecimal.ZERO : fetchOne.value1();
	}
	/**
	 * 	增加退货/退款申请记录->形成退款/退货订单
	 * 	status：退货 1；else 4
	 * @param param
	 * @param order
	 * @return returnOrder
	 */
	public ReturnOrderRecord addRecord(RefundParam param , OrderInfoVo order) {
		ReturnOrderRecord returnOrder = db().newRecord(TABLE);
		returnOrder.setOrderId(order.getOrderId());
		returnOrder.setOrderSn(order.getOrderSn());
		returnOrder.setReturnOrderSn(generateReturnOrderSn());
		returnOrder.setReturnType(param.getReturnType());
		returnOrder.setMoney(param.getReturnMoney() == null ? BigDecimal.ZERO : param.getReturnMoney());
		returnOrder.setReasonType(param.getReasonType() == null ? 0 :param.getReasonType());
		returnOrder.setReasonDesc(param.getReasonDesc());
		returnOrder.setGoodsImages(param.getGoodsImages());
		returnOrder.setVoucherImages(param.getVoucherImages());
		returnOrder.setUserId(order.getUserId());
		returnOrder.setShopId(getShopId());
		//除退货外,refund_status为4
		returnOrder.setRefundStatus(param.getReturnType() == OrderConstant.RT_GOODS ? OrderConstant.REFUND_STATUS_AUDITING : OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);
		if(param.getReturnType() == OrderConstant.RT_GOODS) {
			//退货->申请时间
			returnOrder.setApplyTime(DateUtil.getSqlTimestamp());
		}else {
			//非退货->申请时间
			returnOrder.setShippingOrRefundTime(DateUtil.getSqlTimestamp());
		}
		returnOrder.insert();
		logger().info("新增退款/退货订单:"+returnOrder.toString());
		return returnOrder;
	}
	
	/**
	 * 生成退款订单号
	 * @return
	 */
	public String generateReturnOrderSn() {
		while(true) {
			String returnOrderSn = "R" + DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE) + (ORDER_SN_SUFFIX_MIN_RANDOM + new Random().nextInt(ORDER_SN_SUFFIX_MAX_RANDOM - ORDER_SN_SUFFIX_MIN_RANDOM));
			if(db().fetchCount(TABLE,TABLE.RETURN_ORDER_SN.eq(returnOrderSn)) < 1){
				return returnOrderSn;
			}
		}
	}
	
	public void finishReturn(ReturnOrderRecord returnOrder) throws MpException {
		if(OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING != returnOrder.getRefundStatus()) {
			throw new MpException(JsonResultCode.CODE_ORDER_FINISH_RETURN_STATUS_ERROR);
		}
		returnOrder.setRefundSuccessTime(DateUtil.getSqlTimestamp());
		returnOrder.setRefundStatus(OrderConstant.REFUND_STATUS_FINISH);
		//TODO 如果存在拆单需要修改一些状态
		returnOrder.update();
	}
	
}
