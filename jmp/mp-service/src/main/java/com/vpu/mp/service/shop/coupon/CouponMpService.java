package com.vpu.mp.service.shop.coupon;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleCoupon;
import com.vpu.mp.service.pojo.wxapp.coupon.CouponPageDecorationVo;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: 王兵兵
 * @create: 2019-11-19 19:03
 **/
@Service
public class CouponMpService extends ShopBaseService {

    /**
     * 获取装修模块优惠券列表
     * @param moduleCoupon
     * @param userId
     * @return
     */
    public List<CouponPageDecorationVo> getPageIndexCouponList(ModuleCoupon moduleCoupon, int userId){
        List<CouponPageDecorationVo> couponList = new ArrayList<>();
        for(ModuleCoupon.Coupon coupon : moduleCoupon.getCouponAyy()){
            CouponPageDecorationVo couponVo = getCouponPageDecorationVo(coupon.getCouponId());

            //赋值该优惠券的可用状态，优先显示已领取
            if(couponVo == null){
                couponVo = new CouponPageDecorationVo();
                //已领取或领取达到极限
                couponVo.setStatus((byte)6);
            } else if(couponVo.getReceivePerPerson() > 0){
                //用户已 领取/发放 优惠券数
                int getCouponAmount = getUserCouponAmount(coupon.getCouponId(),userId);
                if(getCouponAmount >= couponVo.getReceivePerPerson()){
                    //已领取或领取达到极限
                    couponVo.setStatus((byte)5);
                }
            }else if(couponVo.getEnabled().equals(BaseConstant.COUPON_ENABLED_DISABLED)){
                //停用
                couponVo.setStatus((byte)4);
            }else if(couponVo.getValidityType().equals(BaseConstant.COUPON_VALIDITY_TYPE_FIXED)){
                if(DateUtil.getLocalDateTime().after(couponVo.getEndTime())){
                    //已过期
                    couponVo.setStatus((byte)2);
                }
            }else if(couponVo.getSurplus() == 0 && couponVo.getLimitSurplusFlag().equals(BaseConstant.COUPON_LIMIT_SURPLUS_FLAG_LIMITED)){
                //库存不足
                couponVo.setStatus((byte)3);
            }

            couponList.add(couponVo);
        }
        return couponList;
    }

    /**
     * 用户已 领取/发放 优惠券数
     * @param couponId
     * @param userId
     * @return
     */
    public int getUserCouponAmount(int couponId,int userId){
        return db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId))).fetchSingle().into(Integer.class);
    }

    private CouponPageDecorationVo getCouponPageDecorationVo(int couponId){
        Optional<CouponPageDecorationVo> vo =  db().select().from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(couponId)).fetchOptionalInto(CouponPageDecorationVo.class);
        return vo.orElse(null);
    }
}
