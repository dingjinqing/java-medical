package com.vpu.mp.service.shop.store.store;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.FieldsUtil;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.shop.store.group.StoreGroupService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.store.verify.StoreVerifierService;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
public class StoreService extends BaseService {
	
	/**
	 * 核销员
	 */
	public StoreVerifierService storeVerifier;
	
	/**
	 * 门店商品
	 */
	public StoreGoodsService storeGoods;
	
	/**
	 * 门店服务
	 */
	public StoreServiceService storeService;
	
	/**
	 * 门店分组
	 */
	public StoreGroupService storeGroup;
	
	/**
	 * 技师管理
	 */
	public ServiceTechnicianService serviceTechnician;
	
	/**
	 * 服务预约（serviceOrder）
	 */
	public ServiceOrderService serviceOrder;
	
	/**
	 * 门店列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListVo
	 */
	public PageResult<StorePageListVo> getPageList(StoreListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				STORE.STORE_NAME,STORE.POS_SHOP_ID,STORE_GROUP.GROUP_NAME,STORE.PROVINCE_CODE,STORE.CITY_CODE,STORE.DISTRICT_CODE,STORE.ADDRESS,STORE.MANAGER,
				STORE.MOBILE,STORE.OPENING_TIME,STORE.CLOSE_TIME,STORE.BUSINESS_STATE
				).from(STORE)
				.leftJoin(STORE_GROUP).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));
				
		select = this.buildOptions(select, param);
		select.where(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(STORE.CREATE_TIME);
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
		return db().update(STORE).set(STORE.DEL_FLAG,DelFlag.DISABLE.getCode()).where(STORE.STORE_ID.eq(storeId)).execute() > 0 ? true : false;
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
		Condition condition = STORE.POS_SHOP_ID.eq(posShopId).and(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
		if(null != db().fetchAny(STORE,condition)) {
			return false;
		}else {
			return true;
		}
	}

}
