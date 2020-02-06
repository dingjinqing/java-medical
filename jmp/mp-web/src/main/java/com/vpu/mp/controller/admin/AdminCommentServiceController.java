package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.store.comment.CommentIdParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentPageListParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> results = new HashMap<String, Object>(2) {{
            put("pageList", result);
            put("technicianTitle", shop().config.storeConfigService.getTechnicianTitle());
        }};
        return success(results);
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
