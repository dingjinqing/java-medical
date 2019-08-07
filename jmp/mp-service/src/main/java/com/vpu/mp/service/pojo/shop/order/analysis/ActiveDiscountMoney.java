package com.vpu.mp.service.pojo.shop.order.analysis;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/5 10:07
 */
@Data
public class ActiveDiscountMoney {

    private Timestamp createTime;

    private BigDecimal marketPrice;

    private BigDecimal goodsPrice;

    private int goodsNumber;

    private BigDecimal discountedTotalPrice;

    private String old;
}
