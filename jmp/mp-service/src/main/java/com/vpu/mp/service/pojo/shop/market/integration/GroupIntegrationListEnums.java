package com.vpu.mp.service.pojo.shop.market.integration;

/**
 * @author huangronggang
 * @date 2019年8月6日
 */
public class GroupIntegrationListEnums {
	/** 是否为团长 */
	public enum IsGrouper{
		TRUE((byte)1),
		FALSE((byte)0);
		private byte value;
		IsGrouper(byte value){
			this.value = value;
		}
		public Byte value() {
			return value;
		}
	}
	/** 团的活动状态 ：进行中 ，成功，失败 */
	public enum Status{
		UNDERWAY((byte)0),
		SUCCESS((byte)1),
		FAIL((byte)2);
		private byte value ;
		Status(byte value){
			this.value =  value;
		}
		public Byte value() {
			return value;
		}
	}
	/** 是否为新人 */
	public enum IsNew{
		NO((byte)0),
		YES((byte)1);
		private byte value;
		IsNew(byte value){
			this.value = value;
		}
		public Byte value() {
			return value;
		}
	}
	
}

