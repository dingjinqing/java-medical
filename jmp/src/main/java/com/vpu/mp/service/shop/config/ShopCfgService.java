package com.vpu.mp.service.shop.config;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import java.lang.reflect.Field;
import java.util.List;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.config.ShopCfg;

/**
 * @author 王兵兵
 *
 * 2019年6月26日
 * 
 */
public class ShopCfgService extends BaseService{

	public  List<ShopCfgRecord> getAllShopCfg() {
		return db().selectFrom(SHOP_CFG).fetch();
	}
	
	public String getShopCfg(String k) {
		return (String) db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
	}
	
	public String getShopCfg(String k,String v) {
		String result =  (String) db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny(SHOP_CFG.V);
		return result == null ? v : result;
	
	}
	
	public Integer updateShopCommonInfo(ShopCfg shopCfg) {
		 Field[] field = shopCfg.getClass().getDeclaredFields(); 
	        for(int j=0 ; j<field.length ; j++){
	                String name = field[j].getName();
	                if(!"".equals((String)Util.getObjectProperty(shopCfg, name))) {
	                	db().update(SHOP_CFG).set(SHOP_CFG.V,(String)Util.getObjectProperty(shopCfg, name)).where(SHOP_CFG.K.eq(Util.humpToUnderline(name))).execute();
	                }
	        }
		return 1;
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
