package com.vpu.mp.service.pojo.shop.market.message.maconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxMaSubscribeMessageData {
	private String name;
	private String value;
}
