package com.vpu.mp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Configuration
@Data
public class UpYunConfig {

	@Value(value = "${uyun.image.sv}")
	protected String server;

	@Value(value = "${uyun.image.op.name}")
	protected String name;

	@Value(value = "${uyun.image.op.pwd}")
	protected String password;

}
