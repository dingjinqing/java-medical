package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.config.center.UserCenterConfigParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 个人中心配置
 * @author 孔德成
 * @date 2019/7/11 11:25
 */
@RestController
@RequestMapping("/api")
public class AdminUserCenterConfigController extends AdminBaseController{


    /**
     * 个人中心获取配置
     *
     * @return
     */
    @GetMapping("/admin/user/center/config/get")
    public JsonResult getUserCenterConfig(){
       return success(shop().config.userCenterConfigService.getkUserCenterConfig());
    }

    /**
     * 设置个人中心配置
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/user/center/config/update")
    public JsonResult updateUserCenterConfig(@RequestBody List<UserCenterConfigParam> param){
        shop().config.userCenterConfigService.updateUserCenterConfig(param);
        return success();
    }

}
