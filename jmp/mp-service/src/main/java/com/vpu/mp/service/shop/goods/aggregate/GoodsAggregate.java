package com.vpu.mp.service.shop.goods.aggregate;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsMatchParam;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestItemBo;
import com.vpu.mp.service.pojo.shop.medical.goods.convertor.GoodsConverter;
import com.vpu.mp.service.pojo.shop.medical.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.medical.goods.entity.GoodsMedicalInfoEntity;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsDetailVo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsMedicalInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Component
public class GoodsAggregate {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    GoodsMedicalInfoDao goodsMedicalInfoDao;

    /**
     * 商品新增
     * @param goodsEntity
     */
    public void insert(GoodsEntity goodsEntity) {
        GoodsDo goodsDo = new GoodsDo();
        FieldsUtil.assignWithIgnoreField(goodsEntity, goodsDo, getGoodsAssignIgnoreFields());
        goodsDao.insert(goodsDo);
        Integer goodsId = goodsDo.getGoodsId();
        goodsEntity.setGoodsId(goodsId);

        if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(goodsEntity.getIsMedical())) {
            GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
            GoodsMedicalInfoEntity goodsMedicalInfoEntity = goodsEntity.getGoodsMedicalInfo();
            goodsMedicalInfoEntity.setGoodsId(goodsId);
            FieldsUtil.assign(goodsMedicalInfoEntity, goodsMedicalInfoDo);
            goodsMedicalInfoDao.insert(goodsMedicalInfoDo);
        }
    }

    /**
     * 商品修改
     * @param goodsEntity
     */
    public void update(GoodsEntity goodsEntity) {
        GoodsDo goodsDo = new GoodsDo();
        FieldsUtil.assignWithIgnoreField(goodsEntity, goodsDo, getGoodsAssignIgnoreFields());
        goodsDao.update(goodsDo);

        if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(goodsEntity.getIsMedical())) {
            GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
            GoodsMedicalInfoEntity goodsMedicalInfoEntity = goodsEntity.getGoodsMedicalInfo();
            FieldsUtil.assign(goodsMedicalInfoEntity, goodsMedicalInfoDo);
            goodsMedicalInfoDao.update(goodsMedicalInfoDo);
        } else {
            goodsMedicalInfoDao.deleteByGoodsId(goodsEntity.getGoodsId());
        }
    }

    /**
     * 新增外部药品信息
     * @param goodsMedicalExternalRequestItemBos
     */
    public void batchInsert(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        List<GoodsDo> goodsDos = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());
        List<GoodsMedicalInfoDo> goodsMedicalInfoDos = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());

        for (GoodsMedicalExternalRequestItemBo bo : goodsMedicalExternalRequestItemBos) {
            GoodsDo goodsDo = GoodsConverter.convertGoodsMedicalExternalRequestItemBoToGoodsDo(bo);

            GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
            FieldsUtil.assign(bo, goodsMedicalInfoDo);
            goodsDos.add(goodsDo);
            goodsMedicalInfoDos.add(goodsMedicalInfoDo);
        }
        goodsDao.batchInsert(goodsDos);

        Map<String, GoodsDo> goodsSnMap = goodsDos.stream().collect(Collectors.toMap(GoodsDo::getGoodsSn, Function.identity()));

        for (GoodsMedicalInfoDo goodsMedicalInfoDo : goodsMedicalInfoDos) {
            GoodsDo goodsDo = goodsSnMap.get(goodsMedicalInfoDo.getGoodsCode());
            goodsMedicalInfoDo.setGoodsId(goodsDo.getGoodsId());
        }
        goodsMedicalInfoDao.batchInsert(goodsMedicalInfoDos);

        for (GoodsMedicalExternalRequestItemBo bo : goodsMedicalExternalRequestItemBos) {
            GoodsDo goodsDo = goodsSnMap.get(bo.getGoodsCode());
            bo.setGoodsId(goodsDo.getGoodsId());
        }
    }

    /**
     * 批量更新外部药品信息
     * @param goodsMedicalExternalRequestItemBos
     */
    public void batchUpdate(List<GoodsMedicalExternalRequestItemBo> goodsMedicalExternalRequestItemBos) {
        List<Integer> goodsIds = goodsMedicalExternalRequestItemBos.stream().map(GoodsMedicalExternalRequestItemBo::getGoodsId).collect(Collectors.toList());

        List<GoodsDo> goodsDos = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());
        List<GoodsMedicalInfoDo> goodsMedicalInfoDos = new ArrayList<>(goodsMedicalExternalRequestItemBos.size());
        Map<Integer, Integer> goodsIdMedicalIdMap = goodsMedicalInfoDao.listIdWithGoodsId(goodsIds).stream().collect(Collectors.toMap(GoodsMedicalInfoDo::getGoodsId, GoodsMedicalInfoDo::getId, (x1, x2) -> x1));

        for (GoodsMedicalExternalRequestItemBo bo : goodsMedicalExternalRequestItemBos) {
            GoodsDo goodsDo = GoodsConverter.convertGoodsMedicalExternalRequestItemBoToGoodsDo(bo);
            GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
            FieldsUtil.assign(bo, goodsMedicalInfoDo);
            goodsMedicalInfoDo.setId(goodsIdMedicalIdMap.get(bo.getGoodsId()));

            if (DelFlag.DISABLE_VALUE.equals(goodsDo.getDelFlag())){
                goodsDo.setGoodsSn(DelFlag.DEL_ITEM_PREFIX+goodsDo.getGoodsId()+DelFlag.DEL_ITEM_SPLITER+goodsDo.getGoodsSn());
                goodsDo.setDelFlag(DelFlag.DISABLE_VALUE);
                goodsMedicalInfoDo.setIsDelete(DelFlag.DISABLE_VALUE);
            }
            goodsDos.add(goodsDo);
            goodsMedicalInfoDos.add(goodsMedicalInfoDo);
        }
        goodsDao.batchUpdate(goodsDos);
        goodsMedicalInfoDao.batchUpdate(goodsMedicalInfoDos);
    }


    /**
     * 商品删除
     * @param goodId
     */
    public void deleteGoodsById(Integer goodId) {
        goodsDao.deleteByGoodsId(goodId);
        goodsMedicalInfoDao.deleteByGoodsId(goodId);
    }

    /**
     * 列举出已有的药品goodsCode
     * @param goodsCodes
     * @return
     */
    public Map<String,Integer> mapGoodsCodeToGoodsId(List<String> goodsCodes) {
        return goodsDao.mapGoodsSnToGoodsId(goodsCodes, MedicalGoodsConstant.GOODS_IS_MEDICAL);
    }

    /**
     * 根据商品id查询
     * @param goodsId
     * @return
     */
    public GoodsDetailVo getByGoodsId(Integer goodsId) {
        GoodsDo goodsDo = goodsDao.getByGoodsId(goodsId);
        if (goodsDo == null) {
            return null;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        FieldsUtil.assign(goodsDo, goodsDetailVo);

        if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(goodsDo.getIsMedical())) {
            GoodsMedicalInfoDo goodsMedicalInfoDo = goodsMedicalInfoDao.getByGoodsId(goodsId);
            GoodsMedicalInfoVo goodsMedicalInfoVo = new GoodsMedicalInfoVo();
            FieldsUtil.assign(goodsMedicalInfoDo, goodsMedicalInfoVo);
            goodsDetailVo.setGoodsMedicalInfoVo(goodsMedicalInfoVo);
        }
        return goodsDetailVo;
    }

    /**
     * 判断goodsSn是否存在
     * @param goodsSn
     * @return true 是 false 否
     */
    public boolean isGoodsSnExist(String goodsSn,Integer goodsId) {
        return goodsDao.isGoodsSnExist(goodsSn,goodsId);
    }

    /**
     * 统计商品数量，包含已删除的
     * @return 商品数量
     */
    public int countAllGoods() {
        return goodsDao.countAllGoods();
    }

    /**
     * 分页获取商品信息
     * @param goodsPageListCondition 商品分页统一条件
     * @param curPage                当前页
     * @param pageRows               行数
     * @return 分页信息
     */
    public PageResult<GoodsEntity> getGoodsPageList(GoodsPageListCondition goodsPageListCondition, Integer curPage, Integer pageRows) {
        PageResult<GoodsDo> goodsDoPageResult = goodsDao.getGoodsPageList(goodsPageListCondition, curPage, pageRows);
        List<GoodsDo> dataList = goodsDoPageResult.dataList;
        List<Integer> goodsIds = dataList.stream().mapToInt(GoodsDo::getGoodsId).boxed().collect(Collectors.toList());

        List<GoodsMedicalInfoDo> goodsMedicalInfoDos = goodsMedicalInfoDao.listByGoodsIds(goodsIds);
        Map<Integer, GoodsMedicalInfoEntity> medicalInfoEntityMap = goodsMedicalInfoDos.stream().map(goodsMedicalInfoDo -> {
            GoodsMedicalInfoEntity goodsMedicalInfoEntity = new GoodsMedicalInfoEntity();
            FieldsUtil.assign(goodsMedicalInfoDo, goodsMedicalInfoEntity);
            return goodsMedicalInfoEntity;
        }).collect(Collectors.toMap(GoodsMedicalInfoEntity::getGoodsId, Function.identity()));

        List<GoodsEntity> retGoodsPageList = new ArrayList<>(dataList.size());
        Set<String> goodsAssignIgnoreFields = getGoodsAssignIgnoreFields();

        for (GoodsDo goodsDo : dataList) {
            GoodsEntity goodsEntity = new GoodsEntity();
            FieldsUtil.assignWithIgnoreField(goodsDo, goodsEntity, goodsAssignIgnoreFields);
            goodsEntity.setGoodsMedicalInfo(medicalInfoEntityMap.get(goodsEntity.getGoodsId()));
            retGoodsPageList.add(goodsEntity);
        }

        PageResult<GoodsEntity> pageResult = new PageResult<>();
        pageResult.setPage(goodsDoPageResult.getPage());
        pageResult.setDataList(retGoodsPageList);

        return pageResult;
    }

    /**
     * 获取Goods转GoodsDo时忽略的字段
     * @return 需要忽略的字段
     */
    private Set<String> getGoodsAssignIgnoreFields() {
        Set<String> assignIgnoreField = new HashSet<>(2);
        assignIgnoreField.add("goodsSpecProducts");
        assignIgnoreField.add("goodsMedicalInfo");
        assignIgnoreField.add("specs");
        assignIgnoreField.add("goodsMedicalInfo");
        assignIgnoreField.add("imgPaths");
        assignIgnoreField.add("labelIds");
        return assignIgnoreField;
    }

    /**
     * 根据goodsId,goodsCommonName,goodsQualityRatio,productionEnterprise匹配药品Id
     * @param goodsMatchParam
     * @return
     */
    public Integer matchGoodsMedical(GoodsMatchParam goodsMatchParam) {
        Integer goodsId;
        goodsId = goodsDao.getGoodsIdByInfo(goodsMatchParam);
        if (goodsId != null) {
            return goodsId;
        }
        goodsMatchParam.setGoodsId(null);
        goodsId = goodsDao.getGoodsIdByInfo(goodsMatchParam);
        if (goodsId != null) {
            return goodsId;
        }
        goodsMatchParam.setProductionEnterprise(null);
        goodsId = goodsDao.getGoodsIdByInfo(goodsMatchParam);
        if (goodsId != null) {
            return goodsId;
        }
        goodsMatchParam.setGoodsQualityRatio(null);
        goodsId = goodsDao.getGoodsIdByInfo(goodsMatchParam);
        return goodsId;
    }
}
