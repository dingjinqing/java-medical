package com.vpu.mp.service.shop.sms;

import cn.hutool.http.HttpResponse;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.config.ShopCfgDao;
import com.vpu.mp.dao.shop.sms.SmsSendRecordDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.sms.SmsAccountParam;
import com.vpu.mp.service.pojo.shop.sms.base.SmsBaseRequest;
import com.vpu.mp.service.shop.config.SmsAccountConfigService;
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



}
