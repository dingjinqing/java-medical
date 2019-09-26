package com.vpu.mp.service.foundation.data;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * 	快递映射关系
 * @author 王帅
 *
 */
public class ExpressMapping {
	public static Map<Integer,String> mapping = ImmutableMap.<Integer,String>builder()
			.put(1, "shentong")
			.build();
}
