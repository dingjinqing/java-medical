package com.vpu.mp.service.saas.schedule.cron;

import com.vpu.mp.db.main.tables.records.CronDefineRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.saas.schedule.cron.CronDefineParam;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Field;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.vpu.mp.db.main.tables.CronDefine.CRON_DEFINE;
import static com.vpu.mp.db.main.tables.CronRecord.CRON_RECORD;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_THREE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author liufei
 * @date 12/19/19
 * 加载db库中的定时任务返回给background使用
 */
@Service
public class MpCronRegistration extends MainBaseService {

    private static Map<String, CronDefineRecord> scheduledCron = new ConcurrentHashMap<>(64);

    @PostConstruct
    private Map<String, CronDefineRecord> initScheduleData() {
        scheduledCron = db().selectFrom(CRON_DEFINE)
            // 获取启用状态的所有定时任务
            .where(CRON_DEFINE.STATUS.eq(BYTE_ONE))
            .fetchMap(CRON_DEFINE.CLASS_NAME);
        return scheduledCron;
    }

    public boolean isAble() {
        return Objects.nonNull(scheduledCron) && CollectionUtils.isNotEmpty(scheduledCron.values());
    }

    public List<CronDefineRecord> findAll() {
        if (isAble()) {
            return new ArrayList<>(scheduledCron.values());
        } else {
            return db().selectFrom(CRON_DEFINE)
                .where(CRON_DEFINE.STATUS.eq(BYTE_ONE))
                .fetch();
        }
    }

    public CronDefineRecord findByCronKey(String key) {
        if (isAble()) {
            CronDefineRecord record = scheduledCron.get(key);
            if (Objects.isNull(record)) {
                record = getCronDefineByKey(key);
                // todo 如果record还是空，说明没有该定时任务
            }
            return record;
        }
        return initScheduleData().get(key);
    }

    public CronDefineRecord getCronDefineByKey(String className) {
        return db().selectFrom(CRON_DEFINE).where(CRON_DEFINE.CLASS_NAME.eq(className)).fetchOneInto(CRON_DEFINE);
    }

    public CronDefineRecord getCronDefineById(int cronId) {
        return db().selectFrom(CRON_DEFINE).where(CRON_DEFINE.ID.eq(cronId)).fetchOneInto(CRON_DEFINE);
    }

    public void insertCronDefine(CronDefineParam param) {
        CronDefineRecord record = new CronDefineRecord();
        FieldsUtil.assignNotNull(param, record);
        db().executeInsert(record);
    }

    public void deleteCronDefine(int cronId) {
        db().delete(CRON_DEFINE).where(CRON_DEFINE.ID.eq(cronId));
    }

    public void truncateCronDefine() {
        db().truncate(CRON_DEFINE).restartIdentity();
    }

    public void updateCronDefine(CronDefineParam param) {
        CronDefineRecord record = new CronDefineRecord();
        FieldsUtil.assignNotNull(param, record);
        db().executeUpdate(record);
    }

    public void failedRecord(int cronId, byte executeNum, String failedReason) {
        db().insertInto(CRON_RECORD, CRON_RECORD.CRON_ID, CRON_RECORD.EXECUTE_NUM, CRON_RECORD.FAILED_REASON)
            .values(cronId, executeNum, failedReason)
            .onDuplicateKeyUpdate()
            .set(CRON_RECORD.EXECUTE_NUM, CRON_RECORD.EXECUTE_NUM.add(BYTE_ONE))
            .set(CRON_RECORD.FAILED_REASON, failedReason)
            .execute();
        // 记录异常后，将该定时任务更新为停用状态，执行失败/异常
        updateCronDefine(CronDefineParam.builder().id(cronId).status(BYTE_ZERO).result(CONDITION_THREE).build());
    }

    public <T> T singleFieldFromRecord(int cronId, Field<T> field) {
        return db().select(field).from(CRON_RECORD).where(CRON_RECORD.CRON_ID.eq(cronId)).fetchOne(field);
    }
}
