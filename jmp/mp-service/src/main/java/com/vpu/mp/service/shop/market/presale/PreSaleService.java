package com.vpu.mp.service.shop.market.presale;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.PresaleProductRecord;
import com.vpu.mp.db.shop.tables.records.PresaleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.share.ShareConfig;
import com.vpu.mp.service.pojo.shop.market.presale.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.ACTIVITY_TIME_RANGE_CONFLICT;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam.*;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam.DELIVER_POSTPONE;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam.DELIVER_SPECIFIC;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.GOODS_TYPE_PRE_SALE;
import static java.lang.String.format;
import static org.jooq.impl.DSL.select;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 定金膨胀活动
 *
 * @author 郑保乐
 */
@Service
public class PreSaleService extends ShopBaseService {

    private static final Presale TABLE = PRESALE;
    private static final PresaleProduct SUB_TABLE = PRESALE_PRODUCT;
    private static final OrderInfo ORDER = ORDER_INFO;
    private static final OrderGoods ORDER_GOODS = OrderGoods.ORDER_GOODS;

    private static final byte NOT_DELETED = 0;

    /** 已购商品数量 **/
    private static final String BOUGHT_QUANTITY = "boughtGoodsQuantity";
    /** 订单数 **/
    private static final String ORDER_QUANTITY = "orderQuantity";
    /** 已付定金订单数 **/
    private static final String BARGAIN_PAID_QUANTITY = "bargainPaidOrderQuantity";
    /** 已付尾款订单数 **/
    private static final String TAIL_PAID_QUANTITY = "tailPaidOrderQuantity";
    /** 下单用户数 **/
    private static final String ORDER_USER_QUANTITY = "orderUserQuantity";

    private static final String SHARE_PAGE_PATH = "pages/presaleitem/presaleitem?goods_id=%s&presale_id=%s";

