package com.vpu.mp.service.pojo.shop.market.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BindOARabbitParam {

	private String appId;
	private String language;
	private Integer sysId;
	private Integer taskJobId;
}
