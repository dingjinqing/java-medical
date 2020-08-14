package com.vpu.mp.service.saas.order;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.main.order.MainInquiryOrderDao;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.order.MainInquiryOrderStatisticsParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderStatisticsParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/8/14
 **/
@Service
@Slf4j
public class MainInquiryOrderService extends MainBaseService {
    @Autowired
    private MainInquiryOrderDao mainInquiryOrderDao;

    /**
     * 同步新增
     * @param list
     */
    public void inquiryOrderSynchronizeInsert(List<InquiryOrderDo> list){
        mainInquiryOrderDao.inquiryOrderSynchronizeInsert(list);
    }

    /**
     * 同步更新
     * @param list
     * @param shopId
     */
    public void inquiryOrderSynchronizeUpdate(List<InquiryOrderDo> list,Integer shopId){

        mainInquiryOrderDao.inquiryOrderSynchronizeUpdate(list,shopId);
    }

    /**
     * 问诊订单统计报表查询
     * @param param
     * @return
     */
    public PageResult<InquiryOrderStatisticsVo> orderStatistics(MainInquiryOrderStatisticsParam param){
        beginAndEndOfDay(param);
        PageResult<InquiryOrderStatisticsVo> result=mainInquiryOrderDao.orderStatisticsPage(param);
        return result;
    }

    /**
     * 日期的时分秒开始和结束
     * @param param
     */
    public void beginAndEndOfDay(MainInquiryOrderStatisticsParam param){
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate != null ) {
            startDate = DateUtil.beginOfDay(startDate).toTimestamp();
            param.setStartTime(startDate);
        }if( endDate != null){
            endDate = DateUtil.endOfDay(endDate).toTimestamp();
            param.setEndTime(endDate);
        }
    }
}
