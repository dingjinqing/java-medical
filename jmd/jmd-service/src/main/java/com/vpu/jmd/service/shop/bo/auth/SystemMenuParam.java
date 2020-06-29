package com.vpu.jmd.service.shop.bo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月18日上午11:11:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemMenuParam {
	public String enName;
	public String name;
	public String linkUrl;
	public Integer topIndex;
	private Byte isChecked;
	public List<SystemMenuInner> includeApi;
}
