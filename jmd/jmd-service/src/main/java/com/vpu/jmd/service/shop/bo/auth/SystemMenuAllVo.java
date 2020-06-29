package com.vpu.jmd.service.shop.bo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月18日上午11:18:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMenuAllVo {
	private List<SystemMenuParam> list;
}
