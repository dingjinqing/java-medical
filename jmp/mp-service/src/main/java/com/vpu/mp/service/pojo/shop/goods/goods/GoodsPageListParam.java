package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class GoodsPageListParam {

	
	/**
	 * 	在售状态值
	 */
    public static final Byte IS_ON_SALE_DEFAULT=1;
    /**
     * 已下架状态值
     */
    public static final Byte NOT_ON_SALE=0;

    public static final String ASC="asc";
    public static final String DESC="desc";
    
    /**
     * 	待排序字段
     */
    public static final String SHOP_PRICE="shopPrice";
    public static final String GOODS_NUMBER="goodsNumber";
    public static final String GOODS_SALE_NUM="goodsSaleNum";
    

    private Integer goodsId;

    private String goodsName;
    private String goodsSn;
    private Integer brandId;
    private Byte source;
    private Byte goodsType;
    private Short catId;
    private Integer sortId;
    private Integer labelId;
    private Timestamp saleTimeStart;
    private Timestamp saleTimeEnd;
    private BigDecimal lowShopPrice;
    private BigDecimal highShopPrice;
    /**
     * 是否在售
     */
    private Byte isOnSale;
    /**
     * true表示已售罄
     */
    private Boolean isSaleOut;

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
    private int currentPage;
    private int pageRows;
}
