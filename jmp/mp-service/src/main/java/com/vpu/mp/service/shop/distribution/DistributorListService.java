package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_GROUP;
import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS_REBATE;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_FANLI_STATISTICS;
import static com.vpu.mp.db.shop.Tables.USER_TOTAL_FANLI;
import static org.jooq.impl.DSL.sum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Record8;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributorInvitedListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorInvitedListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorListVo;

/**
 * 分销员列表
 * @author 常乐
 * 2019年8月5日
 */
@Service
public class DistributorListService extends ShopBaseService{
	/**
	 * 分销员分页列表
	 * @param param
	 */
	public PageResult<DistributorListVo> getPageList(DistributorListParam param) {
		SelectJoinStep<Record8<Integer, String, String, Timestamp, String, String, String, Integer>> select = db().select(USER.USER_ID,USER.USERNAME,USER.MOBILE,USER.CREATE_TIME,USER_DETAIL.REAL_NAME,
				DISTRIBUTOR_GROUP.GROUP_NAME,DISTRIBUTOR_LEVEL.LEVEL_NAME,USER_TOTAL_FANLI.SUBLAYER_NUMBER)
				.from(USER.leftJoin(USER_TOTAL_FANLI).on(USER.USER_ID.eq(USER_TOTAL_FANLI.USER_ID))
				.leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID))
				.leftJoin(DISTRIBUTOR_GROUP).on(DISTRIBUTOR_GROUP.ID.eq(USER.INVITE_GROUP))
				.leftJoin(DISTRIBUTOR_LEVEL).on(USER.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID)));
		SelectConditionStep<Record8<Integer, String, String, Timestamp, String, String, String, Integer>> sql = buildOptions(select,param);
		PageResult<DistributorListVo> distributorList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), DistributorListVo.class);
		
		for(DistributorListVo distributor : distributorList.dataList) {
			//间接邀请用户数
			Integer nextNum = db().select(sum(USER_TOTAL_FANLI.SUBLAYER_NUMBER).as("next_num")).from(USER_TOTAL_FANLI).where(USER_TOTAL_FANLI.USER_ID.in(
					db().select(USER.USER_ID).from(USER).where(USER.INVITE_ID.eq(distributor.getUserId())).fetch().into(Integer.class)))
					.fetchOne().into(Integer.class);
			if(nextNum == null) {
				nextNum = 0;
			}
			distributor.setNextNumber(nextNum);
			
			//累积返利商品总额
			BigDecimal TotalCanFanliMoney = db().select(sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("can_fanli_goods_money"))
					.from(USER_FANLI_STATISTICS).where(USER_FANLI_STATISTICS.FANLI_USER_ID.eq(distributor.getUserId()))
					.fetchOne().into(BigDecimal.class);
			distributor.setTotalCanFanliMoney(TotalCanFanliMoney);
			
			//累积获得佣金金额
			BigDecimal totalFanliMoney = db().select(sum(USER_FANLI_STATISTICS.TOTAL_FANLI_MONEY).as("can_fanli_goods_money"))
					.from(USER_FANLI_STATISTICS).where(USER_FANLI_STATISTICS.FANLI_USER_ID.eq(distributor.getUserId()))
					.fetchOne().into(BigDecimal.class);
			distributor.setTotalFanliMoney(totalFanliMoney);
			
			//待返利佣金金额
			BigDecimal waitFanliMoney = db().select(sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("wait_fanli_money")).from(ORDER_GOODS_REBATE
					.leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_INFO.ORDER_SN)))
					.where(ORDER_INFO.SETTLEMENT_FLAG.eq((byte)0))
					.and(ORDER_INFO.ORDER_STATUS.ge((byte)3))
					.and(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(distributor.getUserId()))
					.fetchOne().into(BigDecimal.class);
			distributor.setWaitFanliMoney(waitFanliMoney);		
		}
		return distributorList;
	}
	
	/**
	 * 分销列表条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public  SelectConditionStep<Record8<Integer, String, String, Timestamp, String, String, String, Integer>> buildOptions(SelectJoinStep<Record8<Integer, String, String, Timestamp, String, String, String, Integer>> select,DistributorListParam param) {
		SelectConditionStep<Record8<Integer, String, String, Timestamp, String, String, String, Integer>> sql = select.where(USER.IS_DISTRIBUTOR.eq((byte) 1));
		//手机号
		if(param.getUsername() != null) {
			sql = sql.and(USER.USERNAME.eq(param.getUsername()));
		}
		//手机号
		if(param.getMobile() != null) {
			sql = sql.and(USER.MOBILE.contains(param.getMobile()));
		}
		//真实姓名
		if(param.getRealName() != null) {
			sql = sql.and(USER_DETAIL.REAL_NAME.contains(param.getRealName()));
		}
		//创建时间
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			sql = sql.and(USER.CREATE_TIME.ge(param.getStartCreateTime())).and(USER.CREATE_TIME.le(param.getStartCreateTime()));
		}
		//被邀请人昵称 || 手机号
		if(param.getInvitedMobile() != null || param.getInvitedUsername() != null) {
			SelectConditionStep<Record1<Integer>> selectInvites = db().select(USER.INVITE_ID)
					.from(USER).where(USER.INVITE_ID.ge(0));
			if(param.getInvitedUsername() != null) {
				selectInvites.and(USER.USERNAME.contains(param.getInvitedUsername()));
			}
			if(param.getInvitedMobile() != null) {
				selectInvites.and(USER.MOBILE.contains(param.getInvitedMobile()));
			}
			ArrayList<Integer> inviteIds = new ArrayList<Integer>();
			List<Integer> invites = selectInvites.fetch().into(Integer.class);
			for(int invite : invites) {
				inviteIds.add(invite);
			}
			sql = sql.and(USER.USER_ID.in(inviteIds));
		}
		//分销分组
		if(param.getDistributorGroup() != null) {
			sql = sql.and(USER.INVITE_GROUP.eq(param.getDistributorGroup()));
		}
		//分销等级
		if(param.getDistributorLevel() != null) {
			sql = sql.and(USER.DISTRIBUTOR_LEVEL.eq(param.getDistributorLevel()));
		}
		return sql;
	}
	
	/**
	 * 分销员已邀请用户列表
	 * @param param
	 * @return
	 */
	public PageResult<DistributorInvitedListVo> getInvitedList(DistributorInvitedListParam param) {
		SelectJoinStep<Record6<String, String, Timestamp, Integer, BigDecimal, BigDecimal>> select = db().select(USER.USERNAME,USER.MOBILE,USER.CREATE_TIME,USER_FANLI_STATISTICS.ORDER_NUMBER,USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY,USER_FANLI_STATISTICS.TOTAL_FANLI_MONEY)
				.from(USER.leftJoin(USER_FANLI_STATISTICS).on(USER.USER_ID.eq(USER_FANLI_STATISTICS.USER_ID)));
		SelectConditionStep<Record6<String, String, Timestamp, Integer, BigDecimal, BigDecimal>> sql = getInvitedListOptions(select,param);
		PageResult<DistributorInvitedListVo> invitedlist = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), DistributorInvitedListVo.class);
		return invitedlist;
	}
	
	/**
	 * 已邀请用户列表条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<Record6<String, String, Timestamp, Integer, BigDecimal, BigDecimal>> getInvitedListOptions(SelectJoinStep<Record6<String, String, Timestamp, Integer, BigDecimal, BigDecimal>> select,DistributorInvitedListParam param) {
		SelectConditionStep<Record6<String, String, Timestamp, Integer, BigDecimal, BigDecimal>> sql = select.where(USER.USER_ID.eq(param.getUserId()).and(USER_FANLI_STATISTICS.REBATE_LEVEL.eq((byte)1)));
		
		if(param.getMobile() != null) {
			sql = sql.and(USER.MOBILE.eq(param.getMobile()));
		}
		if(param.getUsername() != null) {
			sql = sql.and(USER.USERNAME.eq(param.getUsername()));
		}
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			sql = sql.and(USER.CREATE_TIME.ge(param.getStartCreateTime()).and(USER.CREATE_TIME.le(param.getEndCreateTime())));
		}
		return sql;
	}
	
	/**
	 * 获取返利订单数量
	 * @param userId
	 * @return
	 */
	public int getRebateOrderNum(Integer userId) {
		return db().fetchCount(USER_FANLI_STATISTICS, USER_FANLI_STATISTICS.FANLI_USER_ID.eq(userId));
	}
}
