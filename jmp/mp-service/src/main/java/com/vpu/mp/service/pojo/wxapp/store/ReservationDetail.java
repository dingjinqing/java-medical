package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

/**
 * @author liufei
 * @date 11/25/19
 */
@Data
public class ReservationDetail {
    @PositiveOrZero(groups = {ValidCon1.class, ValidCon2.class})
    private Integer orderId;
    @PositiveOrZero(groups = ValidCon1.class)
    private Integer userId;
    @NotBlank(groups = ValidCon.class)
    private String orderSn;
    // 门店信息
    private Integer storeId;
    private String storeName;
    private String storeImgs;
    private String storeImg;
    private String provinceCode;
    private String cityCode;
    private String districtCode;
    private String latitude;
    private String longitude;
    private String address;

    // 服务信息
    private Integer serviceId;
    private String serviceName;
    private String serviceImg;
    // 价格
    private String servicePrice;

    // 核销码
    private String verifyCode;

    // 订单信息
    // 下单时间
    private Timestamp createTime;
    // 订单状态 0：待服务，1：已取消，2：已完成
    private Byte orderStatus;
    private String orderStatusName;
    private String serviceDate;
    private String servicePeriod;
}
