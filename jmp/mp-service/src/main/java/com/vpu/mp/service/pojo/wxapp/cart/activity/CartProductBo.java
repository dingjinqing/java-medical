package com.vpu.mp.service.pojo.wxapp.cart.activity;

import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车商品数据数据
 * @author 孔德成
 * @date 2019/12/4 15:54
 */
@Getter
@Setter
public class CartProductBo {

    private Integer productId;
    private Integer goodsNumber;
    private Byte isChecked;
    private CartActivityInfo cartActivityInfo;

}
