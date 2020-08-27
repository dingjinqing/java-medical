package com.vpu.mp.service.shop.rebate;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.rebate.DoctorTotalRebateDao;
import com.vpu.mp.dao.shop.rebate.DoctorWithdrawDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.rebate.*;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
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
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private MpPaymentService mpPaymentService;

    /**
     * 提现记录列表
     * @param param
     * @return
     */
    public PageResult<DoctorWithdrawVo> getPageList(DoctorWithdrawListParam param){
        return doctorWithDrawDao.getPageList(param);
    }

    /**
     *医师提现申请
     * @param param
     */
    public void addDoctorWithdraw(DoctorWithdrawParam param) throws MpException{
        checkApply(param);
        param.setType(DoctorWithdrawConstant.RT_SUB_MCH);
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
        //修改可提现金额，冻结金额
        doctorTotalRebateDao.updateTotalMoneyBlockedMoney(param.getDoctorId(),doctorTotalRebateVo.getTotalMoney().subtract(param.getWithdrawCash()),doctorTotalRebateVo.getBlockedMoney().add(param.getWithdrawCash()));
    }

    /**
     * 提现申请校验
     * @param param
     * @throws MpException
     */
    public void checkApply(DoctorWithdrawParam param) throws MpException{
        DoctorTotalRebateVo doctorTotalRebateVo= doctorTotalRebateDao.getRebateByDoctorId(param.getDoctorId());
        if(doctorTotalRebateVo==null){
            //不存在可提现金额
            throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_IS_NOT_EXIST);
        }
        if(BigDecimalUtil.compareTo(param.getWithdrawCash(),doctorTotalRebateVo.getTotalMoney())>0){
            //超出可提现金额
            throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_MAXIMUM_LIMIT_MONEY);
        }
    }

    /**
     * 更新状态
     * @param param
     * @throws MpException
     */
    public void update(DoctorWithdrawUpdateParam param)throws MpException {
        DoctorWithdrawVo doctorWithdrawVo= doctorWithDrawDao.getWithdrawByOrderSn(param.getOrderSn());
        if(doctorWithdrawVo==null){
            throw new MpException(JsonResultCode.CODE_FAIL);
        }
        DoctorOneParam doctor=doctorService.getOneInfo(doctorWithdrawVo.getDoctorId());
        DoctorTotalRebateVo doctorTotalRebateVo= doctorTotalRebateDao.getRebateByDoctorId(doctorWithdrawVo.getDoctorId());
        if(DoctorWithdrawConstant.WITHDRAW_CHECK_PAY_SUCCESS.equals(param.getCheckStatus())){
            //出账,暂时注释掉
//            mpPaymentService.pay2Person(param.getOrderSn(),param.getClientIp(),doctorWithdrawVo.getRealName(),doctor.getUserId(),doctorWithdrawVo.getType(),doctorWithdrawVo.getWithdrawCash());
            //释放冻结金额
            doctorTotalRebateDao.updateBlockMoney(doctorWithdrawVo.getDoctorId(),doctorTotalRebateVo.getBlockedMoney().subtract(doctorWithdrawVo.getWithdrawCash()));
        }else if(DoctorWithdrawConstant.WITHDRAW_CHECK_REFUSE.equals(param.getCheckStatus())){
            //修改可提现金额，冻结金额
            doctorTotalRebateDao.updateTotalMoneyBlockedMoney(doctorWithdrawVo.getDoctorId(),doctorTotalRebateVo.getTotalMoney().add(doctorWithdrawVo.getWithdrawCash()),doctorTotalRebateVo.getBlockedMoney().subtract(doctorWithdrawVo.getWithdrawCash()));
        }
        doctorWithDrawDao.update(doctorWithdrawVo.getId(),param.getCheckStatus(),param.getRefuseDesc());

    }
}
