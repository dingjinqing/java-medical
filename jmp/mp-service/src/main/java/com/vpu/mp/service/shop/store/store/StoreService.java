package com.vpu.mp.service.shop.store.store;

import com.vpu.mp.db.shop.tables.records.StoreGroupRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.store.comment.ServiceCommentService;
import com.vpu.mp.service.shop.store.group.StoreGroupService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.store.verify.StoreVerifierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.db.shop.tables.StoreGroup.STORE_GROUP;


/**
 * @author 王兵兵
 * <p>
 * 2019年7月4日
 */
@Slf4j
@Service

public class StoreService extends ShopBaseService {

    /**
     * 营业
     */
    private static byte BUSINESS_STATE_ON = 1;

    /**
     * 关店
     */
    private static byte BUSINESS_STATE_OFF = 1;
    /**
     * 核销员
     */
    @Autowired
    public StoreVerifierService storeVerifier;

    /**
     * 门店商品
     */
    @Autowired
    public StoreGoodsService storeGoods;

    /**
     * 门店服务
     */
    @Autowired
    public StoreServiceService storeService;

    /**
     * 门店分组
     */
    @Autowired
    public StoreGroupService storeGroup;

    /**
     * 技师管理
     */
    @Autowired
    public ServiceTechnicianService serviceTechnician;

    /**
     * 服务预约（serviceOrder）
     */
    @Autowired
    public ServiceOrderService serviceOrder;

    /**
     * 服务评价管理
     */
    @Autowired
    public ServiceCommentService serviceComment;
    /**
     * The Wx service.小程序端接口调用
     */
    @Autowired
    public StoreWxService wxService;

    /**
     * The Reservation.小程序端门店服务预约
     */
    @Autowired
    public StoreReservation reservation;

