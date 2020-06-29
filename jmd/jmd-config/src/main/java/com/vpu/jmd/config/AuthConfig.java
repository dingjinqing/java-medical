package com.vpu.jmd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author lixinguo
 *
 */
@Configuration
@Data
public class AuthConfig {

	@Value(value = "${auth.secret}")
	protected String secret;

	@Value(value = "${auth.timeout}")
	protected Integer timeout;
}
