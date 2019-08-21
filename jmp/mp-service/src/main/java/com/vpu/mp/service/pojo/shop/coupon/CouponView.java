package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 18:04
 **/
@Data
public class CouponView {

    private Integer id;

    /** 优惠券名称 */
    private String actName;

    /** 面额 */
    private BigDecimal denomination;

    /** 是否有最低使用限制 0没有，1有 */
    private Byte useConsumeRestrict;

    /** 最低使用限制，满多少可用 */
    private BigDecimal leastConsume;

    /** 剩余数量 */
    private Integer surplus;

    /** 优惠券有效期类型标记，1领取后开始指定时间段内有效，0固定时间段有效 */
    private Byte validityType;

    /** 0类型有效期开始时间 */
    private Timestamp startTime;

    /** 0类型有效期结束时间 */
    private Timestamp endTime;

    /** 1类型有效天数 */
    private Integer validity;

    /** 1类型有效小时数 */
    private Integer validityHour;

    /** 1类型有效分钟数 */
    private Integer validityMinute;
}
