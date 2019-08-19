package com.vpu.mp.service.pojo.shop.market.integralconvert;

public class IntegralConvertConstant {

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

	/** 禁用信息 0：停用  1：启用 */
	public static final int BLOCK = 0;
	public static final int NOT_BLOCK = 1;
	
	/** 0：未删除 1：删除 */
	public static final int NOT_DELETE = 0; 
	public static final int DELETE = 1; 
}
