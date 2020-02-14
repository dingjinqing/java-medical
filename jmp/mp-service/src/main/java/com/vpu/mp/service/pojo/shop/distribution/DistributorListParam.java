package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员列表入参
 * @author 常乐
 * 2019年8月5日
 */
@Data
public class DistributorListParam {
    /**
     *分销员手机号
     */
	private String mobile;
    /**
     * 分销员微信昵称
     */
	private String username;
    /**
     * 真实姓名
     */
	private String realName;
    /**
     * 被邀请人手机号
     */
	private String invitedMobile;
    /**
     * 被邀请人姓名
     */
	private String invitedUserName;
    /**
     * 注册开始时间
     */
	private Timestamp startCreateTime;
    /**
     * 注册结束时间
     */
	private Timestamp endCreateTime;
    /**
     * 分销等级
     */
	private Byte distributorLevel;
    /**
     * 分销分组
     */
	private Integer distributorGroup;
    /**
     * 有下级用户 0：无；1：有；
     */
	private Byte haveNextUser;
    /**
     * 有手机号 0：无；1：有；
     */
	private Byte haveMobile;
    /**
     * 有真实姓名 0：无；1：有；
     */
	private Byte haveRealName;
	
	private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
	private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
