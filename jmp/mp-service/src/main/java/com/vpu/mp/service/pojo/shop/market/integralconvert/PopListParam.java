package com.vpu.mp.service.pojo.shop.market.integralconvert;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 积分兑换活动弹窗
 * @author liangchen
 * @date 2020.01.15
 */
@Data
public class PopListParam {
    /** 商品名称 */
    private String goodsName;
    /** 是否上架 */
    private Byte isOnSale = -1;
    /** 分页信息 */
    private Integer currentPage = 1;
    private Integer pageRows = 20;
}
