package com.vpu.mp.controller.admin;

import java.util.List;

import com.vpu.mp.service.pojo.shop.market.friendpromote.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;

/**
  *   好友助力活动控制器
 * @author liangchen
 * @date 2019年8月7日
 */
@RestController
@RequestMapping("/api/admin/market/promote")
public class AdminFriendPromoteController extends AdminBaseController{
	/**
	  *  分页查询好友助力活动列表
	 *
	 * @param param
	 * @return 
	 */
	@PostMapping("/list")
	public JsonResult getList(@RequestBody FriendPromoteListParam param) {
		PageResult<FriendPromoteListVo> listVo = shop().friendPromoteService.friendPromoteList(param);
		return success(listVo);
	}
	/**
	 * 启用或停用活动
	 *
	 * @param param
	 * @return 
	 */
	@PostMapping("/switch")
	public JsonResult startOrBlock(@RequestBody FriendPromoteOptionParam param) {
		shop().friendPromoteService.startOrBlock(param);
		return success();
	}
	/**
	 * 删除单个活动
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/delete")
	public JsonResult deleteAct(@RequestBody FriendPromoteOptionParam param) {
		shop().friendPromoteService.deleteAct(param);
		return success();
	}
	
	/**
	 * 活动奖励领取明细
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/receive")
	public JsonResult receiveDetail(@RequestBody FriendPromoteReceiveParam param) {
		PageResult<FriendPromoteReceiveVo> pageResult = 
				shop().friendPromoteService.receiveDetail(param);
		return success(pageResult);
	}

	/**
	 * 活动发起明细
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/launch")
	public JsonResult launchDetail(@RequestBody FriendPromoteLaunchParam param) {
		PageResult<FriendPromoteLaunchVo> pageResult = 
				shop().friendPromoteService.launchDetail(param);
		return success(pageResult);
	}
	
	/**
	 * 活动参与明细
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/participate")
	public JsonResult participateDetail(@RequestBody FriendPromoteParticipateParam param) {
		PageResult<FriendPromoteParticipateVo> pageResult = 
				shop().friendPromoteService.participateDetail(param);
		return success(pageResult);
	}
	
	/**
	 * 添加好友助力活动
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult addActivity(@RequestBody FriendPromoteAddParam param) {
		shop().friendPromoteService.addActivity(param);
		return success();
	}
	
	/**
	 * 查询单个好友助力活动
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/select")
	public JsonResult selectOne(@RequestBody FriendPromoteSelectParam param) {
		FriendPromoteSelectVo vo	= shop().friendPromoteService.selectOne(param);
		return success(vo);
	}
	
	/**
	 * 修改好友助力活动信息
	 * 
	 * @param param 
	 * @return
	 */
	@PostMapping("/update")
	public JsonResult updateActivity(@RequestBody FriendPromoteUpdateParam param) {
		shop().friendPromoteService.updateActivity(param);
		return success();
	}
    /**
     * 根据prdId查询商品信息
     *
     * @param param prdId
     * @return goodsInfo
     */
    @PostMapping("/goodsInfo")
    public JsonResult getGoodsInfoByPrdId(@RequestBody FriendPromoteUpdateParam param) {
        GoodsInfo goodsInfo = shop().friendPromoteService.getGoodsInfo(param.getId());
        return success(goodsInfo);
    }

    /**
     * 活动效果展示
     *
     * @param param 发起id
     * @return 活动效果
     */
    @PostMapping("/analysis")
    public JsonResult getEffectData(@RequestBody FriendPromoteSelectParam param) {

        ActEffectDataVo vo = shop().friendPromoteService.getEffectData(param);

        return success(vo);
    }
}
