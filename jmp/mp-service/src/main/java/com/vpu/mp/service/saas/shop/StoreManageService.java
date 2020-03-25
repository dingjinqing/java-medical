package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.Tables.SHOP;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthVo;
import com.vpu.mp.service.pojo.shop.store.authority.StoreCfgParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreConstant;
import com.vpu.mp.service.pojo.shop.store.authority.Sub;

/**
 * 门店权限的service
 * 
 * @author zhaojianqiang
 * @time 上午10:46:39
 */
@Service
public class StoreManageService extends MainBaseService {
	private static final String STOREJSON = "admin.storeTop.json";

	/**
	 * 获取店员权限的配置和选中状态
	 * @param shopId
	 * @return
	 */
	public StoreAuthVo getstoreJson(Integer shopId) {
		String cfgInfoJson = db().select(SHOP.STORE_CLERK_PRIVILEGE_LIST).from(SHOP).where(SHOP.SHOP_ID.eq(shopId))
				.fetchOneInto(String.class);
		StoreCfgParam cfgInfo = null;
		if (!StringUtils.isEmpty(cfgInfoJson)) {
			cfgInfo = Util.parseJson(cfgInfoJson, StoreCfgParam.class);
		}
		String json = Util.loadResource(STOREJSON);
		List<StoreAuthParam> list = Util.parseJson(json, new TypeReference<List<StoreAuthParam>>() {
		});
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIsOnly().equals(StoreConstant.ISONLY_TWO)) {
				list.remove(i--);
				continue;
			}
			List<Sub> sub = list.get(i).getSub();
			Byte hasSubCheck = StoreConstant.CHECK_ZERO;
			if (sub != null && sub.size() > 0) {
				for (int j = 0; j < sub.size(); j++) {
					if (sub.get(j).getIsOnly().equals(StoreConstant.ISONLY_TWO)) {
						sub.remove(j--);
						continue;
					}
					if (cfgInfo != null && cfgInfo.getSubMenuCfg().size() > 0
							&& inList(sub.get(j).getEnName(), cfgInfo.getSubMenuCfg())) {
						sub.get(j).setCheck(StoreConstant.CHECK_ONE);
						hasSubCheck = StoreConstant.CHECK_ONE;
					}
				}
			}
			//门店概况一直是选中的
			list.get(0).setCheck(StoreConstant.CHECK_ONE);
			list.get(i).setCheck(hasSubCheck);

		}
		List<String> funcfg= cfgInfo==null?new ArrayList<String>():cfgInfo.getFunCfg();
		return new StoreAuthVo(list,funcfg);
	}

	private Boolean inList(String eName, List<String> list) {
		for (String string : list) {
			if (eName.equals(string)) {
				return true;
			}
		}
		return false;

	}
	
	public boolean setting(StoreAuthListParam param,Integer shopId) {
		List<String> menuCfg = param.getMenuCfg();
		boolean flag=false;
		for (String string : menuCfg) {
			if(string.equals("store_overview")) {
				flag=true;
			}
		}
		if(!flag) {
			menuCfg.add("store_overview");
		}
		String json = Util.toJson(param);
		int execute = db().update(SHOP).set(SHOP.STORE_CLERK_PRIVILEGE_LIST,json).where(SHOP.SHOP_ID.eq(shopId)).execute();
		return execute==1;
	}
}
