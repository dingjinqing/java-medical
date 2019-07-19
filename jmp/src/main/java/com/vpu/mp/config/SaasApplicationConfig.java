package com.vpu.mp.config;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.support.SpringUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
@ConditionalOnBean(SpringUtil.class)  
public class SaasApplicationConfig {
	
	@Lazy
	@Bean
	@Scope("prototype")
    public SaasApplication saas(){
        return SaasApplication.instance();
    }
}
