package com.vpu.mp.service.shop.store.store;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.records.StoreGroupRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.FieldsUtil;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
public class StoreService extends BaseService {
	
	/**
	 * 门店列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListOutput
	 */
	public PageResult<StorePageListVo> getPageList(StoreListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				STORE.STORE_NAME,STORE.POS_SHOP_ID,STORE_GROUP.GROUP_NAME,STORE.PROVINCE_CODE,STORE.CITY_CODE,STORE.DISTRICT_CODE,STORE.ADDRESS,STORE.MANAGER,
				STORE.MOBILE,STORE.OPENING_TIME,STORE.CLOSE_TIME,STORE.BUSINESS_STATE
				).from(STORE)
				.leftJoin(STORE_GROUP).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));
		select = this.buildOptions(select, param);
		select.orderBy(STORE.CREATE_TIME);
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StorePageListVo.class);
	}

	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, StoreListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (param.getGroupName() != null && !"".equals(param.getGroupName())) {
			select.where(STORE_GROUP.GROUP_NAME.eq(param.getGroupName()));
		}
		if (param.getGroupId() != null && param.getGroupId() > 0) {
			select.where(STORE_GROUP.GROUP_ID.eq(param.getGroupId()));
		}
		if (param.getIsAuthPos() != null) {
			if(param.getIsAuthPos()) {
				select.where(STORE.POS_SHOP_ID.gt(0));
			}else {
				select.where(STORE.POS_SHOP_ID.eq(0));
			}
		}
		if (!StringUtils.isEmpty(param.getKeywords())) {
			select.where(STORE.STORE_NAME.contains(param.getKeywords()).or(STORE.MANAGER.contains(param.getKeywords())).or(STORE.POS_SHOP_ID.like(param.getKeywords())));
		}
		return select;
	}
	
	/**
	 * 新增门店
	 * @param StorePojo
	 * @return
	 */
	public Boolean addStore(StorePojo store) {
		StoreRecord record = new StoreRecord();
		FieldsUtil.assignNotNull(store,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 更新门店
	 * @param StorePojo
	 * @return
	 */
	public Boolean updateStore(StorePojo store) {
		StoreRecord record = db().newRecord(STORE,store);
		return record.update() > 0 ? true : false;
	}
	
	/**
	 * 删除门店
	 * @param StorePojo
	 * @return
	 */
	public Boolean delStore(Integer storeId) {
		return db().update(STORE).set(STORE.DEL_FLAG,(byte)1).where(STORE.STORE_ID.eq(storeId)).execute() > 0 ? true : false;
	}
	
	/**
	 * 取单个门店信息
	 * @param Integer
	 * @return StorePojo
	 */
	public StorePojo getStore(Integer storeId) {
		return db().fetchOne(STORE,STORE.STORE_ID.eq(storeId)).into(StorePojo.class);
	}
	
	/**
	 * 检查门店编码是否可用,返回true表示可用
	 * @param Integer
	 * @return Boolean
	 */
	public Boolean checkStoreCoding(Integer posShopId) {
		Condition condition = STORE.POS_SHOP_ID.eq(posShopId).and(STORE.DEL_FLAG.eq((byte)0));
		if(null != db().fetchAny(STORE,condition)) {
			return false;
		}else {
			return true;
		}
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
