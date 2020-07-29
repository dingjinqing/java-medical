package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商品返利明细列表入参
 * @author 常乐
 * 2019年8月12日
 */
@Data
public class RebateGoodsDetailParam {
	private Integer goodsId;
	private String distributorMobile;
	private String distributorName;
	private String mobile;
	private String username;
	private Timestamp startRebateTime;
	private Timestamp endRebateTme;
	@JsonProperty("rebateORderSn")
	private String rebateOrderSn;
	private Byte rebateStatus;
	
	/**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows;
	
}
