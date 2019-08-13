package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityIssueListParam;
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

    /**
     * 活动列表
     */
    @PostMapping("/list")
    public JsonResult getPageList(@RequestBody ActivityListParam param) {
        return success(shop().activity.getPageList(param));
    }

    /**
     * 停用活动
     */
    @PostMapping("/disable/{id}")
    public JsonResult disableActivity(@PathVariable Integer id) {
        shop().activity.disableActivity(id);
        return success();
    }

    /**
     * 启用活动
     */
    @PostMapping("/enable/{id}")
    public JsonResult enableActivity(@PathVariable Integer id) {
        shop().activity.enableActivity(id);
        return success();
    }

    /**
     * 删除活动
     */
    @PostMapping("/delete/{id}")
    public JsonResult deleteActivity(@PathVariable Integer id) {
        shop().activity.deleteActivity(id);
        return success();
    }

    /**
     * 创建活动
     */
    @PostMapping("/add")
    public JsonResult addActivity(@RequestBody @Valid ActivityParam param) {
        shop().activity.addActivity(param);
        return success();
    }

    /**
     * 活动修改 - 明细
     */
    @PostMapping("/detail/{id}")
    public JsonResult getActivityDetail(@PathVariable Integer id) {
        return success(shop().activity.getActivityDetail(id));
    }

    /**
     * 活动修改 - 更新
     */
    @PostMapping("/update")
    public JsonResult updateActivity(@RequestBody @Valid ActivityParam param) {
        shop().activity.updateActivity(param);
        return success();
    }

    /**
     * 发放明细
     */
    @PostMapping("/issue_detail")
    public JsonResult issueDetail(@RequestBody ActivityIssueListParam param) {
        return success(shop().issue.getIssuePageList(param));
    }

    /**
     * 获取可用的抽奖活动
     */
    @PostMapping("/lottery")
    public JsonResult getAvailableLotteries() {
        return success(shop().activity.getAvailableLotteries());
    }
}
