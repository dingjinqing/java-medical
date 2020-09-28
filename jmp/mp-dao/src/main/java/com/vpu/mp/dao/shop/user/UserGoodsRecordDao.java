package com.vpu.mp.dao.shop.user;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.member.report.MemberGoodsBrowseReportParam;
import com.vpu.mp.service.pojo.shop.member.report.MemberGoodsBrowseReportVo;
import org.elasticsearch.common.Strings;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;
import static com.vpu.mp.db.shop.Tables.USER_CART_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;
import static com.vpu.mp.db.shop.Tables.USER_GOODS_RECORD;
import static com.vpu.mp.db.shop.tables.PrescriptionItem.PRESCRIPTION_ITEM;

/**
 * 用户浏览商品记录
 * (每日每个商品一条)
 * @author 孔德成
 * @date 2020/9/24 10:59
 */
@Component
public class UserGoodsRecordDao extends ShopBaseDao {

    /**
     * 用户的浏览记录
     * @return
     */
    public PageResult<MemberGoodsBrowseReportVo> userGoodsBrowseReport(MemberGoodsBrowseReportParam param){
        SelectConditionStep<? extends Record> where = db()
                .select(USER_GOODS_RECORD.ID,USER_GOODS_RECORD.UPDATE_TIME.as(MemberGoodsBrowseReportVo.TIME),
                        DSL.max(GOODS_MEDICAL_INFO.GOODS_COMMON_NAME).as(MemberGoodsBrowseReportVo.GOODSNAME),
                        DSL.max(GOODS_MEDICAL_INFO.GOODS_QUALITY_RATIO).as(MemberGoodsBrowseReportVo.SPECIFICATIONS),
                        DSL.max(GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE).as(MemberGoodsBrowseReportVo.MANUFACTURER),
                        DSL.countDistinct(PRESCRIPTION_ITEM.ID).as(MemberGoodsBrowseReportVo.PRESCRIPTIONNUM),
                        DSL.countDistinct(USER_CART_RECORD.ID).as(MemberGoodsBrowseReportVo.ADDCARTNUM),
                        DSL.countDistinct(USER_COLLECTION.ID).as(MemberGoodsBrowseReportVo.COLLECT),
                        DSL.countDistinct(ORDER_GOODS.REC_ID).as(MemberGoodsBrowseReportVo.BUYGOODSNUM)
                        )
                .from(USER_GOODS_RECORD)
                .leftJoin(GOODS_MEDICAL_INFO).on(GOODS_MEDICAL_INFO.GOODS_ID.eq(USER_GOODS_RECORD.GOODS_ID))
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.USER_ID.eq(USER_GOODS_RECORD.USER_ID))
                .leftJoin(PRESCRIPTION_ITEM).on(PRESCRIPTION_ITEM.GOODS_ID.eq(USER_GOODS_RECORD.GOODS_ID)
                        .and(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_ITEM.PRESCRIPTION_CODE))
                        .and(PRESCRIPTION.USER_ID.eq(USER_GOODS_RECORD.USER_ID)))
                .leftJoin(USER_CART_RECORD).on(USER_CART_RECORD.GOODS_ID.eq(USER_GOODS_RECORD.GOODS_ID)
                        .and(USER_CART_RECORD.USER_ID.eq(USER_GOODS_RECORD.USER_ID)))
                .leftJoin(USER_COLLECTION).on(USER_COLLECTION.GOODS_ID.eq(USER_GOODS_RECORD.GOODS_ID)
                        .and(USER_COLLECTION.USER_ID.eq(USER_GOODS_RECORD.USER_ID)))
                .leftJoin(ORDER_INFO).on(ORDER_INFO.USER_ID.eq(USER_GOODS_RECORD.USER_ID))
                .leftJoin(ORDER_GOODS).on(ORDER_GOODS.GOODS_ID.eq(USER_GOODS_RECORD.GOODS_ID)
                        .and(ORDER_GOODS.ORDER_ID.eq(ORDER_INFO.ORDER_ID))
                        .and(ORDER_INFO.USER_ID.eq(USER_GOODS_RECORD.USER_ID)))
                .where(USER_GOODS_RECORD.USER_ID.eq(param.getUserId()));
        where.groupBy(USER_GOODS_RECORD.ID, USER_GOODS_RECORD.UPDATE_TIME);
        paramBbuildSelect(param, where);
        where.orderBy(USER_GOODS_RECORD.UPDATE_TIME.desc());
        return getPageResult(where,param,MemberGoodsBrowseReportVo.class);
    }

    private void paramBbuildSelect(MemberGoodsBrowseReportParam param, SelectConditionStep<? extends Record> where) {
        if (param.getGoodsName()!=null&&! Strings.isEmpty(param.getGoodsName().trim())){
            where.and(GOODS_MEDICAL_INFO.GOODS_COMMON_NAME.eq(param.getGoodsName()));
        }
        if (param.getIsAddCart()!=null){
            if (param.getIsAddCart().equals(BaseConstant.NO)){
                where.and(DSL.countDistinct(USER_CART_RECORD.ID).eq(BaseConstant.NO.intValue()));
            }else {
                where.and(DSL.countDistinct(USER_CART_RECORD.ID).gt(BaseConstant.YES.intValue()));
            }
        }
        if (param.getIsBuy()!=null){
            if (param.getIsAddCart().equals(BaseConstant.NO)){
                where.and(DSL.countDistinct(ORDER_GOODS.REC_ID).eq(BaseConstant.NO.intValue()));
            }else {
                where.and(DSL.countDistinct(ORDER_GOODS.REC_ID).gt(BaseConstant.YES.intValue()));
            }
        }
        if (param.getIsCollect()!=null){
            if (param.getIsAddCart().equals(BaseConstant.NO)){
                where.and(DSL.countDistinct(USER_COLLECTION.ID).eq(BaseConstant.NO.intValue()));
            }else {
                where.and(DSL.countDistinct(USER_COLLECTION.ID).gt(BaseConstant.YES.intValue()));
            }
        }
        if (param.getIsPrescription()!=null){
            if (param.getIsAddCart().equals(BaseConstant.NO)){
                where.and(DSL.countDistinct(PRESCRIPTION_ITEM.ID).eq(BaseConstant.NO.intValue()));
            }else {
                where.and(DSL.countDistinct(PRESCRIPTION_ITEM.ID).gt(BaseConstant.YES.intValue()));
            }
        }
    }

}
