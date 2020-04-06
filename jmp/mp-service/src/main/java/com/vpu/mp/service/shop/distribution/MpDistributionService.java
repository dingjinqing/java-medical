package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.DistributorLevel;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.DistributorApplyRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.MemberMarriageEnum;
import com.vpu.mp.service.pojo.shop.member.data.EducationVo;
import com.vpu.mp.service.pojo.shop.member.data.IndustryVo;
import com.vpu.mp.service.pojo.shop.member.data.MarriageData;
import com.vpu.mp.service.pojo.wxapp.distribution.ActivationInfoVo;
import com.vpu.mp.service.pojo.wxapp.distribution.DistributorApplyDetailParam;
import com.vpu.mp.service.pojo.wxapp.distribution.UserBaseInfoVo;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static org.jooq.impl.DSL.sum;

/**
 * mp分销模块service
 */
@Service
public class MpDistributionService extends ShopBaseService{

    @Autowired
    public DistributionConfigService distributionCfg;

    @Autowired
    public BrokerageStatisticalService bs;

    @Autowired
    public UserTotalFanliService userTotalFanli;

    @Autowired
    public DistributorListService distributorList;

    @Autowired
    public DistributorGroupService distributorGroup;

    @Autowired
    public DistributorListService disList;

    /**
     * 申请分销员页面信息
     * @param lang
     * @return
     */
    public ActivationInfoVo getActivationInfo(Integer userId,String lang){
        //获取用户基本信息
        UserBaseInfoVo baseInfo = db().select(USER.MOBILE,USER_DETAIL.REAL_NAME,USER_DETAIL.ADDRESS,USER_DETAIL.PROVINCE_CODE,USER_DETAIL.CITY_CODE,USER_DETAIL.DISTRICT_CODE,
            USER_DETAIL.EDUCATION,USER_DETAIL.INDUSTRY_INFO,USER_DETAIL.MARITAL_STATUS,USER_DETAIL.SEX)
            .from(USER.leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID)))
            .where(USER.USER_ID.eq(userId)).fetchOne().into(UserBaseInfoVo.class);

        //分销分组信息
        List<DistributorGroupListVo> groupList = bs.getGroupList();

        //转换行业码对应的名称
        if(baseInfo.getIndustryInfo() != null){
            String industryInfo = MemberIndustryEnum.getNameByCode(baseInfo.getIndustryInfo(),lang);
            baseInfo.setIndustryName(industryInfo);
        }
        //教育程度
        if(baseInfo.getEducation() != null){
            String education = MemberEducationEnum.getNameByCode(baseInfo.getEducation(),lang);
            baseInfo.setEducationName(education);
        }
        //性别
        if(baseInfo.getSex() != null){
            if(baseInfo.getSex().equalsIgnoreCase("f")){
                baseInfo.setSex("女");
            }else if(baseInfo.getSex() != null && baseInfo.getSex().equalsIgnoreCase("m")){
                baseInfo.setSex("男");
            }
        }
        //婚姻状况
        if(baseInfo.getMaritalStatus() != null){
            String maritalInfo = MemberMarriageEnum.getNameByCode(baseInfo.getMaritalStatus(),lang);
            baseInfo.setMaritalName(maritalInfo);
        }

        //获取分销配置，成为分销员是否需要审核
        DistributionParam cfg = this.distributionCfg.getDistributionCfg();
        ActivationInfoVo activationInfo = new ActivationInfoVo();
        //用户基本信息
        activationInfo.setUserBaseInfo(baseInfo);
        //分组列表
        activationInfo.setGroupList(groupList);
        //获取行业信息
        List<IndustryVo> allIndustryInfo = MemberIndustryEnum.getAllIndustryInfo(lang);
        activationInfo.setIndustryList(allIndustryInfo);
        //获取教育程度
        List<EducationVo> allEducationWithCode = MemberEducationEnum.getAllEducationWithCode(lang);
        activationInfo.setEducationList(allEducationWithCode);
        //获取婚姻状况
        List<MarriageData> allMarriageWithCode = MemberMarriageEnum.getAllMarriageWithCode(lang);
        activationInfo.setMarriageData(allMarriageWithCode);
        activationInfo.setCfg(cfg);
        return activationInfo;
    }
	/**
	 * 用户申请成为分销员
	 * @param param
	 * @return state申请状态 -1：邀请码不存在；0：审核中；1：审核通过
	 */
	public int distributorApply(DistributorApplyParam param) {
	    //生成有效邀请码
        String inviteCode = this.validInviteCode();
		DistributorApplyRecord record = new DistributorApplyRecord();
		//获取分销配置，成为分销员是否需要审核
        DistributionParam cfg = this.distributionCfg.getDistributionCfg();
        Integer state = 0;
        if(cfg.getJudgeStatus() == 0){//自动审核
            //校验申请提交的邀请码是否有效
            Integer ret = sentInviteCodeVerify(param.getActivationFields().getInvitationCode());
            if(ret == 0){
                //邀请码不存在
               state = -1;
            }else{//自动审核通过
                String checkInfo = Util.toJson(param.getActivationFields());
                assign(param, record);
                record.setActivationFields(checkInfo);
                db().executeInsert(record);
                DistributorApplyDetailParam res = db().select().from(DISTRIBUTOR_APPLY).where(DISTRIBUTOR_APPLY.USER_ID.eq(param.getUserId())).
                    orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc()).limit(1).fetchOne().into(DistributorApplyDetailParam.class);
                //更新申请表数据
                db().update(DISTRIBUTOR_APPLY)
                    .set(DISTRIBUTOR_APPLY.STATUS, (byte) 1)
                    .set(DISTRIBUTOR_APPLY.IS_AUTO_PASS,(byte)1)
                    .where(DISTRIBUTOR_APPLY.ID.eq(res.getId())).execute();

                //邀请保护时间
                Timestamp inviteProtectDate =DateUtil.getTimeStampPlus(cfg.getProtectDate(), ChronoUnit.DAYS);
                Date pd = new Date(inviteProtectDate.getTime());
                //邀请失效时间
                Timestamp inviteExpiryDate = DateUtil.getTimeStampPlus(cfg.getVaild(), ChronoUnit.DAYS);
                Date ed = new Date(inviteExpiryDate.getTime());
                //分销分组
                Integer inviteGroup = 0;
                if(param.getActivationFields().getRebateGroup() != null){
                    inviteGroup = param.getActivationFields().getRebateGroup();
                }
                //邀请人id
                Integer inviteUserId = db().select(USER.USER_ID).from(USER)
                    .where(USER.INVITATION_CODE.eq(param.getActivationFields().getInvitationCode())).fetchOne().into(Integer.class);
                //更新用户信息
                db().update(USER)
                    .set(USER.IS_DISTRIBUTOR, (byte) 1)
                    .set(USER.INVITATION_CODE,inviteCode)
                    .set(USER.INVITE_EXPIRY_DATE,ed)
                    .set(USER.INVITE_PROTECT_DATE,pd)
                    .set(USER.INVITE_GROUP,inviteGroup)
                    .set(USER.INVITE_ID,inviteUserId)
                    .set(USER.INVITE_TIME,DateUtil.getLocalDateTime())
                    .where(USER.USER_ID.eq(res.getUserId())).execute();
                //审核通过
                state = 1;
            }
        }else{//后台手动审核
            String checkInfo = Util.toJsonNotNull(param.getActivationFields());
            assign(param, record);
            record.setActivationFields(checkInfo);
            db().executeInsert(record);
        }
        return state;
	}

    /**
     * 生成分销邀请码
     * @return
     */
	public String generateInvitationCode(){
        String inviteCode = "";
        char[] Str = "0123456789abcdefghijkmlnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * Str.length);
            inviteCode += Str[index];
        }
        return inviteCode;
    }

    /**
     * 判断邀请码是否重复,生成有效邀请吗
     * @param
     * @return
     */
    public String validInviteCode(){
        String inviteCode = this.generateInvitationCode();
        Integer ct = db().selectCount().from(USER).where(USER.INVITATION_CODE.eq(inviteCode)).fetchOne().into(Integer.class);
        while (ct != 0){
            inviteCode = this.generateInvitationCode();
            ct = db().selectCount().from(USER).where(USER.INVITATION_CODE.equalIgnoreCase(inviteCode)).fetchOne().into(Integer.class);
        }
        return inviteCode;
    }

    /**
     * 自动审核校验邀请码是否正确
     * @param sentCode
     * @return
     */
    public Integer sentInviteCodeVerify(String sentCode){
        Record record = db().selectCount().from(USER).where(USER.INVITATION_CODE.eq(sentCode)).fetchOne();
        if(record != null){
            return record.into(Integer.class);
        }else{
            return 0;
        }
    }

    /**
     * 获取申请分销员审核状态
     * @param param
     * @return
     */
	public DistributorApplyDetailParam getDistributorApplyDetail(DistributorApplyDetailParam param){
	    //获取最新申请记录
	   Record record = db().select().from(DISTRIBUTOR_APPLY).where(DISTRIBUTOR_APPLY.USER_ID.eq(param.getUserId())).
           orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc()).limit(1).fetchOne();
	   if(record != null){
           return record.into(DistributorApplyDetailParam.class);
       }else {
	       return null;
       }
    }

    /**
     * 获取分销推广文案
     * @return
     */
    public DistributionDocumentParam getDistributorDoc(){
        DistributionDocumentParam distributionDocument = distributionCfg.getDistributionDocument();
        return distributionDocument;
    }

    /**
     * 分销个人中心
     * @param userId
     * @return
     */
    public RebateCenterVo rebateCenter(Integer userId){
        RebateCenterVo rebateCenterVo = new RebateCenterVo();
        //用户信息
        BigDecimal account = db().select(USER.ACCOUNT).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().into(BigDecimal.class);
        //返利信息
        UserTotalFanliVo userRebate = this.userTotalFanli.getUserRebate(userId);
        if(userRebate.getTotalMoney() != null && userRebate.getTotalMoney().compareTo(account)<0){
            rebateCenterVo.setCanWithdraw(userRebate.getTotalMoney());
        }else{
            rebateCenterVo.setCanWithdraw(account);
        }
        rebateCenterVo.setTotalWithdraw(userRebate.getTotalMoney());
        //待返利佣金
        BigDecimal waitFanliMoney = this.waitFanliMoney(userId);
        rebateCenterVo.setWaitWithdraw(waitFanliMoney);
        //我的邀请码
        UserRecord userInfo = db().select().from(USER).where(USER.USER_ID.eq(userId)).fetchOne().into(UserRecord.class);
        rebateCenterVo.setInvitationCode(userInfo.getInvitationCode());
        //邀请用户数
        Integer inviteUserNum = this.inviteUserNum(userId);
        rebateCenterVo.setInviteUserNum(inviteUserNum);
        //返利订单数
        Integer rebateOrderNum = distributorList.getRebateOrderNum(userId);
        rebateCenterVo.setRebateOrderNum(rebateOrderNum);
        //累积商品返利总额
        BigDecimal TotalCanFanliMoney = this.TotalCanFanliMoney(userId);
        rebateCenterVo.setTotalCanFanliMoney(TotalCanFanliMoney);
        //我的等级
        DistributorLevelParam distributorLevelInfo = this.distributorLevel(userId);
        rebateCenterVo.setDistributorLevel(distributorLevelInfo.getLevelName());
        //我的分组
        DistributorGroupListVo groupInfo = distributorGroup.getGroupByUserId(userId);
        rebateCenterVo.setDistributorGroup(groupInfo.getGroupName());
        return rebateCenterVo;

    }

    /**
     * 待返利佣金金额
     * @param userId
     * @return
     */
    public BigDecimal waitFanliMoney(Integer userId){
        //待返利佣金金额
        BigDecimal waitFanliMoney = db().select(sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("wait_fanli_money")).from(ORDER_GOODS_REBATE
            .leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_INFO.ORDER_SN)))
            .where(ORDER_INFO.SETTLEMENT_FLAG.eq((byte)0))
            .and(ORDER_INFO.ORDER_STATUS.ge((byte)3))
            .and(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(userId))
            .fetchOne().into(BigDecimal.class);
        return waitFanliMoney;
    }

    /**
     * 邀请用户数
     * @param userId
     * @return
     */
    public Integer inviteUserNum(Integer userId){
        int inviteNum = db().selectCount().from(USER).where(USER.INVITE_ID.eq(userId)).fetchOne().into(Integer.class);
        return inviteNum;
    }

    /**
     * 累积返利商品总额
     * @param userId
     * @return
     */
    public BigDecimal TotalCanFanliMoney(Integer userId){
        //累积返利商品总额
        BigDecimal TotalCanFanliMoney = db().select(sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("can_fanli_goods_money"))
            .from(USER_FANLI_STATISTICS).where(USER_FANLI_STATISTICS.FANLI_USER_ID.eq(userId))
            .fetchOne().into(BigDecimal.class);
        return TotalCanFanliMoney;
    }

    /**
     * 分销员等级
     * @param userId
     * @return
     */
    public DistributorLevelParam distributorLevel(Integer userId){
        DistributorLevelParam distributorLevelInfo = db().select(DISTRIBUTOR_LEVEL.LEVEL_NAME).from(USER.leftJoin(DISTRIBUTOR_LEVEL).on(USER.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID)))
            .where(USER.USER_ID.eq(userId)).fetchOne().into(DistributorLevelParam.class);
        return distributorLevelInfo;
    }

    /**
     * 分销员邀请下级用户（我邀请的用户）
     * @param param
     * @return
     */
    public PageResult<DistributorInvitedListVo> myInviteUser(DistributorInvitedListParam param){
        PageResult<DistributorInvitedListVo> invitedList = disList.getInvitedList(param);
        return invitedList;
    }

    public void rebateOrder(){
        //TODO：返利订单查询
    }
}
