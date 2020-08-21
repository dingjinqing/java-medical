package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.Page;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.main.order.OrderInfoBakDao;
import com.vpu.mp.dao.main.order.ReturnOrderBakDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.order.ReturnOrderDao;
import com.vpu.mp.db.main.tables.records.OrderInfoBakRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.order.report.OrderBakSalesReportParam;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.report.MedicalSalesReportVo;
import com.vpu.mp.service.shop.report.MedicalSalesReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.main.Tables.ORDER_INFO_BAK;

/**
 * @author luguangyao
 */
@Service
@Slf4j
public class SaasOrderService extends MainBaseService {

    @Autowired
    private MedicalSalesReportService medicalSalesReportService;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ReturnOrderDao returnOrderDao;
    @Autowired
    private OrderInfoBakDao orderInfoBakDao;
    @Autowired
    private ReturnOrderBakDao returnOrderBakDao;


    public void synOrderGoodsCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderGoodsDo> createOrderGoodsList = orderGoodsDao.listCreateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderGoodsByYesterday =new ArrayList<>();
        createOrderGoodsList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderGoodsByYesterday).execute();
    }

    public void synOrderGoodsUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderGoodsDo> updateOrderGoodsList = orderGoodsDao.listUpdateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> updateOrderGoodsByYesterday =new ArrayList<>();
        updateOrderGoodsList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderGoodsByYesterday).execute();
    }

    public void synOrderCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoDo> createOrderList = orderInfoDao.listCreateOrderByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderByYesterday =new ArrayList<>();
        createOrderList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderByYesterday).execute();
    }

    public void synOrderUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoBakRecord> updateOrderByYesterday =new ArrayList<>();
        List<OrderInfoDo> updateOrderList = orderInfoDao.listUpdateOrderByYesterday(beginTime, endTime);
        updateOrderList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderByYesterday).execute();
    }


    public Object medicalSalesReport(OrderBakSalesReportParam param) {
        medicalSalesReportService.buildSalesReportDate(param);
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        Map<Timestamp, Timestamp> map = param.getMap();
        logger().info("开始时间{},结束时间{}",startDate,endDate);
        Page page = medicalSalesReportService.getPage(param, param.getTotalRows());
        Map<Date, MedicalOrderReportVo> orderMap = orderInfoBakDao.orderSalesReport(startDate, endDate,param.getShopId());
        Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderBakDao.medicalOrderSalesReport(startDate, endDate, param.getShopId());
        List<MedicalOrderReportVo> list  =new ArrayList<>();
        map.forEach((startDate2,endDate2)->{
            MedicalOrderReportVo report = medicalSalesReportService.getMedicalOrderReportVo(startDate2,endDate2, orderMap, returnOrderMap);
            list.add(report);
        });
        MedicalSalesReportVo vo =new MedicalSalesReportVo();
        vo.setDataList(list);
        vo.setPage(page);
        return vo;
    }

    public Workbook medicalSalesReportExport(OrderBakSalesReportParam param, String lang) {
        medicalSalesReportService.buildSalesReportDate(param);
        param.setPageRows(Integer.MAX_VALUE);
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        Map<Timestamp, Timestamp> map = param.getMap();
        logger().info("开始时间{},结束时间{}",startDate,endDate);

        Page page = medicalSalesReportService.getPage(param, param.getTotalRows());
        Map<Date, MedicalOrderReportVo> orderMap = orderInfoBakDao.orderSalesReport(startDate, endDate, param.getShopId());
        Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderBakDao.medicalOrderSalesReport(startDate, endDate,param.getShopId());
        List<MedicalOrderReportVo> list  =new ArrayList<>();
        map.forEach((startDate2,endDate2)->{
            MedicalOrderReportVo report = medicalSalesReportService.getMedicalOrderReportVo(startDate2,endDate2, orderMap, returnOrderMap);
            list.add(report);
        });


        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(list, MedicalOrderReportVo.class);
        return workbook;
    }
}
