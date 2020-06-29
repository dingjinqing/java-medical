package com.vpu.jmd.service.shop.bo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月22日上午10:49:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysShowMenu {
	private ShowSysMenuVo menuParam;
	private Boolean subLogin=true;

}
