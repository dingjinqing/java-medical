package com.vpu.mp.service.pojo.saas.shop.mp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class MpOfficeAccountVo {

	private String appId;
	private String nickName;
	private byte isAuthOk;
}
