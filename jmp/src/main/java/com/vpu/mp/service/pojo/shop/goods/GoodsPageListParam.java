package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.foundation.Page;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class GoodsPageListParam {

    public static final Byte IS_ON_SALE_DEFAULT=1;//出售状态默认值
    public static final String ASC="asc";
    public static final String DESC="desc";

    private Integer goodsId;

    private String goodsName;//商品名
    private Integer catId;//平台分类
    private Integer sortId;//商家分类
    private Integer brandId;//商品品牌Id
    private Byte goodsType;//商品类型对应活动类型
    private BigDecimal lowShopPrice;//最低价格
    private BigDecimal highShopPrice;//最高价格
    private Byte isOnSale;//出售状态

    private String orderField;//排序字段
    private String orderDirection;//排序方式

    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
