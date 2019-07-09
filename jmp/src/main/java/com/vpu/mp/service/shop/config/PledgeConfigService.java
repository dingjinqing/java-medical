package com.vpu.mp.service.shop.config;

/**
 * 服务承诺-开关全局设置
 * @author: 卢光耀
 * @date: 2019-07-09 15:17
 *
*/
public class PledgeConfigService extends BaseShopConfigService{
    /**
     * 服务承诺键值
     */
    final public static String K_PLEDGE = "pledge";

    /**
     * 服务承诺value
     */
    final public static String V_PLEDGE = "0";

    public String getPledgeConfig(){
        return this.get(K_PLEDGE,V_PLEDGE);
    }

    public int setPledgeConfig(String v){
        return this.set(K_PLEDGE,v);
    }

}
