package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.ChooseLinkParam;
import com.vpu.mp.service.pojo.shop.decoration.StoreVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxLinkListVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxNameListVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 装修通用弹窗-选择链接
 * @author 常乐
 * 2019年7月9日
 */
@RestController
@RequestMapping("/api")
public class AdminChooseLinkController extends AdminBaseController{
	@Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }
	
	/**
	 * 常用链接
	 */
	public Boolean commonLink() {
		return false;
	}
	
	/**
	 * 商品链接
	 * @return
	 */
	public Boolean goodsLink() {
		return false;
	}
	
	/**
	 * 自定义页面
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/page/custom")
	public JsonResult customPage(ChooseLinkParam param) {
		PageResult<XcxCustomerPageVo> pageList = shop().chooselink.customPage(param.getPage());
		return success(pageList);
	}
	
	/**
	 * 营销活动
	 * @return
	 */
	public Boolean activityList () {
		return false;
	}
	
	/**
	 * 商品分类
	 * @return
	 */
	public Boolean goodsCategory() {
		return false;
	}
	
	/**
	 * 页面跳转
	 * @return
	 */
	public Boolean webLink() {
		return false;
	}
	
	/**
	 * 表单页面
	 * @return
	 */
	public Boolean fromPage() {
		return false;
	}
	
	/**
	 * 门店列表
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/store")
	public JsonResult store(StoreListQueryParam param) {
		PageResult<StoreVo> storeList = shop().chooselink.store(param);
		return this.success(storeList);
	}
	
	/**
	 * 获取小程序名称
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/xcx/list")
	public JsonResult getXcxNameList() {
		List<XcxNameListVo> list = shop().chooselink.getXcxNameList();
		return this.success(list);
	}
	
	/**
	 * 小程序跳转链接列表
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/link/list")
	public JsonResult XcxLinkList() {
		List<XcxLinkListVo> linkList = shop().chooselink.getXcxLinkList();
		return this.success(linkList);
	}
	
	/**
	 * 添加小程序跳转链接
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/link/save")
	public JsonResult XcxLinkSave(XcxLinkListVo param) {
		Boolean res = shop().chooselink.saveXcxLink(param);
		if(res) {
			return this.success();
		}else{
			return this.fail();
		}
	}
}
