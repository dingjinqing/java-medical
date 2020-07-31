package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderMedicalHistoryDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.order.OrderMedicalHistoryDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsMedicalOneInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsMedicalVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionMakeParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientDetailVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionOneParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionParam;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**订单待开方
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Service
public class OrderMakePrescriptionService extends ShopBaseService implements IorderOperate<OrderToPrescribeQueryParam, PrescriptionMakeParam> {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private MedicalGoodsService medicalGoodsService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private OrderMedicalHistoryDao orderMedicalHistoryDao;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ReturnService  returnService;
    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.MAKE_PRESCRIPTION;
    }

    /**
     * 获取待开方订单以及关联药品信息
     * @param param 参数
     * @return
     * @throws MpException
     */
    @Override
    public Object query(OrderToPrescribeQueryParam param) throws MpException {
        PageResult<OrderInfoVo> orderPageResult=orderInfoDao.listOrderInfo(param);
        List<OrderInfoVo> orderList=orderPageResult.getDataList();
        PageResult<OrderGoodsMedicalVo> pageResult=new PageResult<>();
        pageResult.setPage(orderPageResult.getPage());
        List<OrderGoodsMedicalVo> orderGoodsMedicalVoList=new ArrayList<>();
        for(OrderInfoVo orderInfo:orderList){
            OrderGoodsMedicalVo orderGoodsMedicalVo=new OrderGoodsMedicalVo();
            FieldsUtil.assign(orderInfo,orderGoodsMedicalVo);
            //患者信息
            UserPatientParam userPatientParam = new UserPatientParam();
            userPatientParam.setPatientId(orderInfo.getPatientId());
            userPatientParam.setUserId(orderInfo.getUserId());
            UserPatientDetailVo patient=patientService.getOneDetail(userPatientParam);
            orderGoodsMedicalVo.setPatient(patient);
            //历史诊断
            OrderMedicalHistoryDo medicalHistoryDo= orderMedicalHistoryDao.getByOrderId(orderInfo.getOrderId());
            if(medicalHistoryDo!=null){
                medicalHistoryDo.setDiseaseHistory(splitDiseaseHistory(medicalHistoryDo.getDiseaseHistory()));
            }
            orderGoodsMedicalVo.setMedicalHistory(medicalHistoryDo);
            //药品数组
            List<GoodsMedicalOneInfoVo> goodsMedicalOneInfoVoList=new ArrayList<>();
            //根据goodsId查出orderGoods列表
            List<OrderGoodsDo> orderGoodsDoList=orderGoodsService.getByOrderId(orderInfo.getOrderId()).into(OrderGoodsDo.class);
            for(OrderGoodsDo orderGoodsDo:orderGoodsDoList ){
                //只需要开方的药品
                if(orderGoodsDo.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)&&orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT)){
                    GoodsMedicalInfoDo goodsMedicalInfoDo=medicalGoodsService.getByGoodsId(orderGoodsDo.getGoodsId());
                    GoodsMedicalOneInfoVo goodsMedicalOneInfoVo=new GoodsMedicalOneInfoVo();
                    FieldsUtil.assign(goodsMedicalInfoDo, goodsMedicalOneInfoVo);
                    GoodsDo goodsDo=goodsDao.getByGoodsId(goodsMedicalInfoDo.getGoodsId());
                    goodsMedicalOneInfoVo.setShopPrice(goodsDo.getShopPrice());
                    goodsMedicalOneInfoVo.setGoodsImg(goodsDo.getGoodsImg());
                    goodsMedicalOneInfoVo.setGoodsNumber(orderGoodsDo.getGoodsNumber());
                    goodsMedicalOneInfoVoList.add(goodsMedicalOneInfoVo);
                }

            }
            orderGoodsMedicalVo.setGoodsMedicalOneInfoVoList(goodsMedicalOneInfoVoList);
            orderGoodsMedicalVoList.add(orderGoodsMedicalVo);
        }
        pageResult.setDataList(orderGoodsMedicalVoList);
        return pageResult;
    }

    /**
     * 截图json字符串
     * @param diseaseHistory
     * @return
     */
    public static String splitDiseaseHistory(String diseaseHistory){
        if(StringUtils.isBlank(diseaseHistory)){
            return null;
        }
        int index=diseaseHistory.indexOf("{");
        return  diseaseHistory.substring(index,diseaseHistory.length()-1);

    }

    /**
     * 生成处方
     * @param obj 参数
     * @return
     */
    @Override
    public ExecuteResult execute(PrescriptionMakeParam obj) {
        logger().info("医师开方-开始");
        OrderInfoDo orderInfoDo=orderInfoService.getByOrderId(obj.getOrderId(),OrderInfoDo.class);
        if(!orderInfoDo.getOrderStatus().equals(OrderConstant.ORDER_TO_AUDIT_OPEN)){
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_STATUS_ALREADY_CHANGE,null);
        }
        List<OrderGoodsDo> orderGoodsDoList=orderGoodsService.getByOrderId(obj.getOrderId()).into(OrderGoodsDo.class);
        List<Integer> recIds=getUnAuditAllRecIds(orderGoodsDoList);
        if(obj.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS)){
            logger().info("orderId:{}开方通过",orderInfoDo.getOrderId());
            PrescriptionOneParam prescriptionOneParam=new PrescriptionOneParam();
            FieldsUtil.assign(obj,prescriptionOneParam);
            prescriptionOneParam.setUserId(orderInfoDo.getUserId());
            prescriptionOneParam.setIsUsed((byte)1);
            transaction(() -> {
                //生成处方，处方明细
                PrescriptionParam prescription=prescriptionService.insertPrescription(prescriptionOneParam);
                //更新状态
                orderInfoService.setOrderstatus(orderInfoDo.getOrderSn(),OrderConstant.ORDER_WAIT_DELIVERY);

                orderGoodsDao.updateAuditStatusByRecIds(recIds, OrderConstant.MEDICAL_AUDIT_PASS);
                //更新处方号
                orderGoodsService.updatePrescriptionCode(obj.getOrderId(),prescription.getPrescriptionCode());
            });
        }else if(obj.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_NOT_PASS)){
            //审核未通过 驳回
            logger().info("orderId:{}开方驳回",orderInfoDo.getOrderId());
            //更新状态
            orderInfoDao.updateAuditStatus(orderInfoDo.getOrderId(),OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            orderGoodsDao.updateAuditStatusByRecIds(recIds, OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            //退款
            returnService.auditNotPassRefund(orderInfoDo.getOrderSn());
        }
        logger().info("医师开方-结束");

        return ExecuteResult.create();
    }

    public List<Integer> getUnAuditAllRecIds(List<OrderGoodsDo> orderGoodsDoList){
        return orderGoodsDoList.stream().filter(orderGoodsDo->{
            if(orderGoodsDo.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)){
                return orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT);
            }
            return false;
        }).map(OrderGoodsDo::getRecId).collect(Collectors.toList());
    }


}
