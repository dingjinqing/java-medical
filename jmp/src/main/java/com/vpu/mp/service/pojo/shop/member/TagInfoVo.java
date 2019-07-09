package com.vpu.mp.service.pojo.shop.member;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @author 黄壮壮 2019-07-09 20:14
 */
@Data
public class TagInfoVo {
	/**
	 * 标签名
	 */
	private String tagName;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 用户数
	 */
	private Integer count = 0;
}
