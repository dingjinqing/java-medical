package com.vpu.mp.service.shop.member;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.member.MemberInfo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.db.shop.tables.User;
import static com.vpu.mp.db.shop.Tables.USER;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

/**
 * 
 * @author 黄壮壮
 * 2019-07-08 16:22
 */
public class MemberService extends BaseService {

	/**
	 * 会员列表分页查询
	 * @param param
	 * @return
	 */
	public PageResult<MemberInfo> getPageList(MemberPageListParam param) {
		// TODO Auto-generated method stub
		System.out.println("getPageList 正在查询数据... ...");
		User u = USER.as("u");
		User n = USER.as("n");
		
		//自查寻
		Field<?> inviteUserName =  this.db().select(n.USERNAME).from(n).where(n.INVITE_ID.eq(u.USER_ID)).asField("inviteUserName");
		SelectWhereStep<? extends Record> select =(SelectWhereStep<? extends Record>) this.db()
											.select(u.USER_ID,u.USERNAME.as("userName"),inviteUserName,u.MOBILE,u.ACCOUNT,u.SCORE,u.SOURCE,u.CREATE_TIME)
											.from(u);
		
	    select = this.buildOptions(select,u, param);
		
		
		return this.getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows() , MemberInfo.class);
	}

	/**
	 * 多条件查询
	 * @param select
	 * @param param
	 * @return 
	 */
	private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select,User u,MemberPageListParam param) {

		if(param == null)
			return select;
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
			Field<Integer> inviteID =  this.db().select(u.USER_ID).from(u).where(u.USERNAME.eq(param.getInviteUserName())).asField();
			select.where(u.INVITE_ID.eq(inviteID));
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
}
