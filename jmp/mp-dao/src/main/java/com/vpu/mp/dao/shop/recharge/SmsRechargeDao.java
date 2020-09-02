package com.vpu.mp.dao.shop.recharge;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.SmsRechargeDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.SmsRecharge;
import com.vpu.mp.db.shop.tables.records.SmsRechargeRecord;
import com.vpu.mp.service.pojo.shop.recharge.RechargeParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeListVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsRechargeRecordVo;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.SMS_RECHARGE;
import static com.vpu.mp.db.shop.Tables.SMS_SEND_RECORD;
import static com.vpu.mp.service.pojo.shop.sms.SmsSendRecordConstant.SMS_SEND_STATUS_SUCCESS;

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
    public void  fetchRechargeList(SmsAccountRechargeListVo smsAccountRechargeListVo) {
        for (SmsRechargeRecordVo smsRechargeRecordVo : smsAccountRechargeListVo.getData()) {
            SmsRecharge smsRecharge = db().select().from(SmsRecharge.SMS_RECHARGE)
                .where(SmsRecharge.SMS_RECHARGE.PAY_NO.eq(smsRechargeRecordVo.getPayNo()))
                .fetchAnyInto(SmsRecharge.class);
            if (smsRecharge == null) {
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

    /**
     * 获取短信充值记录
     * @param rechargeParam
     * @return
     */
    public PageResult<SmsRechargeRecordVo> getRechargePage(RechargeParam rechargeParam) {
        SelectJoinStep<? extends Record> select = db().select().from(SMS_RECHARGE);
        buildOptions(select, rechargeParam);
        return super.getPageResult(select, rechargeParam.getPage(),
            rechargeParam.getRows(), SmsRechargeRecordVo.class);
    }

    /**
     * admin短信条件查询
     * @param select 查询SQL
     * @param rechargeParam 查询入参
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, RechargeParam rechargeParam) {
        if (rechargeParam.getStartCreateTime() != null && !"".equals(rechargeParam.getStartCreateTime())) {
            select.where(SMS_RECHARGE.RECHARGE_TIME.gt(Timestamp.valueOf(rechargeParam.getStartCreateTime())));
        }
        if (rechargeParam.getEndCreateTime() != null && !"".equals(rechargeParam.getEndCreateTime())) {
            select.where(SMS_RECHARGE.RECHARGE_TIME.lt(Timestamp.valueOf(rechargeParam.getEndCreateTime())));
        }
        select.orderBy(SMS_RECHARGE.RECHARGE_TIME.desc());
    }

}
