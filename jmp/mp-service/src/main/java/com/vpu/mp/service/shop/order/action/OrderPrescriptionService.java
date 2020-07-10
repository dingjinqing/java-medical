package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionOrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryParam;
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
        //OrderIds
        List<Integer> orderIds = new ArrayList<Integer>(size);
        //构造参数
        for (OrderInfoVo order : orders) {
            orderIds.add(order.getOrderId());
        }
        //查询商品行
        Map<Integer, List<OrderGoodsVo>> goods = orderGoods.getByOrderIds(orderIds.toArray(new Integer[0]))
                .intoGroups(orderGoods.TABLE.ORDER_ID,OrderGoodsVo.class);
        List<OrderGoodsVo> orderGoodsVos = goods.get(orderInfoVo.getOrderId());
        Map<String, PrescriptionOrderGoodsVo> prescriptionMap =new HashMap<>();
        for (OrderGoodsVo orderGoodsVo : orderGoodsVos) {
            GoodsMedicalInfoDo medicalInfo = medicalGoodsService.getByGoodsId(orderGoodsVo.getGoodsId());
            //商品的医疗信息
            if (medicalInfo != null && medicalInfo.getIsRx().equals(BaseConstant.YES)) {
                PrescriptionVo prescriptionVo = prescriptionService
                        .getByGoodsInfo(orderGoodsVo.getGoodsId(), medicalInfo.getGoodsCommonName(), medicalInfo.getGoodsQualityRatio(), medicalInfo.getGoodsProductionEnterprise());
                //处方信息
                if (prescriptionVo != null) {
                    PrescriptionOrderGoodsVo pog =new PrescriptionOrderGoodsVo();
                    pog.setPrescriptionVo(prescriptionVo);
                    PrescriptionOrderGoodsVo preOrderGoods = prescriptionMap.putIfAbsent(prescriptionVo.getPrescriptionNo(), pog);
                    preOrderGoods.getOrderGoodsList().add(orderGoodsVo);
                } else {
                    logger().info("{}药品没有找到对应的处方信息", orderGoodsVo.getGoodsName());
                }
            }
        }



        return null;
    }

    @Override
    public ExecuteResult execute(OrderOperateQueryParam obj) {
        return null;
    }
}
