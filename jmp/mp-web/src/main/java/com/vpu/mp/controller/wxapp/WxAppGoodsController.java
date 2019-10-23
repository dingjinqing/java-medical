package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsIdParams;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.BargainActivityVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GroupBuyActivityVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.SecKillActivityVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 小程序商品相关接口
 */
@RestController
public class WxAppGoodsController extends WxAppBaseController{

    @PostMapping("/api/wxapp/goods/test")
    public JsonResult test(@RequestBody GoodsIdParams params){
        Map<Integer, GroupBuyActivityVo> goodsGroupByInfo = shop().groupBuy.getGoodsGroupByInfo(params.getGoodsIds(), null);
        Map<Integer, BargainActivityVo> goodsBargainInfo = shop().bargain.getGoodsBargainInfo(params.getGoodsIds(), null);
        Map<Integer, SecKillActivityVo> goodsSecKillGoodsInfo = shop().seckill.getGoodsSecKillGoodsInfo(params.getGoodsIds(), null);
        return success(goodsSecKillGoodsInfo);
    }
}
