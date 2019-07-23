package com.vpu.mp.service.foundation.service;

import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DatabaseManager;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;
import com.vpu.mp.support.SpringUtil;

abstract public class CommonBaseService {

	@Autowired
	protected DatabaseManager databaseManager;
	
	@Autowired
	protected OpenPlatform open;

	/**
	 * Shop DB连接事务配置，线程内单例
	 */
	private static ThreadLocal<Configuration> shopDbConfiguration = new ThreadLocal<Configuration>();

	/**
	 * Main DB连接事务配置，线程内单例
	 */
	private static ThreadLocal<Configuration> mainDbConfiguration = new ThreadLocal<Configuration>();


	/**
	 * 当前店铺DB连接
	 * 
	 * @return
	 */
	public DefaultDSLContext shopDb() {
		Configuration config = shopDbConfiguration.get();
		if (config != null) {
			return (DefaultDSLContext) DSL.using(shopDbConfiguration.get());
		}
		return (DefaultDSLContext) databaseManager.currentShopDb();
	}

	/**
	 * 当前DB，需继承实现
	 * @return
	 */
	abstract protected   DefaultDSLContext db();
	
	/**
	 * 当前DB事务处理
	 * 
	 * @param transactional
	 */
	abstract protected void transaction(final ContextTransactionalRunnable transactional);

	

	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public DefaultDSLContext mainDb() {
		return (DefaultDSLContext) databaseManager.mainDb();
	}

	/**
	 * 店铺DB事务处理
	 * 
	 * @param transactional
	 */
	public void shopTransaction(final ContextTransactionalRunnable transactional) {
		shopDb().transaction((configuration) -> {
			shopDbConfiguration.set(configuration);
			transactional.run();
			shopDbConfiguration.remove();
		});
	}

	/**
	 * 主库DB事务处理
	 * 
	 * @param transactional
	 */
	public void mainTransaction(final ContextTransactionalRunnable transactional) {
		mainDb().transaction((configuration) -> {
			mainDbConfiguration.set(configuration);
			transactional.run();
			mainDbConfiguration.remove();
		});
	}

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

	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, Integer currentPage, Class<T> clazz) {
		return getPageResult(select, currentPage, 20, clazz);
	}

	public <T> PageResult<T> getPageResult(SelectLimitStep<?> select, Class<T> clazz) {
		return getPageResult(select, 1, 20, clazz);
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

	public String getDelPrefix(Integer recordId) {
		if (recordId == null) {
			return null;
		}
		return DelFlag.DEL_ITEM_PREFIX + recordId + DelFlag.DEL_ITEM_SPLITER;
	}
	
	protected Logger logger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	protected OpenPlatform open() {
		return open;
	}
	protected SaasApplication saas() {
		return (SaasApplication) SpringUtil.getBean("saasApplication");
	}
}
