package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.wxapp.market.seckill.SeckillGoodsBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 
 * @author lixinguo
 *
 */
@Getter
@Setter
public class ModuleSecKill extends ModuleBase {
    /**
     * 0|1 列表样式: 双列，单列
     */
    @JsonProperty("list_style")
    private Byte listStyle;
    /**
     * 是否显示 商品原价
     */
    @JsonProperty("goods_price")
    private boolean goodsPrice;
    /**
     * 是否显示活动倒计时
     */
    @JsonProperty("goods_count_down")
    private boolean goodsCountDown;


    /**
     * 秒杀商品
     */
    @JsonProperty("seckill_goods")
    private List<SeckillGoodsBo> seckillGoods;

}
