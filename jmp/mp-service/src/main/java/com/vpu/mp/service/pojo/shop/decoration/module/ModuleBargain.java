package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainGoodsBo;
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
public class ModuleBargain extends ModuleBase {
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
     * 是否显示去砍价按钮
     */
    @JsonProperty("free_btn")
    private boolean freeBtn;

    /**
     * 砍价商品
     */
    @JsonProperty("bargain_goods")
    private List<BargainGoodsBo> bargainGoods;

}
