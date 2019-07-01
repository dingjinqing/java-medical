package com.vpu.mp.service.pojo.saas.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemTokenAuthInfo {
	public Integer systemUserId = 0;
	public String userName = "";
	public Integer subAccountId = 0;
	public String subUserName = "";
	public boolean isSubLogin = false;
	public String token = "";
}
