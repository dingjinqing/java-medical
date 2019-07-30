package com.vpu.mp.service.pojo.shop.config;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月3日
 */
@Data
@NoArgsConstructor
public class ShopBaseConfig {
	public Timestamp expireTime = null;
	public String shopName = "";
	public String shopAvatar = "";
	public Timestamp created = null;
	public Byte businessState = 0;
}
