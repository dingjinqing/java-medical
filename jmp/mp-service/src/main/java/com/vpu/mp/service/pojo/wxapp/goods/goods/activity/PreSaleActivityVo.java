package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年10月28日
 * 小程序-定金膨胀返回信息类 activityType=10，
 * {@link ActivityBaseVo#startTime} 尾款付款日开始日期
 * {@link ActivityBaseVo#endTime} 尾款付款日结束日期
 */
public class PreSaleActivityVo extends ActivityBaseVo{
    public PreSaleActivityVo() {
        activityType = GoodsConstant.ACTIVITY_TYPE_PRE_SALE;
    }
    /**活动最高价*/
    @Getter
    @Setter
    private BigDecimal activityMaxPrice;
    /**活动最低价*/
    @Getter
    @Setter
    private BigDecimal activityMinPrice;

    /**退定金模式1:自动退定金0:不退定金*/
    @Getter
    @Setter
    private Byte returnType;

    /**发货时间模式1:deliver_time；2:deliver_days*/
    @Getter
    @Setter
    private Byte deliverType;

    /**当deliverType=1时，发货日期*/
    @Getter
    @Setter
    private Timestamp deliverTime;

    /**当deliverType=0时，发货间隔天数*/
    @Getter
    @Setter
    private Integer deliverDays;

    /**当前活动结束时间*/
    @Getter
    @Setter
    private Timestamp currentActivityEndDate;
}
