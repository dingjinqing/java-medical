package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

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
    String subscriber;
    String mobile;
    String addMessage;
    String serviceDate;
    String servicePeriod;

}
