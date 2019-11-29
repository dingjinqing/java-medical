package com.vpu.mp.service.shop.store.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.CommentServiceRecord;
import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.exception.Assert;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.shop.store.technician.TechnicianInfo;
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
import com.vpu.mp.service.shop.store.comment.ServiceCommentService;
import com.vpu.mp.service.shop.store.postsale.ServiceTechnicianService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.service.StoreServiceService;
import com.vpu.mp.service.shop.user.message.WechatMessageTemplateService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static com.vpu.mp.service.foundation.data.JsonResultCode.CODE_DATA_NOT_EXIST;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.shop.store.service.ServiceOrderService.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author liufei
 * @date 11/14/19
 * 门店服务预约
 */
@Slf4j
@Service
public class StoreReservation extends ShopBaseService {
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
     * The Message template service.
     */
    @Autowired
    public WechatMessageTemplateService messageTemplateService;

    /**
     * The Province service.省市区
     */
    @Autowired
    public ProvinceService provinceService;

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
        log.debug(",门店服务信息:{}", service);
        // 门店信息
        Integer storeId = service.getStoreId();
        StorePojo storePojo = store.getStore(storeId);
        Assert.notNull(storePojo, JsonResultCode.CODE_STORE_NOT_EXIST);
        log.debug(",门店信息:{}", storePojo);
        return ReservationOrder.builder()
            // 获取用户余额account
            .account(Optional.ofNullable(userService.getUserByUserId(userId)).orElseThrow(() -> new BusinessException(CODE_DATA_NOT_EXIST, "userId:" + userId)).getAccount())
            // 获取支付开关配置, 会员卡余额支付,余额支付
            .balanceFirst(tradeService.getBalanceFirst())
            .cardFirst(tradeService.getCardFirst())
            // 获取支持的支付方式
            .paymentVoList(new ArrayList<>(Optional.ofNullable(paymentService.getSupportPayment()).orElse(new HashMap<>(8)).values()))
            // 获取指定用户最近的一个服务预约订单信息(主要是获取用户的名称和手机号;没有就算了)
            .recentOrderInfo(serviceOrderService.getRecentOrderInfo(userId))
            // 获取门店职称配置
            .technicianTitle(storeConfigService.getTechnicianTitle())
            // 获取店铺logo
            .shopAvatar(Optional.ofNullable(shopService.getShopById(getShopId())).orElseThrow(() -> new BusinessException(CODE_DATA_NOT_EXIST, "shopId:" + getShopId())).getShopAvatar())
            // 获取有效用户会员卡列表
            .cardList(userCardDaoService.getStoreValidCardList(userId, storeId))
            .storePojo(storePojo)
            .service(service)
            .build();
    }

    /**
     * Submit reservation.提交确认门店服务预约订单
     *
     * @param param the param
     */
    public WebPayVo submitReservation(SubmitReservationParam param) {
        Integer serviceId = param.getServiceId();
        if (!serviceOrderService.checkReservationNum(param)) {
            // 预约人数已达上限
            throw new BusinessException(JsonResultCode.CODE_RESERVATION_UPPER_LIMIT);
        }
        String serviceName = storeService.getStoreService(serviceId).getServiceName();
        ServiceOrderRecord serviceOrder = new ServiceOrderRecord();
        FieldsUtil.assignNotNull(param, serviceOrder);
        // 事务前置校验
        ServiceOrderTran orderTran = serviceOrderService.checkBeforeCreate(serviceOrder);
        AtomicReference<WebPayVo> webPayVo = new AtomicReference<>();
        this.transaction(() -> {
            // 创建订单
            String orderSn = serviceOrderService.createServiceOrder(orderTran);
            log.debug("订单创建完成，订单编号为：{}", orderSn);
            if (serviceOrder.getMoneyPaid().compareTo(BIGDECIMAL_ZERO) > 0) {
                //TODO 支付接口
                String openId = userService.getUserByUserId(param.getUserId()).getWxOpenid();
                webPayVo.set(mpPaymentService.wxUnitOrder(param.getClientIp(), serviceName, orderSn, serviceOrder.getMoneyPaid(), openId));
                log.debug("微信支付接口调用结果：{}", webPayVo.get());
                // TODO 记录prepayId到订单表中
                serviceOrderService.updateSingleField(orderSn, SERVICE_ORDER.PREPAY_ID, webPayVo.get().getResult().getPrepayId());
            } else {
                // 如果不需要调用微信支付，直接将订单状态由待付款改为待服务
                serviceOrderService.updateServiceOrderStatus(orderSn, ORDER_STATUS_WAIT_SERVICE, ORDER_STATUS_NAME_WAIT_SERVICE);
            }
            if (Objects.isNull(webPayVo.get())) {
                webPayVo.set(new WebPayVo() {{
                    setOrderSn(orderSn);
                }});
            } else {
                webPayVo.get().setOrderSn(orderSn);
            }
        });
        /*// 队列前置校验
        prefixCheck(serviceOrder);
        // TODO 发送模板消息; 1. 预约订单支付成功模板消息; 2. 定时提醒预约服务过期模板消息
        saas.taskJobMainService.dispatchImmediately(
            serviceOrder,
            ServiceOrderRecord.class.getName(),
            getShopId(),
            TaskJobsConstant.TaskJobEnum.RESERVATION_PAY.getExecutionType());*/
        // 返回支付成功后的预约订单详情
//        return serviceOrderService.getServiceOrderDetail(orderSn.get());
        return webPayVo.get();
    }

    /**
     * Send reservation pay success message.服务预约支付发送模板消息
     *
     * @param serviceOrder the service order
     */
    public void sendReservationPaySuccessMessage(ServiceOrderRecord serviceOrder) {
        // TODO 判定是使用公众号发送模板消息还是小程序发送
        if (messageTemplateService.chooseMessageTemplate(null, null, null, null)) {
            //  发送小程序模版消息
            messageTemplateService.sendMaMessage(null, null, null);
        } else {
            //发送公众号模版消息
            messageTemplateService.sendMpMessage(null, null);
        }
    }

    /**
     * Prefix check boolean.发送队列之前的一些前置校验
     *
     * @param serviceOrder the service order
     * @return the boolean
     */
    public boolean prefixCheck(ServiceOrderRecord serviceOrder) {
        if (Objects.isNull(storeService.getStoreService(serviceOrder.getServiceId()))) {
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST, "ServiceId: " + serviceOrder.getServiceId());
        }
        UserRecord userInfo = userService.getUserByUserId(serviceOrder.getUserId());
        if (Objects.isNull(userInfo.getWxOpenid())) {
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST, "WxOpenid: " + userInfo.getWxOpenid());
        }
        // todo 构造发送消息模板的入参数据
        StorePojo storePojo = store.getStore(serviceOrder.getStoreId());
        // 拼接地址信息
        String address = provinceService.getFullAddressById(Integer.parseInt(storePojo.getProvinceCode()), Integer.parseInt(storePojo.getCityCode()), Integer.parseInt(storePojo.getDistrictCode())) + storePojo.getAddress();
        /*
        $keywordsValues = [
        $service->service_name, $store ? $store->store_name : "", $address ?? "",
            $serviceOrder->service_date . " " . $serviceOrder->service_period,
            $serviceOrder->mobile, $serviceOrder->subscriber
        ];*/
        ServiceOrderTemplate serviceOrderTemplate = new ServiceOrderTemplate();
        return true;
    }

    /**
     * Gets reservation detail.获取服务预约订单详情(根据订单号)
     *
     * @param param the param
     * @return the reservation detail
     */
    public ReservationDetail getReservationDetail(ReservationDetail param) {
        Iterator<ReservationDetail> iterator = getReservationDetail(SERVICE_ORDER.ORDER_SN.eq(param.getOrderSn()), ReservationDetail.class).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * Gets reservation detail.获取服务预约订单详情(根据订单id)
     *
     * @param orderId the order id
     * @return the reservation detail
     */
    public ReservationDetail getReservationDetail(Integer orderId) {
        Iterator<ReservationDetail> iterator = getReservationDetail(SERVICE_ORDER.ORDER_ID.eq(orderId), ReservationDetail.class).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * Gets reservation detail.
     *
     * @param condition the condition
     * @return the reservation detail
     */
    public <T> List<T> getReservationDetail(Condition condition, Class<T> clazz) {
        return db().select(
            SERVICE_ORDER.ORDER_ID
            , SERVICE_ORDER.ORDER_SN
            , SERVICE_ORDER.ORDER_STATUS
            , SERVICE_ORDER.ORDER_STATUS_NAME
            , SERVICE_ORDER.TECHNICIAN_ID
            , SERVICE_ORDER.TECHNICIAN_NAME
            , SERVICE_ORDER.SERVICE_DATE
            , SERVICE_ORDER.SERVICE_PERIOD
            , SERVICE_ORDER.VERIFY_CODE
            , SERVICE_ORDER.CREATE_TIME,
            SERVICE_ORDER.SERVICE_ID
            , SERVICE_ORDER.STORE_ID
            , SERVICE_ORDER.MONEY_PAID
            , STORE_SERVICE.SERVICE_NAME
            , STORE_SERVICE.SERVICE_PRICE
            , STORE_SERVICE.SERVICE_SUBSIST
            , STORE_SERVICE.SERVICE_IMG
            , STORE.STORE_NAME
            , DslPlus.jsonExtract(STORE.STORE_IMGS, "$[0]").as("storeImg")
            , STORE.PROVINCE_CODE
            , STORE.CITY_CODE
            , STORE.DISTRICT_CODE
            , STORE.ADDRESS
            , STORE.LATITUDE
            , STORE.LONGITUDE
        ).
            from(SERVICE_ORDER).leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID))
            .leftJoin(STORE).on(SERVICE_ORDER.STORE_ID.eq(STORE.STORE_ID))
            .where(condition).and(SERVICE_ORDER.DEL_FLAG.eq(BYTE_ZERO)).fetchInto(clazz);
    }

    /**
     * Confirm complete reservation detail.服务订单确认完成 todo 取消
     *
     * @param param the param
     * @return the reservation detail
     */
    public ReservationDetail confirmComplete(ReservationDetail param) {
        String username = Optional.ofNullable(userService.getUserInfo(param.getUserId())).orElseThrow(() -> new BusinessException(CODE_DATA_NOT_EXIST, "user:" + param.getUserId())).getUsername();
        Map<Field<?>, Object> map = new HashMap<Field<?>, Object>(5) {{
            put(SERVICE_ORDER.FINISHED_TIME, Timestamp.valueOf(LocalDateTime.now()));
            put(SERVICE_ORDER.ORDER_STATUS, ORDER_STATUS_FINISHED);
            put(SERVICE_ORDER.ORDER_STATUS_NAME, ORDER_STATUS_NAME_FINISHED);
            put(SERVICE_ORDER.VERIFY_ADMIN, username);
            put(SERVICE_ORDER.VERIFY_TYPE, BYTE_ONE);
        }};
        this.transaction(() -> {
            serviceOrderService.updateServiceOrder(param.getOrderId(), map);
            //预约完成服务销量加一
            Integer serviceId = serviceOrderService.selectSingleField(param.getOrderId(), SERVICE_ORDER.SERVICE_ID);
            storeService.updateSingleField(serviceId, STORE_SERVICE.SALE_NUM, STORE_SERVICE.SALE_NUM.add(INTEGER_ONE));
        });
        return getReservationDetail(param.getOrderId());
    }

    /**
     * Reservation list map.预约列表
     *
     * @param userId the user id
     * @return the map
     */
    public Map<Byte, List<ReservationListVo>> reservationList(Integer userId) {
        List<ReservationListVo> list = getReservationDetail(SERVICE_ORDER.USER_ID.eq(userId), ReservationListVo.class);
        list.forEach((e) -> {
            if (commentService.isComment(e.getOrderSn())) {
                e.setFlag(BYTE_ONE);
            }
        });
        Map<Byte, List<ReservationListVo>> map = list.stream().collect(groupingBy(ReservationListVo::getOrderStatus));
        return map;
    }

    /**
     * Reservation list list.预约列表
     *
     * @param userId      the user id
     * @param orderStatus the order status
     * @return the list
     */
    public List<ReservationListVo> reservationList(Integer userId, Byte orderStatus) {
        return getReservationDetail(SERVICE_ORDER.USER_ID.eq(userId).and(SERVICE_ORDER.ORDER_STATUS.eq(orderStatus)), ReservationListVo.class);
    }

    /**
     * Reservation del.预约订单删除
     *
     * @param orderId the order id
     */
    public void reservationDel(Integer orderId) {
        serviceOrderService.updateSingleField(orderId, SERVICE_ORDER.DEL_FLAG, BYTE_ONE);
    }

    /**
     * Reservation comment service comment vo.订单评价
     *
     * @param orderSn the order sn
     * @return the service comment vo
     */
    public ServiceCommentVo reservationComment(String orderSn) {
        return commentService.getCommentByOrderSn(orderSn);
    }

    /**
     * Create comment.添加预约评价
     *
     * @param param the param
     */
    public void createComment(ServiceCommentVo param) {
        // 门店服务评论配置：0不用审核    1先发后审   2先审后发
        Byte commConfig = storeConfigService.getServiceComment();
        CommentServiceRecord serviceRecord = new CommentServiceRecord();
        FieldsUtil.assignNotNull(param, serviceRecord);
        // 0:未审批,1:审批通过,2:审批未通过（不用审核时评价状态直接为通过，否则为待审核）
        serviceRecord.setFlag(BYTE_ZERO.equals(commConfig) ? BYTE_ONE : BYTE_ZERO);
        commentService.createComment(serviceRecord);
    }
}
