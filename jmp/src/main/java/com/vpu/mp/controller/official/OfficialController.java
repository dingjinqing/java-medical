package com.vpu.mp.controller.official;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.pojos.ShopFreeExperience;
import com.vpu.mp.service.foundation.JsonResult;

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
			HttpServletRequest request,
			@RequestParam(value="contact") String contact,
			@RequestParam(value="mobile") String mobile) {
		
		boolean result = saas.official.verifyIsExist(mobile);
		
		if(result) {
			return fail();
		}else {
			
			ShopFreeExperience shopFreeExperience = new ShopFreeExperience();
			
			// get device type
			String source = detectDevice(request);
			
			shopFreeExperience.setMobile(mobile);
			shopFreeExperience.setContact(contact);
			shopFreeExperience.setSource(source);
			
			// store in database
			this.saas.official.insertUserInfo(shopFreeExperience);
			
			return success();
		}
	}
	
	
	/**
	 * 检测用户的登录设备
	 * @param request
	 * @return boolean
	 */
	public String detectDevice(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		System.out.println(userAgent);
		
		int i = userAgent.indexOf("Mobile");
		if(i != -1) {
			return "小程序";  // ?? 应该返回json！！
		}else {
			return "PC";
		}
	}
}
