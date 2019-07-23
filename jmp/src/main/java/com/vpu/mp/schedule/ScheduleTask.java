package com.vpu.mp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
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
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class ScheduleTask {

	@Autowired
	protected SaasApplication saas;
	
	/**
	 * 每一分钟执行一次
	 */
	@Async
	@Scheduled(cron = "0/1 * * * * ?")
	public void taskPerMinute() {
		// TODO: 加入每分钟执行的任务
		 saas.article.getArticleIdRows(1);
		 saas.getShopApp(471752).image.getAllSize();
		 saas.article.getArticleIdRows(1);
		 saas.getShopApp(471752).image.getAllSize();
		System.out.println("@Scheduled id:"+Thread.currentThread().getId());
	}

	// TODO:加入其它定时器
}
