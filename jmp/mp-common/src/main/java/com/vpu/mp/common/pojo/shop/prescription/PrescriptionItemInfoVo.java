package com.vpu.mp.common.pojo.shop.prescription;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/8 8:50
 */
@Data
public class PrescriptionItemInfoVo {

    /**
     * 通用名
     */
    private String    goodsCommonName;
    /**
     * 规格系数
     */
    private String    goodsQualityRatio;
    /**
     * 使用药品数量
     */
    private Integer   goodsNum;
    /**
     * 药品使用方式说明
     */
    private String    goodsUseMemo;
    /**
     * 药品使用方式说明
     */
    private String    goodsBasicUnit;
    /**
     * 生成
     */
    private String    goodsProductionEnterprise;

}
