package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.live.LiveCheckVo;
import com.vpu.mp.service.pojo.shop.market.live.LiveListParam;

/**
 * 小程序直播
 * 
 * @author zhaojianqiang
 * @time 下午4:12:34
 */
@RestController
public class AdminLiveController extends AdminBaseController {

	@PostMapping("/api/admin/market/live/list")
	public JsonResult getLiveList(@RequestBody LiveListParam param) {
		LiveCheckVo checkLive = saas.shop.mp.checkLive(shopId());
		if(checkLive.getIsAuthLive()) {
			return success(checkLive);
		}
		
		
		
		return null;
	}

}
