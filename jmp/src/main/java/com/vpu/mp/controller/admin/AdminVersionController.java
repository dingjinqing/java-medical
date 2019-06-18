package com.vpu.mp.controller.admin;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.shop.version.VersionService.VersionQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author lixinguo
 *
 */
@Controller
public class AdminVersionController extends AdminBaseController {

	/**
	 * 当前版本功能判断
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/admin/version/judgment")
	public ModelAndView getVersionDetail(@LineConvertHump VersionQueryParam param) {

		Map<String, Object> version = shop().version.getVersionDetail(param);
		if (version == null) {
			return this.jsonFail("", -2);
		}
		return this.jsonSuccess(version);
	}
}
