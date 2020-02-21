package com.vpu.mp.service.shop.market.packagesale;

import com.vpu.mp.db.shop.tables.records.PackageSaleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsListVo;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
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
     * @return
     */
    public int getUserGroupGoodsNumber(int userId,int packageId,byte groupId){
        return db().select(DSL.sum(PACKAGE_GOODS_CART.GOODS_NUMBER)).from(PACKAGE_GOODS_CART).
            where(PACKAGE_GOODS_CART.USER_ID.eq(userId)).
            and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageId)).
            and(PACKAGE_GOODS_CART.GROUP_ID.eq(groupId)).
            fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 获取确定分组合、确定商品ID已选的商品数量
     * @param userId
     * @param packageId
     * @param groupId
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

    /**
     * 当前用户所选一口价商品的总金额合优惠金额
     * @param userId
     * @param packageSaleRecord
     * @param allNumber 活动各个分组要求商品数量的总和
     * @return
     */
    public PackageSaleGoodsListVo.TotalMoney getUserPackageMoney(int userId, PackageSaleRecord packageSaleRecord, int allNumber){
        Result<Record2<Integer, BigDecimal>> r =  db().select(PACKAGE_GOODS_CART.GOODS_NUMBER,GOODS_SPEC_PRODUCT.PRD_PRICE).
            from(PACKAGE_GOODS_CART.leftJoin(GOODS).on(PACKAGE_GOODS_CART.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(PACKAGE_GOODS_CART.PRODUCT_ID))).
            where(PACKAGE_GOODS_CART.USER_ID.eq(userId)).
            and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageSaleRecord.getId())).
            and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).
            and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE)).fetch();

        int totalNumber = r.stream().mapToInt(Record2::value1).sum();
        BigDecimal totalMoney = r.stream().map(Record2::value2).reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalSelectMoney;
        if(packageSaleRecord.getPackageType().equals(PackSaleConstant.PACKAGE_TYPE_DISCOUNT)){
            totalSelectMoney = allNumber > totalNumber ? totalMoney : totalMoney.multiply(packageSaleRecord.getTotalRatio()).divide(BigDecimal.TEN);
        }else{
            totalSelectMoney = allNumber > totalNumber ? totalMoney : (packageSaleRecord.getTotalMoney().compareTo(totalMoney) < 0 ? packageSaleRecord.getTotalMoney() : totalMoney);
        }
        BigDecimal discountMoney = allNumber > totalNumber ? BigDecimal.ZERO : totalMoney.subtract(totalSelectMoney);

        PackageSaleGoodsListVo.TotalMoney res = new PackageSaleGoodsListVo.TotalMoney();
        res.setTotalSelectMoney(totalSelectMoney.setScale(2,BigDecimal.ROUND_HALF_UP));
        res.setDiscountMoney(discountMoney.setScale(2,BigDecimal.ROUND_HALF_UP));
        return res;
    }
}
