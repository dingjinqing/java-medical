package com.vpu.mp.controller.admin;

import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;
import com.vpu.mp.service.pojo.shop.config.SearchConfig;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.shop.decoration.BatchSetPageCateParam;
import com.vpu.mp.service.pojo.shop.decoration.PageClassificationVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.pojo.shop.decoration.setIndexParam;
import org.apache.commons.lang3.StringUtils;
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
//	@Override
//    protected ShopApplication shop() {
//        return saas.getShopApp(471752);
//    }
	/**
	 * 装修页面列表
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/list")
	public JsonResult list(@RequestBody XcxCustomerPageVo param) {
		PageResult<XcxCustomerPageVo> list = shop().mpDecoration.getPageList(param);
		return success(list);
	}

	/**
	 * 新建装修页面
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/add")
	public JsonResult addPage(@RequestBody XcxCustomerPageVo param) {
		Integer res = shop().mpDecoration.addPage(param);
		return this.success(res);
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
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/index/set")
	public JsonResult setIndex(@RequestBody setIndexParam param) {
		boolean res = shop().mpDecoration.setIndex(param);
		return success(res);
	}

	/**
	 * 编辑-获取页面数据
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/edit")
	public JsonResult editPage(@RequestBody setIndexParam param) {
		XcxCustomerPageRecord info = shop().mpDecoration.editPage(param);
		return this.success(info.intoMap());
	}

	/**
	 * 页面分类信息
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/cate/page")
	public JsonResult pageCate() {
		List<PageClassificationVo> pageCateList = shop().mpDecoration.getPageCate();
		return this.success(pageCateList);
	}

	/**
	 * 页面装修设置页面分类
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/cate/set")
	public JsonResult setPageCate(@RequestBody PageClassificationVo param) {
		int res = shop().mpDecoration.setPageCate(param);
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
		boolean res = shop().mpDecoration.batchSetPageCate(param);
		return this.success(res);
	}

	/**
	 * 删除装修页面
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/del")
	public JsonResult delXcxPage(@RequestBody PageClassificationVo param) {
		int res = shop().mpDecoration.delXcxPage(param);
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
		Boolean res = shop().mpDecoration.copyDecoration(param.getPageId());
		return success(res);
	}

	/**
	 * 分享装修页面获取小程序二维码
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/share")
	public JsonResult getPageShareCode(@RequestBody Integer pageId) throws Exception {
		return success(shop().mpDecoration.getMpQrCode(pageId));
	}

	/**
	 * 保存装修数据
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/save")
	public JsonResult saveDecoration(@RequestBody XcxCustomerPageVo param) {
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
