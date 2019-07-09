package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.config.SearchConfig;
import com.vpu.mp.service.shop.config.BaseShopConfigService;

/**
 * @author 黄荣刚
 * @date 2019年7月8日
 *
 */
@RestController
@RequestMapping("/api/admin/shop/cfg")
public class AdminShopCfgController  extends AdminBaseController {

	/**
	 * 查询 搜索配置
	 * @return
	 */
	@GetMapping("/getSearchCfg")
	public JsonResult getSearchCfg() {
		return success(shop().config.searchCfg.getSearchConfig());
	}
	/**
	 * 修改搜索配置
	 * @param jsonParam
	 * @return
	 */
	@PostMapping("/updateSearchCfg")
	public JsonResult updateSearchCfg(@RequestBody SearchConfig config){
		shop().config.searchCfg.setSearchConfig(config);
		return success();
	}
	
}
