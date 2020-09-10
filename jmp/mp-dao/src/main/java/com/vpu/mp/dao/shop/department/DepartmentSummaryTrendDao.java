package com.vpu.mp.dao.shop.department;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.DepartmentSummaryTrendDo;
import com.vpu.mp.common.pojo.shop.table.StoreOrderSummaryTrendDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DepartmentSummaryTrendRecord;
import com.vpu.mp.db.shop.tables.records.StoreOrderSummaryTrendRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentStatisticParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.*;
/**
 * @author chenjie
 * @date 2020年09月09日
 */
@Repository
public class DepartmentSummaryTrendDao extends ShopBaseDao {
    /**
     * 添加记录
     *
     * @param param
     * @return
     */
    public void insertDepartmentStatistic(DepartmentSummaryTrendDo param) {
        DepartmentSummaryTrendRecord record = db().newRecord(DEPARTMENT_SUMMARY_TREND);
        FieldsUtil.assign(param, record);
        record.insert();
    }

    /**
     * 更新记录
     *
     * @param param
     * @return
     */
    public void updateDepartmentStatistic(DepartmentSummaryTrendDo param) {
        DepartmentSummaryTrendRecord record = db().newRecord(DEPARTMENT_SUMMARY_TREND);
        FieldsUtil.assign(param, record);
        record.update();
    }

    /**
     * 查询记录
     *
     * @param param
     * @return
     */
    public DepartmentSummaryTrendDo getDepartmentStatistic(DepartmentStatisticParam param) {
        return db().selectFrom(DEPARTMENT_SUMMARY_TREND)
            .where(DEPARTMENT_SUMMARY_TREND.DEPARTMENT_ID.eq(param.getDepartmentId()))
            .and(STORE_ORDER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .and(STORE_ORDER_SUMMARY_TREND.REF_DATE.eq(param.getRefDate()))
            .fetchAnyInto(DepartmentSummaryTrendDo.class);
    }
}
