package com.vpu.mp.dao.shop.recharge;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.SmsRechargeDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.SmsRecharge;
import com.vpu.mp.db.shop.tables.records.SmsRechargeRecord;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeListVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsRechargeRecordVo;
import org.springframework.stereotype.Repository;

/**
 * @author 赵晓东
 * @description 充值记录
 * @create 2020-07-27 16:26
 **/

@Repository
public class SmsRechargeDao extends ShopBaseDao {

    /**
     * 插入拉取的充值记录
     * @param smsAccountRechargeListVo 二方库拉取充值记录回参
     */
    public void fetchRechargeList(SmsAccountRechargeListVo smsAccountRechargeListVo) {
        for (SmsRechargeRecordVo smsRechargeRecordVo : smsAccountRechargeListVo.getData()) {
            SmsRechargeDo rechargeDo = new SmsRechargeDo();
            rechargeDo.setSid(smsAccountRechargeListVo.getSid());
            rechargeDo.setVersion(smsAccountRechargeListVo.getVersion());
            rechargeDo.setTotal(smsAccountRechargeListVo.getTotal());
            FieldsUtil.assign(smsRechargeRecordVo, rechargeDo);
            SmsRechargeRecord rechargeRecord = db().newRecord(SmsRecharge.SMS_RECHARGE, rechargeDo);
            rechargeRecord.insert();
        }
    }

}
