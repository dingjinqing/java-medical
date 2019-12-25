package com.vpu.mp.service.foundation.service;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;


/**
 *
 * @author lixinguo
 *
 */
public class ShopBaseService extends AbstractCommonBaseService {

	/**
	 * Shop DB连接事务配置，线程内单例
	 */
	private static ThreadLocal<Deque<Configuration>> shopDbConfiguration = ThreadLocal.withInitial(ArrayDeque<Configuration>::new);

	/**
	 * 当前登录用户信息，线程单例
	 */
	private static ThreadLocal<AdminTokenAuthInfo> currentAdminLoginUser  = new ThreadLocal<>();

	/**
	 * 当前线程设置当前登录用户
	 * @param user
	 */
	public static void setCurrentAdminLoginUser(AdminTokenAuthInfo user) {
		currentAdminLoginUser.set(user);
	}

	/**
	 * 当前线程得到当前登录用户
	 * @return
	 */
	public static AdminTokenAuthInfo getCurrentAdminLoginUser() {
		return currentAdminLoginUser.get();
	}

	/**
	 * 当前店铺连接
	 */
	@Override
	protected DefaultDSLContext db() {
		Deque<Configuration> config = shopDbConfiguration.get();
		if (config.peek() != null) {
			return (DefaultDSLContext) DSL.using(config.peek());
		}
		return databaseManager.currentShopDb();
	}

	/**
	 * 事务
	 * @param transactional
	 */
	protected void transaction(ContextTransactionalRunnable transactional) {
		db().transaction((configuration) -> {
			Deque<Configuration> config = shopDbConfiguration.get();
			config.push(configuration);
			try {
				transactional.run();
			} finally {
				config.pop();
			}
		});
	}

    /**
	 * 当前店铺Id
     *
	 * @return
	 */
	public Integer getShopId() {
		return databaseManager.getCurrentShopId();
	}

    /**
	 * 当前店铺对于SysId
	 * @return
	 */
	public Integer getSysId() {
		ShopRecord shop = saas.shop.getShopById(this.getShopId());
		return shop == null ? 0 : shop.getSysId();
	}


    public Field<String> dateFormat(Field<Timestamp> field, String format) {
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR,
            field, DSL.inline(format));
    }

    @Autowired
    private DomainConfig domainConfig;

    public String imageUrl(String path) {
        return domainConfig.imageUrl(path);
    }

}
