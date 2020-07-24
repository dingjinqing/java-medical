package com.vpu.mp.service.shop.sms;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.config.ShopCfgDao;
import com.vpu.mp.dao.shop.sms.SmsSendRecordDao;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.sms.SmsBaseParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordParam;
import com.vpu.mp.service.pojo.shop.sms.base.SmsBaseRequest;
import com.vpu.mp.service.shop.config.SmsAccountConfigService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 发送短信
 * @author 孔德成
 * @date 2020/7/24 9:17
 */
@Service
@Slf4j
public class SmsService {


    @Autowired
    protected  SmsApiConfig smsApiConfig;
    @Autowired
    protected SmsSendRecordDao smsSendRecordDao;
    @Autowired
    private ShopCfgDao shopCfgDao;
    @Autowired
    private SmsAccountConfigService smsAccountConfigService;

    /**
     * 发送短信
     */
    public String sendSms(SmsBaseParam param) throws MpException {
        String account = smsAccountConfigService.getShopSmsAccountConfig();
        if (Strings.isBlank(account)){
            return null;
        }
        param.setSid(account);
        //拼写请求内容
        return sendSmsParam(param);
    }


    /**
     * 发送短信
     * @param mobiles 电话  ,分隔
     * @param content 短信内容
     * @param sId 短信账号
     * @param ext 类型
     */
    public String sendSms(Integer userId,String mobiles,String content,String ext,String sId) throws MpException {
        SmsBaseParam param =new SmsBaseParam();
        param.setContent(content);
        param.setMobiles(mobiles);
        param.setSid(sId);
        param.setChannel(SmsApiConfig.CHANNEL_DEFAULT);
        param.setProduct(SmsApiConfig.PRODUCT_WPB_WX);
        param.setExt(ext);
        param.setUserId(userId);
        return sendSms(param);
    }
    /**
     * 发送短信
     *  -默认账号
     * @param mobiles 电话  ,分隔
     * @param content 短信内容
     * @param ext 类型
     */
    public String sendSms(Integer userId,String mobiles,String content,String ext) throws MpException {
        String account = smsAccountConfigService.getShopSmsAccountConfig();
        if (Strings.isBlank(account)){
            return null;
        }
        SmsBaseParam param =new SmsBaseParam();
        param.setContent(content);
        param.setMobiles(mobiles);
        param.setSid(account);
        param.setChannel(SmsApiConfig.CHANNEL_DEFAULT);
        param.setProduct(SmsApiConfig.PRODUCT_WPB_WX);
        param.setExt(ext);
        param.setUserId(userId);
        return sendSms(param);
    }

    /**
     * 发送短信
     * -默认账号
     * -默认验证码方式
     * @param mobiles 电话,分隔
     * @param content 短信内容
     */
    public String sendSms(Integer userId,String mobiles,String content) throws MpException {
        String account = smsAccountConfigService.getShopSmsAccountConfig();
        if (Strings.isBlank(account)){
            return null;
        }
        SmsBaseParam param =new SmsBaseParam();
        param.setContent(content);
        param.setMobiles(mobiles);
        param.setSid(account);
        param.setChannel(SmsApiConfig.CHANNEL_DEFAULT);
        param.setProduct(SmsApiConfig.PRODUCT_WPB_WX);
        param.setExt(SmsApiConfig.EXT_CHECK_CODE);
        param.setUserId(userId);
       return sendSms(param);
    }


    /**
     * 发送短信请求
     * @param param
     * @return
     */
    private String sendSmsParam(SmsBaseParam param) throws MpException {
        long time = System.currentTimeMillis()/1000;
        SmsBaseRequest request  =new SmsBaseRequest();
        request.setSms(Util.toJson(param));
        request.setApiMethod(SmsApiConfig.METHOD_SMS_SEND);
        request.setAppKey(smsApiConfig.getAppKey());
        request.setTimestamp(time);
        Map<String, Object> postBody = Util.transBeanToMap(request);
        postBody.put("sign", generateSing(postBody));
        HttpResponse response = requestApi(postBody);
        SmsSendRecordParam record =new SmsSendRecordParam();
        record.setAccountName(param.getSid());
        record.setUserId(param.getUserId());
        record.setMobile(param.getMobiles());
        record.setExt(param.getExt());
        record.setRequestMsg(param.getContent());
        record.setResponseMsg(response.body());
        record.setResponseCode(response.getStatus()+"");
        smsSendRecordDao.save(record);
        return response.body();
    }

    /**
     * 升序
     * @param map
     * @return
     */
    public static  Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap= new TreeMap<>(String::compareTo);
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 生成签名
     * @param map
     * @return
     */
    public String generateSing(Map<String, Object> map){
        StringBuilder str = new StringBuilder(smsApiConfig.getAppSecret());
        map.forEach((k, v)-> str.append(k).append(v));
        str.append(smsApiConfig.getAppSecret());
        return Util.md5(str.toString()).toUpperCase();
    }

    /**
     * 调用接口,并保存发送记录
     * @param postBody
     * @return
     */
    public HttpResponse requestApi(Map<String, Object> postBody) throws MpException {
        log.info("短信request--{}",postBody);
        HttpResponse response;
        try {
             response = HttpRequest.post(smsApiConfig.getSmsUrl())
                    .header(Header.CONTENT_TYPE, "multipart/form-data")
                    .form(postBody)
                    .timeout(20000)
                    .execute();
        }catch (Exception e){
            throw new MpException(JsonResultCode.CODE_ACCOUNT_SAME);
        }
        log.info("短信resPonse--{}",response.body());
        return response;
    }



}
