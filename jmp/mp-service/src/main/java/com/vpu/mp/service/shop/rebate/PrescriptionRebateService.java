package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.*;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.dao.shop.rebate.PrescriptionRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.rebate.RebateConfig;
import com.vpu.mp.service.pojo.shop.config.rebate.RebateConfigConstant;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.pojo.shop.rebate.*;
import com.vpu.mp.service.shop.config.RebateConfigService;
import com.vpu.mp.service.shop.doctor.DoctorService;
import org.apache.poi.ss.usermodel.Workbook;
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
    @Autowired
    private PrescriptionDao prescriptionDao;

    /**
     * 处方返利信息入库
     * @param prescription
     */
    public void addPrescriptionRebate(PrescriptionDo prescription,OrderInfoDo order){
        RebateConfig rebateConfig=rebateConfigService.getRebateConfig();
        if(rebateConfig!=null&& RebateConfigConstant.SWITCH_ON.equals(rebateConfig.getStatus())){
            List<PrescriptionItemDo> itemList=prescriptionItemDao.listOrderGoodsByPrescriptionCode(prescription.getPrescriptionCode());
            //计算应返利
            updatePrescriptionItem(itemList,order,rebateConfig);
            //返利信息入库
            calculatePrescriptionRebate(prescription,itemList);
            prescriptionDao.updateSettlementFlag(prescription.getPrescriptionCode(),PrescriptionConstant.SETTLEMENT_WAIT);
        }else {
            prescriptionDao.updateSettlementFlag(prescription.getPrescriptionCode(), PrescriptionConstant.SETTLEMENT_NOT);
        }

    }

    /**
     * 计算对应处方明细应返利
     * @param itemList
     * @param order
     */
    public void updatePrescriptionItem( List<PrescriptionItemDo> itemList,OrderInfoDo order,RebateConfig rebateConfig){
        Double sums=itemList.stream().collect(Collectors.summingDouble(PrescriptionItemDo::getDragSumNum));
        BigDecimal avgScoreDiscount = BigDecimalUtil.divide(order.getScoreDiscount(), BigDecimal.valueOf(sums), RoundingMode.HALF_UP);

        for(PrescriptionItemDo item:itemList){
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
//            OrderGoodsDo orderGoodsDo=orderGoodsDao.getByOrderIdGoodsIdPrdId(order.getOrderId(),item.getGoodsId(),item.getPrdId());
            OrderGoodsDo orderGoodsDo=orderGoodsDao.getByPrescriptionDetailCode(item.getPrescriptionDetailCode());
            //可计算返利商品金额
            BigDecimal canRebateMoney = BigDecimalUtil.subtrac(orderGoodsDo.getDiscountedTotalPrice(), BigDecimalUtil.multiply(avgScoreDiscount, new BigDecimal(orderGoodsDo.getGoodsNumber())));
            canRebateMoney = BigDecimalUtil.compareTo(canRebateMoney, null) > 0 ? canRebateMoney : BigDecimalUtil.BIGDECIMAL_ZERO;
            item.setCanCalculateMoney(canRebateMoney);
            //应返利金额
            item.setTotalRebateMoney(item.getCanCalculateMoney().multiply(item.getGoodsSharingProportion())
                .multiply(item.getRebateProportion())
                .setScale(BigDecimalUtil.FOUR_SCALE,BigDecimal.ROUND_HALF_UP));
            item.setRealRebateMoney(item.getTotalRebateMoney());
            prescriptionItemDao.updatePrescriptionItem(item);
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
        rebateParam.setRealRebateMoney(rebateParam.getTotalRebateMoney());
        prescriptionRebateDao.addPrescriptionRebate(rebateParam);
    }
    /**
     * 分页查询列表
     * @param param
     * @return
     */
    public PageResult<PrescriptionRebateVo> getPageList(PrescriptionRebateListParam param){
        PageResult<PrescriptionRebateVo> result=prescriptionRebateDao.getPageList(param);
        List<PrescriptionRebateVo> list=result.getDataList();
        for(PrescriptionRebateVo vo:list){
            List<PrescriptionItemDo> prescriptionItemDos =prescriptionItemDao.listOrderGoodsByPrescriptionCode(vo.getPrescriptionCode());
            vo.setMedicalList(prescriptionItemDos);
        }
        return result;
    }

    /**
     * 报表导出
     * @param param
     * @return
     */
    public Workbook listExport(PrescriptionRebateListParam param,String lang){
        List<PrescriptionRebateReportVo> list=prescriptionRebateDao.getList(param);
        list.stream().forEach(vo -> {
            if(PrescriptionRebateConstant.TO_REBATE.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.WAIT_REBATE);
            }else if(PrescriptionRebateConstant.REBATED.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.REBATED);
            }else if(PrescriptionRebateConstant.REBATE_FAIL.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.REBATE_FAIL);
            }
        });
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(list,PrescriptionRebateReportVo.class);
        return workbook;
    }

}
