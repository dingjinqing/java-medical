package com.vpu.mp.service.shop.report;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.Page;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.order.ReturnOrderDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.report.MedicalSalesReportParam;
import org.apache.poi.ss.usermodel.Workbook;
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
     public PageResult medicalSalesReport(MedicalSalesReportParam param){
         Timestamp startDate = param.getStartTime();
         Timestamp endDate = param.getEndTime();
         if (startDate == null || endDate == null) {
             DateTime date = DateUtil.date();
             endDate = DateUtil.endOfDay(date).toTimestamp();
             startDate = DateUtil.beginOfMonth(date).toTimestamp();
         }else {
             startDate = DateUtil.beginOfDay(startDate).toTimestamp();
             endDate = DateUtil.endOfDay(endDate).toTimestamp();
         }
         long totalRows = DateUtil.between(startDate, endDate, DateUnit.DAY)+1;
         startDate = DateUtils.getTimeStampPlus(startDate, (param.getCurrentPage() - 1) * param.getPageRows(), ChronoUnit.DAYS);
         Timestamp endDate2 = DateUtils.getTimeStampPlus(startDate, (param.getCurrentPage()) * param.getPageRows(), ChronoUnit.DAYS);
         if (endDate2.before(endDate)) {
             endDate = endDate2;
         }
         Page page = getPage(param, totalRows);
         Map<Date, MedicalOrderReportVo> orderMap = orderInfoDao.orderSalesReport(startDate, endDate);
         Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderDao.medicalOrderSalesReport(startDate, endDate);
         List<MedicalOrderReportVo> list  =new ArrayList<>();
         while (startDate.compareTo(endDate) <= 0) {
             MedicalOrderReportVo report = getMedicalOrderReportVo(startDate, orderMap, returnOrderMap);
             list.add(report);
             startDate = DateUtils.getTimeStampPlus(startDate,1, ChronoUnit.DAYS);
         }
         PageResult<MedicalOrderReportVo> pageResult =new PageResult();
         pageResult.setDataList(list);
         pageResult.setPage(page);
         return pageResult;
     }

    private Page getPage(MedicalSalesReportParam param, long totalRows) {
        Page page =new Page();
        page.setCurrentPage(param.getCurrentPage());
        page.setPageRows(param.getPageRows());
        page.setTotalRows((int) totalRows);
        page.setFirstPage(1);
        page.setLastPage(param.getCurrentPage()-1);
        page.setNextPage(page.getCurrentPage()+1);
        if (totalRows%param.getPageRows()>0){
            page.setPageCount((int) (totalRows/param.getPageRows()+1));
        }else {
            page.setPageCount((int) (totalRows/param.getPageRows()));
        }
        return page;
    }

    /**
     * 导出销售报表数据
     * @param param
     * @param lang
     * @return
     */
    public Workbook medicalSalesReportExport(MedicalSalesReportParam param, String lang) {
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate == null || endDate == null) {
            DateTime date = DateUtil.date();
            endDate = DateUtil.endOfDay(date).toTimestamp();
            startDate = DateUtil.beginOfMonth(date).toTimestamp();
        }
        Map<Date, MedicalOrderReportVo> orderMap = orderInfoDao.orderSalesReport(startDate, endDate);
        Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderDao.medicalOrderSalesReport(startDate, endDate);
        List<MedicalOrderReportVo> list  =new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0) {
            MedicalOrderReportVo report = getMedicalOrderReportVo(startDate, orderMap, returnOrderMap);
            list.add(report);
            startDate = DateUtils.getTimeStampPlus(startDate,1, ChronoUnit.DAYS);
        }
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(list, MedicalOrderReportVo.class);
        return workbook;
    }

    private MedicalOrderReportVo getMedicalOrderReportVo(Timestamp startDate, Map<Date, MedicalOrderReportVo> orderMap, Map<Date, MedicalOrderReportVo> returnOrderMap) {
        DateTime parse = DateUtil.parse(startDate.toString());
        Date date = parse.toSqlDate();
        MedicalOrderReportVo report = new MedicalOrderReportVo();
        MedicalOrderReportVo orderReport = orderMap.get(date);
        MedicalOrderReportVo returnReport = returnOrderMap.get(date);
        if (orderReport == null) {
            orderReport = new MedicalOrderReportVo();
        }
        if (returnReport == null) {
            returnReport = new MedicalOrderReportVo();
        }
        BigDecimal goodsPrice = Optional.ofNullable(orderReport.getOrderAmount()).orElse(BigDecimal.ZERO);
        Integer orderNumber = Optional.ofNullable(orderReport.getOrderNumber()).orElse(0);
        BigDecimal medicalAmount = Optional.ofNullable(orderReport.getOrderMedicalAmount()).orElse(BigDecimal.ZERO);
        BigDecimal orderAvg = Optional.ofNullable(orderReport.getOrderAvg()).orElse(BigDecimal.ZERO);
        Integer medicalNumber = Optional.ofNullable(orderReport.getOrderMedicalNumber()).orElse(0);
        BigDecimal returnAmount = Optional.ofNullable(returnReport.getReturnAmount()).orElse(BigDecimal.ZERO);
        Integer returnNumber = Optional.ofNullable(returnReport.getReturnNumber()).orElse(0);
        report.setTime(parse.toDateStr());
        report.setOrderAmount(goodsPrice);
        report.setOrderNumber(orderNumber);
        report.setOrderAvg(orderAvg);
        report.setOrderMedicalAmount(medicalAmount);
        report.setOrderMedicalNumber(medicalNumber);
        report.setReturnAmount(returnAmount);
        report.setReturnNumber(returnNumber);
        return report;
    }
}
