package com.vpu.mp.service.pojo.wxapp.medical.im.param;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/8/12
 **/
@Data
public class ImSessionQueryPageListParam extends BasePageParam {
    String orderSn;
}
