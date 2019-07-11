package com.vpu.mp.service.shop.summary;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.summary.VisitStatisticsParam;
import com.vpu.mp.service.pojo.shop.summary.VisitStatisticsUnit;
import com.vpu.mp.service.pojo.shop.summary.VisitStatisticsVo;
import org.jooq.Record2;
import org.jooq.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpDailyVisit.MP_DAILY_VISIT;
import static com.vpu.mp.service.pojo.shop.summary.VisitStatisticsParam.*;

/**
 * 概况统计
 *
 * @author 郑保乐
 * @date 2019年7月11日
 */
public class SummaryService extends BaseService {

    public void addTestDailyVisit() {
        LocalDate dateToday = LocalDate.now();
        LocalDate i;
        for (int j = 0; j < 100; j++) {
            i = dateToday.minusDays(1);
            String dateString = formatDate(i);
            db().insertInto(MP_DAILY_VISIT, MP_DAILY_VISIT.REF_DATE, MP_DAILY_VISIT.SESSION_CNT, MP_DAILY_VISIT.VISIT_PV,
                    MP_DAILY_VISIT.VISIT_UV, MP_DAILY_VISIT.VISIT_UV_NEW, MP_DAILY_VISIT.STAY_TIME_SESSION, MP_DAILY_VISIT.VISIT_DEPTH)
                    .values(dateString, 10, 20, 30, 40, 128.23, 256.29).execute();
        }
    }

    public VisitStatisticsVo getVisitStatistics(VisitStatisticsParam param) {
        Integer action = param.getAction();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        Integer grading = param.getGrading();
        switch (action) {
            case SESSION_COUNT:
                Result<Record2<String, Integer>> result = getSessionCounts(startDate, endDate);
                List<String> dates = result.getValues(MP_DAILY_VISIT.REF_DATE);
                List<Integer> values = result.getValues(MP_DAILY_VISIT.SESSION_CNT);
                List<VisitStatisticsUnit> visitUnitList = new ArrayList<>();
                for (int i = 0; i < dates.size(); i++) {
                    String date = dates.get(i);
                    Integer value = values.get(i);
                    VisitStatisticsUnit unit = new VisitStatisticsUnit();
                    unit.setRefDate(date);
                    unit.setValue(value.doubleValue());
                    visitUnitList.add(unit);
                }
                return getGroupedValue(visitUnitList, grading);
        }
        return null;
    }

    public VisitStatisticsVo getGroupedValue(List<VisitStatisticsUnit> visitUnits, Integer grading) {
        VisitStatisticsVo vo = new VisitStatisticsVo();
        if (1 == grading) {
            List<String> date = visitUnits.stream().map(VisitStatisticsUnit::getRefDate).collect(Collectors.toList());
            List<Double> list = visitUnits.stream().map(VisitStatisticsUnit::getValue).collect(Collectors.toList());
            vo.setDate(date);
            vo.setList(list);
        }
        return vo;
    }

    public Result<Record2<String, Integer>> getSessionCounts(String startDate, String endDate) {
        return db().select(MP_DAILY_VISIT.REF_DATE, MP_DAILY_VISIT.SESSION_CNT)
                .from(MP_DAILY_VISIT)
                .where(MP_DAILY_VISIT.REF_DATE.between(startDate, endDate)).fetch();
    }

    /**
     * 日期格式化（20190711 形式）
     */
    public String formatDate(LocalDate date) {
        return date.toString().replaceAll("-", "");
    }
}
