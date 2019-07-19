package com.vpu.mp.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vpu.mp.MpRunListener;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author lixinguo
 *
 */
public class BaseControllerTest {
	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;
	protected MockHttpServletRequest mockRequest;
	protected boolean isMockLogin = true;

	protected String language = "zh_CN";

	public static class CustomerLoader extends SpringBootContextLoader {
		@Override
		protected SpringApplication getSpringApplication() {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			SpringApplication app = super.getSpringApplication();
			app.addListeners(new MpRunListener());
			return app;
		}
	}

	@Before
	public void setUp() {
		System.out.println(this.getClass().getName() + " setUp");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockRequest = new MockHttpServletRequest();
		initSession();
	}

	/**
	 * 初始化会话信息
	 */
	public void initSession() {

	}

	@After
	public void teardown() {
		destroy();
		System.out.println(this.getClass().getName() + " teardown");
	}

	public void destroy() {
	}

	protected void builderCommon(MockHttpServletRequestBuilder builder) {
		builder.accept(MediaType.APPLICATION_JSON_UTF8);
		builder.header("V-Lang", language);
	}

	protected MockHttpServletRequestBuilder post(String uri, Object object) {
		return request(uri, object);
	}

	protected MockHttpServletRequestBuilder get(String uri) {
		return request(uri, null);
	}

	protected MockHttpServletRequestBuilder request(String uri, Object object) {
		MockHttpServletRequestBuilder builder = null;
		if (object != null) {
			String json = Util.toJson(object);
			builder = MockMvcRequestBuilders.post(uri).content(json.getBytes());
		} else {
			builder = MockMvcRequestBuilders.get(uri);
		}
		builder.contentType(MediaType.APPLICATION_JSON_UTF8);
		builderCommon(builder);
		return builder;
	}

	protected MockHttpServletRequestBuilder upload(String uri, Object object, MockMultipartFile... files) {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(uri);
		for (MockMultipartFile file : files) {
			builder.file(file);
		}
		builder.contentType(MediaType.MULTIPART_FORM_DATA);
		builderCommon(builder);
		return builder;
	}

	protected ResultActions expectSuccess(MockHttpServletRequestBuilder builder) throws Exception {
		return expectCode(JsonResultCode.CODE_SUCCESS.getCode(), builder);
	}

	protected ResultActions expectFail(MockHttpServletRequestBuilder builder) throws Exception {
		return expectCode(JsonResultCode.CODE_FAIL.getCode(), builder);
	}

	protected ResultActions expectFail(int code, MockHttpServletRequestBuilder builder) throws Exception {
		return expectCode(code, builder);
	}

	protected ResultActions expectJson(String jsonPath, Object code, MockHttpServletRequestBuilder builder)
			throws Exception {
		return mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(code)).andDo(print());
	}

	protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
		return mockMvc.perform(builder);
	}

	protected ResultActions expectCode(int code, MockHttpServletRequestBuilder builder) throws Exception {
		return mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(code)).andDo(print());
	}

}
