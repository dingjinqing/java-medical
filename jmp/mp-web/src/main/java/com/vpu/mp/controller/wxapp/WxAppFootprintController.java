package com.vpu.mp.controller.wxapp;


import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListParam;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    /**
     * 订单列表
     * @return
     */
    @PostMapping("/list")
    private FootprintListVo getFootprintList(@RequestBody FootprintListParam param){
//        Integer userId = wxAppAuth.user().getUserId();
        FootprintListVo footprintPage = shop().footPrintService.getFootprintPage(param.getUserId(), param.getKeyword(), param.getCurrentPage(), param.getPageRows());
        return footprintPage;
    }

}
