package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class PageCategoryListQueryParam {
	public Integer id;
	public Integer del;
	public String name;
	public Integer page;
	public String keywords;
	public String act;
};