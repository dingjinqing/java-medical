package com.vpu.mp.service.foundation;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;

import com.vpu.mp.MpRunListener;

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


}
