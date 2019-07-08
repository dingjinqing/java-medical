package com.vpu.mp.service.shop.store.store;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;

import com.vpu.mp.db.shop.tables.records.StoreGroupRecord;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleOutPut;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

	/**
	 * 门店分组列表-查询
	 * @param param
	 * @return
	 */
	public PageResult<StoreGroup> getStoreGroupPageList(StoreGroupQueryParam param){
		SelectWhereStep<? extends Record> select = db().select(STORE_GROUP.GROUP_ID,STORE_GROUP.GROUP_NAME,
				STORE_GROUP.CREATE_TIME, DSL.count(STORE.GROUP).as("numbers"))
				.from(STORE_GROUP)
				.leftJoin(STORE).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));
		buildParams(select,param);
		select.groupBy(STORE_GROUP.GROUP_ID).orderBy(STORE_GROUP.CREATE_TIME.asc());
		if(null != param.getPageRows()) {
			return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreGroup.class);
		}else {
			return getPageResult(select,param.getPageRows(),StoreGroup.class);
		}
	}
	public void buildParams(SelectWhereStep<? extends  Record> select, StoreGroupQueryParam param) {
		if (param != null) {
			if (param.getGroupName() != null && !"".equals(param.getGroupName())) {
				if ( param.isNeedAccurateQuery() ){
					select.where(STORE_GROUP.GROUP_NAME.eq(param.getGroupName()));
				}else{
					select.where(STORE_GROUP.GROUP_NAME.like(this.likeValue(param.getGroupName())));
				}
			}
		}
	}

    /**
     * 门店分组-(检查组名是否可用)
     * @param param
     * @return true可用，fasle不可用
     */
    public boolean isStoreGroupExist(StoreGroupQueryParam param) {
    	param.setNeedAccurateQuery(Boolean.TRUE);
		SelectWhereStep<? extends Record> select = db().select(STORE_GROUP.GROUP_NAME)
                .from(STORE_GROUP);
		buildParams(select,param);
		return db().fetchCount(select) > 0?Boolean.FALSE:Boolean.TRUE;
    }

	/**
	 * 门店分组-新增
	 * @param param
	 * @return
	 */
	public int insertStoreGroup(StoreGroupQueryParam param) {
		StoreGroupRecord record = db().newRecord(STORE_GROUP,param);
		return  record.insert();
	}

	/**
	 * 门店分组-修改
	 * @param param
	 * @return
	 */
	public int updateStoreGroup(StoreGroupQueryParam param) {
		StoreGroupRecord record = db().newRecord(STORE_GROUP,param);
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return  record.update();
	}

	/**
	 * 门店分组-删除
	 * @param param
	 * @return
	 */
	public void deleteStoreGroup(StoreGroupQueryParam param) {
		db().transaction(configuration->{
			DSLContext dslContext = DSL.using(configuration);
			StoreGroupRecord record = dslContext.newRecord(STORE_GROUP,param);
			List<Integer> result = dslContext.select(STORE.STORE_ID)
					.from(STORE)
					.where(STORE.GROUP.eq(param.getGroupId()))
					.fetch(STORE.STORE_ID);
			if ( result.size() > 0){
				dslContext.update(STORE)
						.set(STORE.GROUP,(Integer)null)
						.where(STORE.STORE_ID.in(result))
						.execute();
			}
			dslContext.delete(STORE_GROUP)
					.where(STORE_GROUP.GROUP_ID.eq(param.getGroupId()))
					.execute();
		});
	}
}
