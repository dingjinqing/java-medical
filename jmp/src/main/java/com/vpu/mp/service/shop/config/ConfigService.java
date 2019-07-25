package com.vpu.mp.service.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * 
 * @author 新国
 *
 */
@Service

public class ConfigService extends ShopBaseService {
	@Autowired
	public BottomNavigatorConfigService bottomCfg;
	@Autowired
	public SearchConfigService searchCfg;
	@Autowired
	public ShopStyleConfigService shopStyleCfg;
	@Autowired
	public CommentConfigService commentConfigService;
	@Autowired
	public PledgeConfigService pledgeCfg;
	@Autowired
	public ShopCommonConfigService shopCommonConfigService;
	@Autowired
	public ShopReturnConfigService returnConfigService;
	@Autowired
	public UserCenterConfigService userCenterConfigService;
	@Autowired
	public StoreConfigService storeConfigService;
	@Autowired
	public DistributionConfigService distributioncfg;
	@Autowired
	public ShopMsgTemplateConfigService shopMsgTemplateService;
	@Autowired
	public BargainConfigService bargainCfg;
}
