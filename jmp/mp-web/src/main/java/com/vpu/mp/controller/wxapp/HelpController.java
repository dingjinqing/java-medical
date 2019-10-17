package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月17日 上午11:27:00
 */
@RestController
public class HelpController extends BaseController {
	@GetMapping(value = "/wxapp/score/scoreDocument")
	public JsonResult scoreDocument(@RequestParam Integer shop_id, @RequestParam Integer user_id) {
		ShopRecord sRecord = saas.shop.getShopById(shop_id);
		if(sRecord==null) {
			return fail();
		}
		ShopCfgRecord scoreNum = saas.getShopApp(shop_id).userCard.scoreService.score.getScoreNum("score_document");
		if(scoreNum!=null) {
			return success(scoreNum.getV());
		}
		return fail();
	}

}
