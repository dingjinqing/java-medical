package com.vpu.mp.service.shop.config;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import java.util.List;

import org.jooq.DSLContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;

/**
 * @author 王兵兵
 *
 *         2019年6月26日
 * 
 */
public class BaseShopConfigService extends BaseService {

	
	/**
	 * 获取配置key对应value
	 * 
	 * @param  key
	 * @return
	 */
	protected String get(String key) {
		return db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchAny(SHOP_CFG.V);
	}

	/**
	 * 设置配置key对应value
	 * 
	 * @param  key
	 * @param  value
	 * @return
	 */
	protected int set(String key, String value) {
		String val = get(key);
		if (val == null) {
			return db().insertInto(SHOP_CFG, SHOP_CFG.K, SHOP_CFG.V).values(key, value).execute();
		} else {
			return db().update(SHOP_CFG).set(SHOP_CFG.V, value).where(SHOP_CFG.K.eq(key)).execute();
		}
	}

	/**
	 * 设置配置key对应value
	 * 
	 * @param  key
	 * @param  value
	 * @param  db
	 * @return
	 */
	protected int set(DSLContext db, String key, String value) {
		String val = get(key);
		if (val == null) {
			return db.insertInto(SHOP_CFG, SHOP_CFG.K, SHOP_CFG.V).values(key, value).execute();
		} else {
			return db.update(SHOP_CFG).set(SHOP_CFG.V, value).where(SHOP_CFG.K.eq(key)).execute();
		}
	}

	/**
	 * 设置其他类型数据配置
	 * 
	 * @param  <T>
	 * @param  key
	 * @param  value
	 * @param  toClass
	 * @return
	 */
	protected <T> int set(String key, T value, Class<? extends T> toClass) {
		return this.set(key, value.toString());
	}

	/**
	 * 设置其他类型数据配置
	 * 
	 * @param  db
	 * @param  <T>
	 * @param  key
	 * @param  value
	 * @param  toClass
	 * @return
	 */
	protected <T> int set(DSLContext db, String key, T value, Class<? extends T> toClass) {
		return this.set(db, key, value.toString());
	}

	/**
	 * 设置json对象数据配置
	 * 
	 * @param  key
	 * @param  value
	 * @return
	 */
	protected int setJsonObject(String key, Object value) {
		return this.set(key, Util.toJSON(value));
	}

	/**
	 * 设置json对象数据配置
	 * 
	 * @param  key
	 * @param  value
	 * @param  db
	 * @return
	 */
	protected int setJsonObject(DSLContext db, String key, Object value) {
		return this.set(db, key, Util.toJSON(value));
	}

	/**
	 * 获取配置key对应value,未取到时，则返回默认值
	 * 
	 * @param  key
	 * @param  defaultValue
	 * @return
	 */
	protected String get(String key, String defaultValue) {
		String val = get(key);
		return val == null ? defaultValue : val;
	}

	/**
	 * 按T类型取配置key对应value
	 * 
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @param  defaultValue
	 * @return
	 */
	protected <T> T get(String key, Class<? extends T> toClass, T defaultValue) {
		return Util.convert(get(key), toClass, defaultValue);
	}

	/**
	 * 按T类型取配置key对应json对象的value
	 * 
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @return
	 */
	protected <T> T getJsonObject(String key, Class<? extends T> toClass) {
		String value = get(key);
		if (null != value) {
			return Util.parseJSON(value, toClass);
		} else {
			return null;
		}
	}

