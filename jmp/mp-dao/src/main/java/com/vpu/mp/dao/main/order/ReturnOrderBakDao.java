package com.vpu.mp.dao.main.order;

import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import static com.vpu.mp.db.main.Tables.RETURN_ORDER_BAK;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;

/**
 * @author 孔德成
 * @date 2020/8/20 17:11
 */
@Component
public class ReturnOrderBakDao extends MainBaseDao {

    /**
     * 获取时间内退款订单的报表
     * @param startTime
     * @param endTime
     * @param shopId
     * @return
     */
    public Map<Date, MedicalOrderReportVo> medicalOrderSalesReport(Timestamp startTime, Timestamp endTime, Integer shopId){
        SelectConditionStep<? extends Record> where = db().select(
                //日期
                date(RETURN_ORDER_BAK.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME),
                //退款金额
                sum((RETURN_ORDER_BAK.MONEY).add(RETURN_ORDER_BAK.SHIPPING_FEE)).as(ActiveDiscountMoney.RETURN_AMOUNT),
                //退款单数
                DSL.count().as(ActiveDiscountMoney.RETURN_NUMBER))
                .from(RETURN_ORDER_BAK)
                .where(RETURN_ORDER_BAK.CREATE_TIME.between(startTime, endTime));
        if (shopId!=null&&shopId>0){
            where.and(RETURN_ORDER_BAK.SHOP_ID.eq(shopId));
        }

       return where.groupBy(date(RETURN_ORDER_BAK.CREATE_TIME))
                .orderBy(RETURN_ORDER_BAK.CREATE_TIME)
                .fetchMap(date(RETURN_ORDER_BAK.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReportVo.class);
    }

}
