package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author liufei
 * @date 11/5/19
 */
@Data
@Builder
public class ReservationInfo {
    private LocalDate reservationDate;
    private List<ReservationTime> reservationTimeList;
}
