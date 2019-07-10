package com.vpu.mp.service.shop.config;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import com.vpu.mp.service.pojo.shop.config.ShopCommonCfgInfo;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;

/**
 * @author 王兵兵
 *
 * 2019年7月9日
 */
public class ShopCommonConfigService extends BaseShopConfigService{
	
	/**
	 * 是否显示前端店铺logo
	 */
	final public static String K_SHOW_LOGO = "show_logo";
	
	/**
	 * logo点击跳转链接
	 */
	final public static String K_LOGO_LINK = "logo_link";
	
	/**
	 * 待付款订单取消时间（分钟）
	 */
	final public static String K_CANCEL_TIME = "cancel_time";
	
	/**
	 * 是否开启发票功能：0关闭，1开启
	 */
	final public static String K_INVOICE = "invoice";
	
	/**
	 * 是否强制用户绑定手机号
	 */
	final public static String K_BIND_MOBILE = "bind_mobile";
	
	/**
	 * 销量展示开关
	 */
	final public static String K_SALES_NUMBER = "sales_number";
	
	/**
	 * 下单填写真实姓名开关
	 */
	final public static String K_ORDER_REAL_NAME = "order_real_name";
	
	/**
	 * 下单填写身份证号开关
	 */
	final public static String K_ORDER_CID = "order_cid";
	
	/**
	 * 下单填写收货人真实姓名开关
	 */
	final public static String K_CONSIGNEE_REAL_NAME = "consignee_real_name";
	
	/**
	 * 下单填写收货人身份证号开关
	 */
	final public static String K_CONSIGNEE_CID = "consignee_cid";
	
	/**
	 * 下单填写自定义信息开关
	 */
	final public static String K_CUSTOM = "custom";
	
	/**
	 * 下单填写自定义信息标题
	 */
	final public static String K_CUSTOM_TITLE = "custom_title";
	
	/**
	 * 是否显示划线价开关
	 */
	final public static String K_DEL_MARKET = "del_market";
	
	/**
	 * 客服入口开关
	 */
	final public static String K_CUSTOM_SERVICE = "custom_service";
	
	/**
	 * 商品搜索页以及推荐商品列表中会显示购买按钮
	 */
	final public static String K_SHOW_CART = "show_cart";
	
	/**
	 * 服务条款名称
	 */
	final public static String K_SERVICE_NAME = "service_name";
	
	/**
	 * 首次下单是否默认勾选服务条款
	 */
	final public static String K_SERVICE_CHOSE = "service_chose";
	
	/**
	 * 服务条款开关
	 */
	final public static String K_SERVICE_TERMS = "service_terms";
	
	/**
	 * 店铺分享配置
	 */
	final public static String K_SHARE_CONFIG = "share_config";
	
	/**
	 * 店铺风格
	 */
	final public static String K_SHOP_STYLE = "shop_style";
	
	/**
	 * 是否显示Logo配置
	 * @return
	 */
	public Byte getShowLogo() {
		return this.get(K_SHOW_LOGO, Byte.class, (byte)0);
	}
	
