package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.shop.goods.aggregate.GoodsSpecProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2020年07月05日
 */
@Service
public class MedicalGoodsSpecProductService {
    @Autowired
    GoodsSpecProductRepository goodsSpecProductRepository;


}
