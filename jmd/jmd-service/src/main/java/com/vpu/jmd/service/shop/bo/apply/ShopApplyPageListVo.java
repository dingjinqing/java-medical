package com.vpu.jmd.service.shop.bo.apply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 升级/续费申请分页列表出参
 * @author liangchen
 * @date 2020.06.03
 */
@Data
public class ShopApplyPageListVo {
    /** 申请id */
    private Integer id;
    /** 店铺id */
    private Integer shopId;
    /** 店铺名称 */
    private String shopName;
    /** 申请时间 */
    private Timestamp created;
    /** 点击模块 */
    private String applyMod;
    /** 申请类型 0升级1续费 */
    private Byte applyType;
    /** 是否联系 0未联系1已联系 */
    private Byte contact;
    /** 备注 */
    private String desc;
    /** 店铺类型 */
    private String shopType;
    /** 手机号 */
    private String mobile;
    /** 店铺创建时间 */
    private Timestamp shopCreated;
    /** 所属账号 */
    private String userName;
    /** 申请升级或续费账号 */
    private String applyName;
    /** 到期时间 */
    @JsonIgnore
    private Date expireTime;
    /** 店铺状态 0已过期 1使用中 */
    private Byte shopStatus;
    /** 申请账号类型 0主账号 1子账号*/
    private Byte accountType;
}
