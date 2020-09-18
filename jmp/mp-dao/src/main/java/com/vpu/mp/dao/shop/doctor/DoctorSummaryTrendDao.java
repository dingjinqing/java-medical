package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorSummaryTrendDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorSummaryTrendRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticMinMaxVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticConstant;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;

import static com.vpu.mp.db.shop.Tables.DOCTOR;
import static com.vpu.mp.db.shop.Tables.DOCTOR_SUMMARY_TREND;

/**
 * @author chenjie
 * @date 2020年09月15日
 */
@Repository
public class DoctorSummaryTrendDao extends ShopBaseDao {

    /**
     * 最大最小
     */
    public static final String MIN_CONSULTATION = "min_consultation";
    public static final String MAX_CONSULTATION = "max_consultation";
    public static final String MIN_INQUIRY_MONEY = "min_inquiry_money";
    public static final String MAX_INQUIRY_MONEY = "max_inquiry_money";
    public static final Integer INTEGER_ZERO = 0;
    /**
     * 添加记录
     *
     * @param param
     * @return
     */
    public void insertDoctorStatistic(DoctorSummaryTrendDo param) {
        DoctorSummaryTrendRecord record = db().newRecord(DOCTOR_SUMMARY_TREND);
        FieldsUtil.assign(param, record);
        record.insert();
    }

    /**
     * 更新记录
     *
     * @param param
     * @return
     */
    public void updateDoctorStatistic(DoctorSummaryTrendDo param) {
        DoctorSummaryTrendRecord record = db().newRecord(DOCTOR_SUMMARY_TREND);
        FieldsUtil.assign(param, record);
        record.update();
    }

    /**
     * 查询记录
     *
     * @param param
     * @return
     */
    public DoctorSummaryTrendDo getDoctorStatistic(DoctorStatisticParam param) {
        return db().selectFrom(DOCTOR_SUMMARY_TREND)
            .where(DOCTOR_SUMMARY_TREND.DOCTOR_ID.eq(param.getDoctorId()))
            .and(DOCTOR_SUMMARY_TREND.TYPE.eq(param.getType()))
            .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(param.getRefDate()))
            .fetchAnyInto(DoctorSummaryTrendDo.class);
    }

    /**
     * 医师业绩列表(自定义时间段)
     *
     * @param param
     * @return
     */
    public PageResult<DoctorStatisticListVo> getDoctorListForCustomize(DoctorStatisticParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR_SUMMARY_TREND.DOCTOR_ID,DOCTOR.NAME
                , DSL.sum(DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER).as("consultation_number")
                ,DSL.sum(DOCTOR_SUMMARY_TREND.INQUIRY_MONEY).as("inquiry_money"),DSL.sum(DOCTOR_SUMMARY_TREND.INQUIRY_NUMBER).as("inquiry_number")
                ,DSL.sum(DOCTOR_SUMMARY_TREND.PRESCRIPTION_MONEY).as("prescription_money"),DSL.sum(DOCTOR_SUMMARY_TREND.PRESCRIPTION_NUM).as("prescription_num")
                ,DSL.sum(DOCTOR_SUMMARY_TREND.CONSUME_MONEY).as("consume_money"))
            .from(DOCTOR_SUMMARY_TREND)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(DOCTOR_SUMMARY_TREND.DOCTOR_ID));
        Date startDate = new Date(param.getStartTime().getTime());
        Date endDate = new Date(param.getEndTime().getTime());
        select.where(DOCTOR_SUMMARY_TREND.TYPE.eq(StatisticConstant.TYPE_YESTODAY)).and(DOCTOR_SUMMARY_TREND.REF_DATE.ge(startDate)).and(DOCTOR_SUMMARY_TREND.REF_DATE.le(endDate));
        select.groupBy(DOCTOR_SUMMARY_TREND.DOCTOR_ID,DOCTOR.NAME);
        buildOptions(select, param);
