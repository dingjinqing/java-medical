package com.vpu.mp.service.pojo.shop.member.card.export.examine;

import java.sql.Timestamp;

/**
 * 	会员卡审核数据下载对象
 * @author 黄壮壮
 *
 */
public class CardExamineDownVo {
	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 	昵称
	 */
	private String username;
	
	/**
	 *	 申请时间
	 */
	private Timestamp createTime;
	
	/**
	 * 	真实姓名
	 */
	private String realname;
	
	/**
	 * 	手机号
	 */
	private String mobile;
	
	/**
	 * 	身份证号
	 */
	private String cid;
	
	/**
	 * 	性别
	 */
	private String sex;
	
	/**
	 * 	生日
	 */
	private String birthday;
	
	
	/**
	 * 	婚姻状况
	 */
	private String maritalStatus;
	
	/**
	 * 	教育程度
	 */
	private String education;
	
	/**
	 * 
	 */
	
}
