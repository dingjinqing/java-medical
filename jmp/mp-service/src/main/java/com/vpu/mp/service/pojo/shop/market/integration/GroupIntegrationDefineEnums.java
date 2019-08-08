package com.vpu.mp.service.pojo.shop.market.integration;

/**
 * @author huangronggang
 * @date 2019年8月6日
 */
public class GroupIntegrationDefineEnums {


	/**
	 * @author huangronggang
	 * @date 2019年8月5日
	 * 瓜分方式 0：按邀请好友数量瓜分，1：好友均分，2：随机瓜分 
	 */
	public enum DivideTypeEnum {
		NUM_INVITED_FRIENDS((byte)0),
		FRIENDS_SHARE((byte)1),
		RANDOM_PARTITION((byte)2);
		private byte value;
		DivideTypeEnum(byte value){
			this.value = value;
		}
		public Byte vlaue(){
			return value;
		}
	}
	
	/**
	 * 瓜分积分活动列表查询类型
	 * @author huangronggang
	 * @date 2019年8月6日
	 */
	public  class QueryType{
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
	
	/**
	 * 拼团瓜分积分活动 是否能继续开团
	 * @author huangronggang
	 * @date 2019年8月6日
	 */
	public enum IsContinue {
		TRUE((byte)1),
		FALSE((byte)0);
		private byte value;
		IsContinue(byte value){
			this.value = value;
		}
		public Byte value() {
			return value;
		}
	}
	/**
	 * 活动状态 ：启用和停用状态
	 * @author huangronggang
	 * @date 2019年8月8日
	 */
	public enum Status{

		STOPPED((byte)0),
		NORMAL((byte)1);
		private Byte value;
		Status(Byte value){
			this.value= value;
		}
		public Byte value(){
			return value;
		}
	}
	/**
	 * 用户开团24小时后,拼团未满员是否可以瓜分积分
	 * @author huangronggang
	 * @date 2019年8月8日
	 */
	public enum IsDayDivide{
		YES((byte)1),
		NO((byte)0);
		private byte value;
		IsDayDivide(byte value){
			this.value =  value;
		}
		public Byte value() {
			return value;
		}
	}
	/**
	 * 是否为团长
	 * @author huangronggang
	 * @date 2019年8月8日
	 */
	public  enum IsGroupper{
		YES ((byte)1),
		NO ((byte)1);
		private byte value;
		IsGroupper(byte value){
			this.value = value;
		}
		public Byte value() {
			return value;
		}
	}
}

