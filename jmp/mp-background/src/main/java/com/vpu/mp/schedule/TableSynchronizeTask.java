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

import java.util.List;

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
     * //    @Scheduled(cron = "0 0 0,3 * * ?")
     */
    public void orderSynchronize() {
        log.info("【同步任务】---订单数据同步到主库");
        Result<ShopRecord> result = saas.shop.getAll();
        //把前一天新增的订单导入到main库中
        for (ShopRecord r : result) {
            List<String> orderSns = saas.orderService.getNotClosedOrderIds(r.getShopId());
            if ( !orderSns.isEmpty() ){
                saas.getShopApp(r.getShopId()).shopTaskService.tableTaskService.oldOrderSynchronize(orderSns);
            }
            saas.getShopApp(r.getShopId()).shopTaskService.tableTaskService.orderSynchronize();
        }
        //更新订单状态不是已结束状态（order_status=1、2、8、10）订单的信息（其中已完成order_status=6的订单单独更新）


    }

    /**
     * 问诊订单同步主库
     *
     */
    @Scheduled(cron = "0 0 0,3 * * ?")
    public void inquiryOrderSynchronize(){
        log.info("【同步任务】---问诊订单数据同步到主库start");
        Result<ShopRecord> shopRecords =saas.shop.getAll();
        shopRecords.forEach(shopRecord -> {
            saas.getShopApp(shopRecord.getShopId()).shopTaskService.tableTaskService.inquiryOrderSynchronize(shopRecord.getShopId());
        });
    }

}
