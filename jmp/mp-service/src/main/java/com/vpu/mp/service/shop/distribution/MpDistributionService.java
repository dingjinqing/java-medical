package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.records.DistributorApplyRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * mp分销模块service
 */
@Service
public class MpDistributionService extends ShopBaseService{

    @Autowired
    public DistributionConfigService distributionCfg;

    @Autowired
    public BrokerageStatisticalService bs;

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
        if(baseInfo.getSex().equalsIgnoreCase("f")){
            baseInfo.setSex("女");
        }else if(baseInfo.getSex().equalsIgnoreCase("m")){
            baseInfo.setSex("男");
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
}
