package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.TradesRecordSummary;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.asset.RevenueDate;
import com.vpu.mp.service.pojo.shop.overview.asset.RevenueProfileParam;
import com.vpu.mp.service.pojo.shop.overview.asset.RevenueProfileVo;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.min;
import static org.jooq.impl.DSL.sum;

/**
 * @author liufei
 * @date 2019/8/2
 * @description 资产管理
 */
@Service
public class AssetManagementService extends ShopBaseService {
    private static final BigDecimal ZERO = BigDecimal.valueOf(0.00);
    private static TradesRecordSummary trs = TradesRecordSummary.TRADES_RECORD_SUMMARY.as("trs");

    /**
     * 营收概况
     * @param param 入参
     * @return 统计数据和折线图数据列表
     */
    public RevenueProfileVo revenueprofile(RevenueProfileParam param) {
        byte screeningTime = param.getScreeningTime();
        RevenueProfileVo vo;
        if (screeningTime > 0) {
            Date current = Util.getEarlySqlDate(new java.util.Date(), 0);
            Date prior = Util.getEarlySqlDate(new java.util.Date(), -screeningTime);
            vo = getRevenueDate(current, screeningTime);
            RevenueProfileVo tempPre = getRevenueDate(prior, screeningTime);
            calGrowthRate(vo, tempPre);
            vo.setRevenueDates(getRevenueDateList(prior, current));
        } else {
            Date startDate = Optional.of(param.getStartTime()).orElse(Util.getEarlySqlDate(new java.util.Date(), -1));
            Date endDate = Optional.of(param.getEndTime()).orElse(Util.getEarlySqlDate(new java.util.Date(), 0));
            int day = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
            vo = getRevenueDate(startDate, endDate);
            RevenueProfileVo tempPre = getRevenueDate(Util.getEarlySqlDate(startDate, -day), startDate);
            calGrowthRate(vo, tempPre);
            vo.setRevenueDates(getRevenueDateList(startDate, endDate));
        }
        return vo;
    }

    /**
     * 计算增长率
     */
    private void calGrowthRate(RevenueProfileVo vo, RevenueProfileVo tempPre) {
        vo.setIncomeRealMoneyPer(getRate(vo.getIncomeRealMoney(), tempPre.getIncomeRealMoney()));
        vo.setIncomeTotalMoneyPer(getRate(vo.getIncomeTotalMoney(), tempPre.getIncomeTotalMoney()));
        vo.setOutgoMoneyPer(getRate(vo.getOutgoMoney(), tempPre.getOutgoMoney()));
        vo.setIncomeRealScorePer(getRate(vo.getIncomeRealScore(), tempPre.getIncomeRealScore()));
        vo.setIncomeTotalScorePer(getRate(vo.getIncomeTotalScore(), tempPre.getIncomeTotalScore()));
        vo.setOutgoScorePer(getRate(vo.getOutgoScore(), tempPre.getOutgoScore()));
    }

    private BigDecimal getRate(BigDecimal vo, BigDecimal tempPre) {
        if (vo.subtract(tempPre).compareTo(ZERO)==0 || tempPre.compareTo(ZERO)==0){
            return ZERO;
        }
        return vo.subtract(tempPre).divide(tempPre,2, RoundingMode.HALF_UP);
    }

    /**
     * 获取指定时间段数据
     */
    private RevenueProfileVo getRevenueDate(Date date, byte type) {
        return getSelectConditon().and(trs.REF_DATE.eq(date)).and(trs.TYPE.eq(type)).fetchOptionalInto(RevenueProfileVo.class).orElse(new RevenueProfileVo());
    }

    /**
     * 获取自定义时间数据
     */
    private RevenueProfileVo getRevenueDate(Date startDate, Date endDate) {
        return getSelectConditonWithSum().and(trs.REF_DATE.greaterOrEqual(startDate)).and(trs.REF_DATE.lessThan(endDate)).and(trs.TYPE.eq((byte) 1)).fetchOptionalInto(RevenueProfileVo.class).orElse(new RevenueProfileVo());
    }

    /**
     * 获取折线图数据
     */
    private List<RevenueDate> getRevenueDateList(Date startDate, Date endDate) {
        return getSelectConditon().and(trs.REF_DATE.greaterOrEqual(startDate)).and(trs.REF_DATE.lessThan(endDate)).and(trs.TYPE.eq((byte) 1)).fetchInto(RevenueDate.class);
    }

    private SelectConditionStep<Record7<Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal>> getSelectConditon() {
        return db().select(trs.REF_DATE, trs.INCOME_REAL_MONEY, trs.INCOME_TOTAL_MONEY, trs.OUTGO_MONEY, trs.INCOME_REAL_SCORE, trs.INCOME_TOTAL_SCORE, trs.OUTGO_SCORE).from(trs).where();
    }

    private SelectConditionStep<Record7<Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal>> getSelectConditonWithSum() {
        return db().select(min(trs.REF_DATE).as("refDate")
            , sum(trs.INCOME_REAL_MONEY).as("incomeRealMoney")
            , sum(trs.INCOME_TOTAL_MONEY).as("incomeTotalMoney")
            , sum(trs.OUTGO_MONEY).as("outgoMoney")
            , sum(trs.INCOME_REAL_SCORE).as("incomeRealScore")
            , sum(trs.INCOME_TOTAL_SCORE).as("incomeTotalScore")
            , sum(trs.OUTGO_SCORE).as("outgoScore")).from(trs).where();
    }
}
