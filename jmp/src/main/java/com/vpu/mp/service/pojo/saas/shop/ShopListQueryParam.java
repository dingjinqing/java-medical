package com.vpu.mp.service.pojo.saas.shop;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class ShopListQueryParam {
	public Integer page;
	public String keywords;
	public Integer sysId;
	public Integer isUse;
	public String shopType;
	public String principalName;
	public String accountKey;
	public Byte shopFlag;
	public Byte isEnabled;
	public Byte hidBottom;
	public String act;
}
