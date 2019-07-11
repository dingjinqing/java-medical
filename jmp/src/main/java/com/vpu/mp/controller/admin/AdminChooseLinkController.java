package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.ActivityVo;
import com.vpu.mp.service.pojo.shop.decoration.ChooseLinkParam;
import com.vpu.mp.service.pojo.shop.decoration.GoodsLinkVo;
import com.vpu.mp.service.pojo.shop.decoration.StoreVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxLinkListVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxNameListVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;

/**
 * 装修通用弹窗-选择链接
 * @author 常乐
 * 2019年7月9日
 */
@RestController
@RequestMapping("/api")
public class AdminChooseLinkController extends AdminBaseController{
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
	@PostMapping(value = "/admin/decorate/goods/list")
	public JsonResult goodsLink(@RequestBody GoodsLinkVo param) {
		PageResult<GoodsLinkVo> goodsLinkList = shop().chooselink.getGoodsLink(param);
		return this.success(goodsLinkList);
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
	 * 创建网页跳转内容
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/web/save")
	public JsonResult saveWebLink(@RequestBody XcxLinkListVo param) {
		Boolean result = shop().chooselink.saveWebLink(param);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 网页跳转列表
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/web/list")
	public JsonResult webLink() {
		List<XcxLinkListVo> list = shop().chooselink.getWebLink();
		return this.success(list);
		
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
	public JsonResult xcxLinkList() {
		List<XcxLinkListVo> linkList = shop().chooselink.getXcxLinkList();
		return this.success(linkList);
	}
	
	/**
	 * 添加小程序跳转链接
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/link/save")
	public JsonResult xcxLinkSave(XcxLinkListVo param) {
		Boolean res = shop().chooselink.saveXcxLink(param);
		if(res) {
			return this.success();
		}else{
			return this.fail();
		}
	}
	
	/**
	 * 拼团抽奖链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/pin/list")
	public JsonResult groupDraw() {
		List<ActivityVo> pinDrawList = shop().chooselink.getGroupDrawList();
		return this.success(pinDrawList);
	}
	
	/**
	 * 瓜分积分链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/integration/list")
	public JsonResult pinIntegration() {
		List<ActivityVo> integrationList = shop().chooselink.getIntegrationList();
		return this.success(integrationList);
	}
	
	/**
	 * 好友助力链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/promote/list")
	public JsonResult promoteList() {
		List<ActivityVo> promoteList = shop().chooselink.getPromoteList();
		return this.success(promoteList);
	}
	
	/**
	 * 加价购链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/price/list")
	public JsonResult priceList(){
		List<ActivityVo> priceList = shop().chooselink.getPriceList();
		return this.success(priceList);
	}
	
	/**
	 * 幸运到抽奖链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/lottery/list")
	public JsonResult lotteryList() {
		List<ActivityVo> lotteryList = shop().chooselink.getLotteryList();
		return this.success(lotteryList);
	}
	
	/**
	 * 优惠券链接
	 * @return
	 */
	@PostMapping(value = "/admin/decorate/voucher/list")
	public JsonResult voucherList() {
		 List<ActivityVo> voucherList = shop().chooselink.getVoucherList();
		 return this.success(voucherList);
	}
	
	/**
	 * 会员卡链接
	 * @return
	 */
	public JsonResult cardList() {
		 List<ActivityVo> cardList = shop().chooselink.getCardList();
		 return this.success(cardList);
	}
}
