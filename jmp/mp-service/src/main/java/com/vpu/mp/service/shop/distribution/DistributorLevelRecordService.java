package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.DistributorLevelRecord;
import com.vpu.mp.db.shop.tables.records.DistributorLevelRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.distribution.UpdateUserLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分销等级变换记录
 * @author 王帅
 */
@Service
public class DistributorLevelRecordService extends ShopBaseService {

    DistributorLevelRecord TABLE = DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD;

    public void add(Collection<UpdateUserLevel> params) {
        ArrayList<DistributorLevelRecordRecord> records = new ArrayList<>(params.size());
        for (UpdateUserLevel param: params) {
            DistributorLevelRecordRecord record = db().newRecord(TABLE, param);
            records.add(record);
        }
        db().batchInsert(records).execute();

    }
}
