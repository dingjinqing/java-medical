package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.db.shop.tables.records.TradesRecord;
import com.vpu.mp.db.shop.tables.records.TradesRecordSummaryRecord;
import com.vpu.mp.db.shop.tables.records.UserRfmSummaryRecord;
import com.vpu.mp.db.shop.tables.records.UserSummaryTrendRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductOverviewParam;
import org.jooq.Record3;
import org.jooq.lambda.tuple.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.tables.DistributionTag.DISTRIBUTION_TAG;
import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.TYPE_LIST;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * The type Statistical table insert.
 *
 * @author liufei
 * @date 12 /12/19
 */
@Service
public class StatisticalTableInsert extends ShopBaseService {
    /**
     * The User summary.
     */
    @Autowired
    UserSummaryTaskService userSummary;

    /**
     * The Goods statistic.
     */
    @Autowired
    GoodsStatisticTaskService goodsStatistic;

    /**
     * The Trade task service.
     */
    @Autowired
    TradeTaskService tradeTaskService;

    /**
     * Insert trades. b2c_trades表，每小时（整点）统计一次数据插入一条记录（统计历史数据，昨天数据）
     */
    public void insertTrades() {
        LocalDateTime yesterdayEndTime = LocalDate.now().atStartOfDay();
        LocalDateTime yesterdayStartTime = LocalDate.now().minusDays(INTEGER_ONE).atStartOfDay();
        byte hour = BYTE_ZERO;
        ProductOverviewParam param = new ProductOverviewParam();
        Timestamp start;
        Timestamp end;
        TradesRecord record = new TradesRecord();
        Date refDate = Date.valueOf(LocalDate.now().minusDays(INTEGER_ONE));
        for (LocalDateTime i = yesterdayStartTime; i.isBefore(yesterdayEndTime); i = i.plusHours(INTEGER_ONE)) {
            start = Timestamp.valueOf(i);
            end = Timestamp.valueOf(i.plusHours(INTEGER_ONE));
            createTradesRecord(param, start, end, record, hour++, refDate);
            db().executeInsert(record);
        }
    }

    private ProductOverviewParam createParam(ProductOverviewParam param, Timestamp start, Timestamp end) {
        param.clear();
        param.setStartTime(start);
        param.setEndTime(end);
        return param;
    }

    private TradesRecord createTradesRecord(ProductOverviewParam param, Timestamp start, Timestamp end, TradesRecord record, byte hour, Date refDate) {
        record.reset();
        record.setUv(userSummary.getUv(start, end));
        record.setPv(userSummary.getPv(start, end));
        record.setPayUserNum(goodsStatistic.paidUv(createParam(param, start, end)));
        record.setPayOrderMoney(goodsStatistic.orderUserMoney(start, end));
        record.setPayOrderNum(userSummary.orderNum(start, end));
        record.setPct(BigDecimalUtil.divideWithOutCheck(record.getPayOrderMoney(), record.getPayUserNum()));
        record.setUvPayRatio(BigDecimalUtil.divideWithOutCheck(record.getPayUserNum(), record.getUv()));
        record.setHour(hour);
        record.setRefDate(refDate);
        return record;
    }

    /**
     * Insert trades now. b2c_trades表，实时统计当天的数据
     */
    public void insertTradesNow() {
        Date refDate = Date.valueOf(LocalDate.now());
        LocalDateTime today = LocalDate.now().atStartOfDay();
        byte hour = (byte) LocalDateTime.now().getHour();
        Timestamp start = Timestamp.valueOf(today.plusHours(hour - 1));
        Timestamp end = Timestamp.valueOf(today.plusHours(hour));
        TradesRecord record = new TradesRecord();
        ProductOverviewParam param = new ProductOverviewParam();
        if (hour - 1 < INTEGER_ZERO) {
            refDate = Date.valueOf(LocalDate.now().minusDays(INTEGER_ZERO));
            hour = 23;
        } else {
            --hour;
        }
        createTradesRecord(param, start, end, record, hour, refDate);
        db().executeInsert(record);
    }

    /**
     * Insert user summary trend. b2c_user_summary_trend
     */
    public void insertUserSummaryTrend() {
        ProductOverviewParam param = new ProductOverviewParam();
        UserSummaryTrendRecord record = new UserSummaryTrendRecord();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        Timestamp end = Timestamp.valueOf(today);
        TYPE_LIST.forEach((e) ->
            db().executeInsert(createUserSummaryTrendRecord(Timestamp.valueOf(today.minusDays(e)), end, e, record, param)
            ));
    }

    private UserSummaryTrendRecord createUserSummaryTrendRecord(Timestamp start, Timestamp end, byte type, UserSummaryTrendRecord record, ProductOverviewParam param) {
        record.reset();
        record.setRefDate(Date.valueOf(LocalDate.now()));
        record.setType(type);
        record.setLoginData(userSummary.getUv(start, end));
        record.setUserData(userSummary.getUserTotal(start, end));
        record.setCouponData(userSummary.receiveCouponUserNum(start, end));
        record.setCartData(goodsStatistic.addCartUserNum(createParam(param, start, end)));
        record.setRegUserData(userSummary.getUserNum(start, end));
        record.setUpgradeUserData(userSummary.upgradeUserSum(start, end));
        record.setChargeUserData(userSummary.chargeUserSum(start, end));
        record.setOrderUserData(goodsStatistic.totalCustomerTranNum(start, end).size());
        record.setNewOrderUserData(goodsStatistic.newCustomerTranNum(start, end));
        record.setOldOrderUserData(goodsStatistic.oldCustomerTranNum(start, end));
        record.setTotalPaidMoney(goodsStatistic.orderUserMoney(start, end));
        record.setNewPayGoodsNumber(userSummary.payNewUserGoodsNum(start, end));
        record.setOldPayGoodsNumber(userSummary.payOldUserGoodsNum(start, end));
        record.setNewPaidMoney(userSummary.payNewUserMoney(start, end));
        record.setOldPaidMoney(userSummary.payNewUserMoney(start, end));
        record.setPayOrderNum(userSummary.orderNum(start, end));
        record.setLoginPv(userSummary.getPv(start, end));
        record.setOrderNum(userSummary.generateOrderNum(start, end));
        record.setOrderUserNum(userSummary.generateOrderUserNum(start, end));
        return record;
    }

