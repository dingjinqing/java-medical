package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawAddParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/group_draw")
public class AdminGroupDrawController extends AdminBaseController {

    /**
     * 拼团抽奖列表
     */
    @PostMapping("/list")
    public JsonResult getGroupDrawPageList(@RequestBody GroupDrawListParam param) {
        return success(shop().groupDraw.getGroupDrawList(param));
    }

    /**
     * 添加活动
     */
    @PostMapping("/add")
    public JsonResult addGroupDraw(@RequestBody GroupDrawAddParam param) {
        shop().groupDraw.addGroupDraw(param);
        return success();
    }
}
