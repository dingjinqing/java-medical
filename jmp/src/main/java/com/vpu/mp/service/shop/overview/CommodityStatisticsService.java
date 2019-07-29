package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordExportVo;
import com.vpu.mp.service.pojo.shop.overview.commodity.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsLabelCouple.GOODS_LABEL_COUPLE;
import static com.vpu.mp.db.shop.tables.GoodsSummary.GOODS_SUMMARY;
import static com.vpu.mp.db.shop.tables.GoodsUserSummary.GOODS_USER_SUMMARY;
import static org.jooq.impl.DSL.*;

/**
 * @Author:liufei
 * @Date:2019/7/22
 * @Description:
 */
@Service
@Scope("prototype")
public class CommodityStatisticsService extends ShopBaseService {
    /** goods_summary表默认排序字段 */
    private static final String DEFAULT_ORDER_BY_FIELD = "uv";
    /** 默认排序规则，升序asc */
    private static final String DEFAULT_ORDER_BY_TYPE = "asc";

    //查询指定时间段商品概览，例如最近一天，七天，一个月
    public ProductOverviewVo fixedDayOverview(ProductOverviewParam param){
        List<ProductOverviewVo> list =  db().select(GOODS_USER_SUMMARY.GOODS_ID_NUMBER
                ,GOODS_USER_SUMMARY.GOODS_ID_VISIT
                ,GOODS_USER_SUMMARY.GOODS_USER_VISIT
                ,GOODS_USER_SUMMARY.GOODS_VISIT
                ,GOODS_USER_SUMMARY.CART_USER_NUMBER
                ,GOODS_USER_SUMMARY.CART_GOODS_NUMBER
                ,GOODS_USER_SUMMARY.PAID_GOODS_NUMBER
                ,GOODS_USER_SUMMARY.PAID_USER_NUMBER)
                .from(GOODS_USER_SUMMARY)
                .where(GOODS_USER_SUMMARY.REF_DATE.eq(new Date(Util.getEarlyTimeStamp(new java.util.Date(),-1).getTime())))
                .and(GOODS_USER_SUMMARY.TYPE.eq(param.getDynamicDate()))
                .fetchInto(ProductOverviewVo.class);
        if (!Util.isEmpty(list)){
            ProductOverviewVo productOverviewVo = list.get(0);
            productOverviewVo.setVisit2paid(productOverviewVo.getPaidGoodsNumber()/Double.valueOf(productOverviewVo.getGoodsIdVisit()));
            return productOverviewVo;
        }
        return null;
    }
    //查询自定义时间段商品概览
    public ProductOverviewVo customizeDayOverview(ProductOverviewParam param){
        List<ProductOverviewVo> list =  db().select(sum(GOODS_USER_SUMMARY.GOODS_ID_NUMBER).as("goodsIdNumber")
                ,sum(GOODS_USER_SUMMARY.GOODS_ID_VISIT).as("goodsIdVisit")
                ,sum(GOODS_USER_SUMMARY.GOODS_USER_VISIT).as("goodsUserVisit")
                ,sum(GOODS_USER_SUMMARY.GOODS_VISIT).as("goodsVisit")
                ,sum(GOODS_USER_SUMMARY.CART_USER_NUMBER).as("cartUserNumber")
                ,sum(GOODS_USER_SUMMARY.CART_GOODS_NUMBER).as("cartGoodsNumber")
                ,sum(GOODS_USER_SUMMARY.PAID_GOODS_NUMBER).as("goodsNumber")
                ,sum(GOODS_USER_SUMMARY.PAID_USER_NUMBER).as("paidUserNumber"))
                .from(GOODS_USER_SUMMARY)
                .where(GOODS_USER_SUMMARY.REF_DATE.greaterOrEqual(new Date(param.getStartTime().getTime())))
                .and(GOODS_USER_SUMMARY.REF_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(GOODS_USER_SUMMARY.TYPE.eq((byte)1))
                .fetchInto(ProductOverviewVo.class);
        if (!Util.isEmpty(list)){
            ProductOverviewVo productOverviewVo = list.get(0);
            productOverviewVo.setVisit2paid(productOverviewVo.getPaidGoodsNumber()/Double.valueOf(productOverviewVo.getGoodsIdVisit()));
            return productOverviewVo;
        }
        return null;
    }
    //条件查询商品概览
    public ProductOverviewVo conditionOverview(ProductOverviewParam param){
        ProductOverviewVo vo = new ProductOverviewVo();
        vo.setGoodsIdVisit(getGoodsNumByVisit(param));
        vo.setGoodsIdNumber(getSaleGoodsNumber(param));
        vo.setVisit2paid(Double.valueOf(paidGoodsNum(param))/getGoodsNumByVisit(param));
        vo.setCartGoodsNumber(getAddCartGoodsNumber(param));
        vo.setCartUsernumber(addCartUserNum(param));
        vo.setGoodsUserVisit(getGoodsUv(param));
        vo.setGoodsVisit(getGoodsPv(param));
        vo.setPaidGoodsNumber(paidGoodsNum(param));

        return vo;
    }
    //在架商品数
    public int getSaleGoodsNumber(ProductOverviewParam param){
        GoodsBak gb = GoodsBak.GOODS_BAK.as("gb");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //基本筛选条件
        Condition baseCondition = gb.BAK_DATE.greaterOrEqual(new Date(param.getStartTime().getTime()))
                .and(gb.BAK_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(gb.GOODS_NUMBER.greaterThan(0))
                .and(gb.IS_ON_SALE.eq((byte)1));
        //品牌条件
        Condition brandCondition = gb.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = gb.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);

        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        if (param.getLabelId() > 0){
            joinStep = db().select(countDistinct(gb.GOODS_ID)).from(gb).leftJoin(glc).on(gb.GOODS_ID.eq(glc.GTA_ID));
            conditionStep = joinStep.where(labelCondtion);
        }else{
            conditionStep = db().select(countDistinct(gb.GOODS_ID)).from(gb).where(baseCondition);
        }
        Optional<Integer> goodsIdNumber = conditionStep.fetchOptionalInto(Integer.class);
        return goodsIdNumber.orElse(0);
    }
    //被访问商品数
    public int getGoodsNumByVisit(ProductOverviewParam param){
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        //基本筛选条件
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        //品牌条件
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ugr.GOODS_ID)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);
        if (param.getLabelId() > 0)
            baseCondition = baseCondition.and(labelCondtion);

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsIdVisit = conditionStep.fetchOptionalInto(Integer.class);
        return goodsIdVisit.orElse(0);
    }
    //商品UV数
    public int getGoodsUv(ProductOverviewParam param){
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        //基本筛选条件
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        //品牌条件
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ugr.USER_ID)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);
        if (param.getLabelId() > 0)
            baseCondition = baseCondition.and(labelCondtion);

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsUv = conditionStep.fetchOptionalInto(Integer.class);
        return goodsUv.orElse(0);
    }
    //商品PV数
    public int getGoodsPv(ProductOverviewParam param) {
        UserGoodsRecord ugr = UserGoodsRecord.USER_GOODS_RECORD.as("ugr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        //基本筛选条件
        Condition baseCondition = ugr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ugr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        //品牌条件
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(sum(ugr.COUNT)).from(ugr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ugr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ugr.GOODS_ID));
        }
        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);
        if (param.getLabelId() > 0)
            baseCondition = baseCondition.and(labelCondtion);

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> goodsPv = conditionStep.fetchOptionalInto(Integer.class);
        return goodsPv.orElse(0);
    }
    //加购人数
    public int addCartUserNum(ProductOverviewParam param){
        UserCartRecord ucr = UserCartRecord.USER_CART_RECORD.as("ucr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        //基本筛选条件
        Condition baseCondition = ucr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ucr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        //品牌条件
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(countDistinct(ucr.USER_ID)).from(ucr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ucr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ucr.GOODS_ID));
        }
        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);
        if (param.getLabelId() > 0)
            baseCondition = baseCondition.and(labelCondtion);

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> addCardUserNum = conditionStep.fetchOptionalInto(Integer.class);
        return addCardUserNum.orElse(0);
    }
    //加购件数
    public int getAddCartGoodsNumber(ProductOverviewParam param) {
        UserCartRecord ucr = UserCartRecord.USER_CART_RECORD.as("ucr");
        Goods g = Goods.GOODS.as("g");
        GoodsLabelCouple glc = GoodsLabelCouple.GOODS_LABEL_COUPLE.as("glc");
        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectJoinStep joinStep;//构造连表条件，不同的条件连接不同的表
        SelectConditionStep conditionStep;//构造筛选条件

        //基本筛选条件
        Condition baseCondition = ucr.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime()))
                .and(ucr.CREATE_TIME.lessThan(new Timestamp(param.getEndTime().getTime())));

        //品牌条件
        Condition brandCondition = g.BRAND_ID.eq(param.getBrandId());
        //商家分类条件
        Condition sortCondition = g.SORT_ID.eq(param.getSortId());
        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        joinStep = db().select(sum(ucr.NUM)).from(ucr);
        if (param.getBrandId() > 0 || param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(g).on(ucr.GOODS_ID.eq(g.GOODS_ID));
        }
        if (param.getSortId() > 0) {
            joinStep = joinStep.leftJoin(glc).on(glc.GTA_ID.eq(ucr.GOODS_ID));
        }
        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);
        if (param.getLabelId() > 0)
            baseCondition = baseCondition.and(labelCondtion);

        conditionStep = joinStep.where(baseCondition);
        Optional<Integer> addCartGoodsNum = conditionStep.fetchOptionalInto(Integer.class);
        return addCartGoodsNum.orElse(0);
    }
    //付款商品数 ToDo 具体计算规则未知
    public int paidGoodsNum(ProductOverviewParam param){
        return 0;
    }


    //查询指定时间段商品效果，例如最近一天，七天，一个月
    public PageResult<ProductEffectVo> fixedDayEffect(ProductEffectParam param){
        //必要筛选条件
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.eq(new Date(Util.getEarlyTimeStamp(new java.util.Date(),-1).getTime())))
                .and(GOODS_SUMMARY.TYPE.eq(param.getDynamicDate()));
        SelectJoinStep joinStep = db().select(GOODS_SUMMARY.GOODS_ID
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
            vo.setNewUserPercentage(vo.getNewUserNumber() / Double.valueOf(vo.getPaidUv()));
            vo.setOldUserPercentage(vo.getOldUserNumber() / Double.valueOf(vo.getPaidUv()));
            vo.setUv2paidGoods(vo.getPaidGoodsNumber() / Double.valueOf(vo.getUv()));
        }
        return pageResult;
    }

    public SortField<Object> getSortField(Optional<String> field,Optional<String> sortType){
        if(!sortType.isPresent() || DEFAULT_ORDER_BY_TYPE.equalsIgnoreCase(sortType.get())) {
            return DSL.field(name(field.isPresent() ? field.get() : DEFAULT_ORDER_BY_FIELD)).asc();
        }
        return DSL.field(name(field.isPresent() ? field.get() : DEFAULT_ORDER_BY_FIELD)).desc();
    }

    //查询自定义时间段商品效果
    public PageResult<ProductEffectVo> customizeDayEffect(ProductEffectParam param){
        //必要筛选条件
        Condition baseCondition = (GOODS_SUMMARY.REF_DATE.greaterOrEqual(new Date(param.getStartTime().getTime())))
                .and(GOODS_SUMMARY.REF_DATE.lessThan(new Date(param.getEndTime().getTime())))
                .and(GOODS_SUMMARY.TYPE.eq((byte)1));
        SelectJoinStep joinStep = db().select(max(GOODS_SUMMARY.GOODS_ID).as("goodsId")
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
            vo.setNewUserPercentage(vo.getNewUserNumber() / Double.valueOf(vo.getPaidUv()));
            vo.setOldUserPercentage(vo.getOldUserNumber() / Double.valueOf(vo.getPaidUv()));
            vo.setUv2paidGoods(vo.getPaidGoodsNumber() / Double.valueOf(vo.getUv()));
        }
        return pageResult;
    }

    /**
     * 创建商品效果查询select
     * @param param
     * @param joinStep
     * @param baseCondition
     * @param isFixedDay true为指定时间，false为自定义时间
     * @return
     */
    public SelectLimitStep createEffectSelect(ProductEffectParam param,SelectJoinStep joinStep,Condition baseCondition,boolean isFixedDay){
        //按照goods_id分组求和各个字段的值

        Optional<String> field = Optional.ofNullable(param.getOrderByField());
        Optional<String> sortType = Optional.ofNullable(param.getOrderByType());
        //动态排序字段，规则
        SortField sortField = getSortField(field,sortType);

        //查询筛选条件，商品品牌，商家分类
        Condition brandCondition = GOODS.BRAND_ID.eq(param.getBrandId());
        Condition sortCondition = GOODS.SORT_ID.eq(param.getSortId());

        //标签条件
        Condition labelCondtion = GOODS_LABEL_COUPLE.ID.eq(param.getLabelId()).and(GOODS_LABEL_COUPLE.TYPE.eq((byte)1));

        if (param.getBrandId() > 0)
            baseCondition = baseCondition.and(brandCondition);
        if (param.getSortId() > 0)
            baseCondition = baseCondition.and(sortCondition);

        //条件连表查询，根据不同的条件构造出不同阶段的select
        SelectConditionStep conditionStep;//构造筛选条件
        SelectLimitStep limitStep;//完善筛选条件

        if (param.getLabelId() > 0){
            conditionStep = joinStep.leftJoin(GOODS_LABEL_COUPLE)
                    .on(GOODS_LABEL_COUPLE.GTA_ID.eq(GOODS_SUMMARY.GOODS_ID))
                    .where(labelCondtion);
        }else{
            conditionStep = joinStep.where(true);
        }
        limitStep = isFixedDay ? conditionStep.and(baseCondition).orderBy(sortField) : conditionStep.and(baseCondition).groupBy(GOODS_SUMMARY.GOODS_ID).orderBy(sortField);
        return limitStep;
    }

    public Workbook export2Excel(ProductEffectParam param){
        SelectLimitStep limitStep = createEffectSelect(param,db().select(GOODS_SUMMARY.GOODS_ID
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
            //自定义时间
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
