package com.vpu.jmd.service.shop.bo.apply;

import lombok.Data;

import java.util.List;


/**
 * 升级/续费申请编辑入参
 * @author liangchen
 * @date 2020.06.04
 */
@Data
public class ShopApplyUpdateParam {
    /** 申请id */
    private Integer id;
    /** 申请id集合 */
    private List<Integer> ids;
    /** 备注 */
    private String desc;
}
