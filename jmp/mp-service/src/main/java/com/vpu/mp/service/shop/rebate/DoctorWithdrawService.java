package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.common.foundation.data.DistributionConstant;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.dao.shop.rebate.DoctorTotalRebateDao;
import com.vpu.mp.dao.shop.rebate.DoctorWithdrawDao;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.config.rebate.RebateConfig;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.rebate.*;
import com.vpu.mp.service.shop.config.RebateConfigService;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    @Autowired
    private MemberService memberService;
    @Autowired
    private RebateConfigService rebateConfigService;

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
        RebateConfig rebateConfig=rebateConfigService.getRebateConfig();
        DoctorTotalRebateVo doctorTotalRebateVo= doctorTotalRebateDao.getRebateByDoctorId(param.getDoctorId());
        //提现申请校验
        checkApply(param,rebateConfig,doctorTotalRebateVo);
        param.setType(DoctorWithdrawConstant.RT_WX_MINI);
        param.setStatus(DoctorWithdrawConstant.WITHDRAW_CHECK_WAIT_CHECK);
        //提现单号
        param.setOrderSn(IncrSequenceUtil.generateOrderSn(DoctorWithdrawConstant.ORDER_SN_PREFIX));
        //提现序号
        param.setWithdrawUserNum(String.valueOf(doctorWithDrawDao.count(param.getDoctorId())+1));
        param.setWithdrawNum(String.valueOf(doctorWithDrawDao.count(null)+1));
        //可提现金额
        param.setWithdraw(doctorTotalRebateVo.getTotalMoney());
        param.setWithdrawSource(Util.toJson(rebateConfig));
        transaction(() -> {
            doctorWithDrawDao.addDoctorWithdraw(param);
            //修改可提现金额，冻结金额
            doctorTotalRebateDao.updateTotalMoneyBlockedMoney(param.getDoctorId(),doctorTotalRebateVo.getTotalMoney().subtract(param.getWithdrawCash()),doctorTotalRebateVo.getBlockedMoney().add(param.getWithdrawCash()));
        });

    }

    /**
     * 提现申请校验
     * @param param
     * @throws MpException
     */
    public void checkApply(DoctorWithdrawParam param,RebateConfig rebateConfig, DoctorTotalRebateVo doctorTotalRebateVo) throws MpException{
        if(doctorTotalRebateVo==null){
            //不存在可提现金额
            throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_IS_NOT_EXIST);
        }
        if(BigDecimalUtil.compareTo(param.getWithdrawCash(),doctorTotalRebateVo.getTotalMoney())>0){
            //超出可提现金额
            throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_MAXIMUM_LIMIT_MONEY);
        }

        if(rebateConfig!=null){
            if(BigDecimalUtil.compareTo(param.getWithdrawCash(),rebateConfig.getWithdrawCashMix())<0){
                //小于单次提现最小额度限制
                throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_LESS_MINIMUM);
            }

            BigDecimal withdrawSum=doctorWithDrawDao.getWithdrawCashSum(param.getDoctorId(),null, DateUtils.getTimestampForTodayStartTime(),DateUtils.getTimestampForTodayEndTime());
            if(withdrawSum!=null&&BigDecimalUtil.compareTo(param.getWithdrawCash().add(withdrawSum),rebateConfig.getWithdrawCashMax())>0){
                //大于每日提现额度限制
                throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_EXCEED_DAY_MAX_LIMIT_MONEY);
            }
        }

    }

    /**
     * 更新状态
     * @param param
     * @throws MpException
     */
    public void audit(DoctorWithdrawUpdateParam param)throws MpException {
        DoctorWithdrawVo doctorWithdrawVo= doctorWithDrawDao.getWithdrawByOrderSn(param.getOrderSn());
        if(doctorWithdrawVo==null){
            throw new MpException(JsonResultCode.CODE_FAIL);
        }
        DoctorOneParam doctor=doctorService.getOneInfo(doctorWithdrawVo.getDoctorId());
        DoctorTotalRebateVo doctorTotalRebateVo= doctorTotalRebateDao.getRebateByDoctorId(doctorWithdrawVo.getDoctorId());
        transaction(() -> {
            if(DoctorWithdrawConstant.WITHDRAW_CHECK_PAY_SUCCESS.equals(param.getCheckStatus())){
                //出账,暂时注释掉
                pay2Person(param.getOrderSn(),param.getClientIp(),doctorWithdrawVo.getRealName(),doctor.getUserId(),doctorWithdrawVo.getType(),doctorWithdrawVo.getWithdrawCash());
                //释放冻结金额
                doctorTotalRebateDao.updateBlockMoney(doctorWithdrawVo.getDoctorId(),doctorTotalRebateVo.getBlockedMoney().subtract(doctorWithdrawVo.getWithdrawCash()));
            }else if(DoctorWithdrawConstant.WITHDRAW_CHECK_REFUSE.equals(param.getCheckStatus())){
                //修改可提现金额，冻结金额
                doctorTotalRebateDao.updateTotalMoneyBlockedMoney(doctorWithdrawVo.getDoctorId(),doctorTotalRebateVo.getTotalMoney().add(doctorWithdrawVo.getWithdrawCash()),doctorTotalRebateVo.getBlockedMoney().subtract(doctorWithdrawVo.getWithdrawCash()));
            }
            doctorWithDrawDao.update(doctorWithdrawVo.getId(),param.getCheckStatus(),param.getRefuseDesc());
        });


    }

    /**
     * 提现发红包
     * @param orderSn
     * @param ip
     * @param realName
     * @param userId
     * @param type
     * @param money
     * @throws MpException
     */
    public void pay2Person(String orderSn, String ip, String realName, Integer userId, Byte type, BigDecimal money) throws MpException {
        logger().info("pay2Person start");
        MpAuthShopRecord wxapp = saas.shop.mp.getAuthShopByShopId(getShopId());
        try {
            if(DoctorWithdrawConstant.RT_WX_OPEN.equals(type)){
                if( StringUtils.isBlank(wxapp.getLinkOfficialAppId())||wxapp == null) {
                    throw new MpException(JsonResultCode.NO_LINK_WECHAT_OFFICIAL_ACCOUNTS);
                }
                String wxOpenId = memberService.getUserWxOpenId(userId);
                String openId = saas.shop.mpOfficialAccountUserService.getOpenIdFromMpOpenId(wxapp.getLinkOfficialAppId(), wxapp.getAppId(), wxOpenId);
                if(StringUtils.isBlank(openId)){
                    throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_NO_FOCUS_WECHAT_OFFICIAL_ACCOUNTS);
                }
                mpPaymentService.companyPay(wxapp.getLinkOfficialAppId(), ip, orderSn, openId, realName, BigDecimalUtil.multiply(money, new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue(), "佣金提现");
            }else if(DoctorWithdrawConstant.RT_WX_MINI.equals(type)) {
                UserRecord userRecord = memberService.getUserRecordById(userId);
                mpPaymentService.companyPay(wxapp.getAppId(), ip, orderSn, userRecord.getWxOpenid(), realName, BigDecimalUtil.multiply(money, new BigDecimal(Byte.valueOf(DoctorWithdrawConstant.YUAN_FEN_RATIO).toString())).intValue(), "佣金提现");
            }else if(DoctorWithdrawConstant.RT_SUB_MCH.equals(type)){
                if( StringUtils.isBlank(wxapp.getLinkOfficialAppId())||wxapp == null) {
                    throw new MpException(JsonResultCode.NO_LINK_WECHAT_OFFICIAL_ACCOUNTS);
                }
                UserRecord userRecord = memberService.getUserRecordById(userId);
                String wxOpenId = memberService.getUserWxOpenId(userId);
                String openId = saas.shop.mpOfficialAccountUserService.getOpenIdFromMpOpenId(wxapp.getLinkOfficialAppId(), wxapp.getAppId(), wxOpenId);
                if(StringUtils.isBlank(openId)){
                    throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_NO_FOCUS_WECHAT_OFFICIAL_ACCOUNTS);
                }
                mpPaymentService.sendRedpack(wxapp.getLinkOfficialAppId(), orderSn, ip, openId, BigDecimalUtil.multiply(money, new BigDecimal(Byte.valueOf(DoctorWithdrawConstant.YUAN_FEN_RATIO).toString())).intValue(), userRecord.getUsername() + "的红包", "佣金提现", "活跃赚取更多佣金");
            }
        } catch (WxPayException e) {
            throw new MpException(JsonResultCode.DOCTOR_WITHDRAW_EX_ERROR,
                e.getMessage(), StringUtils.isBlank(e.getErrCodeDes()) ? e.getCustomErrorMsg() : e.getErrCodeDes());
        }
    }

}
