package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liangchen
 * @date 2019年07月09日
 */
@Data
public class GoodsCommentAddListVo {
    /** 规格id */
    private Integer prdId;
	/** 商品id */
	private Integer goodsId;
    /** 商品图片 */
	private String goodsImg;
    /** 商品名称 */
	private String goodsName;
    /** 商品规格 */
    private String prdDesc;
    /** 商品货号 */
	private String goodsSn;
    /** 平台分类id */
    private Integer catId;
    /** 平台分类名称 */
	private String catName;
    /** 商品价格 */
	private BigDecimal shopPrice;
	/** 库存 */
	private Integer goodsNumber;
	/** 真实评论数 */
	private Integer realCommNum;
	/** 商家添加评论数 */
	private Integer shopCommNum;
	/** 访客数 */
	private Integer uv;
	/** 浏览量 */
	private Integer pv;

}
