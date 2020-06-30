package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.data.JsonResultMessage;
import com.vpu.mp.common.foundation.util.DateUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.virtual.AnalysisParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;
import com.vpu.mp.service.shop.order.virtual.VirtualOrderService;
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
	public JsonResult refundPackOrder(@RequestBody @Valid CouponPackOrderRefundParam param) throws MpException {
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

    /**
     * 优惠券礼包订单数据分析
     */
    @PostMapping("/analysis")
    public JsonResult orderAnalysis(@RequestBody AnalysisParam param) {
        return success(shop().couponPackOrder.getAnalysisData(param, VirtualOrderService.GOODS_TYPE_COUPON_PACK));
    }

}

