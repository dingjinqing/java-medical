package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.Data;

/**
 * 小程序返回商品基类
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Data
public class GoodsBaseMp {
    protected Integer goodsId;
    protected String goodsName;
    /**商品销售数量*/
    protected Integer goodsSaleNum;
    /**商品数量*/
    protected Integer goodsNumber;
    /**是否是使用默认规格*/
    protected Boolean defaultPrd;
    /**商品评价数量*/
    private Integer commentNum;
}
