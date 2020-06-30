package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.common.foundation.data.DistributionConstant.*;
import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_GROUP;
import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS_REBATE;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_FANLI_STATISTICS;
import static com.vpu.mp.db.shop.Tables.USER_TOTAL_FANLI;
import static com.vpu.mp.db.shop.Tables.USER_REMARK;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.db.shop.tables.records.UserRemarkRecord;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import jodd.util.StringUtil;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;

import javax.validation.constraints.Null;

/**
 * 分销员列表
 * @author 常乐
 * 2019年8月5日
 */
@Service
public class DistributorListService extends ShopBaseService{
    @Autowired
    public MpDistributionService mpDis;

    @Autowired
    public DistributionConfigService dcs;
	/**
	 * 分销员分页列表
	 * @param param
	 */
	public PageResult<DistributorListVo> getPageList(DistributorListParam param) {
        com.vpu.mp.db.shop.tables.User d = USER.as("d");
        com.vpu.mp.db.shop.tables.User a = USER.as("a");
        Field nextNumber = count(a.USER_ID).as("nextNumber");
        Field sublayerNumber = sum(USER_TOTAL_FANLI.SUBLAYER_NUMBER).as("sublayerNumber");
        Field totalCanFanliMoney = sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("totalCanFanliMoney");
        Field totalFanliMoney = sum(USER_FANLI_STATISTICS.TOTAL_FANLI_MONEY).as("totalFanliMoney");
        Field waitFanliMoney = sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("waitFanliMoney");


        //下级用户数
        Table<Record1<Integer>> recordTable = db().select(d.USER_ID,nextNumber.as("nextNumber")).from(d).leftJoin(a).on(a.INVITE_ID.eq(d.USER_ID)).where(d.IS_DISTRIBUTOR.eq((byte) 1)).groupBy(d.USER_ID).asTable();

        //分销审核开关是否开启
        DistributionParam distributionCfg = dcs.getDistributionCfg();
        if(distributionCfg.getJudgeStatus() != null && distributionCfg.getJudgeStatus() == 0){//分销审核开关开启展示审核过的分销员
            //下级用户数
            recordTable = db().select(d.USER_ID,nextNumber.as("nextNumber")).from(d).leftJoin(a).on(a.INVITE_ID.eq(d.USER_ID)).groupBy(d.USER_ID).asTable();
        }

        //间接邀请人数
        Table<Record2<Integer, BigDecimal>> record2Table = db().select(d.USER_ID, sublayerNumber.as("sublayerNumber")).from(d).leftJoin(a).on(d.USER_ID.eq(a.INVITE_ID)).leftJoin(USER_TOTAL_FANLI).on(USER_TOTAL_FANLI.USER_ID.eq(a.USER_ID))
            .where(d.IS_DISTRIBUTOR.eq((byte) 1)).groupBy(d.USER_ID).asTable();

        //累计返利商品总额；累计获得佣金金额
        Table<Record3<Integer, BigDecimal, BigDecimal>> record3Table = db().select(USER_FANLI_STATISTICS.FANLI_USER_ID, totalCanFanliMoney.as("totalCanFanliMoney"), totalFanliMoney.as("totalFanliMoney")).from(d).leftJoin(USER_FANLI_STATISTICS).on(d.USER_ID.eq(USER_FANLI_STATISTICS.FANLI_USER_ID)).where(d.IS_DISTRIBUTOR.eq((byte) 1))
            .groupBy(USER_FANLI_STATISTICS.FANLI_USER_ID).asTable();

        //待返利佣金金额
        Table<Record2<Integer, BigDecimal>> record2Table2 = db().select(ORDER_GOODS_REBATE.REBATE_USER_ID, waitFanliMoney.as("waitFanliMoney")).from(d).leftJoin(ORDER_GOODS_REBATE).on(d.USER_ID.eq(ORDER_GOODS_REBATE.REBATE_USER_ID)).leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN
            .eq(ORDER_INFO.ORDER_SN)).where(ORDER_INFO.SETTLEMENT_FLAG.eq((byte) 0)).and(ORDER_INFO.ORDER_STATUS.eq((byte) 3)).groupBy(d.USER_ID,ORDER_GOODS_REBATE.REBATE_USER_ID).asTable();

