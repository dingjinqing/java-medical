package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminTestController extends AdminBaseController {

	@RequestMapping(value = "/admin/test")
	public String test() {
		return "OK";
	}
}