	/**
	 * 设置显示Logo配置
	 * @param value 0 或者 1
	 * @return
	 */
	public int setShowLogo(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_SHOW_LOGO, value,Byte.class);
	}
	

	/**
	 * 取通用配置
	 */
	public ShopCommonCfgInfo getShopCommonCfg() {
		ShopCommonCfgInfo commonCfg = new ShopCommonCfgInfo();
		commonCfg.setShowLogo((Byte) this.get(K_SHOW_LOGO, Byte.class, (byte)0));
		commonCfg.setLogoLink(this.get(K_LOGO_LINK, String.class, ""));
		commonCfg.setCancelTime(this.get(K_CANCEL_TIME, Integer.class, 0));
		commonCfg.setInvoice((Byte) this.get(K_INVOICE, Byte.class, (byte)0));
		commonCfg.setBindMobile((Byte) this.get(K_BIND_MOBILE, Byte.class, (byte)0));
		commonCfg.setSalesNumber((Byte) this.get(K_SALES_NUMBER, Byte.class, (byte)0));
		commonCfg.setOrderRealName((Byte) this.get(K_ORDER_REAL_NAME, Byte.class, (byte)0));
		commonCfg.setOrderCid((Byte) this.get(K_ORDER_CID, Byte.class, (byte)0));
		commonCfg.setConsigneeRealName((Byte) this.get(K_CONSIGNEE_REAL_NAME, Byte.class, (byte)0));
		commonCfg.setConsigneeCid((Byte) this.get(K_CONSIGNEE_CID, Byte.class, (byte)0));
		commonCfg.setCustom((Byte) this.get(K_CUSTOM, Byte.class, (byte)0));
		commonCfg.setCustomTitle(this.get(K_CUSTOM_TITLE, String.class, ""));
		commonCfg.setDelMarket((Byte) this.get(K_DEL_MARKET, Byte.class, (byte)0));
		commonCfg.setCustomService((Byte) this.get(K_CUSTOM_SERVICE, Byte.class, (byte)0));
		commonCfg.setShowCart(this.getJsonObject(K_SHOW_CART, ShowCartConfig.class));
		commonCfg.setServiceName(this.get(K_SERVICE_NAME, String.class, ""));
		commonCfg.setServiceChoose((Byte) this.get(K_SERVICE_CHOSE, Byte.class, (byte)0));
		commonCfg.setServiceTerms((Byte) this.get(K_SERVICE_TERMS, Byte.class, (byte)0));
		commonCfg.setShareCfg(this.getJsonObject(K_SHARE_CONFIG, ShopShareConfig.class));
		commonCfg.setShopStyle(this.getJsonObject(K_SHOP_STYLE, ShopStyleConfig.class));
		return commonCfg;
	}
	
	/**
	 * 更新店铺通用配置
	 * 
	 */
	public Boolean updateShopCommonInfo(ShopCommonCfgInfo commonCfg) {
		try {
			 db().transaction(configuration -> {
				DSLContext db = DSL.using(configuration);
				
		        this.set(db, K_SHOW_LOGO, commonCfg.getShowLogo(), Byte.class);
		        this.set(db, K_LOGO_LINK, commonCfg.getLogoLink(), String.class);
		        this.set(db, K_CANCEL_TIME, commonCfg.getCancelTime(), Integer.class);
		        this.set(db, K_INVOICE, commonCfg.getInvoice(), Byte.class);
		        this.set(db, K_BIND_MOBILE, commonCfg.getBindMobile(), Byte.class);
		        this.set(db, K_SALES_NUMBER, commonCfg.getSalesNumber(), Byte.class);
		        this.set(db, K_ORDER_REAL_NAME, commonCfg.getOrderRealName(), Byte.class);
		        this.set(db, K_ORDER_CID, commonCfg.getOrderCid(), Byte.class);
		        this.set(db, K_CONSIGNEE_REAL_NAME, commonCfg.getConsigneeRealName(), Byte.class);
		        this.set(db, K_CONSIGNEE_CID, commonCfg.getConsigneeCid(), Byte.class);
		        this.set(db, K_CUSTOM, commonCfg.getCustom(), Byte.class);
		        this.set(db, K_CUSTOM_TITLE, commonCfg.getCustomTitle(), String.class);
		        this.set(db, K_DEL_MARKET, commonCfg.getDelMarket(), Byte.class);
		        this.set(db, K_CUSTOM_SERVICE, commonCfg.getCustomService(), Byte.class);
		        this.set(db, K_SHOW_CART, commonCfg.getShowCart(), ShowCartConfig.class);
		        this.set(db, K_SERVICE_NAME, commonCfg.getServiceName(), String.class);
		        this.set(db, K_SERVICE_CHOSE, commonCfg.getServiceChoose(), Byte.class);
		        this.set(db, K_SERVICE_TERMS, commonCfg.getServiceTerms(), Byte.class);
		        this.set(db, K_SHARE_CONFIG, commonCfg.getShareCfg(), ShopShareConfig.class);
		        this.set(db, K_SHOP_STYLE, commonCfg.getShopStyle(), ShopStyleConfig.class);
			 });
		 }
		 catch(RuntimeException e) {
			 e.printStackTrace();
			 return false;
		 }
		return true;
	}
}
