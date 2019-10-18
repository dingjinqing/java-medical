package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 */
@RestController
public class WxAppGoodsBrandSortController extends WxAppBaseController{

    /**
     * 小程序商品分类页面初始化
     * @return
     */
    @PostMapping("/api/wxapp/sort/init")
    public JsonResult goodsSortPageInit(){
       return success(shop().goodsMp.goodsBrandSortMp.goodsSortPageInit());
    }
}
