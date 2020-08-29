package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.common.pojo.shop.table.StoreDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.OrderRepurchaseParam;
import com.vpu.mp.service.pojo.shop.order.OrderRepurchaseVo;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.PayParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayDetailsParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionMakeParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.address.OrderAddressParam;
import com.vpu.mp.service.pojo.wxapp.order.history.OrderGoodsHistoryListParam;
import com.vpu.mp.service.pojo.wxapp.order.validated.CreateOrderValidatedGroup;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.store.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author 王帅
 *
 */
@RestController
@RequestMapping("/api/wxapp/order")
public class WxAppOrderController extends WxAppBaseController{
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private StoreService storeService;

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
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 	结算页面提交
     */
    @PostMapping("/submit")
    @RedisLock(prefix = JedisKeyConstant.NotResubmit.ORDER_SUBMIT, noResubmit = true)
    public JsonResult submit(@RequestBody @Validated(CreateOrderValidatedGroup.class) CreateParam param) {
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        param.setClientIp(RequestUtil.getIp(request));
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
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
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }


    /**
     * 审核
     * -通过
     * -失败
     * @return
     */
    @PostMapping("/audit")
    public JsonResult auditOrderInfo(){
        WxAppSessionUser user = wxAppAuth.user();

        return success();
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
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
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
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
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
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }

    /**
     * 订单列表
     */
    @PostMapping("/list")
    public JsonResult list(@RequestBody @Valid OrderListParam param) {
        param.setWxUserInfo(wxAppAuth.user());
        return success(shop().readOrder.getPageList(param));
    }

    /**
     * 订单列表
     */
    @PostMapping("/refund/list/search")
    public JsonResult mpReturnList(@RequestBody @Valid OrderListParam param) {
        param.setWxUserInfo(wxAppAuth.user());
        return success(shop().readOrder.mpReturnList(param));
    }

	/**
	 * 订单详情
	 */
	@PostMapping("/get")
	public JsonResult get(@RequestBody @Valid OrderParam param) {
		try {
			return success(shop().readOrder.mpGet(param));
		} catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
		}
	}

    /**
     * 再次购买
     * @return
     */
	@PostMapping("/repurchase")
	public JsonResult orderRepurchase(@RequestBody @Valid OrderRepurchaseParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        OrderRepurchaseVo orderRepurchaseVo = shop().writeOrder.orderRepurchase(param);
        if (orderRepurchaseVo.getResultMessage()!=null&&!orderRepurchaseVo.getResultMessage().getFlag()){
            return fail(orderRepurchaseVo.getResultMessage());
        }
        return success(orderRepurchaseVo);
    }
	/**
	 * 统计数量（废弃）
	 */
	@Deprecated
	@PostMapping("/statistic")
	public JsonResult statistic(@RequestBody @Valid OrderListParam param) {
		param.setWxUserInfo(wxAppAuth.user());
		return success(shop().readOrder.statistic(wxAppAuth.user().getUserId()));
	}


    /**
     * 售后中心退款订单列表(存在退款)
     * @param param
     */
    @PostMapping("/refund/list")
	public JsonResult mpOrderReturnList(@RequestBody @Valid OrderParam param){
        try {
            return success(shop().readOrder.mpOrderReturnList(param));
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 售后中心退款订单详情
     * @param param
     */
    @PostMapping("/refund/info")
    public JsonResult mpReturnInfo(@RequestBody @Valid ReturnOrderParam param){
        try {
            return success(shop().readOrder.getReturnOrder(param));
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 历史购买
     * @param param
     */
    @PostMapping("/goods/history")
    public JsonResult getHistoryGoodsList(@RequestBody @Valid OrderGoodsHistoryListParam param){
        Integer userId = wxAppAuth.user().getUserId();
        FootprintListVo historyVos = shop().readOrder.buyingHistoryGoodsList(userId, param.getKeyword(), param.getCurrentPage(), param.getPageRows());
        return success(historyVos);
    }

    /**
     * 支持快递公司
     */
    @PostMapping("/express")
    public JsonResult getExpress(){
        return success(shop().express.getEnabledList());
    }

    /**************好友代付start*******************/

    /**
     * 代付详情
     */
    @PostMapping("/pay/instead/detail")
    public JsonResult insteadDetail(@RequestBody @Valid InsteadPayDetailsParam param){
        param.setWxUserInfo(wxAppAuth.user());
        try {
            return success(shop().readOrder.insteadPayInfo(param));
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 代付支付页
     */
    @PostMapping("/pay/instead")
    public JsonResult insteadPay(@RequestBody @Valid OrderOperateQueryParam param){
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        try {
            return success(shop().orderActionFactory.orderQuery(param));
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 代付支付
     */
    @PostMapping("/pay/instead/submit")
    public JsonResult insteadPaySubmit(@RequestBody @Valid InsteadPayParam param){
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setWxUserInfo(wxAppAuth.user());
        param.setClientIp(RequestUtil.getIp(request));
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }

    /**
     * 代付用户明细
     */
    @PostMapping("/pay/instead/userList")
    public JsonResult insteadPayUserList(@RequestBody @Valid InsteadPayDetailsParam param) {
        return success(shop().readOrder.subOrderService.paymentDetails(param.getOrderSn(), param.getCurrentPage(), param.getPageRows()));
    }

    /**
     * 好友代付分享图
     */
    @PostMapping("/pay/instead/share")
    public JsonResult insteadShareImage(@RequestBody @Valid OrderGoodsHistoryListParam param){
        return success();
    }
    /**************好友代付end*********************/

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

    /**
     * 获取待开方的订单
     * @param param
     * @return
     */
    @PostMapping("/prescribe/medical/get")
    public JsonResult getMakePrescriptionOrder(@RequestBody OrderToPrescribeQueryParam param){
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setAction((byte)OrderServiceCode.MAKE_PRESCRIPTION.ordinal());
        param.setWxUserInfo(wxAppAuth.user());
        try {
            return(success(shop().orderActionFactory.orderQuery(param)));
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 待开方的生成处方
     * @param param
     * @return
     */
    @PostMapping("/prescription/make")
    public JsonResult makePrescription(@RequestBody PrescriptionMakeParam param){
        param.setIsMp(OrderConstant.IS_MP_Y);
        param.setAction((byte)OrderServiceCode.MAKE_PRESCRIPTION.ordinal());
        ExecuteResult executeResult=shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success();
        }
        return result(executeResult.getErrorCode(),executeResult.getResult());
    }

    /**
     * 按距排序门店列表
     * @param orderAddressParam 用户地址
     * @return JsonResult
     */
    @PostMapping("/get/store")
    public JsonResult getClosestStoreInformation(@RequestBody OrderAddressParam orderAddressParam) {
        if (orderAddressParam.getLat() == null || orderAddressParam.getLng() == null) {
            return fail(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        Map<Double, StoreDo> storeListOpen = storeService.getStoreListOpen(orderAddressParam);
        return success(storeListOpen);
    }


}
