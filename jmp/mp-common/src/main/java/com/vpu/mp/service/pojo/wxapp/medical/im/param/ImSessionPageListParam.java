package com.vpu.mp.service.pojo.wxapp.medical.im.param;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月23日
 */
@Data
public class ImSessionPageListParam extends BasePageParam {
    private List<Integer> sessionStatus;

    private Integer doctorId;
}
