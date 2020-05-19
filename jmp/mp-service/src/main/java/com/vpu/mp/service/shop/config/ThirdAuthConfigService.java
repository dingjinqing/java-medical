package com.vpu.mp.service.shop.config;

import com.thoughtworks.xstream.core.BaseException;
import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.db.main.tables.records.AppRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnBusinessAddressParam;
import com.vpu.mp.service.pojo.shop.config.trade.third.*;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 第三方对接配置
 * ERP服务:200000
 * POS服务:200001
 * CRM服务:200002
 * @author 孔德成
 * @date 2020/5/18
 */
@Service
public class ThirdAuthConfigService extends  BaseShopConfigService {

    /**
     * 同城配送订单已收货后推送 1 开启 0关闭
     */
    final public static String K_CITY_ORDER_PUSH = "city_order_push";
    /**
     * 自提订单核销后推送 1开启 0关闭
     */
    final public static String K_VERIFY_ORDER = "verify_order";
    @Autowired
    private ShopReturnConfigService shopReturnConfigService;


    public  Byte  getCityOrderPush(){
        return this.get(K_CITY_ORDER_PUSH, Byte.class, (byte)0);
    }

    public  Byte  getVerifyOrder(){
        return this.get(K_VERIFY_ORDER, Byte.class, (byte)0);
    }

    public void setCityOrderPush(@NotNull @Min(0) @Max(1) Byte action) {
        this.set(K_CITY_ORDER_PUSH,action.toString());
    }

    public void verifyOrder(@NotNull @Min(0) @Max(1) Byte action) {
        this.set(K_CITY_ORDER_PUSH,action.toString());
    }

    public void setPush(ThirdErpPushParam param) {
        switch (param.getType()){
            case K_CITY_ORDER_PUSH:
                setCityOrderPush(param.getAction());
                break;
            case K_VERIFY_ORDER:
                setCityOrderPush(param.getAction());
            default:
                throw new  BusinessException(JsonResultCode.CODE_CARD_RECEIVE_NOCODE);
        }
    }

    public ThirdInfoVo getThirdAuthInfo(ThirdInfoParam param) {
        //初始化信息
        AppAuthRecord appAuthRecord = this.authInit(param.getAction());
        AppRecord appInfo = this.getAppInfo(param);
        AppAuthBo appAuthBo = appAuthRecord.into(AppAuthBo.class);
        AppBo appBo = appInfo.into(AppBo.class);

        ThirdInfoVo  vo =new ThirdInfoVo();
        vo.setAppAuthBo(appAuthBo);
        vo.setAppBo(appBo);
        vo.setCityOrderPush(getCityOrderPush());
        vo.setVerifyOrder(getVerifyOrder());
        return vo;
    }

    private AppRecord getAppInfo(ThirdInfoParam param) {
        String appId;
        switch (param.getAction()) {
            case 1:
                appId="200000";
                break;
            case 2:
                appId="200001";
                break;
            case 3:
                appId="200002";
                break;
            default:
                throw new BusinessException(JsonResultCode.CODE_CARD_RECEIVE_NOCODE);
        }
        return saas.shop.shopApp.getAppRecordByAppId(appId);
    }

    /**
     * 初始化授权
     * @param action
     * @return
     */
    private AppAuthRecord authInit(Byte action){
        if (action!=null){
            Integer shopId = getShopId();
            Integer sysId = getSysId();
            AppAuthRecord appAuthInfo = saas.shop.shopApp.getAppAuthInfo(sysId, shopId, action);
            if (appAuthInfo==null){
               return saas.shop.shopApp.AddAppAuthInfo(sysId,shopId,action);
            }
            return appAuthInfo;
        }
        return null;
    }


    /**
     * 授权
     * @param param
     */
    public void authorize(ThirdAuthorizeParam param) {
        int i = saas.shop.shopApp.updateAppAuthStatus(param.getId(),getShopId(),param.getStatus());
        if (i>0&&param.getAction().equals((byte)3)&&param.getStatus().equals(BaseConstant.YES)){
            logger().info("授权crm-同步");
            AppAuthRecord appAuthRecord = saas.shop.shopApp.AddAppAuthInfo(getSysId(), getShopId(), param.getAction());
            if (appAuthRecord!=null&&appAuthRecord.getIsSync().equals(BaseConstant.YES)){
                //TODO
                /**
                 *   queue_job(function () use ($shopId){
                 *    shop($shopId)->serviceRequest->crmApi->startSyncAllData();
                 *      });
                 */
            }
        }

    }

    public int resetSessionKey(Integer id) {
        AppAuthRecord appAuthRecord = saas.shop.shopApp.getAppAuthRecordById(id, getShopId(), getSysId());
        if (appAuthRecord!=null){
            String s = saas.shop.shopApp.generateUniqueSessionKey(getShopId());
            appAuthRecord.setSessionKey(s);
           return appAuthRecord.update();
        }
        return 0;
    }

    public void saveAppKey(ThirdAppKeyParam param) {
        if (param.getAction().equals((byte)3)){
            if (Strings.isNullOrEmpty(param.getAppSecret())) throw new BusinessException(JsonResultCode.CODE_CARD_RECEIVE_NOCODE);
            logger().info("crm更新appkey");
        }else {
            int i = saas.shop.shopApp.updateAppAuthAppkey(param.getId(), getSysId(), getShopId(), param.getAppKey(), param.getAppSecret());
        }

    }

}
