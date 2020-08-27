package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.dao.shop.rebate.PrescriptionRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.rebate.*;
import com.vpu.mp.service.shop.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
    /**
     * 处方返利信息入库
     * @param prescription
     */
    public void addPrescriptionRebate(PrescriptionDo prescription, List<PrescriptionItemDo> itemList){
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
        PrescriptionRebateParam rebateParam=new PrescriptionRebateParam();
        rebateParam.setPrescriptionCode(prescription.getPrescriptionCode());
        rebateParam.setStatus(PrescriptionRebateConstant.TO_REBATE);
        rebateParam.setDoctorId(doctor.getId());
        rebateParam.setTotalMoney(totalMoney.setScale(BigDecimalUtil.DEFAULT_SCALE,BigDecimal.ROUND_HALF_DOWN));
        rebateParam.setTotalRebateMoney(totalRebateMoney.setScale(BigDecimalUtil.DEFAULT_SCALE,BigDecimal.ROUND_HALF_DOWN));
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
