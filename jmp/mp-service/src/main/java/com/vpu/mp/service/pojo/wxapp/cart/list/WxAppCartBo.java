package com.vpu.mp.service.pojo.wxapp.cart.list;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务类
 * @author 孔德成
 * @date 2019/11/11 9:49
 */
@Builder
@Getter
@Setter
public class WxAppCartBo {

    private Integer userId;
    private Boolean isNewUser;
    private List<Integer> productIdList;
    private List<Integer> goodsIdList;
    private Timestamp date;
    /**
     * 商品列表
     */
    List<WxAppCartGoods> cartGoodsList;
    /**
     * 购物车 - 失效商品
     */
    private List<WxAppCartGoods> invalidCartList;
    private WxAppCartListVo cartListVo;
}
