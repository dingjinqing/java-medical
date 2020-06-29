package com.vpu.jmd.service.shop.bo.tag;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 店铺标签相关操作入参
 * @author liangchen
 * @date 2020.05.27
 */
@Data
public class ShopTagOperationParam {
    /** 标签id */
    private Integer id;
    /** 标签名称 */
    private String name;
    /** 删除标识 */
    private Byte delFlag;
    /** 删除时间 */
    private Timestamp delTime;
}
