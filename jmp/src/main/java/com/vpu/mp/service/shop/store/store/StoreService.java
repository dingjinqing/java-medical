package com.vpu.mp.service.shop.store.store;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleOutPut;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
public class StoreService extends BaseService {
	
	public PageResult<ArticleOutPut> getPageList(StoreListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				).from(STORE)
				.leftJoin(STORE_GROUP).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));
		select = this.buildOptions(select, param);
		select.orderBy(STORE.CREATE_TIME);
		if(null != param.getPage().getPageRows()) {
			return getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows(),ArticleOutPut.class);
		}else {
			return getPageResult(select,param.getPage().getPageRows(),ArticleOutPut.class);
		}
	}

	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, StoreListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (param.getGroupName() != null && !"".equals(param.getGroupName())) {
			select.where(STORE_GROUP.GROUP_NAME.eq(param.getGroupName()));
		}
		if (param.getIsAuthPos() != null) {
			if(param.getIsAuthPos()) {
				select.where(STORE.POS_SHOP_ID.gt(0));
			}else {
				select.where(STORE.POS_SHOP_ID.eq(0));
			}
		}
		if (!StringUtils.isEmpty(param.getKeywords())) {
			select.where(STORE.STORE_NAME.like(param.getKeywords()).or(STORE.MANAGER.like(param.getKeywords())).or(STORE.POS_SHOP_ID.like(param.getKeywords())));
		}
		return select;
	}

}
