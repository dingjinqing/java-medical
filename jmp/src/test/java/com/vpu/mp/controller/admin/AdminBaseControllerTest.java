package com.vpu.mp.controller.admin;

import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.vpu.mp.controller.BaseControllerTest;
import com.vpu.mp.service.auth.AdminAuth;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author lixinguo
 *
 */
public class AdminBaseControllerTest extends BaseControllerTest {

	@Autowired
	protected AdminAuth adminAuth;

	protected boolean loginAccount = true;

	/**
	 * 访问模块
	 */
	@Getter @Setter protected String moduleName;
		
	
	@Autowired
	protected JedisManager jedis;

	
	@Override
	public void initSession() {
		MockitoAnnotations.initMocks(this);
		if (loginAccount) {
			mockLoginReturnToken();
		}
	}
		
	protected String mockLoginReturnToken() {
		String token = Util.getProperty("test.admin.session.token");
		if(StringUtils.isBlank(token)) {
			fail("test.admin.session.token can not be blank!");
		}
		if (jedis.get(token) == null) {
			AdminTokenAuthInfo info = new AdminTokenAuthInfo();
			Integer sysId = Util.getInteger(Util.getProperty("test.admin.session.sys_id"));
			Integer subAccountId = Util.getInteger(Util.getProperty("test.admin.session.sub_account__id"));
			Integer shopId = Util.getInteger(Util.getProperty("test.admin.session.shop_id"));
			info.setSysId(sysId);
			info.setSubAccountId(subAccountId);
			info.setSubLogin(subAccountId > 0);
			info.setLoginShopId(shopId);
			info.setToken(token);
			adminAuth.saveTokenInfo(info);
		}
		return token;
	}

	@Override
	public void destroy() {
		this.setModuleName(null);
	}

	@Override
	protected void builderCommon(MockHttpServletRequestBuilder builder) {
		super.builderCommon(builder);
		if (loginAccount) {
			builder.header("V-Token", mockLoginReturnToken());
		}
		if (this.getModuleName() != null) {
			builder.header("V-ModuleName", this.getModuleName());
		}
	}

}
