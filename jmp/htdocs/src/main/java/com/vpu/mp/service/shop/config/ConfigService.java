package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class ConfigService extends BaseService {
	public BottomNavigatorConfigService bottomCfg;
	public SearchConfigService searchCfg;
	public ShopStyleConfigService shopStyleCfg;
	public CommentConfigService commentConfigService;
	public PledgeConfigService pledgeCfg;
	public ShopCommonConfigService shopCommonConfigService;
	public ShopReturnConfigService returnConfigService;
	public UserCenterConfigService  userCenterConfigService;
	public StoreConfigService storeConfigService;
	public DistributionConfigService distributioncfg;
}
