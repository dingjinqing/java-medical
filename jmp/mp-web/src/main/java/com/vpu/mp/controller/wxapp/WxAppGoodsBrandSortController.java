package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortMenuParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        Integer userId = wxAppAuth.user().getUserId();
        return success(shop().goodsMp.goodsBrandSortMp.goodsSortPageInit(userId));
    }

    /**
     * 小程序商品分类页面左侧菜单项点击获取内容接口
     * @return
     */
    @PostMapping("/api/wxapp/sort/get/content")
    public JsonResult getGoodsSortMenuContent(@RequestBody GoodsSortMenuParam param){
        if (param.getMenuType() == null) {
            return fail();
        }
        Integer userId = wxAppAuth.user().getUserId();
        return success(shop().goodsMp.goodsBrandSortMp.getGoodsSortMenuContent(param,userId));
    }
}
