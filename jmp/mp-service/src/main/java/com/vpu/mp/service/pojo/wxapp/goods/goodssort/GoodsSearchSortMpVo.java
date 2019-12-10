package com.vpu.mp.service.pojo.wxapp.goods.goodssort;

import lombok.Data;

import java.util.List;

/**
 * 小程序-商品搜索页面-商家分类筛选内容
 * @author 李晓冰
 * @date 2019年12月09日
 */
@Data
public class GoodsSearchSortMpVo {
    /**商家分类id*/
    private Integer sortId;
    /**商家分类名称*/
    private String sortName;
    /**关联商品数量*/
    private Integer goodsNum;

    private List<? extends GoodsSearchSortMpVo> children;
}
