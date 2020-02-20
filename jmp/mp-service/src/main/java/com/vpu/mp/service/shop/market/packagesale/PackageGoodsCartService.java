package com.vpu.mp.service.shop.market.packagesale;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.PackageGoodsCart.PACKAGE_GOODS_CART;

/**
 * @author: 王兵兵
 * @create: 2020-02-20 18:18
 **/
@Service
public class PackageGoodsCartService extends ShopBaseService {
    /**
     * 获取确定分组已选的商品数量
     * @param userId
     * @param packageId
     * @param groupId
     * @param goodsId
     * @return
     */
    public int getUserGroupGoodsNumber(int userId,int packageId,byte groupId,int goodsId){
        return db().select(DSL.sum(PACKAGE_GOODS_CART.GOODS_NUMBER)).from(PACKAGE_GOODS_CART).
            where(PACKAGE_GOODS_CART.USER_ID.eq(userId)).
            and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageId)).
            and(PACKAGE_GOODS_CART.GROUP_ID.eq(groupId)).
            and(PACKAGE_GOODS_CART.GOODS_ID.eq(goodsId)).
            fetchOptionalInto(Integer.class).orElse(0);
    }
}
