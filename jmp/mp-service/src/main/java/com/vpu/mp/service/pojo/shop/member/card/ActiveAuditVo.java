package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.I18N;

import lombok.Data;
/**
* @author 黄壮壮
* @Date: 2019年9月27日
* @Description: 激活审核-出参
*/
@Data
public class ActiveAuditVo {
	// ID
	private Integer id;
	// 真实姓名
	private String realName;
	// 状态
	private Byte status;
	// 身份证号
	private String cid;
	// 受教育程度 - 状态信号
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte education;
	// 受教育程度 - 字符串
	@I18N(propertiesFileName = "member")
	@JsonProperty("education")
	private String educationStr;
	// 行业 - 状态信号
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte industryInfo;
	// 行业 名称
	@I18N(propertiesFileName = "member")
	private String industry;
	// 手机号
	private String mobile;
	// 用户昵称
	private String username;
	// 申请时间
	private Timestamp createTime;
	
}
