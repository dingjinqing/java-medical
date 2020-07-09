package com.vpu.mp.service.shop.goods;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.medical.DateFormatStr;
import com.vpu.mp.common.pojo.shop.table.GoodsBrandDo;
import com.vpu.mp.common.pojo.shop.table.GoodsImgDo;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.dao.shop.brand.GoodsBrandDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.goods.repository.GoodsRepository;
import com.vpu.mp.dao.shop.goods.repository.GoodsSpecProductRepository;
import com.vpu.mp.dao.shop.img.GoodsImgDao;
import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
import com.vpu.mp.dao.shop.sort.SortDao;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.convertor.GoodsParamConvertor;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.goods.param.MedicalGoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.vo.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.vo.GoodsSelectVo;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.bo.LabelRelationInfoBo;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCoupleVal;
import com.vpu.mp.service.pojo.shop.label.vo.GoodsLabelVo;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductGoodsPageListVo;
import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductVo;
import com.vpu.mp.service.pojo.shop.sku.vo.SpecVo;
import com.vpu.mp.service.pojo.shop.sort.vo.GoodsSortVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Service
public class MedicalGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsSpecProductRepository goodsSpecProductRepository;
    @Autowired
    private GoodsLabelRepository goodsLabelRepository;
    @Autowired
    private GoodsImgDao goodsImgDao;
    @Autowired
    private GoodsMedicalInfoDao goodsMedicalInfoDao;
    @Autowired
    private GoodsBrandDao goodsBrandDao;
    @Autowired
    private SortDao sortDao;

    @Autowired
    private MedicalGoodsLabelService medicalGoodsLabelService;
    @Autowired
    private MedicalGoodsSortService medicalGoodsSortService;

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
            if (goodsRepository.isGoodsSnExist(goodsEntity.getGoodsSn())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsRepository.insert(goodsEntity);
        // 处理sku
        if (goodsEntity.getGoodsSpecs() != null && goodsEntity.getGoodsSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goodsEntity.getGoodsSpecs(), goodsEntity.getGoodsId());
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        goodsSpecProductRepository.batchSkuInsert(goodsEntity.getGoodsSpecProducts(), goodsEntity.getGoodsId());

        // 处理标签、图片额外信息
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCoupleVal> goodsLabelCoupleVals = GoodsLabelCoupleVal.generateCouples(labelIds, goodsEntity.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
            goodsLabelRepository.batchInsertCouple(goodsLabelCoupleVals);
        }

        // 处理图片
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            List<GoodsImgDo> goodsImgDos = new ArrayList<>(goodsEntity.getImgPaths().size());
            for (String imgPath : goodsEntity.getImgPaths()) {
                goodsImgDos.add(new GoodsImgDo(goodsEntity.getGoodsId(), imgPath));
            }
            goodsImgDao.batchInsert(goodsImgDos);
        }
    }

    /**
     * 修改
     * @param shopId
     * @param goodsEntity
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void update(@RedisLockKeys Integer shopId, GoodsEntity goodsEntity) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goodsEntity.getGoodsSn())) {
            goodsEntity.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsRepository.isGoodsSnExist(goodsEntity.getGoodsSn())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsRepository.update(goodsEntity);

        // 删除旧的spec组
        goodsSpecProductRepository.deleteSpecByGoodsId(goodsEntity.getGoodsId());
        // 处理sku
        if (goodsEntity.getGoodsSpecs() != null && goodsEntity.getGoodsSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goodsEntity.getGoodsSpecs(), goodsEntity.getGoodsId());
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        // 处理需要修改的sku
        List<GoodsSpecProductEntity> goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), true);
        goodsSpecProductRepository.batchSkuUpdate(goodsSpecProductEntities);
        // 处理需要新增的sku
        goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), false);
        goodsSpecProductRepository.batchSkuInsert(goodsSpecProductEntities, goodsEntity.getGoodsId());

        // 处理标签
        goodsLabelRepository.deleteCouple(Collections.singletonList(goodsEntity.getGoodsId()), MedicalLabelConstant.GTA_GOODS);
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCoupleVal> goodsLabelCoupleVals = GoodsLabelCoupleVal.generateCouples(labelIds, goodsEntity.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
            goodsLabelRepository.batchInsertCouple(goodsLabelCoupleVals);
        }

        // 处理图片
        goodsImgDao.deleteByGoodsId(goodsEntity.getGoodsId());
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            List<GoodsImgDo> goodsImgDos = new ArrayList<>(goodsEntity.getImgPaths().size());
            for (String imgPath : goodsEntity.getImgPaths()) {
                goodsImgDos.add(new GoodsImgDo(goodsEntity.getGoodsId(), imgPath));
            }
            goodsImgDao.batchInsert(goodsImgDos);
        }
    }



    /**
     * 删除药品
     * @param goodsId
     */
    public void deleteByGoodsId(Integer goodsId) {
        goodsRepository.deleteGoodsById(goodsId);
        goodsSpecProductRepository.deleteSkuByGoodsId(goodsId);
        goodsSpecProductRepository.deleteSpecByGoodsId(goodsId);
        goodsLabelRepository.deleteCouple(Collections.singletonList(goodsId), MedicalLabelConstant.GTA_GOODS);
        goodsImgDao.deleteByGoodsId(goodsId);
    }

    /**
     * 根据商品id搜索商品信息
     * @param goodsId 商品id
     */
    public GoodsSelectVo getGoodsDetailByGoodsId(Integer goodsId) {
        GoodsSelectVo goodsSelectVo = goodsRepository.getByGoodsId(goodsId);
        if (goodsSelectVo == null) {
            return null;
        }
        //设置sku
        List<GoodsSpecProductVo> skus = goodsSpecProductRepository.getSkuByGoodsId(goodsId);
        goodsSelectVo.setGoodsSpecProducts(skus);

        //设置规格组
        if (!MedicalGoodsConstant.DEFAULT_SKU.equals(goodsSelectVo.getIsDefaultProduct())) {
            List<SpecVo> specVos = goodsSpecProductRepository.getSpecListByGoodsId(goodsId);
            goodsSelectVo.setGoodsSpecs(specVos);
        }

        // 品牌设置
        GoodsBrandDo goodsBrandDo = goodsBrandDao.getByBrandId(goodsSelectVo.getBrandId());
        if (goodsBrandDo != null) {
            goodsSelectVo.setBrandName(goodsBrandDo.getBrandName());
        }

        List<Integer> parentsSortIds = sortDao.getParentSortIds(goodsSelectVo.getSortId());
        // 标签设置
        List<GoodsLabelVo> goodsNormalLabels = goodsLabelRepository.getGoodsNormalLabels(parentsSortIds);
        List<GoodsLabelVo> goodsPointLabels = goodsLabelRepository.getGoodsPointLabels(goodsId);
        goodsSelectVo.setPointLabels(goodsPointLabels);
        goodsSelectVo.setNormalLabels(goodsNormalLabels);

        List<String> imgPaths = goodsImgDao.getByGoodsId(goodsId);
        goodsSelectVo.setGoodsImgs(imgPaths);

        return goodsSelectVo;
    }

    /**
     * 分页查询商品信息
     * @param pageListParam
     */
    public PageResult<GoodsPageListVo> getGoodsPageList(MedicalGoodsPageListParam pageListParam){
        GoodsPageListCondition goodsPageListCondition = GoodsParamConvertor.converPageListConditionFromPageListParam(pageListParam);
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

        PageResult<GoodsEntity> goodsEntityPageResult = goodsRepository.getGoodsPageList(goodsPageListCondition, pageListParam.getCurrentPage(), pageListParam.getPageRows());
        List<GoodsPageListVo> goodsPageListVos = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        List<Integer> goodsIds = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        List<Integer> sortIds = new ArrayList<>(goodsEntityPageResult.getDataList().size());
        for (GoodsEntity goodsEntity : goodsEntityPageResult.getDataList()) {
            GoodsPageListVo goodsPageListVo = new GoodsPageListVo(goodsEntity);
            goodsPageListVos.add(goodsPageListVo);
            goodsIds.add(goodsEntity.getGoodsId());
            sortIds.add(goodsEntity.getSortId());
        }
        PageResult<GoodsPageListVo> retPageResult =new PageResult<>();
        retPageResult.setPage(goodsEntityPageResult.getPage());
        retPageResult.setDataList(goodsPageListVos);

        // 准备需要映射的规格信息
        List<GoodsSpecProductEntity> goodsSpecProductEntities = goodsSpecProductRepository.listSkuByGoodsIds(goodsIds);
        Map<Integer, List<GoodsSpecProductGoodsPageListVo>> goodsIdSkusMap = goodsSpecProductEntities.stream().map(entity -> {
            GoodsSpecProductGoodsPageListVo vo = new GoodsSpecProductGoodsPageListVo();
            vo.setPrdId(entity.getPrdId());
            vo.setGoodsId(entity.getGoodsId());
            vo.setPrdNumber(entity.getPrdNumber());
            vo.setPrdPrice(entity.getPrdPrice());
            return vo;
        }).collect(Collectors.groupingBy(GoodsSpecProductGoodsPageListVo::getGoodsId));

        //准备标签数据
        Map<Integer, List<GoodsLabelVo>> goodsIdLabelsMap = medicalGoodsLabelService.getLabelGtaLabelMap(goodsIds, MedicalLabelConstant.GTA_GOODS);
        Map<Integer, List<GoodsLabelVo>> sortIdLabelsMap = medicalGoodsLabelService.getLabelGtaLabelMap(sortIds, MedicalLabelConstant.GTA_SORT);
        List<GoodsLabelVo> allGoodsLabels = medicalGoodsLabelService.getRelateAllGoodsLabels();

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
        int count = goodsRepository.countAllGoods();
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
}
