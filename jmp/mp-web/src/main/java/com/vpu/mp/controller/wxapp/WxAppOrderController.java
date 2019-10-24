package com.vpu.mp.controller.wxapp;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;

/**
 * 订单
 * @author 王帅
 *
 */
@RestController
@RequestMapping("/api/wxapp/order")
public class WxAppOrderController extends WxAppBaseController{
	
	/**
	 * 	退款、退货查询
	 */
	@PostMapping("/refund/list")
	public JsonResult mpRefundGoodsList(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_Y);
		try {
			return success(shop().orderActionFactory.orderQuery(param));
		} catch (MpException e) {
			return fail(e.getErrorCode());
		}
	}
	
	/**
	 * 退款退货
	 */
	@PostMapping("/refund")
	public JsonResult refundMoney(@RequestBody @Valid RefundParam param) {
		param.setIsMp(OrderConstant.IS_MP_Y);
		param.setWxUserInfo(wxAppAuth.user().getWxUser());
		JsonResultCode code = shop().orderActionFactory.orderOperate(param);
		return code == null ? success() : fail(code);
	}
	
	/**
	 * 延长收货、确认收货、取消订单、提醒发货、删除订单
	 */
	@PostMapping("/operation")
	public JsonResult cancel(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_Y);
		param.setWxUserInfo(wxAppAuth.user().getWxUser());
		JsonResultCode code = shop().orderActionFactory.orderOperate(param);
		return code == null ? success() : fail(code);
	}
	
	/**
	 * 订单列表
	 */
	@PostMapping("/list")
	public JsonResult list(@RequestBody @Valid OrderListParam param) {
		param.setWxUserInfo(wxAppAuth.user());
		PageResult<OrderListMpVo> result = shop().readOrder.getPageList(param);
		return success(result);
	}
	
	/**
	 * 订单详情
	 */
	@PostMapping("/get")
	public JsonResult get(@RequestBody @Valid OrderParam param) {
		try {
			return success(shop().readOrder.mpGet(param));
		} catch (MpException e) {
			return fail(e.getErrorCode());
		}
	}
	
	/**
	 * 统计数量
	 */
	@PostMapping("/statistic")
	public JsonResult statistic(@RequestBody @Valid OrderListParam param) {
		param.setWxUserInfo(wxAppAuth.user());
		return success(shop().readOrder.statistic(param));
	}
}
