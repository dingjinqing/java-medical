package com.vpu.mp.service.pojo.shop.overview.marketcalendar;

import lombok.Getter;

@Getter
public class CalendarAction {
	public static final String ADD = "add";
	public static final String EDIT = "edit";

	/** 未开始 */
	public static final Byte ONE = (byte) 1;
	/** 进行中 */
	public static final Byte TWO = (byte) 2;
	/** 失效 */
	public static final Byte THREE = (byte) 3;
	/** 已结束 */
	public static final Byte FOUR = (byte) 4;
}
