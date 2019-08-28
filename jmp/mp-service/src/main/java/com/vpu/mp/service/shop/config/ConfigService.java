package com.vpu.mp.service.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo.ShowPoster;
import com.vpu.mp.service.shop.goods.GoodsBrandConfigService;

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
	@Autowired
    public DeliverTemplateConfigService deliverTemplateConfigService;
    @Autowired
    public FirstSpecialConfigService firstSpecialConfigService;
	@Autowired
    public InsteadPayConfig insteadPayConfig;
	@Autowired
	public CollectGiftConfigService collectGiftConfigService;
	
	/**
	 * 得到店铺配置
	 * @return
	 */
	public WxAppConfigVo getAppConfig() {
		Byte showLogo = shopCommonConfigService.getShowLogo();
		WxAppConfigVo config = new WxAppConfigVo();
		ShopRecord shop = saas.shop.getShopById(getShopId());
		config.setBottomNavigateMenuList(bottomCfg.getBottomNavigatorConfig());
		config.setShowLogo(showLogo);
		config.setLogoLink(shopCommonConfigService.getLogoLink());
		config.setHideBottom(shop.getHidBottom());
		config.setShopStyleConfig(convertShopStyle(shopCommonConfigService.getShopStyle()));
		ShowPoster showPoster = new ShowPoster();
		// TODO: 取ShowPoster数据
		config.setShowPoster(showPoster);
		return config;
	}
	
	/**
	 * 获取店铺风格
	 * 
	 * @return
	 */
	public String[] convertShopStyle(ShopStyleConfig config) {
		String style = config.getShopStyleValue();
		if (style.indexOf("rgb") == -1) {
			return style.split(",");
		} else {
			String[] s = style.split("),(");
			s[0] += ")";
			if (s.length > 1) {
				s[1] = "(" + s[1];
			}
			return s;
		}
	}
	
	
}
