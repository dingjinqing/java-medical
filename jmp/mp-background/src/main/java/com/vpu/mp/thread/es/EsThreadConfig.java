package com.vpu.mp.thread.es;

import com.github.fonimus.ssh.shell.SshShellHelper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.vpu.mp.service.foundation.jedis.data.DBOperating;
import com.vpu.mp.service.saas.SaasApplication;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * es thread config
 * @author 卢光耀0
 * @date 2019/10/22 10:16 上午
 *
*/
@Component
@Slf4j
public class EsThreadConfig {


    private static final int SIZE = 400;

    @Autowired
    private SaasApplication saas;

//    @Async("esAsyncExecutor")
//    public void doIndexByGoodId(Integer shopId){
//        //TODO 商品暂不实行多线程
//    }

    @Async("esAsyncExecutor")
    public void doIndexByShopId(Integer shopId) {
        List<Integer> list;
        try{
           //some db maybe don't exist
           list  = saas.getShopApp(shopId).goods.getAllGoodsId();
        }catch (Exception e){
            e.printStackTrace();
            return ;
        }


//        List<Integer> list = new ArrayList<>();
//        for (int a = 0; a < 125000; a++) {
//            list.addAll(list1);
//        }
        int allSize = list.size();
        int count = allSize/SIZE + 1;
        int nextNumber = 0;
        log.info("\n当前店铺【{}】,预计分【{}】批执行",shopId,count);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {
            int endNumber = nextNumber+SIZE;
            if( allSize < endNumber){
                endNumber = allSize;
                i = count;
            }
            log.info("\n开始执行第【{}】批，【{}】-【{}】条",i,nextNumber,endNumber);
            List<Integer> needIds = list.subList(nextNumber,endNumber);
            if( needIds.isEmpty() ){
                break;
            }
            nextNumber = endNumber;
            log.info("listSize【{}】",list.size());
            saas.getShopApp(shopId).esGoodsCreateService.batchCreateEsGoodsIndex(needIds,shopId);
            log.info("\n第【{}】批建立成功",i);
        }
        log.info("\n店铺【{}】索引建立完成，共耗时{}ms",shopId,stopwatch.elapsed(TimeUnit.MILLISECONDS));

    }
    @Async("esAsyncExecutor")
    public void doLabelIndexByShopId(Integer shopId) {
        List<Integer> list ;
        try{
            //some db maybe don't exist
            list  = saas.getShopApp(shopId).goods.getAllGoodsId();
        }catch (Exception e){
            e.printStackTrace();
            return ;
        }
//        List<Integer> list = new ArrayList<>();
//        for (int a = 0; a < 125000; a++) {
//            list.addAll(list1);
//        }
        int allSize = list.size();
        int count = allSize/SIZE + 1;
        int nextNumber = 0;
        log.info("\n当前店铺【{}】,预计分【{}】批执行",shopId,count);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {
            int endNumber = nextNumber+SIZE;
            if( allSize < endNumber){
                endNumber = allSize;
                i = count;
            }
            log.info("\n开始执行第【{}】批，【{}】-【{}】条",i,nextNumber,endNumber);
            List<Integer> needIds = list.subList(nextNumber,endNumber);
            if( needIds.isEmpty() ){
                break;
            }
            nextNumber = endNumber;
            log.info("listSize【{}】",list.size());
            saas.getShopApp(shopId).esGoodsLabelCreateService.createEsLabelIndexForGoodsId(needIds, DBOperating.UPDATE);
            log.info("\n第【{}】批建立成功",i);
        }
        log.info("\n店铺【{}】索引建立完成，共耗时{}ms",shopId,stopwatch.elapsed(TimeUnit.MILLISECONDS));

    }
}

