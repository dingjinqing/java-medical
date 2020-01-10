package com.vpu.mp.service.shop.coupon;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.CustomerAvailCoupons;
import com.vpu.mp.db.shop.tables.MrkingVoucher;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 用户持有的优惠券
 *
 * @author 孔德成
 * @date 2019/8/13 16:14
 */
@Service
public class CouponHoldService extends ShopBaseService {


    /**
     * 查询用户收到的优惠券信息（领取明细）
     * @return
     */
    public PageResult<CouponHoldListVo> getCouponHoldList(CouponHoldListParam param) {
        User u = USER;
        /* 优惠券*/
        MrkingVoucher m = MRKING_VOUCHER;
        /* 用户持有的优惠券 */
        CustomerAvailCoupons h = CUSTOMER_AVAIL_COUPONS;

        SelectJoinStep<? extends Record> select =
            db().select(u.USERNAME, u.MOBILE,
                        m.ACT_NAME.as("coupon_name"),m.USE_SCORE,m.SCORE_NUMBER,
                        h.ID,h.ACCESS_MODE, h.IS_USED,h.ORDER_SN, h.START_TIME, h.END_TIME, h.CREATE_TIME, h.USED_TIME,h.DEL_FLAG)
                .from(h)
                .leftJoin(m).on(h.ACT_ID.eq(m.ID))
                .leftJoin(u).on(h.USER_ID.eq(u.USER_ID));
        buildOptions(select,param);
        select.orderBy(h.CREATE_TIME.desc());
        PageResult<CouponHoldListVo> detailList = this.getPageResult(select,param.getCurrentPage(),param.getPageRows(), CouponHoldListVo.class);
        return detailList ;
    }


    /**
     * 按条件查询领取明细
     * @param select
     * @param param
     * @return
     */
    private SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select, CouponHoldListParam param) {
        if (param.getActId()!=null){
            select.where(CUSTOMER_AVAIL_COUPONS.ACT_ID .eq(param.getActId()));
        }
        if(!StringUtils.isNullOrEmpty(param.getMobile())) {
            select.where(USER.MOBILE.like(this.likeValue(param.getMobile())));
        }
        if(!StringUtils.isNullOrEmpty(param.getUsername())) {
            select.where(USER.USERNAME.like(this.likeValue(param.getUsername())));
        }
        if (param.getUserId()!=null){
            select.where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(param.getUserId()));
        }
        if(param.getStatus() != null) {
            Timestamp nowTime =new Timestamp(System.currentTimeMillis());
            if (param.getStatus()==1){
                select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 0))
                        .and(CUSTOMER_AVAIL_COUPONS.END_TIME.ge(nowTime));
            }else if (param.getStatus()==2){
                select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 1));
            }else if (param.getStatus()==3){
                select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 0))
                        .and(CUSTOMER_AVAIL_COUPONS.END_TIME.lt(nowTime));
            }else if (param.getStatus()==4){
                select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 3));
            }
        }
        if (param.getAccessId()!=null && param.getGetSource()!=null){
            select.where(CUSTOMER_AVAIL_COUPONS.ACCESS_ID.eq(param.getAccessId()))
            .and(CUSTOMER_AVAIL_COUPONS.GET_SOURCE.eq(param.getGetSource()));
        }
        else if (param.getGetSource()!=null){
            select.where(CUSTOMER_AVAIL_COUPONS.GET_SOURCE.eq(param.getGetSource()));
        }
        select.where(CUSTOMER_AVAIL_COUPONS.DEL_FLAG.eq((byte)0));
        return select;

    }
}
