package com.vpu.mp.service.shop.report;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.order.ReturnOrderDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReport;
import com.vpu.mp.service.pojo.shop.report.MedicalSalesReportParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 药品销售报表
 * @author 孔德成
 * @date 2020/7/31 15:12
 */
@Service
public class MedicalSalesReportService extends ShopBaseService {

    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ReturnOrderDao returnOrderDao;

    /**
     * 药品销售报表
     * @param param
     * @return
     */
     public List<MedicalOrderReport> medicalSalesReport(MedicalSalesReportParam param){
         Timestamp startDate = param.getStartTime();
         Timestamp endDate = param.getEndTime();
         if (startDate == null || endDate == null) {
             DateTime date = DateUtil.date();
             endDate = DateUtil.endOfDay(date).toTimestamp();
             startDate = DateUtil.beginOfMonth(date).toTimestamp();
         }
         Map<Date, MedicalOrderReport> orderMap = orderInfoDao.orderSalesReport(startDate, endDate);
         Map<Date, MedicalOrderReport> returnOrderMap = returnOrderDao.medicalOrderSalesReport(startDate, endDate);
         List<MedicalOrderReport> list  =new ArrayList<>();
         while (startDate.compareTo(endDate) <= 0) {
             String dateFormat = DateUtils.dateFormat(DateUtils.DATE_FORMAT_SIMPLE, startDate);
             MedicalOrderReport report =new MedicalOrderReport();
             MedicalOrderReport orderReport = orderMap.get(dateFormat);
             MedicalOrderReport returnReport = returnOrderMap.get(dateFormat);
             if (orderReport==null){
                 orderReport =new MedicalOrderReport();
             }
             if (returnReport==null){
                 returnReport =new MedicalOrderReport();
             }
             BigDecimal goodsPrice = Optional.ofNullable(orderReport.getOrderAmount()).orElse(BigDecimal.ZERO);
             Integer orderNumber = Optional.ofNullable(orderReport.getOrderNumber()).orElse(0);
             BigDecimal medicalAmount = Optional.ofNullable(orderReport.getOrderMedicalAmount()).orElse(BigDecimal.ZERO);
             BigDecimal orderAvg = Optional.ofNullable(orderReport.getOrderAvg()).orElse(BigDecimal.ZERO);
             Integer medicalNumber = Optional.ofNullable(orderReport.getOrderMedicalNumber()).orElse(0);
             BigDecimal returnAmount = Optional.ofNullable(returnReport.getReturnAmount()).orElse(BigDecimal.ZERO);
             Integer returnNumber = Optional.ofNullable(returnReport.getReturnNumber()).orElse(0);
             report.setTime(dateFormat);
             report.setOrderAmount(goodsPrice);
             report.setOrderNumber(orderNumber);
             report.setOrderAvg(orderAvg);
             report.setOrderMedicalAmount(medicalAmount);
             report.setOrderMedicalNumber(medicalNumber);
             report.setReturnAmount(returnAmount);
             report.setReturnNumber(returnNumber);
             list.add(report);
             startDate = DateUtils.getTimeStampPlus(startDate,1, ChronoUnit.DAYS);
         }
         return list;
     }

}
