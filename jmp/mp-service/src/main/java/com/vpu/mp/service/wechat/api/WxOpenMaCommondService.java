package com.vpu.mp.service.wechat.api;

import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 微信好物圈的操作
 * @author zhaojianqiang
 *
 * 2019年11月12日 下午5:47:32
 */
public interface WxOpenMaCommondService extends WxOpenMaMpHttpBase {
    /**
     * 导入订单的
     * {@value} https://api.weixin.qq.com/mall/importorder?action=add-order&is_history=0/1&access_token=ACCESS_TOKEN
     */
    static final String WX_COMMOND_IMPORTORDER = "https://api.weixin.qq.com/mall/importorder?action=add-order&is_history=0";
    /**
     * 订单测试
     */
    static final String WX_COMMOND_IMPORTORDER_TEST = "https://api.weixin.qq.com/mall/importorder?action=add-order&is_test=1";



    /**
     * 导入订单
     *
     * @param appId https调用凭证
     *              {@value LOGISTICS_GET_ALL_DELIVERY}
     */
    default WxOpenResult importorder(String appId,String orderList) throws WxErrorException {
    	String json = post(appId, WX_COMMOND_IMPORTORDER_TEST, orderList);
    	return WxOpenGetResult.fromJson(json);
    }


}
