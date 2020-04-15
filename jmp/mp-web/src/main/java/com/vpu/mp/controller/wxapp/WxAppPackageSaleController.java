package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsAddParam;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsListParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 王兵兵
 * @create: 2020-02-20 09:52
 **/
@RestController
public class WxAppPackageSaleController extends WxAppBaseController  {
    /**
     * 	一口价活动页的商品列表接口
     */
    @PostMapping("/api/wxapp/packagesale/goodslist")
    public JsonResult packageSaleGoodsList(@RequestBody @Validated PackageSaleGoodsListParam param) {
        return success(shop().packSale.getWxAppGoodsList(param,wxAppAuth.user().getUserId()));
    }

    /**
     * 	一口价活动页的已选商品列表接口
     */
    @PostMapping("/api/wxapp/packagesale/checkedlist")
    public JsonResult packageSaleCheckedList(@RequestBody @Validated PackageSaleGoodsListParam param) {
        return success(shop().packSale.getCheckedGoodsList(param,wxAppAuth.user().getUserId()));
    }

    /**
     * 	商品加购
     */
    @PostMapping("/api/wxapp/packagesale/add")
    public JsonResult addPackageGoods(@RequestBody @Validated PackageSaleGoodsAddParam param) {
        return success(shop().packSale.addPackageGoodsToCart(param,wxAppAuth.user().getUserId()));
    }
}
