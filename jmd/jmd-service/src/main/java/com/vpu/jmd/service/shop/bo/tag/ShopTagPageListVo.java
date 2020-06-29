package com.vpu.jmd.service.shop.bo.tag;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 店铺标签分页列表出参
 * @author liangchen
 * @date 2020.05.27
 */
@Data
public class ShopTagPageListVo {
    /** 标签id */
    private Integer id;
    /** 标签名称 */
    private String name;
    /** 更新时间 */
    private Timestamp updateTime;
    /** 店铺数量 */
    private Integer shopNum;
}
