package com.vpu.mp.service.pojo.shop.goods.spec;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 货品规格值
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class GoodsSpecVal {
    private Integer specValId;
    private Integer specId;
    private String specValName;
    private Integer goodsId;
    private Timestamp createTime;
    private Timestamp updateTime;
}
