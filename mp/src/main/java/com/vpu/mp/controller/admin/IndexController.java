package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class IndexController {


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("");
		System.out.println("path" + path);
		model.addAttribute("name", "哈哈哈");
		return "admin/index";
	}

}
