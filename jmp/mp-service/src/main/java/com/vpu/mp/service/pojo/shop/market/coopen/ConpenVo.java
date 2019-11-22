package com.vpu.mp.service.pojo.shop.market.coopen;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 活动有礼 详情
 * @author 孔德成
 * @date 2019/11/22 15:02
 */
@Getter
@Setter
public class ConpenVo {


    private Integer    id;
    private Byte       action;
    private String     name;
    private String     title;
    private Byte       bgAction;
    private Integer    isForever;
    private Timestamp  startDate;
    private Timestamp  endDate;
    private Integer    first;
    private Byte       activityAction;
    private String     mrkingVoucherId;
    private Integer    lotteryId;
    private String     customizeImgPath;
    private String     customizeUrl;
    private BigDecimal giveScore;
    private BigDecimal giveAccount;
    private Integer    awardNum;
    private Byte       status;
    private Timestamp  createTime;
    private Timestamp  updateTime;



}
