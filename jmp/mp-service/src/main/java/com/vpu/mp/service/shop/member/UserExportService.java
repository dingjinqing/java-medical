package com.vpu.mp.service.shop.member;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.userExp.UserExpVo;

@Service
public class UserExportService extends ShopBaseService{
	private static final String ALL_CFG="all_cfg";
	private static final String CHOOSED_CFG="choosed_cfg";
;	/**
	 * 	获取用户导出配置
	 */
	public ObjectNode getExportCfg() {
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode ob = mapper.createObjectNode();
		JsonNode ars = mapper.valueToTree(getAllExportCfg());
		ob.set(ALL_CFG, ars);
		//	从数据取数据
		ob.set(CHOOSED_CFG, null);
		return ob;
	}
	
	/**
	 * 	获取所有可选的配置信息
	 */
	private List<String> getAllExportCfg(){
		//		全部可选的信息
		Class<?> clazz = UserExpVo.class;
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null) {
			int length = fields.length;
			List<String> cons = new ArrayList<>(length);
			
			for(Field field: fields) {
				JsonProperty props = field.getDeclaredAnnotation(JsonProperty.class);
				if(props != null) {
					cons.add(props.index(), props.value());
				}
			}
			return cons;
		}else {
			return Collections.emptyList();
		}
	}
}
