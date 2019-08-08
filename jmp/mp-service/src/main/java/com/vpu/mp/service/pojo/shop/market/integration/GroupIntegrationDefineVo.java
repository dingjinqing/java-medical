package com.vpu.mp.service.pojo.shop.market.integration;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月5日
 * 瓜分积分活动分页查视图
 */
@Data
@NoArgsConstructor
public class GroupIntegrationDefineVo {
	/** 活动ID*/
	private Integer id;
	 /** 活动名称 */
	private String name;
	 /** 总抽奖积分 */
	private Integer inteTotal ;
	 /** 每一个团的总积分 */
	private Integer inteGroup ;
	/** 成团人数*/
	private Short limitAmount ;
	 /** 参团限制 */
	private Short joinLimit ;
	/** 瓜分方式 0：按邀请好友数量瓜分，1：好友均分，2：随机瓜分 */
	private Byte divideType;
	/** 开始时间*/
	private Timestamp startTime;
	 /** 结束时间 */
	private Timestamp endTime;
	 /** 状态： 1：启用  0： 禁用 */
	private Byte status ;
	/** 剩余积分 */
	private Integer inteRemain;
	 /**  继续： 1：继续  0： 结束 */
	private Byte isContinue;
	 /** 活动宣传语 */
	private String advertise;
	/** 消耗积分 */
	private Integer useIntegration=0;
	/**参与人数 */
	private Integer inteUserSum=0;
	/** 	团数量 */
	private Integer inteGroupSum=0;
	
}

