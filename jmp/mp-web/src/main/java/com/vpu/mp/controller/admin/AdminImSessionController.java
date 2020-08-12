package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionQueryPageListParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionItemRenderVo;
import com.vpu.mp.service.shop.im.ImSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangpengcheng
 * @date 2020/8/12
 **/
@RestController
public class AdminImSessionController extends AdminBaseController{
    @Autowired
    private ImSessionService imSessionService;

    /**
     * 根据订单号获取历史聊天记录
     * @param param
     * @return
     */
    @PostMapping("/api/admin/im/session/history")
    public JsonResult getSessionHistory(@RequestBody ImSessionQueryPageListParam param){
        PageResult<ImSessionItemRenderVo> pageResult=imSessionService.getSessionHistory(param);
        return success(pageResult);
    }
}
