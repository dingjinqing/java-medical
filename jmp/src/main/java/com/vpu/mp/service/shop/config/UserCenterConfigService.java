package com.vpu.mp.service.shop.config;

/**
 * 个人中心
 *
 * @author 孔德成
 * @date 2019/7/11 9:52
 */
public class UserCenterConfigService extends BaseShopConfigService {

    /**
     * value是json格式
     */
    final public static String K_USER_CENTER = "user_center";


    public Object getkUserCenterConfig(){
        return getJsonObject(K_USER_CENTER,Object.class);
    }


    public Boolean setUserConterConfig(Object object){
        return setJsonObject(K_USER_CENTER,object)==1;
    }


}
