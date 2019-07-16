package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.*;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.goods.GoodsService;
import org.jooq.tools.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年07月08日
 */
@RestController
public class AdminGoodsController extends AdminBaseController {

    /**
     * 商品分页查询
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/list")
    public JsonResult getPageList(@RequestBody GoodsPageListParam param) {
        PageResult<GoodsPageListVo> pageList = shop().goods.getPageList(param);

        return success(pageList);
    }

    /**
     * 商品新增
     * 如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsspecProducts参数
     *
     * @param goods
     * @return
     */
    @PostMapping("/api/admin/goods/add")
    public JsonResult insert(@RequestBody Goods goods) {

        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }

        if (goods.getGoodsName() == null) {
            return fail(JsonResultCode.GOODS_SORT_NAME_IS_NULL);
        }

        //存在重复值则直接返回
        JsonResult result = columnValueExistCheckForInsert(goods);
        if (result.getError() != 0) {
            return result;
        }

        shop().goods.insert(goods);
        return result;
    }

    /**
     * 商品新增接口数据重复检查（非原子操作）
     *
     * @param goods
     * @return
     */
    private JsonResult columnValueExistCheckForInsert(Goods goods) {
        GoodsService goodsService = shop().goods;

        GoodsColumnCheckExistParam gcep = new GoodsColumnCheckExistParam();
        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckForEnum.E_GOODS);

        //检查商品名称是否重复
        gcep.setGoodsName(goods.getGoodsName());
        if (goodsService.isColumnValueExist(gcep)) {
            return fail(JsonResultCode.GOODS_NAME_EXIST);
        }
        gcep.setGoodsName(null);

        //用户输入了商品货号则进行检查是否重复
        if (goods.getGoodsSn() != null) {
            gcep.setGoodsSn(goods.getGoodsSn());
            if (goodsService.isColumnValueExist(gcep)) {
                return fail(JsonResultCode.GOODS_SN_EXIST);
            }
            gcep.setGoodsSn(null);
        }

        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckForEnum.E_GOODS_SPEC_PRODUCTION);

        //检查sku sn是否重复
        for (GoodsSpecProduct goodsSpecProduct : goods.getGoodsSpecProducts()) {
            if (!StringUtils.isBlank(goodsSpecProduct.getPrdSn())) {
                gcep.setPrdSn(goodsSpecProduct.getPrdSn());
                if (goodsService.isColumnValueExist(gcep)) {
                    return fail(JsonResultCode.GOODS_SPEC_PRD_SN_EXIST);
                }
            }
        }

        //检查规格名称是否存在重复
        return isSpecNameOrValueRepeat(goods.getGoodsSpecs());
    }

    /**
     * 查询字段值在数据库内是否重复
     *
     * @param goodsColumnCheckExistParam
     * @return
     */
    @PostMapping("/api/admin/goods/columns/exist")
    public JsonResult isColumnValueExist(@RequestBody GoodsColumnCheckExistParam goodsColumnCheckExistParam) {
        boolean isExist = shop().goods.isColumnValueExist(goodsColumnCheckExistParam);
        if (isExist) {
            return success();
        } else {
            return fail();
        }
    }

    @PostMapping("/api/admin/goods/batch")
    public JsonResult batchOperate(@RequestBody GoodsBatchOperateParam param) {
        shop().goods.batchOperate(param);
        return success();
    }

    @PostMapping("/api/admin/goods/delete")
    public JsonResult delete(@RequestBody GoodsBatchOperateParam param) {
        if (param.getGoodsIds() == null || param.getGoodsIds().size() == 0) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }

        shop().goods.delete(param);
        return success();
    }

    @PostMapping("/api/admin/goods/update")
    public JsonResult update(@RequestBody Goods goods) {
        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }

        //存在重复值则直接返回
        JsonResult result = columnValueExistCheckForUpdate(goods);
        if (result.getError() != 0) {
            return result;
        }

        shop().goods.update(goods);

        return success();
    }

    /**
     * 根据id值查询商品信息
     *
     * @param goods
     * @return
     */
    @PostMapping("/api/admin/goods/select")
    public JsonResult select(@RequestBody Goods goods) {
        if (goods.getGoodsId() == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }

        Goods goodsVo = shop().goods.select(goods.getGoodsId());

        return success(goodsVo);
    }

    private JsonResult columnValueExistCheckForUpdate(Goods goods) {
        GoodsService goodsService = shop().goods;

        GoodsColumnCheckExistParam gcep = new GoodsColumnCheckExistParam();
        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckForEnum.E_GOODS);

        //检查商品名称是否重复
        gcep.setGoodsName(goods.getGoodsName());
        gcep.setGoodsId(goods.getGoodsId());
        if (goodsService.isColumnValueExist(gcep)) {
            return fail(JsonResultCode.GOODS_NAME_EXIST);
        }
        gcep.setGoodsName(null);
        gcep.setGoodsId(null);

        //用户输入了商品货号则进行检查是否重复
        if (goods.getGoodsSn() != null) {
            gcep.setGoodsSn(goods.getGoodsSn());
            gcep.setGoodsId(goods.getGoodsId());
            if (goodsService.isColumnValueExist(gcep)) {
                return fail(JsonResultCode.GOODS_SN_EXIST);
            }
            gcep.setGoodsSn(null);
            gcep.setGoodsId(null);
        }

        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckForEnum.E_GOODS_SPEC_PRODUCTION);
        //检查sku sn是否重复
        for (GoodsSpecProduct goodsSpecProduct : goods.getGoodsSpecProducts()) {
            if (!StringUtils.isBlank(goodsSpecProduct.getPrdSn())) {
                gcep.setPrdSn(goodsSpecProduct.getPrdSn());
                gcep.setPrdId(goodsSpecProduct.getPrdId());
                if (goodsService.isColumnValueExist(gcep)) {
                    return fail(JsonResultCode.GOODS_SPEC_PRD_SN_EXIST);
                }
                gcep.setPrdId(null);
            }
        }

        //检查规格名称是否存在重复
        return isSpecNameOrValueRepeat(goods.getGoodsSpecs());
    }

    /**
     * 判断商品规格名和规格值是否内部自重复
     *
     * @param specs
     * @return
     */
    private JsonResult isSpecNameOrValueRepeat(List<GoodsSpec> specs) {
        if (specs == null) {
            return success();
        }

        Map<String, Object> specNameRepeatMap = new HashMap<>(specs.size());

        for (GoodsSpec goodsSpec : specs) {

            specNameRepeatMap.put(goodsSpec.getSpecName(), null);
            //检查同一规格下规格值是否重复
            List<GoodsSpecVal> goodsSpecVals = goodsSpec.getGoodsSpecVals();
            if (goodsSpecVals == null) {
                continue;
            }
            Map<String, Object> specValRepeatMap = new HashMap<>(goodsSpecVals.size());
            for (GoodsSpecVal goodsSpecVal : goodsSpecVals) {
                specValRepeatMap.put(goodsSpecVal.getSpecValName(), null);
            }
            if (specValRepeatMap.size() != goodsSpecVals.size()) {
                return fail(JsonResultCode.GOODS_SPEC_VAL_REPETITION);
            }
        }
        if (specs.size() != specNameRepeatMap.size()) {
            return fail(JsonResultCode.GOODS_SPEC_NAME_REPETITION);
        }

        return success();
    }
}
