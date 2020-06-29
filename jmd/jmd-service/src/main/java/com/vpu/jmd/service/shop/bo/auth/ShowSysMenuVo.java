package com.vpu.jmd.service.shop.bo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 展示system列表
 * @author zhaojianqiang
 * @date 2020年6月22日上午9:34:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSysMenuVo {
	@JsonProperty("first_web_manage")
	private List<String> firstWebManage;

	@JsonProperty("first_web_decoration")
	private List<String> firstWebDecoration;

	@JsonProperty("data_manage")
	private List<String> dataManage;

	@JsonProperty("goods_manage")
	private List<String> goodsManage;

	@JsonProperty("user_manger")
	private List<String> userManger;

	@JsonProperty("first_trade_manage")
	private List<String> firstTradeManage;

	@JsonProperty("set_manage")
	private List<String> setManage;

}
