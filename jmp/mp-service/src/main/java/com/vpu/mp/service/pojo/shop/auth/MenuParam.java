package com.vpu.mp.service.pojo.shop.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty("first_web_manage")
	private List<?> firstWebManage;
	
	@JsonProperty("first_web_decoration")
	private List<?> firstWebDecoration;
	
	@JsonProperty("goods_manage")
	private List<?> goodsManage;
	
	@JsonProperty("first_trade_manage")
	private List<?> firstTradeManage;
	
	@JsonProperty("first_market_manage")
	private List<?> firstMarketManage;
	
	@JsonProperty("user_manger")
	private List<?> userManger;
	
	@JsonProperty("store_manage")
	private List<?> storeManage;
	
	@JsonProperty("base_manger")
	private List<?> baseManger;

}
