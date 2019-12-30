package com.vpu.mp.service.pojo.wxapp.goods.recommend;

import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import lombok.Data;

import java.util.List;

/**
 * 小程序-商品推荐展示信息
 * @author liangchen
 * @date 2019.12.30
 */
@Data
public class RecommendGoodsVo {
    /** 商品id集合 */
    private List<Integer> recommendGoodsIds;
    /** 商品详情集合 */
    private List<?> recommendGoods;
    /** 是否显示划线价开关 */
    private Byte delMarket;
    /** 商品搜索页以及推荐商品列表中会显示购买按钮 */
    private ShowCartConfig showCart;
}
