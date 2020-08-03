package com.vpu.mp.dao.shop.order;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReport;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.RETURN_ORDER;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;

/**
 * 退款订单
 * @author 孔德成
 * @date 2020/7/31 17:34
 */
@Repository
public class ReturnOrderDao extends ShopBaseDao {


    /**
     * 获取时间内退款订单的报表
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<Date, MedicalOrderReport> medicalOrderSalesReport(Timestamp startTime, Timestamp  endTime){
        return db().select(
                //日期
                date(RETURN_ORDER.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME),
                //退款金额
                sum((RETURN_ORDER.MONEY).add(RETURN_ORDER.SHIPPING_FEE)).as(ActiveDiscountMoney.RETURN_AMOUNT),
                //退款单数
                DSL.count().as(ActiveDiscountMoney.RETURN_NUMBER))
                .from(RETURN_ORDER)
                .where(RETURN_ORDER.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(RETURN_ORDER.CREATE_TIME))
                .orderBy(RETURN_ORDER.CREATE_TIME)
                .fetchMap(date(RETURN_ORDER.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReport.class);
    }






}
