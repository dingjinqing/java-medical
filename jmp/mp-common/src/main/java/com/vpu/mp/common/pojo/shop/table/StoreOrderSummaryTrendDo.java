package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.types.UInteger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author chenjie
 * @date 2020年08月27日
 */
@Data
@NoArgsConstructor
public class StoreOrderSummaryTrendDo {
    private Integer    id;
    private Date       refDate;
    private Byte       type;
    private Integer   storeId;
    private Integer    orderPayUserNum;
    private BigDecimal totalPaidMoney;
    private Integer    orderPayNum;
    private Integer    orderNum;
    private Integer    orderUserNum;
    private Timestamp  createTime;
}
