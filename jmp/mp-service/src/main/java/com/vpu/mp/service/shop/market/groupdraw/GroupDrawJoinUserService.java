package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.invite.InvitedUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.invite.InvitedUserListVo;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.join.JoinUserListVo;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
	@Autowired
	private GroupDrawService groupDrawService;

	/** 查询别名 **/
	private static final String ALIAS_NEW = "n";
	private static final String ALIAS_OLD = "o";
	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;

	/**
	 * 新用户列表
	 */
	public PageResult<InvitedUserListVo> getInvitedUserList(InvitedUserListParam param) {
		SelectConditionStep<Record7<Timestamp, Integer, Integer, Integer, String, String, String>> select = db()
				.select(GROUP_DRAW_INVITE.CREATE_TIME, GROUP_DRAW_INVITE.IDENTITY_ID,
						GROUP_DRAW_INVITE.INVITE_USER_ID.as("inviteUserId"),
						GROUP_DRAW_INVITE.USER_ID.as("invitedUserId"),
						USER.as(ALIAS_NEW).USERNAME.as("invitedUsername"),
						USER.as(ALIAS_OLD).USERNAME.as("inviteUsername"),
						USER.as(ALIAS_NEW).MOBILE.as("invitedUserMobile"))
				.from(GROUP_DRAW_INVITE).leftJoin(USER.as(ALIAS_NEW))
				.on(USER.as(ALIAS_NEW).USER_ID.eq(GROUP_DRAW_INVITE.USER_ID)).leftJoin(USER.as(ALIAS_OLD))
				.on(USER.as(ALIAS_OLD).USER_ID.eq(GROUP_DRAW_INVITE.INVITE_USER_ID)).where();
		buildInvitedUserOptions(select, param);
		select.orderBy(GROUP_DRAW_INVITE.CREATE_TIME.desc());
		return getPageResult(select, param, InvitedUserListVo.class);
	}

	/**
	 * 新用户查询条件
	 */
	private void buildInvitedUserOptions(
			SelectConditionStep<Record7<Timestamp, Integer, Integer, Integer, String, String, String>> select,
			InvitedUserListParam param) {
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

		SelectConditionStep<Record> select = db().select(JOIN_GROUP_LIST.fields())
				.select(USER.USERNAME.as("userName"), USER.MOBILE).from(JOIN_GROUP_LIST).leftJoin(USER)
				.on(JOIN_GROUP_LIST.USER_ID.eq(USER.USER_ID))
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(param.getGroupDrawId()));
		select = buildOptions(select, param);
		select.orderBy(JOIN_GROUP_LIST.OPEN_TIME.desc());
		PageResult<JoinUserListVo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), JoinUserListVo.class);
		List<JoinUserListVo> dataList = pageResult.getDataList();
		for (JoinUserListVo vo : dataList) {
			vo.setDrawNum(groupDrawService.getDrawNum(vo.getGroupDrawId(), vo.getUserId(), vo.getGoodsId()));
			vo.setInviteNum(groupDrawService.getInviteNum(vo.getGroupDrawId(), vo.getUserId(), vo.getGoodsId()));
		}
		return pageResult;
	}

	private SelectConditionStep<Record> buildOptions(SelectConditionStep<Record> select, JoinUserListParam param) {
		if (!StringUtils.isEmpty(param.getNickName())) {
			select.and(USER.USERNAME.like(likeValue(param.getNickName())));
		}
		if (null != param.getStartTime()) {
			select.and(JOIN_GROUP_LIST.OPEN_TIME.ge(param.getStartTime()));
		}
		if (null != param.getEndTime()) {
			select.and(JOIN_GROUP_LIST.OPEN_TIME.le(param.getEndTime()));
		}
		if (!StringUtils.isEmpty(param.getMobile())) {
			select.and(USER.MOBILE.like(likeValue(param.getMobile())));
		}
		if (null != param.getGrouped()) {
			if (param.getGrouped()) {
				select.and(JOIN_GROUP_LIST.STATUS.eq(ONE));
			} else {
				select.and(JOIN_GROUP_LIST.STATUS.eq(TWO)).or(JOIN_GROUP_LIST.STATUS.eq(ZERO));
			}
		}
		if (null != param.getGroupId()) {
			select.and(JOIN_GROUP_LIST.GROUP_ID.eq(param.getGroupId()));
		}
		if (null != param.getMinInviteUserCount()) {
			select.and(JOIN_GROUP_LIST.INVITE_USER_NUM.ge(Integer.valueOf(param.getMinInviteUserCount())));
		}
		if (null != param.getMaxInviteUserCount()) {
			select.and(JOIN_GROUP_LIST.INVITE_USER_NUM.le(Integer.valueOf(param.getMaxInviteUserCount())));
		}
		return select;
	}

	/**
	 * 获得该活动的参团人数
	 * 
	 * @param actId
	 * @return
	 */
	public int getJoinGroupNumByGroupDraw(int actId) {
		return db().selectCount().from(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(actId))
				.and(JOIN_GROUP_LIST.STATUS.ge((byte) 0)).fetchOptionalInto(Integer.class).orElse(0);
	}
}
