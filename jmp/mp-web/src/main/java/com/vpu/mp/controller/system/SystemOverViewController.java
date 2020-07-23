package com.vpu.mp.controller.system;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.overview.LoginRecordVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author  常乐
 * @Date 2019-12-03
 */
@RestController
@RequestMapping("api/system/overView")
public class SystemOverViewController extends SystemBaseController{
    /**
     * 用户登录日志
     * @param param
     * @return
     */
    @PostMapping("/loginRecord")
    public JsonResult loginRecord(@RequestBody LoginRecordVo param){
        PageResult<LoginRecordVo> res = saas.overviewService.loginRecord(param);
        return this.success(res);
    }
}
