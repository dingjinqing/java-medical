package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

/**
 * @author chenjie
 * @date 2020年09月21日
 */
@Data
public class DoctorAttendanceListParam extends BasePageParam {
    private Byte type;
}
