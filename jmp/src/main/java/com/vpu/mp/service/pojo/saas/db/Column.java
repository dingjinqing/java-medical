package com.vpu.mp.service.pojo.saas.db;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Column {
	/**
	 * 字段名
	 */
	String field;

	/**
	 * 类型
	 */
	String type;

	String typeRange1 = "";

	String typeRange2 = "";

	String typeUnsigned = "";

	/**
	 * 是否可以为NULL： YES NO
	 */
	String nullType = "YES";

	/**
	 * 默认值
	 */
	String defaultValue = null;

	/**
	 * 当前列SQL语句
	 */
	String createSql;

	@Override
	public String toString() {
		return "field=" + field + ", type=" + type + ", typeRange1=" + typeRange1 + ", typeRange2=" + typeRange2
				+ ", nullType=" + nullType + ", defaultValue=" + defaultValue;
	}

	static public boolean isEquals(Column c1, Column c2) {
		return StringUtils.equalsIgnoreCase(c1.getField(), c2.getField())
				&& StringUtils.equalsIgnoreCase(c1.getType(), c2.getType())
				&& StringUtils.equalsIgnoreCase(c1.getTypeRange1(), c2.getTypeRange1())
				&& StringUtils.equalsIgnoreCase(c1.getTypeRange2(), c2.getTypeRange2())
				&& StringUtils.equalsIgnoreCase(c1.getTypeUnsigned(), c2.getTypeUnsigned())
				&& (StringUtils.equalsIgnoreCase(c1.getDefaultValue(), c2.getDefaultValue()) || StringUtils
						.equalsAnyIgnoreCase(c1.getDefaultValue(), "CURRENT_TIMESTAMP", "now()", "current_timestamp()")
						&& StringUtils.equalsAnyIgnoreCase(c2.getDefaultValue(), "CURRENT_TIMESTAMP", "now()",
								"current_timestamp()"));

	}

}
