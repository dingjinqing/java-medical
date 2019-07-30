package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月26日
 */
@Data
public class BargainUserListQueryVo {

	private int id;
	private String username;
	private String mobile;
	private Timestamp createTime;
	private BigDecimal bargainMoney;
}
