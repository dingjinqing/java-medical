package com.vpu.mp.service.shop.order.action;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.Order;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.DepartmentCodeVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderPrescriptionVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionOrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.AuditOrderGoodsParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.AuditOrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.OrderGoodsSimpleAuditVo;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionItemInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionSimpleVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.message.UserMessageService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 订单处方处理类
 *  获取订单处方信息
 *  续方
 *  驳回
 * @author 孔德成
 * @date 2020/7/9 9:30
 */
@Service
public class OrderPrescriptionService  extends ShopBaseService implements IorderOperate<PrescriptionQueryParam, AuditOrderGoodsParam> {
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private OrderInfoService orderInfo;
    @Autowired
    private OrderGoodsService orderGoods;
    @Autowired
    private MedicalGoodsService medicalGoodsService;
    @Autowired
    private ReturnService  returnService;
    @Autowired
    private UserMessageService messageService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PrescriptionDao prescriptionDao;
    @Autowired
    private PrescriptionItemDao prescriptionItemDao;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private DoctorDepartmentCoupleDao doctorDepartmentCoupleDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private GoodsMedicalInfoDao medicalInfoDao;


    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.PRESCRIPTION;
    }

    /**
     * 获取订单得处方和商品关联信息
     * @param param 参数
     * @return
     * @throws MpException
     */
    @Override
    public Object query(PrescriptionQueryParam param) throws MpException {
        if (param.getOrderId()!=null){
            return getOneOrderPrescription(param);
        }
        PageResult<OrderPrescriptionVo> orderPrescriptionVoList = orderGoodsDao.listGoodsOldPrescription(param);
        List<String> prescriptionCodeList = orderPrescriptionVoList.getDataList().stream().map(OrderPrescriptionVo::getPrescriptionOldCode).distinct().collect(Collectors.toList());
        List<Integer> orderIdList = orderPrescriptionVoList.getDataList().stream().map(OrderPrescriptionVo::getOrderId).distinct().collect(Collectors.toList());
        Map<String, PrescriptionSimpleVo> prescriptionMap = prescriptionDao.mapPrescriptionByCode(prescriptionCodeList, PrescriptionSimpleVo.class);
        Map<Integer, List<OrderGoodsDo>> goodsVoMap = orderGoodsDao.mapOrderGoodsByOrderId(orderIdList, prescriptionCodeList);
        PageResult<AuditOrderGoodsVo> pageResult =new PageResult<>();
        pageResult.setDataList(new ArrayList<>());
        pageResult.setPage(orderPrescriptionVoList.page);
        goodsVoMap.forEach((goodsId,goodsList) -> {
            List<String> prescriptionCode = goodsList.stream().map(OrderGoodsDo::getPrescriptionOldCode).distinct().collect(Collectors.toList());
            prescriptionCode.forEach(code->{
                AuditOrderGoodsVo vo =new AuditOrderGoodsVo();
                vo.setPrescription(prescriptionMap.get(code));
                List<OrderGoodsDo> goodsDoList = goodsList.stream().filter(goods -> goods.getPrescriptionOldCode().equals(code)).collect(Collectors.toList());
                vo.setGoodsList(goodsDoList);
                vo.setTime(goodsDoList.get(0).getCreateTime());
                vo.setOrderId(goodsDoList.get(0).getOrderId());
                vo.setOrderSn(goodsDoList.get(0).getOrderSn());
                pageResult.getDataList().add(vo);
            });
        });
        return pageResult;
    }

    private Object getOneOrderPrescription(PrescriptionQueryParam param) {
        OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
        if(order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        //查询商品行
        List<OrderGoodsDo> goodsDoList = orderGoods.getByOrderId(order.getOrderId()).into(OrderGoodsDo.class);
        //已审核
        List<String> auditedPrescriptionCodes = auditedPrescriptionCode(goodsDoList);
        //未审核
        List<String> unauditedPrescriptionCodes = unauditedPrescriptionCode(goodsDoList);
        Map<String, PrescriptionOrderGoodsVo> auditedMap =new HashMap<>();
        Map<String, PrescriptionOrderGoodsVo> unauditedMap =new HashMap<>();
        assembleData(goodsDoList, auditedPrescriptionCodes, unauditedPrescriptionCodes, auditedMap, unauditedMap);
        PrescriptionQueryVo vo =new PrescriptionQueryVo();
        vo.setUnauditedList(new ArrayList<>(unauditedMap.values()));
        vo.setAuditedList(new ArrayList<>(auditedMap.values()));
        return vo;
    }

    private void assembleData(List<OrderGoodsDo> goodsDoList, List<String> auditedPrescriptionCodes, List<String> unauditedPrescriptionCodes, Map<String, PrescriptionOrderGoodsVo> auditedMap, Map<String, PrescriptionOrderGoodsVo> unauditedMap) {
        List<String> allCode =new ArrayList<>();
        allCode.addAll(auditedPrescriptionCodes);
        allCode.addAll(unauditedPrescriptionCodes);
        List<PrescriptionVo> prescriptionVos = prescriptionDao.listPrescriptionList(allCode);
        prescriptionVos.forEach(prescriptionVo -> {
            PrescriptionOrderGoodsVo pog =new PrescriptionOrderGoodsVo();
            pog.setPrescriptionVo(prescriptionVo);
            if (auditedPrescriptionCodes.contains(prescriptionVo.getPrescriptionCode())){
                auditedMap.putIfAbsent(prescriptionVo.getPrescriptionCode(), pog);
            }
            if (unauditedPrescriptionCodes.contains(prescriptionVo.getPrescriptionCode())){
                unauditedMap.putIfAbsent(prescriptionVo.getPrescriptionCode(), pog);
            }
        });
        for (OrderGoodsDo orderGoodsDo : goodsDoList) {
            if (orderGoodsDo.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)){
                if (orderGoodsDo.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                     if (orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS)&&auditedMap.containsKey(orderGoodsDo.getPrescriptionCode())){
                         auditedMap.get(orderGoodsDo.getPrescriptionCode()).getOrderGoodsList().add(orderGoodsDo);
                     }else if (orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT)&&unauditedMap.containsKey(orderGoodsDo.getPrescriptionOldCode())){
                         unauditedMap.get(orderGoodsDo.getPrescriptionCode()).getOrderGoodsList().add(orderGoodsDo);
                     }
                }
            }
        }
    }

    private List<String> unauditedPrescriptionCode(List<OrderGoodsDo> goodsDoList) {
        List<OrderGoodsDo> unauditedGoodsList = goodsDoList.stream().filter(goods -> {
            if (goods.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                return goods.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT);
            }
            return false;
        }).collect(Collectors.toList());
        return unauditedGoodsList.stream().map(OrderGoodsDo::getPrescriptionOldCode).distinct().collect(Collectors.toList());
    }

    private List<String> auditedPrescriptionCode(List<OrderGoodsDo> goodsDoList) {
        List<OrderGoodsDo> auditedGoodsList = goodsDoList.stream().filter(goods -> {
            if (goods.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                return goods.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS);
            }
            return false;
        }).collect(Collectors.toList());
        return auditedGoodsList.stream().map(OrderGoodsDo::getPrescriptionCode).distinct().collect(Collectors.toList());
    }


    /**
     * 审核
     * 通过
     * 不通过
     * @return
     */
    @Override
    public ExecuteResult execute(AuditOrderGoodsParam param)  {
        logger().info("审核订单-审核-开始");
        //检查
        OrderInfoDo orderInfoDo = orderInfo.getByOrderId(param.getOrderId(), OrderInfoDo.class);
        ExecuteResult x = checkOrder(orderInfoDo);
        if (x != null) {
            return x;
        }
        List<OrderGoodsSimpleAuditVo> allGoods = orderGoodsDao.listSimpleAuditByOrderId(orderInfoDo.getOrderId());
        //修改订单状态
        transaction(() -> {
            List<Integer> unAuditGoodsId = getUnAuditRecIds(param, allGoods);
            //审核通过
            auditPass(param, orderInfoDo, allGoods, unAuditGoodsId);
            //审核不通过
            auditNotPass(param, orderInfoDo,unAuditGoodsId);
        });
        logger().info("审核订单-审核-结束");
        return null;
    }

    private void auditNotPass(AuditOrderGoodsParam param, OrderInfoDo orderInfoDo,List<Integer> unAuditGoodsId) {
        if (param.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_NOT_PASS)){
            logger().info("orderId:{}审核不通过",orderInfoDo.getOrderId());
            orderGoodsDao.updateAuditStatusByRecIds(unAuditGoodsId,OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            orderInfoDao.updateAuditStatus(orderInfoDo.getOrderId(),OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            //退款
            returnService.auditNotPassRefund(orderInfoDo.getOrderSn());
        }
    }

    private void auditPass(AuditOrderGoodsParam param, OrderInfoDo orderInfoDo, List<OrderGoodsSimpleAuditVo> allGoods, List<Integer> unAuditGoodsId) {
        if (param.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS)){
            logger().info("orderId:{}审核通过",orderInfoDo.getOrderId());
            //生成处方
            PrescriptionVo prescriptionVo = savePrescription(param, orderInfoDo,allGoods,unAuditGoodsId);
            //修改状态
            orderGoodsDao.updateAuditedToWaitDelivery(unAuditGoodsId,prescriptionVo.getPrescriptionCode());
            orderGoodsDao.updateAuditStatusByRecIds(unAuditGoodsId,OrderConstant.MEDICAL_AUDIT_PASS);
            List<Integer> allUnAuditRecIds = getAllUnAuditRecIds(allGoods);
            if (allUnAuditRecIds.containsAll(unAuditGoodsId)){
                logger().info("订单处方全部通过");
                orderInfo.setOrderstatus(orderInfoDo.getOrderSn(), OrderConstant.ORDER_WAIT_DELIVERY);
            }
        }
    }

    /**
     * 处方
     * @param param
     * @param orderInfoDo
     * @return
     */
    private PrescriptionVo savePrescription(AuditOrderGoodsParam param, OrderInfoDo orderInfoDo,List<OrderGoodsSimpleAuditVo> allGoods,List<Integer> unAuditGoodsId) {
        Timestamp time = DateUtil.date().toTimestamp();
        DoctorOneParam doctor = doctorDao.getOneInfo(param.getDoctorId());
        DepartmentCodeVo departmentOne = doctorDepartmentCoupleDao.getOneCodeByByDoctorId(param.getDoctorId());
        PatientOneParam patient = patientDao.getOneInfo(orderInfoDo.getPatientId());
        PrescriptionVo prescriptionVo = prescriptionDao.getDoByPrescriptionNo(param.getPrescriptionOldCode());
        List<PrescriptionItemInfoVo> itemInfoVoList = prescriptionItemDao.listByPrescriptionNo(prescriptionVo.getPrescriptionCode());
        prescriptionVo.setId(null);
        prescriptionVo.setPrescriptionCode(System.currentTimeMillis()+"");
        prescriptionVo.setPosCode("");
        prescriptionVo.setPatientId(orderInfoDo.getPatientId());
        prescriptionVo.setUserId(orderInfoDo.getUserId());
        prescriptionVo.setPatientAge(DateUtil.ageOfNow(patient.getBirthday()));
        prescriptionVo.setDepartmentCode(departmentOne.getCode());
        prescriptionVo.setDepartmentName(departmentOne.getName());
        prescriptionVo.setDoctorCode(doctor.getHospitalCode());
        prescriptionVo.setDoctorName(doctor.getName());
        prescriptionVo.setDiagnoseTime(time);
        prescriptionVo.setPharmacistCode("007");
        prescriptionVo.setPharmacistName("张三");
        prescriptionVo.setDoctorAdvice(param.getDoctorAdvice());
        prescriptionVo.setPatientComplain("");
        prescriptionVo.setPatientSign("");
        prescriptionVo.setSource(PrescriptionConstant.SOURCE_MP_SYSTEM);
        prescriptionVo.setStatus(PrescriptionConstant.STATUS_PASS);
        prescriptionVo.setStatusMemo("");
        prescriptionVo.setExpireType(PrescriptionConstant.EXPIRE_TYPE_TIME);
        prescriptionVo.setPrescriptionExpireTime(DateUtils.getTimeStampPlus(time,PrescriptionConstant.PRESCRIPTION_EXPIRE_DAY, ChronoUnit.DAYS));
        prescriptionVo.setIsValid(BaseConstant.YES);
        prescriptionVo.setIsUsed(BaseConstant.YES);
        prescriptionDao.save(prescriptionVo);
        List<PrescriptionItemDo> list =new ArrayList<>();
        allGoods.forEach(goods->{
            if (unAuditGoodsId.contains(goods.getRecId())){
                GoodsMedicalInfoDo medicalInfoDo = medicalInfoDao.getByGoodsId(goods.getGoodsId());
                PrescriptionItemDo itemDo =new PrescriptionItemDo();
                itemDo.setPrescriptionCode(prescriptionVo.getPrescriptionCode());
                itemDo.setPrescriptionDetailCode(prescriptionVo.getPrescriptionCode());
                itemDo.setGoodsId(goods.getGoodsId());
                itemDo.setGoodsCommonName(medicalInfoDo.getGoodsCommonName());
                itemDo.setGoodsQualityRatio(medicalInfoDo.getGoodsQualityRatio());
                itemDo.setUseMethod(medicalInfoDo.getGoodsUseMethod());
                itemDo.setPerTimeNum(1.0);
                itemDo.setPerTimeUnit(medicalInfoDo.getGoodsBasicUnit());
                itemDo.setPerTimeDosage(1.0);
                itemDo.setPerTimeDosageUnit(medicalInfoDo.getGoodsBasicUnit());
                itemDo.setFrequency(3.0);
                itemDo.setDragSumNum((double)goods.getGoodsNumber());
                itemDo.setDragSumUnit(medicalInfoDo.getGoodsPackageUnit());
                list.add(itemDo);
            }
        });
        prescriptionItemDao.batchSave(list);
        return prescriptionVo;
    }

    private List<Integer> getAllUnAuditRecIds(List<OrderGoodsSimpleAuditVo> allGoods) {
        return allGoods.stream().filter(goods -> {
            if (goods.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                return goods.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT);
            }
            return false;
        }).map(OrderGoodsSimpleAuditVo::getRecId).collect(Collectors.toList());
    }

    private List<Integer> getUnAuditRecIds(AuditOrderGoodsParam param, List<OrderGoodsSimpleAuditVo> allGoods) {
        return allGoods.stream().filter(goods -> {
            if (goods.getPrescriptionOldCode().equals(param.getPrescriptionOldCode())) {
                if (goods.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                    return goods.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT);
                }
            }
            return false;
        }).map(OrderGoodsSimpleAuditVo::getRecId).collect(Collectors.toList());
    }



    private ExecuteResult checkOrder(OrderInfoDo orderInfoDo) {
        if (orderInfoDo==null){
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, "订单不存在", null);
        }
        if (!orderInfoDo.getOrderStatus().equals(OrderConstant.ORDER_TO_AUDIT)){
            logger().info("订单状态不是待审核");
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, "订单状态不是待审核", null);

        }
        if (!orderInfoDo.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)){
            logger().info("不是审核订单");
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, "不是审核订单", null);

        }
        if (!orderInfoDo.getOrderAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT)){
            logger().info("商品不是待审核状态");
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, "商品不是待审核状态", null);
        }
        return null;
    }
}
