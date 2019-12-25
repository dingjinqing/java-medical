package com.vpu.mp.service.shop.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vpu.mp.service.shop.config.message.MessageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo.Setting;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo.ShowPoster;

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
	public GoodsRecommendSortConfigService recommendSortConfigService;
	@Autowired
	public DeliverTemplateConfigService deliverTemplateConfigService;
	@Autowired
	public FirstSpecialConfigService firstSpecialConfigService;
	@Autowired
	public InsteadPayConfig insteadPayConfig;
	@Autowired
	public CollectGiftConfigService collectGiftConfigService;
	@Autowired
	public MessageConfigService messageConfigService;
	@Autowired
    public GiftConfigService giftConfigService;
    @Autowired
    public OrderExportConfigService orderExportCfg;
	/**
	 * 得到店铺配置
	 * 
	 * @return
	 */
	public WxAppConfigVo getAppConfig() {
		ShopRecord shop = saas.shop.getShopById(getShopId());
		Byte showLogo = shopCommonConfigService.getShowLogo();
		WxAppConfigVo config = new WxAppConfigVo();
		Setting setting = WxAppConfigVo.Setting.builder().shopFlag(shop.getShopFlag())
				.shopStyle(convertShopStyle(shopCommonConfigService.getShopStyle()))
				.hideBottom(shop.getHidBottom()).build();
		config.setBottomNavigateMenuList(bottomCfg.getBottomNavigatorConfig());
		config.setShowLogo(showLogo);
		config.setLogoLink(shopCommonConfigService.getLogoLink());
		config.setSetting(setting);
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
		if (config == null || config.getShopStyleValue() == null) {
			return new String[] { "", "" };
		}
		String style = config.getShopStyleValue().trim();
		if (style.indexOf("rgb") == -1) {
			return style.split(",");
		} else {
			String pattern = "rgb\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\),\\s*rgb\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(style);
			if (m.find()) {
				return new String[] {
						String.format("#%02x%02x%02x", Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)),
								Integer.valueOf(m.group(3))),
						String.format("#%02x%02x%02x", Integer.valueOf(m.group(4)), Integer.valueOf(m.group(5)),
								Integer.valueOf(m.group(6)))
				};
			} else {
				return new String[] {};
			}
		}
	}

	/**
	 * 得到语言包
	 * 
	 * @return
	 */
	public String getLocalePack(String language ) {
		return Util.loadResource("static/i18n/wxapp/" + language + ".json");
	}
}
