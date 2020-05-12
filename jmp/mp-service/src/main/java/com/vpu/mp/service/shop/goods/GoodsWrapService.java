package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsDataIIllegalEnum;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsDataIllegalEnumWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 由于GoodsService类属性存在循环引用，导致添加切面时属性无法注入
 * 在此wrap类上添加切面进行锁控制
 * @author 李晓冰
 * @date 2020年03月27日
 */
@Service
public class GoodsWrapService extends ShopBaseService {
    @Autowired
    GoodsService goodsService;

    /**
     * 添加商品加锁函数
     *
     * @param shopId，加锁使用
     * @param goods       商品信息
     * @return
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public GoodsDataIIllegalEnum insertWithLock(@RedisLockKeys Integer shopId, Goods goods) {
        GoodsDataIllegalEnumWrap codeWrap = goodsService.insert(goods);
        if (codeWrap.getGoodsId() != null) {
            goodsService.updateEs(Collections.singletonList(codeWrap.getGoodsId()));
        }
        return codeWrap.getIllegalEnum();
    }

    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public GoodsDataIIllegalEnum updateWithLock(@RedisLockKeys Integer shopId, Goods goods){
        GoodsDataIllegalEnumWrap codeWrap = goodsService.update(goods);
        return codeWrap.getIllegalEnum();
    }
}
