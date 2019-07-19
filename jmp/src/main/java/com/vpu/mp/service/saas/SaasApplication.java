package com.vpu.mp.service.saas;

import java.util.HashMap;

import com.vpu.mp.service.foundation.ServiceContainer;
import com.vpu.mp.service.saas.article.ArticleCategoryService;
import com.vpu.mp.service.saas.article.ArticleService;
import com.vpu.mp.service.saas.categroy.SysCateService;
import com.vpu.mp.service.saas.db.RepairDatabaseService;
import com.vpu.mp.service.saas.official.OfficialService;
import com.vpu.mp.service.saas.overview.ShopOverviewService;
import com.vpu.mp.service.saas.privilege.ChildAccountService;
import com.vpu.mp.service.saas.privilege.MenuService;
import com.vpu.mp.service.saas.privilege.RoleService;
import com.vpu.mp.service.saas.privilege.SystemUserService;
import com.vpu.mp.service.saas.region.CityService;
import com.vpu.mp.service.saas.region.RegionService;
import com.vpu.mp.service.saas.shop.ShopService;
import com.vpu.mp.service.saas.wechat.WechatService;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.support.SpringUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;


/**
 * 
 * @author 新国
 *
 */
@Service
public class SaasApplication extends ServiceContainer {

	public SystemUserService sysUser;
	public ChildAccountService childAccount;
	public RoleService role;
	public MenuService menu;
	public ShopService shop;
	public RegionService region;
	public ArticleService article;
	public ArticleCategoryService articleCategory;
	public OfficialService official;
	public SysCateService sysCate;
	public WechatService wechat;
	public RepairDatabaseService repairDb;
	public ShopOverviewService overviewService;
	
	protected CityService city;

	/**
	 * 线程共享变量，每个线程只有一个SaasApplication
	 */
	private static ThreadLocal<SaasApplication> saasThreadLocal = new  ThreadLocal<SaasApplication>() {
		@Override
		protected SaasApplication initialValue() {
			SaasApplication app =  new  SaasApplication();
			app.initServices();
			return app;
		}
	};

	public static SaasApplication instance() {
		return saasThreadLocal.get();
	}

	protected SaasApplication() {
	}

	protected HashMap<Integer, ShopApplication> shopList = new HashMap<Integer, ShopApplication>();

	public ShopApplication  getShopApp(Integer shopId) {
		
		if (!shopList.containsKey(shopId)) {
			ShopApplication app  = new ShopApplication();
			app.setShopId(shopId);
			app.initServices();
			shopList.put(shopId, app);
		}
		return shopList.get(shopId);
	}

}
