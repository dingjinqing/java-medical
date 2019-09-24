package com.vpu.mp.controller.admin;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardParam;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 会员卡管理
 */
@RestController
@RequestMapping(value = "/api/admin/member")
public class AdminMemberCardController extends AdminBaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 会员卡 - 创建
	 */
	@PostMapping("/card/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		logger.info("创建会员卡");
		shop().member.card.addMemberCard(card);
		return this.success();
	}
	
	/**
	 * 会员卡 - 更新
     */
	@PostMapping("/card/update")
	public JsonResult updateCard(@RequestBody CardParam param) {
		logger.info("更新会员卡");
		shop().member.card.updateMemberCard(param);
		return success();
	}
	/**
	 * 会员卡 - 更新-测试
     */
	@PostMapping("/card/test/update")
	public JsonResult updateCardTest(@RequestBody CardParam param) {
		logger.info("更新会员卡测试");

		return success();
	}
	

	/**
	 *  返回相应的会员卡列表
	 * @param param
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
	 * 
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
	 * 
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
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/card/get")
	public JsonResult getCardById(@RequestBody @Valid CardIdParam param) {
		logger.info("获取会员卡的详细信息");
		BaseCardVo card = shop().member.card.getCardById(param);
		return success(card);
	}	
	
	/**
	 * 获取所有会员卡弹窗
	 */
	@PostMapping("/card/all/get")
	public JsonResult getAllUserCard() {
		logger.info("获取所有会员卡弹窗");
		List<CardBasicVo> allUserCard = shop().member.card.getAllUserCard();
		return success(allUserCard);
	}
	
	
	/**
	 * 获取所有专属会员卡弹窗
	 */
	@PostMapping("/card/exclusive/get")
	public JsonResult getCardExclusive() {
		logger.info("获取所有专享会员卡弹窗");
		List<CardBasicVo> allUserCard = shop().member.card.getCardExclusive();
		return success(allUserCard);
	}
	
	/**
	 * 获取持卡会员
	 */
	@PostMapping("/cardholder")
	public JsonResult getAllCardHolders(@RequestBody CardHolderParam param) {
		logger.info("获取所有持卡会员");
		PageResult<CardHolderVo> result = shop().member.card.getAllCardHolder(param);
		return success(result);
	}
	
}
