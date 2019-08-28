package com.vpu.mp.controller.admin;

import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.USED_SCORE_STATUS;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.exception.MpException;
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
	 * @param param ScoreParam积分入参-参数
	 * @return JsonResult
	 */
	@PostMapping("/api/admin/member/score/update")
	public JsonResult updateMemberScore(@RequestBody @Valid ScoreParam param) {
		/** -判断修改后的值不能小于零 */
		Integer score = param.getScore();
		/** -最小值 */
		Integer scoreDis = param.getScoreDis();
		if((score+scoreDis)<0) {
			return fail(JsonResultMessage.MSG_MEMBER_SCORE_NOT_ENOUGH);
		}
		/** -获取操作员id */
		Integer subAccountId = this.adminAuth.user().getSubAccountId();
		
		if(param.getUserId()!=null) {
			Integer[] arrayUserId = param.getUserId();
			int userNumber = arrayUserId.length;
			
			/** -交易明细类型 */
			Byte tradeType = 4;
			/** -资金流向 */
			Byte tradeFlow = 1;
			/** -处理积分变动信息 */
			
			/** -积分大于零为充值,反之为消耗  */
			if(score>0) {
				param.setScoreStatus(NO_USE_SCORE_STATUS);
			}else {
				param.setScoreStatus(USED_SCORE_STATUS);
			}
			
			
			for(int i=0;i<userNumber;i++) {
				Integer userId = arrayUserId[i];
					/** -处理积分变动产生的异常 */
					try {
						shop().member.score.updateMemberScore(param,subAccountId,userId,tradeType,tradeFlow);
					} catch (MpException e) {
						logger().info("积分更新失败");
						return fail(e.getErrorCode().getMessage());
					}
			}
		}else {
			return fail(JsonResultMessage.MSG_MEMBER_NOT_EXIST);
		}
		return success(param.toString());
	}
}
