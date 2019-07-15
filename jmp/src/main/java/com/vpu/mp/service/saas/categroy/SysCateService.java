package com.vpu.mp.service.saas.categroy;

import static com.vpu.mp.db.main.Tables.CATEGORY;

import java.util.List;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;


/**
 * 平台分类
 * @author 常乐
 * 2019年7月15日
 */
public class SysCateService extends BaseService{
	
	/**
	 * 选择平台分分类列表
	 * @return
	 */
	public List<SysCatevo> getSysCate() {
		//获取平台一级分类
		List<SysCatevo>	parentList = db().select(CATEGORY.CAT_ID,CATEGORY.CAT_NAME)
				 .from(CATEGORY)
				 .where(CATEGORY.PARENT_ID.eq((short) 0 ))
				 .fetchInto(SysCatevo.class);
		
		//查询每级分类下的二级分类
		for(SysCatevo list : parentList ){
			List<SysCatevo> childList = db().select(CATEGORY.CAT_ID,CATEGORY.CAT_NAME,CATEGORY.PARENT_ID)
					.from(CATEGORY)
					.where(CATEGORY.PARENT_ID.eq(list.getCatId()))
					.fetchInto(SysCatevo.class);
			list.setChildCate(childList);
		}
		return parentList;
	}
}
