package com.vpu.mp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.saas.official.FreeExperienceService.FreeExperiencePageListParam;
import com.vpu.mp.support.LineConvertHump;


/**
 * 
 * @author 黄壮壮
 * 2019-06-26 17:27
 */

@Controller
public class SystemFreeExperienceController extends SystemBaseController{

	/**
	 * 获取用户申请试用列表
	 * @param param
	 * @return
	 */
	@RequestMapping("/system/free/experience")
	@ResponseBody
	public JsonResult getFreeExperienceList(@LineConvertHump @RequestBody FreeExperiencePageListParam param) {
		PageResult pageResult = this.saas.official.freeExperienceService.getPageList(param);
		
		return this.success(pageResult);
	}
}
