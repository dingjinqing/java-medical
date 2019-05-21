package com.vpu.mp.controller.system;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ShopController extends SystemBaseController {
	
	@RequestMapping(value = "/system/shop/list")
	public ModelAndView message() {
		ModelMap model = new ModelMap();
		model.addAttribute("title", "店铺列表");
		model.addAttribute("title", "店铺列表");
		return view("system/show_msg",model);
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
//       $data_list = saas()->shop->getPageList(request()->input(), $this->post("page"));
//       foreach ($data_list as $key => $item) {
//           $renew_shop = $this->getShopRenewService()->showRenew($item->shop_id);
//           $data_list[$key]->renew_money = $renew_shop['renew_money'];
//           $data_list[$key]->expire_time = $renew_shop['expire_time'];
//           $tims = $data_list[$key]->expire_time;
//           $data_list[$key]->expire_time = substr($tims, 0, 10);
//           $data_list[$key]->account_info = saas()->shop->account->getAccountInfoForID($item->sys_id);
//       }
//
//       $data = [
//           'title' => '店铺列表',
//           'data_list' => $data_list,
////           'keywords' => request()->input("keywords"),
//           'sys_id' => $this->get("sys_id"),
////           'account_key' => $this->get("account_key"),
//           'account' => $this->get("sys_id") ? saas()->shop->account->getRow($this->get("sys_id")) : null,
//           'nav_type' => 0,
////           'is_use' => $this->post('is_use'),
////           'shop_type' => $this->post('shop_type'),
//           'version_array' => saas()->shop->version->getVersionArray(),
//           'version_list' => saas()->shop->version->getALLVersion(),
//           'shop_flag_list' => ['店+','欧派','寺库'],
//           'request' => request()->all()
//       ];
//       return view("system.shop_list", $data);
//   }
}
