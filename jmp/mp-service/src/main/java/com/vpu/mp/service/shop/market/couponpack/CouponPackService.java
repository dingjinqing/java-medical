package com.vpu.mp.service.shop.market.couponpack;

import com.vpu.mp.db.shop.tables.records.CouponPackRecord;
import com.vpu.mp.db.shop.tables.records.CouponPackVoucherRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackAddParam;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackVoucherAddParam;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.CouponPack.COUPON_PACK;
import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 11:01
 **/
@Service
public class CouponPackService extends ShopBaseService {

    @Autowired
    private CouponPackVoucherService couponPackVoucherService;

    /**
     * 启用状态
     */
    public static final byte STATUS_NORMAL = 1;
    /**
     * 停用状态
     */
    public static final byte STATUS_DISABLED = 0;

    /**
     * 新建优惠券礼包活动
     *
     */
    public void addCouponPack(CouponPackAddParam param) {
        this.transaction(()->{
            CouponPackRecord record = db().newRecord(COUPON_PACK);
            assign(param,record);
            record.insert();
            Integer couponPackId = record.getId();
            for(CouponPackVoucherAddParam goods : param.getCouponPackVoucher()){
                CouponPackVoucherRecord couponPackVoucherRecord = db().newRecord(COUPON_PACK_VOUCHER);
                assign(goods,couponPackVoucherRecord);
                couponPackVoucherRecord.setActId(couponPackId);
                couponPackVoucherRecord.insert();
            }
        });
    }

    /**
     * 首单特惠活动列表分页查询
     *
     */
    public PageResult<CouponPackPageListQueryVo> getPageList(CouponPackPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(COUPON_PACK.ID,COUPON_PACK.ACT_NAME,COUPON_PACK.PACK_NAME,COUPON_PACK.START_TIME,COUPON_PACK.END_TIME,COUPON_PACK.TOTAL_AMOUNT,COUPON_PACK.ACCESS_MODE,COUPON_PACK.ACCESS_COST,COUPON_PACK.STATUS).
            from(COUPON_PACK);
        buildOptions(select,param);
        select.where(COUPON_PACK.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(COUPON_PACK.CREATE_TIME.desc());
        PageResult<CouponPackPageListQueryVo> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),CouponPackPageListQueryVo.class);

        /**查询活动商品数量、订单付款数、付款用户数、付款总金额 */
        for(CouponPackPageListQueryVo vo : res.dataList){
            vo.setVoucherKindsNumber(couponPackVoucherService.getVoucherKindsNumber(vo.getId()));
            vo.setVoucherNumber(couponPackVoucherService.getVoucherNumber(vo.getId()));
            //todo
            vo.setIssueAmount(0);
        }

        return res;
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectWhereStep<? extends Record> select, CouponPackPageListQueryParam param) {
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.START_TIME.lt(now)).and(COUPON_PACK.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        if (isNotEmpty(param.getActName())) {
            select.where(COUPON_PACK.ACT_NAME.contains(param.getActName()));
        }
        if (isNotEmpty(param.getPackName())) {
            select.where(COUPON_PACK.PACK_NAME.contains(param.getPackName()));
        }
        if(param.getAccessMode() != null && param.getAccessMode() >= 0) {
            /** 领取方式过滤*/
            select.where(COUPON_PACK.ACCESS_MODE.eq(param.getAccessMode()));
        }
    }
}
