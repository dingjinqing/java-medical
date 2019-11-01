package com.vpu.mp.service.pojo.shop.overview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author liufei
 * date 2019/7/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssiDataGoods {
    /** 运费模板设置 0: 已设置运费模板，否未设置 */
    public Byte shipTemplateConf;
    /** 商品添加 0: 已添加商品 否未添加 */
    public Byte goodsConf;
    /** 商品库存偏小 0: 商品库存充裕 否有goodsStoreConf个商品库存偏少 */
    public int goodsStoreConf;
    /** 滞销商品 0：商品销售状况良好 否有goodsUnsalableConf个商品滞销 */
    public int goodsUnsalableConf;
    /** 商品评价审核逾期 0: 商品评价审核进度良好 否有goodsComment个商品评价超过3天未审核 */
    public int goodsComment;
    /** 推荐商品 0: 未配置推荐商品 否已配置推荐商品 */
    public int goodsRecommend;
    /** 商家分类 0: 未配置商家分类 否已配置商家分类 */
    public int shopSort;
}
