package com.vpu.mp.service.saas.privilege;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MenuManager {
	
	public List<Menu> getTopMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("概览", "first_web_manage", "/system/shop/shop_view", "/image/system/first_1.png", "", false,
				getFirstWebManageMenuList()));
		list.add(new Menu("店铺管理", "first_web_decoration", "/system/shop/account/list", "/image/system/first_2.png", "",
				false, getFirstWebDecorationMenuList()));
		list.add(new Menu("数据统计", "data_manage", "/system/analysis/basic/overview", "/image/system/first_3.png", "",
				false, getDataManageMenuList()));
		list.add(new Menu("商品管理", "goods_manage", "/system/goods/analysis", "/image/system/first_3.png", "", false,
				getGoodsManageMenuList()));
		list.add(new Menu("会员管理", "user_manger", "/system/user/analysis", "/image/system/get_user.png", "", false,
				getUserMangerMenuList()));
		list.add(new Menu("订单管理", "first_trade_manage", "/system/order/analysis", "/image/system/first_4.png", "",
				false, getFirstTradeManageMenuList()));
		list.add(new Menu("设置", "set_manage", "/system/decoration/template", "/image/system/first_4.png", "", false,
				getSetManageMenuList()));
		return list;
	}

	protected List<Menu> getFirstWebManageMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("概览", "shop_view", "/system/shop/shop_view", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("升级续费申请", "charge_renew_manager", "/system/chargerenew/list",
				"/image/system/picture_space.png", "/image/system/picture_space_h.png", false, null));
		list.add(new Menu("官网申请试用", "free_experience", "/system/free/experience", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("问题反馈", "question_feedback", "/system/question/feedback", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("店铺登录日志", "shop_login", "/system/user/loginlist", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("日志管理", "operation_manager", "/system/operation/list", "/image/system/picture_space.png",
				"/image/system/picture_space_h.png", false, null));
		list.add(new Menu("文章管理", "essay_admin", "", "/image/system/essay_admin.png", "/image/system/essay_admin_h.png",
				false, null));
		list.add(new Menu("图片空间", "image_manager", "/system/image/list", "/image/system/picture_space.png",
				"/image/system/picture_space_h.png", false, null));
		return list;
	}

	protected List<Menu> getFirstWebDecorationMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("店铺账户列表", "shop_account", "/system/shop/account/list", "/image/system/mobile_deco.png",
				"/image/system/mobile_deco_h.png", false, null));
		list.add(new Menu("店铺列表", "shop_list", "/system/shop/list", "/image/system/mobile_deco.png",
				"/image/system/mobile_deco_h.png", false, null));
		list.add(new Menu("发布列表", "shop_mp_list", "/system/shop/mp/list", "/image/system/mobile_deco.png",
				"/image/system/mobile_deco_h.png", false, null));
		list.add(new Menu("小程序版本", "mp_version_list", "/system/mp/version", "/image/system/mobile_deco.png",
				"/image/system/mobile_deco_h.png", false, null));
		list.add(new Menu("版本列表", "version_manager", "/system/version/list", "/image/system/picture_space.png",
				"/image/system/picture_space_h.png", false, null));
		list.add(new Menu("微信全链路", "version_manager", "/system/seller/account/list", "/image/system/picture_space.png",
				"/image/system/picture_space_h.png", false, null));
		list.add(new Menu("申请发布列表", "wx_link", "/system/shop/mp/jump/list", "/image/system/picture_space.png",
				"/image/system/picture_space_h.png", false, null));
		return list;
	}

	protected List<Menu> getDataManageMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("概览", "analysis", "/system/analysis/basic/overview", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("用户画像", "analysis", "/system/analysis/basic/user_portrait", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("访问趋势", "analysis", "/system/analysis/basic/yesterday", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("访问分析", "analysis_visit", "/system/analysis/shop/visit", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("店铺访问量", "shop_pv", "/system/analysis/shop/pv", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("功能使用", "activity_summary", "/system/statistics/view", "/image/system/shop_view.png",
				"/image/system/shop_view_h.png", false, null));
		list.add(new Menu("收入统计", "activity_summary", "/system/statistics/incomeStatistics",
				"/image/system/shop_view.png", "/image/system/shop_view_h.png", false, null));
		return list;
	}

	protected List<Menu> getGoodsManageMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("商品统计", "goods_summary", "/system/goods/analysis", "/image/system/product_list.png",
				"/image/system/product_list_h.png", false, null));
		list.add(new Menu("商品列表", "goods_list", "/system/goods/list", "/image/system/product_list.png",
				"/image/system/product_list_h.png", false, null));
		list.add(new Menu("分类管理", "category", "/system/category/list", "/image/system/picture_attr.png",
				"/image/system/picture_attr_h.png", false, null));
		return list;
	}

	protected List<Menu> getUserMangerMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("会员统计", "user_summary", "/system/user/analysis", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("会员列表", "user_list", "/system/user/list", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		return list;
	}

	protected List<Menu> getFirstTradeManageMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("订单统计", "order_admin", "/system/order/analysis", "/image/system/order_admin.png",
				"/image/system/order_admin_h.png", false, null));
		list.add(new Menu("订单列表", "category", "/system/order/list", "/image/system/order_admin.png",
				"/image/system/order_admin_h.png", false, null));
		return list;
	}

	protected List<Menu> getSetManageMenuList() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu("装修模板", "decoration_template", "/system/decoration/template", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("子账号管理", "get_user_list", "/system/account/user/list", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("Enter Admin", "enter_admin", "/system/account/enteradmin/check",
				"/image/system/get_user.png", "/image/system/get_user_h.png", false, null));
		list.add(new Menu("数据库管理", "db_manage", "/system/db/manage", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		list.add(new Menu("图片存储", "uyun_switch", "/system/image/uyunswitch", "/image/system/get_user.png",
				"/image/system/get_user_h.png", false, null));
		return list;
	}

	

}
