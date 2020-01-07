package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawAddParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawShareParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawUpdateParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.group.GroupListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.invite.InvitedUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderListParam;

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
     * 禁用活动
     */
    @PostMapping("/disable/{id}")
    public JsonResult disableGroupDraw(@PathVariable Integer id) {
        shop().groupDraw.disableGroupDraw(id);
        return success();
    }

    /**
     * 编辑活动 - 查询
     */
    @PostMapping("/detail/{id}")
    public JsonResult groupDrawDetail(@PathVariable Integer id) {
        return success(shop().groupDraw.getGroupDrawById(id));
    }

    /**
     * 编辑活动 - 更新
     */
    @PostMapping("/update")
    public JsonResult updateGroupDraw(@RequestBody @Valid GroupDrawUpdateParam param) {
        shop().groupDraw.updateGroupDraw(param);
        return success();
    }

    /**
     * 参与用户
     */
    @PostMapping("/join_user/list")
    public JsonResult getJoinUserList(@RequestBody @Valid JoinUserListParam param) {
        return success(shop().groupDrawUsers.getJoinUserList(param));
    }

    /**
     * 活动订单
     */
    @PostMapping("/order/list")
    public JsonResult getGroupDrawOrderList(@RequestBody @Valid OrderListParam param) {
        return success(shop().groupDrawOrders.getGroupDrawOrderList(param));
    }

    /**
     * 新用户
     */
    @PostMapping("/invited_user/list")
    public JsonResult getInvitedUserList(@RequestBody @Valid InvitedUserListParam param) {
        return success(shop().groupDrawUsers.getInvitedUserList(param));
    }

    /**
     * 开团明细
     */
    @PostMapping("/group/list")
    public JsonResult getOpenGroupDetailList(@RequestBody GroupListParam param) {
        return success(shop().groupDrawGroups.getGroupList(param));
    }

    /**
     * 活动分享
     */
    @PostMapping("/share")
    public JsonResult getGroupDrawShare(@RequestBody GroupDrawShareParam param) throws Exception {
        return success(shop().groupDraw.getMpQRCode(param));
    }
    /**
     * 活动分享
     */
    @PostMapping("/effect")
    public JsonResult getGroupDrawEffect(@RequestBody GroupDrawAnalysisParam param)  {
        return success(shop().groupDraw.groupDrawAnalysis(param));
    }
}