    /**
     * Insert user rfm summary.
     */
    public void insertUserRfmSummary() {
        userSummary.getRecencyType(LocalDate.now().atStartOfDay()).forEach((k, v) -> {
            Map<Integer, Record3<Integer, Integer, BigDecimal>> rfmData = userSummary.getRFMData(v.v1(), v.v2());
            Stream.of(1, 2, 3, 4).forEach((n) -> {
                Tuple3<Integer, Integer, BigDecimal> tuple3 = userSummary.reduceRfmData(rfmData, (e) -> e.equals(n));
                UserRfmSummaryRecord record = createUserRfmSummary(tuple3, k, n);
                db().executeInsert(record);
            });
        });
    }

    private UserRfmSummaryRecord createUserRfmSummary(Tuple3<Integer, Integer, BigDecimal> tuple3, int recencyType, int frequencyType) {
        return new UserRfmSummaryRecord() {{
            setRefDate(Date.valueOf(LocalDate.now()));
            setRecencyType((byte) recencyType);
            setFrequencyType((byte) frequencyType);
            setPayUserNum(tuple3.v1());
            setOrderNum(tuple3.v2());
            setTotalPaidMoney(tuple3.v3());
        }};
    }

    /**
     * Insert distribution tag.
     */
    public void insertDistributionTag() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        TYPE_LIST.forEach((e) -> {
            userSummary.getTagData(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)).forEach((k, v) -> {
                db().insertInto(DISTRIBUTION_TAG,
                    DISTRIBUTION_TAG.REF_DATE
                    , DISTRIBUTION_TAG.TYPE
                    , DISTRIBUTION_TAG.TAG_ID
                    , DISTRIBUTION_TAG.TAG_NAME
                    , DISTRIBUTION_TAG.PAY_USER_NUM
                    , DISTRIBUTION_TAG.PAY_ORDER_NUM
                    , DISTRIBUTION_TAG.PAY_ORDER_MONEY
                    , DISTRIBUTION_TAG.PAY_GOODS_NUMBER)
                    .values(Date.valueOf(today.toLocalDate()), e, k, v.value2(), v.value3(), v.value4(), v.value5(), v.value6())
                    .onDuplicateKeyUpdate()
                    .set(DISTRIBUTION_TAG.PAY_USER_NUM, v.value3())
                    .set(DISTRIBUTION_TAG.PAY_ORDER_NUM, v.value4())
                    .set(DISTRIBUTION_TAG.PAY_ORDER_MONEY, v.value5())
                    .set(DISTRIBUTION_TAG.PAY_GOODS_NUMBER, v.value6())
                    .execute();
            });
            userSummary.tagUserHasMobileNum(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)).forEach((k, v) -> {
                db().insertInto(DISTRIBUTION_TAG,
                    DISTRIBUTION_TAG.REF_DATE
                    , DISTRIBUTION_TAG.TYPE
                    , DISTRIBUTION_TAG.TAG_ID
                    , DISTRIBUTION_TAG.TAG_NAME
                    , DISTRIBUTION_TAG.HAS_MOBILE_NUM)
                    .values(Date.valueOf(today.toLocalDate()), e, k, v.value2(), v.value3())
                    .onDuplicateKeyUpdate()
                    .set(DISTRIBUTION_TAG.HAS_MOBILE_NUM, v.value3())
                    .execute();
            });
            userSummary.tagUserNum(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)).forEach((k, v) -> {
                db().insertInto(DISTRIBUTION_TAG,
                    DISTRIBUTION_TAG.REF_DATE
                    , DISTRIBUTION_TAG.TYPE
                    , DISTRIBUTION_TAG.TAG_ID
                    , DISTRIBUTION_TAG.TAG_NAME
                    , DISTRIBUTION_TAG.HAS_USER_NUM)
                    .values(Date.valueOf(today.toLocalDate()), e, k, v.value2(), v.value3())
                    .onDuplicateKeyUpdate()
                    .set(DISTRIBUTION_TAG.HAS_USER_NUM, v.value3())
                    .execute();
            });
        });
    }

    /**
     * Insert trades record summary.
     */
    public void insertTradesRecordSummary() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        TradesRecordSummaryRecord record = new TradesRecordSummaryRecord();
        TYPE_LIST.forEach((e) -> {
            record.reset();
            record.setRefDate(Date.valueOf(today.toLocalDate()));
            record.setType(e);
            record.setIncomeTotalMoney(tradeTaskService.getTotalIncomeMoney(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)));
            record.setOutgoMoney(tradeTaskService.getTotalExpensesMoney(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)));
            record.setIncomeRealMoney(record.getIncomeTotalMoney().subtract(record.getOutgoMoney()));
            record.setIncomeTotalScore(tradeTaskService.getTotalIncomeScore(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)));
            record.setOutgoScore(tradeTaskService.getTotalExpensesScore(Timestamp.valueOf(today.minusDays(e)), Timestamp.valueOf(today)));
            record.setIncomeRealScore(record.getIncomeTotalScore().subtract(record.getOutgoScore()));
            db().executeInsert(record);
        });
    }
}
