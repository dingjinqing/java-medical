package com.vpu.mp.service.pojo.shop.market.integralconvert;

import lombok.Data;

/**
 * 积分兑换分页查询列表
 * @author liangchen
 * @date 2019年8月14日
 */
@Data
public class IntegralConvertListParam {
	/** 
	  *   活动状态 
	 * 0：全部活动   1：进行中     2：未开始      3：已过期       4：已停用 
	  *   默认：1
	 */
	public static final int ALL = 0;
	public static final int DOING = 1;
	public static final int TODO = 2;
	public static final int DID = 3;
	public static final int STOP = 4;
	private Integer actState = DOING;
	
	/* 禁用信息 */
	public static final int BLOCK = 0;
	public static final int NOT_BLOCK = 1;
	
	
	/* 分页信息 */
	private Integer currentPage;
	private Integer pageRows;
	
}
