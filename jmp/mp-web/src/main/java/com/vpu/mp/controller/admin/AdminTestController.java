package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminTestController extends AdminBaseController {

	@RequestMapping(value = "/admin/test")
	public JsonResult test() throws Exception {
		throw new Exception("hello");
	}
}
