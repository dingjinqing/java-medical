package com.vpu.mp.service.shop.member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
/**
 * 	用户导出配置
 * @author 黄壮壮
 *
 */
@Service
public class UserExpCfgService extends BaseShopConfigService {
	@Autowired
	private UserExportService uExpSvc;
	private static final String KEY = "userExport_list";
	
	public void setUserExpCfg(List<String> value) {
		String strValue = Util.toJson(value);
		if(strValue==null) {
			return;
		}
		set(KEY,strValue,String.class);
	}
	
	public List<String> getUserExpCfg() {
		String res = get(KEY,String.class,null);
		if(StringUtils.isBlank(res)) {
			return new ArrayList<String>(Arrays.asList(uExpSvc.getFirstColName()));
		}
		
		return Util.json2Object(res, new TypeReference<List<String>>() {
        }, false);
	}
}
