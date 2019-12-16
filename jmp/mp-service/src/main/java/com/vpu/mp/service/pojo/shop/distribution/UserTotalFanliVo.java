package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserTotalFanliVo {
	private Integer id;
	private Integer userId;
	private String mobile;
	private Integer sublayerNumber;
	private BigDecimal totalMoney;
	private BigDecimal canMoney;
	private BigDecimal blocked;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String username;
	private String userAvatar;
}
