package com.vpu.mp.controller.admin;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.shop.tables.records.PageClassificationRecord;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.decoration.PageClassificationService.PageCategoryListQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author lixinguo
 *
 */
@Controller
public class AdminPageClassificationController extends AdminBaseController {

	@RequestMapping(value = "/admin/manage/classification/list")
	public ModelAndView pageClassification(@LineConvertHump PageCategoryListQueryParam param) {
		if (this.isAjax()) {
			if (param.id != null && param.name != null) {
				shop().pageClassification.setName(param.id, param.name);
			}
			if (param.id == null && param.name != null) {
				shop().pageClassification.addRow(param.name);
			}
			return jsonSuccess();
		}

		if (param.del != null) {
			shop().pageClassification.removeRow(param.del);
		}

		PageResult page = this.shop().pageClassification.getPageList(param);
		for (Map<String, Object> r : page.dataList) {
			r.put("count", shop().mpDecoration.getPageCount(Util.getInteger(r.get("id"))));
		}

		ModelMap model = new ModelMap();
		model.addAttribute("title", "页面分类");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		return view("admin/page_classification_list", model);
	}

	@PostMapping(value = "/admin/manage/classification/judge")
	public ModelAndView judge(@LineConvertHump PageCategoryListQueryParam param) {
		String actName = "name";
		if (param.act != null && param.act.equals(actName)) {
			PageClassificationRecord record = shop().pageClassification.getRowByName(param.name);
			if (record == null) {
				return jsonSuccess();
			} else {
				if (param.id != null && record.getId().intValue() == param.id) {
					return jsonSuccess();
				}
			}
		}
		return jsonFail();
	}
}
