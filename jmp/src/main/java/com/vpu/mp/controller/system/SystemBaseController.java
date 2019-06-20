package com.vpu.mp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.auth.SystemAuth;
/**
 * 
 * @author 新国
 *
 */
public class SystemBaseController extends BaseController {

	
	
	@Autowired
	protected SystemAuth sysAuth;
	
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
    
    @Override
    protected ModelMap globalModelMap() {
    	ModelMap model = new ModelMap();
    	model.addAttribute("input_map", this.inputMap());
		model.addAttribute("global_title", "微铺宝小程序Saas后台");
		return model;
	}
}
