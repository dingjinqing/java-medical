package com.vpu.mp.service.shop.store.store;

import com.vpu.mp.db.shop.tables.records.StoreGroupRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.StoreListParam;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.store.comment.ServiceCommentService;
import com.vpu.mp.service.shop.store.group.StoreGroupService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.store.verify.StoreVerifierService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_TWO;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
@Service
public class StoreService extends ShopBaseService {

	/**
	 * 核销员
	 */
	@Autowired public StoreVerifierService storeVerifier;

    /**
	 * 门店商品
	 */
	@Autowired public StoreGoodsService storeGoods;

    /**
	 * 门店服务
	 */
	@Autowired public StoreServiceService storeService;

    /**
	 * 门店分组
	 */
	@Autowired public StoreGroupService storeGroup;

	/**
	 * 技师管理
	 */
	@Autowired public ServiceTechnicianService serviceTechnician;

	/**
	 * 服务预约（serviceOrder）
	 */
	@Autowired public ServiceOrderService serviceOrder;

    /**
	 * 服务评价管理
	 */
	@Autowired public ServiceCommentService serviceComment;

    /**
     * The Goods spec product service.商品规格
     */
    @Autowired
    public GoodsSpecProductService goodsSpecProductService;

    /**
	 * 门店列表分页查询
	 * @param param
	 * @return StorePageListVo
	 */
	public PageResult<StorePageListVo> getPageList(StoreListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
            STORE.STORE_ID,STORE.STORE_NAME,STORE.POS_SHOP_ID,STORE_GROUP.GROUP_NAME,STORE.PROVINCE_CODE,STORE.CITY_CODE,STORE.DISTRICT_CODE,STORE.ADDRESS,STORE.MANAGER,
            STORE.MOBILE, STORE.OPENING_TIME, STORE.CLOSE_TIME, STORE.BUSINESS_STATE, STORE.AUTO_PICK
				).from(STORE)
				.leftJoin(STORE_GROUP).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));

        select = this.buildOptions(select, param);
		select.where(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(STORE.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StorePageListVo.class);
	}

    /**
     * 门店列表查询-小程序端
     * @param param
     * @return StorePageListVo
     */
    public List<StorePageListVo> getList(StoreListParam param) {
        if (BYTE_ZERO.equals(param.getType())) {
            if (param.getGoodsId() != null) {
                // 根据商品id获取商品规格id列表
                List<GoodsSpec> list = goodsSpecProductService.selectSpecByGoodsId(param.getGoodsId());
                Set<Integer> prdIds = list.stream().map(GoodsSpec::getSpecId).collect(Collectors.toSet());
                try {
                    StoreListParam.Location location = MAPPER.readValue(param.getLocation(), StoreListParam.Location.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                // 缺少商品参数 商品规格
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
        }
        /*SelectWhereStep<? extends Record> select = db().selectFrom(STORE);
        select = this.buildOptions(select, param);
        select.where(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(STORE.CREATE_TIME.desc());
        return select.fetchInto(StorePageListVo.class);*/
        return null;
    }

    /**
     * The entry point of application.获得可购买的门店列表
     */
    public void getCanBuyStoreList(Set<Integer> prdIds, Byte deliverType, StoreListParam.Location location, Byte isFromStore) {
        SelectConditionStep<Record1<Integer>> conditionStep = db().select(STORE.STORE_ID).from(STORE_GOODS)
            .leftJoin(STORE).on(STORE_GOODS.STORE_ID.eq(STORE.STORE_ID))
            .where(STORE_GOODS.PRD_ID.in(prdIds))
            .and(STORE_GOODS.IS_ON_SALE.eq(BYTE_ONE))
            .and(STORE.BUSINESS_STATE.eq(BYTE_ONE))
            .and(STORE.DEL_FLAG.eq(BYTE_ZERO));
        if (deliverType.equals(BYTE_ONE)) {
            conditionStep.and(STORE.AUTO_PICK.eq(SHORT_ONE));
        }
        if (deliverType.equals(CONDITION_TWO)) {
            // TODO 同城配送 新增字段
//            conditionStep.and(STORE.city.eq(SHORT_ONE));
        }
        List<Integer> storeIds = conditionStep.groupBy(STORE.STORE_ID)
            .having(DSL.count(STORE.STORE_ID).eq(prdIds.size()))
            .fetchInto(Integer.class);
        List<StorePojo> storeLists = db().selectFrom(STORE).where(STORE.STORE_ID.in(storeIds)).fetchInto(StorePojo.class);
        if (deliverType.equals(CONDITION_TWO)) {
            // 支持同城配送门店列表

        }
    }

    /**
     * The entry point of application.获得同城配送可用的门店列表
     */
    public void cityServiceCanUseStoreList(List<StorePojo> storeLists, StoreListParam.Location location, Byte isFromStore) {

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
	 * @param store
	 * @return
	 */
	public Boolean addStore(StorePojo store) {
		StoreRecord record = new StoreRecord();
		this.assign(store,record);
		return db().executeInsert(record) > 0 ? true : false;
	}

    /**
	 * 更新门店
	 * @param store
	 * @return
	 */
	public Boolean updateStore(StorePojo store) {
		StoreRecord record = new StoreRecord();
        this.assign(store,record);
		return db().executeUpdate(record) > 0 ? true : false;
	}

    /**
     * 批量更新门店信息
     */
    public void batchUpdateStore(List<StorePojo> storeList) {
        List<StoreRecord> resultList = new ArrayList<StoreRecord>(64) {
			private static final long serialVersionUID = 8882723512039998814L;
			{
                storeList.forEach(store -> {
                    StoreRecord record = new StoreRecord();
                    assign(store, record);
                    add(record);
                });
            }
        };
        db().batchUpdate(resultList).execute();
    }

    /**
	 * 删除门店
	 * @param storeId
	 * @return
	 */
	public Boolean delStore(Integer storeId) {
		return db().update(STORE).set(STORE.DEL_FLAG,DelFlag.DISABLE.getCode()).where(STORE.STORE_ID.eq(storeId)).execute() > 0 ? true : false;
	}

    /**
	 * 取单个门店信息
	 * @param storeId
	 * @return StorePojo
	 */
	public StorePojo getStore(Integer storeId) {
		return db().fetchOne(STORE,STORE.STORE_ID.eq(storeId)).into(StorePojo.class);
	}

    /**
	 * 检查门店编码是否可用,返回true表示可用
	 * @param posShopId
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
		if(null != param.getCurrentPage()) {
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
	/**
	 * 获取所有门店id和名称
	 * @return
	 */
	public List<StoreBasicVo> getAllStore() {
		logger().info("获取所有门店id和名称");
		 return db().select(STORE.STORE_ID,STORE.STORE_NAME)
			.from(STORE)
			.fetch()
			.into(StoreBasicVo.class);
	}

    /**
	 * 获取门店名称
	 * @param sourceId
	 * @return
	 */
	public Record1<String> getStoreName(Integer sourceId) {
		return db().select(STORE.STORE_NAME).from(STORE)
				.where(STORE.STORE_ID.eq(sourceId)).fetchAny();
	}
}
