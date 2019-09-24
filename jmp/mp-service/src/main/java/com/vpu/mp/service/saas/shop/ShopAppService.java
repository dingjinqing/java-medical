package com.vpu.mp.service.saas.shop;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;

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

}