        SelectOnConditionStep<? extends Record> select = db().select(
            d.USER_ID, d.USERNAME,d.INVITE_ID,
            d.MOBILE,d.INVITATION_CODE,d.CREATE_TIME,USER_DETAIL.REAL_NAME,DISTRIBUTOR_LEVEL.LEVEL_NAME,DISTRIBUTOR_GROUP.GROUP_NAME,
            recordTable.field("nextNumber"),
            record2Table.field("sublayerNumber"),
            record3Table.field("totalCanFanliMoney"),
            record3Table.field("totalFanliMoney"),
            record2Table2.field("waitFanliMoney"))
            .from(d).leftJoin(a).on(d.USER_ID.eq(a.INVITE_ID))
            .leftJoin(USER_DETAIL).on(d.USER_ID.eq(USER_DETAIL.USER_ID))
            .leftJoin(DISTRIBUTOR_LEVEL).on(d.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID))
            .leftJoin(DISTRIBUTOR_GROUP).on(d.INVITE_GROUP.eq(DISTRIBUTOR_GROUP.ID));
        //临时表拼接
        SelectConditionStep<? extends Record> where = select
            .leftJoin(recordTable).on(recordTable.field(USER.USER_ID).eq(d.USER_ID))
            .leftJoin(record2Table).on(record2Table.field(USER.USER_ID).eq(d.USER_ID))
            .leftJoin(record3Table).on(record3Table.field(USER_FANLI_STATISTICS.FANLI_USER_ID).eq(d.USER_ID))
            .leftJoin(record2Table2).on(record2Table2.field(ORDER_GOODS_REBATE.REBATE_USER_ID).eq(d.USER_ID)).where(d.USER_ID.gt(0));

        //分销审核开关是否开启
