package com.vpu.mp.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.saas.shop.ShopAccount.ShopAccountListQueryParam;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class ShopAccountController extends SystemBaseController {

	@RequestMapping(value = "/system/shop/account/list")
	public ModelAndView getShopAccountList(ShopAccountListQueryParam param) {
		PageResult result = saas.sysShop.accout.getPageList(param);

		for (Map<String, Object> row : result.dataList) {
			row.put("shop_number", saas.sysShop.renew.getShopNumber((Integer) row.get("sys_id")));
			row.put("renew_money", saas.sysShop.renew.getRenewTotal((Integer) row.get("sys_id")));
			if (row.get("end_time") != null) {
				row.put("end_time", row.get("end_time").toString().substring(0, 10));
			}
		}

		ModelMap model = new ModelMap();
		model.addAttribute("title", "商家账号列表");
		model.addAttribute("data_list", result.dataList);
		model.addAttribute("page", result.page);
		model.addAttribute("inputMap", this.inputMap());
		model.addAttribute("nav_type", 0);
		return view("system/shop_account_list", model);
	}

	@RequestMapping(value = "/system/shop/account/add")
	public ModelAndView addShopAccount() {
		Object shopAccountDto = new Object();
		ModelMap model = new ModelMap();
		model.addAttribute("title", "添加商家账号");
		model.addAttribute("shop_account", shopAccountDto);
		model.addAttribute("province", saas.region.province.getAll().intoMaps());
		model.addAttribute("city", saas.region.city.getCityList(110000).intoMaps());
		model.addAttribute("district", saas.region.district.getDistrictList(110100).intoMaps());
		model.addAttribute("act_url", "/system/shop/account/add");
		model.addAttribute("nav_type", 2);
		return view("system/shop_account_info", model);

//        if (isPost()) {
//            $data = $this.all($this.getShopAccountUpdateFields(), ["business_state"]);
//            $data["base_sale"] = $this.post("base_sale") == "on" ? 1:0;
//            $pass = $data["password"];
//            $data["password"] = md5($data["password"]);
////            $account_id = saas().shop.operation.addRow($data);
//            $account_id = $this.getShopAccountService().addRow($data);
//            $data["password_word"] = $pass;
//            $desc = $this.diffEdit($data,array());
//            $data1 = [
//                "shop_id"=>$account_id,
//                "created" => date("Y-m-d H:i:s",time()),
//                "desc" => $desc,
//                "ip"=> $_SERVER["REMOTE_ADDR"],
//                "operator_id"=>$this.auth.user()["system_user_id"],
//                "operator"=>$this.auth.user()["user_name"],
//                "type"=>1
//            ];
//            saas().shop.operation.addRow($data1);
////            $this.getShopAccountService().addRow($data);
//            return redirect("/system/shop/account/list");
//        }
//        $data = [
//            "title" => "添加商家账号",
//            "shop_account" => new \stdClass(),
//            "nav_type" => 2,
//            "province" => saas().region.province.getAll(),
//            "city" => saas().region.city.getCityList(110000),
//            "district" => saas().region.district.getDistrictList(110100),
//            "act_url" => "/system/shop/account/add"
//        ];
//        return view("system.shop_account_info", $data);
	}

	@RequestMapping(value = "/system/user/address")
	@ResponseBody
	public JsonResult getRegionList(
			@RequestParam(value = "province_id", required = false, defaultValue = "0") Integer provinceId,
			@RequestParam(value = "city_id", required = false, defaultValue = "0") Integer cityId) {
		if (provinceId != 0) {
			Map<String, Object> data = new HashMap<String, Object>(2);
			Result<DictCityRecord> r = saas.region.city.getCityList(provinceId);
			cityId = r.size() > 0 ? r.get(0).getCityId() : 0;
			data.put("city", r.intoMaps());
			data.put("district", saas.region.district.getDistrictList(cityId).intoMaps());
			return JsonResult.success(data);
		}
		if (cityId != 0) {
			return JsonResult.success(saas.region.district.getDistrictList(cityId).intoMaps());
		}
		return JsonResult.fail("fail");
	}
}
