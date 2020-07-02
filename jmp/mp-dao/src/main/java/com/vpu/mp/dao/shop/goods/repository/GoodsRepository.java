package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.dao.shop.goods.*;
import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void insertGoods(){

    }
}
