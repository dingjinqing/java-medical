package com.vpu.jmd.service.shop.bo.officeAccount;

import lombok.Data;

/**
 *
 * @author zhaojianqiang
 *
 *         2019年8月21日 上午10:41:28
 */

@Data
public class MpOfficeAccountListParam {

	private Integer sysId;
	private String appId;
	private Integer currentPage;
	private Integer pageRows;

}
