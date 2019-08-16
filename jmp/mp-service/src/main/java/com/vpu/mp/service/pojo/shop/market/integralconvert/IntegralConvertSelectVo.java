package com.vpu.mp.service.pojo.shop.market.integralconvert;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * 查询指定积分活动
 * @author liangchen
 * @date 2019年8月16日
 */
@Data
public class IntegralConvertSelectVo {
	
	/** 活动名称 */
	private String name;
	
	/** 开始日期 */
	private Timestamp startTime;
	
	/** 结束日期 */
	private Timestamp endTime;
	
	/** 单个用户最多可兑换数量 填0则不限制 */
	private Short maxExchangeNum;
	
	/** 商品id */
	private Integer goodsId;
	
	/** 商品名称 */
	private String goodsName;
	
	/** 商品兑换配置 */
	public List<IntegralConvertProductVo> productVo;
	
	/** 店铺分享配置 */
	public String shareConfig;
	
}
