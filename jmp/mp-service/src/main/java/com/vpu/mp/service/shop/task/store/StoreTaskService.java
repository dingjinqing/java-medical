package com.vpu.mp.service.shop.task.store;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import com.vpu.mp.service.shop.store.statistic.StoreOrderSummaryTrendService;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.TYPE_LIST_1;

/**
 * @author chenjie
 * @date 2020年08月27日
 */
@Service
public class StoreTaskService  extends ShopBaseService {
    @Autowired
    public StoreOrderSummaryTrendService storeOrderSummaryTrendService;
    public void insertStoreStatistic(Integer storeId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        TYPE_LIST_1.forEach((e) -> {
            StatisticParam statisticParam = new StatisticParam();
            statisticParam.setStoreId(storeId);
            statisticParam.setStartTime(Timestamp.valueOf(today.minusDays(e+1)));
            statisticParam.setEndTime(Timestamp.valueOf(today.minusDays(1)));
            statisticParam.setType(e);
            statisticParam.setRefDate(Date.valueOf(today.minusDays(1).toLocalDate()));
            storeOrderSummaryTrendService.statisticStore(statisticParam);
        });
    }
}
