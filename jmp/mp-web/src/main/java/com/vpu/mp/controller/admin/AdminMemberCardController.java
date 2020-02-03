package com.vpu.mp.controller.admin;


import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchParam;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeVo;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeVo;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveParam;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveVo;
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

	/**
	 * 会员卡 - 创建
	 */
	@PostMapping("/card/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		logger().info("创建会员卡");
		shop().member.card.createMemberCard(card);
		return this.success();
	}
	
	/**
	 * 会员卡 - 更新
     */
	@PostMapping("/card/update")
	public JsonResult updateCard(@RequestBody CardParam param) {
		logger().info("更新会员卡");
		shop().member.card.updateMemberCard(param);
		return success();
	}
	/**
	 * 会员卡 - 更新-测试
     */
	@PostMapping("/card/test/update")
	public JsonResult updateCardTest(@RequestBody CardParam param) {
		logger().info("更新会员卡测试");
		return success();
	}
	

	/**
	 *  返回相应的会员卡列表
	 * @param param
	 * @return
	 */
	@PostMapping("/card/list")
	public JsonResult getCardList(@RequestBody SearchCardParam param) {

		logger().info(param.toString());
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
		logger().info("开始处理禁用或启动会员卡");
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
		logger().info("开始删除会员卡");
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
		logger().info("获取会员卡的详细信息");
		BaseCardVo card = shop().member.card.getCardDetailById(param);
		return success(card);
	}	
	
	/**
	 * 获取所有会员卡弹窗
	 */
	@PostMapping("/card/all/get")
	public JsonResult getAllUserCard() {
		logger().info("获取所有会员卡弹窗");
		List<CardBasicVo> allUserCard = shop().member.card.getAllUserCard();
		return success(allUserCard);
	}
	
	
	/**
	 * 获取所有专属会员卡弹窗
	 */
	@PostMapping("/card/exclusive/get")
	public JsonResult getCardExclusive() {
		logger().info("获取所有专享会员卡弹窗");
		List<CardBasicVo> allUserCard = shop().member.card.getCardExclusive();
		return success(allUserCard);
	}
	
	/**
	 * 获取持卡会员
	 */
	@PostMapping("/cardholder")
	public JsonResult getAllCardHolders(@RequestBody CardHolderParam param) {
		logger().info("获取所有持卡会员");
		PageResult<CardHolderVo> result = shop().member.card.getAllCardHolder(param);
		return success(result);
	}
	
	
	
	/**
	 * 获取会员卡领取详情
	 */
	@PostMapping("/code/receivelist")
	public JsonResult getReceiveList(@RequestBody CodeReceiveParam param) {
		logger().info("获取会员卡领取详情");
		PageResult<CodeReceiveVo> result = shop().member.card.getReceiveList(param);
		return success(result);
	}
	
	/**
	 * 获取会员卡批次
	 * @param cardId
	 * @return
	 */
	@PostMapping("/card/batch/get/{cardId}")
	public JsonResult getCardBatchList(@PathVariable Integer cardId) {
		logger().info("获取会员卡所有批次");
		List<CardBatchVo> cardBatchList = shop().member.card.getCardBatchList(cardId);
		return success(cardBatchList);
	}
	
	/**
	 * 废除指定的会员卡批次
	 * @param cardId
	 * @return
	 */
	@PostMapping("/card/batch/delete/{id}")
	public JsonResult deleteCardBatch(@PathVariable Integer id) {
		logger().info("删除会员卡批次");
		shop().member.card.deleteCardBatch(id);
		return success();
	}
	
	/**
	 * 	获取充值明细
	 * @param param
	 * @return
	 */
	@PostMapping("/card/charge/list")
	public JsonResult getChargeList(@RequestBody ChargeParam param) {
		PageResult<ChargeVo> chargeList = shop().member.card.getChargeList(param,getLang());
		return success(chargeList);
	}
	
	
	
	/**
	 * 获取消费明细
	 * @param param
	 * @return
	 */
	@PostMapping("/card/consume/list")
	public JsonResult getConsumeList(@RequestBody ChargeParam param) {
		PageResult<ChargeVo> chargeList = shop().member.card.getConsumeList(param,getLang());
		return success(chargeList);
	}
	
	/**
	 * 获取激活列表信息
	 * @param param
	 * @return
	 */
	@PostMapping("/activateAudit/list")
	public JsonResult getActivateAuditList(@RequestBody ActiveAuditParam param) {
		PageResult<ActiveAuditVo> activateAuditList = shop().member.card.getActivateAuditList(param);
		return i18nSuccess(activateAuditList);
	}
	
	/**
	 * 审核用户激活通过
	 * @param id
	 * @return
	 */
	@PostMapping("/activateAudit/activate")
	public JsonResult passActivateAudit(@RequestBody ActiveAuditParam param) {
		shop().member.card.passActivateAudit(param);
		return success();
	}
	
	/**
	 * 拒绝通过审核
	 * @param param
	 * @return
	 */
	@PostMapping("/activateAudit/reject")
	public JsonResult rejectActivateAudit(@RequestBody ActiveAuditParam param) {
		shop().member.card.rejectActivateAudit(param);
		return success();
	}

	/**
	 * 查询会员卡订单
	 */
	@PostMapping("/card/order/list")
	public JsonResult getCardConsumeOrderList(@RequestBody CardConsumeParam param) {
		PageResult<CardConsumeVo> results = shop().member.card.getCardConsumeOrderList(param);
		return success(results);
	}
	
	/**
	 * 添加领取批次
	 */
	@PostMapping("/card/generatecode")
	public JsonResult generateCardCode(@RequestBody CardBatchParam param) {
		CardBatchVo vo = shop().member.card.generateCardCode(param);
		return success(vo);
	}
	/**
	 * 获取领取批次
	 */
	@PostMapping("/card/code/{batchId}")
	public JsonResult getBatchCfg(@PathVariable Integer batchId) {
		logger().info("获取领取批次");
		return success(shop().member.card.getBatchCfg(batchId));
	}
	
	
	/**
	 * 获取等级卡所有等级
	 */
	@PostMapping("/card/all/grade")
	public JsonResult getAvailableGradeCard() {
		List<String> res = shop().member.card.getAllNoDeleteCardGrade();
		return success(res);
	}
	
	
	
}
