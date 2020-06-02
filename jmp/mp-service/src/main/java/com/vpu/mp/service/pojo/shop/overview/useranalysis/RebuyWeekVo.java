package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

/**
 * 客户复购趋势
 * @author liangchen
 * @date 2019年7月22日
 */
@Data
public class RebuyWeekVo {
    /** 老成交用户数 */
	private Integer oldOrderUserData;
    /** 成交 */
	private Integer orderUserData;
    /** 当前周 */
    private Integer weekNum;
    /** 复购率 */
    private Double rebuyRate;
    /** 开始时间 */
    private String startTime;
    /** 结束时间 */
    private String endTime;
    private String XAxis;
}
