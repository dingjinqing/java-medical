package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddCommParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswerParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentConfigParam;

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

		PageResult<GoodsCommentParam> pageResult = shop().goods.goodsComment.getPageList(param);

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
	 * @param goodsComment
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/delete")
	public JsonResult delete(@RequestBody GoodsCommentParam goodsComment) {

		shop().goods.goodsComment.delete(goodsComment);

		return success();
	}

	/**
	 * 修改审核状态
	 *
	 * @param goodsComment
	 * @return
	 */

	@PostMapping("/api/admin/goods/comment/passflag")
	public JsonResult passflag(@RequestBody GoodsCommentParam goodsComment) {

		shop().goods.goodsComment.passflag(goodsComment);

		return success();
	}
	
	@PostMapping("/api/admin/goods/comment/refuseflag")
	public JsonResult refuseflag(@RequestBody GoodsCommentParam goodsComment) {

		shop().goods.goodsComment.refuseflag(goodsComment);

		return success();
	}
	
	/**
	 * 修改审核状态
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
	 * 修改审核状态
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
