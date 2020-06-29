package com.vpu.jmd.service.shop.bo.apply;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 升级/续费申请分页列表入参
 * @author liangchen
 * @date 2020.06.04
 */
@Data
public class ShopApplyPageListParam {
    /** 店铺名称 */
    private String shopName;
    /** 所属账号 */
    private String userName;
    /** 申请升级或续费账号 */
    private String applyName;
    /** 是否联系 0未联系1已联系 */
    private Byte contact;
    /** 店铺类型 */
    private String shopType;
    /** 申请时间 开始 */
    private Timestamp startTime;
    /** 申请时间 结束 */
    private Timestamp endTime;
    /** 申请类型 0升级1续费 */
    private Byte applyType;

    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
