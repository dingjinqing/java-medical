package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.member.DeleteTagParam;
import com.vpu.mp.service.pojo.shop.member.TagInfoParam;
import com.vpu.mp.service.pojo.shop.member.TagPageListParam;
import com.vpu.mp.service.pojo.shop.member.UpdateTagParam;

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
	public JsonResult getTagList(@RequestBody @Valid TagPageListParam param) {
		
		return this.success(shop().tag.getPageList(param));
	}
	
	/**
	 * 添加标签
	 * @param param
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/api/admin/tag/add")
	public JsonResult addTag(@RequestBody @Valid TagInfoParam param,BindingResult result) {

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
	
	/**
	 * 删除标签
	 * @param param
	 * @return
	 */
	@PostMapping(value="/api/admin/tag/delete")
	public JsonResult deleteTag(@RequestBody  @Valid DeleteTagParam param) {
		
		//check tagName is exist
		boolean tagIdExists = shop().tag.tagIdExists(param.getTagId());
		if(!tagIdExists) {
			return this.success();
		}
		shop().tag.deleteTag(param.getTagId());
		return this.success();
	}
	
	/**
	 * 更新标签名称
	 * @param param
	 * @return
	 */
	@PostMapping(value="/api/admin/tag/update")
	public JsonResult updateTagName(@RequestBody @Valid UpdateTagParam param) {
		int result =  shop().tag.updateTag(param);
		System.out.println(result);
		if(result != 1) {
			return this.fail();
		}
		return this.success();
	}
}
