package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.pojo.wxapp.store.*;
import com.vpu.mp.service.saas.region.ProvinceService;
import com.vpu.mp.service.saas.shop.ShopService;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.config.StoreConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.*;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.order.invoice.InvoiceService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConstant.*;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * The type Store wx service.
 *
 * @author liufei
 * @date 10 /22/19
 */
@Slf4j
@Service
public class StoreWxService extends ShopBaseService {
    /**
     * The User service.
     */
    @Autowired
    public UserService userService;
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

    /**
     * The User card service.
     */
    @Autowired
    public UserCardService userCardService;

    /**
     * The Invoice service.发票
     */
    @Autowired
    public InvoiceService invoiceService;

    /**
     * The Store order service.门店订单
     */
    @Autowired
    public StoreOrderService storeOrderService;

    /**
     * The Mp payment service.
     */
    @Autowired
    public MpPaymentService mpPaymentService;

    /**
     * The Account service.余额管理
     */
    @Autowired
    public AccountService accountService;

    /**
     * The Score service.积分服务
     */
    @Autowired
    public ScoreService scoreService;

    /**
     * The Score cfg service.积分配置
     */
    @Autowired
    public ScoreCfgService scoreCfgService;

    /**
     * The Service order service.门店服务订单
     */
    @Autowired
    public ServiceOrderService serviceOrderService;

    /**
     * The Technician service.门店技师
     */
    @Autowired
    public ServiceTechnicianService technicianService;

    /**
     * The Payment service.支付
     */
    @Autowired
    public PaymentService paymentService;

    /**
     * The Trade service.交易服务配置
     */
    @Autowired
    public TradeService tradeService;

    /**
     * The Shop service.店铺
     */
    @Autowired
    public ShopService shopService;

    /**
     * The Province service.省市区
     */
    @Autowired
    public ProvinceService provinceService;

    /**
     * The constant BYTE_TWO.
     */
    public static final Byte BYTE_TWO = 2;

    private static final Condition DEL_CONDITION = STORE.DEL_FLAG.eq(BYTE_ZERO);

