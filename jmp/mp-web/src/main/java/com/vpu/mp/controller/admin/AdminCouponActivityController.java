package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam;
import org.springframework.web.bind.annotation.*;

/**
 * 活动有礼
 *
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/market/activity_reward")
public class AdminCouponActivityController extends AdminBaseController {

    @PostMapping("/list")
    public JsonResult getPageList(@RequestBody ActivityListParam param) {
        return success(shop().activity.getPageList(param));
    }

    @PostMapping("/disable/{id}")
    public JsonResult disableActivity(@PathVariable Integer id) {
        shop().activity.disableActivity(id);
        return success();
    }
}
