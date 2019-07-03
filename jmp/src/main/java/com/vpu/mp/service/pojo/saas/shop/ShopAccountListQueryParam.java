package com.vpu.mp.service.pojo.saas.shop;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class ShopAccountListQueryParam {
	public Byte state;
	public Integer page;
	public String keywords;
	public String company;
}
