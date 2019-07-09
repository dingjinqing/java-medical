package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsComment;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswer;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheck;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 商品品牌控制器
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

		PageResult<GoodsComment> pageResult = shop().goods.goodsComment.getPageList(param);

		return success(pageResult);
	}

	/**
	 * 评论审核分页查询
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/check")
	public JsonResult getPageList(@RequestBody GoodsCommentCheckPageListParam param) {

		PageResult<GoodsCommentCheck> pageResult = shop().goods.goodsComment.getCheckPageList(param);

		return success(pageResult);
	}

	/**
	 * 评论回复
	 * 
	 * @param param
	 * @return
	 *
	 */
	@PostMapping("api/admin/goods/comment/answer")
	public JsonResult insert(@RequestBody GoodsCommentAnswer goodsCommentAnswer) {

		shop().goods.goodsComment.insertAnswer(goodsCommentAnswer);

		return success();
	}


	/**
	 * 删除
	 *
	 * @param goodsBrand
	 * @return
	 */
	@PostMapping("/api/admin/goods/comment/delete")
	public JsonResult delete(@RequestBody GoodsComment goodsComment) {

		shop().goods.goodsComment.delete(goodsComment);

		return success();
	}

	/**
	 * 修改审核状态
	 *
	 * @param goodsBrand
	 * @return
	 */

	@PostMapping("/api/admin/goods/comment/passflag")
	public JsonResult passflag(@RequestBody GoodsComment goodsComment) {

		shop().goods.goodsComment.passflag(goodsComment);

		return success();
	}
	
	@PostMapping("/api/admin/goods/comment/refuseflag")
	public JsonResult refuseflag(@RequestBody GoodsComment goodsComment) {

		shop().goods.goodsComment.refuseflag(goodsComment);

		return success();
	}

}
