package com.vpu.mp.service.pojo.shop.store.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月10日
 */
@Data
public class StoreGoods {
    private Integer storeId;
    private Integer goodsId;
    private String goodsCommonName;
    private String goodsQualityRatio;
    private String goodsApprovalNumber;
    private String goodsProductionEnterprise;
    private Integer prdId;
    private String prdSn;
    private Integer productNumber;
    /**
     * 未同步时的规格价格
     */
    private BigDecimal productPrice;
    /**
     * 是否已同步pos,1是已同步
     */
    private Byte isSync;
    private Byte isOnSale;
    private Byte flag;

    private String goodsImg;
    private String goodsName;
}
