package com.vpu.mp.service.shop.privilege;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

public class MenuService extends BaseService {
	

    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> getMenu()
    {
    	List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
    	String json = Util.loadResource("admin.menu.json");
    	result = Util.parseJSON(json, result.getClass());
		return null;
    }
//    /**
//     * 得到权限配置
//     */
//    public function getAuthority()
//    {
//        return config("authority.admin.admin_authority");
//    }
//
//    /**
//     * 得到角色信息
//     *
//     * @param int $roleId
//     * @return mixed
//     */
//    public function getRole($roleId)
//    {
//        return shop()->role->getRow($roleId);
//    } 

}
