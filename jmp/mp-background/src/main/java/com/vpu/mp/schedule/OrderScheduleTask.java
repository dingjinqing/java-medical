package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.saas.SaasApplication;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单处理定时任务
 *
 */
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix="schedule",name = "switch", havingValue = "on")
public class OrderScheduleTask {
    @Autowired
    private SaasApplication saas;
    @Autowired
    private JedisManager jedisManager;


    /**
     * 拼团订单处理（自动成团、退款等）
     * 每一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void monitorGroupBuyOrders() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r)->{
            String uuid = Util.randomId();
            String key = JedisKeyConstant.TASK_JOB_LOCK_ORDER_GROUP_BUY + r.getShopId();
            //订单处理时间可能较长，加锁防止重入
            if( jedisManager.addLock(key,uuid,1000*90) ){
                saas.getShopApp(r.getShopId()).shopTaskService.groupBuyTaskService.monitorOrder();
                jedisManager.releaseLock(key,uuid);
            }
        });
    }

    /**
     * 预售订单自动关闭
     * 每一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void monitorPreSaleOrders() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r)->{
                saas.getShopApp(r.getShopId()).shopTaskService.preSaleTaskService.monitorOrder();
        });
    }

    /**
     * 订单关闭
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void close(){
        Result<ShopRecord> shops = saas.shop.getAll();
        shops.forEach((shop)->{
            saas.getShopApp(shop.getShopId()).shopTaskService.orderTaskService.close();
        });
    }

    /**
     * 收货
     * 每天00：05执行
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void receive(){
        Result<ShopRecord> shops = saas.shop.getAll();
        shops.forEach((shop)->{
            saas.getShopApp(shop.getShopId()).shopTaskService.orderTaskService.receive();
        });
    }

    /**
     * 完成订单
     * 每十分钟一次
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void finish(){
        Result<ShopRecord> shops = saas.shop.getAll();
        shops.forEach((shop)->{
            saas.getShopApp(shop.getShopId()).shopTaskService.orderTaskService.finish();
        });
    }

    /**
     * 订单未支付通知
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void expiringNoPayOrderNotify(){
        Result<ShopRecord> shops = saas.shop.getAll();
        shops.forEach((shop)->{
            saas.getShopApp(shop.getShopId()).shopTaskService.orderTaskService.expiringNoPayOrderNotify();
        });
    }

    /**
     * 自动处理退款退货
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void autoCloseReturnOrder(){
        Result<ShopRecord> shops = saas.shop.getAll();
        shops.forEach((shop)->{
            saas.getShopApp(shop.getShopId()).shopTaskService.orderTaskService.autoReturnOrder();
        });
    }
}
