package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsDo;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsMedicalInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Component
public class GoodsRepository {
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
        FieldsUtil.assignWithIgnoreField(goodsEntity,goodsDo,getGoodsAssignIgnoreFields());
        goodsDao.insert(goodsDo);
        Integer goodsId = goodsDo.getGoodsId();
        goodsEntity.setGoodsId(goodsId);

        if (MedicalGoodsConstant.GOODS_IS_MEDICAL.equals(goodsEntity.getIsMedical())){
            GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
            GoodsMedicalInfoEntity goodsMedicalInfoEntity = goodsEntity.getGoodsMedicalInfo();
            goodsMedicalInfoEntity.setGoodsId(goodsId);
            FieldsUtil.assign(goodsMedicalInfoEntity,goodsMedicalInfoDo);
            goodsMedicalInfoDao.insert(goodsMedicalInfoDo);
        }
    }

    /**
     * 商品修改
     * @param goodsEntity
     */
    public void update(GoodsEntity goodsEntity) {
        GoodsDo goodsDo = new GoodsDo();
        FieldsUtil.assignWithIgnoreField(goodsEntity,goodsDo,getGoodsAssignIgnoreFields());
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
     * 商品删除
     * @param goodId
     */
    public void deleteGoodsById(Integer goodId) {
        goodsDao.deleteByGoodsId(goodId);
        goodsMedicalInfoDao.deleteByGoodsId(goodId);
    }

    /**
     * 判断goodsSn是否存在
     * @param goodsSn
     * @return true 是 false 否
     */
    public boolean isGoodsSnExist(String goodsSn) {
        return goodsDao.isGoodsSnExist(goodsSn);
    }

    /**
     * 统计商品数量，包含已删除的
     * @return 商品数量
     */
    public int countAllGoods(){
        return goodsDao.countAllGoods();
    }

    /**
     * 获取Goods转GoodsDo时忽略的字段
     * @return 需要忽略的字段
     */
    private Set<String> getGoodsAssignIgnoreFields(){
        Set<String> assignIgnoreField = new HashSet<>(2);
        assignIgnoreField.add("goodsSpecProducts");
        assignIgnoreField.add("goodsMedicalInfo");
        assignIgnoreField.add("specs");
        return assignIgnoreField;
    }
}
