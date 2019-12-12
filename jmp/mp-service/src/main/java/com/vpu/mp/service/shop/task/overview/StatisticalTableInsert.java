package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.db.shop.tables.records.TradesRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductOverviewParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

/**
 * @author liufei
 * @date 12/12/19
 */
public class StatisticalTableInsert extends ShopBaseService {
    @Autowired
    UserSummaryTaskService userSummary;

    @Autowired
    GoodsStatisticTaskService goodsStatistic;

    // b2c_trades表，每小时（整点）统计一次数据插入一条记录（统计历史数据，昨天数据）
    public void insertTrades(boolean flag) {
        LocalDateTime yesterdayEndTime = LocalDate.now().atStartOfDay();
        LocalDateTime yesterdayStartTime = LocalDate.now().minusDays(INTEGER_ONE).atStartOfDay();
        byte hour = BYTE_ZERO;
        ProductOverviewParam param = new ProductOverviewParam();
        Timestamp start;
        Timestamp end;
        for (LocalDateTime i = yesterdayStartTime; i.isBefore(yesterdayEndTime); i = i.plusHours(INTEGER_ONE)) {
            start = Timestamp.valueOf(i);
            end = Timestamp.valueOf(i.plusHours(INTEGER_ONE));
            TradesRecord record = new TradesRecord();
            record.setUv(userSummary.getUv(start, end));
            record.setPv(userSummary.getPv(start, end));
            record.setPayUserNum(goodsStatistic.paidUv(createParam(param, start, end)));
            record.setPayOrderMoney(goodsStatistic.orderUserMoney(start, end));
            record.setPayOrderNum(userSummary.orderNum(start, end));
            record.setPct(BigDecimalUtil.divideWithOutCheck(record.getPayOrderMoney(), record.getPayUserNum()));
            record.setUvPayRatio(BigDecimalUtil.divideWithOutCheck(record.getPayUserNum(), record.getUv()));
            record.setHour(hour++);
            record.setRefDate(Date.valueOf(LocalDate.now().minusDays(INTEGER_ONE)));
            db().executeInsert(record);
        }
    }

    private ProductOverviewParam createParam(ProductOverviewParam param, Timestamp start, Timestamp end) {
        param.clear();
        param.setStartTime(start);
        param.setEndTime(end);
        return param;
    }

    // b2c_trades表，实时统计当天的数据
    public void insertTrades() {
    }

    // b2c_user_summary_trend
    public void insertUserSummaryTrend() {
    }
}
