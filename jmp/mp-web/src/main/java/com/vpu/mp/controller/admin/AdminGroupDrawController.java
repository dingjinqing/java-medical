package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListVo;
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
    public PageResult<GroupDrawListVo> getGroupDrawPageList(@RequestBody GroupDrawListParam param) {
        return shop().groupDraw.getGroupDrawList(param);
    }
}
