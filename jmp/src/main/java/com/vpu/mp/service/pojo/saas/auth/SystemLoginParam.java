package com.vpu.mp.service.pojo.saas.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemLoginParam {
	public String username;
	public String password;
}
