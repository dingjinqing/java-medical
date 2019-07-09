package com.vpu.mp.service.shop.config;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.config.ShopCfgPojo;

/**
 * @author 王兵兵
 *
 * 2019年6月26日
 * 
 */
public class ShopCfgService extends BaseService{
	
	/**
	 * 底部导航配置，格式：json数组
	 * [{"text":"首页","btn":0,"normal":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_no_2.png","hover":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_yes_2.png","page":"pages/index/index"},
	 * {"text":"门店","btn":0,"normal":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_no_1.png","hover":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_yes_2.png","page":"pages/storelist/storelist"},
	 * {"text":"购物车","btn":0,"normal":"http://mpdevimg.weipubao.cn/upload/4748160/image/20190220/23ftVfIZlhuXPQcx.png","hover":"http://mpdevimg.weipubao.cn/upload/4748160/image/20190220/23ftVfIZlhuXPQcx.png","page":"pages/cart/cart"},
	 * {"text":"商品分类","btn":0,"normal":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_no_5.png","hover":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_yes_5.png","page":"pages/sort/sort"},
	 * {"text":"个人中心","btn":0,"normal":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_no_4.png","hover":"http://mpdevimg.weipubao.cn/image/admin/icon_mps/icon_yes_4.png","page":"pages/usercenter/usercenter"}]
	 * TODO 结构说明待完善 
	 */
	final public static String K_BOTTOM = "bottom";
	
	/**
	 * 快递开关
	 */
	final public static String K_EXPRESS = "express";
	
	/**
	 * 自提开关
	 */
	final public static String K_FETCH = "fetch";
	
	/**
	 * 支持退换货类型
	 * TODO 类型北荣待完善
	 */
	final public static String K_DRAWBACK_TYPE = "drawback_type";
	
	/**
	 * 支持退换货的天数
	 */
	final public static String K_DRAWBACK_DAYS = "drawback_days";
	
	/**
	 * 等待用户提出退换货的时间
	 */
	final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";
	
	/**
	 * TODO
	 */
	final public static String K_PAGE = "page";
	
	/**
	 * 0为统一运费 1为满额包邮
	 */
	final public static String K_SHIP_IS_FREE = "ship_is_free";
	
	/**
	 * 统一运费价格
	 */
	final public static String K_SHIP_FEE = "ship_fee";
	
	/**
	 * 满额包邮价格
	 */
	final public static String K_START_SHIP_ORDER_GMV = "start_ship_order_gmv";
	
	/**
	 * 积分有效期  0永久有效   1设置有效期截止日期  2固定时间内有效
	 */
	final public static String K_LIMIT = "score_limit";
	
	/**
	 * 积分保护期（固定天数内不可使用）
	 */
	final public static String K_SCORE_PROTECT = "score_protect";
	
	/**
	 * 积分有效期设置有效期截止日期  ： 日
	 */
	final public static String K_SCORE_DAY = "score_day";
	
	/**
	 * 积分有效期设置有效期截止日期  ： 月
	 */
	final public static String K_SCORE_MONTH = "score_month";
	
	/**
	 * 积分有效期设置有效期截止日期  ： 年   （N年后）
	 */
	final public static String K_SCORE_YEAR = "score_year";
	
	/**
	 * 购物送积分开关
	 */
	final public static String K_SHOPPING_SCORE = "shopping_score";
	
	/**
	 * 购物送积分类型 0满X赠Y，多条规则自定义      1每满X赠Y，单条
	 */
	final public static String K_SCORE_TYPE = "score_type";
	
	/**
	 * 登录送积分开关
	 */
	final public static String K_LOGIN_SCORE= "login_score";
	
	/**
	 * 每日首次登录赠送的积分值
	 */
	final public static String K_SCORE_LOGIN = "score_login";
	
	/**
	 * 是否开启发票功能：0关闭，1开启
	 */
	final public static String K_INVOICE = "invoice";
	
	/**
	 * 待付款订单取消时间（分钟）
	 */
	final public static String K_CANCEL_TIME = "cancel_time";
	
