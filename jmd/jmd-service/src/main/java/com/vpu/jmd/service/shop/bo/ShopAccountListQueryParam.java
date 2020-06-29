package com.vpu.jmd.service.shop.bo;

import lombok.Data;

/**
 *
 * @author 新国
 *
 */
@Data
public class ShopAccountListQueryParam {
	public Byte state;
	public String keywords;
	public String company;
	public Integer currentPage;
	public Integer pageRows;
}
