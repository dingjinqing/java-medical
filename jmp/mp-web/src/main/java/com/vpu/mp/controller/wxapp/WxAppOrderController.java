package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.RequestUtil;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.PayParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.history.OrderGoodsHistoryListParam;
import com.vpu.mp.service.pojo.wxapp.order.validated.CreateOrderValidatedGroup;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * @author 王帅
 *
 */
@RestController
@RequestMapping("/api/wxapp/order")
public class WxAppOrderController extends WxAppBaseController{

    /**
     * 	结算页面
     */
    @PostMapping("")
    public JsonResult order(@RequestBody @Valid OrderBeforeParam param) {
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        try {
            return success(shop().orderActionFactory.orderQuery(param));
        } catch (MpException e) {
            return fail(e.getErrorCode());
        }
    }

    /**
     * 	结算页面提交
     */
    @PostMapping("/submit")
    public JsonResult submit(@RequestBody @Validated(CreateOrderValidatedGroup.class) CreateParam param) {
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        param.setClientIp(RequestUtil.getIp(request));
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
        }
    }

    /**
     * 	支付
     */
    @PostMapping("/pay")
    public JsonResult pay(@RequestBody PayParam param) {
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        param.setClientIp(RequestUtil.getIp(request));
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
        }
    }

	/**
	 * 	退款、退货创建页面
	 */
	@PostMapping("/refund/query")
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
		param.setWxUserInfo(wxAppAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
        }
	}

	/**
	 * 延长收货、确认收货、取消订单、提醒发货、删除订单
	 */
	@PostMapping("/operation")
	public JsonResult cancel(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_Y);
		param.setWxUserInfo(wxAppAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
        }
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

    /**
     * 售后中心退款订单列表(存在退款)
     * @param param
     * @return
     */
    @PostMapping("/refund/list")
	public JsonResult mpReturnList(@RequestBody @Valid OrderParam param){
        try {
            return success(shop().readOrder.mpReturnList(param));
        } catch (MpException e) {
            return fail(e.getErrorCode(), e.getCodeParam());
        }
    }

    /**
     * 售后中心退款订单详情
     * @param param
     * @return
     */
    @PostMapping("/refund/info")
    public JsonResult mpReturnInfo(@RequestBody @Valid ReturnOrderParam param){
        try {
            return success(shop().readOrder.getReturnOrder(param));
        } catch (MpException e) {
            return fail(e.getErrorCode(), e.getCodeParam());
        }
    }

	/**
	 * 历史购买
	 * @param param
	 * @return
	 */
	@PostMapping("/goods/history")
	public JsonResult getHistoryGoodsList(@RequestBody @Valid OrderGoodsHistoryListParam param){
		Integer userId = wxAppAuth.user().getUserId();
		FootprintListVo historyVos = shop().readOrder.buyingHistoryGoodsList(userId, param.getKeyword(), param.getCurrentPage(), param.getPageRows());
		return success(historyVos);
	}

	@PostMapping("/addtest")
	public JsonResult test(){
		List<String> list=new ArrayList<String>();
		list.add("P201911221631543263");
		list.add("P201911221635533137");
		Boolean addCommonOrders = shop().recommendService.orderMallService.addCommonOrders(wxAppAuth.user().getUserId(), list);
		if(addCommonOrders) {
			return success();
		}
		return fail();

	}

	@PostMapping("/cart/addtest")
	public JsonResult test2(){
		List<Integer> list=new ArrayList<>();
		list.add(60);
		list.add(62);
		Boolean addCommonOrders = shop().recommendService.collectionMallService.addCartRows(wxAppAuth.user().getUserId(), list);
		if(addCommonOrders) {
			return success();
		}
		return fail();

	}
}
