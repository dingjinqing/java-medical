package com.vpu.mp.controller.official;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.saas.shop.ShopFreeExperiencePojo;

/**
 * @author 黄壮壮
 * 2019-06-26 10:25
 */
@RestController
@RequestMapping("/index")
public class OfficialController extends OfficialBaseController{

	
	/**
	   *  处理提交申请使用的客户信息
	 * @param username
	 * @param mobile
	 * @url /index/check/free/experience
	 * @return JsonResult
	 */
	@PostMapping("/check/free/experience")
	@ResponseBody
	public JsonResult freeExperienceCheck(
			@RequestBody ShopFreeExperiencePojo shopFreeExperience) {
		
		boolean result = saas.official.freeExperienceService.verifyIsExist(shopFreeExperience.getMobile());
		
		if(result) {
			return fail();
		}else {
			
			
			// get device type
			String source = detectDevice(this.request);
			
			/*
			 * shopFreeExperience.setMobile(mobile); shopFreeExperience.setContact(contact);
			 */
			shopFreeExperience.setSource(source);
			
			// store in database
			this.saas.official.freeExperienceService.insertUserInfo(shopFreeExperience);
			
			return success();
		}
	}
	
	
	/**
	 * TODO 有待优化处理 
	 * 检测用户的登录设备
	 * @param request
	 * @return boolean
	 */
	private String detectDevice(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		System.out.println(userAgent);
		
		int i = userAgent.indexOf("Mobile");
		if(i != -1) {
			return "Mobile"; 
		}else {
			return "PC";
		}
	}
}
