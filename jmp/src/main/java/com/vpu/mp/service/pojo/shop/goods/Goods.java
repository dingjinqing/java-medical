package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class Goods {
	private static final long serialVersionUID = 1778325631;

	private Integer goodsId;
	private String goodsName;//商品名称
	private String goodsAd;//广告词
	private String goodsSn;// 商品货号
	private Integer catId;// 平台分类
	private String goodsImg;//商品主图
	
	private String unit;//单位
	private Integer sortId;//商家分类
	//商品标签未实现
	private Integer brandId;//商品品牌Id
	//商品视频未实现
	
	private List<GoodsSpec> goodsSpecs;//商品规格属性规格值信息
	private List<GoodsSpecProduct> goodsSpecProducts;//商品规格信息

	private Integer goodsNumber;//商品库存
	private BigDecimal shopPrice;//商品价格
	private BigDecimal marketPrice;//市场价格
	
	private Integer limitBuyNum;//商品最小限购数量
	private Integer limitMaxNum;//商品最大限购数量
	private BigDecimal costPrice;//成本价格

    private Integer deliverTemplateId;//运费模板
	private BigDecimal  goodsWeight;//商品重量

    private  Integer isCardExclusive;//会员专享商品
    private Integer canRebate;//是否允许分销改价

	private Integer isOnSale;//是否上下架
    private Timestamp saleTime;//上架时间

    private Integer isPageUp;//是否在文本区域上方
    private Integer goodsPageId;//页面装修id
    private String goodsDesc;//商品详情
}
