package com.vpu.mp.service.pojo.shop.market.packagesale;

/**
 * @author huangronggang
 * @date 2019年8月12日
 */
public class PackSaleConstant {
	
	public static final String ID_DELIMITER =",";
	/** 活动状态 */
	public class ActivityStatus{
		/** 全部 */
		public final static  int ALL=0;
		/** 进行中 */
		public final static int UNDER_WAY=1;
		/** 未开始 */
		public final static int UNSTARTED=2;
		/** 已过期 */
		public final static int OVERDUE=3;
		/** 已停用 */
		public final static int STOPPED=4;
	}
	/**第二三分组状态 （启用/停用）*/
	public class Status{
		/** 启用 */
		public static final byte NORMAL=1;
		/** 停用 */
		public final static byte DISABLED=0;
	}
}

