package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawAddParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * 创建活动
     */
    @PostMapping("/add")
    public JsonResult addGroupDraw(@RequestBody @Valid GroupDrawAddParam param) {
        shop().groupDraw.addGroupDraw(param);
        return success();
    }

    /**
     * 删除活动
     */
    @PostMapping("/delete/{id}")
    public JsonResult deleteGroupDraw(@PathVariable Integer id) {
        shop().groupDraw.deleteGroupDraw(id);
        return success();
    }
}
