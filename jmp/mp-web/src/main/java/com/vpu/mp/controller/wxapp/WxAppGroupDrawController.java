package com.vpu.mp.controller.wxapp;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawInfoParam;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawParam;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawReturn;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawVo;

/**
 * 拼团抽奖
 * 
 * @author zhaojianqiang
 * @time 下午2:13:53
 */
@RestController
public class WxAppGroupDrawController extends WxAppBaseController {

	/**
	 * 拼团抽奖活动列表页
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/api/wxapp/groupdraw/list")
	public JsonResult groupDrawList(@RequestBody GroupDrawParam param) {
		GroupDrawVo vo = shop().groupDraw.groupDrawList(param.getGroupDrawId());
		if (vo == null) {
			// 活动不存在或没有可参与的活动商品
			return fail(JsonResultCode.GROUP_DRAW_FAIL);
		}
		return success(vo);
	}

	/**
	 * 参团详情
	 * 
	 * @return
	 */
	@PostMapping(value = "/api/wxapp/groupdraw/info")
	public JsonResult getGroupDrawInfo(@RequestBody @Valid GroupDrawInfoParam param) {
		GroupDrawReturn checkGroupDraw = shop().groupDraw.checkGroupDraw(param, wxAppAuth.user().getUserId());
		JsonResultCode code = checkGroupDraw.getCode();
		if (code != null && code.equals(JsonResultCode.CODE_SUCCESS)) {
			return success(checkGroupDraw.getVo());
		}
		return fail(code);
	}

}
