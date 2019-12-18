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
        result.forEach((r)->{saas.getShopApp(r.getShopId()).coupon.expiringCouponNotify(r.getShopId());});
    }
}
