package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.comment.CommentIdParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentPageListParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;

/**
 * @author 黄荣刚
 * @date 2019年7月18日
 *服务评论 控制器
 */
@RestController
@RequestMapping("/api/admin/store/services")
public class AdminCommentServiceController extends AdminBaseController {

	/**
	 * 服务评论分页查询
	 * @param param
	 * @return
	 * 
	 * 
	 */
	@PostMapping("/comment/list")
	JsonResult getPageList(@RequestBody @Valid ServiceCommentPageListParam param){
		PageResult<ServiceCommentVo> result = shop().store.serviceComment.getPageList(param);
		return success(result);
	}
	
	@PostMapping("/comment/delete")
	JsonResult delete(@RequestBody @Valid CommentIdParam param) {
		shop().store.serviceComment.batchDelete(param.getCommentIdList());
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	@PostMapping("/comment/pass")
	JsonResult pass(@RequestBody @Valid CommentIdParam param) {
		shop().store.serviceComment.batchPass(param.getCommentIdList());
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	@PostMapping("/comment/refuse")
	JsonResult refuse(@RequestBody @Valid CommentIdParam param) {
		shop().store.serviceComment.batchRefuse(param.getCommentIdList());
		return success(JsonResultCode.CODE_SUCCESS);
	}
}
