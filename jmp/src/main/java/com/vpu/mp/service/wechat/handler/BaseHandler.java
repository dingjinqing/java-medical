package com.vpu.mp.service.wechat.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author 新国
 *
 */
public class BaseHandler {
	protected OpenPlatform open = OpenPlatform.instance();
	
	@Autowired
	protected SaasApplication saas ;

}
