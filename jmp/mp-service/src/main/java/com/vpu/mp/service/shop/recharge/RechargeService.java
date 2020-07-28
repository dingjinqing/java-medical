package com.vpu.mp.service.shop.recharge;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.shop.recharge.RechargeDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.recharge.RechargeParam;
import com.vpu.mp.service.pojo.shop.recharge.RechargeVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeListVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeRecordParam;
import com.vpu.mp.service.shop.config.SmsAccountConfigService;
import com.vpu.mp.service.shop.sms.SmsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 赵晓东
 * @description 短信平台充值
 * @create 2020-07-27 16:47
 **/

@Service
public class RechargeService extends ShopBaseService {

    @Autowired
    private RechargeDao rechargeDao;

    @Autowired
    private SmsAccountService smsAccountService;

    @Autowired
    private SmsAccountConfigService smsAccountConfigService;

    /**
     * 同步本地库充值记录
     * @param smsAccountRechargeListVo 调取二方库回参
     */
    protected void addRechargeList(SmsAccountRechargeListVo smsAccountRechargeListVo){
        rechargeDao.fetchRechargeList(smsAccountRechargeListVo);
    }

    /**
     * admin端查询充值记录时，先调取二方库增量拉取充值信息到本地，再将数据回传给前端
     * @param rechargeParam 前端调取入参
     * @return RechargeVo
     */
    public SmsAccountRechargeListVo getRechargeList(RechargeParam rechargeParam) {
        //查询当前账户id
        SmsAccountRechargeRecordParam smsAccountRechargeRecordParam = new SmsAccountRechargeRecordParam();
        smsAccountRechargeRecordParam.setSid(smsAccountConfigService.getShopSmsAccountConfig());
        FieldsUtil.assign(rechargeParam, smsAccountRechargeRecordParam);
        SmsAccountRechargeListVo smsAccountRechargeListVo = new SmsAccountRechargeListVo();
        try {
            //从二方库拉取数据
            smsAccountRechargeListVo = smsAccountService.listSmsAccountRechargeRecord(smsAccountRechargeRecordParam);
            //向本地库同步
            addRechargeList(smsAccountRechargeListVo);
        } catch (MpException e) {
            e.printStackTrace();
        }
        //向前端返回回参
        return smsAccountRechargeListVo;
    }

}
