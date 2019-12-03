package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;
import org.springframework.web.bind.annotation.*;

/**
 *	收藏有礼控制器
 * @author liangchen
 * @date 2019年8月20日
 */
@RestController
@RequestMapping("/api/admin/market/collect/gift")
public class AdminCollectGiftController extends AdminBaseController{
	/**
	 *	进入页面时返回开关配置状态，默认为关
	 * @param 
	 * @return
	 */
	@GetMapping("/select")
	public JsonResult collectGiftConfig() {
		CollectGiftParam vo = shop().config.collectGiftConfigService.collectGiftConfig();
		return success(vo);
	}
	/**
	 *	开关控制
	 * @return 修改状态信息
	 */
	@GetMapping("/status")
	public JsonResult updateStatus() {
		shop().config.collectGiftConfigService.updateStatus();
		return success();
	}
	/**
	 *	修改收藏有礼配置信息
	 * @param 
	 * @return
	 */
	@PostMapping("/update")
	public JsonResult updateCollectGiftConfig(@RequestBody CollectGiftParam param) {
		shop().config.collectGiftConfigService.updateCollectGiftConfig(param);
		return success();
	}
}
