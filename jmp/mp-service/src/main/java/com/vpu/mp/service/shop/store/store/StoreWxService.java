package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.config.StoreConfigService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_TWO;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author liufei
 * @date 10/22/19
 */
@Slf4j
@Service
public class StoreWxService extends ShopBaseService {
    /**
     * 门店
     */
    @Autowired
    public StoreService store;
    /**
     * 门店服务
     */
    @Autowired
    public StoreServiceService storeService;
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
     * The Member card service.会员卡
     */
    @Autowired
    public MemberCardService memberCardService;

    /**
     * The Shop common config service.门店公共配置
     */
    @Autowired
    public ShopCommonConfigService shopCommonConfigService;

    /**
     * The User card dao service.会员卡
     */
    @Autowired
    public UserCardDaoService userCardDaoService;

    private static final Condition delCondition = STORE.DEL_FLAG.eq(BYTE_ZERO);

    /**
     * 门店列表查询-小程序端
     *
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
            storeList = getStoreByCustomCondition(new HashMap<String, Byte>(2) {{
                put("scan_stores", param.getScanStores());
            }}, null);
        } else if (param.getCardId() != null) {
            // 会员卡详情也跳转过来的
            MemberCardPojo memberCardPojo = memberCardService.getMemberCardInfoById(param.getCardId());
            if (memberCardPojo.getStoreUseSwitch().equals(BYTE_ONE)) {
                return new ArrayList<>(0);
            } else {
                // 会员卡支持门店列表,为空支持所有 todo 库中默认值有问题
                String[] supportStoreList = memberCardPojo.getStoreList().split(",");
                if (supportStoreList.length != 0) {
                    storeList = db().selectFrom(STORE).where(STORE.STORE_ID.in(Arrays.asList(supportStoreList))).and(delCondition).fetchInto(StorePojo.class);
                } else {
                    storeList = getStoreByCustomCondition(new HashMap<String, Byte>(2) {{
                        put("scan_stores", param.getScanStores());
                    }}, null);
                }
            }
        } else {
//            商品详情页自提/同城配送过来
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
     * Gets store by custom condition.
     *
     * @param condition the condition 自定义任意条件
     * @param fields    the fields 自定义结果字段集
     */
    public List<StorePojo> getStoreByCustomCondition(Map<String, ?> condition, List<TableField<StoreRecord, ?>> fields) {
        if (condition.get("scan_stores") != null) {
            Byte scanStore = (Byte) condition.get("scan_stores");
            SelectConditionStep<StoreRecord> conditionStep = db()
                .selectFrom(STORE).where(delCondition);
            if (!BYTE_ZERO.equals(scanStore)) {
                conditionStep.and(STORE.POS_SHOP_ID.greaterThan(INTEGER_ZERO));
            }
            return conditionStep.fetchInto(StorePojo.class);
        } else {
            return null;
        }
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
        return new BigDecimal(s).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * The entry point of application.获得可购买的门店列表
     *
     * @param prdIds      商品规格id列表
     * @param deliverType 配送类型,1:自提,2:同城配送
     * @param location    用户位置
     * @param isFromStore todo 同城服务参数
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
     * The entry point of application.todo 获得同城配送可用的门店列表
     */
    public void cityServiceCanUseStoreList(List<StorePojo> storeLists, Location location, Byte isFromStore) {

    }

    /**
     * Gets wxapp store detail.门店信息详情-小程序
     *
     * @param param the param
     * @return the wxapp store detail
     */
    public StoreInfoVo getWxappStoreDetail(StoreInfoParam param) {
        int storeId = param.getStoreId();
        long userId = param.getUserId();
        if (userId != 0) {
            int source = db().select(USER.SOURCE).from(USER).where(USER.USER_ID.eq((int) userId)).fetchOneInto(Integer.class);
            if (source == -1) {
                db().update(USER).set(USER.SOURCE, storeId).execute();
            }
        }
        StorePojo storePojo = store.getStore(storeId);
        Objects.requireNonNull(storePojo, "店铺不存在");
        StoreInfoVo storeInfoVo = new StoreInfoVo();
        FieldsUtil.assignNotNull(storePojo, storeInfoVo);

        // 获取服务列表,按服务分类归纳
        Map<StoreServiceCategoryListQueryVo, List<StoreServiceListQueryVo>> serviceCat = new HashMap<StoreServiceCategoryListQueryVo, List<StoreServiceListQueryVo>>() {{
            storeService.getAllStoreServiceCategory(new StoreServiceCategoryListQueryParam() {{
                setStoreId(storeId);
            }}).forEach(e -> put(e, storeService.getStoreServiceByCatId(storeId, e.getCatId())));
        }};
        storeInfoVo.setServiceCat(serviceCat);
        storeInfoVo.setAllService(storeService.getAllStoreServiceByStoreId(storeId));
        // todo 扫码购
//        List<String> storeScanIds = Arrays.asList(storeConfigService.getStoreScanIds().split(","));
        // todo 获取购物车商品数
        // todo 获取待核销扫码购订单
        return storeInfoVo;
    }

    /**
     * Store pay order.门店买单-小程序
     *
     * @param param the param
     */
    public StorePayOrderVo storePayOrder(StoreInfoParam param) {
        int storeId = param.getStoreId();
        long userId = param.getUserId();
        StorePayOrderVo payOrderVo = new StorePayOrderVo();
        if (userId != 0) {
            UserRecord userRecord = db().select(USER.SOURCE, USER.SCORE, USER.ACCOUNT)
                .from(USER).where(USER.USER_ID.eq((int) userId))
                .fetchOptionalInto(USER).orElseThrow(() -> {
                    log.error("用戶数据[userId: {}]查询不存在", userId);
                    throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
                });
            payOrderVo.setSource(userRecord.getSource());
            payOrderVo.setScore(userRecord.getScore());
            payOrderVo.setAccount(userRecord.getAccount());
            if (payOrderVo.getSource() == -1) {
                db().update(USER).set(USER.SOURCE, storeId).execute();
            }
        }
        // 获取发票开关配置
        payOrderVo.setInvoiceSwitch(shopCommonConfigService.getInvoice());
        // 获取有效用户会员卡列表
        List<ValidUserCardBean> cardList = userCardDaoService.getValidCardList((int) userId, BYTE_ZERO, INTEGER_ZERO)
            .stream().filter((c) -> StringUtils.isBlank(c.getStoreList()) || Arrays.asList(c.getStoreList().split(",")).contains(String.valueOf(storeId)))
            .collect(Collectors.toList());
        log.debug("有效用户会员卡列表:{}", cardList);
        payOrderVo.setMemberCardList(cardList);
        // 店铺营业状态和logo
        ShopRecord shopRecord = db().select(SHOP.BUSINESS_STATE, SHOP.SHOP_AVATAR).from(SHOP).where(SHOP.SHOP_ID.eq(this.getShopId())).fetchOneInto(SHOP);
        payOrderVo.setShopBusinessState(shopRecord.getBusinessState());
        payOrderVo.setShopLogo(shopRecord.getShopAvatar());
        // 门店营业状态和删除标示
        StoreRecord storeRecord = db().select(STORE.BUSINESS_STATE, STORE.DEL_FLAG).from(STORE).where(STORE.STORE_ID.eq(storeId)).fetchOneInto(STORE);
        payOrderVo.setStoreBusinessState(storeRecord.getBusinessState());
        payOrderVo.setDelFlag(storeRecord.getDelFlag());
        // 门店买单开关配置
        payOrderVo.setStoreBuy(storeConfigService.getStoreBuy());
        return payOrderVo;
    }
}
