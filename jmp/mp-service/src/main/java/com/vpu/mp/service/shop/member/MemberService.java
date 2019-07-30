package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.ORDER_VERIFIER;
import static com.vpu.mp.db.shop.Tables.USER;

import org.jooq.*;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 
 * @author 黄壮壮
 * 2019-07-08 16:22
 */
@Service
public class MemberService extends ShopBaseService {

	private static final String INVITE_USERNAME="inviteUserName";
	private static final String USER_NAME="userName";

	@Autowired public AccountService account;
	@Autowired public ScoreService score;
	@Autowired public MemberCardService card;
	/**
	 * 会员列表分页查询
	 * @param param
	 * @return
	 */
	public PageResult<MemberInfoVo> getPageList(MemberPageListParam param) {
		
		User u = USER.as("u");
		User n = USER.as("n");
		
		//自查寻
		Field<?> inviteUserName =  this.db().select(n.USERNAME).from(n).where(n.INVITE_ID.eq(u.USER_ID)).asField(INVITE_USERNAME);
		SelectWhereStep<? extends Record> select =(SelectWhereStep<? extends Record>) this.db()
											.select(u.USER_ID,u.USERNAME.as(USER_NAME),inviteUserName,u.MOBILE,u.ACCOUNT,u.SCORE,u.SOURCE,u.CREATE_TIME)
											.from(u);
		
	    select = this.buildOptions(select,u, param);
		
		
		return this.getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows() , MemberInfoVo.class);
	}

	/**
	 * 多条件查询
	 * @param select
	 * @param param
	 * @return 
	 */
	private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select,User u,MemberPageListParam param) {
		
		if(param == null) {

			return select;
		}
		//手机号
		if(!StringUtils.isEmpty(param.getMobile())) {
			select.where(u.MOBILE.eq(param.getMobile()));
		}
		//昵称
		if(!StringUtils.isEmpty(param.getUsername())) {
			select.where(u.USERNAME.eq(param.getUsername()));
		}
		//来源
		if(param.getSource() != null) {
			select.where(u.SOURCE.eq(param.getSource()));
		}
		//邀请人
		if(!StringUtils.isEmpty(param.getInviteUserName())) {
			Field<Integer> inviteId =  this.db().select(u.USER_ID).from(u).where(u.USERNAME.eq(param.getInviteUserName())).asField();
			select.where(u.INVITE_ID.eq(inviteId));
		}
		
		//注册时间
		if(!StringUtils.isEmpty(param.getCreateTime())) {
			select.where(u.CREATE_TIME.ge(Util.convertToTimestamp(param.getCreateTime())));
		}
		
		//结束时间
		if(!StringUtils.isEmpty(param.getEndTime())) {
			select.where(u.CREATE_TIME.le(Util.convertToTimestamp(param.getCreateTime())));
		}
		
		//TODO 指定时间内有登录
		//TODO 指定时间内有加购行为
		//TODO 指定时间内有交易记录
		//TODO 客单价
		//TODO 累计购买次数
		//TODO 购买指定商品
		
		return select;
	}
	
	/**
	 * 通用会员列表弹窗分页查询
	 * @param param
	 * @return
	 */
	public PageResult<CommonMemberPageListQueryVo> getCommonPageList(CommonMemberPageListQueryParam param) {
		SelectJoinStep<? extends Record> select = db().select(USER.USER_ID,USER.USERNAME,USER.MOBILE).from(USER);
		select = this.buildCommonPageListQueryOptions(select, param);
		select.where(USER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(USER.CREATE_TIME);
		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), CommonMemberPageListQueryVo.class);
	}
	
	/**
	 * 通用会员选择弹窗的指定规则过滤条件构造
	 * @param SelectJoinStep
	 * @param CommonMemberPageListQueryParam
	 * @return 
	 */
	private SelectJoinStep<? extends Record> buildCommonPageListQueryOptions(SelectJoinStep<? extends Record> select, CommonMemberPageListQueryParam param) {
		if(param == null) {
			return select;
		}
		
		if(null != param.getUserId() && param.getUserId() > 0) {
			select.where(USER.USER_ID.eq(param.getUserId()));
		}
		if(!StringUtils.isEmpty(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if(!StringUtils.isEmpty(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		
		/**
		 * 过滤已经是该门店核销员的用户，用于为该门店添加核销员
		 */
		if(null != param.getStoreId() && param.getStoreId() > 0) {
			select.leftJoin(ORDER_VERIFIER).on(USER.USER_ID.eq(ORDER_VERIFIER.USER_ID)).where(ORDER_VERIFIER.STORE_ID.ne(param.getStoreId()));
		}
		
		return select;
	}

	public UserRecord getUserRecordById(Integer userId) {
		UserRecord user = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchOne();
		return user;
	}


	/**
	 *  通过活动新增用户
	 *
	 * @param param
	 * @param source
	 * @param actId
	 * @return
	 */
	public PageResult<MemberInfoVo> getSourceActList(MemberPageListParam param,String source,int actId ) {
		User a = USER.as("a");
		User b = USER.as("b");
		SelectWhereStep<? extends Record> select = (SelectWhereStep<? extends Record>) db()
				.select(a.USER_ID, a.USERNAME.as(USER_NAME), a.MOBILE, a.CREATE_TIME, a.INVITE_ID, b.USERNAME.as(INVITE_USERNAME))
				.from(a)
				.leftJoin(b).on(a.INVITE_ID.eq(b.USER_ID))
				.where(a.INVITE_SOURCE.eq(source))
				.and(a.INVITE_ACT_ID.eq(actId))
				.orderBy(a.CREATE_TIME.desc());
		select = this.buildOptions(select,a, param);

		return this.getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows() , MemberInfoVo.class);
	}
}
