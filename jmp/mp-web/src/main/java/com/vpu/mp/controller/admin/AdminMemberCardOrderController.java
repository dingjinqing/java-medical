package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.virtual.AnalysisParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardOrderParam;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderRefundParam;
import com.vpu.mp.service.shop.order.virtual.VirtualOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 虚拟订单 - 会员卡订单
 *
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/order/member_card")
public class AdminMemberCardOrderController extends AdminBaseController {

    @PostMapping("/list")
    public JsonResult orderPageList(@RequestBody MemberCardOrderParam param) {
        return success(shop().memberCardOrder.getMemberCardOrderList(param));
    }

    @PostMapping("/refund")
    public JsonResult orderRefund(@RequestBody VirtualOrderRefundParam param) throws MpException {
        if (shop().couponPackOrder.checkVirtualOrderRefundParam(param)) {
            JsonResultCode result = shop().memberCardOrder.memberCardOrderRefund(param);
            return result == null ? success() : fail(result);
        } else {
            return fail(JsonResultMessage.REFUND_REQUEST_PARAMETER_ERROR);
        }
    }

    @PostMapping("/analysis")
    public JsonResult orderAnalysis(@RequestBody AnalysisParam param) {
        return success(shop().memberCardOrder.getAnalysisData(param, VirtualOrderService.GOODS_TYPE_MEMBER_CARD));
    }
}
