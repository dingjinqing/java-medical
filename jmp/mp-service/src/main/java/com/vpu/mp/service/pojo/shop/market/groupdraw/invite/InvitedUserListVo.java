package com.vpu.mp.service.pojo.shop.market.groupdraw.invite;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 郑保乐
 */
@Data
public class InvitedUserListVo {

    /** 新用户id **/
    private Integer invitedUserId;
    /** 新用户昵称 **/
    private String invitedUsername;
    /** 新用户手机号 **/
    private String invitedUserMobile;
    /** 邀请人id **/
    private Integer inviteUserId;
    /** 邀请人昵称 **/
    private String inviteUsername;
    /** 注册时间 **/
    private LocalDateTime createTime;
}
