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
		DistributorApplyRecord record = new DistributorApplyRecord();
		assign(param, record);
        int re = db().executeInsert(record);
		//获取分销配置，成为分销员是否需要审核
        DistributionParam cfg = this.distributionCfg.getDistributionCfg();
        if(cfg.getJudgeStatus() == 0){//不用审核，自动成为分销员
            DistributorApplyDetailParam res = db().select().from(DISTRIBUTOR_APPLY).where(DISTRIBUTOR_APPLY.USER_ID.eq(param.getUserId())).
                orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc()).limit(1).fetchOne().into(DistributorApplyDetailParam.class);
            db().update(DISTRIBUTOR_APPLY).set(DISTRIBUTOR_APPLY.STATUS, (byte) 1).where(DISTRIBUTOR_APPLY.ID.eq(res.getId())).execute();
        }
        return re;
		
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
