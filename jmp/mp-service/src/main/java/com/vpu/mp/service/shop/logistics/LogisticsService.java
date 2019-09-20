package com.vpu.mp.service.shop.logistics;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.wechat.api.impl.WxOpenMaServiceExtraImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.springframework.stereotype.Service;

/**
 * @author liufei
 * @date 20190920
 */
@Service
public class LogisticsService extends ShopBaseService {

    /**
     * 获取支持的快递公司列表
     */
    public String getAllDelivery() throws WxErrorException {
        WxOpenMaServiceExtraImpl maService = open.getMaExtService();
        String result = maService.getAllDelivery(getAppId());
        return result;
    }

    /**
     * 绑定物流公司
     */
    public WxOpenResult bindAccount(String jsonParam) throws WxErrorException {
        WxOpenMaServiceExtraImpl maService = open.getMaExtService();
        return maService.bindAccount(getAppId(), jsonParam);
    }

    /**
     * 获取接口调用凭证appid
     */
    private String getAppId() {
        return saas.shop.mp.getAuthShopByShopId(getShopId()).getAppId();
    }
}
