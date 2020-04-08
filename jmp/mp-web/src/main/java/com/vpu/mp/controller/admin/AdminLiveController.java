package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.live.LiveCheckTwoVo;
import com.vpu.mp.service.pojo.shop.market.live.LiveCheckVo;
import com.vpu.mp.service.pojo.shop.market.live.LiveListParam;
import com.vpu.mp.service.pojo.shop.market.live.LiveListVo;
import com.vpu.mp.service.pojo.shop.market.live.LiveRomeGoodListVo;

/**
 * 小程序直播
 * 
 * @author zhaojianqiang
 * @time 下午4:12:34
 */
@RestController
public class AdminLiveController extends AdminBaseController {

	/**
	 * 直播列表
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/market/live/list")
	public JsonResult getLiveList(@RequestBody LiveListParam param) {
		LiveCheckVo checkLive = saas.shop.mp.checkLive(shopId());
		if(checkLive.getIsAuthLive()) {
			return success(checkLive);
		}
		PageResult<LiveListVo> pageList = shop().liveService.getList(param);
		checkLive.setPageList(pageList);
		return success(checkLive);
	}
	
	/**
	 * 商品列表
	 * @param id
	 * @return
	 */
	@GetMapping("/api/admin/market/live/goodList/{id}")
	public JsonResult getGoodsList(@PathVariable Integer id) {
		List<LiveRomeGoodListVo> goodsList = shop().liveService.liveGoods.packageGoodsList(id);
		return success(goodsList);

	}

}
