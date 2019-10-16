package com.vpu.mp.service.pojo.wxapp.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月16日 上午10:07:09
 */
@Data
public class UserAccountSetVo {

	private String provinceCode;
	
	private String cityCode;
	
	private String districtCode;
	
	private UserInfo userInfo;
	
	
}
