package com.vpu.mp.controller.admin;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminIndexController extends AdminBaseController {

	@RequestMapping(value = "/admin/test")
	@ResponseBody
	public String test() throws InterruptedException {
		
		return "ok";
	}
}
