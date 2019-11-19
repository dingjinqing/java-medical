package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.GoodsOverviewSummaryRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductOverviewParam;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_THREE;
import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.jooq.impl.DSL.*;

/**
 * The type Goods task service.
 *
 * @author liufei
 * @date 11 /18/19 商品统计定时任务统计信息
 */
@Service
public class GoodsTaskService extends ShopBaseService {
    private static final GoodsBak BAK = GoodsBak.GOODS_BAK.as("BAK");
    private static final GoodsLabelCouple LABEL = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("LABEL");
    private static final UserGoodsRecord USER_GR = UserGoodsRecord.USER_GOODS_RECORD.as("USER_GR");
    private static final UserCartRecord CART = UserCartRecord.USER_CART_RECORD.as("CART");
    private static final OrderGoods ORDER_G = OrderGoods.ORDER_GOODS.as("ORDER_G");
    private static final OrderInfo ORDER_I = OrderInfo.ORDER_INFO.as("ORDER_I");

    private Condition conditionBuilder(ProductOverviewParam param) {
        Condition extCondition = DSL.trueCondition();
        if (param.getBrandId() > 0) {
            // 品牌条件
            extCondition = extCondition.and(BAK.BRAND_ID.eq(param.getBrandId()));
        }
        if (param.getBrandId() > 0) {
            // 商家分类条件
            extCondition = extCondition.and(BAK.SORT_ID.eq(param.getSortId()));
        }
        if (param.getBrandId() > 0) {
            // 标签条件
            extCondition = extCondition.and(LABEL.LABEL_ID.eq(param.getLabelId()).and(LABEL.TYPE.eq(BYTE_ONE)));
        }
        return extCondition;
    }

    /**
     * 在架商品数
     *
     * @param param the param
     * @return the int
     */
    public int getSaleGoodsNumber(ProductOverviewParam param) {
        // 基本筛选条件 : 备份时间当天, 商品数量大于0, 在架状态 TODO 备份时间不确定
        Condition baseCondition = BAK.BAK_DATE.greaterThan(param.getStartTime())
            .and(BAK.BAK_DATE.le(param.getEndTime()))
            .and(BAK.GOODS_NUMBER.greaterThan(INTEGER_ZERO))
            .and(BAK.IS_ON_SALE.eq(BYTE_ONE));

        if (param.getLabelId() > 0) {
            return db().select(countDistinct(BAK.GOODS_ID)).from(BAK)
                .leftJoin(LABEL).on(BAK.GOODS_ID.eq(LABEL.GTA_ID))
                .where(baseCondition).and(conditionBuilder(param))
                .fetchOptionalInto(Integer.class).orElse(INTEGER_ZERO);
        } else {
            return db().select(countDistinct(BAK.GOODS_ID))
                .from(BAK).where(baseCondition).and(conditionBuilder(param))
                .fetchOptionalInto(Integer.class).orElse(INTEGER_ZERO);
        }
    }

    /**
     * Common builder int.
     * 被访问商品数, 商品UV数, 商品PV数 公共查询条件
     *
     * @param param the param
     * @param field the field
     * @return the int
     */
    private int commonBuilder(ProductOverviewParam param, Field<?> field) {
        // 基本筛选条件
        Condition baseCondition = USER_GR.CREATE_TIME.greaterOrEqual(Timestamp.valueOf(param.getStartTime().toLocalDate().atStartOfDay()))
            .and(USER_GR.CREATE_TIME.lessThan(Timestamp.valueOf(param.getEndTime().toLocalDate().atStartOfDay())));
        SelectJoinStep<?> joinStep = db().select(field).from(USER_GR);
        return selectBuilder(param, joinStep, USER_GR.GOODS_ID, baseCondition);
    }

    /**
     * 被访问商品数
     *
     * @param param the param
     * @return the int
     */
    public int getGoodsNumByVisit(ProductOverviewParam param) {
        return commonBuilder(param, countDistinct(USER_GR.GOODS_ID));
    }

    /**
     * 商品UV数
     *
     * @param param the param
     * @return the int
     */
    public int getGoodsUv(ProductOverviewParam param) {
        return commonBuilder(param, countDistinct(USER_GR.USER_ID));
    }

    /**
     * 商品PV数
     *
     * @param param the param
     * @return the goods pv
     */
    public int getGoodsPv(ProductOverviewParam param) {
        return commonBuilder(param, sum(USER_GR.COUNT));
    }

    /**
     * 加购人数
     *
     * @param param the param
     * @return the int
     */
    public int addCartUserNum(ProductOverviewParam param) {
        return commonBuilder1(param, countDistinct(CART.USER_ID));
    }

    /**
     * 加购件数
     *
     * @param param the param
     * @return the add cart goods number
     */
    public int getAddCartGoodsNumber(ProductOverviewParam param) {
        return commonBuilder1(param, sum(CART.NUM));
    }

