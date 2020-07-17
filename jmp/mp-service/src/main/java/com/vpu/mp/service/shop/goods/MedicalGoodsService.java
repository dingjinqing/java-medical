package com.vpu.mp.service.shop.goods;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.foundation.util.medical.DateFormatStr;
import com.vpu.mp.common.pojo.saas.api.ApiExternalConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.sort.SortDao;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.medical.brand.vo.GoodsBrandVo;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestBo;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestItemBo;
import com.vpu.mp.service.pojo.shop.medical.goods.convertor.GoodsConverter;
import com.vpu.mp.service.pojo.shop.medical.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsExternalRequestParam;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsPageListParam;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsDetailVo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.medical.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.medical.label.bo.LabelRelationInfoBo;
import com.vpu.mp.service.pojo.shop.medical.label.vo.GoodsLabelVo;
import com.vpu.mp.service.pojo.shop.medical.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductDetailVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductGoodsPageListVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.SpecVo;
import com.vpu.mp.service.pojo.shop.medical.sort.vo.GoodsSortVo;
import com.vpu.mp.service.shop.goods.aggregate.GoodsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private MedicalGoodsSpecProductService medicalGoodsSpecProductService;
    @Autowired
    private MedicalGoodsLabelService medicalGoodsLabelService;
    @Autowired
    private MedicalGoodsSortService medicalGoodsSortService;
    @Autowired
    private MedicalGoodsBrandService medicalGoodsBrandService;
    @Autowired
    private MedicalGoodsImageService medicalGoodsImageService;

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
            if (goodsAggregate.isGoodsSnExist(goodsEntity.getGoodsSn())) {
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
            if (goodsAggregate.isGoodsSnExist(goodsEntity.getGoodsSn())) {
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


    /**
     * 获取商品医药信息
     * @param goodsId 商品id
     * @return GoodsMedicalInfo or null
     * @author 孔德成
     */
    public GoodsMedicalInfoDo getByGoodsId(Integer goodsId) {
        return goodsMedicalInfoDao.getByGoodsId(goodsId);
    }


    @SuppressWarnings("all")
    public JsonResult fetchExternalMedicalInfo() {
        String appId = ApiExternalConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalConstant.SERVICE_NAME_FETCH_MEDICAL_INFOS;
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalConstant.APP_ID_HIS, shopId, ApiExternalConstant.SERVICE_NAME_FETCH_MEDICAL_INFOS);
        MedicalGoodsExternalRequestParam param = new MedicalGoodsExternalRequestParam();
        param.setStartTime(lastRequestTime);

        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
        // 数据拉取错误
        if (!ApiExternalConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            JsonResult result = new JsonResult();
            result.setError(apiExternalRequestResult.getError());
            result.setMessage(apiExternalRequestResult.getMsg());
            result.setContent(apiExternalRequestResult.getData());
            return result;
        }
        String dataJson = apiExternalRequestResult.getData();
        GoodsMedicalExternalRequestBo goodsMedicalExternalRequestBo = Util.parseJson(dataJson, GoodsMedicalExternalRequestBo.class);
        if (goodsMedicalExternalRequestBo == null) {
            return JsonResult.success();
        }

        for (Integer curPage = 1; curPage <= goodsMedicalExternalRequestBo.getPageSize(); curPage++) {

            apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));
            // 数据拉取错误
            if (!ApiExternalConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
                JsonResult result = new JsonResult();
                result.setError(apiExternalRequestResult.getError());
                result.setMessage(apiExternalRequestResult.getMsg());
                result.setContent(apiExternalRequestResult.getData());
                return result;
            }
            dataJson = apiExternalRequestResult.getData();
            goodsMedicalExternalRequestBo = Util.parseJson(dataJson, GoodsMedicalExternalRequestBo.class);

            List<GoodsMedicalExternalRequestItemBo> dataList = goodsMedicalExternalRequestBo.getDataList();
            // 药品数据入库操作
            batchStoreGoodsMedicalExternalInfo(dataList);
        }
        return JsonResult.success();
    }

    /**
     * 插入医院his药品信息
     * @param goodsMedicalExternalRequestItemBos
     */
    @DbTransactional(type = DbType.SHOP_DB)
    private void batchStoreGoodsMedicalExternalInfo(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        List<String> goodsCodes = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());
        // 剔除没有药品编码的数据
        goodsMedicalExternalRequestItemBos = goodsMedicalExternalRequestItemBos.stream().filter(x -> {
            if (x.getGoodsCode() == null) {
                return false;
            } else {
                goodsCodes.add(x.getGoodsCode());
                return true;
            }
        }).collect(Collectors.toList());
        // 获取已存在的goodsSn到goodsId映射
        Map<String, Integer> existGoodsCodes = goodsAggregate.mapGoodsCodeToGoodsId(goodsCodes);

        List<GoodsMedicalExternalRequestItemBo> readyForUpdate = new ArrayList<>(existGoodsCodes.size());
        List<GoodsMedicalExternalRequestItemBo> readyForInsert = new ArrayList<>(goodsCodes.size() - existGoodsCodes.size());

        for (int i = 0; i < goodsMedicalExternalRequestItemBos.size(); i++) {
            GoodsMedicalExternalRequestItemBo bo = goodsMedicalExternalRequestItemBos.get(i);

            if (existGoodsCodes.containsKey(bo.getGoodsCode())) {
                bo.setGoodsId(existGoodsCodes.get(bo.getGoodsCode()));
                readyForUpdate.add(bo);
            } else {
                // 对于数据库不存在，而数据自身状态是删除状态则不入库
                if (BaseConstant.EXTERNAL_ITEM_STATE_DISABLE.equals(bo.getState())) {
                    continue;
                }
                readyForInsert.add(bo);
            }
        }
        // 新增，防止新增数据内存在goodsCode相同的数据
        Map<String, GoodsMedicalExternalRequestItemBo> trimRepeated = readyForInsert.stream().collect(Collectors.toMap(GoodsMedicalExternalRequestItemBo::getGoodsCode, Function.identity(), (x1, x2) -> x1));
        readyForInsert = new ArrayList<>(trimRepeated.values());
        batchInsertGoodsMedicalExternalInfo(readyForInsert);

        // 修改
        batchUpdateGoodsMedicalExternalInfo(readyForUpdate);
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
}
