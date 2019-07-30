package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.brand.*;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/brand/list")
    public JsonResult getPageList(@RequestBody GoodsBrandPageListParam param) {

        PageResult<GoodsBrand> pageResult = shop().goods.goodsBrand.getPageList(param);

        return success(pageResult);
    }

    /**
     * 新增
     *
     * @param goodsBrand
     * @return
     */
    @PostMapping("/api/admin/goods/brand/add")
    public JsonResult insert(@RequestBody GoodsBrand goodsBrand) {

        if (goodsBrand.getBrandName() == null) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_IS_NULL);
        }

        boolean isExist = shop().goods.goodsBrand.isBrandNameExist(goodsBrand);
        if (isExist) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        shop().goods.goodsBrand.insert(goodsBrand);

        return success();
    }

    /**
     * 删除
     *
     * @param goodsBrand
     * @return
     */
    @PostMapping("/api/admin/goods/brand/delete")
    public JsonResult delete(@RequestBody GoodsBrand goodsBrand) {
        if (goodsBrand.getId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_ID_IS_NULL);
        }

        shop().goods.goodsBrand.delete(goodsBrand);

        return success();
    }

    /**
     * 修改
     *
     * @param goodsBrand
     * @return
     */
    @PostMapping("/api/admin/goods/brand/update")
    public JsonResult update(@RequestBody GoodsBrand goodsBrand) {
        if (goodsBrand.getId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_ID_IS_NULL);
        }

        boolean isExist = shop().goods.goodsBrand.isOtherBrandNameExist(goodsBrand);
        if (isExist) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        shop().goods.goodsBrand.update(goodsBrand);

        return success();
    }

    /**
     * 查询单个记录
     *
     * @param goodsBrand
     * @return
     */
    @PostMapping("/api/admin/goods/brand/select")
    public JsonResult select(@RequestBody GoodsBrand goodsBrand) {
        GoodsBrand goodsBrandRet = shop().goods.goodsBrand.select(goodsBrand);
        return success(goodsBrandRet);
    }

    /**
     * 下来框列表查询
     *
     * @return
     */
    @PostMapping("/api/admin/goods/brand/name/list")
    public JsonResult listGoodsBrandName() {
        List<GoodsBrandVo> goodsBrands = shop().goods.goodsBrand.listGoodsBrandName();
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
    public JsonResult getBrandClassifyPageList(@RequestBody GoodsBrandClassifyParam param) {
        PageResult<GoodsBrandClassifyVo> pageResult = shop().goods.goodsBrand.getBrandClassifyList(param);

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
    public JsonResult insertBrandClassify(@RequestBody  GoodsBrandClassifyParam param) {

        if (param.getClassifyName() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_NAME_IS_NULL);
        }

        GoodsBrandService goodsBrand = shop().goods.goodsBrand;
        boolean exist = goodsBrand.isClassifyNameExist(param);
        if (exist) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_NAME_EXIST);
        }

        goodsBrand.insertBrandClassify(param);

        return success();
    }

    @PostMapping("/api/admin/goods/brand/classify/select")
    public JsonResult selectBrandClassify(@RequestBody GoodsBrandClassifyParam param){
        if (param.getClassifyId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }

        GoodsBrandClassifyVo vo = shop().goods.goodsBrand.selectBrandClassify(param);

        return success(vo);
    }

    @PostMapping("/api/admin/goods/brand/classify/update")
    public JsonResult updateBrandClassify(@RequestBody GoodsBrandClassifyParam param){

        if (param.getClassifyId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }
        GoodsBrandService goodsBrand = shop().goods.goodsBrand;

        if (goodsBrand.isOtherClassifyNameExist(param)) {
            return fail(JsonResultCode.GOODS_BRAND_NAME_EXIST);
        }

        goodsBrand.updateBrandClassify(param);

        return success();
    }

    @PostMapping("/api/admin/goods/brand/classify/delete")
    public JsonResult deleteBrandClassify(@RequestBody GoodsBrandClassifyParam param) {
        if (param.getClassifyId() == null) {
            return fail(JsonResultCode.GOODS_BRAND_CALSSIFY_ID_IS_NULL);
        }

        shop().goods.goodsBrand.deleteBrandClassify(param);

        return success();
    }


    @PostMapping("/api/admin/goods/brand/config/set")
    public JsonResult setBrandConfig(@RequestBody GoodsBrandConfig config){
        shop().config.goodsBrandConfigService.setGoodsBrandConfig(config);
        return success();
    }

    @PostMapping("/api/admin/goods/brand/config/get")
    public JsonResult getBrandConfig(){
        GoodsBrandConfig goodsBrandConfig = shop().config.goodsBrandConfigService.getGoodsBrandConfig();
        return success(goodsBrandConfig);
    }

}

