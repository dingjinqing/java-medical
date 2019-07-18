package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddCommParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswerParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentConfigParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentIdParam;

/**
 * 商品评论控制器
 *
 * @author liangchen
 * @date 2019年7月7日
 */

@RestController
public class AdminGoodsCommentController extends AdminBaseController {

	/**
	 * 商品评论分页查询
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/list")
	public JsonResult getPageList(@RequestBody GoodsCommentPageListParam param) {

		PageResult<GoodsCommentVo> pageResult = shop().goods.goodsComment.getPageList(param);

		return success(pageResult);
	}

	/**
	 * 评论审核分页查询
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/checklist")
	public JsonResult getCheckPageList(@RequestBody GoodsCommentPageListParam param) {

		PageResult<GoodsCommentCheckListVo> pageResult = shop().goods.goodsComment.getCheckPageList(param);

		return success(pageResult);
	}

	/**
	 * 评论回复
	 * 
	 * @param goodsCommentAnswer
	 * @return
	 *
	 */
	@PostMapping("api/admin/goods/comment/answer")
	public JsonResult insertAnswer(@RequestBody GoodsCommentAnswerParam goodsCommentAnswer) {

		shop().goods.goodsComment.insertAnswer(goodsCommentAnswer);

		return success();
	}


	/**
	 * 删除
	 *
	 * @param goodsCommentId
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/delete")
	public JsonResult delete(@RequestBody GoodsCommentIdParam goodsCommentId) {

		shop().goods.goodsComment.delete(goodsCommentId);

		return success();
	}

	/**
	 * 修改审核状态
	 *
	 * @param goodsCommentId
	 * @return
	 */

	@PostMapping("/api/admin/goods/comment/passflag")
	public JsonResult passflag(@RequestBody GoodsCommentIdParam goodsCommentId) {

		shop().goods.goodsComment.passflag(goodsCommentId);

		return success();
	}
	
	@PostMapping("/api/admin/goods/comment/refuseflag")
	public JsonResult refuseflag(@RequestBody GoodsCommentIdParam goodsCommentId) {

		shop().goods.goodsComment.refuseflag(goodsCommentId);

		return success();
	}
	
	/**
	 * 修改审核配置
	 * 
	 * @param goodsCommentConfig
	 * @return
	 *
	 */
	@PostMapping("api/admin/goods/comment/checkconfig")
	public JsonResult checkConfig(@RequestBody GoodsCommentConfigParam goodsCommentConfig) {
		
			shop().config.commentConfigService.setCheckConfig(goodsCommentConfig.getV());

		return success();
	}
	
	/**
	 * 修改开关配置
	 * 
	 * @param goodsCommentConfig
	 * @return
	 *
	 */
	@PostMapping("api/admin/goods/comment/switchconfig")
	public JsonResult switchConfig(@RequestBody GoodsCommentConfigParam goodsCommentConfig) {
		
			shop().config.commentConfigService.setSwitchConfig(goodsCommentConfig.getV());

		return success();
	}
	/**
	 * 添加评论分页查询
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/addlist")
	public JsonResult getAddPageList(@RequestBody GoodsCommentPageListParam param) {

		PageResult<GoodsCommentAddListVo> pageResult = shop().goods.goodsComment.getAddList(param);

		return success(pageResult);
	}
	
	
	/**
	 * 添加评论分页查询
	 *
	 * @param goodsCommentAddComm
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/addcomm")
	public JsonResult addComment(@RequestBody GoodsCommentAddCommParam goodsCommentAddComm) {

		shop().goods.goodsComment.addComment(goodsCommentAddComm);

		return success();
	}
	
}
