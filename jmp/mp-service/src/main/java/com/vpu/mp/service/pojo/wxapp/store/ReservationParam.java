package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author liufei
 * @date 11/7/19
 */
@Data
public class ReservationParam {
    @PositiveOrZero
    private Integer serviceId;
    @PositiveOrZero(groups = {ConfirmReservation.class, ValidCon.class, ValidCon1.class})
    private Integer userId;
    @NotNull(groups = ValidCon1.class)
    private Byte orderStatus;
}
