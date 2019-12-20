package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.saas.SaasApplication;

/**
 * 支持多线程，异步执行
 * 启动参数加上 -Dschedule.switch=on ，保证只有一台机器启动定时处理
 * 
 * @author 新国
 *
 */
@Component
@EnableScheduling
@EnableAsync

public class ScheduleTask {

    @Autowired
    private  SaasApplication saas;

    /**
     * TaskJob数据导出
     * 每天2点执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void exportTaskJob(){
        saas.dataExportService.exportData();
    }

	/**
	 * 每一分钟执行一次
	 */
	@Scheduled(cron = "5 * * * * ?")
	public void taskSendMessage() {
        saas.taskJobMainService.getAndSendMessage();
    }
	/**
	 * 每天获取微信数据（每天6-12点每半个小时执行一次）
	 */
	@Scheduled(cron = "0 0/30 6,7,8,9,10,11,12 * * ?")
	public void taskDailyWechat(){
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginDailyTask();});
	}
	/**
	 * 每周获取微信数据（每周一6-12点每半个小时执行一次）
	 */
	@Scheduled(cron = "0 0,30 6,7,8,9,10,11,12 * * ?")
	public void taskWeeklyWechat(){
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginWeeklyTask();});
	}
	/**
	 * 每月获取微信数据（每月1号6-12点每半个小时执行一次）
	 */
	@Scheduled(cron = "0 0/30 6-12 1 * ?")
	public void taskMonthklyWechat(){
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginMonthlyTask();});
	}

}
