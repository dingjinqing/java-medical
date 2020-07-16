package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionOrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单处方处理类
 *  获取订单处方信息
 *  续方
 *  驳回
 * @author 孔德成
 * @date 2020/7/9 9:30
 */
@Service
public class OrderPrescriptionService  extends ShopBaseService implements IorderOperate<PrescriptionQueryParam, OrderOperateQueryParam> {
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private OrderInfoService orderInfo;
    @Autowired
    private OrderGoodsService orderGoods;
    @Autowired
    private MedicalGoodsService medicalGoodsService;


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
        List<OrderInfoVo> orders = orderInfo.getOrdersByCondition(orderInfo.TABLE.MAIN_ORDER_SN.eq("")
                .or(orderInfo.TABLE.ORDER_SN.eq(param.getOrderSn())) , OrderInfoVo.class);
        int size = orders.size();
        if(size == 0) {
            return null;
        }
        OrderInfoVo orderInfoVo = orders.get(0);
        //查询商品行
        List<OrderGoodsDo> goodsDoList = orderGoods.getByOrderId(orderInfoVo.getOrderId()).into(OrderGoodsDo.class);
        Map<String, PrescriptionOrderGoodsVo> defailtMap =new HashMap<>();
        Map<String, PrescriptionOrderGoodsVo> auditPassMap =new HashMap<>();
        for (OrderGoodsDo orderGoodsDo : goodsDoList) {
            GoodsMedicalInfoDo medicalInfo = medicalGoodsService.getByGoodsId(orderGoodsDo.getGoodsId());
            //商品的医疗信息
            if (medicalInfo != null && medicalInfo.getIsRx().equals(BaseConstant.YES)) {
                //未审核
                orderGoodsAuditDefault(defailtMap, orderGoodsDo, medicalInfo);
                //审核通过的
                orderGoodsAuditPass(auditPassMap, orderGoodsDo);
            }
        }
        PrescriptionQueryVo vo =new PrescriptionQueryVo();
        vo.setList(new ArrayList<>(auditPassMap.values()));
        vo.getList().addAll(new ArrayList<>(defailtMap.values()));
        return vo;
    }

    /**
     * 未审核
     * @param prescriptionMap
     * @param orderGoodsDo
     */
    private void orderGoodsAuditPass(Map<String, PrescriptionOrderGoodsVo> prescriptionMap, OrderGoodsDo orderGoodsDo) {
        if (orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_PASS)){
            PrescriptionVo prescriptionVo = prescriptionService.getDoByPrescriptionNo(orderGoodsDo.getPrescriptionOldNo());
            if (prescriptionVo!=null){
                PrescriptionOrderGoodsVo pog =new PrescriptionOrderGoodsVo();
                pog.setPrescriptionVo(prescriptionVo);
                PrescriptionOrderGoodsVo preOrderGoods = prescriptionMap.putIfAbsent(prescriptionVo.getPrescriptionCode(), pog);
                preOrderGoods.getOrderGoodsList().add(orderGoodsDo);
            }
        }
    }

    /**
     * 审核通过的
     * @param prescriptionMap
     * @param orderGoodsDo
     * @param medicalInfo
     */
    private void orderGoodsAuditDefault(Map<String, PrescriptionOrderGoodsVo> prescriptionMap, OrderGoodsDo orderGoodsDo, GoodsMedicalInfoDo medicalInfo) {
        if (orderGoodsDo.getMedicalAuditStatus().equals(OrderConstant.MEDICAL_AUDIT_DEFAULT)){
            //todo  订单表增加患者字段,下单时绑定患者id
            PrescriptionVo prescriptionVo = prescriptionService
                    .getByGoodsInfo(orderGoodsDo.getGoodsId(), 1, medicalInfo.getGoodsCommonName(), medicalInfo.getGoodsQualityRatio(), medicalInfo.getGoodsProductionEnterprise());
            //处方信息
            if (prescriptionVo != null) {
                PrescriptionOrderGoodsVo pog =new PrescriptionOrderGoodsVo();
                pog.setPrescriptionVo(prescriptionVo);
                PrescriptionOrderGoodsVo preOrderGoods = prescriptionMap.putIfAbsent(prescriptionVo.getPrescriptionCode(), pog);
                preOrderGoods.getOrderGoodsList().add(orderGoodsDo);
            } else {
                logger().info("{}药品没有找到对应的处方信息", orderGoodsDo.getGoodsName());
            }
        }
    }

    /**
     * 审核
     * 通过
     * 不通过
     * @param obj 参数
     * @return
     */
    @Override
    public ExecuteResult execute(OrderOperateQueryParam obj) {
        return null;
    }
}
