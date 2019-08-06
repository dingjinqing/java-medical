package com.vpu.mp.controller.wxapp;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.auth.WxAppAuth;

/**
 * 
 * @author lixinguo
 *
 */
public class WxAppBaseController  extends BaseController {

	@Autowired
	protected WxAppAuth wxAppAuth;
}
