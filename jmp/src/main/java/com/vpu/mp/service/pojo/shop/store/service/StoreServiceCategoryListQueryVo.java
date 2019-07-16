package com.vpu.mp.service.pojo.shop.store.service;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceCategoryListQueryVo {
	
	private Integer cat_id;
	
	/**
	 *  分类名称
	 */
	private String catName;
	
	private Timestamp createTime;
}
