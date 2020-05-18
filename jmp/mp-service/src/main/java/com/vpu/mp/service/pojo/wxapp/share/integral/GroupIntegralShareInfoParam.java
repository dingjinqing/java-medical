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
public class GroupIntegralShareInfoParam extends GoodsShareBaseParam {
    /**需要跳转的页面类型*/
    private Byte pageType;
    /**邀请人id*/
    private Integer inviteId;
}
