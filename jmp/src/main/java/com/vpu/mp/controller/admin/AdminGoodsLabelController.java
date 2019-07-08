package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelPageListParam;

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

        PageResult<GoodsLabel> pageResult = shop().goods.goodsLabel.getPageList(param);
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
    	if(goodsLabel.getAddGoodsLabelCoupleList() != null) {
    		shop().goods.goodsLabelCouple.insert(goodsLabel);
    	}
    	return success();
    }
    
    /**
              * 删除商品标签（只是将删除标志位置1）
     * @param goodsLabel
     * @return
     */
    @PostMapping("/api/admin/goods/label/delete")
    public JsonResult delete(@RequestBody GoodsLabel goodsLabel) {
    	shop().goods.goodsLabel.delete(goodsLabel);
    	return success();
    }
    
    /**
              * 更新商品标签
     * @param goodsLabel
     * @return
     */
    @PostMapping("/api/admin/goods/label/update")
    public JsonResult update(@RequestBody GoodsLabel goodsLabel) {
    	if(shop().goods.goodsLabel.isOtherLabelNameExist(goodsLabel)) {
    		return fail(JsonResultCode.GOODS_LABEL_NAME_EXIST);
    	}
    	shop().goods.goodsLabel.update(goodsLabel);
    	shop().goods.goodsLabelCouple.batchInsert(goodsLabel.getAddGoodsLabelCoupleList());
    	shop().goods.goodsLabelCouple.batchDelete(goodsLabel.getDelGoodsLabelCoupleList());
    	return success();
    }
    
    /**
              * 根据商品标签ID查询指定商品标签
     * @param id
     * @return
     */
    @GetMapping("/api/admin/goods/label/select/{id}")
    public JsonResult select(@PathVariable Integer id) {
    	GoodsLabel goodsLabel = shop().goods.goodsLabel.selectById(id);
    	if(goodsLabel != null) {
    		List<GoodsLabelCouple> list = shop().goods.goodsLabelCouple.getList(goodsLabel);
    		goodsLabel.setGoodsLabelCoupleList(list);
    	}
    	return success(goodsLabel);
    }
    
    
}
