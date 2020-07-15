/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.common.pojo.shop.table;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 处方项目明细表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Data
public class PrescriptionItemDo implements Serializable {

    private static final long serialVersionUID = -1714718912;

    private Integer   id;
    private String    posCode;
    private String    posDetailCode;
    private String    prescriptionCode;
    private String    prescriptionDetailCode;
    private Integer   goodsId;
    private String    goodsCommonName;
    private String    goodsQualityRatio;
    private String    useMethod;
    private Double    perTimeNum;
    private String    perTimeUnit;
    private Double    perTimeDosage;
    private String    perTimeDosageUnit;
    private Double    frequency;
    private Integer   dragSumNum;
    private String    dragSumUnit;
    private String    goodsUseMemo;
    private String    goodsProductionEnterprise;
    private Byte      isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;

}
