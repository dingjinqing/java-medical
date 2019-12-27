package com.vpu.mp.service.pojo.wxapp.goods.recommend;

import lombok.Data;

import java.util.List;

/**
 * 小程序-商品推荐
 * @author liangchen
 * @date 2019.12.23
 */
@Data
public class RecommendGoodsParam {
    /** 店铺id */
    private Integer shopId;
    /** 页面名称 */
    private String pageName;
    /** ??? */
    private Integer pageNo;
    /** 用户id */
    private Integer userId;
    /** 推荐商品id数组 */
    private List<Integer> recommendGoodsIds;
}
