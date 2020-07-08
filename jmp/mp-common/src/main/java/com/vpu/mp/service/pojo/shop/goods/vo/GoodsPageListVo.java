package com.vpu.mp.service.pojo.shop.goods.vo;

import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductGoodsPageListVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月08日
 */
@Data
public class GoodsPageListVo {
    private Integer goodsId;
    private String goodsImg;
    private String goodsSn;
    private Integer goodsNumber;
    private Integer goodsSaleNum;
    private Integer brandId;
    private String brandName;
    private Integer sortId;
    private Integer sortName;
    private BigDecimal shopPrice;
    private Byte isMedical;
    private Byte isDefaultProduct;
    private String goodsCommonName;
    private String goodsAliasName;
    private String goodsQualityRatio;
    private Byte isRx;
    private String goodsProductionEnterprise;

    private List<GoodsSpecProductGoodsPageListVo> goodsSpecProducts;
}
