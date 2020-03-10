package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteAddParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteLaunchParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteLaunchVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteOptionParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteParticipateParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteParticipateVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteReceiveParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteReceiveVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteSelectParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteSelectVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteUpdateParam;

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
}
