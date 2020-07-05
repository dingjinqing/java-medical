package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 规格值
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class SpecValDo {
    private Integer   specValId;
    private Integer   specId;
    private String    specValName;
    private Byte      delFlag;
    private Integer   goodsId;
    private Timestamp createTime;
    private Timestamp updateTime;
}