package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminIndexController extends AdminBaseController {

	@RequestMapping(value = "/admin/index")
	public ModelAndView index(Model model, HttpServletRequest request) {
		return view("admin/welcome");
	}
}
