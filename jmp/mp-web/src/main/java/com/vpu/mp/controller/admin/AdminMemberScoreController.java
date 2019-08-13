package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
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
}
