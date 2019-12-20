package com.vpu.mp.schedule;

import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.task.wechat.MaMpScheduleTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * 小程序公众号相关的
 * @author zhaojianqiang
 * @time   上午9:36:05
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class MaMpScheduleTask {

    @Autowired
    private  SaasApplication saas;
	/**
     * 卡券到期提醒，每天11：30
	 */
	@Scheduled(cron = "0 30 11 * * ?")
    public void monitorSeckillGoods() {
		log.info("卡券到期提醒");
        Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r) -> {
			saas.getShopApp(r.getShopId()).shopTaskService.maMpScheduleTaskService.expiringCouponNotify();
		});
    }
	
	/**
	 * 预约服务提前一小时提醒,每分钟
	 */
	@Scheduled(cron = "0 30 11 * * ?")
	public void AppointmentRemindCommand() {
		log.info("预约服务提前一小时提醒");
        Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r) -> {
			saas.getShopApp(r.getShopId()).shopTaskService.maMpScheduleTaskService.sendAppointmentRemind();
		});
	}
	
	
	/**
	 * 商家自定义模板消息 定时发送, 尾款未支付前N小时提醒,每分钟
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void sendTemplateMessage() {
		log.info("商家自定义模板消息 定时发送");
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r) -> {
			saas.getShopApp(r.getShopId()).shopTaskService.maMpScheduleTaskService.sendTemplateMessage();
		});
	}
	
	
	
	/**
	 * 好友助力 发送模板消息，修改助力状态,每分钟
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void friendPromoteCommand() {
		log.info("商家自定义模板消息 定时发送");
		Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r) -> {
			saas.getShopApp(r.getShopId()).shopTaskService.maMpScheduleTaskService.friendPromoteCommand();
		});
	}
}
