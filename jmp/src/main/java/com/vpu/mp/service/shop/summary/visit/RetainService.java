package com.vpu.mp.service.shop.summary.visit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MpDailyRetainRecord;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.summary.*;
import org.jooq.Result;
import org.jooq.TableField;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.vpu.mp.db.shop.tables.MpDailyRetain.MP_DAILY_RETAIN;
import static com.vpu.mp.service.pojo.shop.summary.AccessRetainVo.ACTION_NEW_RETAIN;
import static com.vpu.mp.service.pojo.shop.summary.AccessRetainVo.ACTION_RETAIN;

/**
 * 留存统计
 *
 * @author 郑保乐
 */
public class RetainService extends BaseVisitService {

    public AccessRetainVo getAccessRetain(VisitStatisticsParam param) {
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        Integer action = param.getAction();
        Result<MpDailyRetainRecord> retainResult;
        AccessRetainVo vo = new AccessRetainVo();
        List<AccessRetain> retains = new LinkedList<>();
        switch (action) {
            case ACTION_NEW_RETAIN:
                retainResult = getNewRetainResult(startDate, endDate);
                filter(retainResult, retains, MP_DAILY_RETAIN.VISIT_UV_NEW);
                break;
            case ACTION_RETAIN:
                retainResult = getRetainResult(startDate, endDate);
                filter(retainResult, retains, MP_DAILY_RETAIN.VISIT_UV);
                break;
        }
        vo.setData(retains);
        return vo;
    }

    private void filter(
            Result<MpDailyRetainRecord> retainResult,
            List<AccessRetain> retains, TableField<MpDailyRetainRecord, String> field) {
        retainResult.forEach(r -> {
            /* one day */
            String refDate = r.get(MP_DAILY_RETAIN.REF_DATE);
            String itemsString = r.get(field);
            List<RetainItem> items = Util.parseJson(itemsString, new TypeReference<List<RetainItem>>() {});
            AccessRetain day = new AccessRetain();
            day.setRefDate(refDate);
            day.setList(Objects.requireNonNull(items));
            retains.add(day);
        });
    }

    /**
     * 新增留存
     */
    private Result<MpDailyRetainRecord> getNewRetainResult(String startDate, String endDate) {
        return getResult(startDate, endDate, MP_DAILY_RETAIN.VISIT_UV_NEW);
    }

    /**
     * 活跃留存
     */
    private Result<MpDailyRetainRecord> getRetainResult(String startDate, String endDate) {
        return getResult(startDate, endDate, MP_DAILY_RETAIN.VISIT_UV);
    }

    /**
     * 查询
     */
    private Result<MpDailyRetainRecord> getResult(
            String startDate, String endDate, TableField<MpDailyRetainRecord, String> field) {
        return db().select(MP_DAILY_RETAIN.REF_DATE, field)
                .from(MP_DAILY_RETAIN)
                .where(MP_DAILY_RETAIN.REF_DATE.between(startDate).and(endDate))
                .fetch().into(MP_DAILY_RETAIN);
    }
}
