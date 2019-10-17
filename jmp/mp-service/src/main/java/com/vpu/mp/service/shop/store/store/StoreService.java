package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.shop.tables.records.StoreGroupRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.Location;
import com.vpu.mp.service.pojo.wxapp.store.StoreListParam;
import com.vpu.mp.service.shop.config.StoreConfigService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.store.comment.ServiceCommentService;
import com.vpu.mp.service.shop.store.group.StoreGroupService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.store.verify.StoreVerifierService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
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
@Slf4j
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
     * The Store config service.门店配置
     */
    @Autowired
    public StoreConfigService storeConfigService;

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
     * @param param 查询入参
     * @return StorePageListVo
     */
    public List<StorePojo> getList(StoreListParam param) {
        List<StorePojo> storeList;
        Location location;
        try {
            location = MAPPER.readValue(param.getLocation(), Location.class);
            log.debug("地理位置信息为:[param:{},location:{}]", param.getLocation(), location.toString());
        } catch (IOException e) {
            log.error("反序列化地理位置信息[{}]失败", param.getLocation());
            throw new BusinessException(JsonResultCode.CODE_JACKSON_DESERIALIZATION_FAILED);
        }
        if (BYTE_ZERO.equals(param.getType())) {
            SelectConditionStep<Record11<Integer, String, String, String, String, String, String, String, String, Byte, Byte>> conditionStep = db()
                .select(STORE.STORE_ID, STORE.STORE_NAME, STORE.STORE_IMGS, STORE.PROVINCE_CODE, STORE.CITY_CODE, STORE.DISTRICT_CODE, STORE.ADDRESS, STORE.LATITUDE, STORE.LONGITUDE, STORE.BUSINESS_STATE, STORE.DEL_FLAG)
                .from(STORE).where(STORE.DEL_FLAG.eq(BYTE_ZERO));
            if (!param.getScanStores().equals(BYTE_ZERO)) {
                conditionStep.and(STORE.POS_SHOP_ID.greaterThan(INTEGER_ZERO));
            }
            storeList = conditionStep.fetchInto(StorePojo.class);
        }
        /*else if (cardId){
            // TODO 会员卡详情也跳转过来的
        }*/
        else {
            if (param.getGoodsId() != null) {
                // 根据商品id获取商品规格id列表
                List<GoodsSpecProduct> list = goodsSpecProductService.selectByGoodsId(param.getGoodsId());
                Set<Integer> prdIds = list.stream().map(GoodsSpecProduct::getPrdId).collect(Collectors.toSet());
                log.debug("商品[{}]对应的sku为:{}", param.getGoodsId(), prdIds.toString());
                // 获得该商品可购买的门店列表
                storeList = getCanBuyStoreList(prdIds, param.getType(), location, BYTE_ONE);
                log.debug("可购买的门店列表:{}", storeList.stream().map(StorePojo::getStoreId).collect(Collectors.toSet()));
            } else {
                // 缺少商品参数 商品规格
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
        }
        // 查询开启“扫码购”功能的门店ID列表配置，逗号分隔
        List<String> storeScanIds = Arrays.asList(storeConfigService.getStoreScanIds().split(","));
        log.debug("开启“扫码购”功能的门店ID列表配置项:{}", storeScanIds.toString());
        // 筛掉不支持扫码购的门店或者添加是否支持扫码购的标示位
        if (param.getScanStores().equals(BYTE_ONE)) {
            storeList = storeList.stream().filter(s -> storeScanIds.contains(s.getStoreId().toString())).collect(Collectors.toList());
        } else {
            storeList.forEach(s -> {
                s.setScanBuy(storeScanIds.contains(s.getStoreId().toString()) ? BYTE_ONE : BYTE_ZERO);
            });
        }
        // 设置图片和距离
        double lat1 = location.getLatitude();
        double lon1 = location.getLatitude();
        storeList.forEach(s -> {
            if (s.getStoreImgs() != null) {
                // 设置门店主图中的第一张为门店列表展示图
                JsonNode imgList;
                try {
                    imgList = MAPPER.readTree(s.getStoreImgs());
                } catch (IOException e) {
                    log.error("反序列化门店图片信息[{}]失败", s.getStoreImgs());
                    throw new BusinessException(JsonResultCode.CODE_JACKSON_DESERIALIZATION_FAILED);
                }
                Iterator<JsonNode> iterator = imgList.elements();
                if (iterator.hasNext()) {
                    s.setStoreImgs(iterator.next().asText());
                } else {
                    s.setStoreImgs(null);
                }
            }
            double distance = getDistance(lat1, lon1, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
            log.debug("门店 {} 距离用户位置 {} km", s.getStoreName(), distance);
            s.setDistance(distance);
        });
        // 结果按照距离 从小到大排序
        Collections.sort(storeList);
        return storeList;
    }

    /**
     * Gets distance.计算门店距离,单位km(两个经纬度之间的距离)目前使用的是 Haversine method 计算方法
     *
     * @param lat1 the lat 1
     * @param lon1 the lon 1
     * @param lat2 the lat 2
     * @param lon2 the lon 2
     * @return the distance
     */
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a / 2), 2) +
            Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))));
        double EARTH_RADIUS = 6371.393;
        s = s * EARTH_RADIUS;
//        s = Math.round(s * 1000);
        return new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * The entry point of application.获得可购买的门店列表
     * @param prdIds 商品规格id列表
     * @param deliverType 配送类型,1:自提,2:同城配送
     * @param location 用户位置
     * @param isFromStore todo 未知
     */
    public List<StorePojo> getCanBuyStoreList(Set<Integer> prdIds, Byte deliverType, Location location, Byte isFromStore) {
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
            // TODO 查询支持同城配送的门店列表
            cityServiceCanUseStoreList(storeLists, location, isFromStore);
        }
        return storeLists;
    }

    /**
     * The entry point of application.获得同城配送可用的门店列表
     */
    public void cityServiceCanUseStoreList(List<StorePojo> storeLists, Location location, Byte isFromStore) {

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
