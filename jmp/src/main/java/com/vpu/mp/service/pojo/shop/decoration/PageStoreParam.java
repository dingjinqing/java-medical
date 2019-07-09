package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class PageStoreParam {
	public Integer pageId;
	public String pageName;
	public String pageContent;
	public Byte pageState;
	public Integer catId;
}
