package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.Trades;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.overview.Tuple2;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.tables.UserLoginRecord.USER_LOGIN_RECORD;
import static com.vpu.mp.db.shop.tables.UserSummaryTrend.USER_SUMMARY_TREND;

/**
 * @Author:liufei
 * @Date:2019/7/19
 * @Description:
 */
@Service
@Scope("prototype")
public class RealTimeOverviewService extends BaseService {

    /**
     * 实时概况
     */
    public void realTime(){
        //各个时间段的交易额总和（当天和昨天）
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            List<Tuple2> trades = db().select(Trades.TRADES.HOUR,Trades.TRADES.PAY_ORDER_MONEY)
                    .from(Trades.TRADES)
                    .where(Trades.TRADES.REF_DATE.eq(new java.sql.Date(formatter.parse("2018-09-11").getTime())))
                    .orderBy(Trades.TRADES.HOUR.asc())
                    .fetchInto(Tuple2.class);
//            trades.forEach((e)->System.out.println(e.getE1() + "---" + e.getE2()));
            double sum = 0;
            for(Tuple2<Byte,Double> tuple : trades){
                sum += tuple.getE2();
                tuple.setE2(sum);
            }
            trades.forEach((e)->System.out.println(e.getE1() + "---" + e.getE2()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //pay_user_num_yes---uv_num_yesterday(int int)
        List<Integer> yes_pay_user_num = db().select(USER_SUMMARY_TREND.ORDER_USER_DATA,USER_SUMMARY_TREND.LOGIN_DATA)
                .from(USER_SUMMARY_TREND)
                .where(USER_SUMMARY_TREND.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),0).getTime())))
                .and(USER_SUMMARY_TREND.TYPE.eq((byte)1))
                .fetchInto(Integer.class);
        List<Integer> today_pay_user_num = db().select(DSL.countDistinct(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO)
                .where(OrderInfo.ORDER_INFO.CREATE_TIME.greaterOrEqual(Util.getStartToday(new Date())))
                .and(OrderInfo.ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte)3))
                .and(OrderInfo.ORDER_INFO.IS_COD.eq((byte)0)
                        .or(OrderInfo.ORDER_INFO.IS_COD.eq((byte)1)
                                .and(OrderInfo.ORDER_INFO.SHIPPING_TIME.isNotNull())))
                .fetchInto(Integer.class);

        //uv
        List<Integer> uv_num_today = db().select(DSL.countDistinct(USER_LOGIN_RECORD.USER_ID))
                .from(USER_LOGIN_RECORD)
                .where(USER_LOGIN_RECORD.CREATE_TIME.greaterOrEqual(Util.getStartToday(new Date())))
                .fetchInto(Integer.class);
        //pv  pay_order_num
        //select sum(pv),SUM(pay_order_num) from b2c_trades GROUP BY ref_date HAVING ref_date = '2018-09-11' OR ref_date = '2018-09-12'
        List<Tuple2> tuple2s = db().select(DSL.sum(Trades.TRADES.PV),DSL.sum(Trades.TRADES.PAY_ORDER_NUM))
                .from(Trades.TRADES)
                .groupBy(Trades.TRADES.REF_DATE)
                .having(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),0).getTime()))
                        .or(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),-1).getTime()))))
                .fetchInto(Tuple2.class);
        tuple2s.forEach((e) -> {
            System.out.println(e.getE1() + "---" + e.getE2());});//第一行昨天，第二行今天，按时间排序


    }

    /**
     * 核心指标
     */
    public void coreIndicator(){

    }
}
