package com.vpu.mp.service.pojo.shop.market.fullcut;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2019-08-12 15:06
 **/
@Data
public class MrkingStrategyPageListQueryVo {

    /** 活动主键 */
    private Integer id;

    /** 活动名称 */
    private String actName;

    /** 类型,1每满减 2满减 3满折 4仅第X件打折 */
    private Byte type;

    /** 优惠规则 */
    private List<MrkingStrategyCondition> condition;

    /** 开始时间 */
    private Timestamp startTime;

    /** 结束时间 */
    private Timestamp endTime;

    /** 活动状态：0停用，1启用 */
    private Byte status;


}
