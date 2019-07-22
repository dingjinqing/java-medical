package com.vpu.mp.service.saas.categroy;

import static com.vpu.mp.db.main.Tables.CATEGORY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;


/**
 * 平台分类
 * @author 常乐
 * 2019年7月15日
 */
@Service
@Scope("prototype")
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
	
	 /**
     * 根据父节点查询所有子节点,平台分类最多三层
     *
     * @param parentId
     * @return
     */
    public List<Short> findChildrenByParentId(Short catId) {
    	List<Short> res = new ArrayList<Short>();
    	Short level = db().select(CATEGORY.LEVEL).from(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchOne().into(Short.class);
    	res.add(catId);
    	if(level == 2) {
    		/** 第三级，子分类 */
    	}else if(level == 1) {
    		/** 第二级分类 */
    		List<Short> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId)).fetch(CATEGORY.CAT_ID);
    		res.addAll(children);
    	}else if(level == 0) {
    		/** 第一级分类 */
    		List<Short> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId)).fetch(CATEGORY.CAT_ID);
    		res.addAll(children);
    		 for (Short id : children) {
    			 List<Short> grandchildren = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(id)).fetch(CATEGORY.CAT_ID);
    			 res.addAll(grandchildren);
	        }
    	}
        return res;
    }
}
