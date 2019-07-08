package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

@Data
public class PageStoreParam {
	public Integer pageId;
	public String pageName;
	public String pageContent;
	public Byte pageState;
	public Integer catId;
}
