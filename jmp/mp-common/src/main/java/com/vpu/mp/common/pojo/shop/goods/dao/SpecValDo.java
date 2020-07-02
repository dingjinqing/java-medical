package com.vpu.mp.common.pojo.shop.goods.dao;

import lombok.Data;

/**
 * 规格值
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class SpecValDo {
    private Integer specValId;
    private Integer specId;
    private Integer goodsId;
    private Integer specValName;
}
