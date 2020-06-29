package com.vpu.jmd.service.shop.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 传送的url请求
 * @author zhaojianqiang
 *
 * 2019年11月20日 上午11:17:50
 */
@Data
public class VersionPath {
	private String path;
	@JsonProperty(value = "V-EnName")
	private String eName;
	@JsonProperty(value = "V-VsName")
	private String vName;
}
