package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 商城概览-数据展示-出参
 */
@Data
@Component
public class DataDemonstrationVo {
    /** 用户访问数 */
    private int userVisitNum;
    /** 支付订单数 */
    private int paidOrderNum;
    /** 下单用户数 */
    private int orderUserNum;
    /** 下单数 */
    private int orderNum;
    /** 总支付金额 */
    private int totalPaidSum;
    /** 访问下单转化率 */
    private int paidUserNum;
    /** 访问下单转化率 */
    private double uv2order;
    /** 访问支付转化率*/
    private double uv2paid;
    /** 下单支付转化率*/
    private double order2paid;
}
