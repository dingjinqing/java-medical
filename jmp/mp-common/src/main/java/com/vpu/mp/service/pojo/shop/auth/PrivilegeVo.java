package com.vpu.mp.service.pojo.shop.auth;

import com.vpu.mp.service.pojo.saas.shop.version.VersionMainConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class PrivilegeVo {
	private MenuParam menuParam;
	private PrivilegeAndPassParam passParam;
	private VersionMainConfig vMainConfig;
	
}
