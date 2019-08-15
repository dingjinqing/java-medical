package com.vpu.mp.service.shop.market.presale;

import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.Presale;
import com.vpu.mp.db.shop.tables.records.PresaleProductRecord;
import com.vpu.mp.db.shop.tables.records.PresaleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleListVo;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam;
import com.vpu.mp.service.pojo.shop.market.presale.Product;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.jooq.DSLContext;
import org.jooq.Record14;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam.*;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam.DELIVER_POSTPONE;
import static com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam.DELIVER_SPECIFIC;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.GOODS_TYPE_PRE_SALE;
import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 定金膨胀活动
 *
 * @author 郑保乐
 */
@Service
public class PreSaleService extends ShopBaseService {

    private static final Presale TABLE = Presale.PRESALE;
    private static final OrderInfo ORDER = OrderInfo.ORDER_INFO;
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
                    .filterWhere(ORDER.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_TAIL)).as(TAIL_PAID_QUANTITY),
                DSL.countDistinct(ORDER.USER_ID).as(ORDER_USER_QUANTITY),
                DSL.coalesce(DSL.sum(ORDER_GOODS.GOODS_NUMBER), 0).as(BOUGHT_QUANTITY)
            )
                .from(TABLE)
                .leftJoin(ORDER)
                .on(ORDER.GOODS_TYPE.eq(String.valueOf(GOODS_TYPE_PRE_SALE)).and(ORDER.ACTIVITY_ID.eq(TABLE.ID)))
                .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_ID.eq(ORDER.ORDER_ID))
                .where(TABLE.DEL_FLAG.eq(NOT_DELETED));
        buildOptions(query, param);
        query.groupBy(TABLE.ID);
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
    private Byte getStatusOf(PreSaleListVo vo) {
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
    private void andStatus(SelectConditionStep<Record14<Integer, String, Timestamp, Timestamp, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Integer, Integer, Integer, Integer, Serializable>> query, Byte status) {
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
        presale.insert();
        Integer id = presale.getId();
        this.insertPresaleProduct(db, param, id);
    }

    /**
     * 保存活动产品
     */
    private void insertPresaleProduct(DSLContext db, PreSaleParam param, Integer presaleId) {
        List<Product> products = param.getProducts();
        List<PresaleProductRecord> productRecords = products.stream().map(product -> {
            product.setPresaleId(presaleId);
            return db.newRecord(PRESALE_PRODUCT, product);
        }).collect(Collectors.toList());
        db.batchInsert(productRecords).execute();
    }

    /**
     * 获取分享配置 json
     */
    private String shareConfigJson(PreSaleParam param) {
        return null;
    }

    /**
     * 校验活动商品参数
     */
    private void validateParam(PreSaleParam param) {
        validateTime(param);
        validateDeliverTime(param);
        param.getProducts().forEach(this::validateProduct);
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
                break;
            default:
                throw new IllegalArgumentException("Unexpected prePayStep: " + prePayStep);
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
    private void validateProduct(Product product) {
        Double presalePrice = product.getPresalePrice();
        Double presaleMoney = product.getPresaleMoney();
        Double preDiscountMoney1 = product.getPreDiscountMoney1();
        Double preDiscountMoney2 = product.getPreDiscountMoney2();
        if (preDiscountMoney1 < presaleMoney || preDiscountMoney1 > presalePrice) {
            throwMoneyException();
        }
        if (null != preDiscountMoney2 &&
            (preDiscountMoney2 < presaleMoney
                || preDiscountMoney2 > presalePrice)) {
            throwMoneyException();
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
     * todo 活动分享
     */

    /**
     * todo 导出 Excel
     */
}
