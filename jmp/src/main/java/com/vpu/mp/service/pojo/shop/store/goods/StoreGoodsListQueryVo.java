package com.vpu.mp.service.pojo.shop.store.goods;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月12日
 */
@Data
public class StoreGoodsListQueryVo {
	private String goodsImg;
	private String goodsName;
	
	/**
	 * 是否已同步pos,1是已同步
	 */
	private Byte isSync;
	
	/**
	 * 未同步时的规格价格
	 */
	private Double prdPrice;
	/**
	 * 未同步时的规格库存
	 */
	private Integer prdNumber;
	private String prdDesc;
	
	/**
	 * 同步之后的规格价格
	 */
	private Double productPrice;
	/**
	 * 同步之后的规格库存
	 */
	private Integer productNumber;
	
	private String prdSn;
	private String prdCodes;
}
