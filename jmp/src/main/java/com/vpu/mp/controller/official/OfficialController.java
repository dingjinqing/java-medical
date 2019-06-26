package com.vpu.mp.controller.official;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.pojos.ShopFreeExperience;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.saas.SaasApplication;

@RestController
@RequestMapping("/index")
public class OfficialController extends BaseController{

	
	/**
	   *  处理提交申请使用的客户信息
	 * @author 黄壮壮
	 * @param username
	 * @param mobile
	 * @url /index/check/free/experience
	 * @return JsonResult
	 */
	@PostMapping("/check/free/experience")
	public JsonResult freeExperienceCheck(
			HttpServletRequest request,
			@RequestParam(value="contact") String contact,
			@RequestParam(value="mobile") String mobile) {
		
		boolean result = this.saas.official.verifyIsExist(mobile);
		
		if(result) {
			return JsonResult.fail("该手机号已申请，请勿重复提交");
		}else {
			
			ShopFreeExperience shopFreeExperience = new ShopFreeExperience();
			
			// get device type
			String source = detectDevice(request);
			
			shopFreeExperience.setMobile(mobile);
			shopFreeExperience.setContact(contact);
			shopFreeExperience.setSource(source);
			
			// store in database
			this.saas.official.insertUserInfo(shopFreeExperience);
			
			return JsonResult.success("提交申请成功,请等待业务员联系");
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
			return "小程序";
		}else {
			return "PC";
		}
	}
}
