package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.member.TagInfoVo;
import com.vpu.mp.service.pojo.shop.member.TagPageListParam;

/**
 * 会员标签管理
 * 
 * @author 黄壮壮 2019-07-09 10:19
 */
@RestController
public class AdminTagController extends AdminBaseController {

	/**
	 * 分页获取全部标签列表
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/api/admin/tag/list")
	public JsonResult getTagList(@RequestBody TagPageListParam param) {
		//TODO test
//		String name = new Object(){}.getClass().getEnclosingMethod().getName();
//		System.out.println("1. Tag管理  in "+name);
//		System.out.println(param);
		
		
		//this.shop().tag.getPageList(param);
		//TODO 测试用的shopid
		PageResult<TagInfoVo> pageResult = this.saas.getShopApp(4891645).tag.getPageList(param);
		return this.success(pageResult);
	}
	
	
	@PostMapping(value = "/api/admin/tag/add")
	public JsonResult addTag(@RequestBody @Valid TagPageListParam param,BindingResult result) {
		//TODO test
		System.out.println(param);
		if(result.hasErrors()) {
			return this.fail(result.getFieldError().getDefaultMessage());
		}
		return this.success();
	}
	
}
