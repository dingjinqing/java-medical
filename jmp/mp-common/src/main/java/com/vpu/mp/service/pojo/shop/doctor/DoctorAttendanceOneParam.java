package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorDo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListVo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenjie
 */
@Data
public class DoctorAttendanceOneParam{
    private Integer doctorId;
    private String name;
    private Timestamp lastTime;
    private Integer loginDays;
    private BigDecimal loginRate;
}
