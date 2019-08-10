package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

/**
 * 返利商品统计入参
 * @author 常乐
 * 2019年8月10日
 */
@Data
public class RebateGoodsParam {
	public Integer goodsType;
	public String goodsName;
	
	/**
     * 	分页信息
     */
    public int currentPage;
    public int pageRows;
}
