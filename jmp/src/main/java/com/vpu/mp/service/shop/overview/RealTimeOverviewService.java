package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.Trades;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.realtime.RealTimeVo;
import com.vpu.mp.service.pojo.shop.overview.Tuple2;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

public class RealTimeOverviewService extends ShopBaseService {

    /**
     * 实时概况
     */
    public RealTimeVo realTime(){
        RealTimeVo realTimeVo = new RealTimeVo();
        //各个时间段的交易额累加和（当天和昨天）
        //今天
        List<Tuple2> todayTrades = db().select(Trades.TRADES.HOUR,Trades.TRADES.PAY_ORDER_MONEY)
                .from(Trades.TRADES)
                .where(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),0).getTime())))
                .orderBy(Trades.TRADES.HOUR.asc())
                .fetchInto(Tuple2.class);
        //昨天
        List<Tuple2> yesTrades = db().select(Trades.TRADES.HOUR,Trades.TRADES.PAY_ORDER_MONEY)
                .from(Trades.TRADES)
                .where(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),-1).getTime())))
                .orderBy(Trades.TRADES.HOUR.asc())
                .fetchInto(Tuple2.class);
        double sum = 0;
        byte hour = 0;
        List<Tuple2<Byte,Double>> todayResult = new ArrayList<>(24);
        for(Tuple2 tuple : todayTrades){
            byte tempHour = Byte.valueOf(tuple.getE1().toString());
            double tempMoney = Double.valueOf(tuple.getE2().toString());
            if (tempHour == hour){
                sum += tempMoney;
                todayResult.add(new Tuple2<>(hour,sum));
                hour++;
            }else{
                while (hour <= tempHour){
                    todayResult.add(new Tuple2<>(hour,sum));
                    hour++;
                }
            }
        }
        realTimeVo.setTodayPaidMoney(todayResult);

        //pay_user_num_yes---uv_num_yesterday(int int)
        List<Integer> yesPayUserNum = db().select(USER_SUMMARY_TREND.ORDER_USER_DATA,USER_SUMMARY_TREND.LOGIN_DATA)
                .from(USER_SUMMARY_TREND)
                .where(USER_SUMMARY_TREND.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),0).getTime())))
                .and(USER_SUMMARY_TREND.TYPE.eq((byte)1))
                .fetchInto(Integer.class);
        List<Integer> todayPayUserNum = db().select(DSL.countDistinct(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO)
                .where(OrderInfo.ORDER_INFO.CREATE_TIME.greaterOrEqual(Util.getStartToday(new Date())))
                .and(OrderInfo.ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte)3))
                .and(OrderInfo.ORDER_INFO.IS_COD.eq((byte)0)
                        .or(OrderInfo.ORDER_INFO.IS_COD.eq((byte)1)
                                .and(OrderInfo.ORDER_INFO.SHIPPING_TIME.isNotNull())))
                .fetchInto(Integer.class);

        realTimeVo.setPayUserNum(new Tuple2<>(Util.isEmpty(todayPayUserNum) ? 0 : todayPayUserNum.get(0),Util.isEmpty(yesPayUserNum) ? 0 : todayPayUserNum.get(0)));

        //uv
        List<Integer> uvToday = db().select(DSL.countDistinct(USER_LOGIN_RECORD.USER_ID))
                .from(USER_LOGIN_RECORD)
                .where(USER_LOGIN_RECORD.CREATE_TIME.greaterOrEqual(Util.getStartToday(new Date())))
                .fetchInto(Integer.class);
        List<Integer> uvYesterday = db().select(DSL.countDistinct(USER_LOGIN_RECORD.USER_ID))
                .from(USER_LOGIN_RECORD)
                .where(USER_LOGIN_RECORD.CREATE_TIME.greaterOrEqual(Util.getEarlyTimeStamp(new Date(),-1)))
                .and(USER_LOGIN_RECORD.CREATE_TIME.lessThan(Util.getStartToday(new Date())))
                .fetchInto(Integer.class);

        realTimeVo.setVisitUsers(new Tuple2<>(Util.isEmpty(uvToday) ? 0 : uvToday.get(0),Util.isEmpty(uvYesterday) ? 0 : uvYesterday.get(0)));

        //pv  pay_order_num  //第一行昨天，第二行今天，按时间排序
        //select sum(pv),SUM(pay_order_num) from b2c_trades GROUP BY ref_date HAVING ref_date = '2018-09-11' OR ref_date = '2018-09-12'
        List<Tuple2> tuple2s = db().select(DSL.sum(Trades.TRADES.PV),DSL.sum(Trades.TRADES.PAY_ORDER_NUM))
                .from(Trades.TRADES)
                .groupBy(Trades.TRADES.REF_DATE)
                .having(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),0).getTime()))
                        .or(Trades.TRADES.REF_DATE.eq(new java.sql.Date(Util.getEarlyDate(new Date(),-1).getTime()))))
                .fetchInto(Tuple2.class);
        if(!Util.isEmpty(tuple2s) && tuple2s.size() == 2){
            realTimeVo.setPageViews(new Tuple2<>(Long.valueOf(tuple2s.get(1).getE1().toString()),Long.valueOf(tuple2s.get(0).getE1().toString())));
            realTimeVo.setPayOrderNum(new Tuple2<>(Integer.valueOf(tuple2s.get(1).getE2().toString()),Integer.valueOf(tuple2s.get(0).getE2().toString())));
        }
        return realTimeVo;
    }

    /**
     * 核心指标
     * TODO 页面展示不合理，待定
     */
    public void coreIndicator(){

    }
}
