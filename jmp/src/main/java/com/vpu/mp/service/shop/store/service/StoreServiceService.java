package com.vpu.mp.service.shop.store.service;

import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static com.vpu.mp.db.shop.tables.StoreServiceCategory.STORE_SERVICE_CATEGORY;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.records.StoreServiceCategoryRecord;
import com.vpu.mp.db.shop.tables.records.StoreServiceRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 * 
 * 门店服务
 */
public class StoreServiceService extends BaseService{

	/**
	 * 门店服务分类列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListVo
	 */
	public PageResult<StoreServiceCategoryListQueryVo> getCatePageList(StoreServiceCategoryListQueryParam param) {
		SelectWhereStep<? extends Record> select = 
		db().select(STORE_SERVICE_CATEGORY.CAT_NAME,STORE_SERVICE_CATEGORY.CREATE_TIME).from(STORE_SERVICE_CATEGORY);
		select = this.buildCateOptions(select, param);
		select.orderBy(STORE_SERVICE_CATEGORY.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreServiceCategoryListQueryVo.class);
	}
	

	/**
	 * 门店服务分类的条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildCateOptions(SelectWhereStep<? extends  Record> select, StoreServiceCategoryListQueryParam param) {
		if (param == null) {
			return select;
		}
		
		if (!StringUtils.isEmpty(param.getCatName())) {
			select.where(STORE_SERVICE_CATEGORY.CAT_NAME.contains(param.getCatName()));
		}
		return select;
	}
	
	/**
	 * 门店服务分类列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListVo
	 */
	public PageResult<StoreServiceListQueryVo> getServicePageList(StoreServiceListQueryParam param) {
		SelectWhereStep<? extends Record> select = 
		db().select(STORE_SERVICE.ID,STORE_SERVICE.SERVICE_NAME,STORE_SERVICE.SERVICE_IMG,STORE_SERVICE.SERVICE_PRICE,STORE_SERVICE_CATEGORY.CAT_NAME,STORE_SERVICE.SALE_NUM,STORE_SERVICE.SERVICE_TYPE,STORE_SERVICE.SERVICE_SHELF,STORE_SERVICE.CREATE_TIME).
		from(STORE_SERVICE).
		leftJoin(STORE_SERVICE_CATEGORY).on(STORE_SERVICE_CATEGORY.CAT_ID.eq(STORE_SERVICE.CAT_ID));
		select = this.buildServiceOptions(select, param);
		select.orderBy(STORE_SERVICE.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreServiceListQueryVo.class);
	}
	

	/**
	 * 门店服务的条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildServiceOptions(SelectWhereStep<? extends  Record> select, StoreServiceListQueryParam param) {
		if (param == null) {
			return select;
		}
		
		if(param.getCatId() != null && param.getCatId() > 0) {
			select.where(STORE_SERVICE.CAT_ID.eq(param.getCatId()));
		}
		if (!StringUtils.isEmpty(param.getServiceName())) {
			select.where(STORE_SERVICE.SERVICE_NAME.contains(param.getServiceName()));
		}
		return select;
	}
	
	/**
	 * 新增门店服务分类
	 * @param StoreServiceCategoryParam
	 * @return
	 */
	public Boolean addStoreServiceCategory(StoreServiceCategoryParam storeServiceCategory) {
		StoreServiceCategoryRecord record = new StoreServiceCategoryRecord();
		this.assign(storeServiceCategory,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 更新门店服务分类
	 * @param StoreServiceCategoryParam
	 * @return
	 */
	public Boolean updateStoreServiceCategory(StoreServiceCategoryParam storeServiceCategory) {
		StoreServiceCategoryRecord record = new StoreServiceCategoryRecord();
		this.assign(storeServiceCategory, record);
		return db().executeUpdate(record) > 0 ? true : false;
	}
	
	/**
	 * 删除门店服务分类
	 * @param StorePojo
	 * @return
	 */
	public Boolean delStoreServiceCategory(Integer catId) {
		this.transaction(()->{
			db().deleteFrom(STORE_SERVICE_CATEGORY).where(STORE_SERVICE_CATEGORY.CAT_ID.eq(catId)).execute();
			db().update(STORE_SERVICE).set(STORE_SERVICE.CAT_ID,0).where(STORE_SERVICE.CAT_ID.eq(catId)).execute();
		});
		return true;
	}
	
	/**
	 * 新增门店服务
	 * @param StoreServiceParam
	 * @return
	 */
	public Boolean addStoreService(StoreServiceParam storeService) {
		StoreServiceRecord record = new StoreServiceRecord();
		this.assign(storeService,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 更新门店服务
	 * @param StoreServiceParam
	 * @return
	 */
	public Boolean updateStoreService(StoreServiceParam storeService) {
		StoreServiceRecord record = new StoreServiceRecord();
		this.assign(storeService, record);
		return db().executeUpdate(record) > 0 ? true : false;
	}
	
	/**
	 * 删除门店服务
	 * @param Integer
	 * @return
	 */
	public Boolean delStoreService(Integer id) {
		return db().update(STORE_SERVICE).set(STORE_SERVICE.DEL_FLAG,DelFlag.DISABLE.getCode()).where(STORE_SERVICE.ID.eq(id)).execute() > 0 ? true : false;
	}

}
