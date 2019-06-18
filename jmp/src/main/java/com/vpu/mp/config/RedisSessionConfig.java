package com.vpu.mp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 
 * @author lixinguo
 *
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

}
