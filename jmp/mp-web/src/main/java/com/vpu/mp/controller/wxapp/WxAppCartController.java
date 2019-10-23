package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppAddGoodsToCartParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppCartListParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppChangeNumberParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppRemoveCartProductParam;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  购物车
 * @author 孔德成
 * @date 2019/10/14 16:51
 */
@RestController
@RequestMapping("/api/wxapp/cart")
public class WxAppCartController extends WxAppBaseController {


    @PostMapping("/list")
    public JsonResult getCartList(@RequestBody  WxAppCartListParam param) {
        WxAppCartListVo cartList = shop().cart.getCartList(param);
        return success(cartList);
    }

    /**
     *  添加商品到购物车
     * @param param
     * @return
     */
    @PostMapping("/addGoods")
    public JsonResult addGoodsToCart(@RequestBody WxAppAddGoodsToCartParam param){
        // 检查库存数量
        Integer productNumber = shop().cart.getCartProductNumber(param.getUserId(), param.getPrdId())+param.getGoodsNumber();
        // 检查商品合法性
        ResultMessage resultMessage = shop().cart.checkProductNumber(param.getProductId(),productNumber);
        if (resultMessage.getFlag()){
            return fail(resultMessage);
        }
        //添加商品到购物车
        shop().cart.addSpecProduct(param.getUserId(),param.getPrdId(),param.getGoodsNumber());
        return success();
    }

    /**
     * 从购物车删除商品
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public JsonResult deleteCartById(@RequestBody WxAppRemoveCartProductParam param){

        shop().cart.removeCartProductById(param.getUserId(),param.getRecId());
        return success();
    }

    /**
     *  改变购物车商品数量
     * @param param
     * @return
     */
    @PostMapping("/changeGoodsNumber")
    public JsonResult changeGoodsNumber(@RequestBody WxAppChangeNumberParam param){
        ResultMessage resultMessage = shop().cart.changeGoodsNumber(param.getUserId(), 0, param.getProductId(), param.getGoodsNumber());
        if (resultMessage.getFlag()){
            return fail(resultMessage);
        }
        return success();
    }


}
