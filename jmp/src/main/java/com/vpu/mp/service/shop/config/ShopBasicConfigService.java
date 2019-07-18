package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.shop.config.pledge.ShopPledgeService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 商家-基础配置(主Service,引用分支)
 * @author 卢光耀
 */
@Service
@Scope("prototype")
public class ShopBasicConfigService extends BaseService {

    public ShopPledgeService shopPledge;


}
