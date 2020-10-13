package com.vpu.mp.service.shop.goods;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.foundation.util.medical.DateFormatStr;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.sort.SortDao;
import com.vpu.mp.dao.shop.store.StoreDao;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsMatchParam;
import com.vpu.mp.service.pojo.shop.medical.brand.vo.GoodsBrandVo;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestBo;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestItemBo;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalStoreRequestBo;
import com.vpu.mp.service.pojo.shop.medical.goods.convertor.GoodsConverter;
import com.vpu.mp.service.pojo.shop.medical.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsBatchOperateParam;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsExternalRequestParam;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsExternalStoreRequestParam;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsPageListParam;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsDetailVo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsPrdVo;
import com.vpu.mp.service.pojo.shop.medical.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.medical.label.bo.LabelRelationInfoBo;
import com.vpu.mp.service.pojo.shop.medical.label.vo.GoodsLabelVo;
import com.vpu.mp.service.pojo.shop.medical.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductDetailVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductGoodsPageListVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.SpecVo;
import com.vpu.mp.service.pojo.shop.medical.sort.vo.GoodsSortVo;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoods;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.shop.goods.aggregate.GoodsAggregate;
import com.vpu.mp.service.shop.store.store.StoreGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Service
public class MedicalGoodsService extends ShopBaseService {

    @Autowired
    private GoodsAggregate goodsAggregate;
    @Autowired
    private GoodsMedicalInfoDao goodsMedicalInfoDao;
    @Autowired
    private SortDao sortDao;
    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MedicalGoodsSpecProductService medicalGoodsSpecProductService;
    @Autowired
    private MedicalGoodsLabelService medicalGoodsLabelService;
    @Autowired
    private MedicalGoodsSortService medicalGoodsSortService;
    @Autowired
    private MedicalGoodsBrandService medicalGoodsBrandService;
    @Autowired
    private MedicalGoodsImageService medicalGoodsImageService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private StoreGoodsService storeGoodsService;

    /**
     * 新增
     * @param shopId
     * @param goodsEntity
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void insert(@RedisLockKeys Integer shopId, GoodsEntity goodsEntity) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goodsEntity.getGoodsSn())) {
            goodsEntity.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsAggregate.isGoodsSnExist(goodsEntity.getGoodsSn(), null)) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsAggregate.insert(goodsEntity);
        // 处理sku
        if (goodsEntity.getGoodsSpecs() != null && goodsEntity.getGoodsSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            medicalGoodsSpecProductService.batchSpecInsert(goodsEntity.getGoodsSpecs(), goodsEntity.getGoodsId());
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        medicalGoodsSpecProductService.batchSkuInsert(goodsEntity.getGoodsSpecProducts(), goodsEntity.getGoodsId());

        // 处理标签、图片额外信息
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            medicalGoodsLabelService.batchInsertGoodsCouple(labelIds, goodsEntity.getGoodsId());
        }

        // 处理图片
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            medicalGoodsImageService.batchInsertGoodsImageRelation(goodsEntity.getImgPaths(), goodsEntity.getGoodsId());
        }
    }

    /**
     * 修改
     * @param shopId
     * @param goodsEntity
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void update(@RedisLockKeys Integer shopId, GoodsEntity goodsEntity) {
        Integer goodsId = goodsEntity.getGoodsId();
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goodsEntity.getGoodsSn())) {
            goodsEntity.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsAggregate.isGoodsSnExist(goodsEntity.getGoodsSn(), goodsEntity.getGoodsId())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsAggregate.update(goodsEntity);

        // 删除旧的spec组
        medicalGoodsSpecProductService.deleteSpecByGoodsId(goodsId);
        // 处理sku
        if (goodsEntity.getGoodsSpecs() != null && goodsEntity.getGoodsSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            medicalGoodsSpecProductService.batchSpecInsert(goodsEntity.getGoodsSpecs(), goodsId);
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        // 处理需要修改的sku
        List<GoodsSpecProductEntity> goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), true);
        medicalGoodsSpecProductService.batchSkuUpdate(goodsSpecProductEntities);
        // 处理需要新增的sku
        goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), false);
        medicalGoodsSpecProductService.batchSkuInsert(goodsSpecProductEntities, goodsId);

        // 处理标签
        medicalGoodsLabelService.deleteGoodsCouples(goodsId);
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            medicalGoodsLabelService.batchInsertGoodsCouple(labelIds, goodsId);
        }

        // 处理图片
        medicalGoodsImageService.deleteGoodsImageRelation(goodsId);
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            medicalGoodsImageService.batchInsertGoodsImageRelation(goodsEntity.getImgPaths(), goodsId);
        }
    }


    /**
     * 删除药品
     * @param goodsId
     */
    public void deleteByGoodsId(Integer goodsId) {
        goodsAggregate.deleteGoodsById(goodsId);
        medicalGoodsSpecProductService.deleteSkuByGoodsId(goodsId);
        medicalGoodsSpecProductService.deleteSpecByGoodsId(goodsId);
        medicalGoodsLabelService.deleteGoodsCouples(goodsId);
        medicalGoodsImageService.deleteGoodsImageRelation(goodsId);
    }