    /**
     * 门店列表查询-小程序端
     *
     * @param param 查询入参
     * @return StorePageListVo list
     */
    public List<StorePojo> getList(StoreListParam param) {
        List<StorePojo> storeList = new ArrayList<>();
        Location location = param.getLocation();

        // type为0,普通入口
        if (BYTE_ZERO.equals(param.getType())) {
            storeList = getStoreByCustomCondition(new HashMap<String, Byte>(2) {{
                put("scan_stores", param.getScanStores());
            }}, null);
        } else if (BYTE_ONE.equals(param.getType()) && param.getCardId() != null) {
            // type为1,并且cardId不为空;表示入口为会员卡详情页
            MemberCardPojo memberCardPojo = memberCardService.getMemberCardInfoById(param.getCardId());
            if (memberCardPojo.getStoreUseSwitch().equals(BYTE_ONE)) {
                return new ArrayList<>(0);
            } else {
                // 会员卡支持门店列表,为空支持所有
                List<Integer> supportStoreList = Util.json2Object(memberCardPojo.getStoreList(), new TypeReference<List<Integer>>() {
                }, false);
                if (CollectionUtils.isNotEmpty(supportStoreList)) {
                    storeList = db().selectFrom(STORE).where(STORE.STORE_ID.in(supportStoreList)).and(DEL_CONDITION).fetchInto(StorePojo.class);
                } else {
                    storeList = getStoreByCustomCondition(new HashMap<String, Byte>(2) {{
                        put("scan_stores", param.getScanStores());
                    }}, null);
                }
            }
        } else if (BYTE_TWO.equals(param.getType())) {
//            type为2 ,表示入口为商品详情页自提/同城配送过来
            if (param.getGoodsId() != null) {
                // 根据商品id获取商品规格id列表
                List<GoodsSpecProduct> list = goodsSpecProductService.selectByGoodsId(param.getGoodsId());
                Set<Integer> prdIds = list.stream().map(GoodsSpecProduct::getPrdId).collect(Collectors.toSet());
                log.debug("商品[{}]对应的sku为:{}", param.getGoodsId(), prdIds.toString());
                // 获得该商品可购买的门店列表
                storeList = getCanBuyStoreList(prdIds, param.getDeliverType(), location, BYTE_ONE);
                log.debug("可购买的门店列表:{}", storeList.stream().map(StorePojo::getStoreId).collect(Collectors.toSet()));
            } else {
                // 缺少商品参数 商品规格
                throw new BusinessException("缺少商品参数 商品规格");
            }
        }
        // 查询开启“扫码购”功能的门店ID列表配置，逗号分隔
        String storeScanValue = storeConfigService.getStoreScanIds();
        log.debug("cfg配置表中store_scan_ids值为:{}", storeScanValue);
        List<Integer> storeScanIds = Util.json2Object(storeScanValue, new TypeReference<List<Integer>>() {
        }, false);
        log.debug("开启“扫码购”功能的门店ID列表配置项:{}", storeScanIds.toString());
        // 筛掉不支持扫码购的门店或者添加是否支持扫码购的标示位
        if (param.getScanStores().equals(BYTE_ONE)) {
            storeList = storeList.stream().filter(s -> storeScanIds.contains(s.getStoreId())).collect(toList());
        } else {
            storeList.forEach(s -> {
                s.setScanBuy(storeScanIds.contains(s.getStoreId()) ? BYTE_ONE : BYTE_ZERO);
            });
        }
        // 设置图片和距离
        storeList.forEach(s -> {
            String storeImgs = s.getStoreImgs();
            log.debug("门店宣传图列表为:{}", storeImgs);
            List<String> imgs = Util.json2Object(storeImgs, new TypeReference<List<String>>() {
            }, false);
            if (CollectionUtils.isNotEmpty(imgs)) {
                // 设置门店主图中的第一张为门店列表展示图
                s.setStoreImgs(imgs.get(INTEGER_ZERO));
            } else {
                s.setStoreImgs(null);
            }
            Double lat1 = location.getLatitude();
            Double lon1 = location.getLatitude();
            if (lat1 != null && lon1 != null) {
                double distance = getDistance(lat1, lon1, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                log.debug("门店 {} 距离用户位置 {} km", s.getStoreName(), distance);
                s.setDistance(distance);
            } else {
                // 默认或者异常地理位置信息均为0km
                s.setDistance(DOUBLE_ZERO);
            }
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
     * @return the store by custom condition
     */
    public List<StorePojo> getStoreByCustomCondition(Map<String, ?> condition, List<TableField<StoreRecord, ?>> fields) {
        if (condition.get("scan_stores") != null) {
            Byte scanStore = (Byte) condition.get("scan_stores");
            SelectConditionStep<StoreRecord> conditionStep = db()
                .selectFrom(STORE).where(DEL_CONDITION);
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
     * @return the can buy store list
     */
    public List<StorePojo> getCanBuyStoreList(Set<Integer> prdIds, Byte deliverType, Location location, Byte isFromStore) {
        SelectConditionStep<Record1<Integer>> conditionStep = db().select(STORE.STORE_ID).from(STORE_GOODS)
            .leftJoin(STORE).on(STORE_GOODS.STORE_ID.eq(STORE.STORE_ID))
            .where(STORE_GOODS.PRD_ID.in(prdIds))
            .and(STORE_GOODS.IS_ON_SALE.eq(BYTE_ONE))
            .and(STORE.BUSINESS_STATE.eq(BYTE_ONE))
            .and(STORE.DEL_FLAG.eq(BYTE_ZERO));
        if (deliverType.equals(BYTE_ZERO)) {
            conditionStep.and(STORE.AUTO_PICK.eq(SHORT_ONE));
        }
        if (deliverType.equals(BYTE_ONE)) {
            // TODO 同城配送 新增字段
//            conditionStep.and(STORE.city.eq(SHORT_ONE));
        }
        List<Integer> storeIds = conditionStep.groupBy(STORE.STORE_ID)
            .having(DSL.count(STORE.STORE_ID).eq(prdIds.size()))
            .fetchInto(Integer.class);
        List<StorePojo> storeLists = db().selectFrom(STORE).where(STORE.STORE_ID.in(storeIds)).fetchInto(StorePojo.class);
        if (deliverType.equals(BYTE_ONE)) {
            // TODO 查询支持同城配送的门店列表
            cityServiceCanUseStoreList(storeLists, location, isFromStore);
        }
        return storeLists;
    }

    /**
     * The entry point of application.todo 获得同城配送可用的门店列表
     *
     * @param storeLists  the store lists
     * @param location    the location
     * @param isFromStore the is from store
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
        Integer storeId = param.getStoreId();
        Integer userId = param.getUserId();
        if (userId != null) {
            // 更新用户表 门店来源(-1未录入0后台>0为门店) todo 记录一下用户第一次登录的门店, 可删
            userService.updateFields(USER.USER_ID.eq(userId).and(USER.SOURCE.eq(-1)), new HashMap<Field<?>, Integer>(1) {{
                put(USER.SOURCE, storeId);
            }});
        }
        StorePojo storePojo = store.getStore(storeId);
        Objects.requireNonNull(storePojo, "店铺不存在");
        StoreInfoVo storeInfoVo = new StoreInfoVo();
        FieldsUtil.assignNotNull(storePojo, storeInfoVo);

        // 获取服务列表,按服务分类归纳
        List<StoreServiceCategoryListQueryVo> catService = storeService.getAllStoreServiceCategory(new StoreServiceCategoryListQueryParam() {{
            setStoreId(storeId);
        }});
        catService.forEach(e -> e.setServiceList(storeService.getStoreServiceByCatId(storeId, e.getCatId())));
        storeInfoVo.setServiceCat(catService);
        storeInfoVo.setAllService(storeService.getAllStoreServiceByStoreId(storeId));
        // todo 扫码购
//        List<String> storeScanIds = Arrays.asList(storeConfigService.getStoreScanIds().split(","));
        // todo 获取购物车商品数
        // todo 获取待核销扫码购订单
        // 门店买单开关配置
        storeInfoVo.setStoreBuy(storeConfigService.getStoreBuy());
        return storeInfoVo;
    }

    /**
     * Store pay order.门店买单-小程序
     *
     * @param param the param
     * @return the store pay order vo
     */
    public StorePayOrderVo storePayOrder(StoreInfoParam param) {
        int storeId = param.getStoreId();
        int userId = param.getUserId();
        StorePayOrderVo payOrderVo = new StorePayOrderVo();
        // 用户积分和余额
        UserRecord userRecord = userService.getUserByUserId(userId);
        payOrderVo.setScore(userRecord.getScore());
        payOrderVo.setAccount(userRecord.getAccount());
        // 获取发票开关配置
        payOrderVo.setInvoiceSwitch(shopCommonConfigService.getInvoice());
        // 获取有效用户会员卡列表
        List<ValidUserCardBean> cardList = userCardDaoService.getValidCardList(userId, BYTE_ZERO, BYTE_ZERO)
            .stream().filter((c) -> StringUtils.isBlank(c.getStoreList()) || Objects.requireNonNull(Util.json2Object(c.getStoreList(), new TypeReference<List<Integer>>() {
            }, false)).contains(storeId))
            .collect(toList());
        log.debug("有效用户会员卡列表:{}", cardList);
        payOrderVo.setMemberCardList(cardList);
        // 门店营业状态和删除标示
        StorePojo storePojo = store.getStore(storeId);
        payOrderVo.setStoreBusinessState(storePojo.getBusinessState());
        payOrderVo.setDelFlag(storePojo.getDelFlag());
        // 门店买单开关配置
        payOrderVo.setStoreBuy(storeConfigService.getStoreBuy());
        return payOrderVo;
    }

    /**
     * Store pay.门店买单支付
     *
     * @param param the param 门店订单信息
     * @return the string 订单编号
     */
    public WebPayVo storePay(StoreInfoParam param) {
        // 订单编号, 创建支付订单时生成,支付成功后用来获取订单详情
        AtomicReference<String> orderSn = new AtomicReference<>();
        int userId = param.getUserId();
        // 用户信息
        UserRecord userRecord = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchOneInto(USER);
        // 门店订单信息
        StorePayOrderInfo orderInfo = param.getOrderInfo();
        // 发票信息
        InvoiceVo invoiceVo = invoiceService.get(orderInfo.getInvoiceId());
        // 事务db前置校验
        StoreOrderTran storeOrderTran = storeOrderService.checkBeforeCreate(userRecord, invoiceVo, orderInfo);
        AtomicReference<WebPayVo> webPayVo = new AtomicReference<>();
        this.transaction(() -> {
            // 创建门店订单
            orderSn.set(storeOrderService.createStoreOrder(storeOrderTran));
            if (BigDecimalUtil.greaterThanZero(orderInfo.getMoneyPaid())) {
                //支付预留接口
                String openId = userService.getUserByUserId(param.getUserId()).getWxOpenid();
                webPayVo.set(mpPaymentService.wxUnitOrder(param.getClientIp(), STORE_BUY, orderSn.get(), orderInfo.getMoneyPaid(), openId));
                log.debug("微信支付接口调用结果：{}", webPayVo.get());
            } else {
                // 更新门店订单支付状态
                storeOrderService.updateRecord(STORE_ORDER.ORDER_SN.eq(orderSn.get()), new StoreOrderRecord() {{
                    setPayTime(Timestamp.valueOf(LocalDateTime.now()));
                    setOrderStatus(PAY_SUCCESS);
                    setOrderStatusName(PAY_SUCCESS_NAME);
                }});
            }
            if (Objects.isNull(webPayVo.get())) {
                webPayVo.set(new WebPayVo() {{
                    setOrderSn(orderSn.get());
                }});
            } else {
                webPayVo.get().setOrderSn(orderSn.get());
            }
        });
        // TODO 支付完成送积分; 门店买单支付是否返送积分开关 on 1
//        if (BYTE_ONE.equals(scoreCfgService.getStoreScore())) {
//            storeOrderService.sendScoreAfterPayDone(storeOrderTran.getStoreOrder());
//        }
        return webPayVo.get();
    }
}
