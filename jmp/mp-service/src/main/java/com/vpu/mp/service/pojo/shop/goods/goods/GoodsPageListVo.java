package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelSelectListVo;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品（规格）分页信息返回类，该类同时使用于
 * 1商品分页列表信息，2规格分页列表信息，3商品分页信息（每一条商品数据附带了对应的所有规格数据）
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class  GoodsPageListVo {
    private Integer goodsId;
    private String goodsName;
    private String goodsImg;
    private String goodsSn;
    private BigDecimal shopPrice;
    private Byte source;
    private Byte goodsType;
    private Integer catId;
    private String catName;
    private String sortName;
    private Integer sortId;
    private String brandName;
    private Integer goodsNumber;
    private Integer goodsSaleNum;
    private List<GoodsLabelSelectListVo> goodsLabels = new ArrayList<>();
    /**
     * 商品对应的规格数据,未使用
     */
    private List<GoodsSpecProduct> goodsSpecProducts;
    private Boolean isDefaultPrd;
    /**
     * 查询商品对应的规格时该值为规格id,或者表示默认规格的id，否则为空
     */
    private Integer prdId;
    private String prdDesc;
    private String prdImg;
    private String prdSn;
    private BigDecimal prdPrice;
    private Integer prdNumber;
    /**
     * 商品规格类型数量（默认规格为类型数量为0）
     */
    private Integer prdTypeNum;
    /**
     * 商品规格中价格最高的价格，只有一个规格数据或默认规格时其最高价格和最低价格一样
     */
    private BigDecimal prdMaxShopPrice;
    /**
     * 商品规格中价格最低的价格
     */
    private BigDecimal prdMinShopPrice;
}
