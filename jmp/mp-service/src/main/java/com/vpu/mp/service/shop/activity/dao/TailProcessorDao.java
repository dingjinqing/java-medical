package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;

/**
 *小程序商品tail处理器获取数据库信息类
 * @author 李晓冰
 * @date 2019年11月19日
 */
@Service
public class TailProcessorDao extends ShopBaseService {

    public boolean isCollectedGoods(Integer userId,Integer goodsId){
        int i = db().fetchCount(USER_COLLECTION, USER_COLLECTION.USER_ID.eq(userId).and(USER_COLLECTION.GOODS_ID.eq(goodsId)));
        return i>0;
    }
}
