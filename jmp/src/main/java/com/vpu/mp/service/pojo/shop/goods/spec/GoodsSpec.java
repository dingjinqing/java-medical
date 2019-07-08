package com.vpu.mp.service.pojo.shop.goods.spec;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 货品规格名
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class GoodsSpec {
    private Integer specId;
    private String specName;
    private Integer goodsId;
    private Timestamp createTime;
    private Timestamp updateTime;

    private List<GoodsSpecVal> goodsSpecVals;
}
