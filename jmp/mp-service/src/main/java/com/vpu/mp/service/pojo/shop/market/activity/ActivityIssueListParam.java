package com.vpu.mp.service.pojo.shop.market.activity;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 郑保乐
 */
@Getter
@Setter
public class ActivityIssueListParam extends BasePageParam {

    private Integer activityId;
    private String mobile;
    private String username;
}
