package com.vpu.mp.controller.wxapp;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;

/**
 * 小程序端分销相关控制器
 * @author 常乐
 * 2019年10月16日
 */
@RestController
@RequestMapping("/api/wxapp/distribution")
public class WxAppDistributionController extends WxAppBaseController{
	/**
	 * 分销员分组列表
	 * @return
	 */
	@PostMapping("group/list")
	public JsonResult distributorGroupList() {
		Integer userId = wxAppAuth.user().getUserId();
		System.out.println(userId);
		List<DistributorGroupListVo> groupList = shop().brokerage.getGroupList();
		return this.success(groupList);
	}
	
	/**
	 * 用户申请成为分销员
	 * @param param
	 * @return
	 */
	@PostMapping("distributor/apply")
	public JsonResult saveDistributorApply(@RequestBody DistributorApplyParam param) {	
		Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(userId);
		int res = shop().mpDistribution.distributorApply(param);
		return this.success(res);
	}
}
