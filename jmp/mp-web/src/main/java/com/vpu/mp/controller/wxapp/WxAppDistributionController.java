package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import com.vpu.mp.service.pojo.wxapp.distribution.*;
import com.vpu.mp.service.pojo.wxapp.distribution.withdraw.WithdrawDetailVo;
import com.vpu.mp.service.pojo.wxapp.distribution.withdraw.WithdrawRecordParam;
import com.vpu.mp.service.pojo.wxapp.distribution.withdraw.WithdrawRecordVo;
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

    /**
     * 分销中心
     * @return
     */
    @PostMapping("rebateCenter")
    public JsonResult rebateCenter(){
        Integer userId = wxAppAuth.user().getUserId();
        RebateCenterVo rebateCenter = shop().mpDistribution.rebateCenter(userId);
        return this.success(rebateCenter);
    }

    /**
     * 分销员邀请的下级用户列表
     * @param param
     * @return
     */
    @PostMapping("myInvite")
    public JsonResult myInviteUser(@RequestBody DistributorInvitedListParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        DistributorInvitedListVo inviteList = shop().mpDistribution.myInviteUser(param);
        return this.success(inviteList);
    }

    /**
     * 查看分销员等级列表
     * @return
     */
    @PostMapping("distributor/level/list")
    public JsonResult distributorLevelList(){
        List<DistributorLevelInfoVo> distributorLevelInfoVos = shop().mpDistribution.distributorLevelList();
        return this.success(distributorLevelInfoVos);
    }

    /**
     * 分销员邀请用户返利订单列表
     * @param param
     * @return
     */
    @PostMapping("rebateOrder")
    public JsonResult rebateOrderList(@RequestBody RebateOrderParam param){
        PageResult<RebateOrderVo> rebateOrderVo = shop().mpDistribution.rebateOrder(param);
        return this.success(rebateOrderVo);
    }

    //分销改价相关
    /**
     * 商品分销改价页信息
     * @param param
     * @return
     */
    @PostMapping("rebate/goods/config")
    public JsonResult rebateGoodsCfg(@RequestBody RebateGoodsCfgParam param) {
        GoodsRebateChangePriceVo goodsRebateChangePriceVo = shop().mpDisGoods.rebateGoodsCfg(param);
        return this.success(goodsRebateChangePriceVo);
    }

    /**
     * 分享人信息
     * @param param
     * @return
     */
    @PostMapping("rebate/user/share")
    public JsonResult shareUserInfo(@RequestBody ShareUserInfoParam param){
        ShareUserInfoVo shareUserInfo = shop().mpDisGoods.getShareUserInfo(param);
        return this.success(shareUserInfo);
    }

    /**
     * 用户(分销员)之间建立绑定关系
     * @param param
     * @return
     */
    @PostMapping("/user/bind")
    public JsonResult userBind(@RequestBody UserBindParam param){
        shop().mpDistribution.userBind(param);
        return this.success();
    }

    /**
     *提现详情
     * @return
     */
    @PostMapping("/withdraw/detail")
    public JsonResult withdrawDetail(){
        Integer userId = wxAppAuth.user().getUserId();
        Integer shopId = wxAppAuth.user().getShopId();
        WithdrawDetailVo withdrawDetailVo = shop().mpDistribution.withdrawDetail(userId, shopId());
        return this.success(withdrawDetailVo);
    }

    /**
     * 提现记录
     * @param param
     * @return
     */
    @PostMapping("/withdraw/record")
    public JsonResult withdrawRecord(@RequestBody WithdrawRecordParam param) {
        WithdrawRecordVo withdrawRecordVo = shop().mpDistribution.withdrawRecord(param);
        return this.success(withdrawRecordVo);
    }

    /**
     * 分销员佣金排行榜
     * @param  param
     * @return
     */
    @PostMapping("/rebate/ranking")
    public JsonResult rebateRankingList(@RequestBody RebateRankingParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        RebateRankingListVo rebateRankingList = shop().mpDistribution.getRebateRankingList(param);
        return this.success(rebateRankingList);
    }

}
