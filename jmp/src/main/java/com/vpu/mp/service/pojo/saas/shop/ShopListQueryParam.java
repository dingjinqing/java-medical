package com.vpu.mp.service.pojo.saas.shop;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class ShopListQueryParam {
	public String accountKey;
	public String keywords;
	public Integer isUse;
	public String shopType;
	public Byte shopFlag;
	public Byte isEnabled;
	public Byte hidBottom;
	public Integer currentPage;
	public Integer pageRows;
}
