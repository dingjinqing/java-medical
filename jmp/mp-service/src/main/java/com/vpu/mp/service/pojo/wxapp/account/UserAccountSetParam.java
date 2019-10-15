package com.vpu.mp.service.pojo.wxapp.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserAccountSetParam {

	@JsonProperty(value = "is_setting")
	private Byte isSetting=0;
	
	@JsonProperty(value = "birthday_year")
	private Integer birthdayYear;
	
	@JsonProperty(value = "birthday_month")
	private Integer birthdayMonth;
	
	@JsonProperty(value = "birthday_day")
	private Integer birthdayDay;
	
	@JsonProperty(value = "real_name")
	private String realName;
	
	@JsonProperty(value = "province_code")
	private String provinceCode;
	
	@JsonProperty(value = "city_code")
	private String cityCode;
	
	@JsonProperty(value = "district_code")
	private String districtCode;
	
	@JsonProperty(value = "sex")
	private String sex;
	
	@JsonProperty(value = "card_no")
	private String cardNo;
	
}
