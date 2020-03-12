package com.vpu.mp.controller.wxapp;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.GroupDetailVo;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.GroupStartParam;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.GroupStartVo;

/**
 * 组团瓜分积分
 * @author zhaojianqiang
 * @time 下午4:14:26
 */
@RestController
public class GroupIntegrationController extends WxAppBaseController {
	
	
	/**
	 * 开团或参团
	 * @return
	 */
	@PostMapping("/api/wxapp/pin/integration/start")
	public JsonResult startPinIntegrationGroup(@RequestBody @Valid GroupStartParam param) {
		GroupStartVo vo = shop().groupIntegration.startPinIntegrationGroup(param, wxAppAuth.user().getUserId());
		return success(vo);
		
	}
	
	/**
	 * 瓜分积分拼团详情
	 * @return
	 */
	@PostMapping("/api/wxapp/pin/integration/detail")
	public JsonResult pinIntegrationDetail(@RequestBody @Valid GroupStartParam param) {
		GroupDetailVo vo = shop().groupIntegration.pinIntegrationDetail(param, wxAppAuth.user().getUserId());
		return success(vo);
		
	}

}