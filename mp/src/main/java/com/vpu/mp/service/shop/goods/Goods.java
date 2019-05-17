package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.main.tables.B2cShop;
import com.vpu.mp.service.foundation.BaseComponent;
/**
 * 
 * @author 新国
 *
 */
public class Goods extends BaseComponent {

	public void test() {
		System.out.println("test current shop id:" + this.getShopId());
		Integer number = (Integer) dm.db().selectCount().from(B2cShop.B2C_SHOP).fetchOne().get(0);

		System.out.println("shop num: " + number);

	}
}
