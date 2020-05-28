package com.vpu.mp.service.pojo.shop.goods.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import lombok.Getter;
import lombok.Setter;

/**
 * 对外接口-商品列表和详情中商品信息
 * @author 李晓冰
 * @date 2020年05月28日
 */
@Getter
@Setter
public class ApiGoodsListVo extends ApiGoodsBaseVo{
    @JsonProperty("goods_img")
    private String goodsImg;

    public ApiGoodsListVo() {
        super();
    }

    public ApiGoodsListVo(GoodsRecord goodsRecord) {
        super(goodsRecord);
        this.goodsImg = goodsRecord.getGoodsImg();
    }
}
