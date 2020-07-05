package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.pojo.shop.goods.Goods;
import com.vpu.mp.dao.shop.goods.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Service
public class MedicalGoodsService {
    @Autowired
    GoodsRepository goodsRepository;

    public void insert(Goods goods) {
        goodsRepository.insert(goods);
    }
}
