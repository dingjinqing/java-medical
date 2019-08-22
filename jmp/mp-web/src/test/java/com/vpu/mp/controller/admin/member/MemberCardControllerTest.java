package com.vpu.mp.controller.admin.member;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.vpu.mp.controller.admin.AdminBaseControllerTest;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.shop.member.MemberCardService;

/**
* @author 黄壮壮
* @Date: 2019年8月21日
* @Description: 会员卡业务功能测试
*/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test-user.properties")
public class MemberCardControllerTest extends AdminBaseControllerTest{
	private CardParam param;
	
	@Before
	public void setUpEnvironment() {
		logger().info("准备测试环境，登录-token,店铺选择");
		this.login();
		param = new CardParam();
		param.setId(825);
		param.setGoodsId(new Integer[] {1,2,6});
		//等级会员卡更新v2
		param.setCardName(null);
	}
	
	/** 测试通过会员卡id更新折扣部分商品公共方法 */
	@Test
	public void uppdateDiscountPartGoods() {
		logger().info("测试设置会员卡折扣部分商品id,商家id,平台id");
		JsonResult post = this.post("/api/admin/member/card/test/update", param, token, null, null);
	}
}
