package com.vpu.jmd.service.config;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.config.pledge.ShopPledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商家-基础配置(主Service,引用分支)
 * @author 卢光耀
 */
@Service

public class ShopBasicConfigService extends ShopBaseService {

	@Autowired
    public ShopPledgeService shopPledge;


}
