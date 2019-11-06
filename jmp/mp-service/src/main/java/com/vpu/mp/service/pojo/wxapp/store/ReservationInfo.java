package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.store.technician.ServiceTechnicianPojo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author liufei
 * @date 11/5/19
 */
@Data
public class ReservationInfo {
    private LocalDate reservationDate;
    private List<ReservationTime> reservationTimeList;

    public class ReservationTime {
        private LocalTime startTime;
        private LocalTime endTime;
        private Byte technicianFlag;
        private List<ServiceTechnicianPojo> technicianPojoList;
    }
}