	/**
	 * 
	 */
	final public static String K_SERVICE_COMMENT = "service_comment";
	
	/**
	 * 是否强制用户绑定手机号：0未绑定，1绑定
	 */
	final public static String K_BIND_MOBILE = "bind_mobile";
	
	/**
	 * 是否审核评论  0不审核    1审核
	 */
	final public static String K_COMMENT = "comment";
	
	/**
	 * 门店买单 TODO  待完善
	 */
	final public static String K_STORE_BUY = "store_buy";
	
	/**
	 * 销量展示开关：0关闭，1开启
	 */
	final public static String K_SALES_NUMBER = "sales_number";
	
	/**
	 * 模板消息库,json格式， 分A和B两个数组，分别存储小程序和公众号的templateId
	 */
	final public static String K_MESSAGE_LIBRARY = "message_library";
	
	/**
	 * 是否显示前端店铺logo：0不显示，1显示
	 */
	final public static String K_SHOW_LOGO = "show_logo";
	
	/**
	 * 下单填写真实姓名开关：0关闭，1开启
	 */
	final public static String K_ORDER_REAL_NAME = "order_real_name";
	
	/**
	 * 下单填写身份证号开关：0关闭，1开启
	 */
	final public static String K_ORDER_CID = "order_cid";
	
	/**
	 * 下单填写收货人真实姓名开关：0关闭，1开启
	 */
	final public static String K_CONSIGNEE_REAL_NAME = "consignee_real_name";
	
	/**
	 * 下单填写收货人身份证号开关：0关闭，1开启
	 */
	final public static String K_CONSIGNEE_CID = "consignee_cid";
	
	/**
	 * 下单填写自定义信息开关：0关闭，1开启
	 */
	final public static String K_CUSTOM = "custom";
	
	/**
	 * 下单填写自定义信息标题
	 */
	final public static String K_CUSTOM_TITLE = "custom_title";
	
	/**
	 * 客服入口开关：0关闭，1开启
	 */
	final public static String K_CUSTOM_SERVICE = "custom_service";
	
	/**
	 * 服务承诺开关
	 */
	final public static String K_PLEDGE = "pledge";
	
	/**
	 * 返利配置，json格式
	 * {"status":1,"judge_status":1,"rank_status":0,"vaild":0,"protect_date":null,"desc":"\u5206\u4eab\u7ed9\u4f60\u4e00\u4e2a\u597d\u7269\u5e97\u94fa\u5feb\u6765\u8d2d\u7269\u5427\uff01","bg_img":"http:\/\/mpdevimg2.weipubao.cn\/image\/admin\/dis_bg_1.jpg","rebate_center_name":"\u5206\u9500\u4e2d\u5fc3","withdraw_status":0,"withdraw_cash":"1","withdraw_source":"wx_mini","activation":0,"activation_cfg":[]}
	 * TODO 格式解析待完善
	 */
	final public static String K_FANLI = "fanli";
	
	/**
	 * 服务条款开关：0关闭，1开启
	 */
	final public static String K_SERVICE_TERMS = "service_terms";
	
	/**
	 * 是否显示划线价开关：0关闭，1开启
	 */
	final public static String K_DEL_MARKET = "del_market";
	
	/**
	 * 首次下单是否默认勾选服务条款
	 */
	final public static String K_SERVICE_CHOOSE = "service_choose";
	
	/**
	 * 服务条款名称
	 */
	final public static String K_SERVICE_NAME = "service_name";
	
	/**
	 * TODO 待完善
	 */
	final public static String K_RETURN_SERVICE = "return_service";
	
	/**
	 * 下单需要填写必填信息的商品，json类型，四个属性
	 * {"add_goods":"2,1","add_cate":"","add_sort":"","add_label":"","add_brand":""}
	 * 每项中的ID逗号分隔
	 */
	final public static String K_ORDER_REQUIRE_GOODS_PACKAGE = "order_require_goods_package";
	
