package com.vpu.mp.controller.official;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.pojo.saas.offical.ShopFreeExperience;
import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.foundation.JsonResult;

/**
 * @author 黄壮壮
 * 2019-06-26 10:25
 */
@RestController
@RequestMapping("/api/official")
public class OfficialController extends OfficialBaseController{

	
	/**
	   *  处理提交申请使用的客户信息
	 * @param username
	 * @param mobile
	 * @url /index/check/free/experience
	 * @return JsonResult
	 */
	@PostMapping("/experience/add")
	@ResponseBody
	public JsonResult freeExperienceCheck(
			@RequestBody ShopFreeExperience shopFreeExperience) {
		
		if(StringUtils.isEmpty(shopFreeExperience.getContact())) {
			return this.fail(JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL);
		}
		if(StringUtils.isEmpty(shopFreeExperience.getMobile()))
			return this.fail(JsonResultMessage.MSG_ACCOUNT_MODILE_NOT_NULL);
		
		
		boolean result = saas.official.freeExperienceService.verifyIsExist(shopFreeExperience.getMobile());
		
		if(result) {
			return this.fail(JsonResultMessage.MSG_ACCOUNT_MODILE_REGISTERED);
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
