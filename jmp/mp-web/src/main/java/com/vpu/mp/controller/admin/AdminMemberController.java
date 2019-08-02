package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.shop.member.MemberService;


/**
 * 会员管理
 * @author 黄壮壮
 * 2019-07-08 09:30
 */
@RestController
@RequestMapping(value="/api/admin/member")
public class AdminMemberController extends AdminBaseController{

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
	 * 会员列表
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult getPageList(@RequestBody MemberPageListParam param) {
		PageResult<MemberInfoVo> pageResult = this.shop().member.getPageList(param);
		return this.success(pageResult);
	}
	
	
	
	
	@PostMapping("/account/add")
	public JsonResult updateMemberAccount(@RequestBody AccountParam param) {
		int adminUser = 0;
		Byte tradeType = 0;
		Byte tradeFlow = 0;
		MemberService member = shop().member;
		int ret = member.account.addUserAccount(param,adminUser,tradeType,tradeFlow);
		
		//TODO ret应该返回的是错误码error code
		if(ret == -1) {
			return this.fail(JsonResultMessage.MSG_MEMBER_ACCOUNT_UPDATE_FAIL);
		}else {
			//添加操作记录信 该表java版于php版有很大的区别
			AdminTokenAuthInfo adminTokenAuthInfo = this.adminAuth.user();
			UserRecord user = member.getUserRecordById(param.getUserId());
			//TODO处理记录
			//member.account.addActionRecord(param,user,adminTokenAuthInfo);
		}
		
		return success();
	}
	
	
	/**
	 * 会员列表更新积分
	 * @param param
	 * @return
	 */
	@PostMapping(value="/score/update")
	public JsonResult updateMemberScore(@RequestBody @Valid ScoreParam param) {
		
		// 判断修改后的值不能小于零、
		Integer score = param.getScore();
		//最小值
		Integer scoreDis = param.getScoreDis();
		if((score+scoreDis)<0) {
			return fail(JsonResultMessage.MSG_MEMBER_SCORE_NOT_ENOUGH);
		}
		
		Integer subAccountId = this.adminAuth.user().getSubAccountId();
		
		if(param.getUserId()!=null) {
			Integer[] arrayUserId = param.getUserId();
			int userNumber = arrayUserId.length;
			Byte tradeType = 4;
			Byte tradeFlow = 1;
			for(int i=0;i<userNumber;i++) {
				Integer userId = arrayUserId[i];
				shop().member.score.updateMemberScore(param,subAccountId,userId,tradeType,tradeFlow);
			}
		}else {
			return fail(JsonResultMessage.MSG_MEMBER_NOT_EXIST);
		}
		return success(param.toString());
	}
}
