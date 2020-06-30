package com.vpu.mp.service.pojo.shop.market.groupdraw.order;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;

/**
 * @author 郑保乐
 */
@Getter
@Setter
public class OrderListParam extends BasePageParam {

    private Integer groupDrawId;
    private String goodsName;
    private String orderSn;
    private Byte orderStatus=-1;
    private String consigneeName;
    private String mobile;
    private Timestamp createTime;
    private Integer provinceCode;
    private Integer cityCode;
    private Integer districtCode;
}
