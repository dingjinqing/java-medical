package com.vpu.mp.service.shop.config;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
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
		config.setLogoLink(shopCommonConfigService.getLogoLink());
		config.setSetting(setting);
		config.setStatus(getStatus(user!=null?user.getUserId():null));
		ShowPoster showPoster = new ShowPoster();
		// TODO: 取ShowPoster数据
		config.setShowPoster(showPoster);
		return config;
	}

	/**
	 * 获取店铺风格 rgb/rgba转16进制
	 * 
	 * @return
	 */
	public String[] convertShopStyle(ShopStyleConfig config) {
		if (config == null || config.getShopStyleValue() == null) {
			return new String[] { "", "" };
		}
		String style = config.getShopStyleValue().trim();
		if (!style.contains("rgb")) {
			return style.split(",");
		} else if (style.startsWith("rgba")){
			String pattern = "rgba\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\),\\s*rgba\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(style);
			if (m.find()) {
				Color color1 =new Color(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4)));
				Color color2 =new Color(Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6)), Integer.parseInt(m.group(7)),Integer.parseInt(m.group(8)));
				String hexColor1 = toHexFromColor(color1);
				String hexColor2 = toHexFromColor(color2);
				return new String[]{hexColor1,hexColor2};
			}
		} else {
			String pattern = "rgb\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\),\\s*rgb\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(style);
			if (m.find()) {
				Color color1 =new Color(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
				Color color2 =new Color(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6)));
				String hexColor1 = toHexFromColor(color1);
				String hexColor2 = toHexFromColor(color2);
				return new String[]{hexColor1,hexColor2};
			}
		}
		return new String[] {};
	}
	/**
	 * Color对象转换成字符串
	 * @param color Color对象
	 * @return 16进制颜色字符串
	 * */
	private static String toHexFromColor(Color color){
		String r,g,b;
		StringBuilder su = new StringBuilder();
		r = Integer.toHexString(color.getRed());
		g = Integer.toHexString(color.getGreen());
		b = Integer.toHexString(color.getBlue());
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() ==1 ? "0" +g : g;
		b = b.length() == 1 ? "0" + b : b;
		r = r.toUpperCase();
		g = g.toUpperCase();
		b = b.toUpperCase();
		su.append("0xFF");
		su.append(r);
		su.append(g);
		su.append(b);
		return su.toString();
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
