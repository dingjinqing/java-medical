package com.vpu.mp.service.saas.shop;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import com.vpu.mp.db.main.tables.B2cMpAuthShop;
import com.vpu.mp.db.main.tables.B2cShop;
import com.vpu.mp.db.main.tables.B2cShopAccount;
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
	
	protected B2cShop tableShop = B2cShop.B2C_SHOP;
	protected B2cShopAccount tableShopAccount = B2cShopAccount.B2C_SHOP_ACCOUNT;
	protected B2cMpAuthShop tableMpAuthShop = B2cMpAuthShop.B2C_MP_AUTH_SHOP;

	public PageResult getShopPageList() {
		SelectWhereStep<Record> select = db().select().from(tableShop);
		return this.getPageResult(select);
	}
	
	public Result<Record> getPageList(Map<String, String> options, int totalRows, Page page) {
		B2cShop a = tableShop.as("a");
		B2cShopAccount b = tableShopAccount.as("b");
		B2cMpAuthShop c = tableMpAuthShop.as("c");
		Field<?>[] fs = { c.APP_ID, c.IS_AUTH_OK, c.NICK_NAME, c.PRINCIPAL_NAME };
		Field<?>[] fields = ArrayUtils.addAll(a.fields(), fs);
		
			
		
		return db().select(fields).from(a).join(b).on(a.SYS_ID.eq(b.SYS_ID)).join(c)
				.on(a.SHOP_ID.eq(DSL.sign(c.SHOP_ID))).orderBy(a.CREATED.desc())
				.limit((page.currentPage - 1) * page.pageRows, page.pageRows).fetch();
	}

}
