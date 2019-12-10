package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2019年12月09日
 */
@RestController
public class WxAppGoodsSearchController extends WxAppBaseController{
    /**
     * 搜索界面获取初始化搜索数据
     * @return {@link com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchFilterConditionMpVo}
     */
    @GetMapping("/api/wxapp/goods/search")
    public JsonResult getGoodsSearchFilterCondition() {
        return null;
    }
}
