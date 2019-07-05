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
	 * 
	 */
	final public static String K_BOTTOM = "bottom";
	/**
	 * 
	 */
	final public static String K_EXPRESS = "express";
	/**
	 * 
	 */
	final public static String K_FETCH = "fetch";
	/**
	 * 
	 */
	final public static String K_DRAWBACK_TYPE = "drawback_type";
	/**
	 * 
	 */
	final public static String K_DRAWBACK_DAYS = "drawback_days";
	/**
	 * 
	 */
	final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";
	/**
	 * 
	 */
	final public static String K_PAGE = "page";
	/**
	 * 
	 */
	final public static String K_SHIP_IS_FREE = "ship_is_free";
	/**
	 * 
	 */
	final public static String K_SHIP_FEE = "ship_fee";
	/**
	 * 
	 */
	final public static String K_START_SHIP_ORDER_GMV = "start_ship_order_gmv";
	/**
	 * 
	 */
	final public static String K_LIMIT = "score_limit";
	/**
	 * 
	 */
	final public static String K_SCORE_PROTECT = "score_protect";
	/**
	 * 
	 */
	final public static String K_SCORE_DAY = "score_day";
	/**
	 * 
	 */
	final public static String K_SCORE_MONTH = "score_month";
	/**
	 * 
	 */
	final public static String K_SCORE_YEAR = "score_year";
	/**
	 * 
	 */
	final public static String K_SCORE_TYPE = "score_type";
	/**
	 * 
	 */
	final public static String K_SCORE_LOGIN = "score_login";
	/**
	 * 
	 */
	final public static String K_INVOICE = "invoice";
	/**
	 * 
	 */
	final public static String K_CANCEL_TIME = "cancel_time";
	/**
	 * 
	 */
	final public static String K_SERVICE_COMMENT = "service_comment";
	/**
	 * 
	 */
	final public static String K_BIND_MOBILE = "bind_mobile";
	/**
	 * 
	 */
	final public static String K_COMMENT = "comment";
	/**
	 * 
	 */
	final public static String K_STORE_BUY = "store_buy";
	/**
	 * 
	 */
	final public static String K_SALES_NUMBER = "sales_number";
	/**
	 * 
	 */
	final public static String K_MESSAGE_LIBRARY = "message_library";
	/**
	 * 
	 */
	final public static String K_SHOW_LOGO = "show_logo";
	/**
	 * 
	 */
	final public static String K_ORDER_REAL_NAME = "order_real_name";
	/**
	 * 
	 */
	final public static String K_ORDER_CID = "order_cid";
	/**
	 * 
	 */
	final public static String K_CONSIGNEE_REAL_NAME = "consignee_real_name";
	/**
	 * 
	 */
	final public static String K_CONSIGNEE_CID = "consignee_cid";
	/**
	 * 
	 */
	final public static String K_CUSTOM = "custom";
	/**
	 * 
	 */
	final public static String K_CUSTOM_TITLE = "custom_title";
	/**
	 * 
	 */
	final public static String K_CUSTOM_SERVICE = "custom_service";
	/**
	 * 
	 */
	final public static String K_PLEDGE = "pledge";
	/**
	 * 
	 */
	final public static String K_FANLI = "fanli";
	/**
	 * 
	 */
	final public static String K_SERVICE_TERMS = "service_terms";
	/**
	 * 
	 */
	final public static String K_DEL_MARKET = "del_market";
	/**
	 * 
	 */
	final public static String K_SERVICE_CHOOSE = "service_choose";
	/**
	 * 
	 */
	final public static String K_SERVICE_NAME = "service_name";
	/**
	 * 
	 */
	final public static String K_RETURN_SERVICE = "return_service";
	/**
	 * 
	 */
	final public static String K_ORDER_REQUIRE_GOODS_PACKAGE = "order_require_goods_package";
	/**
	 * 
	 */
	final public static String K_SHOW_CART = "show_cart";
	/**
	 * 
	 */
	final public static String K_LOGO_LINK = "logo_link";
	/**
	 * 
	 */
	final public static String K_SHARE_CONFIG = "share_config";
	
	
	

	public  List<ShopCfgRecord> getAllShopCfg() {
		return db().selectFrom(SHOP_CFG).fetch();
	}
	
	public String getShopCfg(String k) {
		String v = db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
		 if(v == null) {
			 this.setShopCfg(k, "");
			 return "";
		 }else {
			 return v;
		 }
	}
	
	public String getShopCfg(String k,String v) {
		String result =  (String) db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
		if(result == null) {
			 this.setShopCfg(k, v);
			 return v;
		 }else {
			 return result;
		 }
	
	}
	
	public  List<ShopCfgRecord> getShopCfgs(List<String> keys){
		return    db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.in(keys)).fetch();
	}
	
	public Boolean setShopCfg(String k,String v) {
		return db().update(SHOP_CFG).set(SHOP_CFG.V,v).where(SHOP_CFG.K.eq(k)).execute() > 0 ? true : false;
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
