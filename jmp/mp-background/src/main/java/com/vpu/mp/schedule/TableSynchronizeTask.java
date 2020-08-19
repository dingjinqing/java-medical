package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 表同步
 * @author zhaojianqiang
 * @time   下午2:13:25
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class TableSynchronizeTask {
    @Autowired
    private  SaasApplication saas;
	/**
	 * user表同步到主库，半夜三点执行
	 */
    @Scheduled(cron = "0 0 0,3 * * ?")
	public void userSynchronize() {
		log.info("同步user表");
        Result<ShopRecord> result = saas.shop.getAll();
		result.forEach((r) -> {
			saas.getShopApp(r.getShopId()).shopTaskService.tableTaskService.userSys();
		});
	}
    /**
     * order表同步到主库，半夜三点执行
     * //
     */
    @Scheduled(cron = "0 0 0,3 * * ?")
    public void orderSynchronize() {
        log.info("【同步任务】---订单数据同步到主库");
        Result<ShopRecord> result = saas.shop.getAll();
        //同步最近一天的订单
        result.parallelStream().forEach(shopRecord -> {
            saas.getShopApp(shopRecord.getShopId()).shopTaskService.tableTaskService.orderDeltaUpdates(shopRecord.getShopId());
        });
    }

    /**
     * 问诊订单同步主库
     *
     */
    @Scheduled(cron = "0 0 0,3 * * ?")
    public void inquiryOrderSynchronize(){
        Result<ShopRecord> shopRecords =saas.shop.getAll();
        shopRecords.forEach(shopRecord -> {
            saas.getShopApp(shopRecord.getShopId()).shopTaskService.tableTaskService.inquiryOrderSynchronize(shopRecord.getShopId());
        });
    }

}
