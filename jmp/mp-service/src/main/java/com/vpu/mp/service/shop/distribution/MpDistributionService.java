package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.records.DistributorApplyRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.wxapp.distribution.DistributorApplyDetailParam;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_APPLY;
import static com.vpu.mp.db.shop.Tables.USER;

/**
 * mp分销模块service
 */
@Service
public class MpDistributionService extends ShopBaseService{

    @Autowired
    public DistributionConfigService distributionCfg;

	/**
	 * 用户申请成为分销员
	 * @param param
	 * @return
	 */
	public int distributorApply(DistributorApplyParam param) {
	    //生成有效邀请码
        String inviteCode = this.validInviteCode();
		DistributorApplyRecord record = new DistributorApplyRecord();
		assign(param, record);
        int re = db().executeInsert(record);
		//获取分销配置，成为分销员是否需要审核
        DistributionParam cfg = this.distributionCfg.getDistributionCfg();
        if(cfg.getJudgeStatus() == 0){//不用审核，自动成为分销员
            //校验申请提交的邀请码是否有效
            Integer ret = sentInviteCodeVerify(param.getInviteCode());
            if(ret != 0){
                //返回无效邀请码
            }else{//自动审核通过
                param.setInviteCode(null);
                assign(param, record);
                DistributorApplyDetailParam res = db().select().from(DISTRIBUTOR_APPLY).where(DISTRIBUTOR_APPLY.USER_ID.eq(param.getUserId())).
                    orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc()).limit(1).fetchOne().into(DistributorApplyDetailParam.class);
                db().update(DISTRIBUTOR_APPLY).set(DISTRIBUTOR_APPLY.STATUS, (byte) 1).where(DISTRIBUTOR_APPLY.ID.eq(res.getId())).execute();
                //更新用户信息
//                db().update(USER)
//                    .set(USER.IS_DISTRIBUTOR, (byte) 1)
//                    .set(USER.INVITATION_CODE)
//                    .where(DISTRIBUTOR_APPLY.ID.eq(res.getId())).execute();
            }

        }
        return re;
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
        Record record = db().selectCount().from(USER).where(USER.INVITATION_CODE.equalIgnoreCase(inviteCode)).fetchOne();
        while (record != null){
            inviteCode = this.generateInvitationCode();
            record = db().selectCount().from(USER).where(USER.INVITATION_CODE.equalIgnoreCase(inviteCode)).fetchOne();
        }
        return inviteCode;
    }

    /**
     * 自动审核校验邀请码是否正确
     * @param sentCode
     * @return
     */
    public Integer sentInviteCodeVerify(String sentCode){
        Record record = db().selectCount().from(USER).where(USER.INVITATION_CODE.equalIgnoreCase(sentCode)).fetchOne();
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
