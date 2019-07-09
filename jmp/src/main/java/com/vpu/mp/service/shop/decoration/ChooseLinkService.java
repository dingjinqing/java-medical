package com.vpu.mp.service.shop.decoration;

import com.vpu.mp.service.foundation.BaseService;

/**
 * 
 * @author 常乐
 * 2019年7月9日
 */
public class ChooseLinkService extends BaseService {

//	常用链接
	public Boolean commonLink() {
		return false;
	}
	
//	商品链接
	public Boolean goodsLink() {
		return false;
	}
	
//	自定义页面
	public Boolean customLink() {
		return false;
	}
	
//	营销活动
	public Boolean activityList() {
		return false;
	}
	
//	商品分类
	public Boolean goodsCategory() {
		return false;
	}
	
//	网页跳转
	public Boolean webLink() {
		return false;
	}
	
//	小程序跳转
	public Boolean xcxSkip() {
		return false;
	}
	
//	表单页面
	public Boolean fromPage() {
		return false;
	}
	
//	门店
	public Boolean store() {
		return false;
	}
}
