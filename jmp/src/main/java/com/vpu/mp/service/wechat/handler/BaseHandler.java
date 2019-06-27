package com.vpu.mp.service.wechat.handler;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author 新国
 *
 */
public class BaseHandler {
	protected OpenPlatform open = OpenPlatform.instance();
	protected SaasApplication saas = SaasApplication.instance();

}
