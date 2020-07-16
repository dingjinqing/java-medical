package com.vpu.mp.service.pojo.shop.medical.goods.entity;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class GoodsMedicalInfoEntity {
    private Integer id;
    private Integer goodsId;
    private String goodsCommonName;
    private String goodsAliasName;
    private String goodsQualityRatio;
    private Byte isRx;
    private Byte insuranceFlag;
    private String insuranceCode;
    private String insuranceDatabaseName;
    private String goodsBasicUnit;
    private String goodsPackageUnit;
    private Double goodsUnitConvertFactor;
    private Double goodsEquivalentQuantity;
    private String goodsEquivalentUnit;
    private String goodsComposition;
    private String goodsCharacters;
    private String goodsFunction;
    private String goodsUseMethod;
    private String goodsAdverseReaction;
    private String goodsTaboos;
    private String goodsNoticeEvent;
    private String goodsInteraction;
    private String goodsStoreMethod;
    private String goodsPackageMethod;
    private String goodsValidTime;
    private String goodsApprovalNumber;
    private String goodsProductionEnterprise;
    private Byte    goodsLimitDuty;
    private Byte    goodsLimitAntibacterial;
}
