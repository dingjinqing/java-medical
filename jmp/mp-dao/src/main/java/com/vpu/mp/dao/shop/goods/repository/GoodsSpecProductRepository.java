package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsSpecProductDo;
import com.vpu.mp.common.pojo.shop.table.SpecDo;
import com.vpu.mp.common.pojo.shop.table.SpecValDo;
import com.vpu.mp.dao.shop.goods.GoodsSpecProductDao;
import com.vpu.mp.dao.shop.goods.SpecDao;
import com.vpu.mp.dao.shop.goods.SpecValDao;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.sku.entity.SpecEntity;
import com.vpu.mp.service.pojo.shop.sku.entity.SpecValEntity;
import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductVo;
import com.vpu.mp.service.pojo.shop.sku.vo.SpecValVo;
import com.vpu.mp.service.pojo.shop.sku.vo.SpecVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Component
public class GoodsSpecProductRepository {
    @Autowired
    GoodsSpecProductDao goodsSpecProductDao;
    @Autowired
    SpecDao specDao;
    @Autowired
    SpecValDao specValDao;

    /**
     * 获取GoodsSpecProduct转GoodsSpecProductDo时忽略的字段
     * @return 需要忽略的字段
     */
    private static Set<String> getSkuAssignIgnoreFields(){
        Set<String> assignIgnoreField = new HashSet<>(1);
        assignIgnoreField.add("specs");
        return assignIgnoreField;
    }

    /**
     * 获取GoodsSpecProduct转GoodsSpecProductDo时忽略的字段
     * @return 需要忽略的字段
     */
    private static Set<String> getSpecAssignIgnoreFields(){
        Set<String> assignIgnoreField = new HashSet<>(1);
        assignIgnoreField.add("specVals");
        return assignIgnoreField;
    }

    /**
     * 批量插入sku
     * @param goodsSpecProductEntities
     */
    public void batchSkuInsert(List<GoodsSpecProductEntity> goodsSpecProductEntities, Integer goodsId){
        List<GoodsSpecProductDo> goodsSpecProductDos = new ArrayList<>(goodsSpecProductEntities.size());

        for (GoodsSpecProductEntity goodsSpecProductEntity : goodsSpecProductEntities) {
            goodsSpecProductEntity.setGoodsId(goodsId);
            GoodsSpecProductDo goodsSpecProductDo = new GoodsSpecProductDo();
            FieldsUtil.assignWithIgnoreField(goodsSpecProductEntity,goodsSpecProductDo,getSkuAssignIgnoreFields());
            goodsSpecProductDos.add(goodsSpecProductDo);
        }
        goodsSpecProductDao.batchInsert(goodsSpecProductDos);
    }

    /**
     * 批量更新sku
     * @param goodsSpecProductEntities
     */
    public void batchSkuUpdate(List<GoodsSpecProductEntity> goodsSpecProductEntities) {
        List<GoodsSpecProductDo> goodsSpecProductDos = new ArrayList<>(goodsSpecProductEntities.size());
        for (GoodsSpecProductEntity goodsSpecProductEntity : goodsSpecProductEntities) {
            GoodsSpecProductDo goodsSpecProductDo = new GoodsSpecProductDo();
            FieldsUtil.assignWithIgnoreField(goodsSpecProductEntity,goodsSpecProductDo,getSkuAssignIgnoreFields());
            goodsSpecProductDos.add(goodsSpecProductDo);
        }
        goodsSpecProductDao.batchUpdate(goodsSpecProductDos);
    }

    /**
     * 批量插入规格组
     * @param specEntities 规格组
     */
    public void batchSpecInsert(List<SpecEntity> specEntities, Integer goodsId) {
        List<SpecDo> specDos = new ArrayList<>(specEntities.size());

        Set<String> sepcAssignIgnoreFields = getSpecAssignIgnoreFields();
        for (SpecEntity specEntity : specEntities) {
            specEntity.setGoodsId(goodsId);
            SpecDo specDo = new SpecDo();
            FieldsUtil.assignWithIgnoreField(specEntity,specDo,sepcAssignIgnoreFields);
            specDos.add(specDo);
        }
        specDao.batchInsert(specDos);

        List<SpecValEntity> specValEntities = new ArrayList<>(2);
        List<SpecValDo> specValDos = new ArrayList<>(2);
        // 设置specId，提取specVal准备入库
        for (int i = 0; i < specDos.size(); i++) {
            SpecEntity specEntity = specEntities.get(i);
            specEntity.setSpecId(specDos.get(i).getSpecId());

            for (SpecValEntity specValEntity : specEntity.getSpecVals()) {
                specValEntities.add(specValEntity);
                specValEntity.setGoodsId(goodsId);
                specValEntity.setSpecId(specEntity.getSpecId());
                SpecValDo specValDo= new SpecValDo(specEntity.getSpecId(),goodsId, specValEntity.getSpecValName());
                specValDos.add(specValDo);
            }
        }

        specValDao.batchInsert(specValDos);
        for (int i = 0; i < specValDos.size(); i++) {
            specValEntities.get(i).setSpecValId(specValDos.get(i).getSpecValId());
        }
    }

    /**
     * @param goodsId
     * @return
     */
    public List<GoodsSpecProductVo> getSkuByGoodsId(Integer goodsId){
        List<GoodsSpecProductDo> goodsSpecProductDos = goodsSpecProductDao.getSkuByGoodsId(goodsId);

        List<GoodsSpecProductVo> collect = goodsSpecProductDos.stream().map(sku -> {
            GoodsSpecProductVo goodsSpecProductVo = new GoodsSpecProductVo();
            FieldsUtil.assign(sku, goodsSpecProductVo);
            return goodsSpecProductVo;
        }).collect(Collectors.toList());

        return collect;
    }

    public List<SpecVo> getSpecListByGoodsId(Integer goodsId) {
        List<SpecDo> specDos = specDao.getSpecsByGoodsId(goodsId);
        List<SpecValDo> specValDos = specValDao.getSepcValsByGoodsId(goodsId);

        List<SpecVo> specVos = new ArrayList<>(specDos.size());
        for (SpecDo specDo : specDos) {
            SpecVo specVo = new SpecVo();
            specVo.setGoodsId(goodsId);
            specVo.setSpecId(specDo.getSpecId());
            specVo.setSpecName(specDo.getSpecName());
            specVos.add(specVo);
        }
        Map<Integer, List<SpecValDo>> specValDoGroup = specValDos.stream().collect(Collectors.groupingBy(SpecValDo::getSpecId));

        for (SpecVo specVo : specVos) {
            List<SpecValDo> svds = specValDoGroup.get(specVo.getSpecId());
            List<SpecValVo> svls = svds.stream().map(svd -> {
                SpecValVo vo = new SpecValVo();
                vo.setGoodsId(goodsId);
                vo.setSpecId(svd.getSpecId());
                vo.setSpecValId(svd.getSpecValId());
                vo.setSpecValName(svd.getSpecValName());
                return vo;
            }).collect(Collectors.toList());

            specVo.setGoodsSpecVals(svls);
        }

        return specVos;
    }

    /**
     * 删除sku
     * @param goodsId
     */
    public void deleteSkuByGoodsId(Integer goodsId) {
        goodsSpecProductDao.deleteByGoodsId(goodsId);
    }

    /**
     * 根据商品id删除规格组
     * @param goodsId
     */
    public void deleteSpecByGoodsId(Integer goodsId) {
        specDao.deleteByGoodsId(goodsId);
        specValDao.deleteByGoodsId(goodsId);
    }
}
