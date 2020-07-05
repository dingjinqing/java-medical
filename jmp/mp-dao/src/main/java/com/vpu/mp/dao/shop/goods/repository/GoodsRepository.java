package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsDo;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.service.pojo.shop.goods.entity.Goods;
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsMedicalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsRepository {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    GoodsMedicalInfoDao goodsMedicalInfoDao;

    /**
     * 商品新增
     * @param goods
     */
    public void insert(Goods goods) {
        GoodsDo goodsDo = new GoodsDo();
        FieldsUtil.assignWithIgnoreField(goods,goodsDo,getGoodsAssignIgnoreFields());
        goodsDao.insert(goodsDo);
        Integer goodsId = goodsDo.getGoodsId();
        goods.setGoodsId(goodsId);

        GoodsMedicalInfoDo goodsMedicalInfoDo = new GoodsMedicalInfoDo();
        GoodsMedicalInfo goodsMedicalInfo = goods.getGoodsMedicalInfo();
        goodsMedicalInfo.setGoodsId(goodsId);
        FieldsUtil.assign(goodsMedicalInfo,goodsMedicalInfoDo);
        goodsMedicalInfoDao.insert(goodsMedicalInfoDo);
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
        return assignIgnoreField;
    }
}
