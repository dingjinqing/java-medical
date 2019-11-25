package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

/**
 * 筛选商品相关元素初始化类，元素可包含 标签，品牌，商家分类，平台分类
 * @author 李晓冰
 * @date 2019年11月22日
 */
@Data
public class GoodsFilterItemInitParam {
    private Boolean needGoodsNum;

    private Boolean needGoodsLabel;
    private Boolean needGoodsBrand;
    private Boolean needGoodsSort;
}
