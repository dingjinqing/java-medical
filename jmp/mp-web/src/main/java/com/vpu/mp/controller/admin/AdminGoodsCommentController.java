package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddCommParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswerParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentConfigParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentIdParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentVo;

/**
 * 商品评论控制器
 *
 * @author liangchen
 * @date 2019年7月7日
 */

@RestController
@RequestMapping("/api/admin/goods/comment")
public class AdminGoodsCommentController extends AdminBaseController {

	/**
	 * 商品评论分页查询
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
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
	@PostMapping("/checklist")
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
	@PostMapping("/answer")
	public JsonResult insertAnswer(@RequestBody GoodsCommentAnswerParam goodsCommentAnswer) {

		shop().goods.goodsComment.insertAnswer(goodsCommentAnswer);

		return success();
	}

	/**
	 * 删除回复
	 *
	 * @param goodsCommentId
	 * @return
	 */
	@PostMapping("/delAnswer")
	public JsonResult delAnswer(@RequestBody GoodsCommentIdParam goodsCommentId) {

		shop().goods.goodsComment.delAnswer(goodsCommentId);

		return success();
	}

	/**
	 * 删除评论
	 *
	 * @param goodsCommentId
	 * @return
	 */
	@PostMapping("/delete")
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

	@PostMapping("/passflag")
	public JsonResult passflag(@RequestBody GoodsCommentIdParam goodsCommentId) {

		shop().goods.goodsComment.passflag(goodsCommentId);

		return success();
	}
	
	@PostMapping("/refuseflag")
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
	@PostMapping("/checkconfig")
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
	@PostMapping("/switchconfig")
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
	@PostMapping("/addlist")
	public JsonResult getAddPageList(@RequestBody GoodsCommentPageListParam param) {

		PageResult<GoodsCommentAddListVo> pageResult = shop().goods.goodsComment.getAddList(param);

		return success(pageResult);
	}
	
	
	/**
	 * 手动添加评论
	 *
	 * @param goodsCommentAddComm
	 * @return
	 */
	@PostMapping("/addcomm")
	public JsonResult addComment(@RequestBody GoodsCommentAddCommParam goodsCommentAddComm) {

		shop().goods.goodsComment.addComment(goodsCommentAddComm);

		return success();
	}
	
}
