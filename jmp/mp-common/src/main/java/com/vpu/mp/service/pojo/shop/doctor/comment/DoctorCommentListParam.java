package com.vpu.mp.service.pojo.shop.doctor.comment;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2020/8/12 15:45
 */
@Data
public class DoctorCommentListParam extends BasePageParam {

    @NotNull
    private Integer doctorId;
}
