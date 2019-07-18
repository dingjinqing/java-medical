package com.vpu.mp.service.wechat.handler;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;
import org.springframework.beans.factory.annotation.Autowired;

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