    /**
     * 根据商品id搜索商品信息
     * @param goodsId 商品id
     */
    public GoodsDetailVo getGoodsDetailByGoodsId(Integer goodsId) {
        GoodsDetailVo goodsDetailVo = goodsAggregate.getByGoodsId(goodsId);
        if (goodsDetailVo == null) {
            return null;
        }
        //设置sku
        List<GoodsSpecProductDetailVo> skus = medicalGoodsSpecProductService.listSkuDetailByGoodsId(goodsId);
        goodsDetailVo.setGoodsSpecProducts(skus);

        //设置规格组
        if (!MedicalGoodsConstant.DEFAULT_SKU.equals(goodsDetailVo.getIsDefaultProduct())) {
            List<SpecVo> specVos = medicalGoodsSpecProductService.listSpecByGoodsId(goodsId);
            goodsDetailVo.setGoodsSpecs(specVos);
        }

        // 品牌设置
        GoodsBrandVo goodsBrandVo = medicalGoodsBrandService.getGoodsBrandById(goodsDetailVo.getBrandId());
        if (goodsBrandVo != null) {
            goodsDetailVo.setBrandName(goodsBrandVo.getBrandName());
        }

        List<Integer> parentsSortIds = sortDao.getParentSortIds(goodsDetailVo.getSortId());
        parentsSortIds.add(goodsDetailVo.getSortId());
        // 标签设置
        List<GoodsLabelVo> goodsNormalLabels = medicalGoodsLabelService.listSortIdsRelatedLabels(parentsSortIds);
        List<GoodsLabelVo> allRelatedLabels = medicalGoodsLabelService.listAllRelatedLabels();
        goodsNormalLabels.addAll(allRelatedLabels);
        List<GoodsLabelVo> goodsPointLabels = medicalGoodsLabelService.listGoodsIdRelatedLabels(goodsId);

        goodsDetailVo.setPointLabels(goodsPointLabels);
        goodsDetailVo.setNormalLabels(goodsNormalLabels);

        List<String> imgPaths = medicalGoodsImageService.listGoodsImages(goodsId);
        goodsDetailVo.setGoodsImgs(imgPaths);

        return goodsDetailVo;
    }

    /**
     * 分页查询商品信息
     * @param pageListParam
     */
    public PageResult<GoodsPageListVo> getGoodsPageList(MedicalGoodsPageListParam pageListParam) {
        GoodsPageListCondition goodsPageListCondition = GoodsConverter.convertPageListConditionFromPageListParam(pageListParam);
        if (pageListParam.getSortId() != null) {
            List<Integer> parentsSortIds = sortDao.getParentSortIds(pageListParam.getSortId());
            parentsSortIds.add(pageListParam.getSortId());
            goodsPageListCondition.setSortIds(parentsSortIds);
        }

        /**
         * 标签打在分类上时要单独插入子分类关联
         */
        if (pageListParam.getLabelId() != null) {
            LabelRelationInfoBo labelRelationInfo = medicalGoodsLabelService.getLabelRelationInfo(pageListParam.getLabelId());
            if (labelRelationInfo.getIsAll() != null) {
                if (labelRelationInfo.getSortIds() != null) {
                    goodsPageListCondition.getSortIds().addAll(labelRelationInfo.getSortIds());
                }
                if (labelRelationInfo.getGoodsIds() != null) {
                    goodsPageListCondition.setGoodsIdsLimit(labelRelationInfo.getGoodsIds());
                }
            }
        }

        PageResult<GoodsEntity> goodsEntityPageResult = goodsAggregate.getGoodsPageList(goodsPageListCondition, pageListParam.getCurrentPage(), pageListParam.getPageRows());
        List<GoodsPageListVo> goodsPageListVos = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        List<Integer> goodsIds = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        List<Integer> sortIds = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        for (GoodsEntity goodsEntity : goodsEntityPageResult.getDataList()) {
            GoodsPageListVo goodsPageListVo = new GoodsPageListVo(goodsEntity);
            goodsPageListVos.add(goodsPageListVo);
            goodsIds.add(goodsEntity.getGoodsId());
            sortIds.add(goodsEntity.getSortId());
        }
        PageResult<GoodsPageListVo> retPageResult = new PageResult<>();
        retPageResult.setPage(goodsEntityPageResult.getPage());
        retPageResult.setDataList(goodsPageListVos);

        // 准备需要映射的规格信息
        Map<Integer, List<GoodsSpecProductGoodsPageListVo>> goodsIdSkusMap = medicalGoodsSpecProductService.groupSkuSimpleByGoodsIds(goodsIds);

        //准备标签数据
        Map<Integer, List<GoodsLabelVo>> goodsIdLabelsMap = medicalGoodsLabelService.mapGtaToLabel(goodsIds, MedicalLabelConstant.GTA_GOODS);
        Map<Integer, List<GoodsLabelVo>> sortIdLabelsMap = medicalGoodsLabelService.mapGtaToLabel(sortIds, MedicalLabelConstant.GTA_SORT);
        List<GoodsLabelVo> allGoodsLabels = medicalGoodsLabelService.listAllRelatedLabels();

        // 准备分类数据
        Map<Integer, GoodsSortVo> goodsSortVosIdMap = medicalGoodsSortService.getGoodsSortVosIdMap(sortIds);

        for (GoodsPageListVo goodsPageListVo : goodsPageListVos) {
            List<GoodsSpecProductGoodsPageListVo> goodsSpecProductGoodsPageListVos = goodsIdSkusMap.get(goodsPageListVo.getGoodsId());
            List<GoodsLabelVo> goodsPointLabels = goodsIdLabelsMap.get(goodsPageListVo.getGoodsId());
            List<GoodsLabelVo> goodsSortLabels = sortIdLabelsMap.get(goodsPageListVo.getSortId());
            GoodsSortVo goodsSortVo = goodsSortVosIdMap.get(goodsPageListVo.getSortId());

            goodsPageListVo.setGoodsSpecProducts(goodsSpecProductGoodsPageListVos);

            if (goodsSortVo != null) {
                goodsPageListVo.setSortName(goodsSortVo.getSortName());
            }
            if (goodsPointLabels != null) {
                goodsPageListVo.getGoodsPointLabels().addAll(goodsPointLabels);
            }
            if (goodsSortLabels != null) {
                goodsPageListVo.getGoodsNormalLabels().addAll(goodsSortLabels);
            }
            if (allGoodsLabels != null) {
                goodsPageListVo.getGoodsNormalLabels().addAll(allGoodsLabels);
            }
        }
        return retPageResult;
    }

