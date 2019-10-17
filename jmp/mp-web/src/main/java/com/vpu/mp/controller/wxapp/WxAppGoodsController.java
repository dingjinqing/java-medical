package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.goods.mp.GoodsListMpParam;
import com.vpu.mp.service.pojo.shop.goods.goods.mp.GoodsListMpVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 小程序商品相关接口
 */
@RestController
public class WxAppGoodsController extends WxAppBaseController{

    /**
     * 小程序装修商品列表模块数据接口
     * @param goodsListMpParam
     */
    @PostMapping("/api/wxapp/goods/list")
    public JsonResult getGoodsList(@RequestBody GoodsListMpParam goodsListMpParam) {
        List<GoodsListMpVo> goodsList = shop().goods.goodsMpService.getGoodsList(goodsListMpParam);
        return success(goodsList);
    }
}