//        DistributionParam distributionCfg = dcs.getDistributionCfg();
        if(distributionCfg.getJudgeStatus() != null && distributionCfg.getJudgeStatus() == 1){//分销审核开关开启展示审核过的分销员
            where.and(d.IS_DISTRIBUTOR.eq((byte) 1));
        }
        if(distributionCfg.getJudgeStatus() != null && distributionCfg.getJudgeStatus() == 0){//分销审核开关关闭 分销员列表默认展示有下级的分销员
            param.setHaveNextUser((byte)1);
        }
        SelectConditionStep<? extends Record> sql = buildOptions(where, param,record2Table,record3Table,record2Table2,recordTable);

        PageResult<DistributorListVo> distributorList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), DistributorListVo.class);
        for(DistributorListVo dis:distributorList.dataList){
            Record record = db().select(a.USERNAME).from(a).where(a.USER_ID.eq(dis.getInviteId())).fetchOne();
            if(record != null){
                dis.setInviteName(record.into(String.class));
            }else{
                dis.setInviteName(null);
            }
            //备注条数
            Integer remarkNum = db().selectCount().from(USER_REMARK).where(USER_REMARK.IS_DELETE.eq((byte) 0)).and(USER_REMARK.USER_ID.eq(dis.getUserId())).fetchOne().into(Integer.class);
            dis.setRemarkNum(remarkNum);
        }
		return distributorList;
	}

	/**
	 * 分销列表条件查询
	 * @param where
	 * @param param
	 * @return
	 */
	public  SelectConditionStep<? extends Record> buildOptions(SelectConditionStep<? extends Record> where,DistributorListParam param,Table<Record2<Integer, BigDecimal>> record2Table,Table<Record3<Integer, BigDecimal, BigDecimal>> record3Table,Table<Record2<Integer, BigDecimal>> record2Table2,Table<Record1<Integer>> recordTable) {
        com.vpu.mp.db.shop.tables.User d = USER.as("d");
        com.vpu.mp.db.shop.tables.User a = USER.as("a");
		//微信昵称
		if(StringUtil.isNotEmpty(param.getUsername())) {
			where.and(d.USERNAME.contains(param.getUsername()));
		}

		//手机号
		if(StringUtil.isNotEmpty(param.getMobile())) {
			where.and(d.MOBILE.contains(param.getMobile()));
		}
		//真实姓名
		if(StringUtil.isNotEmpty(param.getRealName())) {
			where.and(USER_DETAIL.REAL_NAME.contains(param.getRealName()));
		}
		//邀请人
        if(StringUtil.isNotEmpty(param.getInviteName())){
            List<Integer> inviteIds = db().select(USER.USER_ID).from(USER).where(USER.USERNAME.contains(param.getInviteName())).fetch().into(Integer.class);
            where.and(d.INVITE_ID.in(inviteIds));
        }
        //邀请码
        if(StringUtil.isNotEmpty(param.getInvitationCode())){
            where.and(d.INVITATION_CODE.eq(param.getInvitationCode()));
        }
		//创建时间
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			where.and(d.CREATE_TIME.ge(param.getStartCreateTime())).and(d.CREATE_TIME.le(param.getEndCreateTime()));
		}
		//被邀请人昵称 || 手机号
		if(StringUtil.isNotEmpty(param.getInvitedMobile()) || StringUtil.isNotEmpty(param.getInvitedUserName())) {
			SelectConditionStep<Record1<Integer>> selectInvites = db().select(a.INVITE_ID)
					.from(a).where(a.INVITE_ID.ge(0));
			if(StringUtil.isNotEmpty(param.getInvitedUserName())) {
				selectInvites.and(a.USERNAME.contains(param.getInvitedUserName()));
			}
			if(StringUtil.isNotEmpty(param.getInvitedMobile())) {
				selectInvites.and(a.MOBILE.contains(param.getInvitedMobile()));
			}
			ArrayList<Integer> inviteIds = new ArrayList<Integer>();
			List<Integer> invites = selectInvites.fetch().into(Integer.class);
			for(int invite : invites) {
				inviteIds.add(invite);
			}
			where.and(a.USER_ID.in(inviteIds));
		}
		//分销分组
		if(param.getDistributorGroup() != null) {
			where.and(d.INVITE_GROUP.eq(param.getDistributorGroup()));
		}
		//分销等级
		if(param.getDistributorLevel() != null) {
			where.and(d.DISTRIBUTOR_LEVEL.eq(param.getDistributorLevel()));
		}
		//有下级用户
        if(param.getHaveNextUser() !=null && param.getHaveNextUser()== 1){
            where.and(d.USER_ID.in(db().select(a.INVITE_ID).from(a).fetch()));
        }
        //有手机号
        if(param.getHaveMobile() != null &&  param.getHaveMobile() == 1){
            where.and(d.MOBILE.isNotNull());
        }
        //有真是姓名
        if(param.getHaveRealName() != null && param.getHaveRealName() == 1){
            where.and(USER_DETAIL.REAL_NAME.isNotNull());
        }
        if(param.getOptGroupId() != null){
            where.and(a.INVITE_GROUP.ne(param.getOptGroupId()));
        }
        where.groupBy(d.USER_ID,recordTable.field("nextNumber"),record2Table.field("sublayerNumber"), record3Table.field("totalCanFanliMoney"),
            record3Table.field("totalFanliMoney"), record2Table2.field("waitFanliMoney"),d.USERNAME,d.MOBILE,d.INVITATION_CODE,
            d.CREATE_TIME,USER_DETAIL.REAL_NAME,DISTRIBUTOR_LEVEL.LEVEL_NAME,DISTRIBUTOR_GROUP.GROUP_NAME,d.INVITE_ID);

        //表头排序
        if(param.getSortField().equals(SORT_BY_NEXT_NUM)){
            if(param.getSortWay().equals("asc")){
                where.orderBy(recordTable.field("nextNumber").asc());
            }else{
                where.orderBy(recordTable.field("nextNumber").desc());
            }
        }

        if(param.getSortField().equals(SORT_BY_SUBLAYER_NUM)){
           if(param.getSortWay().equals("asc")){
               where.orderBy(record2Table.field("sublayerNumber").asc());
           }else{
               where.orderBy(record2Table.field("sublayerNumber").desc());
            }
        }

        if(param.getSortField().equals(SORT_BY_TOTAL_CAN_FANLI)){
            if(param.getSortWay().equals("asc")){
                where.orderBy(record3Table.field("totalCanFanliMoney").asc());
            }else{
                where.orderBy(record3Table.field("totalCanFanliMoney").desc());
            }
        }

        if(param.getSortField().equals(SORT_BY_TOTAL_FANLI)){
            if(param.getSortWay().equals("asc")){
                where.orderBy(record3Table.field("totalFanliMoney").asc());
            }else{
                where.orderBy(record3Table.field("totalFanliMoney").desc());
            }
        }

        if(param.getSortField().equals(SORT_BY_WAIT_FANLI)){
            if(param.getSortWay().equals("asc")){
                where.orderBy(record2Table2.field("waitFanliMoney").asc());
            }else{
                where.orderBy(record2Table2.field("waitFanliMoney").desc());
            }
        }

		return where;
	}

	/**
	 * 分销员已邀请用户列表
	 * @param param
	 * @return
	 */
	public DistributorInvitedListVo getInvitedList(DistributorInvitedListParam param) {
		SelectJoinStep<? extends Record> select = db().select(USER.USER_ID,USER.USERNAME,USER_DETAIL.REAL_NAME,USER.MOBILE,USER.CREATE_TIME,USER.INVITE_EXPIRY_DATE,USER.INVITE_TIME,USER.INVITE_PROTECT_DATE,sum(USER_FANLI_STATISTICS.ORDER_NUMBER).as("ORDER_NUMBER"),sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("TOTAL_CAN_FANLI_MONEY"),sum(USER_FANLI_STATISTICS.TOTAL_FANLI_MONEY).as("TOTAL_FANLI_MONEY"))
            .from(USER.leftJoin(USER_FANLI_STATISTICS).on(USER.USER_ID.eq(USER_FANLI_STATISTICS.USER_ID)))
            .leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID));
		List<Integer> userIds = null;
        SelectConditionStep<? extends Record> sql = getInvitedListOptions(select,param,userIds);
		PageResult<InviteUserInfoVo> invitedList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), InviteUserInfoVo.class);
		BigDecimal totalGetFanliMoney = new BigDecimal(0);
        DistributorInvitedListVo InviteInfo = new DistributorInvitedListVo();
		for(InviteUserInfoVo info:invitedList.dataList){
		    if(info.getTotalFanliMoney() != null){
                totalGetFanliMoney = totalGetFanliMoney.add(info.getTotalFanliMoney());
            }
        }
        InviteInfo.setTotalGetFanliMoney(totalGetFanliMoney);
		InviteInfo.setInviteUserInfo(invitedList);
		return InviteInfo;
	}

	/**
	 * 清除分销员身份
	 * @param userId
	 * @return
	 */
	public int delDistributor(Integer userId) {
		int res = db().update(USER).set(USER.IS_DISTRIBUTOR,(byte)0)
            .set(USER.INVITATION_CODE, (String) null)
            .where(USER.USER_ID.eq(userId)).execute();
		return res;
	}

	/**
	 * 已邀请用户列表条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<? extends Record> getInvitedListOptions(SelectJoinStep<? extends Record> select,DistributorInvitedListParam param,List<Integer> userIds) {
        SelectConditionStep<? extends Record> sql = select.where(USER.INVITE_ID.gt(0));
        if(param.getInviteType() == 0) {
            sql = select.where(USER.INVITE_ID.eq(param.getUserId()));
        }
	    if(param.getInviteType() == 1){
            sql = select.where(USER.INVITE_ID.in(userIds));
	    }

		if(StringUtil.isNotEmpty(param.getMobile())) {
			sql = sql.and(USER.MOBILE.contains(param.getMobile()));
		}
		if(StringUtil.isNotEmpty(param.getUsername())) {
			sql = sql.and(USER.USERNAME.contains(param.getUsername()));
		}
        if(StringUtil.isNotEmpty(param.getRealName())) {
            sql = sql.and(USER_DETAIL.REAL_NAME.contains(param.getRealName()));
        }
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			sql = sql.and(USER.CREATE_TIME.ge(param.getStartCreateTime()).and(USER.CREATE_TIME.le(param.getEndCreateTime())));
		}
        if(param.getStartInviteTime() != null && param.getEndInviteTime() != null) {
            sql = sql.and(USER.INVITE_TIME.ge(param.getStartInviteTime()).and(USER.INVITE_TIME.le(param.getEndInviteTime())));
        }
		sql.groupBy(USER_FANLI_STATISTICS.USER_ID,USER.USERNAME,USER.MOBILE,USER.CREATE_TIME,USER.INVITE_EXPIRY_DATE,USER.INVITE_PROTECT_DATE,USER.INVITE_TIME,USER.USER_ID,USER_DETAIL.REAL_NAME);
		return sql;
	}

    /**
     * 间接邀请用户列表
     * @param param
     */
    public DistributorInvitedListVo getIndirectInviteList(DistributorInvitedListParam param){
	    //直接下级
        Result<Record1<Integer>> record = db().select(USER.USER_ID).from(USER).where(USER.INVITE_ID.eq(param.getUserId())).fetch();
        System.out.println("record:"+ record);
        if(record != null){
           List<Integer> userIds = record.into(Integer.class);
            SelectJoinStep<? extends Record> select = db().select(USER.USER_ID,USER.USERNAME,USER_DETAIL.REAL_NAME,USER.MOBILE,USER.CREATE_TIME,USER.INVITE_EXPIRY_DATE,USER.INVITE_TIME,USER.INVITE_PROTECT_DATE,sum(USER_FANLI_STATISTICS.ORDER_NUMBER).as("ORDER_NUMBER"),sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("TOTAL_CAN_FANLI_MONEY"),sum(USER_FANLI_STATISTICS.TOTAL_FANLI_MONEY).as("TOTAL_FANLI_MONEY"))
                .from(USER.leftJoin(USER_FANLI_STATISTICS).on(USER.USER_ID.eq(USER_FANLI_STATISTICS.USER_ID)))
                .leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID));
            param.setInviteType((byte)1);
            SelectConditionStep<? extends Record> sql = getInvitedListOptions(select,param,userIds);
            PageResult<InviteUserInfoVo> invitedList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), InviteUserInfoVo.class);
            BigDecimal totalGetFanliMoney = new BigDecimal(0);
            DistributorInvitedListVo InviteInfo = new DistributorInvitedListVo();
            for(InviteUserInfoVo info:invitedList.dataList){
                if(info.getTotalFanliMoney() != null){
                    totalGetFanliMoney = totalGetFanliMoney.add(info.getTotalFanliMoney());
                }
            }
            InviteInfo.setTotalGetFanliMoney(totalGetFanliMoney);
            InviteInfo.setInviteUserInfo(invitedList);
            return InviteInfo;
        }else{
            return null;
        }
    }

	/**
	 * 获取返利订单数量
	 * @param userId
	 * @return
	 */
	public int getRebateOrderNum(Integer userId) {
		return db().fetchCount(USER_FANLI_STATISTICS, USER_FANLI_STATISTICS.FANLI_USER_ID.eq(userId));
	}

    /**
     *给分销员设置分销邀请码
     * @param param
     * @return
     */
	public int setInviteCode(SetInviteCodeParam param){
        Integer res = mpDis.sentInviteCodeVerify(param.getInviteCode());
        if(res == 0){
            db().update(USER).set(USER.INVITATION_CODE,param.getInviteCode()).where(USER.USER_ID.eq(param.getUserId())).execute();
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 根据分销配置自动给没有分销邀请码的分销员生成邀请码
     * @return
     */
    public int autoSetInviteCode(){
        //没有邀请码的分销员
        Result<Record1<Integer>> record = db().select(USER.USER_ID).from(USER).where(USER.IS_DISTRIBUTOR.eq((byte) 1)).and(USER.INVITATION_CODE.isNull()).fetch();
        System.out.println(record);
        int res = 0;
        if(record != null){
            List<Integer> noInvitationCode = record.into(Integer.class);
            System.out.println(noInvitationCode);
            for(Integer userId:noInvitationCode){
                String code = mpDis.generateInvitationCode();
                SetInviteCodeParam setInviteCodeParam = new SetInviteCodeParam();
                setInviteCodeParam.setUserId(userId);
                setInviteCodeParam.setInviteCode(code);
                res = setInviteCode(setInviteCodeParam);
            }
        }
        return res;
	}

    /**
     * 会员备注列表
     * @param param
     * @return
     */
    public List<UserRemarkListVo> userRemarkList(UserRemarkListVo param){
        Result<Record> record = db().select().from(USER_REMARK).where(USER_REMARK.USER_ID.eq(param.getUserId())).and(USER_REMARK.IS_DELETE.eq((byte)0)).fetch();
        if(record != null){
            return record.into(UserRemarkListVo.class);
        }else{
            return null;
        }
    }

    /**
     *添加会员备注
     * @param param
     * @return
     */
    public int addUserRemark(UserRemarkListVo param){
        UserRemarkRecord record = new UserRemarkRecord();
        assign(param, record);
        return db().executeInsert(record);
    }

    /**
     * 删除会员备注
     * @param id
     * @return
     */
    public int delUserRemark(Integer id){
        int res = db().update(USER_REMARK).set(USER_REMARK.IS_DELETE, (byte) 1).where(USER_REMARK.ID.eq(id)).execute();
        return res;
    }

    /**
     * 分销员设置分组
     * @param param
     * @return
     */
    public int setGroup(DistributorSetGroupParam param){
        int res = db().update(USER).set(USER.INVITE_GROUP, param.getGroupId()).where(USER.USER_ID.in(param.getUserId())).execute();
        return res;
    }

    /**
     * 获取分销员的分组名称
     * @return 分组名称
     */
    public String getGroupName(Integer userId) {
        return db().select(DISTRIBUTOR_GROUP.GROUP_NAME)
        	.from(USER.leftJoin(DISTRIBUTOR_GROUP).on(DISTRIBUTOR_GROUP.ID.eq(USER.INVITE_GROUP)))
        	.where(USER.USER_ID.eq(userId))
        	.fetchAnyInto(String.class);
    }


}
