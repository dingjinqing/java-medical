package com.vpu.mp.service.pojo.shop.market.groupdraw.invite;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 新用户列表入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class InvitedUserListParam extends BasePageParam {

    /** 活动id **/
    @NotNull
    private Integer groupDrawId;
    /** 被邀请人手机号 **/
    private String mobile;
    /** 被邀请人昵称 **/
    private String nickName;
    /** 邀请人昵称 **/
    private String inviteUserNickname;
}
