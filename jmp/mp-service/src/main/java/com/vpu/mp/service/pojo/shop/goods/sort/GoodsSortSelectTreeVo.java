package com.vpu.mp.service.pojo.shop.goods.sort;

import lombok.Data;

/**
 * 商品分类树形下拉框vo
 * @author 李晓冰
 * @date 2019年11月22日
 */
@Data
public class GoodsSortSelectTreeVo {
    protected Integer sortId;
    protected String sortName;
    protected Integer parentId;
    private Byte hasChild;
    /**当前分类及所有子分类所包含的商品数量*/
    private Integer goodsNum;
}
