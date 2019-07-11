package com.vpu.mp.controller.admin;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.shop.ShopApplication;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 装修模块
 * 
 * @author 常乐 2019年6月27日
 */
@RestController
//@RequestMapping("/api")
public class AdminShopDecorateController extends AdminBaseController {

	private static ObjectMapper mapper = new ObjectMapper();
	@Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }

	/**
	 * 装修页面列表
	 * 
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/list")
	public JsonResult list(XcxCustomerPageVo param) {
		PageResult<XcxCustomerPageVo> list = shop().mpDecoration.getPageList(param);
		return success(list);
	}

	/**
	 * 装修页面详情
	 * 
	 * @param  pageId
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/detail")
	public JsonResult pageDetail(Integer pageId) {
		XcxCustomerPageRecord detail = shop().mpDecoration.getPageById(pageId);
		return success(detail.intoMap());
	}

	/**
	 * 设为首页
	 * 
	 * @param  pageId
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/index/set")
	public JsonResult setIndex(Integer pageId) {
		boolean res = shop().mpDecoration.setIndex(pageId);
		return success(res);
	}

	/**
	 * 复制页面
	 * 
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/copy")
	public JsonResult copyDecoration(@RequestBody XcxCustomerPageVo param) {
		Boolean res = shop().mpDecoration.copyDecoration(param.getPageId());
		return success();
	}

	/**
	 * 保存装修数据
	 * 
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/save")
	public JsonResult saveDecoration(@RequestBody XcxCustomerPageVo param) {
		System.out.println(param);
		boolean res = shop().mpDecoration.saveDecoration(param);
		if(res) {
			return this.success();
		}else {
			return this.fail();
		}
		
	}

	/**
	 * 设置店铺风格
	 * 
	 * @param  config
	 * @return
	 */
	@PostMapping("/admin/decorate/style/update")
	public JsonResult updateShopStyle(@RequestBody @Valid ShopStyleConfig config) {
		shop().config.shopStyleCfg.setShopStyleConfig(config);
		return success();
	}

	/**
	 * 店铺风格查询
	 * 
	 * @return
	 */
	@GetMapping("/admin/decorate/style/get")
	public JsonResult getShopStyle() {
		ShopStyleConfig config = shop().config.shopStyleCfg.getShopStyleConfig();
		return config != null ? success(config) :  fail(JsonResultCode.DECORATE_STYLE_ISNOTJSON);
	}

	/**
	 * 底部导航查询
	 *
	 * @return
	 */
	@GetMapping("/admin/bottom/get")
	public JsonResult getDecorateBottom() {
		List<BottomNavigatorConfig> cfg = shop().config.bottomCfg.getBottomNavigatorConfig();
		if (cfg != null) {
			return success(cfg);
		}
		return fail(JsonResultCode.DECORATE_BOTTOM_ISNOTJSON);
	}

	/**
	 * 底部导航设置
	 *
	 * @return
	 */
	@PostMapping("/admin/bottom/update")
	public JsonResult updateDecorateBottom(@RequestBody List<BottomNavigatorConfig> bottomNavConfg) {
		shop().config.bottomCfg.setBottomNavigatorConfig(bottomNavConfg);
		return success();
	}
}
