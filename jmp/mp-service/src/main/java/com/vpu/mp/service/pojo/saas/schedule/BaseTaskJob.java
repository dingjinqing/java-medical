package com.vpu.mp.service.pojo.saas.schedule;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * 基类
 * @author 卢光耀
 * @date 2019-08-14 14:52
 *
*/
@Getter
public class BaseTaskJob  {
    /** 店铺ID */
    protected Integer shopId;

    /** MQ消息内容 */
    protected String content ;

    /** 任务状态：0待执行,1执行中,2已完成 */
    protected Byte status = TaskJobsConstant.STATUS_NEW;

    /** 任务进度：0-100 */
    protected Byte progress = 0;

    /** 执行周期id */
    protected Integer executionType ;

    /** task任务类型(任务标识) */
    protected Byte type;

    /** 周期开始日期 */
    protected Timestamp startTime = Timestamp.valueOf("0000-00-00 00:00:00");

    /** 周期结束日期 */
    protected Timestamp endTime = Timestamp.valueOf("0000-00-00 00:00:00");

    /** 轮循间隔(单位:秒) */
    protected Integer cycle = 60*60*3;

    /** 下次执行时间 */
    protected Timestamp nextExecuteTime;

    /** 反序列化对应的类全称 */
    protected String className;

}
