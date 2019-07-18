package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;

/**
 * 会员管理
 * @author 黄壮壮
 * 2019-07-08 09:30
 */
@RestController
public class AdminMemberController extends AdminBaseController{

	/**
	 * 通用会员列表弹窗分页查询
	 * @return
	 */
	@PostMapping("/api/admin/member/common/list")
	public JsonResult getCommonPageList(@RequestBody CommonMemberPageListQueryParam param) {
		PageResult<CommonMemberPageListQueryVo> pageResult = this.shop().member.getCommonPageList(param);
		return this.success(pageResult);
	}
	
	/**
	 * 会员列表
	 * @return
	 */
	@PostMapping("api/admin/member/list")
	public JsonResult getPageList(@RequestBody MemberPageListParam param) {

		PageResult<MemberInfoVo> pageResult = this.shop().member.getPageList(param);
		return this.success(pageResult);
	}
}
