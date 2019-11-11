package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liufei
 * @date 11/7/19
 */
@Data
public class SubmitReservationParam {
    private Integer storeId;
    private Integer serviceId;
    private Integer userId;
    private Integer technicianId;
    private String technicianName;
    private String subscriber;
    private String mobile;
    private String addMessage;
    private String serviceDate;
    private String servicePeriod;
    private BigDecimal useAccount;
    private String memberCardNo;
    private BigDecimal memberCardBalance;
}
