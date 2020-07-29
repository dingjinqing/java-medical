package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.recharge.RechargeParam;
import com.vpu.mp.service.shop.recharge.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-27 17:33
 **/

@RestController
@RequestMapping("/api/admin/recharge")
public class AdminRechargeController extends AdminBaseController{

    @Autowired
    private RechargeService rechargeService;

    @RequestMapping("/list")
    public JsonResult rechargeList(@RequestBody RechargeParam rechargeParam){
        return super.success(rechargeService.getRechargeList(rechargeParam));
    }

}
