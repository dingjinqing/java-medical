package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminIndexController extends AdminBaseController {

	@RequestMapping(value = "/admin/index")
	@ResponseBody
	public String index(Model model, HttpServletRequest request) {
		System.out.println("index");
		System.out.println(this.request);
		System.out.println(request);
		return "index";
	}
	
	@RequestMapping(value = "/admin/message")
	public ModelAndView message() {
		return view("system/show_msg");
	}
}
