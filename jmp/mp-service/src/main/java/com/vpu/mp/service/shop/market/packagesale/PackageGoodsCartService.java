package com.vpu.mp.service.shop.market.packagesale;

import com.vpu.mp.db.shop.tables.records.PackageSaleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleCartGoodsVo;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsListVo;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
     * @return
     */
    public int getUserGroupGoodsNumber(int userId,int packageId){
        return getUserGroupGoodsNumber(userId,packageId,null,null);
    }

    /**
     * 获取确定分组已选的商品数量
     * @param userId
     * @param packageId
     * @param groupId
     * @return
     */
    public int getUserGroupGoodsNumber(int userId,int packageId,byte groupId){
        return getUserGroupGoodsNumber(userId,packageId,groupId,null);
    }

    /**
     * 获取确定分组合、确定商品ID已选的商品数量
     * @param userId
     * @param packageId
     * @param groupId
     * @return
     */
    public int getUserGroupGoodsNumber(Integer userId,Integer packageId,Byte groupId,Integer goodsId){
        SelectConditionStep<?> select =  db().select(DSL.sum(PACKAGE_GOODS_CART.GOODS_NUMBER)).from(PACKAGE_GOODS_CART).
            where(PACKAGE_GOODS_CART.USER_ID.eq(userId)).
            and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageId));
        if(groupId != null){
            select.and(PACKAGE_GOODS_CART.GROUP_ID.eq(groupId));
        }
        if(goodsId != null){
            select.and(PACKAGE_GOODS_CART.GOODS_ID.eq(goodsId));
        }

        return select.fetchOptionalInto(Integer.class).orElse(0);
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
        BigDecimal totalMoney = r.stream().map(record2->{
            return record2.get(GOODS_SPEC_PRODUCT.PRD_PRICE).multiply(new BigDecimal(record2.get(PACKAGE_GOODS_CART.GOODS_NUMBER)));
        }).reduce(BigDecimal.ZERO,BigDecimal::add);

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

    /**
     * 当前用户所选一口价商品的总金额
     * @param userId
     * @param packageSaleRecord
     * @return
     */
    public BigDecimal getUserPackageMoney(int userId, PackageSaleRecord packageSaleRecord){
        Result<Record2<Integer, BigDecimal>> r =  db().select(PACKAGE_GOODS_CART.GOODS_NUMBER,GOODS_SPEC_PRODUCT.PRD_PRICE).
            from(PACKAGE_GOODS_CART.leftJoin(GOODS).on(PACKAGE_GOODS_CART.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(PACKAGE_GOODS_CART.PRODUCT_ID))).
            where(PACKAGE_GOODS_CART.USER_ID.eq(userId)).
            and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageSaleRecord.getId())).
            and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).
            and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE)).fetch();

        BigDecimal totalMoney = r.stream().map(record2->{
            return record2.get(GOODS_SPEC_PRODUCT.PRD_PRICE).multiply(new BigDecimal(record2.get(PACKAGE_GOODS_CART.GOODS_NUMBER)));
        }).reduce(BigDecimal.ZERO,BigDecimal::add);

        return totalMoney;
    }

    /**
     * 用户已选入购物车的加价购商品
     * @param userId
     * @param packageId
     * @param groupId
     * @return
     */
    public List<PackageSaleCartGoodsVo> getUserGroupCartGoods(Integer userId,Integer packageId,Byte groupId){
        return db().select(PACKAGE_GOODS_CART.GOODS_ID,PACKAGE_GOODS_CART.PRODUCT_ID,PACKAGE_GOODS_CART.GOODS_NUMBER,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_IMG,GOODS_SPEC_PRODUCT.PRD_NUMBER,GOODS.GOODS_NAME,GOODS.SHOP_PRICE,GOODS.GOODS_IMG,GOODS.MARKET_PRICE)
            .from(PACKAGE_GOODS_CART).innerJoin(GOODS).on(GOODS.GOODS_ID.eq(PACKAGE_GOODS_CART.GOODS_ID)).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(PACKAGE_GOODS_CART.PRODUCT_ID))
            .where(PACKAGE_GOODS_CART.USER_ID.eq(userId))
            .and(PACKAGE_GOODS_CART.PACKAGE_ID.eq(packageId))
            .and(PACKAGE_GOODS_CART.GROUP_ID.eq(groupId))
            .and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchInto(PackageSaleCartGoodsVo.class);
    }
}
