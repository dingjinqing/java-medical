package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionItemVo;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<PrescriptionVo> itemList =new ArrayList<>();
        param.getGoods().forEach(goods -> {
            GoodsRecord goodsInfo = goods.getGoodsInfo();
            GoodsMedicalInfoDo medicalInfo = medicalGoodsService.getByGoodsId(goodsInfo.getGoodsId());
            //商品的医疗信息
            if (medicalInfo!=null){
                goods.setMedicalInfo(medicalInfo);
                PrescriptionVo prescriptionVo = prescriptionService
                        .getByGoodsInfo(goods.getGoodsId(), medicalInfo.getGoodsCommonName(), medicalInfo.getGoodsQualityRatio(),medicalInfo.getGoodsProductionEnterprise());
                //处方信息
                if (prescriptionVo!=null){
                    itemList.add(prescriptionVo);
                }else {
                    log.info("{}药品没有处方信息",goodsInfo.getGoodsName());
                }
            }
        });
        //处方单号
        List<String> prescriptionNoList = itemList.stream().map(PrescriptionVo::getPrescriptionNo).distinct().collect(Collectors.toList());
       prescriptionService.ListSimpleByprescriptionNo(prescriptionNoList);


        log.info("药品处方检查-结束");
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
