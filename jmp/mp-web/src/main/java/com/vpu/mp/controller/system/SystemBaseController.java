package com.vpu.mp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.auth.SystemAuth;
import com.vpu.mp.controller.BaseController;
/**
 * 
 * @author 新国
 *
 */
public class SystemBaseController extends BaseController {

	@Autowired
	protected SystemAuth sysAuth;
	


}
