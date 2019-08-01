package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardRefundParam;
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
    public JsonResult orderPageList(@RequestBody MemberCardParam param) {
        return success(shop().memberCardOrder.getMemberCardOrderList(param));
    }

    @PostMapping("/refund")
    public JsonResult orderRefund(@RequestBody MemberCardRefundParam param) {
        shop().memberCardOrder.memberCardOrderRefund(param);
        return success();
    }
}
