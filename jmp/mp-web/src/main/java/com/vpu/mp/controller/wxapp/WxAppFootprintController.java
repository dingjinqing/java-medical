package com.vpu.mp.controller.wxapp;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  足迹
 * @author 孔德成
 * @date 2019/11/4 10:52
 */
@RestController
@RequestMapping("/api/wxapp/footprint")
public class WxAppFootprintController extends WxAppBaseController {


    @PostMapping("/list")
    private void getFootprintList(){
        Integer userId = wxAppAuth.user().getUserId();
        shop().footPrintService.getfootPrintNum(userId);
    }

}
