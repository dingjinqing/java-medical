package com.vpu.mp.dao.shop.sms;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SmsSendRecordRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminVo;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordParam;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.DOCTOR;
import static com.vpu.mp.db.shop.Tables.SMS_SEND_RECORD;

/**
 * @author 孔德成
 * @date 2020/7/24 9:22
 */
@Repository
public class SmsSendRecordDao extends ShopBaseDao {


    /**
     * 保存短信发送记录
     * @param param
     * @return
     */
    public int save(SmsSendRecordParam param){
        SmsSendRecordRecord record = db().newRecord(SMS_SEND_RECORD, param);
       return record.insert();
    }

    /**
     * @author 赵晓东
     * @create 2020-07-27 11:55:27
     * @description admin端查询短信发送情况
     */
    /**
     * admin端查询短信情况
     * @param smsSendRecordAdminParam admin端短信查询入参
     * @return PageResult<SmsSendRecordAdminVo>
     */
    public PageResult<SmsSendRecordAdminVo> selectSmsSendRecordAdmin(SmsSendRecordAdminParam smsSendRecordAdminParam) {
        SelectJoinStep<? extends Record> select = db().select().from(SMS_SEND_RECORD);
        buildOptions(select, smsSendRecordAdminParam);
        return super.getPageResult(select, smsSendRecordAdminParam.getCurrentPage(),
            smsSendRecordAdminParam.getPageRows(), SmsSendRecordAdminVo.class);
    }

    /**
     * admin短信条件查询
     * @param select 查询SQL
     * @param smsSendRecordAdminParam 查询入参
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, SmsSendRecordAdminParam smsSendRecordAdminParam) {
        if (smsSendRecordAdminParam.getAccountName() != null) {
            select.where(SMS_SEND_RECORD.ACCOUNT_NAME.like(smsSendRecordAdminParam.getAccountName()));
        }
        if (smsSendRecordAdminParam.getUserId() != null) {
            select.where(SMS_SEND_RECORD.USER_ID.eq(smsSendRecordAdminParam.getUserId()));
        }
        if (smsSendRecordAdminParam.getResponseCode() != null) {
            select.where(SMS_SEND_RECORD.RESPONSE_CODE.like(smsSendRecordAdminParam.getResponseCode()));
        }
        if (smsSendRecordAdminParam.getExt() != null) {
            select.where(SMS_SEND_RECORD.EXT.like(smsSendRecordAdminParam.getExt()));
        }
        if (smsSendRecordAdminParam.getStartCreateTime() != null) {
            select.where(SMS_SEND_RECORD.CREATE_TIME.gt(smsSendRecordAdminParam.getStartCreateTime()));
        }
        if (smsSendRecordAdminParam.getEndCreateTime() != null) {
            select.where(SMS_SEND_RECORD.CREATE_TIME.lt(smsSendRecordAdminParam.getEndCreateTime()));
        }
        if (smsSendRecordAdminParam.getResponseMsgCode() != null) {
            select.where(SMS_SEND_RECORD.RESPONSE_MSG_CODE.eq(smsSendRecordAdminParam.getResponseMsgCode()));
        }
    }

}
