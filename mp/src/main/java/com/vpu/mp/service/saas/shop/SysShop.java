package com.vpu.mp.service.saas.shop;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
/**
 * 
 * @author 新国
 *
 */
public class SysShop extends BaseComponent {
	
	public ShopAccount accout;
	public ShopRenew renew;
	
	public PageResult getShopPageList() {
		SelectWhereStep<Record> select = db().select().from(SHOP);
		return this.getPageResult(select);
	}
	
	public Result<Record> getPageList(int totalRows, Page page) {
		Field<?>[] fs = { MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.IS_AUTH_OK, MP_AUTH_SHOP.NICK_NAME, MP_AUTH_SHOP.PRINCIPAL_NAME };
		Field<?>[] fields = ArrayUtils.addAll(SHOP.fields(), fs);
		
		return db().select(fields).from(SHOP).join(SHOP_ACCOUNT).on(SHOP.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID)).join(MP_AUTH_SHOP)
				.on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID,Integer.class))).orderBy(SHOP.CREATED.desc())
				.limit((page.currentPage - 1) * page.pageRows, page.pageRows).fetch();
	}

}
