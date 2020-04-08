package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @Author 常乐
 * @Date 2020-04-07
 */
@Data
public class GoodsRebateConfigParam {
    private ArrayList<PrdInfo> rebatePrice;
    private Long rebateTime;

    @Data
    public static class PrdInfo {
        private Integer prdId;
        private BigDecimal prdPrice;
    }
}
