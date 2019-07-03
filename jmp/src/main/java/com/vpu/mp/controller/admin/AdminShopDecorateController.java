package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPagePojo;
import com.vpu.mp.service.shop.decoration.MpDecorationService.PageListQueryParam;

/**
 * 装修模块
 * 
 * @author 常乐
 * 2019年6月27日
 */
@RestController
public class AdminShopDecorateController extends AdminBaseController {
	
	/**
	 * 装修页面列表
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/shopDecorate/pageList")
	public JsonResult list(PageListQueryParam param) {		
		PageResult<XcxCustomerPagePojo> list = shop().mpDecoration.getPageList(param );
		return success(list);
	}
	
	/**
	 * 装修页面详情
	 * @param pageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/shopDecorate/pageDetail")
	public JsonResult pageDetail(Integer pageId) {
		XcxCustomerPageRecord detail = shop().mpDecoration.getPageById(pageId);
		return success(detail.intoMap());
	}
	
	/**
	 * 设为首页
	 * @param pageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/shopDecorate/setIndex")
	public JsonResult setIndex(Integer pageId) {
		boolean res = shop().mpDecoration.setIndex(pageId);
		return success(res);
	}
}
