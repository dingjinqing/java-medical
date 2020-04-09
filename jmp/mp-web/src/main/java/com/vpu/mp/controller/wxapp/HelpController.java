package com.vpu.mp.controller.wxapp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.score.CheckSignVo;
/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月17日 上午11:27:00
 */
@RestController
public class HelpController extends HelpBaseController {
	
	
	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 服务条款
	 * @param shop_id
	 * @param user_id
	 * @return
	 */
	@GetMapping("/api/wxapp/score/scoreDocument")
	public JsonResult scoreDocument(@RequestParam Integer shop_id, @RequestParam Integer user_id) {
		log.info("查询积分说明");
		checkId();
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

	
	/**
	 * 签到帮助页
	 * @param shop_id
	 * @param user_id
	 * @param sign_rule
	 * @return
	 */
	@GetMapping("/api/wxapp/sign/help")
	public JsonResult getSignHelp(@RequestParam Integer shop_id, @RequestParam Integer user_id) {
		log.info("进入签到帮助页");
		checkId();
		CheckSignVo sCheckSignVo = saas.getShopApp(shop_id).userCard.scoreService.checkSignInScore(user_id);
		return success(sCheckSignVo);
	}
}
