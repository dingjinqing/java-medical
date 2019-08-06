package com.vpu.mp.service.pojo.shop.market.groupdraw.join;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 拼团抽奖 - 参与用户 列表出参
 *
 * @author 郑保乐
 */
@Data
public class JoinUserListVo {

    /** 用户id **/
    private Integer userId;
    /** 昵称 **/
    private String username;
    /** 手机号 **/
    private String mobile;
    /** 参与时间 **/
    private LocalDate createTime;
    /** 订单号 **/
    private String orderSn;
    /** 是否团长 **/
    private Boolean isGrouper;
    /** 团id **/
    private Integer groupId;
    /** 成团时间 **/
    private LocalDateTime endTime;
    /** 邀请用户数量 **/
    private Short inviteUserNum;

    /** 抽奖码数量 **/
    private Short codeCount;
}
