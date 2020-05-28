package com.vpu.mp.service.shop.order;

import com.vpu.mp.config.ApiExternalGateConfig;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.service.pojo.saas.api.ApiJsonResult;
import com.vpu.mp.service.pojo.shop.order.pos.PosShippingParam;
import com.vpu.mp.service.shop.order.action.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王帅
 */
@Service
public class OrderApiService extends ShopBaseService {

    @Autowired
    public ShipService ship;

    public ApiJsonResult shipping(ApiExternalGateParam gateParam) {
        try {
            return ship.shippingApi(Util.parseJson(gateParam.getContent(), PosShippingParam.class));
        } catch (MpException e) {
            ApiJsonResult apiJsonResult = new ApiJsonResult();
            apiJsonResult.setCode(ApiExternalGateConfig.ERROR_CODE_SYNC_FAIL);
            apiJsonResult.setMsg(e.getMessage());
            return apiJsonResult;
        }
    }
}
