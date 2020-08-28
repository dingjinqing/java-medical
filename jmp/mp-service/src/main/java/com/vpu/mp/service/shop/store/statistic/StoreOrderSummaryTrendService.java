package com.vpu.mp.service.shop.store.statistic;

import com.vpu.mp.common.pojo.shop.table.StoreOrderSummaryTrendDo;
import com.vpu.mp.dao.shop.store.StoreOrderSummaryTrendDao;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticAddVo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticPayVo;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chenjie
 * @date 2020年08月27日
 */
@Service
public class StoreOrderSummaryTrendService {
    @Autowired
    protected StoreOrderSummaryTrendDao storeOrderSummaryTrendDao;

    @Autowired
    public OrderReadService orderReadService;
    /**
     * 添加记录
     *
     * @param param
     * @return
     */
    public void insertStoreStatistic(StoreOrderSummaryTrendDo param) {
        storeOrderSummaryTrendDao.insertStoreStatistic(param);
    }

    /**
     * 更新记录
     *
     * @param param
     * @return
     */
    public void updateStoreStatistic(StoreOrderSummaryTrendDo param) {
        storeOrderSummaryTrendDao.updateStoreStatistic(param);
    }

    /**
     * 查询记录
     *
     * @param param
     * @return
     */
    public StoreOrderSummaryTrendDo getStoreStatistic(StatisticParam param) {
        return storeOrderSummaryTrendDao.getStoreStatistic(param);
    }

    /**
     * 统计门店订单数据
     * @param param
     */
    public void statisticStore(StatisticParam param) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        StoreOrderSummaryTrendDo storeOrderSummaryTrendDo = new StoreOrderSummaryTrendDo();
        StatisticPayVo payData = orderReadService.getStoreOrderPayData(param);
        StatisticAddVo addData = orderReadService.getStoreOrderAddData(param);
        storeOrderSummaryTrendDo.setRefDate(param.getRefDate());
        storeOrderSummaryTrendDo.setOrderNum(addData.getOrderNum());
        storeOrderSummaryTrendDo.setOrderUserNum(addData.getUserNum());
        storeOrderSummaryTrendDo.setOrderPayNum(payData.getOrderNum());
        storeOrderSummaryTrendDo.setOrderPayUserNum(payData.getUserNum());
        storeOrderSummaryTrendDo.setTotalPaidMoney(payData.getTotalMoneyPaid());
        storeOrderSummaryTrendDo.setType(param.getType());
        storeOrderSummaryTrendDo.setStoreId(param.getStoreId());
        StoreOrderSummaryTrendDo hasStatisticInfo = getStoreStatistic(param);
        if (hasStatisticInfo != null) {
            storeOrderSummaryTrendDo.setId(hasStatisticInfo.getId());
            updateStoreStatistic(storeOrderSummaryTrendDo);
        } else {
            insertStoreStatistic(storeOrderSummaryTrendDo);
        }
    }
}
