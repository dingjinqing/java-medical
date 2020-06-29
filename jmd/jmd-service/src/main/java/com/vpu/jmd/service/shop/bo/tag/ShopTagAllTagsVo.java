package com.vpu.jmd.service.shop.bo.tag;

import lombok.Data;


/**
 * 获取所有店铺标签
 * @author liangchen
 * @date 2020.05.27
 */
@Data
public class ShopTagAllTagsVo {
    /** 标签id */
    private Integer id;
    /** 标签名称 */
    private String name;
}
