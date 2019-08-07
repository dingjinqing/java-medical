package com.vpu.mp.service.pojo.saas.shop.mp;

import lombok.Data;

@Data
public class MpAuthShopVersionVo {
	private String appId;
	private Integer shopId;
	private String nickName;
	private String headImg;
	private Byte isAuthOk;
	private String qrcodeUrl;
	private Byte openPay;
	private Integer bindTemplateId;
	private Byte auditState;
	private Byte publishState;
	private String versionName;
}
