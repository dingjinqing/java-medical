package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * 药方
 * @author 孔德成
 * @date 2020/7/6 16:28
 */
@Service
@Slf4j
public class PrescriptionProcessor implements Processor, CreateOrderProcessor {

    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private MedicalGoodsService medicalGoodsService;
    @Autowired
    private PatientService patientService;


    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_PRESCRIPTION;
    }

    /**
     * 药品是否有对应处方
     * @param param 参数
     * @throws MpException
     */
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        log.info("药品处方检查-开始");
        //患者
        if (param.getPatientId()==null||param.getPatientId().equals(0)){
            Integer integer = patientService.defaultPatientId(param.getWxUserInfo().getUserId());
            param.setPatientId(integer);
        }
        //订单药品信息 处方药和处方匹配
        List<PrescriptionVo> prescriptionList = medicalOrderInit(param);
        //处方状态,订单类型
        auditPrescriptionValid(param, prescriptionList);
        PatientOneParam oneInfo = patientService.getOneInfo(param.getPatientId());
        param.setPatientInfo(oneInfo);
        log.info("药品处方检查-结束");
    }

    /**
     * 审核处方
     * @param param
     * @param prescriptionList
     */
    private void auditPrescriptionValid(OrderBeforeParam param, List<PrescriptionVo> prescriptionList) {
        if (OrderConstant.MEDICAL_TYPE_RX.equals(param.getOrderMedicalType())){
            if (OrderConstant.CHECK_ORDER_PRESCRIPTION_PASS.equals(param.getCheckPrescriptionStatus())){
                log.info("处方药订单,药品与处方匹配通过--审核/直接通过");
                param.setOrderAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT);
                //处方单号去重
                prescriptionList = prescriptionList.stream()
                        .collect(Collectors.collectingAndThen
                                (Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PrescriptionVo::getPrescriptionCode))), ArrayList::new));
                param.setPrescriptionList(prescriptionList);
                //处方是否过期/是否使用
                long size = prescriptionList.stream().filter(prescriptionVo -> {
                    if (prescriptionVo.getSource().equals(PrescriptionConstant.SOURCE_MP_SYSTEM)) {
                        if (!PrescriptionConstant.EXPIRE_TYPE_INVALID.equals(prescriptionVo.getExpireType())) {
                            //系统内
                            if (BaseConstant.YES.equals(prescriptionVo.getIsValid())) {
                                //处方有效期内,没有被使用
                                return prescriptionVo.getIsUsed().equals(BaseConstant.NO);
                            }
                        }

                    }
                    return false;
                }).count();
                if (prescriptionList.size()==size){
                    log.info("所有处方都在有效-直接通过");
                    param.setOrderAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_PRESCRIPTION);
                }
            }else {
                log.info("处方药订单,存在没有匹配到订单得药品--线上开方");
                param.setOrderAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE);
            }
        }else {
            log.info("没有处方药-不审核");
            param.setOrderAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_NOT);
            param.setOrderMedicalType(OrderConstant.MEDICAL_TYPE_OTC);
        }
        //处理审核以外
        if (!param.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)){
            param.getGoods().forEach(goods -> goods.setMedicalAuditType(param.getOrderAuditType()));
        }
    }


    /**
     * 订单药品处方信息
     * @param param
     * @return
     */
    private List<PrescriptionVo> medicalOrderInit(OrderBeforeParam param) {
        List<PrescriptionVo> prescriptionList =new ArrayList<>();
        param.setCheckPrescriptionStatus(OrderConstant.CHECK_ORDER_PRESCRIPTION_NO_NEED);
        for (OrderBeforeParam.Goods goods : param.getGoods()) {
            GoodsRecord goodsInfo = goods.getGoodsInfo();
            GoodsMedicalInfoDo medicalInfo = medicalGoodsService.getByGoodsId(goodsInfo.getGoodsId());
            //商品的医疗信息
            if (medicalInfo != null&&medicalInfo.getIsRx().equals(BaseConstant.YES)) {
                param.setOrderMedicalType(OrderConstant.MEDICAL_TYPE_RX);
                goods.setMedicalInfo(medicalInfo);
                PrescriptionVo prescriptionVo = prescriptionService
                        .getByGoodsInfo(goods.getGoodsId(),param.getPatientId(), medicalInfo.getGoodsCommonName(), medicalInfo.getGoodsQualityRatio(), medicalInfo.getGoodsProductionEnterprise());
                //处方信息
                if (prescriptionVo != null) {
                    prescriptionList.add(prescriptionVo);
                    goods.setPrescriptionInfo(prescriptionVo);
                    goods.setPrescriptionCode(prescriptionVo.getPrescriptionCode());
                    param.setCheckPrescriptionStatus(OrderConstant.CHECK_ORDER_PRESCRIPTION_PASS);
                    if (prescriptionVo.getIsValid().equals(BaseConstant.YES) && !PrescriptionConstant.EXPIRE_TYPE_INVALID.equals(prescriptionVo.getExpireType())) {
                        if (prescriptionVo.getSource().equals(PrescriptionConstant.SOURCE_MP_SYSTEM) && prescriptionVo.getIsUsed().equals(BaseConstant.NO)) {
                            goods.setMedicalAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_PRESCRIPTION);
                        } else {
                            goods.setMedicalAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT);
                        }
                    } else {
                        goods.setMedicalAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT);
                    }
                } else {
                    log.info("{}处方药品没有找到对应的处方信息", goodsInfo.getGoodsName());
                    goods.setMedicalAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE);
                    param.setCheckPrescriptionStatus(OrderConstant.CHECK_ORDER_PRESCRIPTION_NO_PASS);
                }
            }else {
                goods.setMedicalAuditType(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_NOT);

            }
        }
        return prescriptionList;
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processUpdateStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processReturn(ReturnOrderRecord returnOrderRecord, Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {

    }

}
