package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminIndexController {

	@RequestMapping(value = "/admin/index")
	public String index(Model model, HttpServletRequest request) {
		return "admin/welcome";
	}
}
