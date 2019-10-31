package com.vpu.mp.service.pojo.shop.market.payaward;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/8/13 14:59
 */
@Getter
@Setter
public class PayAwardListVo {

    private Integer id;
    private String activityName;
    /**
     * 有效期类型 0定期1永久
     */
    private Byte timeType;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte status;
    /**
     * 优先级
     */
    private Integer actFirst;
    /**
     * 奖励类型 0 无奖品 1普通优惠卷  2分裂优惠卷 3幸运大抽奖 4 余额 5 商品 6积分 7 自定义
     */
    private List<Byte> awardTypeList;
    /**
     * 奖励
     */
    private String awardList;
}
