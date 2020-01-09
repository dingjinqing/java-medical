package com.vpu.mp.service.pojo.shop.market.groupdraw.analysis;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 拼团抽奖数据效果
 * @author liangchen
 * @date 2020.01.06
 */
@Data
public class GroupDrawAnalysisVo {
    /** 开始时间 */
    private Timestamp startTime;
    /** 结束时间 */
    private Timestamp endTime;
    /** 付款订单数 */
    private Map<String,Integer> orderNumber;
    private Integer totalOrderNumber = 0;
    /** 拉新用户数 */
    private Map<String,Integer> newUser;
    private Integer totalNewUser = 0;
    /** 参与用户数 */
    private Map<String,Integer> joinNum;
    private Integer totalJoinNum = 0;
    /** 成团用户数 */
    private Map<String,Integer> successUserNum;
    private Integer totalSuccessUserNum = 0;

}
