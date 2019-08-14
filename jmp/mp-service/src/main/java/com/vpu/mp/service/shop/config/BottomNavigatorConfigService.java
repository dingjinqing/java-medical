package com.vpu.mp.service.shop.config;

import java.util.List;

import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;
import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

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
	 * 
	 * @return
	 */
	public List<BottomNavigatorConfig> getBottomNavigatorConfig() {
		return this.getJsonObject(K_BOTTOM, new TypeReference<List<BottomNavigatorConfig>>() {
		});
	}

	/**
	 * 设置底部导航配置
	 * 
	 * @param config
	 * @return
	 */
	public int setBottomNavigatorConfig(List<BottomNavigatorConfig> config) {
		return this.setJsonObject(K_BOTTOM, config);
	}

	public void test() {
//		this.db().update(SHOP_CFG).set(SHOP_CFG.V,"3").where(SHOP_CFG.REC_ID.eq((short) 3)).execute();
		this.transaction(() -> {
			this.db().update(SHOP_CFG).set(SHOP_CFG.V,"5").where(SHOP_CFG.REC_ID.eq((short) 3)).execute();
			try {
			this.test2();
			}catch(DataAccessException e) {
				System.out.println(e);
			}
			this.db().update(SHOP_CFG).set(SHOP_CFG.V,"5").where(SHOP_CFG.REC_ID.eq((short) 4)).execute();
		});
	}

	public void test2() {
		this.transaction(() -> {
			this.db().update(SHOP_CFG).set(SHOP_CFG.V,"5").where(SHOP_CFG.REC_ID.eq((short) 2)).execute();
			this.db().insertInto(SHOP_CFG).set(SHOP_CFG.REC_ID,(short) 2).execute();
		});
	}

}
