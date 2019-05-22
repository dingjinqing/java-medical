package com.vpu.mp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.saas.shop.CSysShop.ShopListQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class ShopController extends SystemBaseController {
	
	@RequestMapping(value = "/system/shop/list")
	public ModelAndView shopList(@LineConvertHump ShopListQueryParam param) {
		PageResult result = saas.sysShop.getPageList(param);
	
		String[] shopFlagList = new String[]{"店+","欧派","寺库"};
		ModelMap model = new ModelMap();
		model.addAttribute("title", "店铺列表");
		model.addAttribute("data_list", result.dataList);
		model.addAttribute("page", result.page);
		model.addAttribute("inputMap", this.inputMap());
		model.addAttribute("nav_type", 0);
		model.addAttribute("account", param.sysId == null ? new Object() : saas.sysShop.accout.getAccountInfoForID(param.sysId ).intoMap());
		model.addAttribute("version_array", saas.sysShop.version.getVersionMap());
		model.addAttribute("version_list", saas.sysShop.version.getAllVersion().intoMaps());
		model.addAttribute("nav_type", 0);
		model.addAttribute("shop_flag_list", shopFlagList);		
		return view("system/shop_list",model);
	}
	
//	 /**
//    *
//    * @route /system/shop/list
//    * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
//    */
//   public function getShopList()
//   {
//       if ($this->post("act") == "del") $this->getShopService()->removeRow($this->post('shop_id'));
//
//       if ($this->auth->isChildLogin()) {
//           $operator_id = $this->auth->user()['sub_account_id'];
//       } else {
//           $operator_id = 0;
//       }
//       if ($this->post('act') == "renew"){
//           $this->getShopRenewService()->insertRenewDate($this->input(), $operator_id);
//       }
//

}
