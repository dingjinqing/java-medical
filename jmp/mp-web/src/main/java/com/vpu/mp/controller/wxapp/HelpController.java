package com.vpu.mp.controller.wxapp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.Util;
/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月17日 上午11:27:00
 */
@RestController
public class HelpController extends BaseController {
	
	
	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	
	
	@GetMapping(value = "/wxapp/score/scoreDocument")
	public JsonResult scoreDocument(@RequestParam Integer shop_id, @RequestParam Integer user_id) {
		log.info("查询积分说明");
		ShopRecord sRecord = saas.shop.getShopById(shop_id);
		if(sRecord==null) {
			log.info("店铺不存在");
			return fail();
		}
		ShopCfgRecord scoreNum = saas.getShopApp(shop_id).userCard.scoreService.score.getScoreNum("score_document");
		if(scoreNum!=null) {
			log.info("设置查询积分说明");
			String v = scoreNum.getV();
			Map parseJson = Util.parseJson(v, Map.class);
			Object object = parseJson.get("document");
			return success(object);
		}
		log.info("未设置查询积分说明");
		return fail();
	}

}
