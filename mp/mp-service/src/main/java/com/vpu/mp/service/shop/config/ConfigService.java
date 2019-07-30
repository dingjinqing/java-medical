package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.goods.GoodsBrandConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public DistributionConfigService distributionCfg;
	@Autowired
	public ShopMsgTemplateConfigService shopMsgTemplateService;
	@Autowired
	public BargainConfigService bargainCfg;
	@Autowired
    public GoodsBrandConfigService goodsBrandConfigService;
}
