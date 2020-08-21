package com.vpu.mp.dao.main.order;

import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import static com.vpu.mp.db.main.Tables.ORDER_INFO_BAK;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;

/**
 * @author 孔德成
 * @date 2020/8/18 16:08
 */
@Component
public class OrderInfoBakDao extends MainBaseDao {


    /**
     * 药品销售报表
     * @return
     */
    public Map<Date, MedicalOrderReportVo> orderSalesReport(Timestamp startTime, Timestamp endTime, Integer shopId){
        SelectConditionStep<? extends Record> where = db().select(
                //日期
                date(ORDER_INFO_BAK.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME),
                //销售金额  微信+余额+运费
                sum((ORDER_INFO_BAK.MONEY_PAID.add(ORDER_INFO_BAK.USE_ACCOUNT).add(ORDER_INFO_BAK.SHIPPING_FEE))).as(ActiveDiscountMoney.ORDER_AMOUNT),
                //微信
                sum(ORDER_INFO_BAK.MONEY_PAID).as(ActiveDiscountMoney.MONEY_PAID),
                //余额
                sum(ORDER_INFO_BAK.USE_ACCOUNT).as(ActiveDiscountMoney.USE_ACCOUNT),
                //运费
                sum(ORDER_INFO_BAK.SHIPPING_FEE).as(ActiveDiscountMoney.SHIPPING_FEE),
                //销售单数
                count(ORDER_INFO_BAK.ORDER_ID).as(ActiveDiscountMoney.ORDER_NUMBER)
        )
                .from(ORDER_INFO_BAK)
                .where(ORDER_INFO_BAK.CREATE_TIME.between(startTime, endTime));
        if (shopId!=null&&shopId>0){
            where.and(ORDER_INFO_BAK.SHOP_ID.eq(shopId));
        }
       return where.groupBy(date(ORDER_INFO_BAK.CREATE_TIME))
                .orderBy(ORDER_INFO_BAK.CANCELLED_TIME)
                .fetchMap(date(ORDER_INFO_BAK.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReportVo.class);
    }



}
