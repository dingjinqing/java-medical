package com.vpu.mp.service.shop.config;

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
	 * 店铺logo点击时的跳转链接
	 * @return
	 */
	public String getLogoLink() {
		return this.get(K_LOGO_LINK, String.class, "");
	}
	
	/**
	 * 设置店铺logo点击时的跳转链接
	 * @param
	 * @return
	 */
	public int setLogoLink(String value) {
		assert(!"".equals(value));
		return this.set(K_LOGO_LINK, value);
	}
	
	/**
	 * 取待付款订单取消时间（分钟）
	 * @return
	 */
	public Integer getCancelTime() {
		return this.get(K_CANCEL_TIME, Integer.class, 0);
	}
	
	/**
	 * 设置待付款订单取消时间（分钟）
	 * @param
	 * @return
	 */
	public int setCancelTime(Integer value) {
		assert(value >= 0);
		return this.set(K_CANCEL_TIME, value,Integer.class);
	}
	
	/**
	 * 是否开启发票功能：0关闭，1开启
	 * @return
	 */
	public Byte getInvoice() {
		return this.get(K_INVOICE, Byte.class, (byte)0);
	}
	
	/**
	 * 设置是否开启发票功能：0关闭，1开启
	 * @param value 0 或者 1
	 * @return
	 */
	public int setInvoice(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_INVOICE, value,Byte.class);
	}
	
	/**
	 * 是否强制用户绑定手机号
	 * @return
	 */
	public Byte getBindMobile() {
		return this.get(K_BIND_MOBILE, Byte.class, (byte)0);
	}
	
	/**
	 * 设置是否强制用户绑定手机号
	 * @param value 0 或者 1
	 * @return
	 */
	public int setBindMobile(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_BIND_MOBILE, value,Byte.class);
	}
	
	/**
	 * 销量展示开关
	 * @return
	 */
	public Byte getSalesNumber() {
		return this.get(K_SALES_NUMBER, Byte.class, (byte)0);
	}
	
	/**
	 * 设置销量展示开关
	 * @param value 0 或者 1
	 * @return
	 */
	public int setSalesNumber(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_SALES_NUMBER, value,Byte.class);
	}
	
	/**
	 * 下单填写真实姓名开关
	 * @return
	 */
	public Byte getOrderRealName() {
		return this.get(K_ORDER_REAL_NAME, Byte.class, (byte)0);
	}
	
	/**
	 * 设置下单填写真实姓名
	 * @param value 0 或者 1
	 * @return
	 */
	public int setOrderRealName(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_ORDER_REAL_NAME, value,Byte.class);
	}
	
	/**
	 * 下单填写身份证号开关
	 * @return
	 */
	public Byte getOrderCid() {
		return this.get(K_ORDER_CID, Byte.class, (byte)0);
	}
	
	/**
	 * 设置下单填写身份证号
	 * @param value 0 或者 1
	 * @return
	 */
	public int setOrderCid(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_ORDER_CID, value,Byte.class);
	}
	
	/**
	 * 下单填写收货人真实姓名开关
	 * @return
	 */
	public Byte getConsigneeRealName() {
		return this.get(K_CONSIGNEE_REAL_NAME, Byte.class, (byte)0);
	}
	
	/**
	 * 设置下单填写收货人真实姓名
	 * @param value 0 或者 1
	 * @return
	 */
	public int setConsigneeRealName(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_CONSIGNEE_REAL_NAME, value,Byte.class);
	}
	
	/**
	 * 下单填写收货人身份证号开关
	 * @return
	 */
	public Byte getConsigneeCid() {
		return this.get(K_CONSIGNEE_CID, Byte.class, (byte)0);
	}
	
	/**
	 * 设置下单填写收货人身份证号
	 * @param value 0 或者 1
	 * @return
	 */
	public int setConsigneeCid(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_CONSIGNEE_CID, value,Byte.class);
	}
	
	/**
	 * 下单填写自定义信息开关
	 * @return
	 */
	public Byte getCustom() {
		return this.get(K_CUSTOM, Byte.class, (byte)0);
	}
	
	/**
	 * 设置下单填写自定义信息
	 * @param value 0 或者 1
	 * @return
	 */
	public int setCustom(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_CUSTOM, value,Byte.class);
	}
	
	/**
	 * 下单填写自定义信息标题
	 * @return
	 */
	public String getCustomTitle() {
		return this.get(K_CUSTOM_TITLE, String.class, "");
	}
	
	/**
	 * 设置下单填写自定义信息标题
	 * @param
	 * @return
	 */
	public int setCustomTitle(String value) {
		assert(!"".equals(value));
		return this.set(K_CUSTOM_TITLE, value);
	}
	
	/**
	 * 是否显示划线价开关
	 * @return
	 */
	public Byte getDelMarket() {
		return this.get(K_DEL_MARKET, Byte.class, (byte)0);
	}
	
	/**
	 * 设置是否显示划线价
	 * @param value 0 或者 1
	 * @return
	 */
	public int setDelMarket(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_DEL_MARKET, value,Byte.class);
	}
	
	/**
	 * 客服入口开关
	 * @return
	 */
	public Byte getCustomService() {
		return this.get(K_CUSTOM_SERVICE, Byte.class, (byte)0);
	}
	
	/**
	 * 设置客服入口开关
	 * @param value 0 或者 1
	 * @return
	 */
	public int setCustomService(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_CUSTOM_SERVICE, value,Byte.class);
	}
	
	/**
	 * 商品搜索页以及推荐商品列表中会显示购买按钮
	 * @return
	 */
	public ShowCartConfig getShowCart() {
		return this.getJsonObject(K_SHOW_CART, ShowCartConfig.class, null);
	}
	
	/**
	 * 设置商品搜索页以及推荐商品列表中会显示购买按钮
	 * @param value ShowCartConfig
	 * @return
	 */
	public int setShowCart(ShowCartConfig value) {
		assert(value != null);
		return this.setJsonObject(K_SHOW_CART, value);
	}
	
	/**
	 * 获取服务条款名称
	 * @return
	 */
	public String getServiceName() {
		return this.get(K_SERVICE_NAME, String.class, "");
	}
	
	/**
	 * 设置服务条款名称
	 * @param value String
	 * @return
	 */
	public int setServiceName(String value) {
		assert(!"".equals(value));
		return this.set(K_SERVICE_NAME, value);
	}
	
	/**
	 * 首次下单是否默认勾选服务条款
	 * @return
	 */
	public Byte getServiceChose() {
		return this.get(K_SERVICE_CHOSE, Byte.class, (byte)0);
	}
	
	/**
	 * 设置首次下单是否默认勾选服务条款
	 * @param value 0 或者 1
	 * @return
	 */
	public int setServiceChoose(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_SERVICE_CHOSE, value,Byte.class);
	}
	
	/**
	 * 获取服务条款开关
	 * @return
	 */
	public Byte getServiceTerms() {
		return this.get(K_SERVICE_TERMS, Byte.class, (byte)0);
	}
	
	/**
	 * 设置服务条款开关
	 * @param value 0 或者 1
	 * @return
	 */
	public int setServiceTerms(Byte value) {
		assert(value ==(byte)0 || value == (byte)1);
		return this.set(K_SERVICE_TERMS, value,Byte.class);
	}
	
	/**
	 * 店铺分享配置
	 * @return
	 */
	public ShopShareConfig getShareConfig() {
		return this.getJsonObject(K_SHARE_CONFIG, ShopShareConfig.class, null);
	}
	
	/**
	 * 设置店铺分享配置
	 * @param value 0 或者 1
	 * @return
	 */
	public int setShareConfig(ShopShareConfig value) {
		assert(value != null);
		return this.setJsonObject(K_SHARE_CONFIG, value);
	}
	
	/**
	 * 获取店铺风格
	 * @return
	 */
	public ShopStyleConfig getShopStyle() {
		return this.getJsonObject(K_SHOP_STYLE, ShopStyleConfig.class, null);
	}
	
	/**
	 * 设置店铺风格
	 * @param value 0 或者 1
	 * @return
	 */
	public int setShopStyle(ShopStyleConfig value) {
		assert(value != null);
		return this.setJsonObject(K_SHOP_STYLE, value);
	}
	

	/**
	 * 取通用配置
	 */
	public ShopCommonCfgInfo getShopCommonCfg() {
		ShopCommonCfgInfo commonCfg = new ShopCommonCfgInfo();
		this.transaction(() ->{
			commonCfg.setShowLogo(this.getShowLogo());
			commonCfg.setLogoLink(this.getLogoLink());
			commonCfg.setCancelTime(this.getCancelTime());
			commonCfg.setInvoice(this.getInvoice());
			commonCfg.setBindMobile(this.getBindMobile());
			commonCfg.setSalesNumber(this.getSalesNumber());
			commonCfg.setOrderRealName(this.getOrderRealName());
			commonCfg.setOrderCid(this.getOrderCid());
			commonCfg.setConsigneeRealName(this.getConsigneeRealName());
			commonCfg.setConsigneeCid(this.getConsigneeCid());
			commonCfg.setCustom(this.getCustom());
			commonCfg.setCustomTitle(this.getCustomTitle());
			commonCfg.setDelMarket(this.getDelMarket());
			commonCfg.setCustomService(this.getCustomService());
			commonCfg.setShowCart(this.getShowCart());
			commonCfg.setServiceName(this.getServiceName());
			commonCfg.setServiceChoose(this.getServiceChose());
			commonCfg.setServiceTerms(this.getServiceTerms());
			commonCfg.setShareConfig(this.getShareConfig());
			commonCfg.setShopStyle(this.getShopStyle());
		});
	
		return commonCfg;
	}
	
	/**
	 * 更新店铺通用配置
	 * 
	 */
	public Boolean updateShopCommonInfo(ShopCommonCfgInfo commonCfg) {
		this.transaction(()->{
			this.setShowLogo(commonCfg.getShowLogo());
			this.setLogoLink(commonCfg.getLogoLink());
			this.setCancelTime(commonCfg.getCancelTime());
			this.setInvoice(commonCfg.getInvoice());
			this.setBindMobile(commonCfg.getBindMobile());
			this.setSalesNumber(commonCfg.getSalesNumber());
			this.setOrderRealName(commonCfg.getOrderRealName());
			this.setOrderCid(commonCfg.getOrderCid());
			this.setConsigneeRealName(commonCfg.getConsigneeRealName());
			this.setConsigneeCid(commonCfg.getConsigneeCid());
			this.setCustom(commonCfg.getCustom());
			this.setCustomTitle(commonCfg.getCustomTitle());
			this.setDelMarket(commonCfg.getDelMarket());
			this.setCustomService(commonCfg.getCustomService());
			this.setShowCart(commonCfg.getShowCart());
			this.setServiceName(commonCfg.getServiceName());
			this.setServiceChoose(commonCfg.getServiceChoose());
			this.setServiceTerms(commonCfg.getServiceTerms());
			this.setShareConfig(commonCfg.getShareConfig());
			this.setShopStyle(commonCfg.getShopStyle());
		});
		return true;
	}
}
