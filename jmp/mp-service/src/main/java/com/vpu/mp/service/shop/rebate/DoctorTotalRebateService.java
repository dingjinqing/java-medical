package com.vpu.mp.service.shop.rebate;

import com.vpu.mp.dao.shop.rebate.DoctorTotalRebateDao;
import com.vpu.mp.dao.shop.rebate.DoctorWithdrawDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.rebate.DoctorTotalRebateVo;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Service
public class DoctorTotalRebateService extends ShopBaseService {
    @Autowired
    private DoctorTotalRebateDao doctorTotalRebateDao;
    @Autowired
    private DoctorWithdrawDao doctorWithdrawDao;

    /**
     * 根据医师id查询
     * @param doctorId
     * @return
     */
    public DoctorTotalRebateVo getRebateByDoctorId(Integer doctorId){
        DoctorTotalRebateVo doctorTotalRebateVo=doctorTotalRebateDao.getRebateByDoctorId(doctorId);
        if(doctorTotalRebateVo==null){
            return new DoctorTotalRebateVo();
        }
        //累计提现金额
        BigDecimal accruingWithDrawCash=BigDecimal.ZERO;
        BigDecimal paySuccessWithDrawCash=doctorWithdrawDao.getWithdrawCashSum(doctorId, DoctorWithdrawConstant.WITHDRAW_CHECK_PAY_SUCCESS);
        if(paySuccessWithDrawCash!=null){
            accruingWithDrawCash=accruingWithDrawCash.add(paySuccessWithDrawCash);
        }
        doctorTotalRebateVo.setAccruingWithDrawCash(accruingWithDrawCash);
        //待提现金额
        BigDecimal waitWithDrawCash=BigDecimal.ZERO;
        BigDecimal waitCheckWithDrawCash=doctorWithdrawDao.getWithdrawCashSum(doctorId,DoctorWithdrawConstant.WITHDRAW_CHECK_WAIT_CHECK);
        BigDecimal waitPayWithDrawCash=doctorWithdrawDao.getWithdrawCashSum(doctorId,DoctorWithdrawConstant.WITHDRAW_CHECK_WAIT_PAY);
        if(waitCheckWithDrawCash!=null){
            waitWithDrawCash=waitWithDrawCash.add(waitCheckWithDrawCash);
        }
        if(waitPayWithDrawCash!=null){
            waitWithDrawCash=waitWithDrawCash.add(waitPayWithDrawCash);
        }
        doctorTotalRebateVo.setWaitWithDrawCash(waitWithDrawCash);
        return doctorTotalRebateVo;
    }
}
