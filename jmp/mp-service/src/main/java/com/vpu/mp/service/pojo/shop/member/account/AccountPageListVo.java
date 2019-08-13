package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年8月13日
* @Description: 分页查询会员用户详细余额信息-出参
*/
@Setter
@Getter
public class AccountPageListVo {
	/** 用户名称 */
	private String username;
	/** 手机号码 */
	private String mobile;
	/** 订单号 */
	private String orderSn;
	/** 添加时间 */
	private Timestamp createTime;
	/** 余额变化 */
	private BigDecimal amount;
	/** 备注 */
	private String remark;
}
