package com.vpu.jmd.service.wechat.handler;


import com.vpu.jmd.service.base.SaasApplication;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 新国
 *
 */
public class BaseHandler {
	@Autowired
	protected OpenPlatform open;

	@Autowired
	protected SaasApplication saas ;

}
