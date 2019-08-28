package com.vpu.mp.service.pojo.shop.member;

import java.util.HashMap;
import java.util.Map;

/**
* @author 黄壮壮
* @Date: 2019年8月19日
* @Description: 会员受教育程度-枚举类
*/
public enum MemberEducationEnum {
	
	/** 初中 */
	JUNIOR(0,"member.education.junior"),
	/** 高中 */
	HIGH(1,"member.education.high"),
	/** 中专 */
	SECONDARY(2,"member.education.secondary"),
	/** 大专 */
	COLLEGE(3,"member.education.college"),
	/** 本科 */
	UNDERGRADUATE(4,"member.education.undergraduate"),
	/** 硕士 */
	MASTER(5,"member.education.master"),
	/** 博士 */
	DOCTOR(6,"member.education.doctor"),
	/** 其他 */
	OTHER(7,"member.education.other");
	/** 数字代号 */
	private Integer code;
	/**受教育程度*/
	private String name;
	private static Map<Integer,MemberEducationEnum> map = new HashMap<>();
	static {
		for(MemberEducationEnum e: MemberEducationEnum.values()) {
			map.put(e.code,e);
		}
	}
	
	private MemberEducationEnum(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	/** 根据code Id获取枚举类 */
	public static MemberEducationEnum valueOf(int code) {
		return (MemberEducationEnum)map.get(code);
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
}
