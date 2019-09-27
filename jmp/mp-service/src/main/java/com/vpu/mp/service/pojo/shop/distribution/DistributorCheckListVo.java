package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 分销员审核列表出参类
 * @author 常乐
 * 2019年9月20日
 */
@Data
public class DistributorCheckListVo {

	private Integer id;
	private Integer userId;
	private Integer status;
	private String msg;
	private Integer delFlag;
	private String activationFields;
	private Timestamp createTime;
	private String username;
	private String mobile;
	private Integer isDistributor;
	private Integer inviteId;
	private String realName;
}
