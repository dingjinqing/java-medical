package com.vpu.mp.service.shop.goods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.BaseComponent;

@Component
@Scope("prototype")
public class Goods extends BaseComponent {
	
	public void test() {
		System.out.println("test current shop id:"+this.getShopId());
	}
}
