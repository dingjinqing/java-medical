package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 商品状态变化监控定时任务
 *
 */
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class GoodsScheduleTask {

    @Autowired
    private  SaasApplication saas;


	/**
     * 监控秒杀，更新商品类型
	 * 每一分钟执行一次
	 */
//	@Scheduled(cron = "0 */1 * * * ?")
//    public void monitorSeckillGoods() {
//        Result<ShopRecord> result = saas.shop.getAll();
//        result.forEach((r)->{saas.getShopApp(r.getShopId()).
//            shopTaskService.seckillTaskService.monitorGoodsType();});
//    }

}
