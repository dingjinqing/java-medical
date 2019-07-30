package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController extends AdminBaseController {

	@RequestMapping(value = "/admin/test")
	public String test() {
		saas.getShopApp(123456).config.bottomCfg.test();
		return "OK";
	}
}
