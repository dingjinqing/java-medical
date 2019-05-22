package com.vpu.mp.service.foundation;

import java.util.Map;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.TableField;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.TableImpl;

/**
 * 
 * @author 新国
 *
 */
public class BaseComponent {

	protected DataManager dm = DataManager.instance();

	protected Integer shopId = 0;

	public BaseComponent() {
		Util.initComponents(this);
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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

	public PageResult getPageResult(SelectLimitStep<Record> select, Integer currentPage, Integer pageRows) {
		Integer totalRows = db().fetchCount(select);
		PageResult pageResult = new PageResult();
		pageResult.page = Page.getPage(totalRows, currentPage, pageRows);
		Result<Record> result = select
				.limit((pageResult.page.currentPage - 1) * pageResult.page.pageRows, pageResult.page.pageRows).fetch();
		pageResult.dataList = result.intoMaps();
		return pageResult;
	}

	public PageResult getPageResult(SelectLimitStep<Record> select, Integer currentPage) {
		return getPageResult(select, currentPage, 20);
	}

	public PageResult getPageResult(SelectLimitStep<Record> select) {
		return getPageResult(select, 1, 20);
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
