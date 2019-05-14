package com.vpu.mp.controller.system;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.controller.BaseController;

public class SystemBaseController extends BaseController {

	/**
	    *     显示信息页面
	 * @param message
	 * @param links
	 * @return
	 */
    protected ModelAndView showMessage(String message)
    {
    	ModelMap model = new ModelMap();
		model.addAttribute("message", message);
        return redirect("/system/message",model);
    }
    
    
    protected ModelAndView showMessage(String message,Object links)
    {
    	ModelMap model = new ModelMap();
		model.addAttribute("message", message);
		model.addAttribute("links", links);
        return redirect("/system/message",model);
    }
}
