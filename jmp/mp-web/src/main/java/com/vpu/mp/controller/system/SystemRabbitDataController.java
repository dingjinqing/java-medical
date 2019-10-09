package com.vpu.mp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.saas.SaasApplication;

@RestController
public class SystemRabbitDataController extends SystemBaseController {


    @Autowired
    SaasApplication saasApplication;

    @PostMapping("/system/store_management/detail")
    public JsonResult getRabbitDetail(){
        return success(saasApplication.rabbitDataService.getDetails());
    }
}
