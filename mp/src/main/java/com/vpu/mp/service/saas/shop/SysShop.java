package com.vpu.mp.service.saas.shop;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.saas.shop.ShopAccount.ShopAccountListQueryParam;
/**
 * 
 * @author 新国
 *
 */
public class SysShop extends BaseComponent {
	
	public ShopAccount accout;
	public ShopRenew renew;
	
	public PageResult getPageList(ShopAccountListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP);
		select = this.buildOptions(select, param);
		select.orderBy(SHOP.CREATED.desc());
		return this.getPageResult(select, param.page);
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ShopAccountListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			select.where(
					SHOP.USER_NAME.like(param.keywords).or(SHOP.ACCOUNT_NAME.like(param.keywords)));
		}
		if (param.state != null && param.state != 0) {
			select.where(SHOP.STATE.eq(param.state));
		}

		if (!StringUtils.isEmpty(param.company)) {
			select.where(SHOP.COMPANY.like(param.company));
		}

		return select;
	}

}
