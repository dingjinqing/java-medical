package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.config.StoreConfigService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.*;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.order.invoice.InvoiceService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.wechat.WxPayment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Condition;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.*;
import static com.vpu.mp.service.shop.order.store.StoreOrderService.HUNDRED;
import static java.math.BigDecimal.ZERO;
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

    public static final Byte BYTE_TWO = 2;

    private static final Condition delCondition = STORE.DEL_FLAG.eq(BYTE_ZERO);

    /**
     * 门店列表查询-小程序端
     *
     * @param param 查询入参
     * @return StorePageListVo
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
                    storeList = db().selectFrom(STORE).where(STORE.STORE_ID.in(supportStoreList)).and(delCondition).fetchInto(StorePojo.class);
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
            storeList = storeList.stream().filter(s -> storeScanIds.contains(s.getStoreId())).collect(Collectors.toList());
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
        int userId = param.getUserId();
        StorePayOrderVo payOrderVo = new StorePayOrderVo();
        if (userId != 0) {
            UserRecord userRecord = db().select(USER.SOURCE, USER.SCORE, USER.ACCOUNT)
                .from(USER).where(USER.USER_ID.eq(userId))
                .fetchOneInto(USER);
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
        List<ValidUserCardBean> cardList = userCardDaoService.getValidCardList(userId, BYTE_ZERO, INTEGER_ZERO)
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

    /**
     * Store pay.门店买单支付
     *
     * @param param the param 门店订单信息
     * @return the string 订单编号
     */
    public String storePay(StoreInfoParam param) {
        int userId = param.getUserId();
        // 用户信息
        UserRecord userRecord = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchOneInto(USER);
        // 发票信息
        InvoiceVo invoiceVo = invoiceService.get(1);
        // 门店订单信息
        StorePayOrderInfo orderInfo = null;
        try {
            orderInfo = MAPPER.readValue(param.getOrderInfo(), StorePayOrderInfo.class);
            Assert.assertNotNull(orderInfo);
        } catch (IOException e) {
            log.error("门店订单信息");
            e.printStackTrace();
        }
        // 创建门店订单
        StoreOrderRecord storeOrderRecord = storeOrderService.createStoreOrder(userRecord, invoiceVo, orderInfo);
        if (storeOrderRecord == null) {
            // todo 创建门店订单失败
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
        }

        if (storeOrderRecord.getMoneyPaid().compareTo(ZERO) > 0) {
            String openId = orderInfo.getOpenid();
            switch (param.getAppletRequestSource()) {
                case CONDITION_ZERO:
                    defaultPay(storeOrderRecord, orderInfo.getOpenid());
                    break;
                case CONDITION_ONE:
                    aliMiniPay(userRecord);
                    break;
                default:
                    break;
            }
        } else {
            // 会员余额变动
            if (storeOrderRecord.getUseAccount().compareTo(ZERO) > 0) {
                try {
                    accountService.addUserAccount(new AccountParam() {{
                        setUserId(userId);
                        setAccount(userRecord.getAccount());
                        setOrderSn(storeOrderRecord.getOrderSn());
                        setAmount(storeOrderRecord.getUseAccount());
                        setPayment("balance");
                        setIsPaid(BYTE_ONE);
                        setRemark(storeOrderRecord.getOrderSn());
                    }}, 0, CONDITION_TWO, BYTE_ZERO, "zh");
                } catch (MpException e) {
                    e.printStackTrace();
                }
            }
            // 创建用户积分记录
            if (storeOrderRecord.getScoreDiscount().compareTo(ZERO) > 0) {
                scoreService.addUserScore(new UserScoreVo() {{
                    setScoreDis(userRecord.getScore());
                    setUserId(userId);
                    setScore(storeOrderRecord.getScoreDiscount().multiply(HUNDRED).intValue());
                    setOrderSn(storeOrderRecord.getOrderSn());
                    setShopId(getShopId());
                    setRemark(storeOrderRecord.getOrderSn());
                }}, "0", BYTE_ONE, BYTE_ZERO);
            }
            // 增加会员卡消费记录
            if (storeOrderRecord.getMemberCardBalance().compareTo(ZERO) > 0) {
                UserCardParam userCardParam = userCardDaoService.getUserCardInfo(storeOrderRecord.getMemberCardNo());
                userCardService.cardConsumer(new UserCardConsumeBean() {{
                    setMoneyDis(storeOrderRecord.getMemberCardBalance());
                    setUserId(userId);
                    setMoney(storeOrderRecord.getMemberCardBalance());
                    setCardNo(storeOrderRecord.getMemberCardNo());
                    setCardId(userCardParam.getCardId());
                    setReason(storeOrderRecord.getOrderSn());
                    setType(BYTE_ZERO);
                }}, INTEGER_ZERO, CONDITION_THREE, BYTE_ZERO, BYTE_ZERO, false);
            }
            // 跟新门店订单支付状态
            storeOrderService.updateRecord(STORE_ORDER.ORDER_SN.eq(storeOrderRecord.getOrderSn()), new StoreOrderRecord() {{
                setPayTime(Timestamp.valueOf(LocalDateTime.now()));
                setOrderStatus(BYTE_ONE);
                setOrderStatusName("已支付");
            }});
            // todo 支付完成送积分
            // 门店买单返送积分开关 on 1
//            scoreCfgService.get(STORE_SCORE);
            if (true) {
                storeOrderService.sendScoreAfterPayDone(storeOrderRecord);
            }
        }
        return "";
    }

    /**
     * Default pay.微信小程序请求支付
     */
    public String defaultPay(StoreOrderRecord storeOrderInfo, String openId) {
        BigDecimal amount = storeOrderInfo.getMoneyPaid().multiply(HUNDRED);
        WxPayment wxPayment = mpPaymentService.getMpPay();
        // 获取微信支付id
        String prepayId = mpPaymentService.getPrepayId("门店买单", storeOrderInfo.getOrderSn(), openId, amount);
        // 更新门店订单中微信支付id
        storeOrderService.updatePrepayIdByOrderSn(storeOrderInfo.getOrderSn(), prepayId);
//    todo    $payConfig = $payment->jssdk->bridgeConfig($prepayId, false);
        return storeOrderInfo.getOrderSn();
    }

    /**
     * Ali mini pay.支付宝小程序请求支付
     *
     * @param userInfo the user info
     */
    public void aliMiniPay(UserRecord userInfo) {

    }
}
