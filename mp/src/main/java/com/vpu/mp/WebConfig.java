package com.vpu.mp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vpu.mp.support.LineToHumpHandler;

/**
 * 
 * @author 新国
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer  {

	@Autowired
	protected Environment env;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LineToHumpHandler());
	}
	
}
