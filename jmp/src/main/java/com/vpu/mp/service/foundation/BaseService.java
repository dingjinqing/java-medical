package com.vpu.mp.service.foundation;

import java.util.HashMap;

import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;

import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author 新国
 *
 */
public class BaseService extends ServiceContainer {

	/**
	 * DB连接事务配置，线程内单例
	 */
	private static ThreadLocal< HashMap<Integer, Configuration> > transactionDbConfigration = new ThreadLocal< HashMap<Integer, Configuration> >() {
		@Override
		protected  HashMap<Integer, Configuration>  initialValue() {
			return new  HashMap<Integer, Configuration> ();
		}
	};
	
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
	 * 
	 * @return
	 */
	public OpenPlatform open() {
		return OpenPlatform.instance();
	}

	/**
	 * 当前shopId的Db连接，shopId=0时为主库
	 * @return
	 */
	public DefaultDSLContext db() {
		DefaultDSLContext transDb = getTransactionDb(shopId);
		return transDb != null ? transDb: ( this.shopId == 0 ? dm.db() : dm.db(shopId));
	}

	/**
	 * 主库连接
	 * @return
	 */
	public DefaultDSLContext mainDb() {
		DefaultDSLContext transDb = getTransactionDb(0);
		return transDb != null ? transDb:  dm.db();
	}

	/**
	 * 店铺Db连接
	 * @param shopId
	 * @return
	 */
	public DefaultDSLContext shopDb(Integer shopId) {
		DefaultDSLContext transDb = getTransactionDb(shopId);
		return transDb != null ? transDb:  dm.db(shopId);
	}
	
	/**
	 * 得到线程 事务配置变量单例，当调用this.transaction(configuration->{})时，保存事务当前配置，以便db()时取到相应配置的连接
	 * @return
	 */
	protected  HashMap<Integer, Configuration> transactionConfiuration() {
		return transactionDbConfigration.get();
	}
	
	/**
	 * 得到店铺ID的事务连接，店铺为0时为主库
	 * @param shopId
	 * @return
	 */
	protected DefaultDSLContext getTransactionDb(Integer shopId) {
		if(transactionConfiuration().containsKey(shopId)) {
			return (DefaultDSLContext) DSL.using(transactionConfiuration().get(shopId));
		}
		return null;
	}
	
	/**
	 * 当前DB事务处理
	 * @param transactional
	 */
	public void transaction(final ContextTransactionalRunnable transactional) {
		db().transaction((configuration)->{
			transactionConfiuration().put(shopId, configuration);
			transactional.run();
			transactionConfiuration().remove(shopId);
		});
	}
	
	/**
	 * 得到分页结果
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
				.limit((pageResult.page.currentPage - 1) * pageResult.page.pageRows, pageResult.page.pageRows)
				.fetch();
		pageResult.dataList = result.into(clazz);
		return pageResult;
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

}
