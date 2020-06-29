package com.vpu.jmd.service.base;


import com.vpu.jmd.database.DatabaseManager;
import com.vpu.jmd.database.FieldsUtil;
import com.vpu.jmd.service.base.bo.Page;
import com.vpu.jmd.service.base.bo.PageResult;
import com.vpu.jmd.service.wechat.OpenPlatform;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 *
 * @author lixinguo
 *
 */
public abstract  class AbstractCommonBaseService {

	@Autowired
	protected DatabaseManager databaseManager;

	@Autowired
	protected OpenPlatform open;

	@Autowired
	protected SaasApplication saas;


	/**
	 * 当前DB
	 * @return
	 */
	protected abstract DefaultDSLContext db();

	/**
	 * 得到分页结果
	 *
	 * @param <T>
	 * @param select
	 * @param currentPage
	 * @param pageRows
	 * @param clazz
	 * @return
	 */
	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, Integer currentPage, Integer pageRows,
										   Class<T> clazz) {
		Integer totalRows = db().fetchCount(select);
		PageResult<T> pageResult = new PageResult<>();
		pageResult.page = Page.getPage(totalRows, currentPage, pageRows);
		Result<?> result = select
				.limit((pageResult.page.currentPage - 1) * pageResult.page.pageRows, pageResult.page.pageRows).fetch();
		pageResult.dataList = result.into(clazz);
		return pageResult;
	}

	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, BasePageParam param, Class<T> clazz) {
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), clazz);
	}

	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, Integer currentPage, Class<T> clazz) {
		return getPageResult(select, currentPage, 20, clazz);
	}

	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, Class<T> clazz) {
		return getPageResult(select, 1, 20, clazz);
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


	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}

	protected OpenPlatform open() {
		return open;
	}

	protected SaasApplication saas() {
		return saas;
	}

	/**
	 * 复制from到jooq的记录对象中，不含值为null的字段
	 *
	 * @param from
	 * @param to
	 */
	public void assign(Object from, Record to) {
		FieldsUtil.assignNotNull(from, to);
	}

	/**
	 * 复制from到jooq的记录对象中，不含值为null的字段
	 *
	 * @param from
	 * @param to
	 * @param onlyFields
	 */
	public void assign(Object from, Record to, String[] onlyFields) {
		FieldsUtil.assignNotNull(from, to, Arrays.asList(onlyFields));
	}


}
