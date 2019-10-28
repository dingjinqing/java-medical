package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsIdParams;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
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
        Timestamp now = DateUtil.getLocalDateTime();
        Map<Integer, GroupBuyActivityVo> goodsGroupByInfo = shop().groupBuy.getGoodsGroupByInfo(params.getGoodsIds(), now);
        Map<Integer, BargainActivityVo> goodsBargainInfo = shop().bargain.getGoodsBargainInfo(params.getGoodsIds(), now);
        Map<Integer, SecKillActivityVo> goodsSecKillGoodsInfo = shop().seckill.getGoodsSecKillInfo(params.getGoodsIds(), now);
        Map<Integer, ReducePriceActivityVo> goodsReducePriceInfo = shop().reducePrice.getGoodsReducePriceInfo(params.getGoodsIds(), now);
        Map<Integer, FirstSpecialActivityVo> goodsFirstSpecialInfo = shop().firstSpecial.getGoodsFirstSpecialInfo(params.getGoodsIds(), now);
        Map<Integer, PreSaleActivityVo> goodsPreSaleInfo = shop().preSale.getGoodsPreSaleInfo(params.getGoodsIds(), now);
        return success(goodsPreSaleInfo);
    }
}
