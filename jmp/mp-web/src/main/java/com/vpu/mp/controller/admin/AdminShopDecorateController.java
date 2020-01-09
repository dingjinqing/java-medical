package com.vpu.mp.controller.admin;

import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.validator.ValidList;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;
import com.vpu.mp.service.pojo.shop.config.SearchConfig;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.shop.decoration.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/list")
	public JsonResult list(@RequestBody XcxCustomerPageVo param) {
		PageResult<XcxCustomerPageVo> list = shop().adminDecoration.getPageList(param);
		return success(list);
	}

	/**
	 * 新建装修页面
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/add")
	public JsonResult addPage(@RequestBody XcxCustomerPageVo param) {
		Integer res = shop().adminDecoration.addPage(param);
		return res > 0 ? this.success(res) : fail();
	}

	/**
	 * 装修页面详情
	 *
	 * @param  pageId
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/detail")
	public JsonResult pageDetail(Integer pageId) {
		XcxCustomerPageRecord detail = shop().adminDecoration.getPageById(pageId);
		return success(detail.intoMap());
	}

	/**
	 * 设为首页
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/index/set")
	public JsonResult setIndex(@RequestBody PageIdParam param) {
		boolean res = shop().adminDecoration.setIndex(param);
		return success(res);
	}

	/**
	 * 编辑-获取页面数据
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/get")
	public JsonResult editPage(@RequestBody @Validated PageIdParam param) {
		return success(shop().adminDecoration.getPage(param));
	}

	/**
	 * 页面分类信息
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/cate/page")
	public JsonResult pageCate() {
		List<PageClassificationVo> pageCateList = shop().adminDecoration.getPageCate();
		return this.success(pageCateList);
	}

	/**
	 * 页面装修设置页面分类
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/cate/set")
	public JsonResult setPageCate(@RequestBody PageClassificationVo param) {
		int res = shop().adminDecoration.setPageCate(param);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail();
		}
	}

	/**
	 * 批量设置页面分类
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/cate/batchSet")
	public JsonResult batchSetPageCate(@RequestBody BatchSetPageCateParam param) {
		boolean res = shop().adminDecoration.batchSetPageCate(param);
		return this.success(res);
	}

	/**
	 * 删除装修页面
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/del")
	public JsonResult delXcxPage(@RequestBody PageClassificationVo param) {
		int res = shop().adminDecoration.delXcxPage(param);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail();
		}
	}

	/**
	 * 复制页面
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/copy")
	public JsonResult copyDecoration(@RequestBody XcxCustomerPageVo param) {
		Boolean res = shop().adminDecoration.copyDecoration(param.getPageId());
		return success(res);
	}

	/**
	 * 分享装修页面获取小程序二维码
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/share")
	public JsonResult getPageShareCode(@RequestBody Integer pageId) throws Exception {
		return success(shop().adminDecoration.getMpQrCode(pageId));
	}

	/**
	 * 保存装修数据
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/save")
	public JsonResult saveDecoration(@RequestBody @Valid PageStoreParam param) {
		boolean res = shop().adminDecoration.storePage(param);
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
	public JsonResult updateDecorateBottom(@RequestBody @Valid ValidList<BottomNavigatorConfig> bottomNavConfg) {
		shop().config.bottomCfg.setBottomNavigatorConfig(bottomNavConfg);
		return success();
	}

    /**
     * 查询 搜索配置
     * @return
     */
    @GetMapping("/admin/get/searchcfg")
    public JsonResult getSearchCfg() {
    	SearchConfig searchConfig = shop().config.searchCfg.getSearchConfig();
    	if(null==searchConfig) {
    		searchConfig=new SearchConfig(1, 1, 0);
    	}
        return success(searchConfig);
    }
    /**
     * 修改搜索配置
     * @param
     * @return
     */
    @PostMapping("/admin/update/searchcfg")
    public JsonResult updateSearchCfg(@RequestBody SearchConfig config){
    	List<String> hotWords = config.getHotWords();
    	if(null!=hotWords) {
    		if(hotWords.size()>11) {
    			return fail(JsonResultCode.SEARCHCFG_HOTWORDS_LIMIT);
    		}
    	}
    	if(config.getTitleAction().equals(3)&&StringUtils.isEmpty(config.getTitleCustom())) {
    		//自定义，titleCustom不能为空
    		return fail(JsonResultCode.SEARCHCFG_TITLECUSTOM_NOTNULL);
    	}
        shop().config.searchCfg.setSearchConfig(config);
        return success();
    }
}
