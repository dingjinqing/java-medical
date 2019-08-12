package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/enable/{id}")
    public JsonResult enableActivity(@PathVariable Integer id) {
        shop().activity.enableActivity(id);
        return success();
    }

    @PostMapping("/delete/{id}")
    public JsonResult deleteActivity(@PathVariable Integer id) {
        shop().activity.deleteActivity(id);
        return success();
    }

    @PostMapping("/add")
    public JsonResult addActivity(@RequestBody @Valid ActivityParam param) {
        shop().activity.addActivity(param);
        return success();
    }
}
