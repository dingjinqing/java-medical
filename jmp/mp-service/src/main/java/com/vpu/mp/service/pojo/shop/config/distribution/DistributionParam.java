package com.vpu.mp.service.pojo.shop.config.distribution;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分销配置出入参
 * @author 常乐
 * 2019年7月17日
 */
@Data
@NoArgsConstructor
public class DistributionParam {
	/**
	 * 分销开关
	 */
	@JsonProperty(value = "status")
	public Byte status;
	
	/**
	 * 分销员审核开关
	 */
	@JsonProperty(value = "judge_status")
	public Byte judgeStatus;
	
	/**
	 * 分销员排名开关
	 */
	@JsonProperty(value = "rank_status")
	public Byte rankStatus;
	
	/**
	 * 返利有效期
	 */
	@JsonProperty(value = "vaild")
	public Byte vaild;
	
	/**
	 * 分销员保护期
	 */
	@JsonProperty(value = "protect_date")
	public Byte protectDate;
	
	/**
	 * 分销中心页面名称
	 */
	@JsonProperty(value = "desc")
	public Byte desc;
	
	/**
	 * 海报背景图
	 */
	@JsonProperty(value = "bg_img")
	public Byte bgImg;
	
	/**
	 * 邀请文案
	 */
	@JsonProperty(value = "rebate_center_name")
	public Byte rebateCenterName;
	
	/**
	 * 提现开关
	 */
	@JsonProperty(value = "withdraw_status")
	public Byte withdrawStatus;
	
	/**
	 * 返利最小提现金额
	 */
	@JsonProperty(value = "withdraw_cash")
	public Byte withdrawCash;
	
	/**
	 * 返利方式
	 */
	@JsonProperty(value = "withdraw_source")
	public Byte withdrawSource;
	
	/**
	 * 分销员审核开关开启后，是否需要提个人信息
	 */
	@JsonProperty(value = "activation")
	public Byte activation;
	
	/**
	 * 个人信息容
	 */
	@JsonProperty(value = "activation_cfg")
	public Byte activationCfg;
	
}
