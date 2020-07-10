package com.vpu.mp.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.goods.param.MedicalGoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.vo.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.vo.GoodsSelectVo;
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
     * @param goodsEntity
     * @return
     */
    @PostMapping("/api/admin/medical/goods/insert")
    public JsonResult insert(@RequestBody GoodsEntity goodsEntity){

        //判断商品名称是否为空
        if (StrUtil.isBlank(goodsEntity.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }

        //如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsSpecProducts参数
        if (goodsEntity.getGoodsSpecProducts() == null || goodsEntity.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品主图是否为空
        if (StrUtil.isBlank(goodsEntity.getGoodsImg())) {
            return fail(JsonResultCode.MEDICAL_GOODS_MAIN_IMG_IS_NULL);
        }
        try {
            shop().medicalGoodsService.insert(shopId(), goodsEntity);
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            return fail(JsonResultCode.MEDICAL_GOODS_SKU_CONTENT_ILLEGAL,e.getMessage());
        }

        return success();
    }

    /**
     * 药品修改
     * @param goodsEntity
     * @return
     */
    @PostMapping("/api/admin/medical/goods/update")
    public JsonResult update(@RequestBody GoodsEntity goodsEntity){

        if (goodsEntity.getGoodsId() == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        //判断商品名称是否为空
        if (StrUtil.isBlank(goodsEntity.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }
        //如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsSpecProducts参数
        if (goodsEntity.getGoodsSpecProducts() == null || goodsEntity.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品主图是否为空
        if (StrUtil.isBlank(goodsEntity.getGoodsImg())) {
            return fail(JsonResultCode.MEDICAL_GOODS_MAIN_IMG_IS_NULL);
        }

        try {
            shop().medicalGoodsService.update(shopId(), goodsEntity);
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

        shop().medicalGoodsService.deleteByGoodsId(goodsId);

        return success();
    }

    /**
     * 药品查询
     * @param goodsId 药品id
     * @return
     */
    @GetMapping("/api/admin/medical/goods/get/{goodsId}")
    public JsonResult getByGoodsId(@PathVariable("goodsId") Integer goodsId) {
        if (goodsId == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        GoodsSelectVo goodsSelectVo = shop().medicalGoodsService.getGoodsDetailByGoodsId(goodsId);

        return success(goodsSelectVo);
    }

    /**
     * 药品分页查询
     * @param pageListParam 分页信息
     * @return
     */
    @PostMapping("/api/admin/medical/goods/page/list")
    public JsonResult getGoodsPageList(@RequestBody MedicalGoodsPageListParam pageListParam){
        PageResult<GoodsPageListVo> goodsPageList = shop().medicalGoodsService.getGoodsPageList(pageListParam);

        return success(goodsPageList);
    }
}
