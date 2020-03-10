package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActivityVo {
	public Integer id;
	public String actName;
	public String type;
<<<<<<< HEAD
	public String actCode;
=======
    /**
     * 优惠券有效期类型
     */
    private Byte    validityType;
    /**
     * 优惠券有效天数
     */
    private Integer    validity;
    /**
     * 优惠券有效小时
     */
    private Integer validityHour;
    /**
     * 优惠券有效分钟数
     */
    private Integer validityMinute;
>>>>>>> fd6040fcd... 优惠券链接 领取日期起有效期判断优化
	public Timestamp startTime;
	public Timestamp endTime;
}
