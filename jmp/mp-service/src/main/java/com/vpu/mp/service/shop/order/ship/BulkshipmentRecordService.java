package com.vpu.mp.service.shop.order.ship;

import com.vpu.mp.db.main.tables.ShopAccount;
import com.vpu.mp.db.main.tables.ShopChildAccount;
import com.vpu.mp.db.shop.tables.BulkshipmentRecord;
import com.vpu.mp.db.shop.tables.records.BulkshipmentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch.BatchShipListParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch.BatchShipListVo;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

/**
 * table = BULKSHIPMENT_RECORD
 * @author 王帅
 */
@Service
public class BulkshipmentRecordService extends ShopBaseService {
    public final BulkshipmentRecord TABLE = BulkshipmentRecord.BULKSHIPMENT_RECORD;
    public final ShopAccount MAIN_TABLE_ACCOUNT = ShopAccount.SHOP_ACCOUNT;
    public final ShopChildAccount MAIN_TABLE_CHILD_ACCOUNT= ShopChildAccount.SHOP_CHILD_ACCOUNT;
    public BulkshipmentRecordRecord addRecord(int size, AdminTokenAuthInfo adminInfo) {
        BulkshipmentRecordRecord record = db().newRecord(TABLE);
        record.setSysId(adminInfo.getSysId());
        record.setAccountId(adminInfo.getSubAccountId());
        record.setTotalNum(size);
        record.insert();
        return record;
    }

    public PageResult<BatchShipListVo> batchShipList(BatchShipListParam param) {
        SelectOnConditionStep<? extends Record> select = db().select(TABLE.ID, TABLE.SYS_ID, TABLE.ACCOUNT_ID, TABLE.TOTAL_NUM, TABLE.SUCCESS_NUM, TABLE.CREATE_TIME
            ,MAIN_TABLE_ACCOUNT.USER_NAME, MAIN_TABLE_ACCOUNT.MOBILE
            ,MAIN_TABLE_CHILD_ACCOUNT.ACCOUNT_NAME.as("child_user_name"), MAIN_TABLE_CHILD_ACCOUNT.MOBILE.as("child_mobile"))
            .from(TABLE)
            .leftJoin(MAIN_TABLE_ACCOUNT).on(MAIN_TABLE_ACCOUNT.SYS_ID.eq(TABLE.SYS_ID))
            .leftJoin(MAIN_TABLE_CHILD_ACCOUNT).on(MAIN_TABLE_CHILD_ACCOUNT.ACCOUNT_ID.eq(TABLE.ACCOUNT_ID));
        if(param.getBatchId() != null) {
            select.where(TABLE.ID.eq(param.getBatchId()));
        }
        if(param.getStart() != null) {
            select.where(TABLE.CREATE_TIME.ge(param.getStart()));
        }
        if(param.getEnd() != null) {
            select.where(TABLE.CREATE_TIME.le(param.getEnd()));
        }
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), BatchShipListVo.class);
    }
}
