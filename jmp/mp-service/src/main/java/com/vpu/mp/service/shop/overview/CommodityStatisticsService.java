package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.GoodsOverviewSummary;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.commodity.*;
import com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsLabelCouple.GOODS_LABEL_COUPLE;
import static com.vpu.mp.db.shop.tables.GoodsSummary.GOODS_SUMMARY;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.divideWithOutCheck;
import static com.vpu.mp.service.pojo.shop.config.trade.TradeConstant.FIELD_CLAZZ;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.STRING_ZERO;
import static com.vpu.mp.service.pojo.shop.overview.commodity.CharConstant.*;
import static com.vpu.mp.service.shop.store.store.StoreWxService.BYTE_TWO;
import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.min;
import static org.jooq.impl.DSL.*;

/**
 * The type Commodity statistics service.
 *
 * @author liufei
 * @date 2019 /7/22
 */
@Slf4j
@Service
public class CommodityStatisticsService extends ShopBaseService {
    @Autowired
    private GoodsStatisticTaskService goodsStatisticTaskService;

    /**
     * goods_summary表默认排序字段
     */
    private static final String DEFAULT_ORDER_BY_FIELD = "uv";
    /**
     * 默认排序规则，升序asc
     */
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
    public ProductOverviewVo fixedDayOverview(ProductOverviewParam param) {
        byte type = param.getDynamicDate();
        LocalDate now = LocalDate.now();
        LocalDate prefix = now.minusDays(type);
        ProductOverviewVo nowData = getGoodsOverviewSummary(DateUtil.yyyyMmDdDate(now), type);
        if (Objects.isNull(nowData)) {
            return ProductOverviewVo.builder().build();
        }
        nowData.setStartTime(Date.valueOf(prefix));
        nowData.setEndTime(Date.valueOf(now));
        nowData.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(nowData.getPaidGoodsNum()), BigDecimal.valueOf(nowData.getVisitedGoodsNum())));
        ProductOverviewVo prefixData = getGoodsOverviewSummary(DateUtil.yyyyMmDdDate(prefix), type);
        if (Objects.isNull(prefixData)) {
            return nowData;
        }
        prefixData.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(prefixData.getPaidGoodsNum()), BigDecimal.valueOf(prefixData.getVisitedGoodsNum())));
        // 获取变化率数据
        nowData.setChangeRate(getChangeRateData(nowData, prefixData));
        return nowData;
    }

    private static final String CHANGE_RATE = "changeRate";
    /**
     * 计算统计数据变化率
     */
    private Map<String, BigDecimal> getChangeRateData(ProductOverviewVo nowData, ProductOverviewVo prefixData) {
        Map<String, BigDecimal> changeRate = new HashMap<>(16);
        // 计算变化率
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(nowData.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors)
                .filter((e) -> !StringUtils.equals(CHANGE_RATE, e.getName()))
                .filter((e) -> !FIELD_CLAZZ.equalsIgnoreCase(e.getName()))
                .forEach((d) -> {
                    try {
                        // 拿到属性名称
                        String fieldName = d.getName();
                        Method method = d.getReadMethod();
                        Object o1 = method.invoke(nowData);
                        Object o2 = method.invoke(prefixData);
                        BigDecimal result = getRate(o1, o2);
                        log.debug("属性 {} 对应上一时间段的变化率为 {} !", fieldName, result);
                        changeRate.put(fieldName, result);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("计算变化率过程失败, 原因如下：{}", e.getMessage());
                        throw new BusinessException(JsonResultCode.CODE_FAIL);
                    }
                });
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
        return changeRate;
    }

    /**
     * 计算统计数据变化率公式 (now-prefix)/prefix
     */
    private BigDecimal getRate(Object now, Object prefix) {
        if (Objects.isNull(now) || Objects.isNull(prefix)) {
            return BIGDECIMAL_ZERO;
        }
        BigDecimal n = BigDecimal.valueOf(Double.parseDouble(now.toString()));
        BigDecimal p = BigDecimal.valueOf(Double.parseDouble(prefix.toString()));
        return BigDecimalUtil.divideWithOutCheck(n.subtract(p), p);
    }

    /**
     * 获取指定时间下的商品概览统计数据(查询统计表)
     */
    private ProductOverviewVo getGoodsOverviewSummary(Date date, byte type) {
        return db().selectFrom(GOS)
            .where(GOS.REF_DATE.eq(date))
            .and(GOS.TYPE.eq(type))
            .limit(INTEGER_ONE)
            .fetchOneInto(ProductOverviewVo.class);
    }

    /**
     * 查询自定义时间段商品概览  @param param the param
     *
     * @param param the param
     * @return the product overview vo
     */
    public ProductOverviewVo customizeDayOverview(ProductOverviewParam param) {
        LocalDate start = param.getStartTime().toLocalDateTime().toLocalDate();
        LocalDate end = param.getEndTime().toLocalDateTime().toLocalDate();
        long days = end.toEpochDay() - start.toEpochDay();
        LocalDate prefixStart = start.minusDays(days);

        ProductOverviewVo data = getCustomizeGoodsOverviewSummary(start, end);
        if (Objects.isNull(data)) {
            return ProductOverviewVo.builder().build();
        }
        data.setStartTime(Date.valueOf(start));
        data.setEndTime(Date.valueOf(end));
        data.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(data.getPaidGoodsNum()), BigDecimal.valueOf(data.getVisitedGoodsNum())));
        ProductOverviewVo prefixData = getCustomizeGoodsOverviewSummary(prefixStart, start);
        if (Objects.isNull(prefixData)) {
            return data;
        }
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
            .where(GOS.REF_DATE.greaterThan(Date.valueOf(start)))
            .and(GOS.REF_DATE.le(Date.valueOf(end)))
            .and(GOS.TYPE.eq(BYTE_ONE))
            .fetchOneInto(ProductOverviewVo.class);
    }

    /**
     * 条件查询商品概览  @param param the param
     *
     * @param param the param
     * @return the product overview vo
     */
    public ProductOverviewVo conditionOverview(ProductOverviewParam param) {
        ProductOverviewVo data = ProductOverviewVo.builder()
            // 在架商品数
            .onShelfGoodsNum(goodsStatisticTaskService.getSaleGoodsNumber(param))
            // 动销商品数
            .soldGoodsNum(goodsStatisticTaskService.getDySoldGoodsNum(param))
            // 被访问商品数
            .visitedGoodsNum(goodsStatisticTaskService.getGoodsNumByVisit(param))
            // 商品访客数 (商品UV)
            .goodsUserVisit(goodsStatisticTaskService.getGoodsUv(param))
            // 商品浏览量 (商品pv)
            .goodsPageviews(goodsStatisticTaskService.getGoodsPv(param))
            // 加购人数
            .purchaseNum(goodsStatisticTaskService.addCartUserNum(param))
            // 加购件数
            .purchaseQuantity(goodsStatisticTaskService.getAddCartGoodsNumber(param))
            // 付款商品数
            .paidGoodsNum(goodsStatisticTaskService.paidGoodsNum(param))
            // 下单商品数
            .orderGoodsNum(goodsStatisticTaskService.getPayOrderGoodsNum(param))
            .build();
        if (Objects.isNull(data)) {
            return ProductOverviewVo.builder().build();
        }
        // 商品访问付款转化率
        data.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(data.getPaidGoodsNum()), BigDecimal.valueOf(data.getVisitedGoodsNum())));

        LocalDate start = param.getStartTime().toLocalDateTime().toLocalDate();
        LocalDate end = param.getEndTime().toLocalDateTime().toLocalDate();
        data.setStartTime(Date.valueOf(start));
        data.setEndTime(Date.valueOf(end));
        // 计算起始日期和结束日期相差的天数
        long days = end.toEpochDay() - start.toEpochDay();
        LocalDate prefixStart = start.minusDays(days);
        // 计算得到前一段等长的起止日期
        param.setStartTime(Timestamp.valueOf(prefixStart.atStartOfDay()));
        param.setEndTime(param.getStartTime());

        ProductOverviewVo prefixData = ProductOverviewVo.builder()
            .onShelfGoodsNum(goodsStatisticTaskService.getSaleGoodsNumber(param))
            .soldGoodsNum(goodsStatisticTaskService.getDySoldGoodsNum(param))
            .visitedGoodsNum(goodsStatisticTaskService.getGoodsNumByVisit(param))
            .goodsUserVisit(goodsStatisticTaskService.getGoodsUv(param))
            .goodsPageviews(goodsStatisticTaskService.getGoodsPv(param))
            .purchaseNum(goodsStatisticTaskService.addCartUserNum(param))
            .purchaseQuantity(goodsStatisticTaskService.getAddCartGoodsNumber(param))
            .paidGoodsNum(goodsStatisticTaskService.paidGoodsNum(param))
            .orderGoodsNum(goodsStatisticTaskService.getPayOrderGoodsNum(param))
            .build();
        if (Objects.isNull(prefixData)) {
            return data;
        }
        prefixData.setVisit2paid(divideWithOutCheck(BigDecimal.valueOf(prefixData.getPaidGoodsNum()), BigDecimal.valueOf(prefixData.getVisitedGoodsNum())));

        data.setChangeRate(getChangeRateData(data, prefixData));
        return data;
    }

    /**
     * 查询指定时间段商品效果，例如最近一天，七天，一个月  @param param the param
     *
     * @param param the param
     * @return the page result
     */
    public PageResult<ProductEffectVo> fixedDayEffect(ProductEffectParam param) {
        /** 必要筛选条件 */
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.eq(DateUtil.yyyyMmDdDate(LocalDate.now())))
            .and(GOODS_SUMMARY.TYPE.eq(param.getDynamicDate()));
        SelectJoinStep<?> joinStep = db().select(GOODS_SUMMARY.GOODS_ID
            , GOODS.GOODS_NAME
            , GOODS.GOODS_IMG
            , GOODS.SHOP_PRICE
            , GOODS_SUMMARY.NEW_USER_NUMBER
            , GOODS_SUMMARY.OLD_USER_NUMBER
            , GOODS_SUMMARY.PV
            , GOODS_SUMMARY.UV
            , GOODS_SUMMARY.CART_UV
            , GOODS_SUMMARY.PAID_UV
            , GOODS_SUMMARY.PAID_GOODS_NUMBER
            , GOODS_SUMMARY.GOODS_SALES
            , GOODS_SUMMARY.RECOMMEND_USER_NUM
            , GOODS_SUMMARY.COLLECT_USE_NUM
            , GOODS_SUMMARY.SHARE_PV
            , GOODS_SUMMARY.SHARE_UV
        )
            .from(GOODS_SUMMARY)
            .leftJoin(GOODS)
            .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID));

        PageResult<ProductEffectVo> pageResult = this.getPageResult(createEffectSelect(param, joinStep, baseCondition, true), param.getCurrentPage(), param.getPageRows(), ProductEffectVo.class);

        for (ProductEffectVo vo : pageResult.dataList) {
            vo.setNewUserPercentage(BigDecimalUtil.divideWithOutCheck(vo.getNewUserNumber(), vo.getPaidUv()));
            vo.setOldUserPercentage(BigDecimalUtil.divideWithOutCheck(vo.getOldUserNumber(), vo.getPaidUv()));
            vo.setUv2paidGoods(BigDecimalUtil.divideWithOutCheck(vo.getPaidGoodsNumber(), vo.getUv()));
        }
        return pageResult;
    }

    private SortField<Object> getSortField(Optional<String> field, Optional<String> sortType, boolean isFixedDay) {
        if (!sortType.isPresent() || DEFAULT_ORDER_BY_TYPE.equalsIgnoreCase(sortType.get())) {
            if (isFixedDay) {
                return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).asc();
            }
            return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).as(Util.underlineToHump(field.orElse(DEFAULT_ORDER_BY_FIELD))).asc();
        }
        if (isFixedDay) {
            return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).desc();
        }
        return DSL.field(name(field.orElse(DEFAULT_ORDER_BY_FIELD))).as(Util.underlineToHump(field.orElse(DEFAULT_ORDER_BY_FIELD))).desc();
    }

    /**
     * 查询自定义时间段商品效果  @param param the param
     *
     * @param param the param
     * @return the page result
     */
    public PageResult<ProductEffectVo> customizeDayEffect(ProductEffectParam param) {
        // 必要筛选条件
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.greaterThan(new Date(param.getStartTime().getTime())))
            .and(GOODS_SUMMARY.REF_DATE.le(new Date(param.getEndTime().getTime())))
            .and(GOODS_SUMMARY.TYPE.eq(BYTE_ONE));
        SelectJoinStep<?> joinStep = db().select(max(GOODS_SUMMARY.GOODS_ID).as("goodsId")
            , max(GOODS.GOODS_NAME).as("goodsName")
            , max(GOODS.GOODS_IMG).as("goodsImg")
            , max(GOODS.SHOP_PRICE).as("shopPrice")
            , sum(GOODS_SUMMARY.NEW_USER_NUMBER).as("newUserNumber")
            , sum(GOODS_SUMMARY.OLD_USER_NUMBER).as("oldUserNumber")
            , sum(GOODS_SUMMARY.PV).as("pv")
            , sum(GOODS_SUMMARY.UV).as("uv")
            , sum(GOODS_SUMMARY.CART_UV).as("cartUv")
            , sum(GOODS_SUMMARY.PAID_UV).as("paidUv")
            , sum(GOODS_SUMMARY.PAID_GOODS_NUMBER).as("paidGoodsNumber")
            , sum(GOODS_SUMMARY.GOODS_SALES).as("goodsSales")
            , sum(GOODS_SUMMARY.RECOMMEND_USER_NUM).as("recommendUserNum")
            , sum(GOODS_SUMMARY.COLLECT_USE_NUM).as("collectUserNum")
            , sum(GOODS_SUMMARY.SHARE_PV).as("sharePv")
            , sum(GOODS_SUMMARY.SHARE_UV).as("shareUv")
        )
            .from(GOODS_SUMMARY)
            .leftJoin(GOODS)
            .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID));
        PageResult<ProductEffectVo> pageResult = this.getPageResult(createEffectSelect(param, joinStep, baseCondition, false), param.getCurrentPage(), param.getPageRows(), ProductEffectVo.class);
        for (ProductEffectVo vo : pageResult.dataList) {
            vo.setNewUserPercentage(BigDecimalUtil.divideWithOutCheck(vo.getNewUserNumber(), vo.getPaidUv()));
            vo.setOldUserPercentage(BigDecimalUtil.divideWithOutCheck(vo.getOldUserNumber(), vo.getPaidUv()));
            vo.setUv2paidGoods(BigDecimalUtil.divideWithOutCheck(vo.getPaidGoodsNumber(), vo.getUv()));
        }
        return pageResult;
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
    private SelectLimitStep<?> createEffectSelect(ProductEffectParam param, SelectJoinStep<?> joinStep, Condition baseCondition, boolean isFixedDay) {
        Optional<String> field = Optional.ofNullable(param.getOrderByField());
        Optional<String> sortType = Optional.ofNullable(param.getOrderByType());
        // 动态排序字段，规则
        SortField<?> sortField = getSortField(field, sortType, isFixedDay);

        // 查询筛选条件，商品品牌，商家分类
        Condition brandCondition = GOODS.BRAND_ID.eq(param.getBrandId());
        Condition sortCondition = GOODS.SORT_ID.eq(param.getSortId());

        //  标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq(BYTE_ONE));

        if (param.getBrandId() > 0) {
            baseCondition = baseCondition.and(brandCondition);
        }
        if (param.getSortId() > 0) {
            baseCondition = baseCondition.and(sortCondition);
        }

        /** 条件连表查询，根据不同的条件构造出不同阶段的select */
        SelectConditionStep<?> conditionStep;/** 构造筛选条件 */
        SelectLimitStep<?> limitStep;/** 完善筛选条件 */

        if (param.getLabelId() > 0) {
            conditionStep = joinStep.leftJoin(GOODS_LABEL_COUPLE)
                .on(GOODS_LABEL_COUPLE.GTA_ID.eq(GOODS_SUMMARY.GOODS_ID))
                .where(labelCondtion);
        } else {
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
    public Workbook export2Excel(ProductEffectParam param) {
        SelectLimitStep<?> limitStep = createEffectSelect(param, db().select(GOODS_SUMMARY.GOODS_ID
            , GOODS.GOODS_NAME
            , GOODS.GOODS_IMG
            , GOODS.SHOP_PRICE
            , GOODS_SUMMARY.NEW_USER_NUMBER
            , GOODS_SUMMARY.OLD_USER_NUMBER
            , GOODS_SUMMARY.PV
            , GOODS_SUMMARY.UV
            , GOODS_SUMMARY.CART_UV
            , GOODS_SUMMARY.PAID_UV
            , GOODS_SUMMARY.PAID_GOODS_NUMBER
            , GOODS_SUMMARY.GOODS_SALES
            , GOODS_SUMMARY.RECOMMEND_USER_NUM
            , GOODS_SUMMARY.COLLECT_USE_NUM
            , GOODS_SUMMARY.SHARE_PV
            , GOODS_SUMMARY.SHARE_UV
            )
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID))
            , (GOODS_SUMMARY.REF_DATE.eq(new Date(Util.getEarlyTimeStamp(new java.util.Date(), -1).getTime())))
                .and(GOODS_SUMMARY.TYPE.eq(param.getDynamicDate())), true);
        if (param.getDynamicDate() <= 0) {
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
                , sum(GOODS_SUMMARY.PAID_GOODS_NUMBER).as("paidGoodsNumber")
                , sum(GOODS_SUMMARY.GOODS_SALES).as("goodsSales")
                , sum(GOODS_SUMMARY.RECOMMEND_USER_NUM).as("recommendUserNum")
                , sum(GOODS_SUMMARY.COLLECT_USE_NUM).as("collectUserNum")
                , sum(GOODS_SUMMARY.SHARE_PV).as("sharePv")
                , sum(GOODS_SUMMARY.SHARE_UV).as("shareUv")
            )
                .from(GOODS_SUMMARY)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID)), (GOODS_SUMMARY.REF_DATE.greaterOrEqual(new Date(param.getStartTime().getTime())))
                .and(GOODS_SUMMARY.REF_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(GOODS_SUMMARY.TYPE.eq((byte) 1)), false);

        }
        List<ProductEffectExportVo> exportVos = limitStep.fetchInto(ProductEffectExportVo.class);
        for (ProductEffectExportVo vo : exportVos) {
            vo.setGoodsInfo(vo.getGoodsName() + "  " + vo.getShopPrice());
        }
        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(exportVos, ProductEffectExportVo.class);
        return workbook;
    }

    // b2c_goods_summary
    public RankingVo getGoodsRanking(RankingParam param) {
        if (Objects.isNull(param.getUnit())) {
            return getAllData(param);
        }
        return null;
    }

    public RankingVo getAllData(RankingParam param) {
        // 销售额
        Map<Integer, Result<Record4<Date, Integer, BigDecimal, String>>> sales = getGoodsGroupData(param, GOODS_SUMMARY.GOODS_SALES);
        // 销售订单
        Map<Integer, Result<Record4<Date, Integer, Integer, String>>> salesOrder = getGoodsGroupData(param, GOODS_SUMMARY.PAID_GOODS_NUMBER);

        Map<String, ChartData> salesChart = getAllChartData(sales, param, GOODS_SUMMARY.GOODS_SALES);
        Map<String, ChartData> salesOrderChart = getAllChartData(salesOrder, param, GOODS_SUMMARY.PAID_GOODS_NUMBER);

        return RankingVo.builder()
            .salesChar(salesChart)
            .salesOrderChar(salesOrderChart)
            .salesTable(getAllTableData(param, salesChart))
            .salesOrderTable(getAllTableData(param, salesOrderChart))
            .build();
    }

    public ChartData getChartData() {
        return null;
    }

    public <T extends Number> Map<String, ChartData> getAllChartData(Map<Integer, Result<Record4<Date, Integer, T, String>>> source,
                                                                     RankingParam param,
                                                                     Field<T> field) {
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> dayRows = new ArrayList<>();
        List<Map<String, Object>> weekRows = new ArrayList<>();
        List<Map<String, Object>> monthRows = new ArrayList<>();
        List<Map<String, Object>> yearRows = new ArrayList<>();

        source.forEach((k, v) -> {
            columns.add(v.getValue(INTEGER_ZERO, GOODS.GOODS_NAME));
            param.setUnit(BYTE_ZERO);
            dayCharData(dayRows, v, getRefDateList(param), this::dayRule, field);
            weekCharData(weekRows, v, getRefDateList1(param), this::weekRule, field);
            param.setUnit(BYTE_ONE);
            dayCharData(monthRows, v, getRefDateList(param), this::monthRule, field);
            param.setUnit(BYTE_TWO);
            dayCharData(yearRows, v, getRefDateList(param), this::yearRule, field);
        });

        return new HashMap<String, ChartData>(4) {{
            put(DAY_CHAR_DATA, ChartData.builder().columns(columns).rows(dayRows).build());
            put(WEEK_CHAR_DATA, ChartData.builder().columns(columns).rows(weekRows).build());
            put(MONTH_CHAR_DATA, ChartData.builder().columns(columns).rows(monthRows).build());
            put(YEAR_CHAR_DATA, ChartData.builder().columns(columns).rows(yearRows).build());
        }};
    }

    public TableData getTableData(RankingParam param, List<Map<String, Object>> rows) {
        Set<String> goodsName = rows.stream().findAny().orElse(EMPTY_MAP).keySet();
        List<String> refDate;
        if (BYTE_THREE.equals(param.getUnit())) {
            refDate = getRefDateList1(param).stream().map(Object::toString).collect(Collectors.toList());
        } else {
            refDate = getRefDateList(param).stream().map(Object::toString).collect(Collectors.toList());
        }
        return TableData.builder().refDate(refDate).goodsName(goodsName).arrayData(getTableArrayData(rows)).build();
    }

    public List<List<Object>> getTableArrayData(List<Map<String, Object>> rows) {
        List<Map<String, Object>> copy = new ArrayList<>(rows);
        return new ArrayList<List<Object>>() {{
            copy.forEach(e -> {
                e.remove("Date");
                add(new ArrayList<>(e.values()));
            });
        }};
    }

    public Map<String, TableData> getAllTableData(RankingParam param, Map<String, ChartData> chartData) {
        return new HashMap<String, TableData>(4) {{
            param.setUnit(BYTE_ZERO);
            put(DAY_TABLE_DATA, getTableData(param, chartData.get(DAY_CHAR_DATA).getRows()));
            param.setUnit(BYTE_THREE);
            put(WEEK_TABLE_DATA, getTableData(param, chartData.get(WEEK_CHAR_DATA).getRows()));
            param.setUnit(BYTE_ONE);
            put(MONTH_TABLE_DATA, getTableData(param, chartData.get(MONTH_CHAR_DATA).getRows()));
            param.setUnit(BYTE_TWO);
            put(YEAR_TABLE_DATA, getTableData(param, chartData.get(YEAR_CHAR_DATA).getRows()));
        }};
    }

    /**
     * Gets goods group data.获取商品销售额/销售订单排行前10的分组数据
     *
     * @param <T>   the type parameter
     * @param param the param
     * @param field the field
     * @return the goods group data
     */
    public <T extends Number> Map<Integer, Result<Record4<Date, Integer, T, String>>> getGoodsGroupData(RankingParam param, Field<T> field) {
        // 获取销售额/销售订单排行前10的商品id
        Map<Integer, Result<Record1<Integer>>> ids = db()
            .select(min(GOODS_SUMMARY.GOODS_ID))
            .from(GOODS_SUMMARY)
            .where(GOODS_SUMMARY.REF_DATE.greaterThan(param.getStartTime()))
            .and(GOODS_SUMMARY.REF_DATE.le(param.getEndTime()))
            .and(GOODS_SUMMARY.TYPE.eq(BYTE_ONE))
            .groupBy(GOODS_SUMMARY.GOODS_ID)
            .orderBy(sum(field).desc())
            .limit(10)
            .fetchGroups(min(GOODS_SUMMARY.GOODS_ID));

        Set<Integer> goodsIds = ids.keySet();
        log.debug("销售额/销售订单排行前10的商品id为：{}", Util.toJson(goodsIds));

        Map<Integer, Result<Record4<Date, Integer, T, String>>> sales = db()
            .select(GOODS_SUMMARY.REF_DATE, GOODS_SUMMARY.GOODS_ID, field, GOODS.GOODS_NAME)
            .from(GOODS_SUMMARY)
            .leftJoin(GOODS).on(GOODS_SUMMARY.GOODS_ID.eq(GOODS.GOODS_ID))
            .where(GOODS_SUMMARY.REF_DATE.greaterThan(param.getStartTime()))
            .and(GOODS_SUMMARY.REF_DATE.le(param.getEndTime()))
            .and(GOODS_SUMMARY.TYPE.eq(BYTE_ONE))
            .and(GOODS_SUMMARY.GOODS_ID.in(goodsIds))
            .orderBy(GOODS_SUMMARY.REF_DATE)
            .fetchGroups(GOODS_SUMMARY.GOODS_ID);
        log.debug("销售额/销售订单排行前10的商品日销售额详情为：");
        sales.forEach((k, v) -> {
            log.debug("商品id：{}", k);
            v.forEach(record -> log.debug("日销售额/销售订单：{}", record.value1() + "--" + record.value2() + "--" + record.value3() + "--" + record.value4()));
        });
        return sales;
    }

    private List<LocalDate> getRefDateList(RankingParam param) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate start;
        LocalDate end;
        switch (param.getUnit()) {
            case 1:
                // 月
                start = param.getStartTime().toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
                end = param.getEndTime().toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
                for (LocalDate date = start;
                     date.isBefore(end.plusMonths(INTEGER_ONE));
                     date = date.plusMonths(INTEGER_ONE)) {
                    result.add(date);
                }
                return result;
            case 2:
                // 年
                start = param.getStartTime().toLocalDate().with(TemporalAdjusters.firstDayOfYear());
                end = param.getEndTime().toLocalDate().with(TemporalAdjusters.firstDayOfYear());
                for (LocalDate date = start;
                     date.isBefore(end.plusYears(INTEGER_ONE));
                     date = date.plusYears(INTEGER_ONE)) {
                    result.add(date);
                }
                return result;
            default:
                // 0天
                for (LocalDate date = param.getStartTime().toLocalDate().plusDays(INTEGER_ONE);
                     date.isBefore(param.getEndTime().toLocalDate().plusDays(INTEGER_ONE));
                     date = date.plusDays(INTEGER_ONE)) {
                    result.add(date);
                }
                return result;
        }
    }

    private List<Tuple2<LocalDate, LocalDate>> getRefDateList1(RankingParam param) {
        List<Tuple2<LocalDate, LocalDate>> result = new ArrayList<>();
        // 周
        for (LocalDate date = param.getStartTime().toLocalDate().plusDays(INTEGER_ONE);
             date.isBefore(param.getEndTime().toLocalDate().plusDays(INTEGER_ONE));
             date = date.plusDays(7)) {
            result.add(new Tuple2<>(date, date.plusDays(7).compareTo(param.getEndTime().toLocalDate()) > 0 ? param.getEndTime().toLocalDate() : date.plusDays(7)));
        }
        return result;
    }

    private <T extends Number> boolean dayRule(Record4<Date, Integer, T, String> t, LocalDate u) {
        return t.getValue(GOODS_SUMMARY.REF_DATE).toLocalDate().compareTo(u) == 0;
    }

    private <T extends Number> boolean weekRule(Record4<Date, Integer, T, String> t, Tuple2<LocalDate, LocalDate> u) {
        return t.getValue(GOODS_SUMMARY.REF_DATE).toLocalDate().compareTo(u.v1()) > 0 ||
            t.getValue(GOODS_SUMMARY.REF_DATE).toLocalDate().compareTo(u.v2()) < 0;
    }

    private <T extends Number> boolean monthRule(Record4<Date, Integer, T, String> t, LocalDate u) {
        return t.getValue(GOODS_SUMMARY.REF_DATE).toLocalDate().getYear() == u.getYear();
    }

    private <T extends Number> boolean yearRule(Record4<Date, Integer, T, String> t, LocalDate u) {
        LocalDate tDate = t.getValue(GOODS_SUMMARY.REF_DATE).toLocalDate();
        return tDate.getYear() == u.getYear();
    }

    // 构造每天/月/年的图形数据
    private <T extends Number> void dayCharData(List<Map<String, Object>> rows,
                                                final Result<Record4<Date, Integer, T, String>> results,
                                                List<LocalDate> showDate,
                                                BiPredicate<Record4<Date, Integer, T, String>, LocalDate> rule,
                                                Field<T> field) {
        String name = results.get(INTEGER_ZERO).getValue(GOODS.GOODS_NAME);
        log.debug("当前商品：{}", name);
        log.debug("展示日期列表：{}", Util.toJson(showDate));
        AtomicBoolean flag = new AtomicBoolean(false);

        showDate.forEach(d -> {
            Optional<Map<String, Object>> optional = rows.stream().filter(m -> LocalDate.parse(m.get("Date").toString()).compareTo(d) == 0).findFirst();
            Map<String, Object> map;
            if (optional.isPresent()) {
                map = optional.get();
            } else {
                map = new HashMap<>();
                flag.set(true);
            }
            List<Record4<Date, Integer, T, String>> temp = results.stream().filter(e -> rule.test(e, d)).collect(Collectors.toList());
            log.debug("满足过滤规则的日期有：{}", temp.size());
            map.putIfAbsent("Date", Date.valueOf(d));
            Class<T> clazz = field.getType();
            if (clazz.equals(BigDecimal.class)) {
                map.put(name, temp.stream().map(e -> e.getValue(field)).map(Number::doubleValue).reduce(DOUBLE_ZERO, Double::sum));
            } else if (clazz.equals(Integer.class)) {
                map.put(name, temp.stream().map(e -> e.getValue(field)).map(Number::intValue).reduce(INTEGER_ZERO, Integer::sum));
            } else {
                map.put(name, STRING_ZERO);
            }
            if (flag.get()) {
                rows.add(map);
            }
            flag.set(false);
        });
    }

    // 构造每周的图形数据
    private <T extends Number> void weekCharData(List<Map<String, Object>> rows,
                                                 final Result<Record4<Date, Integer, T, String>> results,
                                                 List<Tuple2<LocalDate, LocalDate>> showDate,
                                                 BiPredicate<Record4<Date, Integer, T, String>, Tuple2<LocalDate, LocalDate>> rule,
                                                 Field<T> field) {
        String name = results.get(INTEGER_ZERO).getValue(GOODS.GOODS_NAME);
        log.debug("当前商品：{}", name);
        log.debug("展示日期列表：{}", Util.toJson(showDate));
        AtomicBoolean flag = new AtomicBoolean(false);
        showDate.forEach(d -> {
            Optional<Map<String, Object>> optional = rows.stream().filter(m -> m.get("Date").toString().equals(d.v1() + "~" + d.v2())).findFirst();
            Map<String, Object> map;
            if (optional.isPresent()) {
                map = optional.get();
            } else {
                map = new HashMap<>();
                flag.set(true);
            }
            List<Record4<Date, Integer, T, String>> temp = results.stream().filter(e -> rule.test(e, d)).collect(Collectors.toList());
            log.debug("满足过滤规则的日期有：{}", temp.size());
            map.putIfAbsent("Date", d.v1() + "~" + d.v2());
            Class<T> clazz = field.getType();
            if (clazz.equals(BigDecimal.class)) {
                map.put(name, temp.stream().map(e -> e.getValue(field)).map(Number::doubleValue).reduce(DOUBLE_ZERO, Double::sum));
            } else if (clazz.equals(Integer.class)) {
                map.put(name, temp.stream().map(e -> e.getValue(field)).map(Number::intValue).reduce(INTEGER_ZERO, Integer::sum));
            } else {
                map.put(name, STRING_ZERO);
            }
            if (flag.get()) {
                rows.add(map);
            }
            flag.set(false);
        });
    }

    private Date dateDecrement(Date date) {
        return Date.valueOf(date.toLocalDate().minusDays(INTEGER_ONE));
    }
}
