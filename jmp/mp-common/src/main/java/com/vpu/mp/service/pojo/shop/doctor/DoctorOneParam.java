package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorDo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListVo;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * @author chenjie
 */
@Data
public class DoctorOneParam extends DoctorDo {
    private List<DepartmentOneParam> departmentList;
    private String    titleName;
    private List<Integer> departmentIds;
    private List<String> departmentNames;
    private String departmentIdsStr;
    private PageResult<DoctorCommentListVo> commentList;
    private String hospitalName;
    private String departmentName;
    private Boolean isAttention=false;
}
