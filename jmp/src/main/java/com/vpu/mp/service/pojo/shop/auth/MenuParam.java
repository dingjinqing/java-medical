package com.vpu.mp.service.pojo.shop.auth;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class MenuParam {
	private List<?> first_web_manage;
	private List<?> first_web_decoration;
	private List<?> goods_manage;
	private List<?> first_trade_manage;
	private List<?> first_market_manage;
	private List<?> user_manger;
	private List<?> store_manage;
	private List<?> base_manger;
	
}
