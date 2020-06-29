package com.vpu.jmd.service.shop.bo.auth;

import lombok.Data;

import java.util.List;

@Data
public class SystemMenuInner {
	public String enName;
	public String name;
	public String linkUrl;
	public Integer topIndex;
	private Byte isChecked;
	public List<String> includeApi;
}
