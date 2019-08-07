package com.vpu.mp.service.pojo.shop.market.groupdraw.group;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 成团明细入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class GroupListParam extends BasePageParam {

    @NotNull
    private Integer groupDrawId;
    /** 团长昵称 **/
    private String username;
    /** 团长手机号 **/
    private String mobile;
    /** 开团时间最小值 **/
    private Timestamp startTime;
    /** 开团时间最大值 **/
    private Timestamp endTime;
    /** 团id **/
    private Integer groupId;
    /** 是否成团 **/
    private Boolean grouped;
}
