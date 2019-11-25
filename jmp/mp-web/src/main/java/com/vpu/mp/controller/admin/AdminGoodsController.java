package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goods.*;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年07月08日
 */
@RestController
public class AdminGoodsController extends AdminBaseController {

    /**
     * 商品分页查询页面过滤框数据初始化，包含了全部品牌，全部标签，店铺内商品对应的平台分类和商家分类,
     * 对于平台分类和商家分类会根据对应的查询条件进行过滤
     * @param param {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam}
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsInitialVo}
     */
    @PostMapping("/api/admin/goods/page/init")
    public JsonResult getPageInitValue(@RequestBody GoodsPageListParam param) {

        if (param.getSelectType() == null) {
            param.setSelectType(GoodsPageListParam.GOODS_LIST);
        }
        GoodsInitialVo goodsInitialVo = null;
        try {
            goodsInitialVo = shop().goods.pageInitValue(param);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(goodsInitialVo);
    }

    /**
     * 获取全品牌，标签，商家分类数据
     * @return
     */
    @PostMapping("/api/admin/goods/sortBrandLabel/list")
    public JsonResult getSortBrandLabelList(@RequestBody GoodsFilterItemInitParam param){
//        GoodsInitialVo goodsInitialVo = shop().goods.getSortBrandLabelList();
        GoodsFilterItemInitVo vo = shop().goods.getGoodsFilterItem(param);
        return success(vo);
    }

    /**
     *  根据平台分类id获取其所有的祖先节点数据，
     * （修改商品回显商品平台分类数据的时候使用到）
     * @param catId 平台分类id
     * @return LinkedList:有序链表，按照祖先顺序排列， map {'cat_id':1,'cat_name':'裤子'}
     */
    @GetMapping("/api/admin/goods/getSysCatParents")
    public JsonResult getSysCatParents(Integer catId) {
        LinkedList<Map<String, Object>> parentByChildId = saas.sysCate.findParentByChildId(catId);
        return success(parentByChildId);
    }
    /**
     * 商品分页查询
     * @param param 过滤条件
     * @return
     */
    @PostMapping("/api/admin/goods/list")
    public JsonResult getPageList(@RequestBody GoodsPageListParam param) {
        PageResult<GoodsPageListVo> pageList = shop().goods.getPageList(param);
        return success(pageList);
    }

    @PostMapping("/api/admin/goods/listAllIds")
    public JsonResult getGoodsIdsListAll(@RequestBody GoodsPageListParam param) {
        List<Integer> goodsIds = shop().goods.getGoodsIdsListAll(param);
        return success(goodsIds);
    }

    /**
     * 商品规格分页查询
     */
    @PostMapping("/api/admin/goods/product/list")
    public JsonResult getProductPageList(@RequestBody GoodsPageListParam param) {
        return success(shop().goods.getProductPageList(param));
    }

    @PostMapping("/api/admin/goods/product/listAllIds")
    public JsonResult getProductIdsListAll(@RequestBody GoodsPageListParam param){
        return success(shop().goods.getProductIdsListAll(param));
    }

    /**
     * 查询商品和其所有下属规格信息
     * @param goodsIdParams
     * @return
     */
    @PostMapping("/api/admin/goods/info/list")
    public JsonResult getGoodsAndProductsByIds(@RequestBody GoodsIdParams goodsIdParams) {
        return success(shop().goods.getGoodsAndProductsByGoodsIds(goodsIdParams.getGoodsIds()));
    }

    /**
     * 通过规格1ds查询规格信息
     * @param goodsIdParams  guigeids
     * @return
     */
    @PostMapping("/api/admin/goods/product/info/list")
    public JsonResult getProductsByIds(@RequestBody GoodsIdParams goodsIdParams) {
        return success(shop().goods.goodsSpecProductService.getProductsByProductIds(goodsIdParams.getProductId()));
    }

    /**
     * 查询商品所有规则信息
     * @param goodsId 商品ID
     * @return  规则信息
     */
    @GetMapping("/api/admin/goods/product/all/{goodsId}")
    public JsonResult getAllProductListByGoodsId(@PathVariable("goodsId") Integer goodsId){
        List<GoodsProductVo> productVos = shop().goods.getAllProductListByGoodsId(goodsId);
        return success(productVos);
    }

    /**
     * 商品新增
     * @param goods 商品参数
     * @return 操作结果
     */
    @PostMapping("/api/admin/goods/add")
    public JsonResult insert(@RequestBody Goods goods) {

        //如果商品使用默认的规格形式，也需要根据默认形式设置一个GoodsSpecProducts参数
        if (goods.getGoodsSpecProducts() == null || goods.getGoodsSpecProducts().size() == 0) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品名称是否为空
        if (StringUtils.isBlank(goods.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }

        //判断平台分类是否为空
        if (goods.getCatId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        //判断商品主图是否为空
        if (StringUtils.isBlank(goods.getGoodsImg())) {
            return fail(JsonResultCode.GOODS_SORT_NAME_IS_NULL);
        }

        //判断商品的规格属性的prdDesc和传入的规格键值是否能对应上
        JsonResult result = isGoodsSpecProductDescRight(goods.getGoodsSpecProducts(), goods.getGoodsSpecs());
        //传入的规格属性的prdDesc存在错误
        if (result.getError() != 0) {
            return result;
        }

        //判断商品特定等级会员卡的价格是否存在大于对应规格价钱的情况
        result=isGradePrdPriceOk(goods);
        if (result.getError() != 0) {
            return result;
        }

        //存在重复值则直接返回
        result = columnValueExistCheckForInsert(goods);
        if (result.getError() != 0) {
            return result;
        }

        shop().goods.insert(goods);

        result.setContent(goods.getGoodsId());

        return result;
    }

    /**
     * 商品新增接口数据重复检查（非原子操作）
     *
     * @param goods 商品
     * @return {@link com.vpu.mp.service.foundation.data.JsonResult}
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
     * @return {@link com.vpu.mp.service.foundation.data.JsonResult}
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

    @PostMapping("/api/admin/goodsPrd/updatePriceNumber")
    public JsonResult updateGoodsPrdPriceNumbers(@RequestBody GoodsBatchOperateParam param){
        shop().goods.updateGoodsPrdPriceNumbers(param);
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
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //判断商品名称是否为空
        if (StringUtils.isBlank(goods.getGoodsName())) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }

        //判断平台分类是否为空
        if (goods.getCatId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        //判断商品主图是否为空
        if (StringUtils.isBlank(goods.getGoodsImg())) {
            return fail(JsonResultCode.GOODS_SORT_NAME_IS_NULL);
        }

        //判断商品的规格属性的prdDesc和传入的规格键值是否能对应上
        JsonResult result = isGoodsSpecProductDescRight(goods.getGoodsSpecProducts(), goods.getGoodsSpecs());
        //传入的规格属性的prdDesc存在错误
        if (result.getError() != 0) {
            return result;
        }

        //判断商品特定等级会员卡的价格是否存在大于对应规格价钱的情况
        result=isGradePrdPriceOk(goods);
        if (result.getError() != 0) {
            return result;
        }

        //存在重复值则直接返回
         result = columnValueExistCheckForUpdate(goods);
        if (result.getError() != 0) {
            return result;
        }

        shop().goods.update(goods);

        return success();
    }

    /**
     * 根据id值查询商品信息
     * @param goods 商品信息
     * @return {@link com.vpu.mp.service.foundation.data.JsonResult}
     */
    @PostMapping("/api/admin/goods/select")
    public JsonResult select(@RequestBody Goods goods) {
        if (goods.getGoodsId() == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }

        GoodsVo goodsVo = shop().goods.select(goods.getGoodsId());


        return success(goodsVo);
    }

    /**
     * 获得商品的小程序跳转图片
     * @param goodsId 商品id
     * @return 图片绝对地址和跳转链接相对地址
     */
    @GetMapping("/api/admin/goods/qrCode/get")
    public JsonResult getQrCode(Integer goodsId){
        if (goodsId == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        GoodsQrCodeVo vo= shop().goods.getGoodsQrCode(goodsId);
        return success(vo);
    }

    /**
     * 更新数据时判断传入的商品名称、货号，sku码和规格名值是否重复。
     * @param goods 商品
     * @return {@link JsonResult#getError()}!=0表示存在重复
     */
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

        //检查规格名称和值是否存在重复
        return isSpecNameOrValueRepeat(goods.getGoodsSpecs());
    }

    /**
     * 判断商品规格名和规格值是否内部自重复
     *
     * @param specs 商品规格
     * @return {@link JsonResult#getError()}!=0表示存在重复
     */
    private JsonResult isSpecNameOrValueRepeat(List<GoodsSpec> specs) {
        //在选择默认规格的情况下该字段可以是空
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

    /**
     * 判断商品会员卡价格是否正确
     * @param goods {@link com.vpu.mp.service.pojo.shop.goods.goods}
     * @return
     */
    private JsonResult isGradePrdPriceOk(Goods goods){
        //判断商品特定等级会员卡的价格是否存在大于对应规格价钱的情况
        if (goods.getGoodsGradePrds() != null && goods.getGoodsGradePrds().size() > 0) {
            Map<String, BigDecimal> collect =
                goods.getGoodsSpecProducts()
                    .stream()
                    .collect(Collectors.toMap(GoodsSpecProduct::getPrdDesc, GoodsSpecProduct::getPrdPrice));

            boolean r = goods.getGoodsGradePrds().stream().anyMatch(goodsGradePrd -> {
                if (goodsGradePrd.getGradePrice() == null || goodsGradePrd.getGrade() == null) {
                    return true;
                }
                return goodsGradePrd.getGradePrice().compareTo(collect.get(goodsGradePrd.getPrdDesc())) > 0;
            });

            if (r) {
                return fail(JsonResultCode.CODE_PARAM_ERROR);
            }
        }
        return success();
    }

    /**
     * 验证出入的商品规格属性和商品规格键值的正确性，
     * 验证方式是动态计算{@link GoodsSpecProduct#}的值是否和{@link GoodsSpec}计算出来的值一致
     * @param goodsSpecProducts 商品规格属性
     * @param goodsSpecs    商品规格键值
     * @return {@link JsonResult#getError()}!=0表示存在错误
     */
    private JsonResult isGoodsSpecProductDescRight(List<GoodsSpecProduct> goodsSpecProducts, List<GoodsSpec> goodsSpecs) {

        //判断是否是默认sku
        boolean isDefaultSku = goodsSpecProducts.size() == 1 &&
            StringUtils.isBlank(goodsSpecProducts.get(0).getPrdDesc()) &&
            (goodsSpecs == null || goodsSpecs.size() == 0);

        //是默认sku直接返回
        if (isDefaultSku) {
            return success();
        }

        //根据商品规格值计算出应该有多少规格数据，计算笛卡尔积
        int cartesianNum = 1;
        for (GoodsSpec goodsSpec : goodsSpecs) {
            //商品写了规格名称但是未设置规格值
            if (goodsSpec.getGoodsSpecVals() == null || goodsSpec.getGoodsSpecVals().size() == 0) {
                return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
            }
            //笛卡尔积计算
            cartesianNum *= goodsSpec.getGoodsSpecVals().size();
        }

        //传入的规格属性条目和根据规格名值计算出来的数据不对应
        if (cartesianNum != goodsSpecProducts.size()) {
            return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
        }

        //验证传入的prdDesc的正确性，拆解prdDesc，检查对应的名和值是否咋goodsSpec中都存在
        List<String> specDescs = goodsSpecProducts.stream().map(GoodsSpecProduct::getPrdDesc).collect(Collectors.toList());
        Map<String, List<GoodsSpecVal>> specs = goodsSpecs.stream().collect(Collectors.toMap(GoodsSpec::getSpecName, GoodsSpec::getGoodsSpecVals));

        for (String prdDesc : specDescs) {
            if (prdDesc == null) {
                return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
            }
            String[] splits = prdDesc.split(GoodsSpecProductService.PRD_DESC_DELIMITER);

            for (String split : splits) {
                String[] s = split.split(GoodsSpecProductService.PRD_VAL_DELIMITER);

                if (s.length < 2||StringUtils.isBlank(s[0])||StringUtils.isBlank(s[1])) {
                    return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
                }

                String speck =s[0],specv=s[1];

                //检查规格名称是否存在
                List<GoodsSpecVal> goodsSpecVals = specs.get(speck);
                //规格名称不存在
                if (goodsSpecVals == null) {
                    return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
                }

                boolean b = goodsSpecVals.stream().anyMatch(goodsSpecVal -> StringUtils.equals(specv,goodsSpecVal.getSpecValName()));

                if (!b) {
                    return fail(JsonResultCode.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT);
                }
            }
        }
        return success();
    }

    /**
     * 取将要导出的列数
     */
    @PostMapping("/api/admin/goods/export/rows")
    public JsonResult getExportTotalRows(@RequestBody @Valid GoodsExportParam param) {
        return success(shop().goods.getExportGoodsListSize(param));
    }

    @PostMapping("/api/admin/goods/export")
    public void export(@RequestBody @Valid GoodsExportParam param, HttpServletResponse response) {
        Workbook workbook =shop().goods.exportGoodsList(param,getLang());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.GOODS_EXPORT_FILE_NAME ,"excel","excel") + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook,fileName,response);
    }

    /**
     * 小程序装修商品列表模块数据接口
     * @param goodsListMpParam
     */
    @PostMapping("/api/admin/goods/mp/list")
    public JsonResult getGoodsList(@RequestBody GoodsListMpParam goodsListMpParam) {
        List<? extends  GoodsListMpVo> goodsList = shop().goodsMp.getPageIndexGoodsList(goodsListMpParam,null);
        return success(goodsList);
    }
}
