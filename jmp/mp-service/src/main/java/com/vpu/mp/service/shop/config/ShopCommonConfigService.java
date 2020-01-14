package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.ShopCommonCfgInfo;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author 王兵兵
 *
 * 2019年7月9日
 */
@Service

public class ShopCommonConfigService extends BaseShopConfigService{

    @Autowired
    ShopShareConfig defaultShopShareConfig;
    @Autowired
    ShowCartConfig defaultShowCartConfig;

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
     * 开关开启，前端装修商品模块、店铺商品页、推荐商品列表、活动商品列表会展示已售罄商品
     */
	final public static String K_SOLD_OUT_GOODS = "sold_out_goods";

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
     *是否显示划线价（市场价/销量/评论）开关，单选
     * 取值：0关闭，1显示市场价，2显示销量，3显示评价数
     */
	final public static String K_DEL_MARKET = "del_market";

    /**
	 * 客服入口开关
	 */
	final public static String K_CUSTOM_SERVICE = "custom_service";
    /**
     *
     */
    final public static String K_RETURN_SERVICE = "return_service";

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
	 * 地理位置授权
	 */
	final public static String K_GEO_LOCATION = "geographic_location";

    /**
     *小程序端"店铺商品页"默认排序规则，四选一
     * add_time 按照商品上新时间倒序排列
     * goods_sale_num 按照商品销量倒序排列
     * comment_num 按照商品评价数量倒序排列
     * pv 按照商品访问次数倒序排列，7天内访问次数最多的商品将排在商品列表最上方
     */
    final public static String K_GOODS_SORT = "goods_sort";

    /**
     * 开关开启，会在商品详情页滚动展示最近的5条购买记录
     */
    final public static String K_GOODS_RECORD = "goods_record";

