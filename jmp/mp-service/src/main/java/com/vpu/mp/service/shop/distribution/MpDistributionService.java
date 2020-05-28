package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.DistributorLevel;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.DistributorApplyRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRebateRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
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
import com.vpu.mp.service.pojo.wxapp.distribution.*;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import jodd.util.StringUtil;
import org.jooq.*;
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
        DistributorApplyDetailParam detail = new DistributorApplyDetailParam();
	    //用户是否分销员
        Integer isDistributor = this.isDistributor(param.getUserId());
        //获取最新申请记录
	   Record record = db().select().from(DISTRIBUTOR_APPLY).where(DISTRIBUTOR_APPLY.USER_ID.eq(param.getUserId())).
           orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc()).limit(1).fetchOne();
	   if(record != null){
           detail = record.into(DistributorApplyDetailParam.class);
           if(isDistributor == 0 && detail.getStatus() == 1){
               detail.setStatus(3);
           }
       }else {
           detail.setStatus(3);
       }
	   return detail;
    }

    /**
     * 判断用户是否为分销员
     * @param userId
     * return 0：否；1：是
     */
    public Integer isDistributor(Integer userId){
        Integer isDistributor = db().select(USER.IS_DISTRIBUTOR).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().into(Integer.class);
        return isDistributor;
	}

    /**
     * 获取分销推广文案
     * @return
     */
    public DistributionDocumentParam getDistributorDoc(){
        DistributionDocumentParam distributionDocument = distributionCfg.getDistributionDocument();
        //获取分销审核开关配置
        Byte activation = this.distributionCfg.getDistributionCfg().getActivation();
        distributionDocument.setActivation(activation);
        return distributionDocument;
    }

    /**
     * 分销个人中心
     * @param userId
     * @return
     */
    public RebateCenterVo rebateCenter(Integer userId) {
        RebateCenterVo rebateCenterVo = new RebateCenterVo();
        //是否是分销员
        Integer isDistributor = this.isDistributor(userId);
        rebateCenterVo.setIsDistributor(isDistributor);
        //分销开关是否开启
        DistributionParam distributionCfg = this.distributionCfg.getDistributionCfg();
        rebateCenterVo.setStatus(distributionCfg.getStatus());
        //分销审核开关是否开启
        rebateCenterVo.setJudgeStatus(distributionCfg.getJudgeStatus());
        //用户信息
        BigDecimal account = db().select(USER.ACCOUNT).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().into(BigDecimal.class);
        //返利信息
        UserTotalFanliVo userRebate = this.userTotalFanli.getUserRebate(userId);
        if (userRebate.getTotalMoney() != null && userRebate.getTotalMoney().compareTo(account) < 0) {
            rebateCenterVo.setCanWithdraw(userRebate.getTotalMoney());
        } else {
            rebateCenterVo.setCanWithdraw(account);
        }
        if (distributionCfg.getStatus() != 1 || (isDistributor != 1 && distributionCfg.getJudgeStatus() == 1)){
            if (distributionCfg.getWithdrawStatus() != 1) {
                BigDecimal account1 = new BigDecimal("0.00");
                rebateCenterVo.setCanWithdraw(account1);
            }
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

    /**
     * 邀请用户返利订单
     * @param param
     * @return
     */
    public PageResult<RebateOrderVo> rebateOrder(RebateOrderParam param){
        SelectOnConditionStep<? extends Record> select = db().select(ORDER_GOODS_REBATE.ORDER_SN, ORDER_INFO.CREATE_TIME, ORDER_INFO.FINISHED_TIME,
            ORDER_INFO.ORDER_STATUS, ORDER_INFO.ORDER_STATUS_NAME, USER.USERNAME, sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("fanliMoney"),
            ORDER_GOODS_REBATE.REBATE_LEVEL)
            .from(ORDER_GOODS_REBATE)
            .leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
            .leftJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
        SelectOnConditionStep<? extends Record> sql = rebateOrderOptions(select, param);
        PageResult<RebateOrderVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getRowsPage(), RebateOrderVo.class);

        for(RebateOrderVo list : pageResult.dataList){
            getCanCalculateMoney(list.getOrderSn(),param.getUserId());
        }
        //TODO 计算实际返利信息

        return pageResult;
    }

    /**
     * 返利订单条件查询
     * @param select
     * @param param
     * @return
     */
    public SelectOnConditionStep<? extends Record> rebateOrderOptions(SelectOnConditionStep<? extends Record> select,RebateOrderParam param){
        select.where(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(param.getUserId()));
        if(param.getStartTime() != null && param.getStartTime() != null){
            select.and(ORDER_INFO.CREATE_TIME.gt(param.getStartTime())).and(ORDER_INFO.CREATE_TIME.lt(param.getEndTime()));
        }
        select.groupBy(ORDER_GOODS_REBATE.ORDER_SN, ORDER_INFO.CREATE_TIME, ORDER_INFO.FINISHED_TIME,
            ORDER_INFO.ORDER_STATUS, ORDER_INFO.ORDER_STATUS_NAME, USER.USERNAME,
            ORDER_GOODS_REBATE.REBATE_LEVEL);
        return select;
    }

    /**
     * 计算订单返利金额
     * @param orderSn
     * @param rebateUserId
     * @return
     */
    public BigDecimal getCanCalculateMoney(String orderSn,Integer rebateUserId) {
        //查询商品行信息
        List<OrderGoodsRecord> orderGoodsInfo = db().select(ORDER_GOODS.ORDER_SN, ORDER_GOODS.PRODUCT_ID, ORDER_GOODS.CAN_CALCULATE_MONEY, ORDER_GOODS.GOODS_NUMBER, ORDER_GOODS.RETURN_NUMBER)
            .from(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetch().into(OrderGoodsRecord.class);

        //得到订单信息 SETTLEMENT_FLAG：结算标记：0未结算；1结算
        Integer settlementFlag = db().select(ORDER_INFO.SETTLEMENT_FLAG).from(ORDER_INFO).where(ORDER_INFO.ORDER_SN.eq(orderSn)).fetchOne().into(Integer.class);
        BigDecimal calculateMoney = new BigDecimal(0);
        for (OrderGoodsRecord item : orderGoodsInfo) {
            if (rebateUserId > 0 && settlementFlag == 1) {
                Record2<BigDecimal, BigDecimal> record = db().select(ORDER_GOODS_REBATE.REBATE_MONEY, ORDER_GOODS_REBATE.REAL_REBATE_MONEY).from(ORDER_GOODS_REBATE).where(ORDER_GOODS_REBATE.ORDER_SN.eq(item.getOrderSn())).and(ORDER_GOODS_REBATE.PRODUCT_ID.eq(item.getProductId()))
                    .and(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(rebateUserId)).fetchOne();
                if (record != null) {
                    OrderGoodsRebateRecord goodsRebate = record.into(OrderGoodsRebateRecord.class);
                    if (goodsRebate.getRebateMoney().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal divide = goodsRebate.getRealRebateMoney().divide(goodsRebate.getRebateMoney(), 2);
                        calculateMoney = calculateMoney.multiply(divide);
                    } else {
                        BigDecimal goodsNumber = BigDecimal.valueOf(item.getGoodsNumber());
                        BigDecimal returnNumber = BigDecimal.valueOf(item.getReturnNumber());
                        calculateMoney = calculateMoney.add(item.getCanCalculateMoney().multiply(goodsNumber.subtract(returnNumber)));
                    }
                }
            } else {
                BigDecimal goodsNumber = BigDecimal.valueOf(item.getGoodsNumber());
                BigDecimal returnNumber = BigDecimal.valueOf(item.getReturnNumber());
                calculateMoney = calculateMoney.add(item.getCanCalculateMoney().multiply(goodsNumber.subtract(returnNumber)));
            }

        }
        return calculateMoney;
    }
}
