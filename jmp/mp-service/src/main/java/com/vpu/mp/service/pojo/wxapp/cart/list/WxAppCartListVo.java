package com.vpu.mp.service.pojo.wxapp.cart.list;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *  购物车列表
 * @author 孔德成
 * @date 2019/10/16 11:46
 */
@Data
public class WxAppCartListVo {


    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 商品总数量
     */
    private Integer totalGoodsNum;
    /**
     * 是否能支付
     */
    private Byte isCanPayment;
    /**
     * 是否全选
     */
    private Byte isAllCheck;
    /**
     * 购物车一般商品
     */
    private List<CartGoodsInfo> cartGoodsList;
    /**
     * 购物车 - 失效商品
     */
    private List<CartGoodsInfo> invalidCartList;

}
