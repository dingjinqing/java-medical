package com.vpu.mp.controller.admin;


import com.vpu.mp.service.pojo.shop.decoration.DecorateBonntParam;
import com.vpu.mp.service.pojo.shop.decoration.DecorateStyleParam;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPagePojo;
import com.vpu.mp.service.shop.decoration.MpDecorationService.PageListQueryParam;

import javax.validation.Valid;

/**
 * 装修模块
 * 
 * @author 常乐
 * 2019年6月27日
 */
@RestController
@RequestMapping("/api")
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

	/**
	 * 设置店铺风格
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/manage/decorate/updateStyle")
	public JsonResult upadteShopStyle(@RequestBody @Valid DecorateStyleParam param){
		shop().mpDecoration.updateShopStyle(param);
		return success();
	}

	/**
	 * 店铺风格查询
	 * @return
	 */
	@GetMapping("/admin/manage/decorate/getStyle")
	public JsonResult getShopStyle() {
		return success(shop().mpDecoration.getShopStyle());
	}

	/**
	 * 底部导航查询
	 *
	 * @return
	 */
	@GetMapping("/admin/manage/bottom/get")
	public JsonResult getDecorateBottom(){
		return success(shop().mpDecoration.getDecorateBonnt());
	}


	/**
	 * 底部导航设置
	 *
	 * @return
	 */
	@PostMapping("/admin/manage/bottom/update")
	public JsonResult updateDecorateBottom(@RequestBody String jsonParam){
		//底部导航配置以json形式存储在数据库，所以不需要转换pojo
		shop().mpDecoration.updataDecorateBonnt(jsonParam);
		return success();
	}



}
