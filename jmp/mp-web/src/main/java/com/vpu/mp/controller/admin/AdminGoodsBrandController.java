package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.brand.*;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品品牌控制器
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@RestController
public class AdminGoodsBrandController extends AdminBaseController {

    /**
     * 商品品牌分页查询
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/brand/list")
    public JsonResult getPageList(@RequestBody GoodsBrandPageListParam param) {
        return success(shop().goods.goodsBrand.getPageList(param));
    }

    /**
     * 新增
     * @param goodsBrand 新增品牌参数
     * @return JsonResult
     */
    @PostMapping("/api/admin/goods/brand/add")
    public JsonResult insert(@RequestBody GoodsBrandAddParam goodsBrand) {

        if (goodsBrand.getBrandName() == null) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_IS_NULL);
        }

        boolean isExist = shop().goods.goodsBrand.isBrandNameExist(null,goodsBrand.getBrandName());
        if (isExist) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        shop().goods.goodsBrand.insert(goodsBrand);

        return success();
    }

    /**
     * 品牌删除
     * @param id 品牌id
     */
    @GetMapping("/api/admin/goods/brand/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        if (id == null) {
            return fail(JsonResultCode.GOODS_BRAND_ID_IS_NULL);
        }
        shop().goods.goodsBrand.delete(id);
        return success();
    }

    /**
     * 修改
     */
    @PostMapping("/api/admin/goods/brand/update")
    public JsonResult update(@RequestBody GoodsBrandUpdateParam goodsBrand) {
        if (goodsBrand.getId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_ID_IS_NULL);
        }
        boolean isExist = shop().goods.goodsBrand.isBrandNameExist(goodsBrand.getId(),goodsBrand.getBrandName());
        if (isExist) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }
        shop().goods.goodsBrand.update(goodsBrand);
        return success();
    }
    /**
     * 查询单个记录
     * @param id 品牌id
     * @return JsonResult
     */
    @GetMapping("/api/admin/goods/brand/select/{id}")
    public JsonResult select(@PathVariable("id") Integer id) {
        if (id == null) {
            return fail(JsonResultCode.GOODS_BRAND_ID_IS_NULL);
        }
        return success(shop().goods.goodsBrand.select(id));
    }

    /**
     * 下来框列表查询
     *
     * @return
     */
    @PostMapping("/api/admin/goods/brand/name/list")
    public JsonResult listGoodsBrandName() {
        List<GoodsBrandSelectListVo> goodsBrands = shop().goods.goodsBrand.getGoodsBrandSelectList();
        return success(goodsBrands);
    }

    @PostMapping("/api/admin/goods/brand/batch/update")
    public JsonResult batchUpdateBrand(@RequestBody GoodsBrandBatchParam param){
        if (param.getClassifyId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }

        if (param.getIds() == null || param.getIds().size() == 0) {
            return success();
        }

        shop().goods.goodsBrand.batchUpdateBrand(param);

        return success();
    }

    /**
     * 品牌分类分页查询
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/brand/classify/list")
    public JsonResult getBrandClassifyPageList(@RequestBody GoodsBrandClassifyPageListParam param) {
        PageResult<GoodsBrandClassifyPageListVo> pageResult = shop().goods.goodsBrand.getBrandClassifyPageList(param);
        return success(pageResult);
    }

    /**
     * 品牌分类下拉框
     * @return
     */
    @GetMapping("/api/admin/goods/brand/classify/name/list")
    public JsonResult getBrandClassifyName(){
        List<GoodsBrandClassifyVo> brandClassifyList = shop().goods.goodsBrand.getBrandClassifyList();

        return success(brandClassifyList);
    }

    @PostMapping("/api/admin/goods/brand/classify/add")
    public JsonResult insertBrandClassify(@RequestBody GoodsBrandClassifyAddUpdateParam param) {

        if (param.getClassifyName() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_NAME_IS_NULL);
        }

        GoodsBrandService goodsBrand = shop().goods.goodsBrand;
        boolean exist = goodsBrand.isClassifyNameExist(null,param.getClassifyName());
        if (exist) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_NAME_EXIST);
        }

        goodsBrand.insertBrandClassify(param);

        return success();
    }

    @PostMapping("/api/admin/goods/brand/classify/update")
    public JsonResult updateBrandClassify(@RequestBody GoodsBrandClassifyAddUpdateParam param){

        if (param.getClassifyId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }
        GoodsBrandService goodsBrand = shop().goods.goodsBrand;

        if (goodsBrand.isClassifyNameExist(param.getClassifyId(),param.getClassifyName())) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        goodsBrand.updateBrandClassify(param);

        return success();
    }

    @GetMapping("/api/admin/goods/brand/classify/delete/{classifyId}")
    public JsonResult deleteBrandClassify(@PathVariable Integer classifyId) {
        if (classifyId == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }
        shop().goods.goodsBrand.deleteBrandClassify(classifyId);
        return success();
    }


    @PostMapping("/api/admin/goods/brand/config/set")
    public JsonResult setBrandConfig(@RequestBody GoodsBrandConfig config){
        shop().config.goodsBrandConfigService.setGoodsBrandConfig(config);
        return success();
    }

    @GetMapping("/api/admin/goods/brand/config/get")
    public JsonResult getBrandConfig(){
        GoodsBrandConfig goodsBrandConfig = shop().config.goodsBrandConfigService.getGoodsBrandConfig();
        return success(goodsBrandConfig);
    }

}

