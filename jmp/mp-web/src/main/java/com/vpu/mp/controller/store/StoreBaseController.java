package com.vpu.mp.controller.store;

import com.vpu.mp.auth.StoreAuth;
import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.pojo.shop.auth.StoreTokenAuthInfo;
import com.vpu.mp.service.shop.ShopApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author chenjie
 * @date 2020年08月25日
 */
public class StoreBaseController extends BaseController {

    @Autowired
    protected StoreAuth storeAuth;


    /**
     * 得到当前登录店铺
     * @return
     */
    protected ShopApplication shop() {
        StoreTokenAuthInfo user = storeAuth.user();
        Assert.isTrue(user!=null && user.isShopLogin(),"shop is null");
        return saas.getShopApp(user.getLoginShopId());
    }

    /**
     * 得到登录店铺ID
     * @return
     */
    protected Integer shopId() {
        StoreTokenAuthInfo user = storeAuth.user();
        Assert.isTrue(user!=null && user.isShopLogin(),"shop is null");
        return user.getLoginShopId();
    }

    /**
     * 日志
     * @return
     */
    protected Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
