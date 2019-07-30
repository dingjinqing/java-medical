package com.vpu.mp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.auth.SystemAuth;
/**
 * 
 * @author 新国
 *
 */
public class SystemBaseController extends BaseController {

	@Autowired
	protected SystemAuth sysAuth;
	


}
