package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.summary.RefDateRecord;
import com.vpu.mp.service.pojo.shop.summary.visit.RefDateRecordHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 访问分析通用逻辑
 *
 * @author 郑保乐
 */
@Service
@Scope("prototype")
class BaseVisitService extends BaseService {

    /**
     * 按粒度分组
     *
     * @param records 日单元
     * @param grading 粒度
     */
    @SuppressWarnings("unchecked")
    <T extends Number> List<RefDateRecord<T>> getGroupedValue(List<RefDateRecord<T>> records, Integer grading) {
        /* 默认是时间倒序, 改成正序 */
        Collections.reverse(records);
        List<RefDateRecord<T>> groupedRecords = new LinkedList<>();
        if (1 == grading) {
            /* 按天统计 */
            for (RefDateRecord<T> unit : records) {
                RefDateRecordHolder<T> holder = new RefDateRecordHolder<>();
                holder.setRefDate(unit.getRefDate());
                holder.setValue(unit.getValue());
                groupedRecords.add(holder);
            }
        } else {
            /* 按 n 天统计 */
            String start;
            String end;
            int startI;
            int endI;
            int i = records.size() - 1;
            do {
                if (0 > i) {
                    break;
                }
                /* 一个粒度区间 */
                start = records.get(i).getRefDate();
                endI = i;
                i -= grading;
                if (i < 0) {
                    i = 0;
                }
                end = records.get(i).getRefDate();
                startI = i;
                /* 总和 */
                Double sum = records.subList(startI, endI).stream().mapToDouble(r -> (Double) r.getValue()).sum();
                String dateResult = start + "-" + end;
                RefDateRecordHolder<Double> holder = new RefDateRecordHolder<>();
                holder.setRefDate(dateResult);
                holder.setValue(sum);
                groupedRecords.add((RefDateRecord<T>) holder);
                i--;
            } while (i > 0);
        }
        return groupedRecords;
    }
}
