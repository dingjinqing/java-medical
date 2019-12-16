package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liufei
 * @date 11/18/19
 * 概况模块-统计信息定时任务
 */
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix = "schedule", name = "switch", havingValue = "on")
public class StatisticsScheduleTask {

    @Autowired
    private SaasApplication saas;

    /**
     * 商品统计
     * b2c_goods_overview_summary 商品概况信息表
     * b2c_goods_summary 商品效果信息表
     * 每天凌晨零点过后8秒开始统计前一天的数据
     */
    @Scheduled(cron = "8 * 0 * * ?")
    public void goodsStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.goodsStatisticTaskService.insertOverview();
            shop.shopTaskService.goodsStatisticTaskService.insertGoodsSummary();
        });
    }

    /**
     * User statistics.
     * 各个统计定时任务相隔一分钟 执行，避免同时执行大量统计任务，占用资源
     */
    @Scheduled(cron = "8 1 0 * * ?")
    public void userStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.statisticalTableInsert.insertTrades();
            shop.shopTaskService.statisticalTableInsert.insertUserSummaryTrend();
        });
    }

    /**
     * RFM model statistics.
     */
    @Scheduled(cron = "8 2 0 * * ?")
    public void rfmStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.statisticalTableInsert.insertUserRfmSummary();
        });
    }

    /**
     * 标签成交分析统计数据
     */
    @Scheduled(cron = "8 3 0 * * ?")
    public void userLabelStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.statisticalTableInsert.insertDistributionTag();
        });
    }

    /**
     * 交易统计数据
     */
    @Scheduled(cron = "8 4 0 * * ?")
    public void tradeStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.statisticalTableInsert.insertTradesRecordSummary();
        });
    }

    /**
     * Real time statistics.
     * 每小时实时统计过去一个小时内产生的数据
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void realTimeStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.statisticalTableInsert.insertTradesNow();
        });
    }
}
