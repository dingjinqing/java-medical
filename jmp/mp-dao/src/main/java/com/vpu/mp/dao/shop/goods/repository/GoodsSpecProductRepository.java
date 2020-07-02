package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.dao.shop.goods.GoodsSpecProductDao;
import com.vpu.mp.dao.shop.goods.SpecDao;
import com.vpu.mp.dao.shop.goods.SpecValDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsSpecProductRepository {
    @Autowired
    GoodsSpecProductDao goodsSpecProductDao;
    @Autowired
    SpecDao specDao;
    @Autowired
    SpecValDao specValDao;
}
