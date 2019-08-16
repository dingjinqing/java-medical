package com.vpu.mp.service.shop.order.refund;

import static com.vpu.mp.db.shop.tables.ReturnOrder.RETURN_ORDER;

import java.math.BigDecimal;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.ReturnOrder;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;

/**
 * Table:return_order
 * @author 王帅
 *
 */
@Service
public class ReturnOrderService extends ShopBaseService{
	
	public final ReturnOrder TABLE = RETURN_ORDER;
	
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
		SelectJoinStep<Record> select = db().select().from(RETURN_ORDER);
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
		select.orderBy(RETURN_ORDER.RET_ID);

		if (!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(RETURN_ORDER.ORDER_SN.eq(param.getOrderSn()));
		}
		if (!StringUtils.isEmpty(param.getReturnOrderSn())) {
			select.where(RETURN_ORDER.RETURN_ORDER_SN.eq(param.getReturnOrderSn()));
		}
		if (param.getRefundStatus() != null && param.getRefundStatus().length != 0) {
			select.where(RETURN_ORDER.REFUND_STATUS.in(param.getRefundStatus()));
		}
		if (param.getReturnType() != null && param.getReturnType().length != 0) {
			select.where(RETURN_ORDER.RETURN_TYPE.in(param.getReturnType()));
		}
		if (param.getReturnStart() != null) {
			select.where(RETURN_ORDER.APPLY_TIME.ge(param.getReturnStart()));
		}
		if (param.getReturnEnd() != null) {
			select.where(RETURN_ORDER.APPLY_TIME.le(param.getReturnStart()));
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
}
