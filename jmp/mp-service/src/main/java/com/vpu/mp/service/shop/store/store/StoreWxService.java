package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.db.shop.tables.records.StoreRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.Assert;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
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
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.shop.store.technician.TechnicianInfo;
import com.vpu.mp.service.pojo.wxapp.store.*;
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
import com.vpu.mp.service.shop.store.comment.ServiceCommentService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.user.user.UserService;
import com.vpu.mp.service.wechat.WxPayment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.*;
import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.STRING_ONE;
import static com.vpu.mp.service.shop.order.store.StoreOrderService.HUNDRED;
import static com.vpu.mp.service.shop.store.service.ServiceOrderService.ORDER_STATUS_NAME_WAIT_SERVICE;
import static com.vpu.mp.service.shop.store.service.ServiceOrderService.ORDER_STATUS_WAIT_SERVICE;
import static java.math.BigDecimal.ZERO;
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
     * The Comment service.服务评论
     */
    @Autowired
    public ServiceCommentService commentService;

    /**
     * The Common config service.服务评论配置
     */
    @Autowired
    public ShopCommonConfigService commonConfigService;

    /**
     * The constant BYTE_TWO.
     */
    public static final Byte BYTE_TWO = 2;

    private static final Condition delCondition = STORE.DEL_FLAG.eq(BYTE_ZERO);

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
    public String storePay(StoreInfoParam param) {
        int userId = param.getUserId();
        // 用户信息
        UserRecord userRecord = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchOneInto(USER);
        // 发票信息
        InvoiceVo invoiceVo = invoiceService.get(1);
        // 门店订单信息
        StorePayOrderInfo orderInfo = Util.json2Object(param.getOrderInfo(), StorePayOrderInfo.class, false);
        this.transaction(() -> {
            // 创建门店订单
            StoreOrderRecord storeOrderRecord = storeOrderService.createStoreOrder(userRecord, invoiceVo, orderInfo);
            if (storeOrderRecord == null) {
                log.error("创建门店订单失败,原因如下");
                throw new BusinessException(JsonResultCode.CODE_FAIL);
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
                        log.error("会员余额变动失败,原因如下:{}", e.getMessage());
                        throw new BusinessException(JsonResultCode.CODE_FAIL);
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
                // 支付完成送积分; 门店买单支付是否返送积分开关 on 1
                if (STRING_ONE.equals(scoreCfgService.getStoreScore())) {
                    storeOrderService.sendScoreAfterPayDone(storeOrderRecord);
                }
            }
        });
        return "";
    }

    /**
     * Default pay.微信小程序请求支付
     *
     * @param storeOrderInfo the store order info
     * @param openId         the open id
     * @return the string
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

    /**
     * The constant HH_MM_FORMATTER.
     */
    public static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Reservation detail reservation detail vo.门店服务预约详情
     *
     * @param serviceId the service id
     * @return the reservation detail vo
     */
    public ReservationDetailVo reservationDetail(Integer serviceId) {
        // 门店服务信息
        StoreServiceParam service = storeService.getStoreService(serviceId);
        Assert.notNull(service, JsonResultCode.CODE_STORE_SERVICE_NOT_EXIST);
        // 门店信息
        StorePojo storePojo = store.getStore(service.getStoreId());
        Assert.notNull(storePojo, JsonResultCode.CODE_STORE_NOT_EXIST);

        // 设置服务起始日期, 不能预约过期的服务, 持续时间最多两个月
        LocalDate now = LocalDate.now();
        LocalDate startDate = service.getStartDate().toLocalDate().compareTo(now) > 0 ? service.getStartDate().toLocalDate() : now;
        LocalDate twoMonthsLater = service.getStartDate().toLocalDate().plus(2, ChronoUnit.MONTHS);
        LocalDate endDate = service.getEndDate().toLocalDate().compareTo(twoMonthsLater) <= 0 ? service.getEndDate().toLocalDate() : twoMonthsLater;
        log.debug("服务有效预约日期为:{} - {}", startDate, endDate);

        // 服务可预约时段, 用服务时长(单位/分钟)切分成当前可预约的分段服务
        LocalTime startPeriod = LocalTime.parse(service.getStartPeriod(), HH_MM_FORMATTER);
        LocalTime endPeriod = LocalTime.parse(service.getEndPeriod(), HH_MM_FORMATTER);
        int serviceDuration = service.getServiceDuration();
        log.debug("服务可预约时段为:{} - {}; 单次服务时长为: {}", startPeriod, endPeriod, serviceDuration);

        List<ReservationInfo> reservationList = new ArrayList<>();
        for (LocalDate i = startDate; i.isBefore(endDate) || i.equals(endDate); i = i.plusDays(1)) {
            ReservationInfo reservationInfo = createReservationInfo(i, startPeriod, endPeriod, serviceDuration, service);
            if (Objects.nonNull(reservationInfo)) {
                reservationList.add(reservationInfo);
            }
        }
        return ReservationDetailVo.builder()
            .storeInfo(storePojo)
            .serviceInfo(service)
            // 获取服务的最新评论
            .commentInfo(commentService.getNewestcomment(service.getId()))
            .reservationInfoList(reservationList)
            // 是否强制用户绑定手机号
            .isBindMobile(commonConfigService.getBindMobile())
            // 获取门店职称配置
            .technicianTitle(storeConfigService.getTechnicianTitle())
            .build();
    }

    /**
     * Create reservation info reservation info.
     *
     * @param date            the date
     * @param startPeriod     the start period
     * @param endPeriod       the end period
     * @param serviceDuration the service duration
     * @param serviceInfo     the service info
     * @return the reservation info
     */
    private ReservationInfo createReservationInfo(LocalDate date, LocalTime startPeriod, LocalTime endPeriod, int serviceDuration, StoreServiceParam serviceInfo) {
        LocalTime localTime = LocalTime.now();
        if (date.isEqual(LocalDate.now()) && localTime.isAfter(startPeriod)) {
                // 如果可服务日期是当天, 获取当天距离当前时间最近的一次可服务时间段
                startPeriod = startPeriod.plus((((localTime.toSecondOfDay() / 60 - startPeriod.toSecondOfDay() / 60) / serviceDuration + 1) * serviceDuration), ChronoUnit.MINUTES);
        }
        List<ReservationTime> reservationTimeList = new ArrayList<>();
        for (LocalTime i = startPeriod;
             i.isBefore(endPeriod) && (i.plusMinutes(serviceDuration).isBefore(endPeriod) || i.plusMinutes(serviceDuration).equals(endPeriod)) && i.plusMinutes(serviceDuration).isAfter(i);
             i = i.plus(serviceDuration, ChronoUnit.MINUTES)) {
            ReservationTime reservationTime = createReservationTime(date, i, i.plusMinutes(serviceDuration), serviceInfo);
            if (reservationTime != null) {
                reservationTimeList.add(reservationTime);
            }
        }
        return CollectionUtils.isNotEmpty(reservationTimeList) ? ReservationInfo.builder()
            .reservationTimeList(reservationTimeList)
            .reservationDate(date)
            .build() : null;
    }

    /**
     * Create reservation time reservation info . reservation time.
     *
     * @param date        the date
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @param serviceInfo the service info
     * @return the reservation time
     */
    private ReservationTime createReservationTime(LocalDate date, LocalTime startPeriod, LocalTime endPeriod, StoreServiceParam serviceInfo) {
        Integer serviceId = serviceInfo.getId();
        int serviceNum = Objects.nonNull(serviceInfo.getServicesNumber()) ? serviceInfo.getServicesNumber() : 0;
        int tecServiceNum = Objects.nonNull(serviceInfo.getTechServicesNumber()) ? serviceInfo.getTechServicesNumber() : 0;
        List<TechnicianInfo> result = null;
        // 服务类型:0无技师1有技师
        Byte technicianFlag = serviceInfo.getServiceType();
        if (technicianFlag.equals(BYTE_ZERO)) {
            // 服务数量为0表示无上限,可以无限接受服务预约
            if (serviceNum != 0) {
                if (serviceOrderService.checkMaxNumOfReservations(serviceId, null, date, startPeriod, endPeriod) >= serviceNum) {
                    return null;
                }
            }
        } else {
            //
            List<TechnicianInfo> technicianInfoList = technicianService.getTechnicianList(serviceInfo.getStoreId(), date);
            result = technicianInfoList.stream().filter((e) -> {
                // 过滤不支持给定服务的技师
                Byte serviceType = e.getServiceType();
                if (BYTE_ZERO.equals(serviceType)) {
                    return true;
                }
                if (BYTE_ONE.equals(serviceType)) {
                    return Objects.requireNonNull(Util.json2Object(e.getServiceList(), new TypeReference<List<Integer>>() {
                    }, false)).contains(serviceId);
                }
                return false;
            }).filter((e) -> {
                // 过滤不在给定服务时间段内的技师
                LocalTime tempStart = LocalTime.parse(e.getBegcreateTime(), HH_MM_FORMATTER);
                LocalTime tempEnd = LocalTime.parse(e.getEndTime(), HH_MM_FORMATTER);
                return (startPeriod.isAfter(tempStart) || startPeriod.equals(tempStart)) && (endPeriod.isBefore(tempEnd) || endPeriod.equals(tempEnd));
            }).filter((e) -> {
                // 过滤超过技师单时段服务数量上限的技师
                if (tecServiceNum == 0) {
                    return true;
                }
                return serviceOrderService.checkMaxNumOfReservations(serviceId, e.getId(), date, startPeriod, endPeriod) < tecServiceNum;
            }).collect(toList());
            if (CollectionUtils.isEmpty(result)) {
                return null;
            }
        }
        return ReservationTime.builder()
            .startTime(startPeriod)
            .endTime(endPeriod)
            .technicianFlag(technicianFlag)
            .technicianPojoList(result).build();
    }

    /**
     * Submit reservation.创建门店服务预约订单
     */
    public ReservationOrder createReservation(Integer serviceId, Integer userId) {
        // 门店服务信息
        StoreServiceParam service = storeService.getStoreService(serviceId);
        Assert.notNull(service, JsonResultCode.CODE_STORE_SERVICE_NOT_EXIST);
        // 门店信息
        Integer storeId = service.getStoreId();
        StorePojo storePojo = store.getStore(storeId);
        Assert.notNull(storePojo, JsonResultCode.CODE_STORE_NOT_EXIST);

        return ReservationOrder.builder()
            // 获取用户余额account
            .account(userService.getUserByUserId(userId).getAccount())
            // 获取支付开关配置, 会员卡余额支付,余额支付
            .balanceFirst(tradeService.getBalanceFirst())
            .cardFirst(tradeService.getCardFirst())
            // 获取支持的支付方式
            .paymentVoList(new ArrayList<>(paymentService.getSupportPayment().values()))
            // 获取指定用户最近的一个服务预约订单信息(主要是获取用户的名称和手机号;没有就算了)
            .recentOrderInfo(serviceOrderService.getRecentOrderInfo(userId))
            // 获取门店职称配置
            .technicianTitle(storeConfigService.getTechnicianTitle())
            // 获取店铺logo
            .shopAvatar(shopService.getShopById(getShopId()).getShopAvatar())
            // 获取有效用户会员卡列表
            .cardList(userCardDaoService.getStoreValidCardList(userId, storeId))
            .storePojo(storePojo)
            .service(service)
            .build();
    }

    /**
     * Submit reservation.提交确认门店服务预约订单
     */
    public void submitReservation(SubmitReservationParam param) {
        Integer serviceId = param.getServiceId();
        if (!serviceOrderService.checkReservationNum(serviceId, param.getTechnicianId())) {
            // 预约人数已达上限
            throw new BusinessException(JsonResultCode.CODE_RESERVATION_UPPER_LIMIT);
        }
        ServiceOrderRecord serviceOrder = new ServiceOrderRecord();
        FieldsUtil.assignNotNull(param, serviceOrder);
        log.debug("门店服务订单即将创建: {}", serviceOrder);
        this.transaction(() -> {
            serviceOrderService.createServiceOrder(serviceOrder);
            // 会员余额变动
            if (serviceOrder.getUseAccount().compareTo(ZERO) > 0) {
                try {
                    accountService.addUserAccount(new AccountParam() {{
                        setUserId(serviceOrder.getUserId());
                        setAccount(serviceOrder.getUseAccount());
                        setOrderSn(serviceOrder.getOrderSn());
                        setPayment("balance");
                        setIsPaid(BYTE_ONE);
                        setRemark(serviceOrder.getOrderSn());
                    }}, 0, CONDITION_TWO, BYTE_ZERO, "zh");
                } catch (MpException e) {
                    log.error("会员余额变动失败,原因如下:{}", e.getMessage());
                    throw new BusinessException(JsonResultCode.CODE_FAIL);
                }
            }
            // 增加会员卡消费记录
            if (serviceOrder.getMemberCardBalance().compareTo(ZERO) > 0) {
                UserCardParam userCardParam = userCardDaoService.getUserCardInfo(serviceOrder.getMemberCardNo());
                userCardService.cardConsumer(new UserCardConsumeBean() {{
                    setMoneyDis(userCardParam.getMoney());
                    setUserId(serviceOrder.getUserId());
                    setMoney(serviceOrder.getMemberCardBalance());
                    setCardNo(serviceOrder.getMemberCardNo());
                    setCardId(userCardParam.getCardId());
                    setReason(serviceOrder.getOrderSn());
                    setType(BYTE_ZERO);
                }}, INTEGER_ZERO, CONDITION_THREE, BYTE_ZERO, BYTE_ZERO, false);
            }
            if (serviceOrder.getMoneyPaid().compareTo(BIGDECIMAL_ZERO) > 0) {
                //TODO 支付接口
            }
            // 更新门店订单支付状态
            serviceOrderService.updateServiceOrderStatus(serviceOrder.getOrderSn(), ORDER_STATUS_WAIT_SERVICE, ORDER_STATUS_NAME_WAIT_SERVICE);
        });
        // 队列前置校验
        prefixCheck(serviceOrder);
        // TODO 发送模板消息; 1. 预约订单支付成功模板消息; 2. 定时提醒预约服务过期模板消息
        saas.taskJobMainService.dispatchImmediately(
            serviceOrder,
            ServiceOrderRecord.class.getName(),
            getShopId(),
            TaskJobsConstant.TaskJobEnum.RESERVATION_PAY.getExecutionType());
    }

    /**
     * Send reservation pay success message.服务预约支付发送模板消息
     *
     * @param serviceOrder the service order
     */
    public void sendReservationPaySuccessMessage(ServiceOrderRecord serviceOrder) {
        if (Objects.isNull(storeService.getStoreService(serviceOrder.getServiceId()))) {
            // todo 未找到与订单对应的服务id(service_id not found!)
        }
        // todo 处理省市区地理位置信息,code转汉字
/*        $store = $this->store->getRow($service->store_id);
        $page = "pages/appointinfo/appointinfo?order_sn=" . $serviceOrder->order_sn; // 跳转到服务预约信息页
        if ($store) {
            $province = saas()->region->province->getRow($store->province_code);
            $city = saas()->region->city->getRow($store->city_code);
            $district = saas()->region->district->getRow($store->district_code);
            $address = ($province ? $province->name : "") . " " . ($city ? $city->name : "") . " "
                . ($district ? $district->name : "") . " " . ($store ? $store->address : "");
        }
        $keywordsValues = [
        $service->service_name, $store ? $store->store_name : "", $address ?? "",
            $serviceOrder->service_date . " " . $serviceOrder->service_period,
            $serviceOrder->mobile, $serviceOrder->subscriber
        ];*/
        UserRecord userInfo = userService.getUserByUserId(serviceOrder.getUserId());
        if (Objects.isNull(userInfo.getWxOpenid())) {
            // TODO 用户 openid not found!
        }
        // TODO 判定是使用公众号发送模板消息还是小程序发送
    }

    /**
     * Prefix check boolean.发送队列之前的一些前置校验
     *
     * @param serviceOrder the service order
     * @return the boolean
     */
    public boolean prefixCheck(ServiceOrderRecord serviceOrder) {
        if (Objects.isNull(storeService.getStoreService(serviceOrder.getServiceId()))) {
            // todo 未找到与订单对应的服务id(service_id not found!)
        }
        UserRecord userInfo = userService.getUserByUserId(serviceOrder.getUserId());
        if (Objects.isNull(userInfo.getWxOpenid())) {
            // TODO 用户 openid not found!
        }
        return true;
    }
}
