package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.shop.config.ShopCfgService;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.PageListQueryParam;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPagePojo;
import com.vpu.mp.service.shop.ShopApplication;

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
	@PostMapping(value = "/admin/shopDecorate/list")
	public JsonResult list(XcxCustomerPagePojo param) {		
		PageResult<XcxCustomerPagePojo> list = shop().mpDecoration.getPageList(param );
		return success(list);
	}
	
	/**
	 * 装修页面详情
	 * @param pageId
	 * @return
	 */
	@PostMapping(value = "/admin/shopDecorate/detail")
	public JsonResult pageDetail(Integer pageId) {
		XcxCustomerPageRecord detail = shop().mpDecoration.getPageById(pageId);
		return success(detail.intoMap());
	}
	
	/**
	 * 设为首页
	 * @param pageId
	 * @return
	 */
	@PostMapping(value = "/admin/shopDecorate/setIndex")
	public JsonResult setIndex(Integer pageId) {
		boolean res = shop().mpDecoration.setIndex(pageId);
		return success(res);
	}
	
	/**
	 * 复制页面
	 * @param PageId
	 * @return
	 */
	@PostMapping(value = "/admin/shopDecorate/copyDecoration")
	public JsonResult copyDecoration(Integer PageId) {
		XcxCustomerPageRecord res = shop().mpDecoration.copyDecoration(PageId);
		return success(res);
	}

	
	/**
	 * 保存装修数据
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/shopDecorate/saveDecoration")
	public JsonResult saveDecoration(XcxCustomerPagePojo param) {
		XcxCustomerPageRecord res = shop().mpDecoration.saveDecoration(param);
		return success(res);
	}
	

	/**
	 * 设置店铺风格
	 * @param jsonParam
	 * @return
	 */
	@PostMapping("/admin/manage/decorate/updateStyle")
	public JsonResult upadteShopStyle(@RequestBody String jsonParam){
		shop().shopCfg.setShopCfg(ShopCfgService.K_SHOP_STYLE,jsonParam);
		return success();
	}

	/**
	 * 店铺风格查询
	 * @return
	 */
	@GetMapping("/admin/manage/decorate/getStyle")
	public JsonResult getShopStyle() {
		return success(shop().shopCfg.getShopCfg(ShopCfgService.K_SHOP_STYLE));
	}

	/**
	 * 底部导航查询
	 *
	 * @return
	 */
	@GetMapping("/admin/manage/bottom/get")
	public JsonResult getDecorateBottom(){
		return success(shop().shopCfg.getShopCfg(ShopCfgService.K_BOTTOM));
	}


	/**
	 * 底部导航设置
	 *
	 * @return
	 */
	@PostMapping("/admin/manage/bottom/update")
	public JsonResult updateDecorateBottom(@RequestBody String jsonParam){
		//底部导航配置以json形式存储在数据库，所以不需要转换pojo
		shop().shopCfg.setShopCfg(ShopCfgService.K_BOTTOM,jsonParam);
		return success();
	}
}
