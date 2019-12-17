package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.TradesRecord.TRADES_RECORD;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * The type Trade task service.
 *
 * @author liufei
 * @date 12 /16/19
 */
@Service
public class TradeTaskService extends ShopBaseService {

    /**
     * Gets total income money.总现金资产收入
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the total income money
     */
    public BigDecimal getTotalIncomeMoney(Timestamp startTime, Timestamp endTime) {
        return commonOperator(startTime, endTime, BYTE_ZERO, BYTE_ZERO);
    }

    /**
     * Gets total expenses money.总现金资产支出
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the total expenses money
     */
    public BigDecimal getTotalExpensesMoney(Timestamp startTime, Timestamp endTime) {
        return commonOperator(startTime, endTime, BYTE_ZERO, BYTE_ONE);
    }

    /**
     * Gets total income score.总积分资产收入
     *
     * @param startTime the start time
     * @param endTime   the end time
     */
    public BigDecimal getTotalIncomeScore(Timestamp startTime, Timestamp endTime) {
        return commonOperator(startTime, endTime, BYTE_ONE, BYTE_ZERO);
    }

    /**
     * Gets total expenses score.总积分资产支出
     *
     * @param startTime the start time
     * @param endTime   the end time
     */
    public BigDecimal getTotalExpensesScore(Timestamp startTime, Timestamp endTime) {
        return commonOperator(startTime, endTime, BYTE_ONE, BYTE_ONE);
    }

    private BigDecimal commonOperator(Timestamp startTime, Timestamp endTime, Byte content, Byte flow) {
        return db().select(DSL.sum(TRADES_RECORD.TRADE_NUM)).from(TRADES_RECORD)
            .where(TRADES_RECORD.CREATE_TIME.ge(startTime).and(TRADES_RECORD.CREATE_TIME.lessThan(endTime)))
            .and(TRADES_RECORD.TRADE_CONTENT.eq(content))
            .and(TRADES_RECORD.TRADE_FLOW.eq(flow))
            .fetchOptionalInto(BigDecimal.class).orElse(BIGDECIMAL_ZERO);
    }

}