    /**
     * 门店列表分页查询
     *
     * @param param
     * @return StorePageListVo
     */
    public PageResult<StorePageListVo> getPageList(StoreListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(
            STORE.STORE_ID, STORE.STORE_NAME, STORE.POS_SHOP_ID, STORE_GROUP.GROUP_NAME, STORE.PROVINCE_CODE, STORE.CITY_CODE, STORE.DISTRICT_CODE, STORE.ADDRESS, STORE.MANAGER,
            STORE.MOBILE, STORE.OPENING_TIME, STORE.CLOSE_TIME, STORE.BUSINESS_STATE, STORE.AUTO_PICK, STORE.BUSINESS_TYPE
        ).from(STORE)
            .leftJoin(STORE_GROUP).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));

        select = this.buildOptions(select, param);
        select.where(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(STORE.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), StorePageListVo.class);
    }

    public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select, StoreListQueryParam param) {
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
            if (param.getIsAuthPos()) {
                select.where(STORE.POS_SHOP_ID.gt(0));
            } else {
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
     *
     * @param store
     * @return
     */
    public Boolean addStore(StorePojo store) {
        StoreRecord record = new StoreRecord();
        this.assign(store, record);
        return db().executeInsert(record) > 0 ? true : false;
    }

    /**
     * 更新门店
     *
     * @param store
     * @return
     */
    public Boolean updateStore(StorePojo store) {
        StoreRecord record = new StoreRecord();
        this.assign(store, record);
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
     *
     * @param storeId
     * @return
     */
    public Boolean delStore(Integer storeId) {
        return db().update(STORE).set(STORE.DEL_FLAG, DelFlag.DISABLE.getCode()).where(STORE.STORE_ID.eq(storeId)).execute() > 0 ? true : false;
    }

    /**
     * 取单个门店信息
     *
     * @param storeId
     * @return StorePojo
     */
    public StorePojo getStore(Integer storeId) {
        return db().fetchOne(STORE, STORE.STORE_ID.eq(storeId)).into(StorePojo.class);
    }

    /**
     * 检查门店编码是否可用,返回true表示可用
     *
     * @param posShopId
     * @return Boolean
     */
    public Boolean checkStoreCoding(Integer posShopId) {
        Condition condition = STORE.POS_SHOP_ID.eq(posShopId).and(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (null != db().fetchAny(STORE, condition)) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 门店分组列表-查询
     *
     * @param param
     * @return
     */
    public PageResult<StoreGroup> getStoreGroupPageList(StoreGroupQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(STORE_GROUP.GROUP_ID, STORE_GROUP.GROUP_NAME,
            STORE_GROUP.CREATE_TIME, DSL.count(STORE.GROUP).as("numbers"))
            .from(STORE_GROUP)
            .leftJoin(STORE).on(STORE.GROUP.eq(STORE_GROUP.GROUP_ID));
        buildParams(select, param);
        select.groupBy(STORE_GROUP.GROUP_ID).orderBy(STORE_GROUP.CREATE_TIME.asc());
        if (null != param.getCurrentPage()) {
            return getPageResult(select, param.getCurrentPage(), param.getPageRows(), StoreGroup.class);
        } else {
            return getPageResult(select, param.getPageRows(), StoreGroup.class);
        }
    }

    public void buildParams(SelectWhereStep<? extends Record> select, StoreGroupQueryParam param) {
        if (param != null) {
            if (param.getGroupName() != null && !"".equals(param.getGroupName())) {
                if (param.isNeedAccurateQuery()) {
                    select.where(STORE_GROUP.GROUP_NAME.eq(param.getGroupName()));
                } else {
                    select.where(STORE_GROUP.GROUP_NAME.like(this.likeValue(param.getGroupName())));
                }
            }
        }
    }

    /**
     * 门店分组-(检查组名是否可用)
     *
     * @param param
     * @return true可用，fasle不可用
     */
    public boolean isStoreGroupExist(StoreGroupQueryParam param) {
        param.setNeedAccurateQuery(Boolean.TRUE);
        SelectWhereStep<? extends Record> select = db().select(STORE_GROUP.GROUP_NAME)
            .from(STORE_GROUP);
        buildParams(select, param);
        return db().fetchCount(select) > 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 门店分组-新增
     *
     * @param param
     * @return
     */
    public int insertStoreGroup(StoreGroupQueryParam param) {
        StoreGroupRecord record = db().newRecord(STORE_GROUP, param);
        return record.insert();
    }

    /**
     * 门店分组-修改
     *
     * @param param
     * @return
     */
    public int updateStoreGroup(StoreGroupQueryParam param) {
        StoreGroupRecord record = db().newRecord(STORE_GROUP, param);
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return record.update();
    }

    /**
     * 门店分组-删除
     *
     * @param param
     * @return
     */
    public void deleteStoreGroup(StoreGroupQueryParam param) {
        db().transaction(configuration -> {
            DSLContext dslContext = DSL.using(configuration);
            StoreGroupRecord record = dslContext.newRecord(STORE_GROUP, param);
            List<Integer> result = dslContext.select(STORE.STORE_ID)
                .from(STORE)
                .where(STORE.GROUP.eq(param.getGroupId()))
                .fetch(STORE.STORE_ID);
            if (result.size() > 0) {
                dslContext.update(STORE)
                    .set(STORE.GROUP, (Integer) null)
                    .where(STORE.STORE_ID.in(result))
                    .execute();
            }
            dslContext.delete(STORE_GROUP)
                .where(STORE_GROUP.GROUP_ID.eq(param.getGroupId()))
                .execute();
        });
    }

    public List<StoreBasicVo> getStoreListByStoreIds(List<Integer> storeId) {
		logger().info("正在根据门店id数组查询门店基本信息");
		return db().select(STORE.STORE_ID,STORE.STORE_NAME)
				.from(STORE)
				.where(STORE.STORE_ID.in(storeId))
				.and(STORE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetchInto(StoreBasicVo.class);
	}

    /**
     * 获取所有门店id和名称
     */
    public List<StoreBasicVo> getAllStore() {
        logger().info("获取所有门店id和名称");
        return db().select(STORE.STORE_ID, STORE.STORE_NAME)
            .from(STORE)
            .fetchInto(StoreBasicVo.class);
    }

    /**
     * 获取门店名称
     *
     * @param sourceId
     * @return
     */
    public String getStoreName(Integer sourceId) {
        Record1<String> record = db().select(STORE.STORE_NAME).from(STORE)
            .where(STORE.STORE_ID.eq(sourceId)).fetchAny();
        if (record != null) {
            return record.into(String.class);
        }
        return "";
    }

    @Autowired
    QrCodeService qrCodeService;

    /**
     * Share store service share qr code vo.通用分享方法
     *
     * @param qrCodeTypeEnum the qr code type enum
     * @param pathParam      the path param
     * @return the share qr code vo
     */
    public ShareQrCodeVo share(QrCodeTypeEnum qrCodeTypeEnum, String pathParam) {
        String imageUrl = qrCodeService.getMpQrCode(qrCodeTypeEnum, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;
    }

    /**
     * 王帅
     * 过滤门店
     * @param expressList    配送方式list
     * @param productIds     规格ids
     * @param address        地址
     * @param isFormStore    ??
     * @return
     */
    public List<StorePojo>[] filterExpressList(Byte[] expressList, List<Integer> productIds, UserAddressVo address, byte isFormStore) {
        List<StorePojo>[] result = new List[3];
        //自提
        if (expressList[OrderConstant.DELIVER_TYPE_SELF] == OrderConstant.YES) {
            result[OrderConstant.DELIVER_TYPE_SELF] = getCanBuyStoreList(productIds, OrderConstant.DELIVER_TYPE_SELF, address, isFormStore);
        }
        //同城配送
        if (expressList[OrderConstant.CITY_EXPRESS_SERVICE] == OrderConstant.YES) {
            result[OrderConstant.CITY_EXPRESS_SERVICE] = getCanBuyStoreList(productIds, OrderConstant.CITY_EXPRESS_SERVICE, address, isFormStore);
        }
        return result;
    }

    /**王帅
     * @param productIds  规格ids
     * @param express     配送方式
     * @param address     地址
     * @param isFormStore ??
     * @return
     */
    public List<StorePojo> getCanBuyStoreList(List<Integer> productIds, byte express, UserAddressVo address, byte isFormStore) {
        //条件
        Condition condition = STORE_GOODS.PRD_ID.in(productIds).and(STORE_GOODS.IS_ON_SALE.eq(StoreGoodsService.ON_SALE)).and(STORE.BUSINESS_STATE.eq(BUSINESS_STATE_ON)).and(STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        //自提
        condition = express == OrderConstant.DELIVER_TYPE_SELF ? condition.and(STORE.AUTO_PICK.eq((short) OrderConstant.YES)) : condition;
        //TODO 同城配送
        condition = express == OrderConstant.CITY_EXPRESS_SERVICE ? condition.and(STORE.AUTO_PICK.eq((short) OrderConstant.YES)) : condition;
        //获取门店id
        List<Integer> storeIds = db().select(STORE.STORE_ID, DSL.count(STORE.STORE_ID)).from(STORE).innerJoin(STORE_GOODS).on(STORE.STORE_ID.eq(STORE_GOODS.STORE_ID)).
            where(condition).
            groupBy(STORE.STORE_ID).
            having(DSL.count(STORE.STORE_ID).eq(productIds.size())).
            fetch(STORE.STORE_ID);
        if (CollectionUtils.isEmpty(storeIds)) {
            return null;
        }
        List<StorePojo> storeList = db().select().from(STORE).where(STORE.STORE_ID.in(storeIds)).fetchInto(StorePojo.class);
        if(express == OrderConstant.CITY_EXPRESS_SERVICE){
            //TODO 同城配送特殊处理
            storeList = storeList;
        }
        return storeList;
    }
}
