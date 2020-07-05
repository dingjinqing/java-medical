package com.vpu.mp.service.shop.goods;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.util.medical.DateFormatStr;
import com.vpu.mp.dao.shop.goods.repository.GoodsRepository;
import com.vpu.mp.dao.shop.goods.repository.GoodsSpecProductRepository;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Service
public class MedicalGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsSpecProductRepository goodsSpecProductRepository;

    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void insert(@RedisLockKeys Integer shopId, Goods goods) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goods.getGoodsSn())) {
            goods.setGoodsSn(generateGoodsSn());
        }

        goodsRepository.insert(goods);
        if (goods.getSpecs() != null && goods.getSpecs().size() > 0) {
            goodsSpecProductRepository.batchSpecInsert(goods.getSpecs(),goods.getGoodsId());
        }



    }

    /**
     * 当前时间
     * 生成商品GoodsSn
     * @return
     */
    private String generateGoodsSn(){
        int count = goodsRepository.countAllGoods();
        String nowStr = DateUtil.format(new DateTime(), DateFormatStr.DATE_FORMAT_FULL_COMPACT);
        return String.format("G%s-%08d", nowStr, count);
    }
}
