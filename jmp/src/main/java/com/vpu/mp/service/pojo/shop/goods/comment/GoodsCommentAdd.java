package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liangchen
 * @date 2019年07月09日
 */
@Data
public class GoodsCommentAdd {
	
	private Integer goodsId;
	private String goodsImg;
	private String goodsName;
	private String goodsSn;
	private String sortName;
	private BigDecimal shopPrice;
	private Integer goodsNumber;
	private Integer goodsSaleNum;
	/** 访客数 */
	private Integer uv;
	/** 浏览量 */
	private Integer pv;

}
