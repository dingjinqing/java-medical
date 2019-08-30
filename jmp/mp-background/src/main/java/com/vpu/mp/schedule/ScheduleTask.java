package com.vpu.mp.schedule;

import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import org.jooq.Condition;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持多线程，异步执行
 * 启动参数加上 -Dschedule.switch=on ，保证只有一台机器启动定时处理
 * 
 * @author 新国
 *
 */
@Component

@EnableAsync
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class ScheduleTask {

    @Autowired
    private  SaasApplication saas;


	/**
	 * 每一分钟执行一次
	 */
	@Async
	@Scheduled(cron = "0/5 * * * * ?")
	public void taskSendMessage() {
        saas.taskJobMainService.getAndSendMessage(TaskJobsConstant.TaskJobEnum.SEND_MESSAGE);
    }
	/**
	 * 每天获取微信数据（每天6-12点每半个小时执行一次）
	 */
	@Scheduled(cron = "0 0/30 6,7,8,9,10,11,12 * * ?")
	public void taskDailyWechat(){
		System.out.println("定时任务开始执行");
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginDailyTask();});
	}
	/**
	 * 每周获取微信数据（每周一6-12点每半个小时执行一次）
	 */
	@Scheduled(cron = "0 0,30 6,7,8,9,10,11,12 * * ?")
	public void taskWeeklyWechat(){
		System.out.println("定时任务开始执行");
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginWeeklyTask();});
	}
	/**
	 * 每月获取微信数据（每月1号6-12点每半个小时执行一次）
	 */
//	@Scheduled(cron = "0 0,30 6,7,8,9,10,11,12 0 1/1 *")
	@Scheduled(cron = "0 */1 * * * ?")
	public void taskMonthklyWechat(){
		System.out.println("定时任务开始执行");
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r)->{saas.getShopApp(r.getShopId()).
				shopTaskService.wechatTaskService.beginMonthlyTask();});
	}

}
