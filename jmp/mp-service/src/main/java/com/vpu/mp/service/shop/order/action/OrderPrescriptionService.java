package com.vpu.mp.service.shop.order.action;

import cn.hutool.db.sql.Order;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
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
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionSimpleVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.message.MessageService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private MessageService messageService;
    @Autowired
    private PrescriptionDao prescriptionDao;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ReturnService  returnService;


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
        //修改订单状态
        List<OrderGoodsSimpleAuditVo> allGoods = orderGoodsDao.listSimpleAuditByOrderId(orderInfoDo.getOrderId());
        List<Integer> unAuditGoodsId = getUnAuditRecIds(param, allGoods);
        //审核通过
        auditPass(param, orderInfoDo, allGoods, unAuditGoodsId);
        //审核不通过
        if (param.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_NOT_PASS)){
            logger().info("orderId:{}审核不通过",orderInfoDo.getOrderId());
            orderGoodsDao.updateAuditStatusByRecIds(unAuditGoodsId,OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            orderInfoDao.updateAuditStatus(orderInfoDo.getOrderId(),OrderConstant.MEDICAL_AUDIT_NOT_PASS);
            //退款
            returnService.auditNotPassRefund(orderInfoDo.getOrderSn());
        }
        logger().info("审核订单-审核-开始");
        return null;
    }

    private void auditPass(AuditOrderGoodsParam param, OrderInfoDo orderInfoDo, List<OrderGoodsSimpleAuditVo> allGoods, List<Integer> unAuditGoodsId) {
        if (param.getAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS)){
            logger().info("orderId:{}审核通过",orderInfoDo.getOrderId());
            //生成处方
            PrescriptionVo prescriptionVo = prescriptionDao.getDoByPrescriptionNo(param.getPrescriptionOldCode());
            //修改状态
            orderGoodsDao.updateAuditedToWaitDelivery(unAuditGoodsId,prescriptionVo.getPrescriptionCode());
            List<Integer> allUnAuditRecIds = getAllUnAuditRecIds(allGoods);
            if (allUnAuditRecIds.containsAll(unAuditGoodsId)){
                logger().info("订单处方全部通过");
                orderInfo.setOrderstatus(orderInfoDo.getOrderSn(), OrderConstant.ORDER_WAIT_DELIVERY);
            }
        }
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
