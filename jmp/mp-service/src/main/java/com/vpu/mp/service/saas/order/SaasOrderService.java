package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.Page;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.main.order.OrderGoodsBakDao;
import com.vpu.mp.dao.main.order.OrderInfoBakDao;
import com.vpu.mp.dao.main.order.ReturnOrderBakDao;
import com.vpu.mp.dao.main.order.ReturnOrderGoodsBakDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.order.ReturnOrderDao;
import com.vpu.mp.db.main.tables.records.OrderInfoBakRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.order.report.OrderBakSalesReportParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderQueryVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnListVo;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.report.MedicalSalesReportVo;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.report.MedicalSalesReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.Tables.ORDER_GOODS_BAK;
import static com.vpu.mp.db.main.Tables.ORDER_INFO_BAK;
import static com.vpu.mp.db.main.Tables.RETURN_ORDER_GOODS_BAK;

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
    private OrderGoodsBakDao orderGoodsBakDao;
    @Autowired
    private ReturnOrderBakDao returnOrderBakDao;
    @Autowired
    private ReturnOrderGoodsBakDao returnOrderGoodsBakDao;


    public void synOrderGoodsCreate(Timestamp beginTime, Timestamp endTime, Integer shopId) {
        List<OrderGoodsDo> createOrderGoodsList = orderGoodsDao.listCreateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderGoodsByYesterday = new ArrayList<>();
        createOrderGoodsList.forEach(orderInfoDo -> {
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderGoodsByYesterday).execute();
    }

    public void synOrderGoodsUpdate(Timestamp beginTime, Timestamp endTime, Integer shopId) {
        List<OrderGoodsDo> updateOrderGoodsList = orderGoodsDao.listUpdateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> updateOrderGoodsByYesterday = new ArrayList<>();
        updateOrderGoodsList.forEach(orderInfoDo -> {
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderGoodsByYesterday).execute();
    }

    public void synOrderCreate(Timestamp beginTime, Timestamp endTime, Integer shopId) {
        List<OrderInfoDo> createOrderList = orderInfoDao.listCreateOrderByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderByYesterday = new ArrayList<>();
        createOrderList.forEach(orderInfoDo -> {
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderByYesterday).execute();
    }

    public void synOrderUpdate(Timestamp beginTime, Timestamp endTime, Integer shopId) {
        List<OrderInfoBakRecord> updateOrderByYesterday = new ArrayList<>();
        List<OrderInfoDo> updateOrderList = orderInfoDao.listUpdateOrderByYesterday(beginTime, endTime);
        updateOrderList.forEach(orderInfoDo -> {
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
        logger().info("开始时间{},结束时间{}", startDate, endDate);
        Page page = medicalSalesReportService.getPage(param, param.getTotalRows());
        Map<Date, MedicalOrderReportVo> orderMap = orderInfoBakDao.orderSalesReport(startDate, endDate, param.getShopId());
        Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderBakDao.medicalOrderSalesReport(startDate, endDate, param.getShopId());
        List<MedicalOrderReportVo> list = new ArrayList<>();
        map.forEach((startDate2, endDate2) -> {
            MedicalOrderReportVo report = medicalSalesReportService.getMedicalOrderReportVo(startDate2, endDate2, orderMap, returnOrderMap);
            list.add(report);
        });
        MedicalSalesReportVo vo = new MedicalSalesReportVo();
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
        logger().info("开始时间{},结束时间{}", startDate, endDate);

        Page page = medicalSalesReportService.getPage(param, param.getTotalRows());
        Map<Date, MedicalOrderReportVo> orderMap = orderInfoBakDao.orderSalesReport(startDate, endDate, param.getShopId());
        Map<Date, MedicalOrderReportVo> returnOrderMap = returnOrderBakDao.medicalOrderSalesReport(startDate, endDate, param.getShopId());
        List<MedicalOrderReportVo> list = new ArrayList<>();
        map.forEach((startDate2, endDate2) -> {
            MedicalOrderReportVo report = medicalSalesReportService.getMedicalOrderReportVo(startDate2, endDate2, orderMap, returnOrderMap);
            list.add(report);
        });


        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
        excelWriter.writeModelList(list, MedicalOrderReportVo.class);
        return workbook;
    }

    public OrderQueryVo getOrderInfoPageList(OrderPageListQueryParam param) {
        log.info("订单综合查询开始");
        OrderQueryVo result = new OrderQueryVo();
        //退款退货订单查询(其主表不同,所以走分支逻辑)
        if (param.searchType != null && param.searchType == 1) {
            result.setList(getReturnPageList(param));
            return result;
        }

        PageResult<OrderListInfoVo> pageResult = new PageResult<>();
        result.setList(pageResult);
        //得到订单号(包含主订单和正常订单)
        PageResult<String> orderSn = orderInfoBakDao.getOrderSns(param, result);
        pageResult.setPage(orderSn.getPage());
        if (orderSn.getDataList().size() < 1) {
            return result;
        }
        //查询出全部订单按照主订单分组，正常订单的key为orderSn
        Map<String, List<OrderListInfoVo>> allOrder = orderInfoBakDao.getOrders(orderSn.getDataList());
        //构造展示商品的订单:MainOrderCount.count=1的可能为正常订单或处于未子订单未被拆分,>1的为已经拆分
        Map<Integer, OrderListInfoVo> goodsList = new HashMap<Integer, OrderListInfoVo>();
        //主订单或正常订单
        ArrayList<OrderListInfoVo> mainOrderList = new ArrayList<OrderListInfoVo>(orderSn.getDataList().size());
        //现子订单数>0的主订单
        ArrayList<Integer> orderCountMoreZero = new ArrayList<Integer>();
        for (String moc : orderSn.getDataList()) {
            List<OrderListInfoVo> list = allOrder.get(moc);
            int size = list.size();
            OrderListInfoVo mOrder = null;
            List<OrderListInfoVo> cList = size > 1 ? new ArrayList<OrderListInfoVo>(size - 1) : null;
            for (OrderListInfoVo order : list) {
                //将所有订单id放入goodsList,在后续向订单添加商品时增加过滤主订单下与子订单重复的商品
                goodsList.put(order.getOrderId(), order);
                if (order.getOrderSn().equals(moc)) {
                    //设置订单支付方式（无子单）
                    orderInfoBakDao.setPayCodeList(order);
                    mOrder = order;
                    if (size == 1) {
                        break;
                    }
                } else {
                    cList.add(order);
                }
            }
            if (cList != null) {
                orderCountMoreZero.add(mOrder.getOrderId());
            }
            mOrder.setChildOrders(cList);
            mainOrderList.add(mOrder);
        }
        //需要查询商品的订单
        Integer[] allOrderSn = goodsList.keySet().toArray(new Integer[0]);
        //key为order_id,v为其下商品
        Map<Integer, List<OrderGoodsVo>> goods = orderGoodsBakDao.getByOrderIds(allOrderSn).intoGroups(ORDER_GOODS_BAK.ORDER_ID, OrderGoodsVo.class);
        Set<Map.Entry<Integer, List<OrderGoodsVo>>> entrySet = goods.entrySet();
        for (Map.Entry<Integer, List<OrderGoodsVo>> entry : entrySet) {
            //过滤主订单中已经拆到子订单的商品(依赖于orderinfo表自增id,当循环到主订单时其子订单下的商品都已插入到childOrders.goods里)
            if (orderCountMoreZero.contains(entry.getKey())) {
                orderInfoBakDao.filterMainOrderGoods(goodsList.get(entry.getKey()), entry.getValue());
                continue;
            }
            goodsList.get(entry.getKey()).setGoods(entry.getValue());
        }
        //查询订单订单是否存在退款中订单
        Map<Integer, Integer> returningCount = returnOrderBakDao.getOrderCount(allOrderSn, OrderConstant.REFUND_STATUS_AUDITING, OrderConstant.REFUND_STATUS_AUDIT_PASS, OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);
        //设置订单操作
        for (List<OrderListInfoVo> orderList : allOrder.values()) {
            for (OrderListInfoVo order : orderList) {
                OrderOperationJudgment.operationSet(order, returningCount.get(order.getOrderId()), returnOrderBakDao.canBeShipped(order.getOrderSn()));
            }
        }
        pageResult.setDataList(mainOrderList);
        log.info("订单综合查询结束");
        return result;
    }

    /**
     * 退货、款订单
     *
     * @return
     */
    public PageResult<OrderReturnListVo> getReturnPageList(OrderPageListQueryParam param) {
        PageResult<OrderReturnListVo> result = returnOrderBakDao.getPageList(param);
        List<String> collect;
        List<OrderReturnListVo> dataList = result.dataList;
        if (dataList != null && dataList.size() > 0) {
            collect = dataList.stream().map(OrderReturnListVo::getOrderSn).collect(Collectors.toList());
        } else {
            return result;
        }
        //获取订单再分组
        Map<Integer, List<OrderReturnGoodsVo>> goods = returnOrderGoodsBakDao.getByOrderSn(collect.toArray(new String[collect.size()])).intoGroups(RETURN_ORDER_GOODS_BAK.RET_ID, OrderReturnGoodsVo.class);
        ;
        for (OrderReturnListVo order : dataList) {
            order.setGoods(goods.get(order.getRetId()));
        }
        return result;
    }
}
