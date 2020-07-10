package com.vpu.mp.service.pojo.saas.shop.officeAccount;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年8月21日 上午10:41:28
 */

@Data
public class MpOAPayManageParam {
	
	
	private Integer sysId;
	private String appId;
	private String payMchId;
	private String payKey;
	private String payCertContent;
	private String payKeyContent;

}
