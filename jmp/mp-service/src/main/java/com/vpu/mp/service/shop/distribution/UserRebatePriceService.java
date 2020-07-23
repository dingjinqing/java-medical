package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.db.shop.tables.UserRebatePrice;
import com.vpu.mp.db.shop.tables.records.UserRebatePriceRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.UserRebatePrice.USER_REBATE_PRICE;


/**
 * 用户分销价格表
 * @author 王帅
 */
@Service
public class UserRebatePriceService extends ShopBaseService {

    final static private UserRebatePrice TABLE = USER_REBATE_PRICE;

    public Result<UserRebatePriceRecord> getUserRebatePrice(Integer userId, Integer[] prdIds) {
        return db().selectFrom(TABLE).where(TABLE.USER_ID.eq(userId).and(TABLE.PRODUCT_ID.in(prdIds)).and(TABLE.EXPIRE_TIME.gt(DateUtils.getSqlTimestamp()))).fetch();
    }
}
