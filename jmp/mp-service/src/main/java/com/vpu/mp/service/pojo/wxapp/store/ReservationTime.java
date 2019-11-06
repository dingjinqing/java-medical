package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.store.technician.TechnicianInfo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * @author liufei
 * @date 11/6/19
 */
@Data
@Builder
public class ReservationTime {
    private LocalTime startTime;
    private LocalTime endTime;
    private Byte technicianFlag;
    private List<TechnicianInfo> technicianPojoList;
}
