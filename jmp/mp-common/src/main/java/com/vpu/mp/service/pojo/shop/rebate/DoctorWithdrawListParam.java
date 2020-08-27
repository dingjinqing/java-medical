package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Data
public class DoctorWithdrawListParam extends BasePageParam {
    private Integer doctorId;
}
