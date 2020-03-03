package com.vpu.mp.service.shop.market.couponpack;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.coupon.pack.CouponPackVoucherVo;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static com.vpu.mp.db.shop.tables.MrkingVoucher.MRKING_VOUCHER;
import static org.jooq.impl.DSL.sum;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 15:59
 **/
@Service
public class CouponPackVoucherService extends ShopBaseService {

    /**
     * 获得礼包优惠券种类数
     * @param couponPackId
     * @return
     */
    public int getVoucherKindsNumber(int couponPackId){
        return db().selectCount().from(COUPON_PACK_VOUCHER).where(COUPON_PACK_VOUCHER.ACT_ID.eq(couponPackId)).and(COUPON_PACK_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOneInto(Integer.class);
    }

    /**
     * 获得礼包优惠券数
     * @param couponPackId
     * @return
     */
    public int getVoucherNumber(int couponPackId){
        Record r =  db().select(sum(COUPON_PACK_VOUCHER.TOTAL_AMOUNT).as("voucherNumber")).from(COUPON_PACK_VOUCHER).where(COUPON_PACK_VOUCHER.ACT_ID.eq(couponPackId)).and(COUPON_PACK_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne();
        if(r != null){
            return r.into(Integer.class);
        }else{
            return 0;
        }
    }

    /**
     * 获得礼包下属优惠券信息列表
     * @param packId
     * @return
     */
    public List<CouponPackVoucherVo> getCouponPackVoucherList(Integer packId){
        List<CouponPackVoucherVo> packList =  db().select(COUPON_PACK_VOUCHER.fields()).
            select(MRKING_VOUCHER.ACT_NAME,MRKING_VOUCHER.DENOMINATION,MRKING_VOUCHER.LEAST_CONSUME,MRKING_VOUCHER.RECOMMEND_GOODS_ID,MRKING_VOUCHER.RECOMMEND_PRODUCT_ID,MRKING_VOUCHER.RECOMMEND_SORT_ID,MRKING_VOUCHER.RECOMMEND_CAT_ID,MRKING_VOUCHER.ALIAS_CODE,MRKING_VOUCHER.ACT_CODE,MRKING_VOUCHER.VALIDITY,MRKING_VOUCHER.VALIDITY_TYPE,MRKING_VOUCHER.VALIDITY_HOUR,MRKING_VOUCHER.VALIDITY_MINUTE,MRKING_VOUCHER.RANDOM_MAX,MRKING_VOUCHER.START_TIME,MRKING_VOUCHER.END_TIME).
            from(COUPON_PACK_VOUCHER).leftJoin(MRKING_VOUCHER).on(COUPON_PACK_VOUCHER.VOUCHER_ID.eq(MRKING_VOUCHER.ID)).
            where(COUPON_PACK_VOUCHER.ACT_ID.eq(packId)).
            fetchInto(CouponPackVoucherVo.class);

        packList.forEach(p->{
            if(StringUtil.isNotEmpty(p.getRecommendCatId()) || StringUtil.isNotEmpty(p.getRecommendGoodsId()) || StringUtil.isNotEmpty(p.getRecommendProductId()) || StringUtil.isNotEmpty(p.getRecommendSortId())){
                p.setIsAllGoodsUse(false);
            }else{
                p.setIsAllGoodsUse(true);
            }
        });
        return packList;
    }
}
