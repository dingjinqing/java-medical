package com.vpu.mp.service.pojo.shop.decoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 新国
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageCategoryListQueryParam {
	public Integer currentPage;
	public String keywords;
}