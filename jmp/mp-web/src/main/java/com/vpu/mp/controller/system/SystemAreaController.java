package com.vpu.mp.controller.system;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.saas.area.AreaSelectVo;

/**
 * 区域选择弹窗
 * 
 * @author liangchen
 * @date 2019年8月27日
 */
@RestController
@RequestMapping("/api/system/area")
public class SystemAreaController extends SystemBaseController {

	/**
	 * 返回所有地区代码及名称
	 * 
	 * @param
	 * @return JsonResult
	 */
	@GetMapping("/select")
	public JsonResult getAllArea() {
		List<AreaSelectVo> areaSelectVo = saas.areaSelectService.getAllArea();
		return success(areaSelectVo);

	}
}
