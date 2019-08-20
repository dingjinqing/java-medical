package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;

/**
 *	收藏有礼控制器
 * @author liangchen
 * @date 2019年8月20日
 */
@RestController
public class AdminCollectGiftController extends AdminBaseController{
	/**
	 *	进入页面时返回开关配置状态，默认为关
	 * @param 
	 * @return
	 */
	@RequestMapping("/api/admin/market/collectgift/select")
	public JsonResult collectGiftConfig() {
		CollectGiftParam vo = shop().config.collectGiftConfigService.collectGiftConfig();
		return success(vo);
	}
	/**
	 *	开关控制
	 * @param 
	 * @return
	 */
	@RequestMapping("/api/admin/market/collectgift/status")
	public JsonResult updateStatus() {
		shop().config.collectGiftConfigService.updateStatus();
		return success();
	}
	/**
	 *	修改收藏有礼配置信息
	 * @param 
	 * @return
	 */
	@RequestMapping("/api/admin/market/collectgift/update")
	public JsonResult updateCollectGiftConfig(@RequestBody CollectGiftParam param) {
		shop().config.collectGiftConfigService.updateCollectGiftConfig(param);
		return success();
	}
}
