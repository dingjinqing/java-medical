package com.vpu.jmd.service.shop.bo.tag;

import lombok.Data;

import java.util.List;

/**
 * 为店铺设置标签
 * @author liangchen
 * @date 2020.06.03
 */
@Data
public class ShopTagSetParam {
    /** 店铺id */
    private Integer shopId;
    /** 标签id */
    private List<Integer> tagIds;
}
