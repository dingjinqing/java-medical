package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListVo;

/**
* @author 黄壮壮
* @Date: 2019年8月13日
* @Description: 积分管理
*/
@RestController
public class AdminMemberScoreController extends AdminBaseController {
	@PostMapping("/api/admin/member/score/list")
	public JsonResult getScoreDetails(@RequestBody @Valid ScorePageListParam param) {
		logger().info("正在会员用户积分明细");
		PageResult<ScorePageListVo> pageList = shop().member.score.getPageListOfScoreDetails(param);
		return success(pageList);
	}
	
	
	/**
	 * 更新积分
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/member/score/update")
	public JsonResult updateMemberScore(@RequestBody @Valid ScoreParam param) {
		// 判断修改后的值不能小于零
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
