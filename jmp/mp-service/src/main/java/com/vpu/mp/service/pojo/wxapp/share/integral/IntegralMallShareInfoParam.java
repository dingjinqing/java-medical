package com.vpu.mp.service.pojo.wxapp.share.integral;

import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 李晓冰
 * @date 2020年05月15日
 */
@Getter
@Setter
public class IntegralMallShareInfoParam extends GoodsShareBaseParam {
    /**使用积分*/
    private Integer score;
}
