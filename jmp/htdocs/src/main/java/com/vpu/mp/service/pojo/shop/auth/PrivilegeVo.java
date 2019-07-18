package com.vpu.mp.service.pojo.shop.auth;

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
	
}