	/**
	 * 按T类型取配置key对应json对象的value
	 * 
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected <T> T getJsonObject(String key, TypeReference valueTypeRef) {
		return Util.parseJSON(get(key), valueTypeRef);
	}

	/**
	 * 按T类型取配置key对应json对象的value,如果未取到，则返回默认值
	 * 
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @param  defaultValue
	 * @return
	 */
	protected <T> T getJsonObject(String key, Class<? extends T> toClass, T defaultValue) {
		T result = getJsonObject(get(key), toClass);
		if (result == null) {
			return defaultValue;
		}
		return result;
	}

// TODO: 把下面的配置写到子类中去处理	
//
//	/**
//	 * 快递开关
//	 */
//	final public static String K_EXPRESS = "express";
//
//	/**
//	 * 自提开关
//	 */
//	final public static String K_FETCH = "fetch";
//
//	/**
//	 * 支持退换货类型 TODO 类型北荣待完善
//	 */
//	final public static String K_DRAWBACK_TYPE = "drawback_type";
//
//	/**
//	 * 支持退换货的天数
//	 */
//	final public static String K_DRAWBACK_DAYS = "drawback_days";
//
//	/**
//	 * 等待用户提出退换货的时间
//	 */
//	final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";
//
//	/**
//	 * TODO
//	 */
//	final public static String K_PAGE = "page";
//
//	/**
//	 * 0为统一运费 1为满额包邮
//	 */
//	final public static String K_SHIP_IS_FREE = "ship_is_free";
//
//	/**
//	 * 统一运费价格
//	 */
//	final public static String K_SHIP_FEE = "ship_fee";
//
//	/**
//	 * 满额包邮价格
//	 */
//	final public static String K_START_SHIP_ORDER_GMV = "start_ship_order_gmv";
//
//	/**
//	 * 积分有效期 0永久有效 1设置有效期截止日期 2固定时间内有效
//	 */
//	final public static String K_LIMIT = "score_limit";
//
//	/**
//	 * 积分保护期（固定天数内不可使用）
//	 */
//	final public static String K_SCORE_PROTECT = "score_protect";
//
//	/**
//	 * 积分有效期设置有效期截止日期 ： 日
//	 */
//	final public static String K_SCORE_DAY = "score_day";
//
//	/**
//	 * 积分有效期设置有效期截止日期 ： 月
//	 */
//	final public static String K_SCORE_MONTH = "score_month";
//
//	/**
//	 * 积分有效期设置有效期截止日期 ： 年 （N年后）
//	 */
//	final public static String K_SCORE_YEAR = "score_year";
//
//	/**
//	 * 购物送积分开关
//	 */
//	final public static String K_SHOPPING_SCORE = "shopping_score";
//
//	/**
//	 * 购物送积分类型 0满X赠Y，多条规则自定义 1每满X赠Y，单条
//	 */
//	final public static String K_SCORE_TYPE = "score_type";
//
//	/**
//	 * 登录送积分开关
//	 */
//	final public static String K_LOGIN_SCORE = "login_score";
//
//	/**
//	 * 每日首次登录赠送的积分值
//	 */
//	final public static String K_SCORE_LOGIN = "score_login";
//
//	/**
//	 * 是否开启发票功能：0关闭，1开启
//	 */
//	final public static String K_INVOICE = "invoice";
//
//	/**
//	 * 待付款订单取消时间（分钟）
//	 */
//	final public static String K_CANCEL_TIME = "cancel_time";
//
//	/**
//	 * 
//	 */
//	final public static String K_SERVICE_COMMENT = "service_comment";
//
//	/**
//	 * 是否强制用户绑定手机号：0未绑定，1绑定
//	 */
//	final public static String K_BIND_MOBILE = "bind_mobile";
//
//	/**
//	 * 是否审核评论 0不审核 1审核
//	 */
//	final public static String K_COMMENT = "comment";
//
//	/**
//	 * 门店买单 TODO 待完善
//	 */
//	final public static String K_STORE_BUY = "store_buy";
//
//	/**
//	 * 销量展示开关：0关闭，1开启
//	 */
//	final public static String K_SALES_NUMBER = "sales_number";
//
//	/**
//	 * 模板消息库,json格式， 分A和B两个数组，分别存储小程序和公众号的templateId
//	 */
//	final public static String K_MESSAGE_LIBRARY = "message_library";
//
//	/**
//	 * 是否显示前端店铺logo：0不显示，1显示
//	 */
//	final public static String K_SHOW_LOGO = "show_logo";
//
//	/**
//	 * 下单填写真实姓名开关：0关闭，1开启
//	 */
//	final public static String K_ORDER_REAL_NAME = "order_real_name";
//
//	/**
//	 * 下单填写身份证号开关：0关闭，1开启
//	 */
//	final public static String K_ORDER_CID = "order_cid";
//
//	/**
//	 * 下单填写收货人真实姓名开关：0关闭，1开启
//	 */
//	final public static String K_CONSIGNEE_REAL_NAME = "consignee_real_name";
//
//	/**
//	 * 下单填写收货人身份证号开关：0关闭，1开启
//	 */
//	final public static String K_CONSIGNEE_CID = "consignee_cid";
//
//	/**
//	 * 下单填写自定义信息开关：0关闭，1开启
//	 */
//	final public static String K_CUSTOM = "custom";
//
//	/**
//	 * 下单填写自定义信息标题
//	 */
//	final public static String K_CUSTOM_TITLE = "custom_title";
//
//	/**
//	 * 客服入口开关：0关闭，1开启
//	 */
//	final public static String K_CUSTOM_SERVICE = "custom_service";
//
//	/**
//	 * 服务承诺开关
//	 */
//	final public static String K_PLEDGE = "pledge";
//
//	/**
//	 * 返利配置，json格式
//	 * {"status":1,"judge_status":1,"rank_status":0,"vaild":0,"protect_date":null,"desc":"\u5206\u4eab\u7ed9\u4f60\u4e00\u4e2a\u597d\u7269\u5e97\u94fa\u5feb\u6765\u8d2d\u7269\u5427\uff01","bg_img":"http:\/\/mpdevimg2.weipubao.cn\/image\/admin\/dis_bg_1.jpg","rebate_center_name":"\u5206\u9500\u4e2d\u5fc3","withdraw_status":0,"withdraw_cash":"1","withdraw_source":"wx_mini","activation":0,"activation_cfg":[]}
//	 * TODO 格式解析待完善
//	 */
//	final public static String K_FANLI = "fanli";
//
//	/**
//	 * 服务条款开关：0关闭，1开启
//	 */
//	final public static String K_SERVICE_TERMS = "service_terms";
//
//	/**
//	 * 是否显示划线价开关：0关闭，1开启
//	 */
//	final public static String K_DEL_MARKET = "del_market";
//
//	/**
//	 * 首次下单是否默认勾选服务条款
//	 */
//	final public static String K_SERVICE_CHOOSE = "service_choose";
//
//	/**
//	 * 服务条款名称
//	 */
//	final public static String K_SERVICE_NAME = "service_name";
//
//	/**
//	 * TODO 待完善
//	 */
//	final public static String K_RETURN_SERVICE = "return_service";
//
//	/**
//	 * 下单需要填写必填信息的商品，json类型，四个属性
//	 * {"add_goods":"2,1","add_cate":"","add_sort":"","add_label":"","add_brand":""}
//	 * 每项中的ID逗号分隔
//	 */
//	final public static String K_ORDER_REQUIRE_GOODS_PACKAGE = "order_require_goods_package";
//
//	/**
//	 * 购买按钮：结构 {"show_cart":1,"cart_type":"3"}
//	 * show_cart：是否展示购买按钮开关，cart_type按钮类型[0,1,2,3]4种
//	 */
//	final public static String K_SHOW_CART = "show_cart";
//
//	/**
//	 * logo点击跳转链接
//	 */
//	final public static String K_LOGO_LINK = "logo_link";
//
//	/**
//	 * 分享配置,json串存储 TODO 格式解析待完善
//	 */
//	final public static String K_SHARE_CONFIG = "share_config";

}
