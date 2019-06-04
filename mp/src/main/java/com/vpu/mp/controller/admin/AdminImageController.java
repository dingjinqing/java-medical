package com.vpu.mp.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.image.ImageService.ImageListQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminImageController extends AdminBaseController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/manage/image/list")
	public ModelAndView getList(@LineConvertHump ImageListQueryParam param) {
		
		float size = this.shop().image.getAllSize() / 1024 / 1024;
		Map<String, Object> versionNumberMap = saas.shop.version.versionNumShow("picture_num", this.shopId());
		Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");
		self.put("use", String.format("%.2f", size));
		
		String message = this.shop().image.processPostRequest(param);
		PageResult page = this.shop().image.getPageList(param);
		
		ModelMap model = new ModelMap();
		model.addAttribute("title", "图片空间管理");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		model.addAttribute("nav_type", 0);
		model.addAttribute("img_cat_arr", Util.toJSON(this.shop().image.category.getImageCategoryForZTree(null)));
		model.addAttribute("upload_sort_list", this.shop().image.getUploadSortList());
		model.addAttribute("img_cat_list", this.shop().image.category.getCategoryTree((byte) -1).intoMaps());
		model.addAttribute("input_map", inputMap());
		model.addAttribute("version", versionNumberMap);
		model.addAttribute("message", message);
		return view("admin/image_manager_list", model);
	}

}
