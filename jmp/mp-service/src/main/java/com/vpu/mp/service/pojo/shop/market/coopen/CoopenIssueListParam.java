package com.vpu.mp.service.pojo.shop.market.coopen;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *  开屏有礼 发放记录
 * @author 孔德成
 * @date 2019/11/22 15:26
 */
@Getter
@Setter
public class CoopenIssueListParam  extends BasePageParam {
    @NotNull
    private Integer activityId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String mobile;
    private String username;
}
