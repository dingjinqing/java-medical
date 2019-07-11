package com.vpu.mp.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 支持多线程，异步执行
 * 
 * @author 新国
 *
 */
@Component
@EnableScheduling
@EnableAsync
public class ScheduleTask {

	/**
	 * 每一分钟执行一次
	 */
	@Async
	@Scheduled(cron = "0 0/1 * * * ?")
	public void taskPerMinute() {
		// TODO: 加入每分钟执行的任务
	}

	// TODO:加入其它定时器
}
