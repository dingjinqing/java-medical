package com.vpu.mp.service.saas.shop;

import com.vpu.mp.db.main.Tables;
import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.main.tables.AppAuth.APP_AUTH;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年9月23日 下午6:59:58
 */
@Service
public class ShopAppService  extends MainBaseService {
	public AppAuthRecord getShopAppByErp(Integer shopId) {
		return db().selectFrom(APP_AUTH).where(APP_AUTH.SHOP_ID.eq(shopId)).and(APP_AUTH.ACTION.eq((byte)1)).fetchAny();
	}
    /** 门店对接action 2：pos */
    public static final Byte ACTION_POS = 2;
    /** status默认值0 */
    public static final Byte STATUS_DEFAULT_VALUE = 0;
    /**
     * 判断当前门店是否对接pos
     * @param shopId 店铺id
     * @return status 0：禁用 1：启用
     */
    public Byte getShopAppByPos(Integer shopId){
        Byte status = db().select(Tables.APP_AUTH.STATUS)
            .from(Tables.APP_AUTH)
            .where(Tables.APP_AUTH.SHOP_ID.eq(shopId))
            .and(Tables.APP_AUTH.ACTION.eq(ACTION_POS))
            .fetchOptionalInto(Byte.class)
            .orElse(STATUS_DEFAULT_VALUE);
        return status;
    }
}
