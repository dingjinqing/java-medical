package com.vpu.mp.service.shop.rebate;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.rebate.DoctorTotalRebateDao;
import com.vpu.mp.dao.shop.rebate.DoctorWithdrawDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.rebate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Service
public class DoctorWithdrawService extends ShopBaseService {
    @Autowired
    private DoctorWithdrawDao doctorWithDrawDao;
    @Autowired
    private DoctorTotalRebateDao doctorTotalRebateDao;

    /**
     * 提现记录列表
     * @param param
     * @return
     */
    public PageResult<DoctorWithdrawVo> getPageList(DoctorWithdrawListParam param){
        return doctorWithDrawDao.getPageList(param);
    }
    public void addDoctorWithdraw(DoctorWithdrawParam param){
        param.setType(DoctorWithdrawConstant.WX_MINI_TYPE);
        param.setStatus(DoctorWithdrawConstant.WITHDRAW_CHECK_WAIT_CHECK);
        //提现单号
        param.setOrderSn(IncrSequenceUtil.generateOrderSn(DoctorWithdrawConstant.ORDER_SN_PREFIX));
        //提现序号
        param.setWithdrawUserNum(String.valueOf(doctorWithDrawDao.count(param.getDoctorId())));
        param.setWithdrawNum(String.valueOf(doctorWithDrawDao.count(null)));
        //可提现金额
        DoctorTotalRebateVo doctorTotalRebateVo= doctorTotalRebateDao.getRebateByDoctorId(param.getDoctorId());
        param.setWithdraw(doctorTotalRebateVo.getTotalMoney());


        doctorWithDrawDao.addDoctorWithdraw(param);
        //冻结金额
        doctorTotalRebateDao.blockedMoney(param.getDoctorId(),doctorTotalRebateVo.getTotalMoney().subtract(param.getWithdraw()),doctorTotalRebateVo.getBlockedMoney().add(param.getWithdraw()));

    }

    public void update(DoctorWithdrawUpdateParam param)throws MpException {
        DoctorWithdrawVo doctorWithdrawVo= doctorWithDrawDao.getWithDrawByOrderSn(param.getOrderSn());
        if(doctorWithdrawVo==null){
            throw new MpException(JsonResultCode.CODE_FAIL);
        }
        doctorWithDrawDao.update(doctorWithdrawVo.getId(),param.getPassStatus(),param.getRefuseDesc());

    }
}
