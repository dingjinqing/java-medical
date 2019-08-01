package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.transaction.*;
import org.jooq.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collectors;

import static com.vpu.mp.service.shop.overview.RealTimeOverviewService.div;
import static org.jooq.impl.DSL.*;

/**
 * @author liufei
 * @date 2019/7/31
 * @description 交易统计
 */
@Service
public class TransactionStatisticsService extends ShopBaseService {

    private static OrderInfo oi = OrderInfo.ORDER_INFO.as("oi");
    private static OrderGoods og = OrderGoods.ORDER_GOODS.as("og");
    private static UserLoginRecord ulr = UserLoginRecord.USER_LOGIN_RECORD.as("ulr");
    private static UserTag ut = UserTag.USER_TAG.as("ut");
    private static Tag t = Tag.TAG.as("t");
    private static User u = User.USER.as("u");
    private static DistributionTag dt = DistributionTag.DISTRIBUTION_TAG.as("dt");

    /**
     * 地域分布
     *
     * @param param 入参
     * @return 地域分布显示数据集
     */
    public GeographicalCollVo geographical(GeographicalParam param) {
        GeographicalCollVo collVo = new GeographicalCollVo();
        Optional<Date> screeningTime = Optional.of(param.getScreeningTime());
        Timestamp startTime = Util.currentMonthFirstDay(screeningTime.orElse(new Date()));
        Timestamp endTime = Util.nextMonthFirstDay(screeningTime.orElse(new Date()));
        List<GeographicalVo> voList = db().select(min(oi.PROVINCE_NAME).as("province")
                , min(oi.PROVINCE_CODE).as("provinceCode")
                , sum(oi.MONEY_PAID.add(oi.SCORE_DISCOUNT).add(oi.USE_ACCOUNT).add(oi.MEMBER_CARD_BALANCE)).as("totalDealMoney")
                , countDistinct(oi.USER_ID).as("orderUserNum")
                , count(oi.ORDER_ID).as("orderNum"))
                .from(oi)
                .where(commomCondition(startTime, endTime))
                .groupBy(oi.PROVINCE_CODE)
                .fetchInto(GeographicalVo.class);
        for (GeographicalVo vo : voList) {
            vo.setUv(getDistrictUv(startTime, endTime, vo.getProvinceCode()));
            vo.setUv2paid(div(vo.getOrderUserNum(), vo.getUv()));
        }
        double maxMoney = voList.stream().max(Comparator.comparing(GeographicalVo::getTotalDealMoney)).orElse(new GeographicalVo()).getTotalDealMoney();
        double minMoney = voList.stream().min(Comparator.comparing(GeographicalVo::getTotalDealMoney)).orElse(new GeographicalVo()).getTotalDealMoney();
        collVo.setMaxTotalDealMoney(maxMoney);
        collVo.setMinTotalDealMoney(minMoney);
        collVo.setVos(voList.stream().sorted(Comparator.comparing(GeographicalVo::getTotalDealMoney)).collect(Collectors.toList()));
        //voList.stream().sorted(Comparator.comparing(GeographicalVo::getTotalDealMoney).reversed());

        return collVo;
    }

    /**
     * 构造公共查询条件，成交用户数，总成交金额，付款订单数
     */
    private Condition commomCondition(Timestamp startTime, Timestamp endTime) {
        return oi.CREATE_TIME.greaterOrEqual(startTime).and(oi.CREATE_TIME.lessThan(endTime).and(oi.ORDER_STATUS.greaterOrEqual((byte) 3).or(oi.ORDER_STATUS.eq((byte) 0).and(oi.BK_ORDER_PAID.greaterThan((byte) 0)))).and(oi.IS_COD.eq((byte) 0).or(oi.IS_COD.eq((byte) 1).and(oi.SHIPPING_TIME.isNotNull()))));
    }

    /**
     * 获得地区用户uv
     */
    private Integer getDistrictUv(Timestamp startTime, Timestamp endTime, String provinceCode) {
        Optional<Integer> optional = db().select(countDistinct(ulr.USER_ID)).from(ulr).where(ulr.CREATE_TIME.greaterOrEqual(startTime)).and(ulr.CREATE_TIME.lessThan(endTime)).and(ulr.PROVINCE_CODE.eq(provinceCode)).fetchOptionalInto(Integer.class);
        return optional.orElse(0);
    }

    /**
     * 标签成交分析
     * @param param 入参
     */
    public PageResult<LabelAnalysisVo> labelAnalysis(LabelAnalysisParam param){
        if (param.getScreeningTime() > 0){
            switch(param.getScreeningTime()){
                case 1 :
                    return fixedLabelAnalysis(param);
                case 7 :
                    return fixedLabelAnalysis(param);
                case 30 :
                    return fixedLabelAnalysis(param);
                default :
                    param.setScreeningTime((byte)1);
                    return fixedLabelAnalysis(param);
            }
        }else {
            return customerLabelAnalysis(param);
        }
    }

