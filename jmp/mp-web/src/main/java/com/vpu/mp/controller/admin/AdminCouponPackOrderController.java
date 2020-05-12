package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author huangronggang
 * @date 2019年8月2日
 * 虚拟订单 ---优惠劵
 */
@RestController
@RequestMapping("/api/admin/order/couponpack")
public class AdminCouponPackOrderController extends AdminBaseController {
	
	/** 
	 * 分页查询 优惠劵包 虚拟订单列表
	 * */
	@PostMapping("/list")
	public JsonResult couponPackOrderList(@RequestBody CouponPackOrderPageParam param) {
		return success(shop().couponPackOrder.getPageList(param));
	}
	/**
	 * 虚拟商品订单 优惠劵包订单 退款
	 * @param param
	 * @return
	 */
	@PostMapping("/refund")
	public JsonResult refundPackOrder(@RequestBody @Valid CouponPackOrderRefundParam param) {
	    if(shop().couponPackOrder.checkVirtualOrderRefundParam(param.getVirtualOrderRefundParam())){
            JsonResultCode result = shop().couponPackOrder.refundCouponPackOrder(param);
            return result == null ? success() : fail(result);
        }else{
	        return fail(JsonResultMessage.REFUND_REQUEST_PARAMETER_ERROR);
        }
	}

    /**
     * 取将要导出的列数
     */
    @PostMapping("/export/rows")
    public JsonResult getExportTotalRows(@RequestBody @Valid CouponPackOrderPageParam param) {
        return success(shop().couponPackOrder.getExportRows(param));
    }

    @PostMapping("/export")
    public void export(@RequestBody @Valid CouponPackOrderPageParam param, HttpServletResponse response) {
        Workbook workbook = shop().couponPackOrder.exportOrderList(param, getLang());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.VIRTUAL_ORDER_COUPON_PACK_FILE_NAME, "excel", "excel") + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook, fileName, response);
    }
	
}

