package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.*;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.dao.shop.rebate.PrescriptionRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.rebate.RebateConfig;
import com.vpu.mp.service.pojo.shop.config.rebate.RebateConfigConstant;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.rebate.*;
import com.vpu.mp.service.shop.config.RebateConfigService;
import com.vpu.mp.service.shop.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Service
public class PrescriptionRebateService extends ShopBaseService {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PrescriptionRebateDao prescriptionRebateDao;
    @Autowired
    private PrescriptionItemDao prescriptionItemDao;
    @Autowired
    private RebateConfigService rebateConfigService;
    @Autowired
    private GoodsMedicalInfoDao medicalInfoDao;
    @Autowired
    private OrderGoodsDao orderGoodsDao;

    /**
     * 处方返利信息入库
     * @param prescription
     */
    public void addPrescriptionRebate(PrescriptionDo prescription,OrderInfoDo order){
        List<PrescriptionItemDo> itemList=prescriptionItemDao.listOrderGoodsByPrescriptionCode(prescription.getPrescriptionCode());
        //计算应返利
        updatePrescriptionItem(itemList,order);
        //返利信息入库
        calculatePrescriptionRebate(prescription,itemList);
    }

    /**
     * 计算对应处方明细应返利
     * @param itemList
     * @param order
     */
    public void updatePrescriptionItem( List<PrescriptionItemDo> itemList,OrderInfoDo order){
        Double sums=itemList.stream().collect(Collectors.summingDouble(PrescriptionItemDo::getDragSumNum));
        BigDecimal avgScoreDiscount = BigDecimalUtil.divide(order.getScoreDiscount(), BigDecimal.valueOf(sums), RoundingMode.HALF_UP);

        for(PrescriptionItemDo item:itemList){
            RebateConfig rebateConfig=rebateConfigService.getRebateConfig();
            if(rebateConfig!=null&& RebateConfigConstant.SWITCH_ON.equals(rebateConfig.getStatus())){
                BigDecimal sharingProportion=rebateConfig.getGoodsSharingProportion().divide(BigDecimalUtil.BIGDECIMAL_100).setScale(BigDecimalUtil.FOUR_SCALE);
                BigDecimal rxProportion=rebateConfig.getRxMedicalDoctorProportion().divide(BigDecimalUtil.BIGDECIMAL_100).setScale(BigDecimalUtil.FOUR_SCALE);
                BigDecimal noRxProportion=rebateConfig.getNoRxMedicalDoctorProportion().divide(BigDecimalUtil.BIGDECIMAL_100).setScale(BigDecimalUtil.FOUR_SCALE);
                GoodsMedicalInfoDo medicalInfoDo = medicalInfoDao.getByGoodsId(item.getGoodsId());
                item.setGoodsSharingProportion(sharingProportion);
                if(MedicalGoodsConstant.IS_RX.equals(medicalInfoDo.getIsRx())){
                    item.setRebateProportion(rxProportion);
                }else {
                    item.setRebateProportion(noRxProportion);
                }
                OrderGoodsDo orderGoodsDo=orderGoodsDao.getOrderGoodsByOrderIdGoodsId(order.getOrderId(),item.getGoodsId());
                //可计算返利商品金额
                BigDecimal canRebateMoney = BigDecimalUtil.subtrac(orderGoodsDo.getDiscountedTotalPrice(), BigDecimalUtil.multiply(avgScoreDiscount, new BigDecimal(orderGoodsDo.getGoodsNumber())));
                canRebateMoney = BigDecimalUtil.compareTo(canRebateMoney, null) > 0 ? canRebateMoney : BigDecimalUtil.BIGDECIMAL_ZERO;
                item.setCanCalculateMoney(canRebateMoney);
                //应返利金额
                item.setTotalRebateMoney(item.getCanCalculateMoney().multiply(item.getGoodsSharingProportion())
                    .multiply(item.getRebateProportion())
                    .setScale(BigDecimalUtil.FOUR_SCALE,BigDecimal.ROUND_HALF_UP));
                prescriptionItemDao.updatePrescriptionItem(item);
            }

        }
    }

    /**
     * 返利信息入库
     * @param prescription
     * @param itemList
     */
    public void calculatePrescriptionRebate(PrescriptionDo prescription,List<PrescriptionItemDo> itemList){
        DoctorOneParam doctor=doctorService.getDoctorByCode(prescription.getDoctorCode());
        //总返利金额
        BigDecimal totalRebateMoney=itemList.stream().map(PrescriptionItemDo::getTotalRebateMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
        //总金额
        BigDecimal totalMoney=BigDecimal.ZERO;
        for(PrescriptionItemDo item :itemList){
            BigDecimal num=BigDecimal.valueOf(item.getDragSumNum());
            BigDecimal itemTotalMoney=item.getMedicinePrice().multiply(num);
            totalMoney=totalMoney.add(itemTotalMoney);
        }
        BigDecimal canRebateMoneyTotal=itemList.stream().map(PrescriptionItemDo::getCanCalculateMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
        PrescriptionRebateParam rebateParam=new PrescriptionRebateParam();
        rebateParam.setPrescriptionCode(prescription.getPrescriptionCode());
        rebateParam.setStatus(PrescriptionRebateConstant.TO_REBATE);
        rebateParam.setDoctorId(doctor.getId());
        rebateParam.setTotalMoney(totalMoney.setScale(BigDecimalUtil.DEFAULT_SCALE,BigDecimal.ROUND_HALF_UP));
        rebateParam.setTotalRebateMoney(totalRebateMoney.setScale(BigDecimalUtil.DEFAULT_SCALE,BigDecimal.ROUND_HALF_UP));
        rebateParam.setCanCalculateMoney(canRebateMoneyTotal);
        prescriptionRebateDao.addPrescriptionRebate(rebateParam);
    }
    /**
     * 分页查询列表
     * @param param
     * @return
     */
    public PageResult<PrescriptionRebateVo> getPageList(PrescriptionRebateListParam param){
        beginAndEndOfDay(param);
        PageResult<PrescriptionRebateVo> result=prescriptionRebateDao.getPageList(param);
        List<PrescriptionRebateVo> list=result.getDataList();
        for(PrescriptionRebateVo vo:list){
            List<PrescriptionItemDo> prescriptionItemDos =prescriptionItemDao.listOrderGoodsByPrescriptionCode(vo.getPrescriptionCode());
            vo.setMedicalList(prescriptionItemDos);
        }
        return result;
    }
    public void beginAndEndOfDay(PrescriptionRebateListParam param){
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate != null ) {
            startDate = DateUtil.beginOfDay(startDate).toTimestamp();
            param.setStartTime(startDate);
        }if( endDate != null){
            endDate = DateUtil.endOfDay(endDate).toTimestamp();
            param.setEndTime(endDate);
        }
    }
}
