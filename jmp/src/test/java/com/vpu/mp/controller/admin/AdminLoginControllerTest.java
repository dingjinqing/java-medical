package com.vpu.mp.controller.admin;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vpu.mp.MpApplication;
import com.vpu.mp.controller.BaseControllerTest;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;

/**
 * 
 * @author lixinguo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = BaseControllerTest.CustomerLoader.class)
public class AdminLoginControllerTest extends AdminBaseControllerTest {

	@Override
	public void initSession() {
		this.loginAccount = false;
		super.initSession();
	}

	@Test
	public void testLogin() throws Exception {

		// 测试主账号正确登陆
		ShopLoginParam loginParam = new ShopLoginParam();
		loginParam.setUsername("user001");
		loginParam.setPassword("123456");
		this.expectSuccess(post("/api/admin/login", loginParam)).andReturn();

		// 测试错误账号密码登陆
		loginParam.setPassword("334343");
		this.expectFail(JsonResultCode.CODE_ACCOUNT_OR_PASSWORD_INCRRECT.getCode(),
				post("/api/admin/login", loginParam)).andReturn();

	}

	@Test
	public void testLogout() throws Exception {
		String userToken = mockLoginReturnToken();
		this.expectSuccess(get("/api/admin/logout").header("V-Token", userToken)).andReturn();
	}

}