    /**
     * Common builder 1 int.
     * 加购人数, 加购件数 公共查询条件
     *
     * @param param the param
     * @param field the field
     * @return the int
     */
    private int commonBuilder1(ProductOverviewParam param, Field<?> field) {
        // 基本筛选条件
        Condition baseCondition = CART.CREATE_TIME.greaterOrEqual(Timestamp.valueOf(param.getStartTime().toLocalDate().atStartOfDay()))
            .and(CART.CREATE_TIME.lessThan(Timestamp.valueOf(param.getEndTime().toLocalDate().atStartOfDay())));
        SelectJoinStep<?> joinStep = db().select(field).from(CART);
        return selectBuilder(param, joinStep, CART.GOODS_ID, baseCondition);
    }

    private int selectBuilder(ProductOverviewParam param, SelectJoinStep<?> joinStep, Field<Integer> field, Condition baseCondition) {
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(BAK).on(field.eq(BAK.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(LABEL).on(LABEL.GTA_ID.eq(field));
        }
        return joinStep.where(baseCondition).and(conditionBuilder(param)).fetchOptionalInto(Integer.class).orElse(INTEGER_ZERO);
    }

    /**
     * 付款商品数
     *
     * @param param the param
     * @return the int
     */
    public int paidGoodsNum(ProductOverviewParam param) {
        return commonBuilder2(param, sum(ORDER_G.GOODS_NUMBER), EXT_CONDITION);
    }

    /**
     * 下单商品数
     *
     * @param param the param
     * @return the int
     */
    public int getPayOrderGoodsNum(ProductOverviewParam param) {
        return commonBuilder2(param, sum(ORDER_G.GOODS_NUMBER), TRUE_CONDITION);
    }

    /**
     * 动销商品数(统计时间内，销量不为0的商品数量)
     *
     * @param param the param
     * @return the int
     */
    public int getDySoldGoodsNum(ProductOverviewParam param) {
        return commonBuilder2(param, count(ORDER_G.GOODS_ID), EXT_CONDITION);
    }

    /**
     * The constant EXT_CONDITION.
     */
    private static final Condition EXT_CONDITION = ORDER_I.ORDER_STATUS.greaterOrEqual(CONDITION_THREE);
    /**
     * The constant TRUE_CONDITION.
     */
    private static final Condition TRUE_CONDITION = DSL.trueCondition();

    /**
     * Common builder 2 int.
     * 动销商品数, 下单商品数, 付款商品数 公共查询条件(排除货到付款非发货)
     *
     * @param param        the param
     * @param field        the field
     * @param extCondition the ext condition
     * @return the int
     */
    private int commonBuilder2(ProductOverviewParam param, Field<?> field, Condition extCondition) {
        Condition baseConditon = ORDER_I.CREATE_TIME.greaterOrEqual(Timestamp.valueOf(param.getStartTime().toLocalDate().atStartOfDay()))
            .and(ORDER_I.CREATE_TIME.lessThan(Timestamp.valueOf(param.getEndTime().toLocalDate().atStartOfDay())))
            .and(ORDER_I.IS_COD.eq(BYTE_ZERO).or(ORDER_I.IS_COD.eq(BYTE_ONE).and(ORDER_I.SHIPPING_TIME.isNotNull())))
            .and(ORDER_I.ORDER_SN.notEqual(ORDER_I.MAIN_ORDER_SN))
            .and(extCondition);
        SelectJoinStep<?> joinStep = db().select(field).from(ORDER_G)
            .leftJoin(ORDER_I).on(ORDER_G.ORDER_SN.eq(ORDER_I.ORDER_SN));
        return selectBuilder(param, joinStep, ORDER_G.GOODS_ID, baseConditon);
    }

    private static final List<Byte> TYPE_LIST = new ArrayList<Byte>() {{
        add(Byte.valueOf("1"));
        add(Byte.valueOf("7"));
        add(Byte.valueOf("30"));
    }};

    /**
     * Insert overview.
     * 往表b2c_goods_overview_summary中插入统计数据(昨天, 前一星期, 前一个月)
     */
    public void insertOverview() {
        TYPE_LIST.forEach((e) -> db().executeInsert(createOverviewRecord(new ProductOverviewParam() {{
            setDynamicDate(e);
            setStartTime(Date.valueOf(LocalDate.now().minusDays(e)));
            setEndTime(Date.valueOf(LocalDate.now()));
        }})));
    }

    private GoodsOverviewSummaryRecord createOverviewRecord(ProductOverviewParam param) {
        return new GoodsOverviewSummaryRecord() {{
            setRefDate(DateUtil.yyyyMmDdDate(LocalDate.now()));
            setType(param.getDynamicDate());
            setOnShelfGoodsNum(getSaleGoodsNumber(param));
            setSoldGoodsNum(getDySoldGoodsNum(param));
            setVisitedGoodsNum(getGoodsNumByVisit(param));
            setGoodsUserVisit(getGoodsUv(param));
            setGoodsPageviews(getGoodsPv(param));
            setPurchaseNum(addCartUserNum(param));
            setPurchaseQuantity(getAddCartGoodsNumber(param));
            setPaidGoodsNum(paidGoodsNum(param));
            setOrderGoodsNum(getPayOrderGoodsNum(param));
        }};
    }
}
