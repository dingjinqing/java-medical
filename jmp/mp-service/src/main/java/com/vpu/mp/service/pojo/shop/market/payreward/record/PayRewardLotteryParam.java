package com.vpu.mp.service.pojo.shop.market.payreward.record;

import lombok.Data;

/**
 * 抽奖记录
 * @author 孔德成
 * @date 2019/8/14 13:36
 */
@Data
public class PayRewardLotteryParam {

    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows ;

    /**
     * 用户电话
     */
    private String mobile;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 中奖等级  0未中奖，1一等奖，2二等奖，3三等奖，4四等奖
     */
    private Byte lotteryGrade;
    /**
     * 抽奖来源 1.登录2.支付
     */
    private Byte lotterySource;
    /**|
     * 来源id
     */
    private Integer actId;

}
