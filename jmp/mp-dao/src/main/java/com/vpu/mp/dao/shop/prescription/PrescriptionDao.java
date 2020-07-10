package com.vpu.mp.dao.shop.prescription;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionSimpleVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRecord;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION_ITEM;

/**
 * 处方
 *
 * @author 孔德成
 * @date 2020/7/2 14:17
 */
@Repository
public class PrescriptionDao extends ShopBaseDao {

    /**
     * 添加
     *
     * @param param
     * @return
     */
    public int save(PrescriptionDo param) {
        PrescriptionRecord record = db().newRecord(PRESCRIPTION, param);
        return record.insert();
    }

    /**
     * 获取一条记录
     *
     * @param id
     * @return
     */
    public PrescriptionDo getById(Integer id) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(PrescriptionDo.class);
    }
    /**
     * 获取
     * @param prescriptionNo
     * @return
     */
    public PrescriptionInfoVo getInfoByPrescriptionNo(String prescriptionNo) {
         return db().select(PRESCRIPTION.PRESCRIPTION_NO,PRESCRIPTION.PATIENT_TREATMENT_NO,
                 PRESCRIPTION.PATIENT_NAME,PRESCRIPTION.PATIENT_AGE,PRESCRIPTION.PATIENT_SEX,PRESCRIPTION.DEPARTMENT_NAME,
                 PRESCRIPTION.DIAGNOSIS_NAME,PRESCRIPTION.PHARMACIST_NAME,PRESCRIPTION.PRESCRIPTION_CREATE_TIME)
                 .from(PRESCRIPTION)
                 .where(PRESCRIPTION.PRESCRIPTION_NO.eq(prescriptionNo))
                 .fetchAnyInto(PrescriptionInfoVo.class);
    }
    /**
     * 获取
     * @param prescriptionNo
     * @return
     */
    public PrescriptionVo getDoByPrescriptionNo(String prescriptionNo){
        return db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PRESCRIPTION_NO.eq(prescriptionNo))
                .fetchAnyInto(PrescriptionVo.class);
    }

    /**
     * 获取一条记录
     *
     * @param id
     * @return
     */
    public <E> E getById(Integer id, Class<? extends E> type) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(type);
    }

    /**
     * 分页
     *
     * @param param
     * @return
     */
    public PageResult<PrescriptionListVo> listPageResult(PrescriptionListParam param) {
        SelectConditionStep<Record> and = db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PATIENT_ID.eq(param.getPatientId()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        return getPageResult(and, param, PrescriptionListVo.class);
    }

    /**
     * 小程序分页
     * @param param
     * @return
     */
    public PageResult<PrescriptionSimpleVo> listPageResultWx(PrescriptionListParam param) {
        SelectConditionStep<Record> and = db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PATIENT_ID.eq(param.getPatientId()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        return getPageResult(and, param, PrescriptionSimpleVo.class);
    }


    /**
     * 获取有效处方通过商品id
     * @param goodsId 商品id
     * @return
     */
    public PrescriptionVo getValidByGoodsId(Integer goodsId ) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_NO.eq(PRESCRIPTION_ITEM.PRESCRIPTION_NO))
                .where(PRESCRIPTION_ITEM.GOODS_ID.eq(goodsId))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }



    /**
     * 通用有效名查询处方明细
     * @param goodsCommonName 商品名称
     * @return
     */
    public PrescriptionVo getValidByCommonName(String goodsCommonName){
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_NO.eq(PRESCRIPTION_ITEM.PRESCRIPTION_NO))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }

    /**
     * 通过有效通用名和规格系数查询处方明细
     * @param goodsCommonName 通用名
     * @param goodsQualityRatio 规格系数
     */
    public PrescriptionVo getValidByCommonNameAndQualityRatio(String goodsCommonName, String goodsQualityRatio) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_NO.eq(PRESCRIPTION_ITEM.PRESCRIPTION_NO))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION_ITEM.GOODS_QUALITY_RATIO.eq(goodsQualityRatio))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }
    /**
     * 通过有效通用名和规格系数查询处方明细
     * @param goodsCommonName 通用名
     * @param goodsQualityRatio 规格系数
     * @param productionEnterprise 生产企业
     */
    public PrescriptionVo getValidByCommonNameAndQualityRatio(String goodsCommonName, String goodsQualityRatio,String productionEnterprise) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_NO.eq(PRESCRIPTION_ITEM.PRESCRIPTION_NO))
                .leftJoin(GOODS_MEDICAL_INFO).on(GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE.eq(productionEnterprise))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION_ITEM.GOODS_QUALITY_RATIO.eq(goodsQualityRatio))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }


    /**
     * 患者未过期的历史处方no
     * @param patientId
     * @return
     */
    public List<String> getValidPrescriptionByPatient(Integer patientId) {
        return db().select(PRESCRIPTION.PRESCRIPTION_NO).from(PRESCRIPTION)
            .where(PRESCRIPTION.PATIENT_ID.eq(patientId)
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(String.class);
    }

    /**
     * 患者未过期的历史处方列表
     * @param patientId
     * @return
     */
    public List<PrescriptionInfoVo> listValidPrescriptionInfosByPatient(Integer patientId) {
        return db().select().from(PRESCRIPTION)
            .where(PRESCRIPTION.PATIENT_ID.eq(patientId)
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(PrescriptionInfoVo.class);
    }
}
