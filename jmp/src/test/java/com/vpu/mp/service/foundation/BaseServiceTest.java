package com.vpu.mp.service.foundation;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;

import com.vpu.mp.MpRunListener;
import com.vpu.mp.support.SpringUtil;

public class BaseServiceTest {

	public static class CustomerLoader extends SpringBootContextLoader {
		@Override
		protected SpringApplication getSpringApplication() {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			SpringApplication app = super.getSpringApplication();
			app.addListeners(new MpRunListener());
			return app;
		}
	}

	/**
	 * 由于没有调用 initServices 方法，所以service下的其他service属性将不能使用
	 * @param <T>
	 * @param cls
	 * @return
	 */
	public <T> T getService(Class<T> cls) {
		Integer shopId = Util.getInteger(Util.getProperty("test.admin.session.shop_id"));
		T service = SpringUtil.getBean(cls);
		((BaseService) service).setShopId(shopId);
		return service;
	}

}
