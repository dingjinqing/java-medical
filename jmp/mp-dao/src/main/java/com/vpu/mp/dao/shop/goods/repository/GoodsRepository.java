package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.goods.Goods;
import com.vpu.mp.common.pojo.shop.table.GoodsDo;
import com.vpu.mp.dao.shop.goods.*;
import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
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

    @Autowired
    GoodsSpecProductRepository goodsSpecProductRepository;
    @Autowired
    GoodsLabelRepository goodsLabelRepository;

    public void insert(Goods goods) {
        Set<String> assignIgnoreField = new HashSet<>(2);
        assignIgnoreField.add("goodsSpecProducts");
        assignIgnoreField.add("goodsMedicalInfo");

        GoodsDo goodsDo = new GoodsDo();
        FieldsUtil.assignWithIgnoreField(goods,goodsDo,assignIgnoreField);

        Integer goodsId = goodsDao.insert(goodsDo);
    }
}
