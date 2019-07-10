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

	
	//出售状态默认值
    public static final Byte IS_ON_SALE_DEFAULT=1;
    public static final String ASC="asc";
    public static final String DESC="desc";
    
    //待排序字段
    public static final String SHOP_PRICE="shopPrice";
    public static final String GOODS_NUMBER="goodsNumber";
    public static final String GOODS_SALE_NUM="goodsSaleNum";
    
    //标签类别对应码
    public static final Byte GOODS_LABEL=1;
    public static final Byte CATEGORY_LABEL=2;
    

    private Integer goodsId;

    private String goodsName;
    private Integer catId;
    private Integer sortId;
    private Integer labelId;
    private Byte labelType;
    private Integer brandId;
    private Byte goodsType;
    private BigDecimal lowShopPrice;
    private BigDecimal highShopPrice;
    private Byte isOnSale;

    /**
     * 排序字段
     */
    private String orderField;
    /**
     * 排序方式
     */
    private String orderDirection;

    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
