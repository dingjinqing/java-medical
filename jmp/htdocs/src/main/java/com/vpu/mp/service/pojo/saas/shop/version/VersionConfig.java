package com.vpu.mp.service.pojo.saas.shop.version;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 新国
 *
 */
public class VersionConfig {
	@JsonProperty(value = "main_config")
	public VersionMainConfig mainConfig = new VersionMainConfig();

	@JsonProperty(value = "num_config")
	public VersionNumberConfig numConfig = new VersionNumberConfig();
}
