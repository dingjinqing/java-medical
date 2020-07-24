package com.vpu.mp.dao.shop.sms;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SmsSendRecordRecord;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordParam;
import org.springframework.stereotype.Repository;

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

}
