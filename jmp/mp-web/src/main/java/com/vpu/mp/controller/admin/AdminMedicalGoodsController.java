package com.vpu.mp.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.goods.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@RestController
@Slf4j
public class AdminMedicalGoodsController extends AdminBaseController{

    /**
     * 药品新增
     * @param goods
     * @return
     */
    @PostMapping("/api/admin/medical/goods/insert")
    public JsonResult insert(@RequestBody Goods goods){

        //判断商品名称是否为空
        if (StrUtil.isBlank(goods.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }

        //如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsSpecProducts参数
        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品主图是否为空
        if (StrUtil.isBlank(goods.getGoodsImg())) {
            return fail(JsonResultCode.MEDICAL_GOODS_MAIN_IMG_IS_NULL);
        }
        try {
            shop().medicalGoodsService.insert(shopId(),goods);
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            return fail(JsonResultCode.MEDICAL_GOODS_SKU_CONTENT_ILLEGAL,e.getMessage());
        }

        return success();
    }

    /**
     * 药品修改
     * @param goods
     * @return
     */
    @PostMapping("/api/admin/medical/goods/update")
    public JsonResult update(@RequestBody Goods goods){

        if (goods.getGoodsId() == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        //判断商品名称是否为空
        if (StrUtil.isBlank(goods.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }
        //如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsSpecProducts参数
        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品主图是否为空
        if (StrUtil.isBlank(goods.getGoodsImg())) {
            return fail(JsonResultCode.MEDICAL_GOODS_MAIN_IMG_IS_NULL);
        }

        try {
            shop().medicalGoodsService.update(shopId(),goods);
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            return fail(JsonResultCode.MEDICAL_GOODS_SKU_CONTENT_ILLEGAL,e.getMessage());
        }

        return success();
    }

    /**
     * 药品删除
     * @param goodsId 药品id
     * @return
     */
    @GetMapping("/api/admin/medical/goods/delete/{goodsId}")
    public JsonResult delete(@PathVariable("goodsId") Integer goodsId){
        if (goodsId == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }

        shop().medicalGoodsService.deleteByGoodsIds(goodsId);

        return success();
    }
}
