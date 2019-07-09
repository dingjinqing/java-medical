package com.vpu.mp.service.shop.decoration;

import static com.vpu.mp.db.shop.Tables.STORE;

import org.jooq.Record3;
import org.jooq.SelectJoinStep;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.StoreParam;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPagePojo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;

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
	
	/**
	 * 自定义页面
	 * @param page
	 * @return
	 */
	public PageResult<XcxCustomerPagePojo> customPage(Integer page) {
		XcxCustomerPagePojo xcx = new XcxCustomerPagePojo();
		xcx.setPageId(page);
		PageResult<XcxCustomerPagePojo> list = saas().getShopApp(shopId).mpDecoration.getPageList(xcx);
		return list;
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
	
	/**
	 * 门店
	 * @param page
	 * @return
	 */
	public PageResult<StoreParam> store(StoreListQueryParam param) {
		SelectJoinStep<Record3<String,Byte,Integer>> select = db()
				.select(STORE.STORE_NAME,STORE.BUSINESS_STATE,STORE.STORE_ID)
				.from(STORE);
		select.orderBy(STORE.STORE_ID.desc());
		PageResult<StoreParam> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), StoreParam.class);
		return pageResult;
	}
}
