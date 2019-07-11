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

	
	@Override
	protected ShopApplication shop() {
		// TODO Auto-generated method stub
		return saas.getShopApp(471752);
	}
	
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
     * 	商品新增
     *
     * @param goods
     * @return
     */
    @PostMapping("/api/admin/goods/add")
    public JsonResult insert(@RequestBody Goods goods) {

        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
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
     *	 商品新增接口数据重复检查（非原子操作）
     *
     * @param goods
     * @return
     */
    private JsonResult columnValueExistCheckForInsert(Goods goods) {
        GoodsService goodsService = shop().goods;

        GoodsColumnCheckExistParam gcep = new GoodsColumnCheckExistParam();
        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckFor.E_GOODS);

        //检查商品名称是否重复
        if (goods.getGoodsName() != null) {
            gcep.setGoodsName(goods.getGoodsName());
            if (goodsService.isColumnValueExist(gcep)) {
                return fail(JsonResultCode.GOODS_NAME_EXIST);
            }
            gcep.setGoodsName(null);
        }
        //用户输入了商品货号则进行检查是否重复
        if (goods.getGoodsSn() != null) {
            gcep.setGoodsSn(goods.getGoodsSn());
            if (goodsService.isColumnValueExist(gcep)) {
                return fail(JsonResultCode.GOODS_SN_EXIST);
            }
            gcep.setGoodsSn(null);
        }

        gcep.setColumnCheckFor(GoodsColumnCheckExistParam.ColumnCheckFor.E_GOODS_SPEC_PRODUCTION);

        //检查sku sn是否重复
        for (GoodsSpecProduct goodsSpecProduct : goods.getGoodsSpecProducts()) {
            if (goodsSpecProduct.getPrdSn() != null) {
                gcep.setPrdSn(goodsSpecProduct.getPrdSn());
                if (goodsService.isColumnValueExist(gcep)) {
                    return fail(JsonResultCode.GOODS_SPEC_PRD_SN_EXIST);
                }
            }
        }

        //检查规格名称是否存在重复
        List<GoodsSpec> specs = goods.getGoodsSpecs();
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
    public JsonResult delete(@RequestBody GoodsBatchOperateParam param){
        shop().goods.delete(param);
        return success();
    }
}
