package com.vpu.mp.service.pojo.shop.goods.vo;

import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsMedicalInfoEntity;
import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductGoodsPageListVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品分页返回体
 * @author 李晓冰
 * @date 2020年07月08日
 */
@Data
@NoArgsConstructor
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

    public GoodsPageListVo(GoodsEntity goodsEntity) {
        goodsId = goodsEntity.getGoodsId();
        goodsImg = goodsEntity.getGoodsImg();
        goodsSn = goodsEntity.getGoodsSn();
        goodsNumber = goodsEntity.getGoodsNumber();
        goodsSaleNum = goodsEntity.getGoodsSaleNum();
        brandId = goodsEntity.getBrandId();
        sortId = goodsEntity.getSortId();
        isMedical = goodsEntity.getIsMedical();
        isDefaultProduct = goodsEntity.getIsDefaultProduct();
        if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(isMedical)) {
            GoodsMedicalInfoEntity goodsMedicalInfo = goodsEntity.getGoodsMedicalInfo();
            goodsCommonName = goodsMedicalInfo.getGoodsCommonName();
            goodsAliasName = goodsMedicalInfo.getGoodsAliasName();
            goodsQualityRatio = goodsMedicalInfo.getGoodsQualityRatio();
            isRx = goodsMedicalInfo.getIsRx();
            goodsProductionEnterprise = goodsMedicalInfo.getGoodsProductionEnterprise();
        }
    }
}
