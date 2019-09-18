package com.vpu.mp.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryVo;
import com.vpu.mp.service.pojo.shop.member.MemberDetailsVo;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.member.MemberParam;
import com.vpu.mp.service.pojo.shop.member.MememberLoginStatusParam;
import com.vpu.mp.service.pojo.shop.member.account.AddMemberCardParam;
import com.vpu.mp.service.pojo.shop.member.account.MemberCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.pojo.shop.member.data.IndustryVo;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
import com.vpu.mp.service.pojo.shop.member.tag.UserTagParam;
/**
 * 会员管理
 * @author 黄壮壮
 * 2019-07-08 09:30
 */
@RestController
@RequestMapping(value="/api/admin/member")
public class AdminMemberController extends AdminBaseController{
	
	/**
	 * 返回所有行业信息
	 * @return
	 */
	@PostMapping("/industry/get")
	public JsonResult getIndustryList() {
		List<IndustryVo> allIndustryInfo = MemberIndustryEnum.getAllIndustryInfo();
		allIndustryInfo.stream().forEach(item->this.i18nSuccess(item));
		return success(allIndustryInfo);
	}
	
	/**
	 * 通用会员列表弹窗分页查询
	 * @return
	 */
	@PostMapping("/common/list")
	public JsonResult getCommonPageList(@RequestBody CommonMemberPageListQueryParam param) {
		PageResult<CommonMemberPageListQueryVo> pageResult = this.shop().member.getCommonPageList(param);
		return this.success(pageResult);
	}
	
	/**
	 * 会员列表分页查询
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult getPageList(@RequestBody MemberPageListParam param) {
		/** 获取语言，用于国际化 */
		String language = StringUtils.isEmpty(request.getHeader("V-Lang"))?"":request.getHeader("V-Lang");
		PageResult<MemberInfoVo> pageResult = this.shop().member.getPageList(param,language);
		return this.success(pageResult);
	}
	
	/**
	 * 会员卡-弹窗
	 */
	@PostMapping("/card/all/list")
	public JsonResult getAllCardList() {
		logger().info("获取系统中的所有会员卡");
		MemberCardVo vo  = shop().member.card.getAllCardList();
		return success(vo);
	}
	
	/**
	 * 会员-添加会员卡
	 */
	@PostMapping("/card/all/add")
	public JsonResult addCardForMember(@RequestBody @Valid AddMemberCardParam param) {
		logger().info("为会员发放会员卡");
		logger().info(param.toString());
		shop().member.card.addCardForMember(param);
		return success();
	}
	
	
	/**
	 * 批量禁止登录-恢复登录
	 */
	@PostMapping("/manage/update")
	public JsonResult loginStatusControl(@RequestBody @Valid MememberLoginStatusParam param) {
		logger().info("更新用户登录状态");
		shop().member.changeLoginStatus(param);
		return success();
	}
	
	
	/**
	 * 打标签，为用户设置标签
	 */
	@PostMapping("/tag/edit")
	public JsonResult setTagForMember(@RequestBody @Valid UserTagParam param) {
		logger().info("为会员用户打标签");
		shop().member.setTagForMember(param);
		return success();
	}
	
	/**
	 * 查询某个用户的标签列表
	 * @param param
	 * @return
	 */
	@PostMapping("/tag/get")
	public JsonResult getTagForMember(@RequestBody MemberParam param) {
		logger().info("正在该会员的标签");
		List<TagVo> tagList = shop().member.getTagForMember(param);
		return success(tagList);
	}
	
	
	/**
	 * 会员信息详情
	 */
	@PostMapping("/manager/center/{userId}")
	public JsonResult getMemberInfo(@PathVariable Integer userId) {
		logger().info("获取会员用户id为 " + userId + " 详情信息");
		/** 获取语言，用于国际化 */
		String language = StringUtils.isEmpty(request.getHeader("V-Lang"))?"":request.getHeader("V-Lang");
		MemberDetailsVo vo = shop().member.getMemberInfoById(userId,language);
		
		return i18nSuccess(vo);
	}
	
	
	/**
	 * 更新会员信息
	 */
	@PostMapping("/info/update")
	public JsonResult updateMemberInfo(@RequestBody MemberParam param) {
		logger().info("");
		/** 获取语言，用于国际化 */
		String language = StringUtils.isEmpty(request.getHeader("V-Lang"))?"":request.getHeader("V-Lang");
		shop().member.updateMemberInfo(param);
		
		return success();
	}
	
	/**
	 * 获取用户目前持有的所有可用会员卡
	 * @param userId
	 * @return
	 */
	@PostMapping("/card/all/get/{userId}")
	public JsonResult getAllAvailableMemberCard(@PathVariable Integer userId) {
		logger().info("正在获取用户所有的可用会员卡");
		List<CardBasicVo> allAvailableMemberCard = shop().member.getAllAvailableMemberCard(userId);
		return success(allAvailableMemberCard);
	}
	
//-------------------------------优化代码结构-正在做代码迁移-----------------------------------------------
	
//	@PostMapping("/account/add")
//	public JsonResult updateMemberAccount(@RequestBody AccountParam param) {
//		int adminUser = 0;
//		Byte tradeType = 0;
//		Byte tradeFlow = 0;
//		MemberService member = shop().member;
//		int ret = member.account.addUserAccount(param,adminUser,tradeType,tradeFlow);
//		
//		//TODO ret应该返回的是错误码error code
//		if(ret == -1) {
//			return this.fail(JsonResultMessage.MSG_MEMBER_ACCOUNT_UPDATE_FAIL);
//		}else {
//			//添加操作记录信 该表java版于php版有很大的区别
//			AdminTokenAuthInfo adminTokenAuthInfo = this.adminAuth.user();
//			UserRecord user = member.getUserRecordById(param.getUserId());
//			//TODO处理记录
//			//member.account.addActionRecord(param,user,adminTokenAuthInfo);
//		}
//		
//		return success();
//	}
//	
	
//--------------------------------优化代码结构-正在做代码迁移--------------------------------------
//	/**
//	 * 会员列表更新积分
//	 * @param param
//	 * @return
//	 */
//	@PostMapping(value="/score/update")
//	public JsonResult updateMemberScore(@RequestBody @Valid ScoreParam param) {
//		
//		// 判断修改后的值不能小于零
//		Integer score = param.getScore();
//		//最小值
//		Integer scoreDis = param.getScoreDis();
//		if((score+scoreDis)<0) {
//			return fail(JsonResultMessage.MSG_MEMBER_SCORE_NOT_ENOUGH);
//		}
//		
//		Integer subAccountId = this.adminAuth.user().getSubAccountId();
//		
//		if(param.getUserId()!=null) {
//			Integer[] arrayUserId = param.getUserId();
//			int userNumber = arrayUserId.length;
//			Byte tradeType = 4;
//			Byte tradeFlow = 1;
//			for(int i=0;i<userNumber;i++) {
//				Integer userId = arrayUserId[i];
//				shop().member.score.updateMemberScore(param,subAccountId,userId,tradeType,tradeFlow);
//			}
//		}else {
//			return fail(JsonResultMessage.MSG_MEMBER_NOT_EXIST);
//		}
//		return success(param.toString());
//	}
}
