package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsComment;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 	商品品牌控制器
 *
 * @author liangchen
 * @date 2019年7月7日
 */
@RestController
public class AdminGoodsCommentController extends AdminBaseController {

 
    /**
     *	 商品评论分页查询
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/comment/list")
    public JsonResult getPageList(@RequestBody GoodsCommentPageListParam param) {

        PageResult<GoodsComment> pageResult = shop().goods.goodsComment.getPageList(param);

        return success(pageResult);
    }

   /* *//**
     * 	新增
     *
     * @param goodsBrand
     * @return
     *//*
    @PostMapping("/api/admin/goods/brand/add")
    public JsonResult insert(@RequestBody GoodsBrand goodsBrand) {

        boolean isExist=shop().goods.goodsBrand.isBrandNameExist(goodsBrand);
        if (isExist){
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        shop().goods.goodsBrand.insert(goodsBrand);

        return success();
    }

    *//**
     * 	删除
     *
     * @param goodsBrand
     * @return
     *//*
    @PostMapping("/api/admin/goods/brand/delete")
    public JsonResult delete(@RequestBody GoodsBrand goodsBrand) {

        shop().goods.goodsBrand.delete(goodsBrand);

        return success();
    }

    *//**
     * 	修改
     *
     * @param goodsBrand
     * @return
     *//*
    @PostMapping("/api/admin/goods/brand/update")
    public JsonResult update(@RequestBody GoodsBrand goodsBrand) {

        boolean isExist=shop().goods.goodsBrand.isOtherBrandNameExist(goodsBrand);
        if (isExist){
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        shop().goods.goodsBrand.update(goodsBrand);

        return success();
    }

    *//**
     * 查询单个记录
     * @param goodsBrand
     * @return
     *//*
    @PostMapping("/api/admin/goods/brand/select")
    public JsonResult select(@RequestBody GoodsBrand goodsBrand){
        GoodsBrand goodsBrandRet=shop().goods.goodsBrand.select(goodsBrand);
        return success(goodsBrandRet);
    }
*/
}
