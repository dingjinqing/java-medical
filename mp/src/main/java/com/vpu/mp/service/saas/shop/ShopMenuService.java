package com.vpu.mp.service.saas.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

public class ShopMenuService extends BaseService {

	public List<Map<String, Object>> getMenu() {
		return parseJson("admin.menu.json");
	}

	public List<Map<String, Object>> getAuthority() {
		return parseJson("admin.autority.json");
	}

	public List<Map<String, Object>> getRoleMenu(Integer roleId) {
		if (getAuthority() != null) {
			return getRoleAuthority(roleId);
		}
		List<Map<String, Object>> menu = getMenu();
		if (roleId == 0)
			return menu;

		return null;
	}

	public List<Map<String, Object>> getRoleAuthority(Integer roleId) {
		List<Map<String, Object>> authorities = getAuthority();
		List<Map<String, Object>> menu = getMenu();
		if (roleId == 0) {
			return menu;
		}
		String[] privilegeList = saas().shop.role.getPrivilegeList(roleId);
		if (privilegeList == null) {
			return null;
		}
		ArrayList<String> roleLinkList = new ArrayList<String>();
		for (String privilege : privilegeList) {
			for (Map<String, Object> authority : authorities) {
				@SuppressWarnings("unchecked")
				ArrayList<Object> preName = (ArrayList<Object>) authority.get("pre_name");
				String link = (String) authority.get("link");
				if (authority.get("en_name").equals(privilege) || preName != null && preName.indexOf(privilege) != -1) {
					if(link != null && roleLinkList.indexOf(link)!=-1) {
						roleLinkList.add(link);
					}
					break;
				}
			}
		}
//        $pr_list = [];
//        foreach ($privilege as $pr){
//            foreach ($authority as $k=>$auth){
//                $authority[$k]['pre_name'] = $authority[$k]['pre_name'] ?? [];
//                if($pr == $authority[$k]['en_name'] || in_array($pr,$authority[$k]['pre_name'])){
//                    if(!in_array($authority[$k]['link'],$pr_list))
//                        array_push($pr_list,$authority[$k]['link']);
//                    break;
//                }
//            }
//        }

		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> parseJson(String jsonPath) {
		String json = Util.loadResource(jsonPath);
		return Util.parseJSON(json, ArrayList.class);
	}

}