    /**
     * 商品默认平台分类id
     */
    final public static String K_DEFAULT_SORT = "default_sort";


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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setShowLogo need value equal zero or one");
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
		Assert.isTrue(!"".equals(value),"setLogoLink need value not equal empty");
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
		Assert.isTrue(value >= 0,"setCancelTime value need >=0");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setInvoice need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setBindMobile need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setSalesNumber need value equal zero or one");
		return this.set(K_SALES_NUMBER, value,Byte.class);
	}

    /**
     * 获取售罄商品展示设置
     * @return
     */
	public Byte getSoldOutGoods(){
	    return this.get(K_SOLD_OUT_GOODS,Byte.class,(byte)0);
    }

    /**
     * 设置售罄商品展示设置
     * @return
     */
    public int setSoldOutGoods(Byte value){
    	Assert.isTrue(value ==(byte)0 || value == (byte)1,"setSoldOutGoods need value equal zero or one");
        return this.set(K_SOLD_OUT_GOODS, value,Byte.class);
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setOrderRealName need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setOrderCid need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setConsigneeRealName need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setConsigneeCid need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setCustom need value equal zero or one");
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
		Assert.isTrue(!"".equals(value),"setCustomTitle need value not empty");
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
     *是否显示划线价（市场价/销量/评论）开关，单选
     * 取值：0关闭，1显示市场价，2显示销量，3显示评价数
     */
	public int setDelMarket(Byte value) {
		Assert.isTrue(value ==(byte)0 || value == (byte)1 || value == (byte)2 || value == (byte)3,"setDelMarket need value equal zero or one or two or three");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setCustomService need value equal zero or one");
		return this.set(K_CUSTOM_SERVICE, value,Byte.class);
	}

    /**
     * todo
     */
    public Byte getReturnService() {
        return this.get(K_RETURN_SERVICE, Byte.class, (byte) 0);
    }

    /**
     * @param value 0 或者 1
     */
    public int setReturnService(Byte value) {
    	Assert.isTrue(value == (byte) 0 || value == (byte) 1,"setReturnService need value equal zero or one");
        return this.set(K_RETURN_SERVICE, value, Byte.class);
    }

    /**
	 * 商品搜索页以及推荐商品列表中会显示购买按钮
	 * @return
	 */
	public ShowCartConfig getShowCart() {
		return this.getJsonObject(K_SHOW_CART, ShowCartConfig.class, defaultShowCartConfig);
	}

    /**
	 * 设置商品搜索页以及推荐商品列表中会显示购买按钮
	 * @param value ShowCartConfig
	 * @return
	 */
	public int setShowCart(ShowCartConfig value) {
		Assert.isTrue(value != null,"setShowCart need value not null");
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
		Assert.isTrue(!"".equals(value),"setServiceName need value not empty");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setServiceChoose need value equal zero or one");
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
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setServiceTerms need value equal zero or one");
		return this.set(K_SERVICE_TERMS, value,Byte.class);
	}

    /**
	 * 店铺分享配置
	 * @return
	 */
	public ShopShareConfig getShareConfig() {
		return this.getJsonObject(K_SHARE_CONFIG, ShopShareConfig.class, defaultShopShareConfig);
	}

    /**
	 * 设置店铺分享配置
	 * @param value 0 或者 1
	 * @return
	 */
	public int setShareConfig(ShopShareConfig value) {
		Assert.isTrue(value != null,"setShareConfig need value not null");
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
		Assert.isTrue(value != null,"setShopStyle need value not null");
		return this.setJsonObject(K_SHOP_STYLE, value);
	}


    /**
	 * 得到开启地理位置授权
	 * @return
	 */
	public Byte getGeoLocation() {
		return this.get(K_GEO_LOCATION, Byte.class, (byte)1);
	}

    /**
	 * 设置开启地理位置授权
	 * @param value 0 或者 1
	 * @return
	 */
	public int setGeoLocation(Byte value) {
		Assert.isTrue(value ==(byte)0 || value == (byte)1,"setGeoLocation need value equal zero or one ");
		return this.set(K_GEO_LOCATION, value,Byte.class);
	}

    /**
     * 小程序端"店铺商品页"默认排序规则
     * @return
     */
    public String getGoodsSort() {
        return this.get(K_GOODS_SORT, String.class, "add_time");
    }

    /**
     *小程序端"店铺商品页"默认排序规则，四选一
     * add_time 按照商品上新时间倒序排列(默认)
     * goods_sale_num 按照商品销量倒序排列
     * comment_num 按照商品评价数量倒序排列
     * pv 按照商品访问次数倒序排列，7天内访问次数最多的商品将排在商品列表最上方
     */
    public int setGoodsSort(String value) {
    	Assert.isTrue(StringUtil.isNotEmpty(value),"setGoodsSort need value isNotEmpty");
        return this.set(K_GOODS_SORT, value,String.class);
    }

    /**
     * 开关开启，会在商品详情页滚动展示最近的5条购买记录
     * @return
     */
    public Byte getGoodsRecord() {
        return this.get(K_GOODS_RECORD, Byte.class, (byte)1);
    }

    /**
     * 开关开启，会在商品详情页滚动展示最近的5条购买记录
     * @param value 0 或者 1
     * @return
     */
    public int setGoodsRecord(Byte value) {
    	Assert.isTrue(value ==(byte)0 || value == (byte)1,"setGoodsRecord need value equal zero or one");
        return this.set(K_GOODS_RECORD, value,Byte.class);
    }

    /**
     * 商品默认平台分类id
     * @return
     */
    public Integer getDefaultSort() {
        return this.get(K_DEFAULT_SORT, Integer.class, 0);
    }

    /**
     * 商品默认平台分类id
     * @return
     */
    public int setDefaultSort(Integer value) {
    	Assert.isTrue(value > 0,"setDefaultSort need value >=0");
        return this.set(K_DEFAULT_SORT, value,Integer.class);
    }


    /**
	 * 取通用配置
	 */
	public ShopCommonCfgInfo getShopCommonCfg() {
		ShopCommonCfgInfo commonCfg = new ShopCommonCfgInfo();
		this.transaction(() ->{
            commonCfg.setSalesNumber(this.getSalesNumber());
            commonCfg.setShowCart(this.getShowCart());
            commonCfg.setDelMarket(this.getDelMarket());
            commonCfg.setSoldOutGoods(this.getSoldOutGoods());
            commonCfg.setGoodsSort(this.getGoodsSort());
            commonCfg.setGoodsRecord(this.getGoodsRecord());
            commonCfg.setCustomService(this.getCustomService());
            commonCfg.setReturnService(this.getReturnService());
            commonCfg.setDefaultSort(this.getDefaultSort());
            commonCfg.setShareConfig(this.getShareConfig());
            commonCfg.setBindMobile(this.getBindMobile());
            commonCfg.setGeographicLocation(this.getGeoLocation());
		});

        return commonCfg;
	}

    /**
	 * 更新店铺通用配置
     *
     */
	public Boolean updateShopCommonInfo(ShopCommonCfgInfo commonCfg) {
		this.transaction(()->{
		    this.setSalesNumber(commonCfg.getSalesNumber());
			this.setShowCart(commonCfg.getShowCart());
			this.setDelMarket(commonCfg.getDelMarket());
			this.setSoldOutGoods(commonCfg.getSoldOutGoods());
			this.setGoodsSort(commonCfg.getGoodsSort());
			this.setGoodsRecord(commonCfg.getGoodsRecord());
			this.setCustomService(commonCfg.getCustomService());
			this.setReturnService(commonCfg.getReturnService());
			this.setDefaultSort(commonCfg.getDefaultSort());
			this.setShareConfig(commonCfg.getShareConfig());
			this.setBindMobile(commonCfg.getBindMobile());
			this.setGeoLocation(commonCfg.getGeographicLocation());
		});
		return true;
	}
}
