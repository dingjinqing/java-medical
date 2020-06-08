package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author 常乐
 * @Date 2020-04-07
 */
@Data
public class GoodsRebateConfigParam {
    private Map<Integer,BigDecimal> rebatePrice;
    private Long rebateTime;
}
