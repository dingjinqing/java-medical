/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main;


import com.vpu.jmd.db.main.tables.ActivityStatistics;
import com.vpu.jmd.db.main.tables.AliMiniAgent;
import com.vpu.jmd.db.main.tables.AliMiniAppVersion;
import com.vpu.jmd.db.main.tables.AliMiniAuthShop;
import com.vpu.jmd.db.main.tables.AliMyOrder;
import com.vpu.jmd.db.main.tables.AliUserToken;
import com.vpu.jmd.db.main.tables.App;
import com.vpu.jmd.db.main.tables.AppAuth;
import com.vpu.jmd.db.main.tables.Article;
import com.vpu.jmd.db.main.tables.ArticleCategory;
import com.vpu.jmd.db.main.tables.ArticleRecord;
import com.vpu.jmd.db.main.tables.BackProcess;
import com.vpu.jmd.db.main.tables.Cache;
import com.vpu.jmd.db.main.tables.Category;
import com.vpu.jmd.db.main.tables.ChargeRenew;
import com.vpu.jmd.db.main.tables.CronDefine;
import com.vpu.jmd.db.main.tables.CronRecord;
import com.vpu.jmd.db.main.tables.DbOptionRecord;
import com.vpu.jmd.db.main.tables.DecorationTemplate;
import com.vpu.jmd.db.main.tables.DictCity;
import com.vpu.jmd.db.main.tables.DictCountry;
import com.vpu.jmd.db.main.tables.DictDistrict;
import com.vpu.jmd.db.main.tables.DictProvince;
import com.vpu.jmd.db.main.tables.FailedJobs;
import com.vpu.jmd.db.main.tables.Goods;
import com.vpu.jmd.db.main.tables.GoodsBrand;
import com.vpu.jmd.db.main.tables.GoodsImg;
import com.vpu.jmd.db.main.tables.GoodsSpecProduct;
import com.vpu.jmd.db.main.tables.GraspGoods;
import com.vpu.jmd.db.main.tables.Jobs;
import com.vpu.jmd.db.main.tables.LogManage;
import com.vpu.jmd.db.main.tables.MpAuthShop;
import com.vpu.jmd.db.main.tables.MpDailyRetain;
import com.vpu.jmd.db.main.tables.MpDailyVisit;
import com.vpu.jmd.db.main.tables.MpDeployHistory;
import com.vpu.jmd.db.main.tables.MpDistributionVisit;
import com.vpu.jmd.db.main.tables.MpJumpVersion;
import com.vpu.jmd.db.main.tables.MpMonthlyRetain;
import com.vpu.jmd.db.main.tables.MpMonthlyVisit;
import com.vpu.jmd.db.main.tables.MpOfficialAccount;
import com.vpu.jmd.db.main.tables.MpOfficialAccountUser;
import com.vpu.jmd.db.main.tables.MpOperateLog;
import com.vpu.jmd.db.main.tables.MpSummaryTrend;
import com.vpu.jmd.db.main.tables.MpSummaryTrendShop;
import com.vpu.jmd.db.main.tables.MpUserPortrait;
import com.vpu.jmd.db.main.tables.MpVersion;
import com.vpu.jmd.db.main.tables.MpVisitPage;
import com.vpu.jmd.db.main.tables.MpWeeklyRetain;
import com.vpu.jmd.db.main.tables.MpWeeklyVisit;
import com.vpu.jmd.db.main.tables.OrderGoods;
import com.vpu.jmd.db.main.tables.OrderInfo;
import com.vpu.jmd.db.main.tables.OrderInfoNew;
import com.vpu.jmd.db.main.tables.QfImg;
import com.vpu.jmd.db.main.tables.Shop;
import com.vpu.jmd.db.main.tables.ShopAccount;
import com.vpu.jmd.db.main.tables.ShopActivity;
import com.vpu.jmd.db.main.tables.ShopChildAccount;
import com.vpu.jmd.db.main.tables.ShopChildRole;
import com.vpu.jmd.db.main.tables.ShopFreeExperience;
import com.vpu.jmd.db.main.tables.ShopGrade;
import com.vpu.jmd.db.main.tables.ShopGradeLog;
import com.vpu.jmd.db.main.tables.ShopOperation;
import com.vpu.jmd.db.main.tables.ShopQuestionFeedback;
import com.vpu.jmd.db.main.tables.ShopRenew;
import com.vpu.jmd.db.main.tables.ShopRole;
import com.vpu.jmd.db.main.tables.ShopTag;
import com.vpu.jmd.db.main.tables.ShopUploadedImage;
import com.vpu.jmd.db.main.tables.ShopUploadedImageCategory;
import com.vpu.jmd.db.main.tables.ShopVersion;
import com.vpu.jmd.db.main.tables.Sms;
import com.vpu.jmd.db.main.tables.Sort;
import com.vpu.jmd.db.main.tables.Spec;
import com.vpu.jmd.db.main.tables.SpecVals;
import com.vpu.jmd.db.main.tables.StatisticsShop;
import com.vpu.jmd.db.main.tables.SystemCfg;
import com.vpu.jmd.db.main.tables.SystemChildAccount;
import com.vpu.jmd.db.main.tables.SystemRole;
import com.vpu.jmd.db.main.tables.SystemUser;
import com.vpu.jmd.db.main.tables.Tag;
import com.vpu.jmd.db.main.tables.TaskJobContent;
import com.vpu.jmd.db.main.tables.TaskJobMain;
import com.vpu.jmd.db.main.tables.ThirdPartyServices;
import com.vpu.jmd.db.main.tables.UploadUyunRecord;
import com.vpu.jmd.db.main.tables.UploadedImage;
import com.vpu.jmd.db.main.tables.UploadedImageCategory;
import com.vpu.jmd.db.main.tables.User;
import com.vpu.jmd.db.main.tables.UserDetail;
import com.vpu.jmd.db.main.tables.UserLoginRecord;
import com.vpu.jmd.db.main.tables.UserSummaryTrend;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MiniMain extends SchemaImpl {

    private static final long serialVersionUID = -415813261;

    /**
     * The reference instance of <code>mini_main</code>
     */
    public static final MiniMain MINI_MAIN = new MiniMain();

    public final OrderInfoNew ORDER_INFO_NEW = OrderInfoNew.ORDER_INFO_NEW;

    /**
     * 定时任务定义表
     */
    public final CronDefine CRON_DEFINE = CronDefine.CRON_DEFINE;

    /**
     * 定时任务执行结果记录表
     */
    public final CronRecord CRON_RECORD = CronRecord.CRON_RECORD;

    /**
     * The table <code>mini_main.b2c_activity_statistics</code>.
     */
    public final ActivityStatistics ACTIVITY_STATISTICS = ActivityStatistics.ACTIVITY_STATISTICS;

    /**
     * The table <code>mini_main.b2c_ali_mini_agent</code>.
     */
    public final AliMiniAgent ALI_MINI_AGENT = AliMiniAgent.ALI_MINI_AGENT;

    /**
     * The table <code>mini_main.b2c_ali_mini_app_version</code>.
     */
    public final AliMiniAppVersion ALI_MINI_APP_VERSION = AliMiniAppVersion.ALI_MINI_APP_VERSION;

    /**
     * The table <code>mini_main.b2c_ali_mini_auth_shop</code>.
     */
    public final AliMiniAuthShop ALI_MINI_AUTH_SHOP = AliMiniAuthShop.ALI_MINI_AUTH_SHOP;

    /**
     * The table <code>mini_main.b2c_ali_my_order</code>.
     */
    public final AliMyOrder ALI_MY_ORDER = AliMyOrder.ALI_MY_ORDER;

    /**
     * The table <code>mini_main.b2c_ali_user_token</code>.
     */
    public final AliUserToken ALI_USER_TOKEN = AliUserToken.ALI_USER_TOKEN;

    /**
     * The table <code>mini_main.b2c_app</code>.
     */
    public final App APP = App.APP;

    /**
     * The table <code>mini_main.b2c_app_auth</code>.
     */
    public final AppAuth APP_AUTH = AppAuth.APP_AUTH;

    /**
     * The table <code>mini_main.b2c_article</code>.
     */
    public final Article ARTICLE = Article.ARTICLE;

    /**
     * The table <code>mini_main.b2c_article_category</code>.
     */
    public final ArticleCategory ARTICLE_CATEGORY = ArticleCategory.ARTICLE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_article_record</code>.
     */
    public final ArticleRecord ARTICLE_RECORD = ArticleRecord.ARTICLE_RECORD;

    /**
     * The table <code>mini_main.b2c_back_process</code>.
     */
    public final BackProcess BACK_PROCESS = BackProcess.BACK_PROCESS;

    /**
     * The table <code>mini_main.b2c_cache</code>.
     */
    public final Cache CACHE = Cache.CACHE;

    /**
     * The table <code>mini_main.b2c_category</code>.
     */
    public final Category CATEGORY = Category.CATEGORY;

    /**
     * The table <code>mini_main.b2c_charge_renew</code>.
     */
    public final ChargeRenew CHARGE_RENEW = ChargeRenew.CHARGE_RENEW;

    /**
     * The table <code>mini_main.b2c_db_option_record</code>.
     */
    public final DbOptionRecord DB_OPTION_RECORD = DbOptionRecord.DB_OPTION_RECORD;

    /**
     * The table <code>mini_main.b2c_decoration_template</code>.
     */
    public final DecorationTemplate DECORATION_TEMPLATE = DecorationTemplate.DECORATION_TEMPLATE;

    /**
     * The table <code>mini_main.b2c_dict_city</code>.
     */
    public final DictCity DICT_CITY = DictCity.DICT_CITY;

    /**
     * The table <code>mini_main.b2c_dict_country</code>.
     */
    public final DictCountry DICT_COUNTRY = DictCountry.DICT_COUNTRY;

    /**
     * The table <code>mini_main.b2c_dict_district</code>.
     */
    public final DictDistrict DICT_DISTRICT = DictDistrict.DICT_DISTRICT;

    /**
     * 省份字典
     */
    public final DictProvince DICT_PROVINCE = DictProvince.DICT_PROVINCE;

    /**
     * The table <code>mini_main.b2c_failed_jobs</code>.
     */
    public final FailedJobs FAILED_JOBS = FailedJobs.FAILED_JOBS;

    /**
     * The table <code>mini_main.b2c_goods</code>.
     */
    public final Goods GOODS = Goods.GOODS;

    /**
     * The table <code>mini_main.b2c_goods_brand</code>.
     */
    public final GoodsBrand GOODS_BRAND = GoodsBrand.GOODS_BRAND;

    /**
     * The table <code>mini_main.b2c_goods_img</code>.
     */
    public final GoodsImg GOODS_IMG = GoodsImg.GOODS_IMG;

    /**
     * The table <code>mini_main.b2c_goods_spec_product</code>.
     */
    public final GoodsSpecProduct GOODS_SPEC_PRODUCT = GoodsSpecProduct.GOODS_SPEC_PRODUCT;

    /**
     * The table <code>mini_main.b2c_grasp_goods</code>.
     */
    public final GraspGoods GRASP_GOODS = GraspGoods.GRASP_GOODS;

    /**
     * The table <code>mini_main.b2c_jobs</code>.
     */
    public final Jobs JOBS = Jobs.JOBS;

    /**
     * The table <code>mini_main.b2c_log_manage</code>.
     */
    public final LogManage LOG_MANAGE = LogManage.LOG_MANAGE;

    /**
     * The table <code>mini_main.b2c_mp_auth_shop</code>.
     */
    public final MpAuthShop MP_AUTH_SHOP = MpAuthShop.MP_AUTH_SHOP;

    /**
     * The table <code>mini_main.b2c_mp_daily_retain</code>.
     */
    public final MpDailyRetain MP_DAILY_RETAIN = MpDailyRetain.MP_DAILY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_daily_visit</code>.
     */
    public final MpDailyVisit MP_DAILY_VISIT = MpDailyVisit.MP_DAILY_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_deploy_history</code>.
     */
    public final MpDeployHistory MP_DEPLOY_HISTORY = MpDeployHistory.MP_DEPLOY_HISTORY;

    /**
     * The table <code>mini_main.b2c_mp_distribution_visit</code>.
     */
    public final MpDistributionVisit MP_DISTRIBUTION_VISIT = MpDistributionVisit.MP_DISTRIBUTION_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_jump_version</code>.
     */
    public final MpJumpVersion MP_JUMP_VERSION = MpJumpVersion.MP_JUMP_VERSION;

    /**
     * The table <code>mini_main.b2c_mp_monthly_retain</code>.
     */
    public final MpMonthlyRetain MP_MONTHLY_RETAIN = MpMonthlyRetain.MP_MONTHLY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_monthly_visit</code>.
     */
    public final MpMonthlyVisit MP_MONTHLY_VISIT = MpMonthlyVisit.MP_MONTHLY_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_official_account</code>.
     */
    public final MpOfficialAccount MP_OFFICIAL_ACCOUNT = MpOfficialAccount.MP_OFFICIAL_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_mp_official_account_user</code>.
     */
    public final MpOfficialAccountUser MP_OFFICIAL_ACCOUNT_USER = MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;

    /**
     * The table <code>mini_main.b2c_mp_operate_log</code>.
     */
    public final MpOperateLog MP_OPERATE_LOG = MpOperateLog.MP_OPERATE_LOG;

    /**
     * The table <code>mini_main.b2c_mp_summary_trend</code>.
     */
    public final MpSummaryTrend MP_SUMMARY_TREND = MpSummaryTrend.MP_SUMMARY_TREND;

    /**
     * The table <code>mini_main.b2c_mp_summary_trend_shop</code>.
     */
    public final MpSummaryTrendShop MP_SUMMARY_TREND_SHOP = MpSummaryTrendShop.MP_SUMMARY_TREND_SHOP;

    /**
     * The table <code>mini_main.b2c_mp_user_portrait</code>.
     */
    public final MpUserPortrait MP_USER_PORTRAIT = MpUserPortrait.MP_USER_PORTRAIT;

    /**
     * The table <code>mini_main.b2c_mp_version</code>.
     */
    public final MpVersion MP_VERSION = MpVersion.MP_VERSION;

    /**
     * The table <code>mini_main.b2c_mp_visit_page</code>.
     */
    public final MpVisitPage MP_VISIT_PAGE = MpVisitPage.MP_VISIT_PAGE;

    /**
     * The table <code>mini_main.b2c_mp_weekly_retain</code>.
     */
    public final MpWeeklyRetain MP_WEEKLY_RETAIN = MpWeeklyRetain.MP_WEEKLY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_weekly_visit</code>.
     */
    public final MpWeeklyVisit MP_WEEKLY_VISIT = MpWeeklyVisit.MP_WEEKLY_VISIT;

    /**
     * The table <code>mini_main.b2c_order_goods</code>.
     */
    public final OrderGoods ORDER_GOODS = OrderGoods.ORDER_GOODS;

    /**
     * The table <code>mini_main.b2c_order_info</code>.
     */
    public final OrderInfo ORDER_INFO = OrderInfo.ORDER_INFO;

    /**
     * The table <code>mini_main.b2c_qf_img</code>.
     */
    public final QfImg QF_IMG = QfImg.QF_IMG;

    /**
     * The table <code>mini_main.b2c_shop</code>.
     */
    public final Shop SHOP = Shop.SHOP;

    /**
     * The table <code>mini_main.b2c_shop_account</code>.
     */
    public final ShopAccount SHOP_ACCOUNT = ShopAccount.SHOP_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_shop_activity</code>.
     */
    public final ShopActivity SHOP_ACTIVITY = ShopActivity.SHOP_ACTIVITY;

    /**
     * The table <code>mini_main.b2c_shop_child_account</code>.
     */
    public final ShopChildAccount SHOP_CHILD_ACCOUNT = ShopChildAccount.SHOP_CHILD_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_shop_child_role</code>.
     */
    public final ShopChildRole SHOP_CHILD_ROLE = ShopChildRole.SHOP_CHILD_ROLE;

    /**
     * The table <code>mini_main.b2c_shop_free_experience</code>.
     */
    public final ShopFreeExperience SHOP_FREE_EXPERIENCE = ShopFreeExperience.SHOP_FREE_EXPERIENCE;

    /**
     * The table <code>mini_main.b2c_shop_grade</code>.
     */
    public final ShopGrade SHOP_GRADE = ShopGrade.SHOP_GRADE;

    /**
     * The table <code>mini_main.b2c_shop_grade_log</code>.
     */
    public final ShopGradeLog SHOP_GRADE_LOG = ShopGradeLog.SHOP_GRADE_LOG;

    /**
     * The table <code>mini_main.b2c_shop_operation</code>.
     */
    public final ShopOperation SHOP_OPERATION = ShopOperation.SHOP_OPERATION;

    /**
     * The table <code>mini_main.b2c_shop_question_feedback</code>.
     */
    public final ShopQuestionFeedback SHOP_QUESTION_FEEDBACK = ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK;

    /**
     * The table <code>mini_main.b2c_shop_renew</code>.
     */
    public final ShopRenew SHOP_RENEW = ShopRenew.SHOP_RENEW;

    /**
     * The table <code>mini_main.b2c_shop_role</code>.
     */
    public final ShopRole SHOP_ROLE = ShopRole.SHOP_ROLE;

    /**
     * The table <code>mini_main.b2c_shop_uploaded_image</code>.
     */
    public final ShopUploadedImage SHOP_UPLOADED_IMAGE = ShopUploadedImage.SHOP_UPLOADED_IMAGE;

    /**
     * The table <code>mini_main.b2c_shop_uploaded_image_category</code>.
     */
    public final ShopUploadedImageCategory SHOP_UPLOADED_IMAGE_CATEGORY = ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_shop_version</code>.
     */
    public final ShopVersion SHOP_VERSION = ShopVersion.SHOP_VERSION;

    /**
     * The table <code>mini_main.b2c_sms</code>.
     */
    public final Sms SMS = Sms.SMS;

    /**
     * The table <code>mini_main.b2c_sort</code>.
     */
    public final Sort SORT = Sort.SORT;

    /**
     * The table <code>mini_main.b2c_spec</code>.
     */
    public final Spec SPEC = Spec.SPEC;

    /**
     * The table <code>mini_main.b2c_spec_vals</code>.
     */
    public final SpecVals SPEC_VALS = SpecVals.SPEC_VALS;

    /**
     * The table <code>mini_main.b2c_statistics_shop</code>.
     */
    public final StatisticsShop STATISTICS_SHOP = StatisticsShop.STATISTICS_SHOP;

    /**
     * The table <code>mini_main.b2c_system_cfg</code>.
     */
    public final SystemCfg SYSTEM_CFG = SystemCfg.SYSTEM_CFG;

    /**
     * The table <code>mini_main.b2c_system_child_account</code>.
     */
    public final SystemChildAccount SYSTEM_CHILD_ACCOUNT = SystemChildAccount.SYSTEM_CHILD_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_system_role</code>.
     */
    public final SystemRole SYSTEM_ROLE = SystemRole.SYSTEM_ROLE;

    /**
     * The table <code>mini_main.b2c_system_user</code>.
     */
    public final SystemUser SYSTEM_USER = SystemUser.SYSTEM_USER;

    /**
     * The table <code>mini_main.b2c_third_party_services</code>.
     */
    public final ThirdPartyServices THIRD_PARTY_SERVICES = ThirdPartyServices.THIRD_PARTY_SERVICES;

    /**
     * The table <code>mini_main.b2c_uploaded_image</code>.
     */
    public final UploadedImage UPLOADED_IMAGE = UploadedImage.UPLOADED_IMAGE;

    /**
     * The table <code>mini_main.b2c_uploaded_image_category</code>.
     */
    public final UploadedImageCategory UPLOADED_IMAGE_CATEGORY = UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_upload_uyun_record</code>.
     */
    public final UploadUyunRecord UPLOAD_UYUN_RECORD = UploadUyunRecord.UPLOAD_UYUN_RECORD;

    /**
     * The table <code>mini_main.b2c_user</code>.
     */
    public final User USER = User.USER;

    /**
     * The table <code>mini_main.b2c_user_detail</code>.
     */
    public final UserDetail USER_DETAIL = UserDetail.USER_DETAIL;

    /**
     * The table <code>mini_main.b2c_user_login_record</code>.
     */
    public final UserLoginRecord USER_LOGIN_RECORD = UserLoginRecord.USER_LOGIN_RECORD;

    /**
     * The table <code>mini_main.b2c_user_summary_trend</code>.
     */
    public final UserSummaryTrend USER_SUMMARY_TREND = UserSummaryTrend.USER_SUMMARY_TREND;

    public final TaskJobMain TASK_JOB_MAIN = TaskJobMain.TASK_JOB_MAIN;
    public final TaskJobContent TASK_JOB_CONTENT = TaskJobContent.TASK_JOB_CONTENT;
    /**
     * The table <code>mini_main.b2c_tag</code>.
     */
    public final Tag TAG = Tag.TAG;
    /**
     * The table <code>mini_main.b2c_shop_tag</code>.
     */
    public final ShopTag SHOP_TAG = ShopTag.SHOP_TAG;
    /**
     * No further instances allowed
     */
    private MiniMain() {
        super("mini_main", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            ActivityStatistics.ACTIVITY_STATISTICS,
            AliMiniAgent.ALI_MINI_AGENT,
            AliMiniAppVersion.ALI_MINI_APP_VERSION,
            AliMiniAuthShop.ALI_MINI_AUTH_SHOP,
            AliMyOrder.ALI_MY_ORDER,
            AliUserToken.ALI_USER_TOKEN,
            App.APP,
            AppAuth.APP_AUTH,
            Article.ARTICLE,
            ArticleCategory.ARTICLE_CATEGORY,
            ArticleRecord.ARTICLE_RECORD,
            BackProcess.BACK_PROCESS,
            Cache.CACHE,
            Category.CATEGORY,
            ChargeRenew.CHARGE_RENEW,
            DbOptionRecord.DB_OPTION_RECORD,
            DecorationTemplate.DECORATION_TEMPLATE,
            DictCity.DICT_CITY,
            DictCountry.DICT_COUNTRY,
            DictDistrict.DICT_DISTRICT,
            DictProvince.DICT_PROVINCE,
            FailedJobs.FAILED_JOBS,
            Goods.GOODS,
            GoodsBrand.GOODS_BRAND,
            GoodsImg.GOODS_IMG,
            GoodsSpecProduct.GOODS_SPEC_PRODUCT,
            GraspGoods.GRASP_GOODS,
            Jobs.JOBS,
            LogManage.LOG_MANAGE,
            MpAuthShop.MP_AUTH_SHOP,
            MpDailyRetain.MP_DAILY_RETAIN,
            MpDailyVisit.MP_DAILY_VISIT,
            MpDeployHistory.MP_DEPLOY_HISTORY,
            MpDistributionVisit.MP_DISTRIBUTION_VISIT,
            MpJumpVersion.MP_JUMP_VERSION,
            MpMonthlyRetain.MP_MONTHLY_RETAIN,
            MpMonthlyVisit.MP_MONTHLY_VISIT,
            MpOfficialAccount.MP_OFFICIAL_ACCOUNT,
            MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER,
            MpOperateLog.MP_OPERATE_LOG,
            MpSummaryTrend.MP_SUMMARY_TREND,
            MpSummaryTrendShop.MP_SUMMARY_TREND_SHOP,
            MpUserPortrait.MP_USER_PORTRAIT,
            MpVersion.MP_VERSION,
            MpVisitPage.MP_VISIT_PAGE,
            MpWeeklyRetain.MP_WEEKLY_RETAIN,
            MpWeeklyVisit.MP_WEEKLY_VISIT,
            OrderGoods.ORDER_GOODS,
            OrderInfo.ORDER_INFO,
            QfImg.QF_IMG,
            Shop.SHOP,
            ShopAccount.SHOP_ACCOUNT,
            ShopActivity.SHOP_ACTIVITY,
            ShopChildAccount.SHOP_CHILD_ACCOUNT,
            ShopChildRole.SHOP_CHILD_ROLE,
            ShopFreeExperience.SHOP_FREE_EXPERIENCE,
            ShopGrade.SHOP_GRADE,
            ShopGradeLog.SHOP_GRADE_LOG,
            ShopOperation.SHOP_OPERATION,
            ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK,
            ShopRenew.SHOP_RENEW,
            ShopRole.SHOP_ROLE,
            ShopUploadedImage.SHOP_UPLOADED_IMAGE,
            ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY,
            ShopVersion.SHOP_VERSION,
            Sms.SMS,
            Sort.SORT,
            Spec.SPEC,
            SpecVals.SPEC_VALS,
            StatisticsShop.STATISTICS_SHOP,
            SystemCfg.SYSTEM_CFG,
            SystemChildAccount.SYSTEM_CHILD_ACCOUNT,
            SystemRole.SYSTEM_ROLE,
            SystemUser.SYSTEM_USER,
            ThirdPartyServices.THIRD_PARTY_SERVICES,
            UploadedImage.UPLOADED_IMAGE,
            UploadedImageCategory.UPLOADED_IMAGE_CATEGORY,
            UploadUyunRecord.UPLOAD_UYUN_RECORD,
            User.USER,
            UserDetail.USER_DETAIL,
            UserLoginRecord.USER_LOGIN_RECORD,
            UserSummaryTrend.USER_SUMMARY_TREND,
            TaskJobContent.TASK_JOB_CONTENT,
            TaskJobMain.TASK_JOB_MAIN,
            CronDefine.CRON_DEFINE,
            CronRecord.CRON_RECORD,
            OrderInfoNew.ORDER_INFO_NEW,
            Tag.TAG,
            ShopTag.SHOP_TAG);
    }
}
