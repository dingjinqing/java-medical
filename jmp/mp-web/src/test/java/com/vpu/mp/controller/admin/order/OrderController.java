package com.vpu.mp.controller.admin.order;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.vpu.mp.WebApplication;
import com.vpu.mp.controller.admin.AdminBaseControllerTest;
import com.vpu.mp.service.shop.order.info.MpOrderInfoService;

/**
 * @author 我是
 * @Date: 2019年8月21日
 * @Description: 訂單
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource("classpath:test-user.properties")
public class OrderController extends AdminBaseControllerTest {
	@Autowired
	MpOrderInfoService service;
	@Before
	public void setUpEnvironment() {
		logger().info("准备测试环境，登录-token,店铺选择");
		this.login();
	}
}
