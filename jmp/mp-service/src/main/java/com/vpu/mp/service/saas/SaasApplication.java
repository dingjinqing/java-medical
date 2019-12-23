package com.vpu.mp.service.saas;

import com.vpu.mp.service.foundation.database.DatabaseManager;
import com.vpu.mp.service.saas.area.AreaSelectService;
import com.vpu.mp.service.saas.article.ArticleCategoryService;
import com.vpu.mp.service.saas.article.ArticleService;
import com.vpu.mp.service.saas.categroy.SysCateService;
import com.vpu.mp.service.saas.db.DataExportService;
import com.vpu.mp.service.saas.db.RepairDatabaseService;
import com.vpu.mp.service.saas.image.SystemImageService;
import com.vpu.mp.service.saas.official.OfficialService;
import com.vpu.mp.service.saas.overview.ShopOverviewService;
import com.vpu.mp.service.saas.privilege.ChildAccountService;
import com.vpu.mp.service.saas.privilege.MenuService;
import com.vpu.mp.service.saas.privilege.RoleService;
import com.vpu.mp.service.saas.privilege.SystemUserService;
import com.vpu.mp.service.saas.region.CityService;
import com.vpu.mp.service.saas.region.RegionService;
import com.vpu.mp.service.saas.schedule.TaskJobMainService;
import com.vpu.mp.service.saas.schedule.cron.MpCronRegistration;
import com.vpu.mp.service.saas.schedule.rabbit.RabbitDataService;
import com.vpu.mp.service.saas.shop.MpDeployHistoryService;
import com.vpu.mp.service.saas.shop.ShopService;
import com.vpu.mp.service.saas.shop.UserLoginService;
import com.vpu.mp.service.saas.shop.WxMainUserService;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.market.message.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 新国
 *
 */
@Service
public class SaasApplication {
	@Autowired
	public SystemUserService sysUser;
	@Autowired
	public ChildAccountService childAccount;
	@Autowired
	public RoleService role;
	@Autowired
	public MenuService menu;
	@Autowired
	public ShopService shop;
	@Autowired
	public RegionService region;
	@Autowired
	public ArticleService article;
	@Autowired
	public ArticleCategoryService articleCategory;
	@Autowired
	public OfficialService official;
	@Autowired
	public SysCateService sysCate;
	@Autowired
	public RepairDatabaseService repairDb;
	@Autowired
	public ShopOverviewService overviewService;
	@Autowired
	public CityService city;
	@Autowired
	public SystemImageService sysImage;
	@Autowired
	public MpDeployHistoryService deployHistoryService;
	@Autowired
    public TaskJobMainService taskJobMainService;
    @Autowired
    public DataExportService dataExportService;
	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	protected ShopApplication shopApplication;

	@Autowired
    public RabbitDataService rabbitDataService;

	@Autowired
    public AreaSelectService areaSelectService;

    @Autowired
	public MessageTemplateService messageTemplateService;

    @Autowired
	public WxMainUserService wxUserService;

    @Autowired
	public UserLoginService userLoginService;

    @Autowired
    public MpCronRegistration mpCronRegistration;

	public ShopApplication getShopApp(Integer shopId) {
		databaseManager.switchShopDb(shopId);
		return shopApplication;
	}

}
