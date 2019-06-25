package com.vpu.mp.controller.index;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.index.IndexService;
import com.vpu.mp.db.main.tables.records.ShopFreeExperienceRecord;


/**
 * 
 * @author 黄壮壮
 * 2019-06-24 
 */

@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
	@Autowired
	IndexService indexService;
	
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
		
		boolean result = indexService.verifyIsExist(mobile);
		
		if(result) {
			return JsonResult.fail("该手机号已申请，请勿重复提交");
		}else {
			
			ShopFreeExperienceRecord shopFreeExperienceRecord = new ShopFreeExperienceRecord();
			
			// get device type
			String source = detectDevice(request);
			
			shopFreeExperienceRecord.setMobile(mobile);
			shopFreeExperienceRecord.setContact(contact);
			shopFreeExperienceRecord.setSource(source);
			
			// store in database
			indexService.insertUserInfo(shopFreeExperienceRecord);
			
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
