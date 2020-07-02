package com.vpu.mp.dao.shop.label.repository;

import com.vpu.mp.dao.shop.label.GoodsLabelCoupleDao;
import com.vpu.mp.dao.shop.label.GoodsLabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsLabelRepository {
    @Autowired
    GoodsLabelDao goodsLabelDao;
    @Autowired
    GoodsLabelCoupleDao goodsLabelCoupleDao;
}
