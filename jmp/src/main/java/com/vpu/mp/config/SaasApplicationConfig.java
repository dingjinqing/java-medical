package com.vpu.mp.config;

import com.vpu.mp.service.saas.SaasApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaasApplicationConfig {
	
	@Bean
    public SaasApplication saas(){
        return SaasApplication.instance();
    }
}
