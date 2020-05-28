package com.vpu.mp.service.pojo.shop.goods.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 对外接口-商品列表和详情中商品信息
 * @author 李晓冰
 * @date 2020年05月28日
 */
@Data
public class ApiGoodsVo {
    @JsonProperty("goods_id")
    private Integer goodsId;
    @JsonProperty("goods_sn")
    private String goodsSn;
    @JsonProperty("cat_name")
    private String catName;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("goods_img")
    private String goodsImg;
    @JsonProperty("add_time")
    private Timestamp addTime;
    @JsonProperty("update_time")
    private Timestamp updateTime;
    @JsonProperty("sku_count")
    private Integer skuCount;
    @JsonProperty("sku_list")
    private List<ApiGoodsSkuVo> skuList;

    @JsonIgnore
    private Integer sortId;

    public static ApiGoodsVo convertFromGoodsRecord(GoodsRecord goodsRecord){
        ApiGoodsVo vo =new ApiGoodsVo();
        vo.setGoodsId(goodsRecord.getGoodsId());
        vo.setGoodsSn(goodsRecord.getGoodsSn());
        vo.setGoodsName(goodsRecord.getGoodsName());
        vo.setGoodsImg(goodsRecord.getGoodsImg());
        vo.setAddTime(goodsRecord.getCreateTime());
        vo.setUpdateTime(goodsRecord.getUpdateTime());
        vo.setSortId(goodsRecord.getSortId());
        return vo;
    }
}
