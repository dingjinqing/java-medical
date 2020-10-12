package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.math.BigDecimal;

/**
 * @author chenjie
 * @date 2020年09月21日
 */
@Data
public class DoctorAttendanceListParam extends BasePageParam {
    private Byte type;
    private Integer doctorId;
    private Integer lastRank=0;
    private BigDecimal lastRate= new BigDecimal(2);
}
