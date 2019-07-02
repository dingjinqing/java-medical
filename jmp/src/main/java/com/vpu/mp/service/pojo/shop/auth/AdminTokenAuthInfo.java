package com.vpu.mp.service.pojo.shop.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 新国
 *
 */
@Data
@NoArgsConstructor
public class AdminTokenAuthInfo {
	public Integer sysId = 0;
	public String userName = "";
	public Integer subAccountId = 0;
	public String subUserName = "";
	public boolean subLogin = false;
	public Integer loginShopId = 0;
	public boolean shopLogin = false;
	public String token = "";
}
