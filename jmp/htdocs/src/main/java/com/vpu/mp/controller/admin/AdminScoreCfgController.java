package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.member.ScoreCfgVo;
import com.vpu.mp.service.pojo.shop.member.ShopCfgParam;

/**
 * 积分管理controller
 * @author 黄壮壮
 * 2019-07-15 14:21
 */
@RestController
@RequestMapping(value="/api/admin/user/score")
public class AdminScoreCfgController extends AdminBaseController {

	@GetMapping(value="/get")
	public JsonResult getScoreConfig() {
		
		ScoreCfgVo vo = shop().score.getShopScoreCfg();
		return this.success(vo);
	}
	@PostMapping(value="/update")
	public JsonResult userScoreConfig(@RequestBody ShopCfgParam param) {
		
		//判断积分有效期类型
		int r = this.shop().score.setShopCfg(param);
		if(r == -1) {
			return this.fail();
		}
		return this.success();
	}
}