    /**
     * 固定日期数据查询
     */
    private PageResult<LabelAnalysisVo> fixedLabelAnalysis(LabelAnalysisParam param){
        SelectLimitStep limitStep = db().select(dt.TAG.as("tagName")
                ,dt.PAY_USER_NUM.as("paidUserNum")
                ,dt.PAY_ORDER_NUM.as("paidNum")
                ,dt.PAY_ORDER_MONEY.as("paidMoney")
                ,dt.PAY_GOODS_NUMBER.as("paidGoddsNum")
                ,dt.HAS_MOBILE_NUM.as("userNumWithPhone")
                ,dt.HAS_USER_NUM.as("userNum"))
                .from(dt)
                .where(dt.REF_DATE.eq(Util.getEarlySqlDate(new Date(),0)))
                .and(dt.TYPE.eq(param.getScreeningTime()));
        return getPageResult(limitStep,param.getCurrentPage(),param.getPageRows(),LabelAnalysisVo.class);

    }

    /**
     * 自定义时间实时查询数据
     */
    private PageResult<LabelAnalysisVo> customerLabelAnalysis(LabelAnalysisParam param){
        Timestamp startTime = Util.getEarlyTimeStamp(new Date(),-1);
        Timestamp endTime = Util.getEarlyTimeStamp(new Date(),0);
        if (param.getEndTime() != null){
            endTime = new Timestamp(param.getEndTime().getTime());
        }
        if (param.getStartTime() != null){
            startTime = new Timestamp(param.getStartTime().getTime());
        }
        /** 先查用户数，放入分页集合中，然后循环设置其他数据 */
        SelectLimitStep limitStep = db().select(min(ut.TAG_ID).as("tadId"),min(t.TAG_NAME).as("tagName"),count(ut.USER_ID).as("userNum"))
                .from(ut).leftJoin(t).on(ut.TAG_ID.eq(t.TAG_ID))
                .where(ut.CREATE_TIME.greaterOrEqual(startTime))
                .and(ut.CREATE_TIME.lessThan(endTime))
                .groupBy(ut.TAG_ID);
        PageResult<LabelAnalysisVo> result = getPageResult(limitStep,param.getCurrentPage(),param.getPageRows(),LabelAnalysisVo.class);

        Map<Integer,Record4<BigDecimal,Integer,Integer,BigDecimal>> temp = db()
                .select(sum(oi.MONEY_PAID.add(oi.SCORE_DISCOUNT).add(oi.USE_ACCOUNT).add(oi.MEMBER_CARD_BALANCE)).as("paidMoney")
                , countDistinct(oi.USER_ID).as("paidUserNum")
                , count(oi.ORDER_ID).as("paidNum")
                , sum(og.GOODS_NUMBER).as("paidGoodsNum"))
                .from(ut)
                .leftJoin(oi)
                .on(ut.USER_ID.eq(oi.USER_ID))
                .leftJoin(og)
                .on(oi.ORDER_SN.eq(og.ORDER_SN))
                .where(commomCondition(startTime,endTime))
                .groupBy(ut.TAG_ID)
                .fetchMap(ut.TAG_ID);

        Map<Integer,Record1<Integer>> temp1 = db().select(countDistinct(u.USER_ID).as("userNumWithPhone")).from(ut)
                .leftJoin(u)
                .on(ut.USER_ID.eq(u.USER_ID))
                .where(u.CREATE_TIME.greaterOrEqual(startTime))
                .and(u.CREATE_TIME.lessThan(endTime))
                .and(u.MOBILE.isNotNull())
                .groupBy(u.USER_ID)
                .fetchMap(ut.TAG_ID);
        for (LabelAnalysisVo vo : result.getDataList()){
            if (temp.containsKey(vo.getTagId())){
                Record4<BigDecimal,Integer,Integer,BigDecimal> record4 = temp.get(vo.getTagId());
                vo.setPaidMoney(record4.value1());
                vo.setPaidUserNum(record4.value2());
                vo.setPaidNum(record4.value3());
                vo.setPaidGoodsNum(record4.value4().intValue());
            }
            if (temp1.containsKey(vo.getTagId())){
                Record1<Integer> record1 = temp1.get(vo.getTagId());
                vo.setUserNumWithPhone(record1.value1());
            }
        }
        return result;
    }
    private void sortBy(LabelAnalysisParam param,PageResult<LabelAnalysisVo> result){

    }
}
