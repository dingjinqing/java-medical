package com.vpu.mp.service.pojo.shop.market.coopen;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

/**
 *  开屏有礼 发放记录
 * @author 孔德成
 * @date 2019/11/22 15:26
 */
@Getter
@Setter
public class CoopenIssueListParam  extends BasePageParam {
    private Integer activityId;
    private String mobile;
    private String username;
}
