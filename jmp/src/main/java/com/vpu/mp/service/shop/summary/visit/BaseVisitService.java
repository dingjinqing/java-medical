package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.summary.RefDateRecord;
import com.vpu.mp.service.pojo.shop.summary.RefDateRecordHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 访问分析通用逻辑
 *
 * @author 郑保乐
 */
class BaseVisitService extends BaseService {

    /**
     * 按粒度分组
     *
     * @param records 日单元
     * @param grading    粒度
     */
    @SuppressWarnings("unchecked")
    <T> List<RefDateRecord<T>> getGroupedValue(List<RefDateRecord<T>> records, Integer grading) {
        /* 默认是时间倒序, 改成正序 */
        Collections.reverse(records);
        List<RefDateRecord<T>> groupedRecords = new ArrayList<>();
        if (1 == grading) {
            for (RefDateRecord unit : records) {
                RefDateRecordHolder holder = new RefDateRecordHolder();
                holder.setRefDate(unit.getRefDate());
                holder.setValue(unit.getValue());
                groupedRecords.add(holder);
            }
        } else {
            String start;
            String end;
            int startI;
            int endI;
            int i = records.size() - 1;
            do {
                end = records.get(i).getRefDate();
                endI = i;
                i -= grading;
                if (i < 0) i = 0;
                start = records.get(i).getRefDate();
                startI = i;
                double sum = records.subList(startI, endI).stream().mapToDouble(r -> (Double) r.getValue()).sum();
                String dateResult = start + "-" + end;
                RefDateRecordHolder holder = new RefDateRecordHolder();
                holder.setRefDate(dateResult);
                holder.setValue(sum);
                groupedRecords.add(holder);
                i--;
            } while (i > 0);
        }
        /* 按时间升序排序 */
        Collections.reverse(groupedRecords);
        return groupedRecords;
    }
}
