package com.vpu.mp.service.pojo.shop.market.groupdraw.order;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 郑保乐
 */
@Getter
@Setter
public class OrderListParam extends BasePageParam {

    private Integer groupDrawId;
    private String goodsName;
    private String orderSn;
    private String orderStatusName;
    private String consigneeName;
    private String mobile;
    private Timestamp createTime;
    private Short provinceCode;
    private Short cityCode;
    private Short districtCode;
}
