package com.vpu.mp.service.shop.goods;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.shop.medical.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.medical.sku.entity.SpecEntity;
import com.vpu.mp.service.pojo.shop.medical.sku.entity.SpecValEntity;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductDetailVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.GoodsSpecProductGoodsPageListVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.SpecValVo;
import com.vpu.mp.service.pojo.shop.medical.sku.vo.SpecVo;
import com.vpu.mp.service.shop.goods.aggregate.GoodsSpecProductAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月05日
 */
@Service
public class MedicalGoodsSpecProductService {
    @Autowired
    GoodsSpecProductAggregate goodsSpecProductAggregate;

    /**
     * 批量插入sku
     * @param goodsSpecProductEntities
     * @param goodsId
     */
    public void batchSkuInsert(List<GoodsSpecProductEntity> goodsSpecProductEntities, Integer goodsId) {
        for (GoodsSpecProductEntity goodsSpecProduct : goodsSpecProductEntities) {
            goodsSpecProduct.setGoodsId(goodsId);
        }
        goodsSpecProductAggregate.batchSkuInsert(goodsSpecProductEntities);
    }

    /**
     * 批量更新sku
     * @param goodsSpecProductEntities
     */
    public void batchSkuUpdate(List<GoodsSpecProductEntity> goodsSpecProductEntities){
        goodsSpecProductAggregate.batchSkuUpdate(goodsSpecProductEntities);
    }

    /**
     * 批量新增sku
     * @param goodsSpecProductEntities
     */
    public void batchSkuInsert(List<GoodsSpecProductEntity> goodsSpecProductEntities) {
        goodsSpecProductAggregate.batchSkuInsert(goodsSpecProductEntities);
    }

    /**
     * 删除sku
     * @param goodsId
     */
    public void deleteSkuByGoodsId(Integer goodsId) {
        goodsSpecProductAggregate.deleteSkuByGoodsId(goodsId);
    }

    /**
     * 根据商品id查询对应sku集合
     * @param goodsId
     * @return
     */
    public List<GoodsSpecProductDetailVo> listSkuDetailByGoodsId(Integer goodsId) {
        List<GoodsSpecProductEntity> goodsSpecProductEntities = goodsSpecProductAggregate.listSkuByGoodsId(goodsId);
        return goodsSpecProductEntities.stream().map(entity -> {
            GoodsSpecProductDetailVo vo = new GoodsSpecProductDetailVo();
            FieldsUtil.assign(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据商品id集合获取其分组信息
     * @param goodsIds
     * @return
     */
    public Map<Integer, List<GoodsSpecProductGoodsPageListVo>> groupSkuSimpleByGoodsIds(List<Integer> goodsIds) {
        List<GoodsSpecProductEntity> goodsSpecProductEntities = goodsSpecProductAggregate.listSkuByGoodsIds(goodsIds);
        return goodsSpecProductEntities.stream().map(entity -> {
            GoodsSpecProductGoodsPageListVo vo = new GoodsSpecProductGoodsPageListVo();
            vo.setPrdId(entity.getPrdId());
            vo.setGoodsId(entity.getGoodsId());
            vo.setPrdNumber(entity.getPrdNumber());
            vo.setPrdPrice(entity.getPrdPrice());
            return vo;
        }).collect(Collectors.groupingBy(GoodsSpecProductGoodsPageListVo::getGoodsId));
    }

    /**
     * 批量插入规格组
     * @param specEntities
     * @param goodsId
     */
    public void batchSpecInsert(List<SpecEntity> specEntities, Integer goodsId) {
        for (SpecEntity specEntity : specEntities) {
            specEntity.setGoodsId(goodsId);
            for (SpecValEntity goodsSpecVal : specEntity.getGoodsSpecVals()) {
                goodsSpecVal.setGoodsId(goodsId);
            }
        }
        goodsSpecProductAggregate.batchSpecInsert(specEntities);
    }

    /**
     * 根据商品id删除规格组
     * @param goodsId
     */
    public void deleteSpecByGoodsId(Integer goodsId) {
        goodsSpecProductAggregate.deleteSpecByGoodsId(goodsId);
    }

    /**
     * 根据商品id获取其对应规格组信息
     * @param goodsId
     * @return
     */
    public List<SpecVo> listSpecByGoodsId(Integer goodsId){
        List<SpecEntity> specEntities = goodsSpecProductAggregate.listSpecListByGoodsId(goodsId);

        List<SpecVo> specVos = new ArrayList<>(specEntities.size());
        for (SpecEntity specEntity : specEntities) {
            SpecVo specVo = new SpecVo();
            specVo.setGoodsId(specEntity.getGoodsId());
            specVo.setSpecId(specEntity.getSpecId());
            specVo.setSpecName(specEntity.getSpecName());

            List<SpecValVo> specValVos = new ArrayList<>(specEntity.getGoodsSpecVals().size());
            for (SpecValEntity goodsSpecValEntity : specEntity.getGoodsSpecVals()) {
                SpecValVo specValVo = new SpecValVo();
                specValVo.setGoodsId(goodsSpecValEntity.getGoodsId());
                specValVo.setSpecId(goodsSpecValEntity.getSpecId());
                specValVo.setSpecValName(goodsSpecValEntity.getSpecValName());
                specValVos.add(specValVo);
            }
            specVo.setGoodsSpecVals(specValVos);
            specVos.add(specVo);
        }
        return specVos;
    }

}
