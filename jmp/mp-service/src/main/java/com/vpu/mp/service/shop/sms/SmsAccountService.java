package com.vpu.mp.service.shop.sms;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.config.ShopCfgDao;
import com.vpu.mp.dao.shop.sms.SmsSendRecordDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.sms.SmsAccountParam;
import com.vpu.mp.service.pojo.shop.sms.account.SmsAccountInfoParam;
import com.vpu.mp.service.pojo.shop.sms.account.SmsAccountInfoVo;
import com.vpu.mp.service.pojo.shop.sms.base.SmsBaseRequest;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeListVo;
import com.vpu.mp.service.pojo.shop.sms.recharge.SmsAccountRechargeRecordParam;
import com.vpu.mp.service.shop.config.SmsAccountConfigService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 孔德成
 * @date 2020/7/24 16:45
 */
@Service
public class SmsAccountService extends ShopBaseService {

    @Autowired
    protected SmsApiConfig smsApiConfig;
    @Autowired
    protected SmsSendRecordDao smsSendRecordDao;
    @Autowired
    private ShopCfgDao shopCfgDao;
    @Autowired
    private SmsAccountConfigService smsAccountConfigService;
    @Autowired
    private SmsService smsService;


    /**
     * 创建短信账户
     * @param param
     * @return
     */
    public String createSmsAccount(SmsAccountParam param) throws MpException {
        long time = System.currentTimeMillis()/1000;
        SmsBaseRequest request  =new SmsBaseRequest();
        request.setSms(Util.toJson(param));
        request.setApiMethod(SmsApiConfig.METHOD_CREATE_ACCOUNT);
        request.setAppKey(smsApiConfig.getAppKey());
        request.setTimestamp(time);
        Map<String, Object> postBody = Util.transBeanToMap(request);
        postBody.put("sign", smsService.generateSing(postBody));
        HttpResponse response = smsService.requestApi(postBody);
        return response.body();
    }

    /**
     * 短信账户的充值记录
     * @return
     */
    public SmsAccountRechargeListVo listSmsAccountRechargeRecord(SmsAccountRechargeRecordParam param) throws MpException {
        SmsBaseRequest request  =new SmsBaseRequest();
        request.setSms(Util.toJson(param));
        request.setApiMethod(SmsApiConfig.METHOD_SMS_RECHARGE_RECORD);
        request.setAppKey(smsApiConfig.getAppKey());
        request.setTimestamp(System.currentTimeMillis()/1000);
        Map<String, Object> postBody = Util.transBeanToMap(request);
        postBody.put("sign", smsService.generateSing(postBody));
        HttpResponse response = smsService.requestApi(postBody);
        return JSONUtil.toBean(response.body(),SmsAccountRechargeListVo.class);
    }

    /**
     * 获取短信账户信息
     * @return
     */
    public SmsAccountInfoVo getSmsAccountInfo() throws MpException {
        String accountConfig = smsAccountConfigService.getShopSmsAccountConfig();
        if (Strings.isBlank(accountConfig)){
            return null;
        }
        SmsAccountInfoParam param =new SmsAccountInfoParam();
        param.setSid(accountConfig);
        SmsBaseRequest request  =new SmsBaseRequest();
        request.setSms(Util.toJson(param));
        request.setApiMethod(SmsApiConfig.METHOD_SMS_CHECK);
        request.setAppKey(smsApiConfig.getAppKey());
        request.setTimestamp(System.currentTimeMillis()/1000);
        Map<String, Object> postBody = Util.transBeanToMap(request);
        postBody.put("sign", smsService.generateSing(postBody));
        HttpResponse response = smsService.requestApi(postBody);
        SmsAccountInfoVo smsAccountInfoVo = JSONUtil.toBean(response.body(), SmsAccountInfoVo.class);
        smsAccountInfoVo.setRechargeUrl(smsApiConfig.getChargeUrl());
        return smsAccountInfoVo;
    }



}
