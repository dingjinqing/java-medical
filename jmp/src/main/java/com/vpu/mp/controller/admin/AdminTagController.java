package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultMessage;
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
		System.out.println(param);
		PageResult<TagInfoVo> pageResult = shop().tag.getPageList(param);
		return this.success(pageResult);
	}
	
	
	@PostMapping(value = "/api/admin/tag/add")
	public JsonResult addTag(@RequestBody @Valid TagPageListParam param,BindingResult result) {

		//测试参数
		if(result.hasErrors()) {
			return this.fail(result.getFieldError().getDefaultMessage());
		}
		
		//check is the tagName is exits
		boolean tagNameExists = shop().tag.tagNameExists(param.getTagName());
		// if tag name exists then return
		if(tagNameExists) {
			JsonResult r = this.fail(JsonResultMessage.MSG_MEMBER_TAG_NAME_EXIST);
			r.setMessage(param.getTagName()+r.getMessage());
			return r;
		}
		
		// insert into database
		shop().tag.addTagName(param.getTagName());
	
		return this.success();
	}
	
}
