package com.vpu.mp.service.foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuUtil {

	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>>getMenu(String jsonPath) {
		String json = Util.loadResource(jsonPath);
		return Util.parseJSON(json, ArrayList.class);
	}
	

	 public static List<Map<String,Object>> getRoleMenu(String jsonPath,Integer roleId) {
		 List<Map<String,Object>> menu = getMenu(jsonPath);
		 if(roleId == 0) {
			 return menu;
		 }
		 
		 
		 return null;
	 }
}
