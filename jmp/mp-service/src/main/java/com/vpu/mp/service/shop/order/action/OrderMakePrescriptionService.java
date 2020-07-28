package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsMedicalOneInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsMedicalVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionMakeParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionOneParam;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**订单待开方
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Service
public class OrderMakePrescriptionService extends ShopBaseService implements IorderOperate<OrderToPrescribeQueryParam, PrescriptionMakeParam> {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private MedicalGoodsService medicalGoodsService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private PrescriptionService prescriptionService;
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
        List<OrderInfoVo> orders = orderInfoService.getOrdersByCondition(orderInfoService.TABLE.ORDER_AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE).and(orderInfoService.TABLE.ORDER_AUDIT_STATUS.eq(OrderConstant.MEDICAL_AUDIT_DEFAULT)) , OrderInfoVo.class);
        List<OrderGoodsMedicalVo> orderGoodsMedicalVoList=new ArrayList<>();
        for(OrderInfoVo orderInfo:orders){
            OrderGoodsMedicalVo orderGoodsMedicalVo=new OrderGoodsMedicalVo();
            FieldsUtil.assign(orderInfo,orderGoodsMedicalVo);
            List<GoodsMedicalOneInfoVo> goodsMedicalOneInfoVoList=new ArrayList<>();
            //根据goodsId查出orderGoods列表
            List<OrderGoodsDo> orderGoodsDoList=orderGoodsService.getByOrderId(orderInfo.getOrderId()).into(OrderGoodsDo.class);
            for(OrderGoodsDo orderGoodsDo:orderGoodsDoList ){
                //只需要开方的药品
                if(orderGoodsDo.getMedicalAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)){
                    GoodsMedicalInfoDo goodsMedicalInfoDo=medicalGoodsService.getByGoodsId(orderGoodsDo.getGoodsId());
                    GoodsMedicalOneInfoVo goodsMedicalOneInfoVo=new GoodsMedicalOneInfoVo();
                    FieldsUtil.assign(goodsMedicalInfoDo, goodsMedicalOneInfoVo);
                    GoodsDo goodsDo=goodsDao.getByGoodsId(goodsMedicalInfoDo.getGoodsId());
                    goodsMedicalOneInfoVo.setShopPrice(goodsDo.getShopPrice());
                    goodsMedicalOneInfoVoList.add(goodsMedicalOneInfoVo);
                }

            }
            orderGoodsMedicalVo.setGoodsMedicalOneInfoVoList(goodsMedicalOneInfoVoList);
            orderGoodsMedicalVoList.add(orderGoodsMedicalVo);
        }
        return orderGoodsMedicalVoList;
    }

    /**
     * 生成处方
     * @param obj 参数
     * @return
     */
    @Override
    public ExecuteResult execute(PrescriptionMakeParam obj) {
        PrescriptionOneParam prescriptionOneParam=new PrescriptionOneParam();
        FieldsUtil.assign(obj,prescriptionOneParam);
        //生成处方，处方明细
        prescriptionService.insertPrescription(prescriptionOneParam);
        //更新审核状态
        orderInfoService.updateAuditStatus(obj.getOrderId(),OrderConstant.MEDICAL_AUDIT_PASS);
        orderGoodsService.updateAuditStatus(obj.getGoodsIdList(),OrderConstant.MEDICAL_AUDIT_PASS);
        return ExecuteResult.create();
    }


}
