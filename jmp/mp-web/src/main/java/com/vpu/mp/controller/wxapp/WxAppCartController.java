package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.wxapp.cart.CartGoodsNumParam;
import com.vpu.mp.service.pojo.wxapp.cart.CartGoodsNumVo;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppAddGoodsToCartParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppChangeNumberParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppRemoveCartProductParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppRemoveCartProductsParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppSwitchCartProductsParam;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartListVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *  购物车
 * @author 孔德成
 * @date 2019/10/14 16:51
 */
@RestController
@RequestMapping("/api/wxapp/cart")
public class WxAppCartController extends WxAppBaseController {


    @PostMapping("/list")
    public JsonResult getCartList() {
        WxAppSessionUser user = wxAppAuth.user();
        WxAppCartListVo cartList = shop().cart.getCartList(user.getUserId());
        return success(cartList);
    }

    /**
     *  添加商品到购物车
     * @param param
     * @return
     */
    @PostMapping("/add")
    public JsonResult addGoodsToCart(@RequestBody @Valid WxAppAddGoodsToCartParam param){
        WxAppSessionUser user = wxAppAuth.user();
        // 检查库存数量
        Integer productNumber = shop().cart.getCartProductNumber(user.getUserId(), param.getPrdId())+param.getGoodsNumber();
        // 检查商品合法性
        ResultMessage resultMessage = shop().cart.checkProductNumber(param.getPrdId(),productNumber);
        if (!resultMessage.getFlag()){
            return fail(resultMessage);
        }
        //添加商品到购物车
        shop().cart.addSpecProduct(user.getUserId(),param.getPrdId(),param.getGoodsNumber());
        return success();
    }

    /**
     * 从购物车删除商品
     * @param param
     * @return
     */
    @PostMapping("/remove")
    public JsonResult deleteCartById(@RequestBody @Valid WxAppRemoveCartProductParam param){
        WxAppSessionUser user = wxAppAuth.user();
        shop().cart.removeCartProductById(user.getUserId(),param.getRecId().longValue());
        return success();
    }

    /**
     *  改变购物车商品数量
     * @param param
     * @return
     */
    @PostMapping("/change")
    public JsonResult changeGoodsNumber(@RequestBody @Valid WxAppChangeNumberParam param){
        WxAppSessionUser user = wxAppAuth.user();
        ResultMessage resultMessage = shop().cart.changeGoodsNumber(user.getUserId(), 0, param.getProductId(), param.getCartNumber());
        if (!resultMessage.getFlag()){
            return fail(resultMessage);
        }
        return success();
    }

    /**
     * 从购物车删除商品
     * @param param rceId
     * @return
     */
    @PostMapping("/removes")
    public JsonResult close(@RequestBody @Valid WxAppRemoveCartProductsParam param){
        WxAppSessionUser user = wxAppAuth.user();
        shop().cart.removeCartProductByIds(user.getUserId(),param.getRecIds());
        return success();
    }

    /**
     *  切换选择状态
     * @param param id
     * @return
     */
    @PostMapping("/switch")
    public JsonResult checked(@RequestBody @Valid WxAppSwitchCartProductsParam param){
        WxAppSessionUser user = wxAppAuth.user();
        int flag = shop().cart.switchCheckedProduct(user.getUserId(), param.getRecIds(),param.getIsChecked());
        if (flag>0){
            return success();
        }
        return fail();
    }

    /**
     * 查询商品数量
     * @param param
     * @return
     */
    @PostMapping("/goods/num")
    public JsonResult cartGoodsNum(@RequestBody @Valid CartGoodsNumParam param){
        WxAppSessionUser user = wxAppAuth.user();
        Integer num = shop().cart.cartGoodsNum(user.getUserId(),param.getGoodsId());
        CartGoodsNumVo cartGoodsNumVo =new CartGoodsNumVo();
        cartGoodsNumVo.setGoodsId(param.getGoodsId());
        cartGoodsNumVo.setGoodsNum(num==null?0:num);
        return success(cartGoodsNumVo);
    }

}
