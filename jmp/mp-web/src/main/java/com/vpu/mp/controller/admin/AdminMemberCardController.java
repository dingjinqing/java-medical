package com.vpu.mp.controller.admin;


import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardVo;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardParam;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 会员卡管理
 */
@RestController
@RequestMapping(value="/api/admin/member")
public class AdminMemberCardController extends AdminBaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 创建一张会员卡
	 * @param card
	 * @return
	 */
	@PostMapping("/card/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		/** logger info*/
		logger.info(card.getDesc());
		logger.info(Util.toJson(card));
		this.shop().member.card.addMemberCard(card);
		return this.success();
	}
	
	
	/**
	 * 返回相应的会员卡列表
	 * @return
	 */
	@PostMapping("/card/list")
	public JsonResult getCardList(@RequestBody SearchCardParam param) {
		
		logger.info(param.toString());
		PageResult<? extends BaseCardVo> result = shop().member.card.getCardList(param);
		return success(result);
	}
	
	/**
	 * 设置会员卡使用状态或停用状态
	 * @param param
	 * @return
	 */
	@PostMapping("/card/power")
	public JsonResult powerCard(@RequestBody PowerCardParam param) {
		logger.info("开始处理禁用或启动会员卡");
		shop().member.card.powerCard(param);
		return success();
	}
	
	
	/**
	 * 删除会员卡
	 * @param param
	 * @return
	 */
	@PostMapping("/card/delete")
	public JsonResult deleteCard(@RequestBody @Valid CardIdParam param) {
		logger.info("开始删除会员卡");
		shop().member.card.deleteCard(param);
		return success();
	}
	
	/**
	 * 获取需要更新的会员卡的详细信息
	 * @param param
	 * @return
	 */
	@PostMapping("/card/get")
	public JsonResult getCardById(@RequestBody @Valid CardIdParam param) {
		logger.info("获取会员卡的详细信息");
		BaseCardVo card = shop().member.card.getCardById(param);
		return success(card);
	}
	
	
}
