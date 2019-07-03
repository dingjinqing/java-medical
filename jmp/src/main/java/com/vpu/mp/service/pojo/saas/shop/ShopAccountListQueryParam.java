package com.vpu.mp.service.pojo.saas.shop;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class ShopAccountListQueryParam {
	public Byte state;
	public Page page;
	public String keywords;
	public String company;
}
