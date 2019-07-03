package com.vpu.mp.service.foundation;

import java.util.Map;

import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.impl.DefaultDSLContext;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author 新国
 *
 */
public class BaseService  extends ServiceContainer{

	protected DataManager dm = DataManager.instance();

	/**
	 * saas应用
	 * 
	 * @return
	 */
	public SaasApplication saas() {
		return SaasApplication.instance();
	}
	
	/**
	 * 开放平台实例
	 * @return
	 */
	public OpenPlatform open() {
		return OpenPlatform.instance();
	}

	public DefaultDSLContext db() {
		return this.shopId == 0 ? dm.db() : dm.db(shopId);
	}

	public DefaultDSLContext mainDb() {
		return dm.db();
	}

	public DefaultDSLContext shopDb(Integer shopId) {
		return dm.db(shopId);
	}
	
	public<T> PageResult<T> getPageResult(SelectLimitStep<?> select, Integer currentPage, Integer pageRows,Class<T> clazz) {
		Integer totalRows = db().fetchCount(select);
		PageResult<T> pageResult = new PageResult<>();
		pageResult.page = Page.getPage(totalRows, currentPage, pageRows);
		Result<?> result = select
				.limit((pageResult.page.currentPage - 1) * pageResult.page.pageRows, pageResult.page.pageRows).fetch();
		pageResult.dataList = result.into(clazz);
		return pageResult;
	}
	
	public<T> PageResult<T> getPageResult(SelectLimitStep<?> select, Integer currentPage,Class<T> clazz) {
		return getPageResult(select, currentPage, 20,clazz);
	}
	
	public<T> PageResult<T> getPageResult(SelectLimitStep<?> select,Class<T> clazz) {
		return getPageResult(select, 1, 20,clazz);
	}

	public String likeValue(String val) {
		return "%" + likeReplace(val) + "%";
	}

	protected String likeReplace(String val) {
		val = val.replaceAll("%", "\\%");
		return val.replaceAll("_", "\\_");
	}

	public String prefixLikeValue(String val) {
		return likeReplace(val) + "%";
	}

	public String suffixLikeValue(String val) {
		return "%" + likeReplace(val);
	}

	public boolean valid(Map<String, String> options, String key, String skiptValue) {
		if (options.containsKey(key)) {
			if (options.get(key) != null && options.get(key).equals(skiptValue)) {
				return true;
			}
		}
		return false;
	}

	public boolean valid(Map<String, String> options, String key) {
		if (options.containsKey(key)) {
			if (options.get(key) != null && options.get(key).trim().equals("")) {
				return true;
			}
		}
		return false;
	}
	

}
