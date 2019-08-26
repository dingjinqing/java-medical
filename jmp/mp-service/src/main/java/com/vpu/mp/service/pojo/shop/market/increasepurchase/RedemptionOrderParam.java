package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liufei
 * @date 2019/8/15
 * @description
 */
@Data
public class RedemptionOrderParam {
    /** 加价购活动id */
    @NotNull
    private Integer activityId;
    /** 商品名称 */
    private String goodsName;
    /** 订单号 */
    private String orderSn;
    /** 订单状态 */
    private Byte orderStatus;
    /** 收货人姓名 */
    private String receiverName;
    /** 收货人手机号 */
    private String receiverPhone;
    /** 收货地址 省---市---区*/
    private Integer provinceCode;
    private String provinceName;

    private Integer cityCode;
    private String cityName;

    private Integer districtCode;
    private String districtName;

    /** 分页参数 */
    private Integer currentPage;
    private Integer pageRows;

    /** 按页导出参数：起始页，结束页，每页行数 */
    private Integer startPage;
    private Integer endPage;
}
