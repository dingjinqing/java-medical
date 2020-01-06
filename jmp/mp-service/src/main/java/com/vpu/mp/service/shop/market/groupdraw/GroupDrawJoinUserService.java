package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.invite.InvitedUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.invite.InvitedUserListVo;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListVo;
import org.jooq.Record10;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.GroupDrawInvite.GROUP_DRAW_INVITE;
import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.User.USER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 拼团抽奖 - 参与用户、新用户
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawJoinUserService extends ShopBaseService {

    /** 查询别名 **/
    private static final String ALIAS_NEW = "n";
    private static final String ALIAS_OLD = "o";

    /**
     * 新用户列表
     */
    public PageResult<InvitedUserListVo> getInvitedUserList(InvitedUserListParam param) {
        SelectConditionStep<Record7<Timestamp, Integer, Integer, Integer, String, String, String>> select =
            db().select(GROUP_DRAW_INVITE.CREATE_TIME, GROUP_DRAW_INVITE.IDENTITY_ID,
                GROUP_DRAW_INVITE.INVITE_USER_ID.as("inviteUserId"), GROUP_DRAW_INVITE.USER_ID.as("invitedUserId"),
                USER.as(ALIAS_NEW).USERNAME.as("invitedUsername"), USER.as(ALIAS_OLD).USERNAME.as("inviteUsername"),
                USER.as(ALIAS_NEW).MOBILE.as("invitedUserMobile")).from(GROUP_DRAW_INVITE)
                .leftJoin(USER.as(ALIAS_NEW)).on(USER.as(ALIAS_NEW).USER_ID.eq(GROUP_DRAW_INVITE.USER_ID))
                .leftJoin(USER.as(ALIAS_OLD)).on(USER.as(ALIAS_OLD).USER_ID.eq(GROUP_DRAW_INVITE.INVITE_USER_ID))
                .where();
        buildInvitedUserOptions(select, param);
        select.orderBy(GROUP_DRAW_INVITE.CREATE_TIME.desc());
        return getPageResult(select, param, InvitedUserListVo.class);
    }

    /**
     * 新用户查询条件
     */
    private void buildInvitedUserOptions(SelectConditionStep<Record7<Timestamp, Integer, Integer, Integer, String,
        String, String>> select, InvitedUserListParam param) {
        Integer groupDrawId = param.getGroupDrawId();
        String nickName = param.getNickName();
        String mobile = param.getMobile();
        String inviteUserNickname = param.getInviteUserNickname();
        select.and(GROUP_DRAW_INVITE.IDENTITY_ID.eq(groupDrawId));
        if (isNotEmpty(nickName)) {
            select.and(USER.as(ALIAS_NEW).USERNAME.like(likeValue(nickName)));
        }
        if (isNotEmpty(mobile)) {
            select.and(USER.as(ALIAS_NEW).MOBILE.like(likeValue(mobile)));
        }
        if (isNotEmpty(inviteUserNickname)) {
            select.and(USER.as(ALIAS_OLD).USERNAME.like(likeValue(inviteUserNickname)));
        }
    }

    /**
     * 参与用户列表
     */
    public PageResult<JoinUserListVo> getJoinUserList(JoinUserListParam param) {
        SelectConditionStep<Record10<Timestamp, Integer, String, Timestamp, Byte, Integer, Integer, Integer, String,
            String>> select = db().select(JOIN_GROUP_LIST.CREATE_TIME, JOIN_GROUP_LIST.USER_ID,
            JOIN_GROUP_LIST.ORDER_SN,
            JOIN_GROUP_LIST.END_TIME, JOIN_GROUP_LIST.IS_GROUPER, JOIN_GROUP_LIST.GROUP_ID,
            JOIN_GROUP_LIST.INVITE_USER_NUM, DSL.count(JOIN_DRAW_LIST.USER_ID).as("codeCount"),
            USER.USERNAME,
            USER.MOBILE).from(JOIN_GROUP_LIST)
            .leftJoin(JOIN_DRAW_LIST).on(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(JOIN_DRAW_LIST.GROUP_DRAW_ID)
                .and(JOIN_GROUP_LIST.USER_ID.eq(JOIN_DRAW_LIST.USER_ID)))
            .leftJoin(USER).on(JOIN_GROUP_LIST.USER_ID.eq(USER.USER_ID))
            .where();
        buildOptions(select, param);
        return getPageResult(select, param, JoinUserListVo.class);
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectConditionStep<Record10<Timestamp, Integer, String, Timestamp, Byte, Integer,
        Integer, Integer, String, String>> select, JoinUserListParam param) {
        Integer groupDrawId = param.getGroupDrawId();
        String nickName = param.getNickName();
        Timestamp endTime = param.getEndTime();
        Timestamp startTime = param.getStartTime();
        Integer groupId = param.getGroupId();
        Boolean isGrouped = param.getGrouped();
        Boolean isGrouper = param.getIsGrouper();
        Short maxInviteUserCount = param.getMaxInviteUserCount();
        Short minInviteUserCount = param.getMinInviteUserCount();
        if (isNotEmpty(nickName)) {
            select.and(USER.USERNAME.like(this.likeValue(nickName)));
        }
        if (null != endTime) {
            select.and(JOIN_GROUP_LIST.CREATE_TIME.le(endTime));
        }
        if (null != startTime) {
            select.and(JOIN_GROUP_LIST.CREATE_TIME.ge(startTime));
        }
        if (null != groupId) {
            select.and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId));
        }
        if (null != isGrouped) {
            select.and(JOIN_GROUP_LIST.STATUS.eq((byte) (isGrouped ? 1 : 2)));
        }
        if (null != isGrouper) {
            select.and(JOIN_GROUP_LIST.IS_GROUPER.eq((byte) (isGrouper ? 1 : 0)));
        }
        if (null != minInviteUserCount) {
            select.and(JOIN_GROUP_LIST.INVITE_USER_NUM.ge(Integer.valueOf(minInviteUserCount)));
        }
        if (null != maxInviteUserCount) {
            select.and(JOIN_GROUP_LIST.INVITE_USER_NUM.le(Integer.valueOf(maxInviteUserCount)));
        }
        select.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId));
        select.groupBy(JOIN_GROUP_LIST.GROUP_DRAW_ID, JOIN_GROUP_LIST.CREATE_TIME, JOIN_GROUP_LIST.USER_ID,
            JOIN_GROUP_LIST.ORDER_SN, JOIN_GROUP_LIST.END_TIME, JOIN_GROUP_LIST.IS_GROUPER, JOIN_GROUP_LIST.GROUP_ID,
            JOIN_GROUP_LIST.INVITE_USER_NUM);
        select.orderBy(JOIN_GROUP_LIST.CREATE_TIME.desc());
    }
}
