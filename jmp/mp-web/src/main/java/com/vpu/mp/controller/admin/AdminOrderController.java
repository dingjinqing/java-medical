package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.money.RefundMoneyParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;

/**
 * 	订单模块
 * 
 * @author 常乐,王帅 2019年6月27日
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AdminBaseController {
	/**
	 * 	订单综合查询（不包括买单订单、虚拟商品订单）
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult orderList(@RequestBody OrderPageListQueryParam param) {
		PageResult<? extends OrderListInfoVo> result = shop().readOrder.getPageList(param);
		return success(result);
	}

	/**
	 * 	买单订单列表查询
	 */
	@PostMapping("/store/list")
	public JsonResult storyOrderList(@RequestBody StoreOrderPageListQueryParam param) {
		PageResult<StoreOrderListInfoVo> result = shop().readOrder.getPageList(param);
		return success(result);
	}

	/**
	 * 	订单详情（不包括退款货、买单订单、虚拟商品订单）
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping("/get")
	public JsonResult get(@RequestBody OrderParam order) {
		return success(shop().readOrder.get(order.getMainOrderSn()));
	}

	/**
	 * 	订单标星切换
	 */
	@PostMapping("/star")
	public JsonResult switchStar(@RequestBody StarParam param) {
		shop().writeOrder.switchStar(param);
		return success();
	}
	
	/**
	 * 	更新卖家备注
	 */
	@PostMapping("/sellerRemark")
	public JsonResult sellerRemark(@RequestBody @Valid SellerRemarkParam param) {
		return success(shop().writeOrder.sellerRemark(param));
	}
	
	/**
	 * 	发货_查询可发货商品
	 */
	@PostMapping("/shipGoods")
	public JsonResult shipGoodsList(@RequestBody @Valid OrderOperateQueryParam param) {
		return success(shop().orderOperateQuery.shipGoodsList(param));
	}
	
	/**
	 * 	发货
	 */
	@PostMapping("/ship")
	public JsonResult ship(@RequestBody @Valid ShipParam param) {
		JsonResultCode code = shop().writeOrder.ship(param);
		return code == null ? success() : fail(code);
	}
	
	/**
	 * 	退款、退货查询
	 */
	@PostMapping("/refund/list")
	public JsonResult refundGoodsList(@RequestBody OrderOperateQueryParam param) {
		shop().orderOperateQuery.refundGoodsList(param);
		return null == null ? success() : fail();
	}
	
	/**
	 * 退款
	 */
	@PostMapping("/refund/money")
	public JsonResult refundMoney(@RequestBody RefundMoneyParam param) {
		JsonResultCode code = shop().orderActionFactory.orderOperate(param);
		return code == null ? success() : fail(code);
	}
}