//        if (param.getOrderField() != null) {
//            doctorFiledSorted(select, param);
//        }
        return this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), DoctorStatisticListVo.class);
    }

    /**
     * 医师搜索查询
     *
     * @param select
     * @param param
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, DoctorStatisticParam param) {
        if (param.getDoctorName() != null) {
            select.where(DOCTOR.NAME.like(likeValue(param.getDoctorName())));
        }
        if (param.getDepartmentId() != null) {
            select.where(DOCTOR_SUMMARY_TREND.DOCTOR_ID.in(param.getDoctorIds()));
        }
    }

    /**
     * 医师业绩列表(自定义时间段)
     *
     * @param param
     * @return
     */
    public PageResult<DoctorStatisticListVo> getDoctorListForType(DoctorStatisticParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR_SUMMARY_TREND.DOCTOR_ID,DOCTOR.NAME
                , DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER
                ,DOCTOR_SUMMARY_TREND.INQUIRY_MONEY,DOCTOR_SUMMARY_TREND.INQUIRY_NUMBER
                ,DOCTOR_SUMMARY_TREND.PRESCRIPTION_MONEY,DOCTOR_SUMMARY_TREND.PRESCRIPTION_NUM
                ,DOCTOR_SUMMARY_TREND.CONSUME_MONEY)
            .from(DOCTOR_SUMMARY_TREND)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(DOCTOR_SUMMARY_TREND.DOCTOR_ID));
        select.where(DOCTOR_SUMMARY_TREND.TYPE.eq(param.getType()));
        buildOptions(select, param);
//        if (param.getOrderField() != null) {
//            doctorFiledSorted(select, param);
//        }
        return this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), DoctorStatisticListVo.class);
    }

    public DoctorStatisticMinMaxVo getMinMaxStatisticData(Date refDate, Byte type){
        return db().select(DSL.min(DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER).as(MIN_CONSULTATION),DSL.max(DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER).as(MAX_CONSULTATION)
                ,DSL.min(DOCTOR_SUMMARY_TREND.INQUIRY_MONEY).as(MIN_INQUIRY_MONEY),DSL.max(DOCTOR_SUMMARY_TREND.INQUIRY_MONEY).as(MAX_INQUIRY_MONEY)
            ).from(DOCTOR_SUMMARY_TREND)
            .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
            .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
            .fetchAnyInto(DoctorStatisticMinMaxVo.class);
    }

    public DoctorSummaryTrendDo getOneInfo(Date refDate, Byte type, Integer doctorId){
        return db().selectFrom(DOCTOR_SUMMARY_TREND)
            .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
            .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
            .and(DOCTOR_SUMMARY_TREND.DOCTOR_ID.eq(doctorId))
            .fetchAnyInto(DoctorSummaryTrendDo.class);
    }

    public void updateDoctorStatisticInquiryScore(Byte type, Date refDate,DoctorStatisticMinMaxVo doctorStatisticMinMax) {
        BigDecimal differ = doctorStatisticMinMax.getMaxInquiryMoney().subtract(doctorStatisticMinMax.getMinInquiryMoney());
        if(BigDecimal.ZERO.equals(differ)) {
            db().update(DOCTOR_SUMMARY_TREND)
                .set(DOCTOR_SUMMARY_TREND.CONSUME_MONEY, BigDecimal.ZERO)
                .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
                .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
                .execute();
        } else {
            db().update(DOCTOR_SUMMARY_TREND)
                .set(DOCTOR_SUMMARY_TREND.CONSUME_MONEY,
                    (DOCTOR_SUMMARY_TREND.INQUIRY_MONEY.sub(doctorStatisticMinMax.getMinInquiryMoney())).multiply(differ))
                .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
                .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
                .execute();
        }

    }

    public void updateDoctorStatisticConsultationScore(Byte type, Date refDate,DoctorStatisticMinMaxVo doctorStatisticMinMax) {
        Integer differ = doctorStatisticMinMax.getMaxConsultation() - doctorStatisticMinMax.getMinConsultation();
        BigDecimal minConsultation = new BigDecimal(doctorStatisticMinMax.getMinConsultation());
        BigDecimal differDecimal = new BigDecimal(differ);
        if(INTEGER_ZERO.equals(differ)) {
            db().update(DOCTOR_SUMMARY_TREND)
                .set(DOCTOR_SUMMARY_TREND.CONSUME_MONEY, BigDecimal.ZERO)
                .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
                .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
                .execute();
        } else {
            db().update(DOCTOR_SUMMARY_TREND)
                .set(DOCTOR_SUMMARY_TREND.CONSUME_MONEY,
                    (DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER.cast(SQLDataType.DECIMAL(10,2)).subtract(minConsultation)).multiply(differDecimal))
                .where(DOCTOR_SUMMARY_TREND.TYPE.eq(type))
                .and(DOCTOR_SUMMARY_TREND.REF_DATE.eq(refDate))
                .execute();
        }

    }
}