    /**
     * 获取定金膨胀活动列表
     */
    public PageResult<PreSaleListVo> getPageList(PreSaleListParam param) {
        SelectConditionStep<Record14<Integer, String, Timestamp, Timestamp, Timestamp, Timestamp, Byte, Timestamp,
            Timestamp, Integer, Integer, Integer, Integer, Serializable>> query =
            db().select(TABLE.ID, TABLE.PRESALE_NAME, TABLE.PRE_START_TIME, TABLE.PRE_END_TIME,
                TABLE.START_TIME, TABLE.END_TIME, TABLE.STATUS, TABLE.PRE_START_TIME_2, TABLE.PRE_END_TIME_2,
                DSL.count(ORDER.ORDER_ID).as(ORDER_QUANTITY),
                DSL.count(ORDER.ORDER_ID)
                    .filterWhere(ORDER.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_BARGIAN)).as(BARGAIN_PAID_QUANTITY),
                DSL.count(ORDER.ORDER_ID)
                    .filterWhere(ORDER.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_BARGIAN)
                        .and(ORDER.BK_ORDER_PAID.gt((byte) 0))).as(TAIL_PAID_QUANTITY),
                DSL.countDistinct(ORDER.USER_ID).as(ORDER_USER_QUANTITY),
                DSL.coalesce(DSL.sum(ORDER_GOODS.GOODS_NUMBER), 0).as(BOUGHT_QUANTITY)
            )
                .from(TABLE)
                .leftJoin(ORDER)
                .on(ORDER.GOODS_TYPE.eq(String.valueOf(GOODS_TYPE_PRE_SALE)).and(ORDER.ACTIVITY_ID.eq(TABLE.ID)))
                .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_ID.eq(ORDER.ORDER_ID))
                .where(TABLE.DEL_FLAG.eq(NOT_DELETED));
        buildOptions(query, param);
        query.groupBy(TABLE.ID, TABLE.PRESALE_NAME, TABLE.PRE_START_TIME, TABLE.PRE_END_TIME, TABLE.START_TIME,
            TABLE.END_TIME, TABLE.STATUS, TABLE.PRE_START_TIME_2, TABLE.PRE_END_TIME_2);
        query.orderBy(TABLE.CREATE_TIME.desc());
        PageResult<PreSaleListVo> page = getPageResult(query, param, PreSaleListVo.class);
        transform(page);
        return page;
    }

    /**
     * 状态转换
     */
    private void transform(PageResult<PreSaleListVo> page) {
        page.getDataList().forEach(this::transform);
    }

    /**
     * 状态转换
     */
    private void transform(PreSaleListVo vo) {
        vo.setStatus(getStatusOf(vo));
    }

    /**
     * 获取活动状态
     */
    private Byte getStatusOf(StatusContainer vo) {
        Byte originalStatus = vo.getStatus();
        if (originalStatus == 0) {
            return DISABLED;
        } else {
            Timestamp preStartTime = vo.getPreStartTime();
            Timestamp preEndTime = vo.getPreEndTime();
            Timestamp preStartTime2 = vo.getPreStartTime2();
            Timestamp preEndTime2 = vo.getPreEndTime2();
            Timestamp startTime = vo.getStartTime();
            Timestamp endTime = vo.getEndTime();
            Timestamp now = Util.currentTimeStamp();
            if (now.before(preStartTime)) {
                return NOT_STARTED;
            } else if ((now.after(preStartTime) && now.before(preEndTime))) {
                return ONGOING;
            } else if (null != startTime && null != endTime) {
                if (now.after(startTime) && now.before(endTime)) {
                    return ONGOING;
                }
            } else if (null != preStartTime2 && null != preEndTime2) {
                if (now.after(preStartTime2) && now.before(preEndTime2)) {
                    return ONGOING;
                }
            }
            return EXPIRED;
        }
    }

    /**
     * 条件查询
     */
    private void buildOptions(SelectConditionStep<Record14<Integer, String, Timestamp, Timestamp, Timestamp,
        Timestamp, Byte, Timestamp, Timestamp, Integer, Integer,
        Integer, Integer, Serializable>> query, PreSaleListParam param) {
        String name = param.getName();
        Timestamp preStartTime = param.getPreStartTime();
        Timestamp preEndTime = param.getPreEndTime();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Byte status = param.getStatus();
        if (!isEmpty(name)) {
            query.and(TABLE.PRESALE_NAME.like(format("%s%%", name)));
        }
        if (null != preStartTime) {
            query.and(TABLE.PRE_START_TIME.ge(preStartTime));
        }
        if (null != preEndTime) {
            query.and(TABLE.PRE_END_TIME.le(preEndTime));
        }
        if (null != startTime) {
            query.and(TABLE.START_TIME.ge(startTime));
        }
        if (null != endTime) {
            query.and(TABLE.END_TIME.le(endTime));
        }
        if (null != status) {
            andStatus(query, status);
        }
    }

    /**
     * 状态转换
     */
    private void andStatus(SelectConditionStep<Record14<Integer, String, Timestamp, Timestamp, Timestamp, Timestamp,
        Byte, Timestamp, Timestamp, Integer, Integer, Integer, Integer, Serializable>> query, Byte status) {
        Timestamp now = Util.currentTimeStamp();
        switch (status) {
            case NOT_STARTED:
                query.and(TABLE.PRE_START_TIME.gt(now));
                break;
            case ONGOING:
                query.and(TABLE.PRE_START_TIME.le(now).and(TABLE.PRE_END_TIME.gt(now)))
                    .or(TABLE.START_TIME.ge(now).and(TABLE.END_TIME.gt(now)))
                    .or(TABLE.PRE_START_TIME_2.le(now).and(TABLE.PRE_END_TIME_2.gt(now)));
                break;
            case EXPIRED:
                query.and(TABLE.PRE_END_TIME.le(now).and(TABLE.PRE_START_TIME_2.gt(now))
                    .and(TABLE.PRE_END_TIME_2.le(now).and(TABLE.START_TIME.gt(now))))
                    .or(TABLE.PRE_END_TIME.le(now).and(TABLE.START_TIME.gt(now)).or(TABLE.END_TIME.gt(now)));
                break;
            case DISABLED:
                break;
            default:
                throw new IllegalArgumentException("Unexpected status: " + status);
        }
    }

    /**
     * 添加活动
     */
    public void addPreSale(PreSaleParam param) {
        validateParam(param);
        db().transaction(config -> {
            DSLContext db = DSL.using(config);
            this.insertPresale(db, param);
        });
    }

    /**
     * 保存活动
     */
    private void insertPresale(DSLContext db, PreSaleParam param) {
        PresaleRecord presale = db.newRecord(TABLE, param);
        String config = shareConfigJson(param);
        presale.setShareConfig(config);
        presale.insert();
        Integer id = presale.getId();
        this.insertPresaleProduct(db, param, id);
    }

    /**
     * 保存活动产品
     */
    private void insertPresaleProduct(DSLContext db, PreSaleParam param, Integer presaleId) {
        List<ProductParam> products = param.getProducts();
        List<PresaleProductRecord> productRecords =
            products.stream().map(product -> {
                product.setPresaleId(presaleId);
                return db.newRecord(SUB_TABLE, product);
            }).collect(Collectors.toList());
        db.batchInsert(productRecords).execute();
    }

    /**
     * 获取分享配置 json
     */
    private String shareConfigJson(PreSaleParam param) {
        ShareConfig config = createShareConfig(param);
        return Util.underLineStyleGson().toJson(config);
    }

    /**
     * 分享配置
     */
    private ShareConfig createShareConfig(PreSaleParam param) {
        ShareConfig shareConfig = new ShareConfig();
        shareConfig.setShareAction(param.getShareType());
        shareConfig.setShareDoc(param.getShareText());
        shareConfig.setShareImgAction(param.getShareImgType());
        shareConfig.setShareImg(param.getShareImg());
        return shareConfig;
    }

    /**
     * 校验活动商品参数
     */
    private void validateParam(PreSaleParam param) {
        validateTime(param);
        assertTimeNoConflict(param);
        validateDeliverTime(param);
        param.getProducts().forEach(product -> this.validateProduct(product, param));
    }

    /**
     * 校验支付时间
     */
    private void validateTime(PreSaleParam param) {
        byte prePayStep = param.getPrePayStep();
        switch (prePayStep) {
            case 1:
                if (param.getPreStartTime().after(param.getPreEndTime())) {
                    throw new IllegalArgumentException("PreEndTime earlier than preStartTime");
                }
                break;
            case 2:
                Assert.notNull(param.getPreStartTime2(), "Missing parameter preStartTime2");
                Assert.notNull(param.getPreEndTime2(), "Missing parameter preEndTime2");
                if (param.getPreStartTime2().after(param.getPreEndTime2())) {
                    throw new IllegalArgumentException("PreEndTime2 earlier than preStartTime2");
                }
                if (param.getPreStartTime2().before(param.getPreEndTime())) {
                    throw new IllegalArgumentException("PreEndTime2 earlier than preEndTime");
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected prePayStep: " + prePayStep);
        }
    }

    /**
     * 判断活动时间是否冲突
     */
    private void assertTimeNoConflict(PreSaleParam param) {
        Tuple2<Timestamp, Timestamp> timePair = new Tuple2<>(param.getPreStartTime(), param.getPreEndTime());
        Tuple2<Timestamp, Timestamp> timePair2 = new Tuple2<>(param.getPreStartTime2(), param.getPreEndTime2());
        List<Tuple2<Timestamp, Timestamp>> timePairs = Arrays.asList(timePair, timePair2);
        assertTimeNoConflict(timePairs, param.getId());
    }

    /**
     * 判断活动时间段是否冲突
     */
    private void assertTimeNoConflict(List<Tuple2<Timestamp, Timestamp>> timePairs, Integer id) {
        Condition statusCondition = TABLE.DEL_FLAG.eq((byte) 0).and(TABLE.STATUS.eq((byte) 1));
        Condition timeCondition = DSL.or();
        for (Tuple2<Timestamp, Timestamp> timePair : timePairs) {
            timeCondition =
                timeCondition.or(TABLE.PRE_START_TIME.ge(timePair.v1()).and(TABLE.PRE_END_TIME.le(timePair.v2())))
                    .or(TABLE.PRE_START_TIME_2.ge(timePair.v1()).and(TABLE.PRE_END_TIME_2.le(timePair.v2())));
        }
        SelectConditionStep<Record1<Integer>> query =
            select(TABLE.ID).from(TABLE).where(statusCondition.and(timeCondition));
        if (null != id) {
            query.and(TABLE.ID.ne(id));
        }
        if (db().fetchExists(query)) {
            throw new IllegalArgumentException(ACTIVITY_TIME_RANGE_CONFLICT);
        }
    }

    /**
     * 校验发货时间
     */
    private void validateDeliverTime(PreSaleParam param) {
        byte deliverType = param.getDeliverType();
        switch (deliverType) {
            case DELIVER_SPECIFIC:
                Assert.notNull(param.getDeliverTime(), "Missing parameter deliverTime");
                if (param.getDeliverTime().before(param.getEndTime())) {
                    throw new IllegalArgumentException("DeliverTime earlier than endTime");
                }
                break;
            case DELIVER_POSTPONE:
                Assert.notNull(param.getDeliverDays(), "Missing parameter deliverDays");
                break;
            default:
                throw new IllegalArgumentException("Unexpected deliverType: " + deliverType);
        }
    }

    /**
     * 校验活动产品参数
     */
    private void validateProduct(ProductParam product, PreSaleParam param) {
        Byte prePayStep = param.getPrePayStep();
        int twoSteps = 2;
        if (twoSteps == prePayStep) {
            Assert.notNull(product.getPreDiscountMoney2(), "Missing parameter preDiscountMoney2");
        }
        Double presalePrice = product.getPresalePrice();
        Double presaleMoney = product.getPresaleMoney();
        Double preDiscountMoney1 = product.getPreDiscountMoney1();
        Double preDiscountMoney2 = product.getPreDiscountMoney2();
        if (preDiscountMoney1 < presaleMoney || preDiscountMoney1 > presalePrice) {
            throwMoneyException();
        }
        if (null != preDiscountMoney2) {
            boolean illegalPresaleMoney = preDiscountMoney2 < presaleMoney || preDiscountMoney2 > presalePrice;
            if (illegalPresaleMoney) {
                throwMoneyException();
            }
        }
    }

    /**
     * 抵扣金额异常
     */
    private void throwMoneyException() {
        throw new IllegalArgumentException("Discount money error");
    }

    /**
     * 删除活动
     */
    public void deletePreSale(Integer id) {
        db().update(TABLE).set(TABLE.DEL_FLAG, (byte) 1).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 停用活动
     */
    public void disablePreSale(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, (byte) 0).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 启用活动
     */
    public void enablePreSale(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, (byte) 1).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 获取活动明细
     */
    public PreSaleVo getDetail(Integer preSaleId) {
        PreSaleVo preSaleVo =
            db().select(TABLE.ID, TABLE.PRE_START_TIME,
                TABLE.PRE_END_TIME, TABLE.START_TIME, TABLE.END_TIME, TABLE.PRESALE_NAME, TABLE.BUY_NUMBER,
                TABLE.BUY_TYPE, TABLE.DELIVER_DAYS, TABLE.DELIVER_TIME, TABLE.DELIVER_TYPE, TABLE.GOODS_ID,
                TABLE.DISCOUNT_TYPE, TABLE.PRE_PAY_STEP, TABLE.PRESALE_TYPE, TABLE.RETURN_TYPE, TABLE.SHARE_CONFIG,
                TABLE.SHOW_SALE_NUMBER, TABLE.STATUS, GOODS.GOODS_NAME)
                .select(TABLE.PRE_START_TIME_2.as("preStartTimeTwo"))
                .select(TABLE.PRE_END_TIME_2.as("preEndTimeTwo"))
                .from(TABLE)
                .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(TABLE.GOODS_ID))
                .where(TABLE.ID.eq(preSaleId))
                .fetchOneInto(PreSaleVo.class);
        List<ProductVo> productVos = db().select(SUB_TABLE.ID, SUB_TABLE.PRESALE_ID, SUB_TABLE.PRODUCT_ID,
            SUB_TABLE.PRESALE_MONEY, SUB_TABLE.PRESALE_NUMBER, SUB_TABLE.PRESALE_PRICE, SUB_TABLE.GOODS_ID,
            GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_DESC, GOODS_SPEC_PRODUCT.PRD_NUMBER,
            GOODS_SPEC_PRODUCT.PRD_PRICE)
            .select(SUB_TABLE.PRE_DISCOUNT_MONEY_1.as("preDiscountMoneyOne"))
            .select(SUB_TABLE.PRE_DISCOUNT_MONEY_2.as("preDiscountMoneyTwo"))
            .from(SUB_TABLE)
            .leftJoin(GOODS_SPEC_PRODUCT)
            .on(GOODS_SPEC_PRODUCT.PRD_ID.eq(SUB_TABLE.PRODUCT_ID))
            .where(SUB_TABLE.PRESALE_ID.eq(preSaleId)).fetchInto(ProductVo.class);
        preSaleVo.setProducts(productVos);
        preSaleVo.setShareConfiguration(shareConfig(preSaleVo));
        preSaleVo.setStatus(preSaleVo.getStatus());
        return preSaleVo;
    }

    /**
     * 获取分享配置
     */
    private ShareConfig shareConfig(PreSaleVo preSaleVo) {
        String shareConfig = preSaleVo.getShareConfig();
        return Util.underLineStyleGson().fromJson(shareConfig, ShareConfig.class);
    }

    /**
     * 更新活动
     */
    public void updatePreSale(PreSaleParam param) {
        Integer presaleId = param.getId();
        Assert.notNull(presaleId, "Missing parameter id");
        PreSaleListVo presale = db().selectFrom(TABLE).where(TABLE.ID.eq(presaleId)).fetchOneInto(PreSaleListVo.class);
        Byte status = getStatusOf(presale);
        String shareConfiguration = shareConfigJson(param);
        if (ONGOING == status) {
            db().update(TABLE).set(TABLE.PRESALE_NAME, param.getPresaleName())
                .set(TABLE.SHARE_CONFIG, shareConfiguration);
        } else if (NOT_STARTED == status) {
            validateParam(param);
            db().update(TABLE).set(TABLE.PRESALE_NAME, param.getPresaleName()).set(TABLE.PRE_START_TIME,
                param.getPreStartTime()).set(TABLE.PRE_END_TIME, param.getPreEndTime()).set(TABLE.PRE_START_TIME_2,
                param.getPreStartTime2()).set(TABLE.PRE_END_TIME_2, param.getPreEndTime2()).set(TABLE.PRESALE_TYPE,
                param.getPresaleType()).set(TABLE.PRE_PAY_STEP, param.getPrePayStep()).set(TABLE.START_TIME,
                param.getStartTime()).set(TABLE.END_TIME, param.getEndTime()).set(TABLE.GOODS_ID,
                param.getGoodsId()).set(TABLE.DELIVER_TYPE, param.getDeliverType()).set(TABLE.DELIVER_TIME,
                param.getDeliverTime()).set(TABLE.DELIVER_DAYS, param.getDeliverDays()).set(TABLE.DISCOUNT_TYPE,
                param.getDiscountType()).set(TABLE.RETURN_TYPE, param.getReturnType()).set(TABLE.SHOW_SALE_NUMBER,
                param.getShowSaleNumber()).set(TABLE.BUY_TYPE, param.getBuyType()).set(TABLE.BUY_NUMBER, param.getBuyNumber())
                .where(TABLE.ID.eq(presaleId))
                .execute();
            db().transaction(configuration -> {
                DSLContext db = DSL.using(configuration);
                db.delete(SUB_TABLE).where(SUB_TABLE.PRESALE_ID.eq(presaleId)).execute();
                param.getProducts().forEach(product -> {
                    product.setPresaleId(presaleId);
                    db.insertInto(SUB_TABLE)
                        .columns(SUB_TABLE.PRESALE_ID, SUB_TABLE.GOODS_ID, SUB_TABLE.PRODUCT_ID,
                            SUB_TABLE.PRESALE_PRICE,
                            SUB_TABLE.PRESALE_NUMBER, SUB_TABLE.PRESALE_MONEY, SUB_TABLE.PRE_DISCOUNT_MONEY_1,
                            SUB_TABLE.PRE_DISCOUNT_MONEY_2)
                        .values(presaleId, product.getGoodsId(), product.getProductId(),
                            BigDecimal.valueOf(product.getPresalePrice()),
                            product.getPresaleNumber(), BigDecimal.valueOf(product.getPresaleMoney()),
                            BigDecimal.valueOf(product.getPreDiscountMoney1()),
                            BigDecimal.valueOf(product.getPreDiscountMoney2()))
                        .execute();
                });
            });
        } else {
            throw new IllegalStateException("Error status");
        }
    }

    /**
     * 活动分享
     */
    public String sharePreSale(Integer presaleId) {
        Integer goodsId = db().selectFrom(TABLE).where(TABLE.ID.eq(presaleId)).fetchOne(TABLE.GOODS_ID);
        return format(SHARE_PAGE_PATH, goodsId, presaleId);
    }
}
