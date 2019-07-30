package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelPageListParam;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelVo;

/**
 * @author 黄荣刚
 * @date 2019年7月4日
 *
 */
@RestController
public class AdminGoodsLabelController extends AdminBaseController {


    /**
     *	 商品标签分页查询
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/label/list")
    public JsonResult getPageList(@RequestBody GoodsLabelPageListParam param) {

        PageResult<GoodsLabelVo> pageResult = shop().goods.goodsLabel.getPageList(param);
        return success(pageResult);
    }
    
    /**
     *   
              *   增加商品标签
     * @param goodsLabel
     * @return
     */
    @PostMapping("/api/admin/goods/label/add")
    public JsonResult insert(@RequestBody GoodsLabel goodsLabel) {
    	if(shop().goods.goodsLabel.isLabelNameExist(goodsLabel)) {
    		return fail(JsonResultCode.GOODS_LABEL_NAME_EXIST);
    	}
    	shop().goods.goodsLabel.insert(goodsLabel);
    	return success(JsonResultCode.CODE_SUCCESS);
    }
    
    /**
              * 删除商品标签（只是将删除标志位置1）
     * @param goodsLabel
     * @return
     */
    @PostMapping("/api/admin/goods/label/delete/{id}")
    public JsonResult delete(@PathVariable Integer id) {
    	int result = shop().goods.goodsLabel.delete(id);
    	if(result >0) {
    		return success(JsonResultCode.CODE_SUCCESS);
    	}
    	return fail(JsonResultCode.CODE_FAIL);
    }
    
    /**
              * 更新商品标签
     * @param goodsLabel
     * @return
     */
    @PostMapping("/api/admin/goods/label/update")
    public JsonResult update(@RequestBody GoodsLabel goodsLabel) {
    	if(shop().goods.goodsLabel.selectById(goodsLabel.getId())==null) {
    		return fail(JsonResultCode.GOODS_LABEL_NOT_EXIST);
    	}
    	if(shop().goods.goodsLabel.isOtherLabelNameExist(goodsLabel)) {
    		return fail(JsonResultCode.GOODS_LABEL_NAME_EXIST);
    	}
    	shop().goods.goodsLabel.update(goodsLabel);
    	return success();
    }
    
    /**
     * 根据商品标签ID查询指定商品标签
     * @param id
     * @return
     */
    @GetMapping("/api/admin/goods/label/select/{id}")
    public JsonResult select(@PathVariable Integer id) {
    	GoodsLabelVo goodsLabelVo = shop().goods.goodsLabel.selectGoodsLabelVoById(id);
    	if(goodsLabelVo != null) {
    		return success(goodsLabelVo);
    	}
    	return fail(JsonResultCode.GOODS_LABEL_NOT_EXIST);
    }
    
    
}
