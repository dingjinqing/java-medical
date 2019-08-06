package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawAddParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListParam;

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

    /**
     * 参与用户
     */
    @PostMapping("/join_user/list")
    public JsonResult getJoinUserList(@RequestBody @Valid JoinUserListParam param) {
        return success(shop().groupDrawUsers.getJoinUserList(param));
    }
}
