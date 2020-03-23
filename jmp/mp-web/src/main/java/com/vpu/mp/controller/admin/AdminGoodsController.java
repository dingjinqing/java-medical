package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.*;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsGroupListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年07月08日
 */
@RestController
public class AdminGoodsController extends AdminBaseController {

    /**
     * 获取全品牌，全部标签，商家分类数据,平台分类数据
     * param.needXX=true表示需要查询对应的数据，否则对应数不会返回
     * param.needGoodsNum = true 表示需要根据商品过滤平台和商家分类，并计算分类对应的商品数量
     * param.selectType : 1 以商品为统计对象，2以商品规格为统计对象
     * param.isOnSale : 1在售，0下架
     * param.isSaleOut : true 查询售罄商品
     *
     * @param {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsFilterItemInitParam}
     * @return JsonResult
     */
    @PostMapping("/api/admin/goods/filterItem/list")
    public JsonResult getGoodsFilterItem(@RequestBody GoodsFilterItemInitParam param) {
        GoodsFilterItemInitVo vo = shop().goods.getGoodsFilterItem(param);
        return success(vo);
    }

    /**
     * 根据平台分类id获取其所有的祖先节点数据，
     * （修改商品回显商品平台分类数据的时候使用到）
     *
     * @param catId 平台分类id
     * @return LinkedList:有序链表，按照祖先顺序排列，
     */
    @GetMapping("/api/admin/goods/getSysCatParents")
    public JsonResult getSysCatParents(Integer catId) {
        LinkedList<Integer> parentIds = saas.sysCate.findParentIdsByChildId(catId);
        return success(parentIds);
    }

    /**
     * 商品分页查询
     *
     * @param param 过滤条件
     * @return
     */
    @PostMapping("/api/admin/goods/list")
    public JsonResult getPageList(@RequestBody GoodsPageListParam param) {
        param.setSelectType(GoodsPageListParam.GOODS_LIST);
        PageResult<GoodsPageListVo> pageList = shop().goods.getPageList(param);
        return success(pageList);
    }

    /**
     * 商品选择弹窗，根据过滤条件查询对应商品id集合
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/listAllIds")
    public JsonResult getGoodsIdsListAll(@RequestBody GoodsPageListParam param) {
        param.setSelectType(GoodsPageListParam.GOODS_LIST);
        List<Integer> goodsIds = shop().goods.getGoodsIdsListAll(param);
        return success(goodsIds);
    }

    /**
     * 商品规格分页查询
     */
    @PostMapping("/api/admin/goods/product/list")
    public JsonResult getProductPageList(@RequestBody GoodsPageListParam param) {
        param.setSelectType(GoodsPageListParam.GOODS_PRD_LIST);
        return success(shop().goods.getProductPageList(param));
    }

    /**
     * 商品规格分页查询id
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/product/listAllIds")
    public JsonResult getProductIdsListAll(@RequestBody GoodsPageListParam param) {
        param.setSelectType(GoodsPageListParam.GOODS_PRD_LIST);
        return success(shop().goods.getProductIdsListAll(param));
    }

    /**
     * 查询商品和其所有下属规格信息
     *
     * @param goodsIdParams
     * @return
     */
    @PostMapping("/api/admin/goods/info/list")
    public JsonResult getGoodsAndProductsByIds(@RequestBody GoodsIdParams goodsIdParams) {
        return success(shop().goods.getGoodsAndProductsByGoodsIds(goodsIdParams.getGoodsIds()));
    }

    /**
     * 通过规格1ds查询规格信息
     *
     * @param goodsIdParams guigeids
     * @return
     */
    @PostMapping("/api/admin/goods/product/info/list")
    public JsonResult getProductsByIds(@RequestBody GoodsIdParams goodsIdParams) {
        return success(shop().goods.goodsSpecProductService.getProductsByProductIds(goodsIdParams.getProductId()));
    }

    /**
     * 查询商品所有规则信息
     *
     * @param goodsId 商品ID
     * @return 规则信息
     */
    @GetMapping("/api/admin/goods/product/all/{goodsId}")
    public JsonResult getAllProductListByGoodsId(@PathVariable("goodsId") Integer goodsId) {
        List<GoodsProductVo> productVos = shop().goods.getAllProductListByGoodsId(goodsId);
        return success(productVos);
    }

