package com.vpu.mp.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleTask {

	/**
	 * 每一分钟执行一次
	 */
	@Async
	@Scheduled(cron = "0/5 * * * * ?")
	public void taskPerMinute() {
		
		System.out.println("@Scheduled id:"+Thread.currentThread().getId());
	}

}