	/**
	 * 购买按钮：结构
	 * {"show_cart":1,"cart_type":"3"}
	 * show_cart：是否展示购买按钮开关，cart_type按钮类型[0,1,2,3]4种
	 */
	final public static String K_SHOW_CART = "show_cart";
	
	/**
	 * logo点击跳转链接
	 */
	final public static String K_LOGO_LINK = "logo_link";
	
	/**
	 * 分享配置,json串存储
	 * TODO 格式解析待完善
	 */
	final public static String K_SHARE_CONFIG = "share_config";
	
	/**
	 * 店铺风格
	 * {"shopStyleId":"5","shopStyleValue":"#feb609,#333333"}
	 * TODO 格式解析待完善
	 */
	final public static  String K_SHOP_STYLE="shop_style";
	
	/**
	   * 搜索配置  
	 *  title_action:默认搜索（取值如下   0：不设置，1：全部商品，2：自定义）
	 *  title_custom:自定义搜索值
	 *  is_open_history:是否开启搜索历史
	 *  is_open_hot_words：是否开启热词
	 *  hot_words: 热词
	 * {"title_action":"2","title_custom":"","is_open_history":"1","is_open_hot_words":"1","hot_words":["瓜子花生八宝粥","啤酒饮料矿泉水"]}
	 */
	final public static String K_SEARCH_CONFIG="search_config";
	

	public  List<ShopCfgRecord> getAllShopCfg() {
		return db().selectFrom(SHOP_CFG).fetch();
	}
	
	public String getShopCfg(String k) {
		return db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
	}
	
	public String getShopCfg(String k,String v) {
		String result =  (String) db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
		if(result == null) {
			 return v;
		 }else {
			 return result;
		 }
	
	}
	
	public  List<ShopCfgRecord> getShopCfgs(List<String> keys){
		return db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.in(keys)).fetch();
	}
	
	public Boolean setShopCfg(String k,String v) {
		if(null != db().select(SHOP_CFG.REC_ID).from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchOne()) {
			return db().update(SHOP_CFG).set(SHOP_CFG.V,v).where(SHOP_CFG.K.eq(k)).execute() > 0 ? true : false;
		}else {
			return db().insertInto(SHOP_CFG,SHOP_CFG.K,SHOP_CFG.V).values(k,v).execute() > 0 ? true : false;
		}
		
	}
	
	public Boolean updateShopCommonInfo(List<ShopCfgPojo> shopCfgs) {
		 try {
			 db().transaction(configuration -> {
				DSLContext db = DSL.using(configuration);
		        for(int j=0 ; j<shopCfgs.size() ; j++){
	                	int res = db.update(SHOP_CFG).set(SHOP_CFG.V,shopCfgs.get(j).getV()).where(SHOP_CFG.K.eq(shopCfgs.get(j).getK())).execute();
	                	if(res <= 0) {
	                		throw new RuntimeException(shopCfgs.get(j).getK());
	                	}
	                }
			 });
		 }
		 catch(RuntimeException e) {
			 logger().info(e.getMessage() + " update fail");
			 return false;
		 }
		return true;
	}
	
//	public HashMap<String,String> getGoodsPackage(Integer action){
//		String goodsPackage = "";
//		if(action == 1) {
//			goodsPackage = this.getShopCfg("order_require_goods_package","{}");
//		}else {
//			goodsPackage = this.getShopCfg("order_return_goods_package","{}");
//		}
//		HashMap<String, String> goodsPackageMap = new HashMap<String,String>();
//		if("".equals(goodsPackage)) {
//			goodsPackageMap = Util.parseJSON(goodsPackage,HashMap.class);
//			for(HashMap.Entry<String,String> entry : goodsPackageMap.entrySet()) {
//				String[] goodsIds = entry.getValue().split(",");
//				if ("add_goods".equals(entry.getKey())) {
//	               GoodsRecord goodsList = saas().getShopApp(429979).goods.getRow()
//	            } 
//			}
//		}
//		
//		return null;
//	}
}
