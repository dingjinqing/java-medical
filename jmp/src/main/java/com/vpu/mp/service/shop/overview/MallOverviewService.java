package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.UserLoginRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.overview.BindUnBindOfficialParam;
import com.vpu.mp.service.pojo.shop.overview.DataDemonstrationParam;
import com.vpu.mp.service.pojo.shop.overview.DataDemonstrationVo;
import org.jooq.Comparator;
import org.jooq.Condition;
import java.util.Date;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 商城概览service
 */
public class MallOverviewService extends BaseService {

    /**
     * 获取数据展示数据，
     * @param param 1：当天数据；7：近一周数据；30：近一个月数据；90：近三个月数据
     * @return
     */
    public DataDemonstrationVo dataDemonstration(DataDemonstrationParam param){
        DataDemonstrationVo vo = new DataDemonstrationVo();
        switch (param.getScreeningTime()){
            case 1 :
                return getDataDemonstration((byte) 1,vo);
            case 7 :
                return getDataDemonstration((byte) 7,vo);
            case 30 :
                return getDataDemonstration((byte) 30,vo);
            case 90 :
                return getDataDemonstration((byte) 90,vo);
            default :
                return getDataDemonstration((byte) 1,vo);
        }
    }
    public DataDemonstrationVo getDataDemonstration(byte screeningTime,DataDemonstrationVo vo){
        Condition orderInfoTime = OrderInfo.ORDER_INFO.CREATE_TIME.
                compare(Comparator.GREATER_OR_EQUAL,Util.getBeforeOrAfterDay(new Date(),(-screeningTime+1)));
        Condition UserLoginRecordTime = UserLoginRecord.USER_LOGIN_RECORD.CREATE_TIME.
                compare(Comparator.GREATER_OR_EQUAL,Util.getBeforeOrAfterDay(new Date(),(-screeningTime+1)));
        Condition payOrderCon = OrderInfo.ORDER_INFO.ORDER_STATUS.compare(Comparator.GREATER_OR_EQUAL,(byte)3);
        vo.setUserVisitNum(db().fetchCount(UserLoginRecord.USER_LOGIN_RECORD,UserLoginRecordTime));
        vo.setPaidOrderNum(db().fetchCount(OrderInfo.ORDER_INFO,orderInfoTime.and(payOrderCon)));
        vo.setOrderUserNum(db().selectDistinct(count(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime).execute());
        vo.setOrderNum(db().fetchCount(OrderInfo.ORDER_INFO,orderInfoTime));
        vo.setTotalPaidSum(db().select(sum(OrderInfo.ORDER_INFO.MONEY_PAID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime.and(payOrderCon))
                .execute());
        vo.setPaidUserNum(db().selectDistinct(count(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime.and(payOrderCon))
                .execute());

        double orderNum = vo.getOrderNum();
        double userVisitNum = vo.getUserVisitNum();
        double paidNum = vo.getPaidOrderNum();
        vo.setUv2order(userVisitNum!=0&&orderNum!=0 ? orderNum/userVisitNum : 0.0);
        vo.setUv2paid(userVisitNum!=0&&paidNum!=0 ? paidNum/userVisitNum : 0.0);
        vo.setOrder2paid(paidNum!=0&&orderNum!=0 ? paidNum/orderNum : 0.0);
        return vo;
    }
}
