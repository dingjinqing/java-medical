package com.vpu.mp.service.pojo.shop.market.coopen;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 *  开屏有礼 列表
 * @author 孔德成
 * @date 2019/11/22 14:23
 */
@Getter
@Setter
public class CoopenListVo {

    private Integer id;


    /** 活动名称 **/
    private String name;
    /** 触发条件 **/
    private Byte action;
    /** 活动类型 **/
    private Byte activityAction;
    private Integer first;
    /**
     * 是否永久有效
     */
    private Integer    isForever;
    private Timestamp startDate;
    private Timestamp endDate;
    private Byte status;
}
