package com.vpu.mp.service.pojo.shop.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminTokenAuthInfo {
	public Integer sysId = 0;
	public String userName = "";
	public Integer subAccountId = 0;
	public String subUserName = "";
	public boolean isSubLogin = false;
	public Integer loginShopId = 0;
	public boolean isShopLogin = false;
	public String token = "";
}