    /**
     * 当前时间
     * 生成商品GoodsSn
     * @return
     */
    private String generateGoodsSn() {
        int count = goodsAggregate.countAllGoods();
        String nowStr = DateUtil.format(new DateTime(), DateFormatStr.DATE_FORMAT_FULL_COMPACT);
        return String.format("G%s-%08d", nowStr, count);
    }


    public void batchOperate(MedicalGoodsBatchOperateParam param) {
        goodsAggregate.batchOperate(param);
    }

    /**
     * 获取商品医药信息
     * @param goodsId 商品id
     * @return GoodsMedicalInfo or null
     * @author 孔德成
     */
    public GoodsMedicalInfoDo getByGoodsId(Integer goodsId) {
        return goodsMedicalInfoDao.getByGoodsId(goodsId);
    }


    private JsonResult createJsonResultByApiExternalRequestResult(ApiExternalRequestResult apiExternalRequestResult) {
        JsonResult result = new JsonResult();
        result.setError(apiExternalRequestResult.getError());
        result.setMessage(apiExternalRequestResult.getMsg());
        result.setContent(apiExternalRequestResult.getData());
        logger().debug("拉取药品信息错误：error " + apiExternalRequestResult.getError() + ",msg " + apiExternalRequestResult.getMsg());
        return result;
    }

    private JsonResult createJsonResultByApiExternalRequestResult(ApiExternalRequestResult apiExternalRequestResult, String storeCode) {
        JsonResult result = new JsonResult();
        result.setError(apiExternalRequestResult.getError());
        result.setMessage(apiExternalRequestResult.getMsg());
        result.setContent(apiExternalRequestResult.getData());
        logger().error("拉取药房：" + storeCode + " 商品信息错误：error " + apiExternalRequestResult.getError() + ",msg " + apiExternalRequestResult.getMsg());
        return result;
    }

