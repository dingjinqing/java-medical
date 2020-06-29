package com.vpu.jmd.service.shop.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.vpu.jmd.service.shop.bo.version.VersionMainConfig;
import com.vpu.jmd.service.shop.bo.version.VersionNumberConfig;
import lombok.Data;

/**
 *
 * @author zhaojianqiang
 *
 */
@Data
public class VersionEditParam {
	@JsonProperty(value = "main_config")
	public VersionMainConfig mainConfig = new VersionMainConfig();

	@JsonProperty(value = "num_config")
	public VersionNumberConfig numConfig = new VersionNumberConfig();

	public Integer shopId;
}
