package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class ModuleIntegral extends ModuleBase {

    @JsonProperty("list_styles")
    private Byte listStyles;

    @JsonProperty("show_goods_price")
    private Boolean showGoodsPrice;

    @Data
    public static class IntegralGoods{
        @JsonProperty("goods_id")
        private Integer goodsId;
        @JsonProperty("integral_goods_id")
        private Integer integralGoodsId;
        @JsonProperty("stock_sum")
        private Integer stockSum;
        @JsonProperty("goods_name")
        private String goodsName;
        @JsonProperty("goods_img")
        private String goodsImg;
        @JsonProperty("prd_price")
        private BigDecimal prdPrice;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
        @JsonProperty("start_time")
        private Timestamp startTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
        @JsonProperty("end_time")
        private Timestamp endTime;
        @JsonProperty("is_on_sale")
        private Byte isOnSale;
        @JsonProperty("act_del_flag")
        private Byte actDelFlag;
        @JsonProperty("goods_is_delete")
        private Byte goodsIsDelete;
    }
}
