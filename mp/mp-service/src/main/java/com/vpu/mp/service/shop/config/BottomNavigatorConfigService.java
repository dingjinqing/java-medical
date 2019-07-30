package com.vpu.mp.service.shop.config;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import java.util.List;

import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;

/**
 * 
 * @author 新国
 *
 */
@Service

public class BottomNavigatorConfigService extends BaseShopConfigService {

	/**
	 * 导航键值
	 */
	final public static String K_BOTTOM = "bottom";

	/**
	 * 获取底部导航配置
	 * @return
	 */
	public List<BottomNavigatorConfig> getBottomNavigatorConfig() {
		return this.getJsonObject(K_BOTTOM, new TypeReference<List<BottomNavigatorConfig>>() {});
	}

	/**
	 * 设置底部导航配置
	 * @param config
	 * @return
	 */
	public int setBottomNavigatorConfig(List<BottomNavigatorConfig> config) {
		return this.setJsonObject(K_BOTTOM, config);
	}
	
	
	public void test() {
		try {
			this.transaction(() -> {
				
				Short v =1;
				db().update(SHOP_CFG).set(SHOP_CFG.V, "3").where(SHOP_CFG.K.eq("express")).execute();
				db().insertInto(SHOP_CFG, SHOP_CFG.REC_ID).values(v).execute();
				
				try {
					this.transaction(() -> {
						db().update(SHOP_CFG).set(SHOP_CFG.V, "3").where(SHOP_CFG.K.eq("express")).execute();
						db().insertInto(SHOP_CFG, SHOP_CFG.REC_ID).values(v).execute();
					});
				}catch(DataAccessException e) {
					System.out.println("rollback 1 ok:"+e.getMessage());
				}
				
				
			});
		}catch(DataAccessException e) {
			System.out.println("rollback 2 ok:"+e.getMessage());
		}
		
		
		
	}
}