    /**
     * 药品上下架控制方式
     * 如果store_code不为null，则表明是从药房拉取到了的数据，
     * 如果his_staus不为null,则表明是从his拉取到了的数据，
     * 两者都不为null，则表明从药房和his都拉取到了数据并匹配上，
     * store_status字段暂时未使用，具体药房商品状态在门店商品表内存储。
     * @return
     */
    @SuppressWarnings("all")
    public JsonResult fetchExternalMedicalInfo() {
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_MEDICAL_INFOS;
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalRequestConstant.APP_ID_HIS, shopId, ApiExternalRequestConstant.SERVICE_NAME_FETCH_MEDICAL_INFOS);
        MedicalGoodsExternalRequestParam param = new MedicalGoodsExternalRequestParam();
        if (lastRequestTime == null) {
            Timestamp startTime = DateUtils.convertToTimestamp(MedicalGoodsConstant.PULL_START_TIME);
            lastRequestTime = startTime.getTime() / 1000;
        }
        param.setStartTime(null);
        Timestamp now = DateUtils.getLocalDateTime();
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
        // 数据拉取错误
        if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            saas().externalRequestHistoryService.eraseRequestHistory(appId, shopId, serviceName, now);
            return createJsonResultByApiExternalRequestResult(apiExternalRequestResult);
        }
        String dataJson = apiExternalRequestResult.getData();
        GoodsMedicalExternalRequestBo goodsMedicalExternalRequestBo = Util.parseJson(dataJson, GoodsMedicalExternalRequestBo.class);
        if (goodsMedicalExternalRequestBo == null) {
            logger().error("拉取his反序列化错误，请求参数：" + param + " 商品信息反序列化错误：" + dataJson);
            JsonResult result = new JsonResult();
            result.setError(ApiExternalRequestConstant.ERROR_CODE_PARSE_RETVAL);
            return result;
        }
        Integer pullCount = 0, pageSize = goodsMedicalExternalRequestBo.getPageSize(), totalCount = goodsMedicalExternalRequestBo.getTotalCount();
        Integer requestTryCount = 0;
        for (Integer curPage = 1; curPage <= pageSize; curPage++) {
            logger().debug("拉取his数据：共" + totalCount + "条，当前页：" + curPage);
            param.setCurrentPage(curPage);
            apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
            // 数据拉取错误
            if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
                if (requestTryCount < 3 && ApiExternalRequestConstant.HIS_NET_ERROR_UNEXPECTED_FILE_CODE.equals(apiExternalRequestResult.getError()) &&
                    ApiExternalRequestConstant.HIS_NET_ERROR_UNEXPECTED_FILE_MSG.equals(apiExternalRequestResult.getMsg())) {
                    try {
                        Thread.sleep(2000);
                        requestTryCount++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    saas().externalRequestHistoryService.eraseRequestHistory(appId, shopId, serviceName, now);
                    return createJsonResultByApiExternalRequestResult(apiExternalRequestResult);
                }
            }
            goodsMedicalExternalRequestBo = Util.parseJson(apiExternalRequestResult.getData(), GoodsMedicalExternalRequestBo.class);
            if (goodsMedicalExternalRequestBo == null) {
                logger().error("拉取his反序列化错误，请求参数：" + param + " 商品信息反序列化错误：" + apiExternalRequestResult.getData());
                continue;
            }
            // 药品数据入库操作
            try {
//                batchSaveGoodsMedicalExternalInfo(goodsMedicalExternalRequestBo.getDataList());
                saveGoodsMedicalExternalInfo(goodsMedicalExternalRequestBo.getDataList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            pullCount += goodsMedicalExternalRequestBo.getDataList().size();
            pageSize = goodsMedicalExternalRequestBo.getPageSize();
            totalCount = goodsMedicalExternalRequestBo.getTotalCount();
        }
        logger().debug("拉取药品信息结束：共处理" + pullCount + "条");
        return JsonResult.success();
    }


    private void saveGoodsMedicalExternalInfo(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        // 剔除不合法说句，并处理
        goodsMedicalExternalRequestItemBos = filterHisIllegalData(goodsMedicalExternalRequestItemBos);
        List<Integer> esUpdateGoodsIds = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());

        for (GoodsMedicalExternalRequestItemBo bo : goodsMedicalExternalRequestItemBos) {
            bo.setSource(MedicalGoodsConstant.SOURCE_FROM_HIS);
            bo.setHisStatus(bo.getState() == null ? null : bo.getState().byteValue());

            GoodsEntity goodsEntity = goodsAggregate.getByExternalInfo(bo.getGoodsKeyComposedByNameQualityEnterprise());
            if (goodsEntity == null) {
                // 对于数据库不存在，而数据自身状态是删除状态则不入库
                if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
                    continue;
                }
                boolean b = insertHisInfo(bo);
                if (b) {
                    esUpdateGoodsIds.add(bo.getGoodsId());
                }
            } else {
                bo.setGoodsId(goodsEntity.getGoodsId());
                updateHisInfo(bo);
                esUpdateGoodsIds.add(goodsEntity.getGoodsId());
            }
        }
    }

    private boolean insertHisInfo(GoodsMedicalExternalRequestItemBo bo) {
        boolean goodsSnExist = goodsAggregate.isGoodsSnExist(bo.getGoodsCode(), null);
        if (goodsSnExist) {
            return false;
        }
        goodsAggregate.insertExternalInfo(bo);
        GoodsSpecProductEntity sku = new GoodsSpecProductEntity();
        sku.setGoodsId(bo.getGoodsId());
        sku.setPrdPrice(bo.getGoodsPrice());
        sku.setPrdCostPrice(bo.getGoodsPrice());
        sku.setPrdMarketPrice(bo.getGoodsPrice());
        sku.setPrdNumber(bo.getGoodsNumber());
        medicalGoodsSpecProductService.batchSkuInsert(Collections.singletonList(sku));
        return true;
    }

    private void updateHisInfo(GoodsMedicalExternalRequestItemBo bo) {
        goodsAggregate.updateExternalInfo(bo);

        List<GoodsSpecProductEntity> goodsSpecProductEntities = medicalGoodsSpecProductService.listSkusByGoodsId(bo.getGoodsId());
        for (GoodsSpecProductEntity goodsSpecProductEntity : goodsSpecProductEntities) {
            goodsSpecProductEntity.setPrdPrice(bo.getGoodsPrice());
            goodsSpecProductEntity.setPrdCostPrice(bo.getGoodsPrice());
            goodsSpecProductEntity.setPrdMarketPrice(bo.getGoodsPrice());
            goodsSpecProductEntity.setPrdNumber(bo.getGoodsNumber());
            if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
                goodsSpecProductEntity.setDelFlag(DelFlag.DISABLE_VALUE);
            }
        }
        medicalGoodsSpecProductService.batchSkuUpdate(goodsSpecProductEntities);
    }

    /**
     * 获取his信息不完整数据
     * @param goodsMedicalExternalRequestItemBos
     * @return
     */
    private List<GoodsMedicalExternalRequestItemBo> filterHisIllegalData(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        return goodsMedicalExternalRequestItemBos.stream().filter(x -> {
            if (StringUtils.isBlank(x.getGoodsCode())) {
                logger().info("同步药品信息错误：" + getShopId() + ":缺少药品编码-" + x.toString());
                return false;
            }
            if (StringUtils.isBlank(x.getGoodsCommonName())) {
                logger().info("同步药品信息错误：" + getShopId() + ":缺少药品名称-" + x.toString());
                return false;
            }
            if (x.getGoodsPrice() == null || x.getGoodsPrice().equals(BigDecimal.ZERO)) {
                logger().info("同步药品信息错误：" + getShopId() + ":缺少药品价格-" + x.getGoodsPrice());
                return false;
            }
            if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(x.getIsMedical())) {
                if (StringUtils.isBlank(x.getGoodsQualityRatio())) {
                    logger().info("同步药品信息错误：" + getShopId() + ":缺少药品规格系数-" + x.toString());
                    return false;
                }
                if (StringUtils.isBlank(x.getGoodsProductionEnterprise())) {
                    logger().info("同步药品信息错误：" + getShopId() + ":缺少药品生产企业-" + x.toString());
                    return false;
                }
            }

            x.setGoodsCode(x.getGoodsCode().trim());
            x.setGoodsCommonName(x.getGoodsCommonName().replaceAll("\\*", "").trim());

            if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(x.getIsMedical())) {
                x.setGoodsQualityRatio(x.getGoodsQualityRatio().trim().replaceAll("\\*", ""));
                x.setGoodsProductionEnterprise(x.getGoodsProductionEnterprise().trim().replaceAll("\\*", ""));
                String goodsKey = x.getGoodsCommonName() + x.getGoodsQualityRatio() + x.getGoodsProductionEnterprise();
                x.setGoodsKeyComposedByNameQualityEnterprise(goodsKey);
            } else {
                // 普通商品通过名称标识唯一
                x.setGoodsKeyComposedByNameQualityEnterprise(x.getGoodsCommonName());
            }
            if (x.getGoodsApprovalNumber() != null) {
                x.setGoodsApprovalNumber(x.getGoodsApprovalNumber().trim().replaceAll("国药准字", ""));
            }
            return true;
        }).collect(Collectors.toList());
    }


    /**
     * 插入医院his药品信息
     * @param goodsMedicalExternalRequestItemBos
     */
    @Deprecated
    private void batchSaveGoodsMedicalExternalInfo(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        transaction(() -> {
            List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemReadyToStore = filterHisIllegalData(goodsMedicalExternalRequestItemBos);
            List<String> medicalKeys = goodsMedicalExternalRequestItemReadyToStore.stream().filter(x -> StringUtils.isNotBlank(x.getGoodsKeyComposedByNameQualityEnterprise()))
                .map(GoodsMedicalExternalRequestItemBo::getGoodsKeyComposedByNameQualityEnterprise).collect(Collectors.toList());

            // 剔除联合唯一字段可能重复的情况 此处是为了防止对方数据存错误
            goodsMedicalExternalRequestItemReadyToStore = filterGoodsCodeRepeatedInfos(goodsMedicalExternalRequestItemReadyToStore);
            goodsMedicalExternalRequestItemReadyToStore = filterMedicalKeyRepeatedInfos(goodsMedicalExternalRequestItemReadyToStore);

            // 获取已存在的goodsSn到goodsId映射
            Map<String, Integer> existMedicalKeys = goodsAggregate.mapMedicalKeyToGoodsId(medicalKeys);

            List<GoodsMedicalExternalRequestItemBo> readyForUpdate = new ArrayList<>(existMedicalKeys.size());
            List<GoodsMedicalExternalRequestItemBo> readyForInsert = new ArrayList<>(medicalKeys.size() - existMedicalKeys.size());

            for (int i = 0; i < goodsMedicalExternalRequestItemReadyToStore.size(); i++) {
                GoodsMedicalExternalRequestItemBo bo = goodsMedicalExternalRequestItemReadyToStore.get(i);
                bo.setSource(MedicalGoodsConstant.SOURCE_FROM_HIS);
                bo.setHisStatus(bo.getState() == null ? null : bo.getState().byteValue());
                if (existMedicalKeys.containsKey(bo.getGoodsKeyComposedByNameQualityEnterprise())) {
                    bo.setGoodsId(existMedicalKeys.get(bo.getGoodsKeyComposedByNameQualityEnterprise()));
                    readyForUpdate.add(bo);
                } else {
                    // 对于数据库不存在，而数据自身状态是删除状态则不入库
                    if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
                        continue;
                    }
                    readyForInsert.add(bo);
                }
            }

            List<GoodsMedicalExternalRequestItemBo> readyToUpdateNotMedical = new ArrayList<>(0);
            readyForInsert = filterGoodsCodeDbRepeatedInfos(readyForInsert, readyForUpdate);
            // 新增
            batchInsertGoodsMedicalExternalInfo(readyForInsert);
            // 修改
            calculateGoodsIdByGoodsCode(readyToUpdateNotMedical);
            readyForUpdate.addAll(readyToUpdateNotMedical);
            batchUpdateGoodsMedicalExternalInfo(readyForUpdate);
        });
    }


    /**
     * 测试指定门店和分页信息使用
     * @param param
     * @return
     */
    public ApiExternalRequestResult fetchExternalStoreTest(MedicalGoodsExternalStoreRequestParam param) {
        String appId = ApiExternalRequestConstant.APP_ID_STORE;

        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_PULL_GOODS_INFOS;
        Integer shopId = getShopId();

        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
        GoodsMedicalExternalStoreRequestBo goodsMedicalExternalStoreRequestBo = Util.parseJson(apiExternalRequestResult.getData(), GoodsMedicalExternalStoreRequestBo.class);
        return apiExternalRequestResult;
    }

    /**
     * 迭代拉取药店商品信息
     * @return
     */
    public void fetchExternalStoresGoodsInfo() {
        String appId = ApiExternalRequestConstant.APP_ID_STORE;
        Integer shopId = getShopId();
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(appId, shopId, ApiExternalRequestConstant.SERVICE_NAME_PULL_GOODS_INFOS);
        Timestamp now = DateUtils.getLocalDateTime();
        List<StoreBasicVo> storeInfos = storeDao.listStoreCodes();

        for (StoreBasicVo storeInfo : storeInfos) {
            if (StringUtils.isBlank(storeInfo.getStoreCode())) {
                continue;
            }
            JsonResult jsonResult = fetchExternalStoreGoodsInfo(lastRequestTime, storeInfo, now, appId, shopId, ApiExternalRequestConstant.SERVICE_NAME_PULL_GOODS_INFOS);
            if (!JsonResult.success().equals(jsonResult)) {
                logger().info("门店：" + storeInfo.getStoreCode() + " 药品同步数据失败");
            }
        }
    }

    /**
     * 拉取指定药店药品信息
     * @param lastRequestTime
     * @param storeInfo
     * @param currentPullTime
     * @param appId
     * @param shopId
     * @param serviceName
     * @return
     */
    public JsonResult fetchExternalStoreGoodsInfo(Long lastRequestTime, StoreBasicVo storeInfo, Timestamp currentPullTime, String appId, Integer shopId, String serviceName) {
        MedicalGoodsExternalStoreRequestParam param = new MedicalGoodsExternalStoreRequestParam();
        param.setStartTime(lastRequestTime);
        param.setShopSn(storeInfo.getStoreCode());
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
        // 数据拉取错误
        if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            saas().externalRequestHistoryService.eraseRequestHistory(appId, shopId, serviceName, currentPullTime);
            return createJsonResultByApiExternalRequestResult(apiExternalRequestResult, storeInfo.getStoreCode());
        }
        String dataJson = apiExternalRequestResult.getData();
        GoodsMedicalExternalStoreRequestBo goodsMedicalExternalStoreRequestBo = Util.parseJson(dataJson, GoodsMedicalExternalStoreRequestBo.class);
        if (goodsMedicalExternalStoreRequestBo == null) {
            logger().error("拉取药房：" + storeInfo.getStoreCode() + " 请求参数：" + param + " 商品信息反序列化错误：" + dataJson);
            JsonResult result = new JsonResult();
            result.setError(ApiExternalRequestConstant.ERROR_CODE_PARSE_RETVAL);
            return result;
        }
        if (goodsMedicalExternalStoreRequestBo.getDataList() == null || goodsMedicalExternalStoreRequestBo.getDataList().size() == 0) {
            return JsonResult.success();
        }
        Integer pullCount = 0, pageSize = goodsMedicalExternalStoreRequestBo.getPageSize(), totalCount = goodsMedicalExternalStoreRequestBo.getTotalCount();
        for (Integer curPage = 1; curPage <= pageSize; curPage++) {
            logger().debug("拉取药房：" + storeInfo.getStoreCode() + ",共" + totalCount + "条，当前页：" + curPage);
            param.setCurrentPage(curPage);
            apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
            // 数据拉取错误
            if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
                saas().externalRequestHistoryService.eraseRequestHistory(appId, shopId, serviceName, currentPullTime);
                return createJsonResultByApiExternalRequestResult(apiExternalRequestResult, storeInfo.getStoreCode());
            }
            goodsMedicalExternalStoreRequestBo = Util.parseJson(apiExternalRequestResult.getData(), GoodsMedicalExternalStoreRequestBo.class);
            if (goodsMedicalExternalStoreRequestBo == null) {
                logger().error("拉取药房：" + storeInfo.getStoreCode() + " 请求参数：" + param + " 商品信息反序列化错误：" + apiExternalRequestResult.getData());
                continue;
            }
            // 药品数据入库操作
            try {
                batchSaveGoodsMedicalExternalStoreInfo(goodsMedicalExternalStoreRequestBo.getDataList(), storeInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pullCount += goodsMedicalExternalStoreRequestBo.getDataList().size();
            pageSize = goodsMedicalExternalStoreRequestBo.getPageSize();
            totalCount = goodsMedicalExternalStoreRequestBo.getTotalCount();
        }
        //控制药品的可上架状态
//        goodsAggregate.batchUpStoreAndMedicalGoods();
        logger().debug("拉取药店：" + storeInfo.getStoreCode() + " 商品信息结束：共处理" + pullCount + "条");
        return JsonResult.success();
    }

    /**
     * 对拉取到的数据进行插入
     * @param goodsMedicalExternalRequestItemBos
     * @param storeInfo                          门店编码
     */
    private void batchSaveGoodsMedicalExternalStoreInfo(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos, StoreBasicVo storeInfo) {
        transaction(() -> {
            // 剔除不合法药品信息
            List<GoodsMedicalExternalRequestItemBo> externalStoreRequestItemBos = filterStoreGoodsIllegalData(goodsMedicalExternalRequestItemBos, storeInfo.getStoreCode());
            // 剔除联合唯一字段可能重复的情况 此处是为了防止对方数据存错误
            externalStoreRequestItemBos = filterGoodsCodeRepeatedInfos(externalStoreRequestItemBos);
            externalStoreRequestItemBos = filterMedicalKeyRepeatedInfos(externalStoreRequestItemBos);
            List<String> goodsKeys = externalStoreRequestItemBos.stream().filter(x -> StringUtils.isNotBlank(x.getGoodsKeyComposedByNameQualityEnterprise()))
                .map(GoodsMedicalExternalRequestItemBo::getGoodsKeyComposedByNameQualityEnterprise).collect(Collectors.toList());
            // 门店价格按照his价格进行设置
            Map<String, Integer> goodsMedicalKeyToGoodsId = goodsAggregate.mapMedicalKeyToGoodsId(goodsKeys);
            Map<Integer, BigDecimal> goodsIdToGoodsPrice = goodsAggregate.mapGoodsIdToGoodsPrice(goodsMedicalKeyToGoodsId.values());
            List<GoodsMedicalExternalRequestItemBo> readyToInsert = new ArrayList<>(externalStoreRequestItemBos.size());
            List<GoodsMedicalExternalRequestItemBo> readyToUpdate = new ArrayList<>(externalStoreRequestItemBos.size());
            for (GoodsMedicalExternalRequestItemBo externalStoreRequestItemBo : externalStoreRequestItemBos) {
                String key = externalStoreRequestItemBo.getGoodsKeyComposedByNameQualityEnterprise();
                Integer goodsId = goodsMedicalKeyToGoodsId.get(key);
                // 转换为药房商品自身状态字段
//                externalStoreRequestItemBo.setStoreStatus(externalStoreRequestItemBo.getState() == null ? null : externalStoreRequestItemBo.getState().byteValue());

                externalStoreRequestItemBo.setStorePrice(externalStoreRequestItemBo.getGoodsPrice());
                if (goodsId == null) {
                    // 肯定是医院没有，而药房存在的药品，如果是新增的药品，则来源设置为药房，对于修改不处理来源字段（保持之前的状态）
                    externalStoreRequestItemBo.setSource(MedicalGoodsConstant.SOURCE_FROM_STORE);
                    readyToInsert.add(externalStoreRequestItemBo);
                    continue;
                }
                BigDecimal shopPrice = goodsIdToGoodsPrice.get(goodsId);
                externalStoreRequestItemBo.setGoodsPrice(shopPrice);
                externalStoreRequestItemBo.setGoodsId(goodsId);
                readyToUpdate.add(externalStoreRequestItemBo);
            }
            // 非药品数据待修改
            List<GoodsMedicalExternalRequestItemBo> readyToUpdateNotMedical = new ArrayList<>(0);
            readyToInsert = filterGoodsCodeDbRepeatedInfos(readyToInsert, readyToUpdateNotMedical);
            // 插入需要插入的药房数据
            batchInsertGoodsMedicalExternalInfo(readyToInsert);
            // 设置待修改商品id值
            calculateGoodsIdByGoodsCode(readyToUpdateNotMedical);
            readyToUpdate.addAll(readyToUpdateNotMedical);
            batchUpdateGoodsMedicalStoreExternalInfo(readyToUpdate);
            List<Integer> goodsIds = externalStoreRequestItemBos.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsId).collect(Collectors.toList());
            Map<Integer, List<GoodsSpecProductDetailVo>> goodsSkuGroups = medicalGoodsSpecProductService.groupGoodsIdToSku(goodsIds);
            List<StoreGoods> storeGoodsList = externalStoreRequestItemBos.stream().map(bo -> {
                StoreGoods storeGoods = convertBoToStoreGoods(bo, storeInfo.getStoreId());
                List<GoodsSpecProductDetailVo> goodsSpecProductDetailVos = goodsSkuGroups.get(bo.getGoodsId());
                storeGoods.setPrdId(goodsSpecProductDetailVos.get(0).getPrdId());
                storeGoods.setPrdSn(goodsSpecProductDetailVos.get(0).getPrdSn());
                return storeGoods;
            }).collect(Collectors.toList());
            storeGoodsService.batchSyncStoreGoods(storeGoodsList);
        });
    }

    private StoreGoods convertBoToStoreGoods(GoodsMedicalExternalRequestItemBo bo, Integer storeId) {
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setStoreId(storeId);
        storeGoods.setGoodsId(bo.getGoodsId());
        storeGoods.setGoodsCommonName(bo.getGoodsCommonName());
        storeGoods.setGoodsQualityRatio(bo.getGoodsQualityRatio());
        storeGoods.setGoodsApprovalNumber(bo.getGoodsApprovalNumber());
        storeGoods.setGoodsProductionEnterprise(bo.getGoodsProductionEnterprise());
        storeGoods.setIsOnSale(BaseConstant.EXTERNAL_ITEM_STATE_ENABLE.equals(bo.getState()) ? MedicalGoodsConstant.ON_SALE : MedicalGoodsConstant.OFF_SALE);
        storeGoods.setGoodsStoreSn(bo.getGoodsCode());
        storeGoods.setProductNumber(bo.getGoodsNumber());
        storeGoods.setProductPrice(bo.getGoodsPrice());
        return storeGoods;
    }

    /**
     * 过滤掉从his拉取的不合法数据
     * @param goodsMedicalExternalRequestItemBos
     * @return
     */
    private List<GoodsMedicalExternalRequestItemBo> filterStoreGoodsIllegalData(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos, String storeCode) {
        return goodsMedicalExternalRequestItemBos.stream().filter(x -> {
            if (StringUtils.isBlank(x.getGoodsCode())) {
                logger().info("同步药房：" + getShopId() + ":缺少药品唯一码-" + x.toString());
                return false;
            }
            if (StringUtils.isBlank(x.getGoodsCommonName())) {
                logger().info("同步药房：" + storeCode + " 药品信息错误：" + getShopId() + ":缺少通用名称-" + x.toString());
                return false;
            }
            if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(x.getIsMedical())) {
                if (StringUtils.isBlank(x.getGoodsQualityRatio())) {
                    logger().info("同步药房：" + storeCode + " 药品信息错误：" + getShopId() + ":缺少规格系数-" + x.toString());
                    return false;
                }
                if (StringUtils.isBlank(x.getGoodsProductionEnterprise())) {
                    logger().info("同步药房：" + storeCode + " 药品信息错误：" + getShopId() + ":缺少药品生产企业-" + x.toString());
                    return false;
                }
            }

            x.setGoodsCommonName(x.getGoodsCommonName().trim());
            if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(x.getIsMedical())) {
                x.setGoodsQualityRatio(x.getGoodsQualityRatio().trim());
                x.setGoodsProductionEnterprise(x.getGoodsProductionEnterprise().trim());
                String key = x.getGoodsCommonName() + x.getGoodsQualityRatio() + x.getGoodsProductionEnterprise();
                x.setGoodsKeyComposedByNameQualityEnterprise(key);
            } else {
                x.setGoodsKeyComposedByNameQualityEnterprise(x.getGoodsCommonName());
            }
            if (x.getGoodsApprovalNumber() != null) {
                // 完全是为了顾及医院数据存在质量问题
                x.setGoodsApprovalNumber(x.getGoodsApprovalNumber().trim().replaceAll("国药准字", ""));
            }
            x.setStoreCode(x.getGoodsCode());
            x.setGoodsCode(MedicalGoodsConstant.STORE_GOODS_CODE_PREFIX + x.getGoodsCode());
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 过滤掉goodsCode可能产生重复的数据
     * @param externalRequestItemBos
     * @return
     */
    private List<GoodsMedicalExternalRequestItemBo> filterGoodsCodeRepeatedInfos(List<GoodsMedicalExternalRequestItemBo> externalRequestItemBos) {
        Map<String, GoodsMedicalExternalRequestItemBo> collect = externalRequestItemBos.stream().collect(Collectors.toMap(GoodsMedicalExternalRequestItemBo::getGoodsCode, Function.identity(), (x1, x2) -> x1));
        return new ArrayList<>(collect.values());
    }

    /**
     * 过滤掉药品联合唯一字段（名称+规格系数+生产企业）可能重复
     * @param externalRequestItemBos
     * @return
     */
    private List<GoodsMedicalExternalRequestItemBo> filterMedicalKeyRepeatedInfos(List<GoodsMedicalExternalRequestItemBo> externalRequestItemBos) {
        Map<String, GoodsMedicalExternalRequestItemBo> collect = externalRequestItemBos.stream().collect(Collectors.toMap(GoodsMedicalExternalRequestItemBo::getGoodsKeyComposedByNameQualityEnterprise, Function.identity(), (x1, x2) -> x1));
        return new ArrayList<>(collect.values());
    }

    /**
     * 过滤数据库中可能重复的药房code
     * @param readyForInsert
     * @param readyForUpdate
     * @return
     */
    private List<GoodsMedicalExternalRequestItemBo> filterGoodsCodeDbRepeatedInfos(List<GoodsMedicalExternalRequestItemBo> readyForInsert, List<GoodsMedicalExternalRequestItemBo> readyForUpdate) {
        List<String> goodsCodes = readyForInsert.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsCode).collect(Collectors.toList());
        Map<String, Integer> goodsSnMapToGoodsId = goodsAggregate.mapGoodsSnToGoodsId(goodsCodes);
        return readyForInsert.stream().filter(bo -> {
            if (goodsSnMapToGoodsId.get(bo.getGoodsCode()) != null) {
                bo.setGoodsId(goodsSnMapToGoodsId.get(bo.getGoodsCode()));
                readyForUpdate.add(bo);
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    /**
     * 根据goodsCode/Sn 设置商品的id值
     * @param readyToExecuteList
     */
    private void calculateGoodsIdByGoodsCode(List<GoodsMedicalExternalRequestItemBo> readyToExecuteList) {
        List<String> goodsCodes = readyToExecuteList.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsCode).collect(Collectors.toList());
        Map<String, Integer> goodsSnMapToGoodsId = goodsAggregate.mapGoodsSnToGoodsId(goodsCodes);
        for (GoodsMedicalExternalRequestItemBo bo : readyToExecuteList) {
            bo.setGoodsId(goodsSnMapToGoodsId.get(bo.getGoodsCode()));
        }
    }

    /**
     * 批量新增药品信息
     * @param readyForInserts
     */
    private void batchInsertGoodsMedicalExternalInfo(List<GoodsMedicalExternalRequestItemBo> readyForInserts) {
        goodsAggregate.batchInsert(readyForInserts);
        List<GoodsSpecProductEntity> goodsSpecProductEntities = new ArrayList<>(readyForInserts.size());

        for (GoodsMedicalExternalRequestItemBo bo : readyForInserts) {
            GoodsSpecProductEntity entity = new GoodsSpecProductEntity();
            entity.setGoodsId(bo.getGoodsId());
            entity.setPrdPrice(bo.getGoodsPrice());
            entity.setPrdCostPrice(bo.getGoodsPrice());
            entity.setPrdMarketPrice(bo.getGoodsPrice());
            entity.setPrdNumber(bo.getGoodsNumber());
            goodsSpecProductEntities.add(entity);
        }
        medicalGoodsSpecProductService.batchSkuInsert(goodsSpecProductEntities);
    }

    /**
     * 批量更新药品信息
     * @param readyForUpdates
     */
    private void batchUpdateGoodsMedicalExternalInfo(List<GoodsMedicalExternalRequestItemBo> readyForUpdates) {
        List<Integer> goodsIds = readyForUpdates.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<GoodsSpecProductGoodsPageListVo>> goodsIdSkuMap = medicalGoodsSpecProductService.groupSkuSimpleByGoodsIds(goodsIds);

        List<GoodsSpecProductEntity> goodsSpecProductEntities = new ArrayList<>(readyForUpdates.size());
        for (GoodsMedicalExternalRequestItemBo bo : readyForUpdates) {
            List<GoodsSpecProductGoodsPageListVo> skus = goodsIdSkuMap.get(bo.getGoodsId());
            if (skus == null || skus.size() == 0) {
                continue;
            }
            GoodsSpecProductEntity entity = new GoodsSpecProductEntity();
            entity.setPrdId(skus.get(0).getPrdId());
            entity.setGoodsId(bo.getGoodsId());
            entity.setPrdPrice(bo.getGoodsPrice());
            entity.setPrdCostPrice(bo.getGoodsPrice());
            entity.setPrdMarketPrice(bo.getGoodsPrice());
            entity.setPrdNumber(bo.getGoodsNumber());
            if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
                entity.setDelFlag(DelFlag.DISABLE_VALUE);
            }
            goodsSpecProductEntities.add(entity);
        }
        goodsAggregate.batchUpdate(readyForUpdates);
        medicalGoodsSpecProductService.batchSkuUpdate(goodsSpecProductEntities);
    }

    /**
     * 批量更新从药房拉取过来的药品数据
     * @param readyForUpdates
     */
    private void batchUpdateGoodsMedicalStoreExternalInfo(List<GoodsMedicalExternalRequestItemBo> readyForUpdates) {
        List<Integer> goodsIds = readyForUpdates.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<GoodsSpecProductGoodsPageListVo>> goodsIdSkuMap = medicalGoodsSpecProductService.groupSkuSimpleByGoodsIds(goodsIds);
        List<GoodsSpecProductEntity> goodsSpecProductEntities = new ArrayList<>(readyForUpdates.size());
        for (GoodsMedicalExternalRequestItemBo bo : readyForUpdates) {
            List<GoodsSpecProductGoodsPageListVo> skus = goodsIdSkuMap.get(bo.getGoodsId());
            if (skus == null || skus.size() == 0) {
                continue;
            }
            GoodsSpecProductEntity entity = new GoodsSpecProductEntity();
            if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
                entity.setDelFlag(DelFlag.DISABLE_VALUE);
            }
            goodsSpecProductEntities.add(entity);
        }
        goodsAggregate.batchUpdateStoreGoodsInfo(readyForUpdates);
        medicalGoodsSpecProductService.batchSkuUpdate(goodsSpecProductEntities);
    }


    public void batchSyncStoreGoodsNumberAndStatus(String storeCode) {

    }

    /**
     * 根据goodsId,goodsCommonName,goodsQualityRatio,productionEnterprise匹配药品信息
     * @param goodsMatchParam
     * @return
     */
    public GoodsPrdVo matchGoodsMedicalDetail(GoodsMatchParam goodsMatchParam) {
        Integer goodsId = goodsAggregate.matchGoodsMedical(goodsMatchParam);
        if (goodsId == null) {
            return null;
        }
        GoodsDetailVo goodsDetail = goodsAggregate.getByGoodsId(goodsId);
        GoodsPrdVo goodsPrd = new GoodsPrdVo();
        FieldsUtil.assign(goodsDetail, goodsPrd);
        Integer prdId = goodsSpecProductService.getDefaultPrdId(goodsDetail.getGoodsId());
        goodsPrd.setPrdId(prdId);
        return goodsPrd;
    }

}
