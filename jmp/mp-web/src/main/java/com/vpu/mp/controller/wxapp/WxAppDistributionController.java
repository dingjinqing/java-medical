package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.wxapp.distribution.ActivationInfoVo;
import com.vpu.mp.service.pojo.wxapp.distribution.DistributorApplyDetailParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
		List<DistributorGroupListVo> groupList = shop().brokerage.getGroupList();
		return this.success(groupList);
	}

    /**
     * 申请分销员页面信息
     * @return
     */
	@PostMapping("distributor/activation")
    public JsonResult activationInfo(){
        Integer userId = wxAppAuth.user().getUserId();
        ActivationInfoVo activationInfo = shop().mpDistribution.getActivationInfo(userId,getLang());
        return this.success(activationInfo);
    }
	
	/**
	 * 用户申请成为分销员接口
	 * @param param
	 * @return
	 */
	@PostMapping("distributor/apply")
	public JsonResult saveDistributorApply(@RequestBody DistributorApplyParam param) {
	    System.out.println(param);
		Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(userId);
		int res = shop().mpDistribution.distributorApply(param);
		return this.success(res);
	}

    /**
     * 获取分销员审核状态
     * @param param
     * @return
     */
    @PostMapping("distributor/apply/detail")
	public JsonResult getDistributorApplyDetail(@RequestBody DistributorApplyDetailParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        DistributorApplyDetailParam info = shop().mpDistribution.getDistributorApplyDetail(param);
        return this.success(info);
    }

    /**
     * 获取分销推广文案信息
     * @return
     */
    @PostMapping("document")
    public JsonResult getDistributorDocument(){
        //获取分销推广文案信息
        DistributionDocumentParam distributorDoc = shop().mpDistribution.getDistributorDoc();
        return this.success(distributorDoc);
    }
}
