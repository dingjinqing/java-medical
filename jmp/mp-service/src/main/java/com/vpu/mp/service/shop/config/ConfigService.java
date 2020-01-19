package com.vpu.mp.service.shop.config;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.ShopSelectInnerResp;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo.Setting;
import com.vpu.mp.service.pojo.wxapp.config.WxAppConfigVo.ShowPoster;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.config.message.MessageConfigService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 新国
 *
 */
@Service

public class ConfigService extends ShopBaseService {
	private static final byte ONE = 1;
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
    @Autowired
    public DomainConfig domainConfig;
	/**
	 * 得到店铺配置
	 * 
	 * @return
	 */
	public WxAppConfigVo getAppConfig(WxAppSessionUser user) {
		ShopRecord shop = saas.shop.getShopById(getShopId());
		Byte showLogo = shopCommonConfigService.getShowLogo();
		WxAppConfigVo config = new WxAppConfigVo();
		Setting setting = WxAppConfigVo.Setting.builder().shopFlag(shop.getShopFlag())
				.shopStyle(convertShopStyle(shopCommonConfigService.getShopStyle()))
				.hideBottom(shop.getHidBottom()).build();
		config.setBottomNavigateMenuList(bottomCfg.getBottomNavigatorConfig());
		config.setShowLogo(showLogo);
		if(StringUtil.isNotEmpty(shop.getLogo())){
            config.setLogo(domainConfig.imageUrl(shop.getLogo()));
        }
		config.setLogoLink(shopCommonConfigService.getLogoLink());
		config.setSetting(setting);
		config.setStatus(getStatus(user!=null?user.getUserId():null));
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
	public String getLocalePack(String language) {
		return Util.loadResource("static/i18n/wxapp/" + language + ".json");
	}
	
	
	private byte getStatus(Integer userId) {
		byte status=0;
		ShopSelectInnerResp shopInfo = saas.shop.getShopInfo(getShopId());
		String expireTimeStatus = shopInfo.getExpireTimeStatus();
		if(expireTimeStatus.equals("1")) {
			//已过期
			status=1;
		}
		Byte isEnabled = shopInfo.getIsEnabled();
		if(Objects.equals(isEnabled, ONE)) {
			//已禁止
			status=2;
		}
		Byte businessState = shopInfo.getBusinessState();
		if(!Objects.equals(businessState, ONE)) {
			//未营业
			status=3;
		}
		if(userId!=null) {
			UserRecord user = saas.getShopApp(getShopId()).user.getUserByUserId(userId);
			Byte delFlag = user.getDelFlag();
			if(Objects.equals(delFlag, ONE)) {
				//用户被禁止登陆
				status=4;
			}
		}
		return status;
		
	}
}
