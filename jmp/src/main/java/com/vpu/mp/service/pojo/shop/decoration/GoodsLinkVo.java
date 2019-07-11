package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

@Data
public class GoodsLinkVo {
	public Integer goodsId;
	public String goodsName;
	public String goodsImg;
	public String keyWords;
	public static Integer page;
}