    /**
     * 商品新增
     *
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

        GoodsService goodsService = shop().goods;
        //判断商品特定等级会员卡的价格是否存在大于对应规格价钱的情况
        if (!goodsService.isGradePrdPriceOk(goods)) {
            return fail(JsonResultCode.GOODS_NAME_IS_NULL);
        }

        GoodsDataIIllegalEnum code = shop().goods.insertWithLock(shopId(),goods);
        if (GoodsDataIIllegalEnum.GOODS_NAME_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_NAME_EXIST);
        }
        if (GoodsDataIIllegalEnum.GOODS_SN_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_SN_EXIST);
        }
        if (GoodsDataIIllegalEnum.GOODS_PRD_SN_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_SPEC_PRD_SN_EXIST);
        }

        if (GoodsDataIIllegalEnum.GOODS_OK.equals(code)) {
            return success();
        } else {
            return fail();
        }
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

    /**
     * 商品批处理操作
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/batch")
    public JsonResult batchOperate(@RequestBody GoodsBatchOperateParam param) {
        // 上下价处理，调用的方法同步es时会进行同步处理，而其他的批量处理是异步的
        if (param.getIsOnSale() != null) {
            shop().goods.batchIsOnSaleOperate(param);
        } else {
            shop().goods.batchOperate(param);
        }
        return success();
    }

    /**
     * 单独修改规格对应的价格或数量
     * @param param PrdPriceNumberParam
     * @return JsonResult
     */
    @PostMapping("/api/admin/goodsPrd/updatePriceNumber")
    public JsonResult updateGoodsPrdPriceNumbers(@RequestBody PrdPriceNumberParam param) {
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
        //ps:此处省略规格组，规格名值,因为加上后出现过操作超时的现象
        GoodsDataIIllegalEnum code = shop().goods.update(goods);
        if (GoodsDataIIllegalEnum.GOODS_NAME_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_NAME_EXIST);
        }
        if (GoodsDataIIllegalEnum.GOODS_SN_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_SN_EXIST);
        }
        if (GoodsDataIIllegalEnum.GOODS_PRD_SN_EXIST.equals(code)) {
            return fail(JsonResultCode.GOODS_SPEC_PRD_SN_EXIST);
        }

        if (GoodsDataIIllegalEnum.GOODS_OK.equals(code)) {
            return success();
        } else {
            return fail();
        }
    }

    /**
     * 根据id值查询商品信息
     *
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
     *
     * @param goodsId 商品id
     * @return 图片绝对地址和跳转链接相对地址
     */
    @GetMapping("/api/admin/goods/qrCode/get")
    public JsonResult getQrCode(Integer goodsId) {
        if (goodsId == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        GoodsQrCodeVo vo = shop().goods.getGoodsQrCode(goodsId);
        return success(vo);
    }

    /**
     * 更新数据时判断传入的商品名称、货号，sku码和规格名值是否重复。
     *
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
     * 取将要导出的列数
     */
    @PostMapping("/api/admin/goods/export/rows")
    public JsonResult getExportTotalRows(@RequestBody @Valid GoodsExportParam param) {
        return success(shop().goods.getExportGoodsListSize(param));
    }

    @PostMapping("/api/admin/goods/export")
    public void export(@RequestBody @Valid GoodsExportParam param, HttpServletResponse response) {
        Workbook workbook = shop().goods.exportGoodsList(param, getLang());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.GOODS_EXPORT_FILE_NAME, "excel", "excel") + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook, fileName, response);
    }

    /**
     * 根据条件获取商品数量
     * @param param 商品数量获取条件
     * @return 商品数量
     */
    @PostMapping("/api/admin/goods/num")
    public JsonResult getGoodsNumByCondition(@RequestBody GoodsNumCountParam param){
        return success(shop().goods.getGoodsNum(param));
    }

    @PostMapping("/api/admin/goods/nums")
    public JsonResult getGoodsNumByCondition(@RequestBody GoodsNumCountParamModel param) {
        return success(shop().goods.getGoodsNum(param.getGoodsNumCountParams()));
    }


    /**
     * 小程序装修商品列表模块数据接口
     * @param goodsListMpParam
     */
    @PostMapping("/api/admin/goods/mp/list")
    public JsonResult getGoodsList(@RequestBody GoodsListMpParam goodsListMpParam) {
        goodsListMpParam.setFromPage(EsGoodsConstant.GOODS_LIST_PAGE);
        List<? extends GoodsListMpVo> goodsList = shop().goodsMp.getPageIndexGoodsList(goodsListMpParam, null);
        return success(goodsList);
    }

    /**
     * 装修-商品分组-获取数据接口
     * @param param 分组过滤条件
     * @return JsonResult
     */
    @PostMapping("/api/admin/goods/mp/group/list")
    public JsonResult getGoodsGroupList(@RequestBody GoodsGroupListMpParam param) {
        if (param.getSortGroupArr() == null || param.getSortGroupArr().size() == 0) {
            return fail();
        }
        param.setUserId(null);
        if (!GoodsConstant.GOODS_GROUP_LIST_TOP_POSITION.equals(param.getPositionStyle())||!GoodsConstant.GOODS_GROUP_LIST_SHOW_ALL_COLUMN.equals(param.getGroupDisplay())) {
            param.setSortGroupArr(param.getSortGroupArr().subList(0,1));
        }

        return success(shop().goodsMp.goodsGroupMpService.getGoodsGroupList(param));
    }

    /**
     * 小程序test
     *
     */
    @PostMapping("/api/admin/goods/test")
    public void test() {
        shop().shopTaskService.wechatTaskService.beginDailyTask();
    }
}
