package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpDailyVisitRecord;
import com.vpu.mp.service.pojo.shop.summary.RefDateRecord;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitStatisticsParam;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitStatisticsUnit;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitStatisticsVo;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpDailyVisit.MP_DAILY_VISIT;
import static com.vpu.mp.service.pojo.shop.summary.visit.VisitStatisticsParam.*;

/**
 * 折线图
 *
 * @author 郑保乐
 * @date 2019年7月11日
 */
@Service
public class AmountService extends BaseVisitService {
    /** 日期标识符 */
    private static final Integer CUSTOM_DAYS = 0;
    /** 总数默认值 */
    private static final Double TOTAL_NUM = 0.0;
    public void addTestDailyVisit() {
        LocalDate dateToday = LocalDate.now();
        LocalDate i;
        int count = 100;
        for (int j = 0; j < count; j++) {
            i = dateToday.minusDays(1);
            String dateString = formatDate(i);
            db().insertInto(MP_DAILY_VISIT, MP_DAILY_VISIT.REF_DATE, MP_DAILY_VISIT.SESSION_CNT, MP_DAILY_VISIT.VISIT_PV,
                    MP_DAILY_VISIT.VISIT_UV, MP_DAILY_VISIT.VISIT_UV_NEW, MP_DAILY_VISIT.STAY_TIME_SESSION, MP_DAILY_VISIT.VISIT_DEPTH)
                    .values(dateString, 10, 20, 30, 40, 128.23, 256.29).execute();
        }
    }
    /**
     *得到之前的某一天(字符串类型)
     *@param days N天前
     *@return preDay(String)
     */
    public String getDate(Integer days) {
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //获取当前时间
        Calendar c = Calendar.getInstance();
        //计算指定日期
        c.add(Calendar.DATE, - days);
        Date time = c.getTime();
        //返回格式化后的String日期
        return sdf.format(time);
    }
    /**
     * 访问统计折线图
     */
    public VisitStatisticsVo getVisitStatistics(VisitStatisticsParam param) {
        //得到时间
        if (!param.getType().equals(CUSTOM_DAYS)){
            param.setStartDate(getDate(param.getType()));
            param.setEndDate(getDate(NumberUtils.INTEGER_ZERO));
        }
        Integer action = param.getAction();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        Integer grading = param.getGrading();
        Result<MpDailyVisitRecord> result = getSessionCounts(startDate, endDate);
        List<RefDateRecord<Double>> units;
        switch (action) {
            case SESSION_COUNT:
                units = sessionUnits(result);
                break;
            case PV:
                units = pvUnits(result);
                break;
            case UV:
                units = uvUnits(result);
                break;
            case UV_NEW:
                units = uvNewUnits(result);
                break;
            case STAY_TIME_UV:
                units = uvStayUnits(result);
                break;
            case STAY_TIME_SESSION:
                units = sessionStayUnits(result);
                break;
            case VISIT_DEPTH:
                units = visitDepthUnits(result);
                break;
            default:
                throw new IllegalStateException("Unexpected action: " + action);
        }
        VisitStatisticsVo handleData =  getStatisticsVo(units, grading);
        Double nums = handleData.getList().stream().reduce(Double::sum).orElse(TOTAL_NUM);
        VisitStatisticsVo vo = new VisitStatisticsVo();
        vo.setDate(handleData.getDate());
        vo.setList(handleData.getList());
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);
        vo.setTotalNum(nums);
        return vo;
    }

    /**
     * 打开次数
     */
    private List<RefDateRecord<Double>> sessionUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = new VisitStatisticsUnit();
            unit.setRefDate(r.getRefDate());
            unit.setValue(r.getSessionCnt().doubleValue());
            return unit;
        });
    }

    /**
     * 访问次数
     */
    private List<RefDateRecord<Double>> pvUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getVisitPv().doubleValue());
            return unit;
        });
    }

    /**
     * 访问人数
     */
    private List<RefDateRecord<Double>> uvUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getVisitUv().doubleValue());
            return unit;
        });
    }

    /**
     * 新用户人数
     */
    private List<RefDateRecord<Double>> uvNewUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getVisitUvNew().doubleValue());
            return unit;
        });
    }

    /**
     * 人均停留时长
     */
    private List<RefDateRecord<Double>> uvStayUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getStayTimeUv());
            return unit;
        });
    }

    /**
     * 次均停留时长
     */
    private List<RefDateRecord<Double>> sessionStayUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getStayTimeSession());
            return unit;
        });
    }

    /**
     * 平均访问深度
     */
    private List<RefDateRecord<Double>> visitDepthUnits(Result<MpDailyVisitRecord> result) {
        return result.map(r -> {
            VisitStatisticsUnit unit = visitStatisticsUnit(r);
            unit.setValue(r.getVisitDepth());
            return unit;
        });
    }

    /**
     * 创建带日期的处理单元
     */
    private VisitStatisticsUnit visitStatisticsUnit(MpDailyVisitRecord record) {
        VisitStatisticsUnit unit = new VisitStatisticsUnit();
        unit.setRefDate(record.getRefDate());
        return unit;
    }

    /**
     * 按粒度分组
     *
     * @param visitUnits 日单元
     * @param grading    粒度
     */
    private VisitStatisticsVo getStatisticsVo(List<RefDateRecord<Double>> visitUnits, Integer grading) {
        List<RefDateRecord<Double>> groupedValue = getGroupedValue(visitUnits, grading);
        VisitStatisticsVo vo = new VisitStatisticsVo();
        List<String> dates = groupedValue.stream().map(RefDateRecord::getRefDate).collect(Collectors.toList());
        List<Double> values = groupedValue.stream().map(RefDateRecord::getValue).collect(Collectors.toList());
        vo.setDate(dates);
        vo.setList(values);
        return vo;
    }

    /**
     * 统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    private Result<MpDailyVisitRecord> getSessionCounts(
            String startDate, String endDate) {
        return db().select(MP_DAILY_VISIT.REF_DATE, MP_DAILY_VISIT.SESSION_CNT, MP_DAILY_VISIT.VISIT_PV,
                MP_DAILY_VISIT.VISIT_UV, MP_DAILY_VISIT.VISIT_UV_NEW, MP_DAILY_VISIT.STAY_TIME_UV,
                MP_DAILY_VISIT.STAY_TIME_SESSION, MP_DAILY_VISIT.VISIT_DEPTH)
                .from(MP_DAILY_VISIT)
                .where(MP_DAILY_VISIT.REF_DATE.between(startDate).and(endDate)).fetch().into(MP_DAILY_VISIT);
    }

    /**
     * 日期格式化（20190711 形式）
     */
    private String formatDate(LocalDate date) {
        return date.toString().replaceAll("-", "");
    }
}
