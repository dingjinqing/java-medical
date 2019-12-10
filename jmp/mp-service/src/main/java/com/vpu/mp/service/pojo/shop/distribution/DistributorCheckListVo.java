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
    /**
     * 用户ID
     */
	private Integer userId;
    /**
     * 审核状态 0：审核中；1：审核功过；2：未通过
     */
	private Integer status;
    /**
     *
     */
	private String msg;
	private Integer delFlag;
	private String activationFields;
	private Timestamp createTime;
    /**
     * 用户名
     */
	private String username;
    /**
     * 手机号
     */
	private String mobile;

	private Integer inviteId;
    /**
     * 真实姓名
     */
	private String realName;
    /**
     * 性别 f:女 m：男
     */
	private String sex;
    /**
     * 身份证号
     */
	private String cid;
    /**
     * 出生年
     */
	private Integer birthdayYear;
    /**
     * 出生月
     */
	private Integer birthdayMonth;
    /**
     * 出生日
     */
	private Integer birthdayDay;
    /**
     * 婚姻状况 1：未婚；2：已婚
     */
	private Byte maritalStatus;
    /**
     * 教育程度
     */
	private Byte education;
    /**
     * 所在行业
     */
	private Byte industryInfo;
    /**
     * 所在地
     */
	private String address;
    /**
     * 行业名称
     */
	private String industryName;
    /**
     * 教育程度名称
     */
	private String educationName;

}
