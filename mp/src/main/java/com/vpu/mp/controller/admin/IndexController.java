package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vpu.mp.db.main.tables.B2cArticle;
import com.vpu.mp.db.shop.tables.B2cShipping;
import com.vpu.mp.service.foundation.DataManager;


@Controller
public class IndexController {
	
	@Autowired
	protected DataManager dm;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("");
		System.out.println("path" + path);
		model.addAttribute("name", "哈哈哈");
		return "admin/index";
	}

	protected void test() {
		Result<?> rs= dm.db().select().from(B2cArticle.B2C_ARTICLE).fetch();
		System.out.println(rs);
	}
	
	protected void test2() {
		Result<?> rs= dm.db(471752).select().from(B2cShipping.B2C_SHIPPING).fetch();
		System.out.println(rs);
	}
	protected void test3() {
		String sql = "update b2c_user set shop_id=1; update b2c_user set shop_id=2;";
		dm.db().query(sql).execute();
	}
	
	protected void test4() {
		dm.installShopDb(dm.getInstallShopDbConfig(10232));
	}
}
