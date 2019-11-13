package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.commodity.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsLabelCouple.GOODS_LABEL_COUPLE;
import static com.vpu.mp.db.shop.tables.GoodsSummary.GOODS_SUMMARY;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.divideWithOutCheck;
import static com.vpu.mp.service.shop.overview.RealTimeOverviewService.div;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.jooq.impl.DSL.*;

/**
 * The type Commodity statistics service.
 *
 * @author liufei
 * @date 2019 /7/22
 */
@Service
public class CommodityStatisticsService extends ShopBaseService {
    /** goods_summary表默认排序字段 */
    private static final String DEFAULT_ORDER_BY_FIELD = "uv";
    /** 默认排序规则，升序asc */
    private static final String DEFAULT_ORDER_BY_TYPE = "asc";

    /**
     * The constant GOS.
     */
    public static final GoodsOverviewSummary GOS = GoodsOverviewSummary.GOODS_OVERVIEW_SUMMARY.as("GOS");

    /**
     * 查询指定时间段商品概览，例如最近一天，七天，一个月
     *
     * @param param the param
     * @return the product overview vo
     */
    public ProductOverviewVo fixedDayOverview(ProductOverviewParam param){
        byte type = param.getDynamicDate();
        LocalDate now = LocalDate.now();
        LocalDate prefix = now.minusDays(type);
        ProductOverviewVo nowData = getGoodsOverviewSummary(DateUtil.yyyyMmDdDate(now), type);
        if (Objects.isNull(nowData))
            return ProductOverviewVo.builder().build();
        nowData.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(nowData.getPaidGoodsNum()), BigDecimal.valueOf(nowData.getVisitedGoodsNum())));
        ProductOverviewVo prefixData = getGoodsOverviewSummary(DateUtil.yyyyMmDdDate(prefix), type);
        if (Objects.isNull(prefixData))
            return nowData;
        prefixData.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(prefixData.getPaidGoodsNum()), BigDecimal.valueOf(prefixData.getVisitedGoodsNum())));
        // 获取变化率数据
        nowData.setChangeRate(getChangeRateData(nowData, prefixData));
        return nowData;
    }

    /**
     * 计算统计数据变化率
     */
    private Map<String, BigDecimal> getChangeRateData(ProductOverviewVo nowData, ProductOverviewVo prefixData) {
        Map<String, BigDecimal> changeRate = new HashMap<>(16);
        // 计算变化率
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(nowData.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors).forEach((d) -> {
                // 拿到属性名称
                String fieldName = d.getName();
                Method method = d.getReadMethod();
                try {
                    Object o1 = method.invoke(nowData);
                    Object o2 = method.invoke(prefixData);
                    BigDecimal result = getRate(o1, o2);
                    changeRate.put(fieldName, result);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return changeRate;
    }

    /**
     * 计算统计数据变化率公式 (now-prefix)/prefix
     */
    private BigDecimal getRate(Object now, Object prefix) {
        if (Objects.isNull(now) || Objects.isNull(prefix))
            return BIGDECIMAL_ZERO;
        BigDecimal n = BigDecimal.valueOf(Long.parseLong(now.toString()));
        BigDecimal p = BigDecimal.valueOf(Long.parseLong(prefix.toString()));
        return n.subtract(p).divide(p, 2, RoundingMode.DOWN);
    }

    /**
     * 获取指定时间下的商品概览统计数据(查询统计表)
     */
    private ProductOverviewVo getGoodsOverviewSummary(Date date, byte type) {
        return db().selectFrom(GOS)
            .where(GOS.REF_DATE.eq(date))
            .and(GOS.TYPE.eq(type))
            .fetchOneInto(ProductOverviewVo.class);
    }

    /**
     * 查询自定义时间段商品概览  @param param the param
     *
     * @param param the param
     * @return the product overview vo
     */
    public ProductOverviewVo customizeDayOverview(ProductOverviewParam param){
        Date date;
        LocalDate start = param.getStartTime().toLocalDate();
        LocalDate end = param.getEndTime().toLocalDate();
        long days = end.toEpochDay() - start.toEpochDay();
        LocalDate prefixStart = start.minusDays(days);

        ProductOverviewVo data = getCustomizeGoodsOverviewSummary(start, end);
        if (Objects.isNull(data))
            return ProductOverviewVo.builder().build();
        data.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(data.getPaidGoodsNum()), BigDecimal.valueOf(data.getVisitedGoodsNum())));
        ProductOverviewVo prefixData = getCustomizeGoodsOverviewSummary(prefixStart, start);
        if (Objects.isNull(prefixData))
            return data;
        data.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(data.getPaidGoodsNum()), BigDecimal.valueOf(data.getVisitedGoodsNum())));
        // 获取变化率数据
        data.setChangeRate(getChangeRateData(data, prefixData));
        return data;
    }

    /**
     * 获取自定义时间下的商品概览统计数据(查询统计表)
     */
    private ProductOverviewVo getCustomizeGoodsOverviewSummary(LocalDate start, LocalDate end) {
        return db().select(
            sum(GOS.ON_SHELF_GOODS_NUM).as("onShelfGoodsNum")
            , sum(GOS.SOLD_GOODS_NUM).as("soldGoodsNum")
            , sum(GOS.VISITED_GOODS_NUM).as("visitedGoodsNum")
            , sum(GOS.GOODS_USER_VISIT).as("goodsUserVisit")
            , sum(GOS.GOODS_PAGEVIEWS).as("goodsPageviews")
            , sum(GOS.PURCHASE_NUM).as("purchaseNum")
            , sum(GOS.PURCHASE_QUANTITY).as("purchaseQuantity")
            , sum(GOS.PAID_GOODS_NUM).as("paidGoodsNum")
            , sum(GOS.ORDER_GOODS_NUM).as("orderGoodsNum"))
            .from(GOS)
            .where(GOS.REF_DATE.ge(Date.valueOf(start)))
            .and(GOS.REF_DATE.lessThan(Date.valueOf(end)))
            .and(GOS.TYPE.eq(BYTE_ONE))
            .fetchOneInto(ProductOverviewVo.class);
    }

    /**
     * 条件查询商品概览  @param param the param
     *
     * @param param the param
     * @return the product overview vo
     */
    public ProductOverviewVo conditionOverview(ProductOverviewParam param){
        ProductOverviewVo vo = new ProductOverviewVo();
        vo.setVisitedGoodsNum(getGoodsNumByVisit(param));
        vo.setOnShelfGoodsNum(getSaleGoodsNumber(param));
/*        vo.setVisit2paid(div(getGoodsNumByVisit(param),paidGoodsNum(param)));
        vo.setCartGoodsNumber(getAddCartGoodsNumber(param));
        vo.setCartUsernumber(addCartUserNum(param));
        vo.setGoodsUserVisit(getGoodsUv(param));
        vo.setGoodsVisit(getGoodsPv(param));
        vo.setPaidGoodsNumber(paidGoodsNum(param));*/
        return vo;
    }
    /** 在架商品数 */
    private int getSaleGoodsNumber(ProductOverviewParam param){
        GoodsBak gb = GoodsBak.GOODS_BAK.as("gb");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 基本筛选条件 */
        Condition baseCondition = gb.BAK_DATE.greaterOrEqual(new Date(param.getStartTime().getTime()))
                .and(gb.BAK_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(gb.GOODS_NUMBER.greaterThan(0))
                .and(gb.IS_ON_SALE.eq((byte)1));
        /** 品牌条件 */
        Condition brandCondition = gb.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = gb.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }

        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        if (param.getLabelId() > 0){
            joinStep = db().select(countDistinct(gb.GOODS_ID)).from(gb).leftJoin(glc).on(gb.GOODS_ID.eq(glc.GTA_ID));
            conditionStep = joinStep.where(labelCondtion);
        }else{
            conditionStep = db().select(countDistinct(gb.GOODS_ID)).from(gb).where(baseCondition);
        }
        Optional<Integer> goodsIdNumber = conditionStep.fetchOptionalInto(Integer.class);
        return goodsIdNumber.orElse(0);
    }
    /** 被访问商品数 */
    private int getGoodsNumByVisit(ProductOverviewParam param){
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        /** 基本筛选条件 */
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        /** 品牌条件 */
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ugr.GOODS_ID)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            baseCondition = baseCondition.and(labelCondtion);
        }

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsIdVisit = conditionStep.fetchOptionalInto(Integer.class);
        return goodsIdVisit.orElse(0);
    }
    /** 商品UV数 */
    private int getGoodsUv(ProductOverviewParam param){
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        /** 基本筛选条件 */
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        /** 品牌条件 */
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ugr.USER_ID)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            baseCondition = baseCondition.and(labelCondtion);
        }

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsUv = conditionStep.fetchOptionalInto(Integer.class);
        return goodsUv.orElse(0);
    }
    /** 商品PV数 */
    private int getGoodsPv(ProductOverviewParam param) {
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        /** 基本筛选条件 */
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        /** 品牌条件 */
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(sum(ugr.COUNT)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            baseCondition = baseCondition.and(labelCondtion);
        }

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsPv = conditionStep.fetchOptionalInto(Integer.class);
        return goodsPv.orElse(0);
    }
    /** 加购人数 */
    private int addCartUserNum(ProductOverviewParam param){
        UserCartRecord ucr = UserCartRecord.USER_CART_RECORD.as("ucr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        /** 基本筛选条件 */
        Condition baseCondition = ucr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ucr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        /** 品牌条件 */
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ucr.USER_ID)).from(ucr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ucr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ucr.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            baseCondition = baseCondition.and(labelCondtion);
        }

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> addCardUserNum = conditionStep.fetchOptionalInto(Integer.class);
        return addCardUserNum.orElse(0);
    }
    /** 加购件数 */
    private int getAddCartGoodsNumber(ProductOverviewParam param) {
        UserCartRecord ucr = UserCartRecord.USER_CART_RECORD.as("ucr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectJoinStep<?> joinStep;/** 构造连表条件，不同的条件连接不同的表 */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */

        /** 基本筛选条件 */
        Condition baseCondition = ucr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ucr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        /** 品牌条件 */
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        /** 商家分类条件 */
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(sum(ucr.NUM)).from(ucr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ucr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ucr.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            baseCondition = baseCondition.and(labelCondtion);
        }

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> addCartGoodsNum = conditionStep.fetchOptionalInto(Integer.class);
        return addCartGoodsNum.orElse(0);
    }
    /** 付款商品数 */
    private int paidGoodsNum(ProductOverviewParam param){
        Goods g = Goods.GOODS.as("g");
        OrderGoods og = OrderGoods.ORDER_GOODS.as("og");
        OrderInfo oi = OrderInfo.ORDER_INFO.as("oi");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");

        Condition ogConditon = og.CREATE_TIME.greaterOrEqual(Util.getEarlyTimeStamp(param.getStartTime(),0)).and(og.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(param.getStartTime(),0)));
        Condition oiCondition = oi.ORDER_STATUS.greaterOrEqual((byte)3);
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        Condition labelCondtion = glc.ID.eq(param.getLabelId()).and(glc.TYPE.eq((byte)1));

        SelectJoinStep<Record1<BigDecimal>> joinStep = db().select(sum(og.GOODS_NUMBER)).from(og).leftJoin(oi).on(og.ORDER_SN.eq(oi.ORDER_SN));
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(og.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(og.GOODS_ID));
        }
        if (param.getBrandId() > 0) {
            ogConditon = ogConditon.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            ogConditon = ogConditon.and(sortCondition);
        }
        if (param.getLabelId() > 0) {
            ogConditon = ogConditon.and(labelCondtion);
        }
        return joinStep.where(ogConditon.and(oiCondition)).fetchOptionalInto(Integer.class).orElse(0);
    }


    /**
     * 查询指定时间段商品效果，例如最近一天，七天，一个月  @param param the param
     *
     * @param param the param
     * @return the page result
     */
    public PageResult<ProductEffectVo> fixedDayEffect(ProductEffectParam param){
        /** 必要筛选条件 */
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.eq(new Date(Util.getEarlyTimeStamp(new java.util.Date(),-1).getTime())))
                .and(GOODS_SUMMARY.TYPE.eq(param.getDynamicDate()));
        SelectJoinStep<?> joinStep = db().select(GOODS_SUMMARY.GOODS_ID
                ,GOODS.GOODS_NAME
                ,GOODS.GOODS_IMG
                ,GOODS.SHOP_PRICE
                ,GOODS_SUMMARY.NEW_USER_NUMBER
                ,GOODS_SUMMARY.OLD_USER_NUMBER
                ,GOODS_SUMMARY.PV
                ,GOODS_SUMMARY.UV
                ,GOODS_SUMMARY.CART_UV
                ,GOODS_SUMMARY.PAID_UV
                ,GOODS_SUMMARY.PAID_GOODS_NUMBER)
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID));

        PageResult<ProductEffectVo> pageResult = this.getPageResult(createEffectSelect(param,joinStep,baseCondition,true),param.getCurrentPage(), param.getPageRows(),ProductEffectVo.class);

        for(ProductEffectVo vo : pageResult.dataList) {
            vo.setNewUserPercentage(div(vo.getPaidUv(),vo.getNewUserNumber()));
            vo.setOldUserPercentage(div(vo.getPaidUv(),vo.getOldUserNumber()));
            vo.setUv2paidGoods(div(vo.getUv(),vo.getPaidGoodsNumber()));
        }
        return pageResult;
    }

    private SortField<Object> getSortField(Optional<String> field, Optional<String> sortType){
        if(!sortType.isPresent() || DEFAULT_ORDER_BY_TYPE.equalsIgnoreCase(sortType.get())) {
            return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).asc();
        }
        return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).desc();
    }

    /**
     * 查询自定义时间段商品效果  @param param the param
     *
     * @param param the param
     * @return the page result
     */
    public PageResult<ProductEffectVo> customizeDayEffect(ProductEffectParam param){
        /** 必要筛选条件 */
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.greaterOrEqual(new Date(param.getStartTime().getTime())))
                .and(GOODS_SUMMARY.REF_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(GOODS_SUMMARY.TYPE.eq((byte)1));
        SelectJoinStep<?> joinStep = db().select(max(GOODS_SUMMARY.GOODS_ID).as("goodsId")
                ,max(GOODS.GOODS_NAME).as("goodsName")
                ,max(GOODS.GOODS_IMG).as("goodsImg")
                ,max(GOODS.SHOP_PRICE).as("shopPrice")
                ,sum(GOODS_SUMMARY.NEW_USER_NUMBER).as("newUserNumber")
                ,sum(GOODS_SUMMARY.OLD_USER_NUMBER).as("oldUserNumber")
                ,sum(GOODS_SUMMARY.PV).as("pv")
                ,sum(GOODS_SUMMARY.UV).as("uv")
                ,sum(GOODS_SUMMARY.CART_UV).as("cartUv")
                ,sum(GOODS_SUMMARY.PAID_UV).as("paidUv")
                ,sum(GOODS_SUMMARY.PAID_GOODS_NUMBER).as("paidGoodsNumber"))
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID));
        PageResult<ProductEffectVo> pageResult = this.getPageResult(createEffectSelect(param,joinStep,baseCondition,false),param.getCurrentPage(), param.getPageRows(),ProductEffectVo.class);
        for(ProductEffectVo vo : pageResult.dataList) {
            vo.setNewUserPercentage(div(vo.getPaidUv(),vo.getNewUserNumber()));
            vo.setOldUserPercentage(div(vo.getPaidUv(),vo.getOldUserNumber()));
            vo.setUv2paidGoods(div(vo.getUv(),vo.getPaidGoodsNumber()));
            vo.setRecommendUserNum(recommendUserNum(param,vo.getGoodsId()));
            vo.setCollectUserNum(collectUserNum(param,vo.getGoodsId()));
        }
        return pageResult;
    }

    /**
     * 推荐用户数
     */
    private int recommendUserNum(ProductEffectParam param,int goodsId){
        WxShoppingRecommend wsr = WxShoppingRecommend.WX_SHOPPING_RECOMMEND.as("wsr");
        return db().select(count(wsr.USER_ID)).from(wsr).where(wsr.CREATE_TIME.greaterOrEqual(Util.getEarlyTimeStamp(param.getStartTime(),0)))
        .and(wsr.CREATE_TIME.lessOrEqual(Util.getEarlyTimeStamp(param.getEndTime(),0)))
        .and(wsr.GOODS_ID.eq(goodsId)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 商品收藏人数
     */
    private int collectUserNum(ProductEffectParam param,int goodsId){
        UserCollection uc = UserCollection.USER_COLLECTION.as("uc");
        return db().select(countDistinct(uc.USER_ID)).from(uc).where(uc.CREATE_TIME.greaterOrEqual(Util.getEarlyTimeStamp(param.getStartTime(),0)))
        .and(uc.CREATE_TIME.lessOrEqual(Util.getEarlyTimeStamp(param.getEndTime(),0)))
            .and(uc.GOODS_ID.eq(goodsId)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 创建商品效果查询select
     *
     * @param param         the param
     * @param joinStep      the join step
     * @param baseCondition the base condition
     * @param isFixedDay    true为指定时间，false为自定义时间
     * @return select limit step
     */
    public SelectLimitStep<?> createEffectSelect(ProductEffectParam param,SelectJoinStep<?> joinStep,Condition baseCondition,boolean isFixedDay){
        /** 按照goods_id分组求和各个字段的值 */

        Optional<String> field = Optional.ofNullable(param.getOrderByField());
        Optional<String> sortType = Optional.ofNullable(param.getOrderByType());
        /** 动态排序字段，规则 */
        SortField<?> sortField = getSortField(field,sortType);

        /** 查询筛选条件，商品品牌，商家分类 */
        Condition brandCondition = GOODS.BRAND_ID.eq(param.getBrandId());
        Condition sortCondition = GOODS.SORT_ID.eq(param.getSortId());

        /** 标签条件 */
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }

        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */
        SelectLimitStep<?> limitStep;/** 完善筛选条件 */

        if (param.getLabelId() > 0){
            conditionStep = joinStep.leftJoin(GOODS_LABEL_COUPLE)
                    .on(GOODS_LABEL_COUPLE.GTA_ID.eq(GOODS_SUMMARY.GOODS_ID))
                    .where(labelCondtion);
        }else{
            conditionStep = joinStep.where();
        }
        limitStep = isFixedDay ? conditionStep.and(baseCondition).orderBy(sortField) : conditionStep.and(baseCondition).groupBy(GOODS_SUMMARY.GOODS_ID).orderBy(sortField);
        return limitStep;
    }

    /**
     * Export 2 excel workbook.
     *
     * @param param the param
     * @return the workbook
     */
    public Workbook export2Excel(ProductEffectParam param){
        SelectLimitStep<?> limitStep = createEffectSelect(param,db().select(GOODS_SUMMARY.GOODS_ID
            ,GOODS.GOODS_NAME
            ,GOODS.GOODS_IMG
            ,GOODS.SHOP_PRICE
            ,GOODS_SUMMARY.NEW_USER_NUMBER
            ,GOODS_SUMMARY.OLD_USER_NUMBER
            ,GOODS_SUMMARY.PV
            ,GOODS_SUMMARY.UV
            ,GOODS_SUMMARY.CART_UV
            ,GOODS_SUMMARY.PAID_UV
            ,GOODS_SUMMARY.PAID_GOODS_NUMBER)
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID))
            ,(GOODS_SUMMARY.REF_DATE.eq(new Date(Util.getEarlyTimeStamp(new java.util.Date(),-1).getTime())))
                .and(GOODS_SUMMARY.TYPE.eq(param.getDynamicDate())),true);
        if(param.getDynamicDate() <= 0) {
            /** 自定义时间 */
            limitStep = createEffectSelect(param, db().select(max(GOODS_SUMMARY.GOODS_ID).as("goodsId")
                , max(GOODS.GOODS_NAME).as("goodsName")
                , max(GOODS.GOODS_IMG).as("goodsImg")
                , max(GOODS.SHOP_PRICE).as("shopPrice")
                , sum(GOODS_SUMMARY.NEW_USER_NUMBER).as("newUserNumber")
                , sum(GOODS_SUMMARY.OLD_USER_NUMBER).as("oldUserNumber")
                , sum(GOODS_SUMMARY.PV).as("pv")
                , sum(GOODS_SUMMARY.UV).as("uv")
                , sum(GOODS_SUMMARY.CART_UV).as("cartUv")
                , sum(GOODS_SUMMARY.PAID_UV).as("paidUv")
                , sum(GOODS_SUMMARY.PAID_GOODS_NUMBER).as("paidGoodsNumber"))
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID)), (GOODS_SUMMARY.REF_DATE.greaterOrEqual(new Date(param.getStartTime().getTime())))
                .and(GOODS_SUMMARY.REF_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(GOODS_SUMMARY.TYPE.eq((byte) 1)),false);

        }
        List<ProductEffectExportVo> exportVos =  limitStep.fetchInto(ProductEffectExportVo.class);
        for (ProductEffectExportVo vo : exportVos){
            vo.setGoodsInfo(vo.getGoodsName() + "  " + vo.getShopPrice());
        }
        Workbook workbook=ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(exportVos,ProductEffectExportVo.class);
        return workbook;
    }
}
