package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.asset.RevenueProfileParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liufei
 * @date 2019/8/2
 * @description 资产管理
 */
@RestController
public class AdminAssetManagementController extends AdminBaseController{
    /**
     * 营收概况
     */
    @PostMapping("/api/admin/assetmanagement/revenueprofile")
    public JsonResult revenueprofile(@RequestBody RevenueProfileParam param){
        return success(shop().assetService.revenueprofile(param));
    }
}
