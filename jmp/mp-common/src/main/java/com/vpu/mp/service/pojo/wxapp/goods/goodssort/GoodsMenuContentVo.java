package com.vpu.mp.service.pojo.wxapp.goods.goodssort;

import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 一级分类直接关联商品时，内容返回类
 * @author 李晓冰
 * @date 2020年01月07日
 */
@Getter
@Setter
public class GoodsMenuContentVo extends MenuContentBaseVo{
    /**划线价开关 0关，1显示*/
    Byte delMarket;
    /**购物车按钮显示配置*/
    ShowCartConfig showCart;
    List<? extends GoodsListMpVo> goodsListMpVos;
}
