package com.vpu.mp.controller.system;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.saas.user.MainUserPageListParam;
import com.vpu.mp.service.saas.user.MainUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2020年08月17日
 */
@RestController
public class SystemUserController extends SystemBaseController{

    @Autowired
    private MainUserService mainUserService;

    @PostMapping("/api/system/user/page/list")
    public JsonResult getPageList(@RequestBody MainUserPageListParam pageListParam){
        return success(mainUserService.getPageList(pageListParam));
    }
}
