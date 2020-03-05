package com.vpu.mp.service.shop.market.couponpack;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.*;

import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;

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
}
