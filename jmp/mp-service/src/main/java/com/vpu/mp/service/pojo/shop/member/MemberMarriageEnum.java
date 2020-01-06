package com.vpu.mp.service.pojo.shop.member;

import com.vpu.mp.service.foundation.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 黄壮壮
* @Date: 2019年8月19日
* @Description: 会员受教育程度-枚举类
*/
public enum MemberMarriageEnum {
	
	/**未婚 */
	UNMARRIED(1,"member.marriage.unmarried"),
	/** 已婚  */
	MARRIED(2,"member.marriage.married"),
	/** 保密*/
	CONFIDENTIALITY(3,"member.marriage.confidentiality");
	/** 数字代号 */
	private Integer code;
	/**受教育程度*/
	private String name;
	private static Map<Integer,MemberMarriageEnum> map = new HashMap<>();
	static {
		for(MemberMarriageEnum e: MemberMarriageEnum.values()) {
			map.put(e.code,e);
		}
	}
	
	private MemberMarriageEnum(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	/** 根据code Id获取枚举类 */
	public static MemberMarriageEnum valueOf(int code) {
		return (MemberMarriageEnum)map.get(code);
	}
	/**
	 * 根据 code id 获取相应的name
	 * @return
	 */
	public static String getNameByCode(int code) {
		MemberMarriageEnum obj = valueOf(code);
		return obj.getName();
	}

    /**
     * 自定义语言  常乐
     * @param code 教育程度数字代号
     * @param lang 语言
     * @return
     */
    public static String getNameByCode(int code,String lang) {
        MemberMarriageEnum obj = valueOf(code);
        return Util.translateMessage(lang, obj.getName(),"","member");
    }
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	/**
	 * 获取所有教育名称
	 * @param lang 语言
	 * @param choose true 加入"请选择"，false，没有此项
	 */
	public static List<String> getAllEducation(String lang,boolean choose){
		List<String> eduList = new ArrayList<String>(); 
		if(choose) {
			// 请选择
			eduList.add(Util.translateMessage(lang,"member.please.choose","","member"));
		}
		int length = MemberMarriageEnum.values().length;
		for(int i=0;i<length;i++) {
			eduList.add(MemberMarriageEnum.getNameByCode(i,lang));
		}
		return eduList;
	}
	
	public static List<String> getAllEducation(String lang){
		return getAllEducation(lang,false);
	}
	
	public static String[] getArrayMarriage(String lang) {
		MemberMarriageEnum[] values = MemberMarriageEnum.values();
		String[] result=new String[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i]=MemberMarriageEnum.getNameByCode(i,lang);
		}
		return result;
	}
}
