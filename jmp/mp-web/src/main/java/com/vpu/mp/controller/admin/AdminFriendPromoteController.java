package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListVo;

/**
  *   好友助力活动控制器
 * @author liangchen
 * @date 2019年8月7日
 */
@RestController
public class AdminFriendPromoteController extends AdminBaseController{
	/**
	  *  分页查询好友助力活动列表
	 *
	 * @param param
	 * @return 
	 */
	@RequestMapping("api/admin/market/friendpromote/list")
	public JsonResult getList(@RequestBody FriendPromoteListParam param) {
		PageResult<FriendPromoteListVo> listVo = shop().friendPromoteService.friendPromoteList(param);
		return success(listVo);
	}
}
