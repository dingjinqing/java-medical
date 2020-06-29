/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main;


import *;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in mini_main
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {
    /**
     * 定时任务定义表
     */
    public static final CronDefine CRON_DEFINE = CronDefine.CRON_DEFINE;

    /**
     * 定时任务执行结果记录表
     */
    public static final CronRecord CRON_RECORD = CronRecord.CRON_RECORD;

    /**
     * The table <code>mini_main.b2c_activity_statistics</code>.
     */
    public static final ActivityStatistics ACTIVITY_STATISTICS = ActivityStatistics.ACTIVITY_STATISTICS;

    /**
     * The table <code>mini_main.b2c_ali_mini_agent</code>.
     */
    public static final AliMiniAgent ALI_MINI_AGENT = AliMiniAgent.ALI_MINI_AGENT;

    /**
     * The table <code>mini_main.b2c_ali_mini_app_version</code>.
     */
    public static final AliMiniAppVersion ALI_MINI_APP_VERSION = AliMiniAppVersion.ALI_MINI_APP_VERSION;

    /**
     * The table <code>mini_main.b2c_ali_mini_auth_shop</code>.
     */
    public static final AliMiniAuthShop ALI_MINI_AUTH_SHOP = AliMiniAuthShop.ALI_MINI_AUTH_SHOP;

    /**
     * The table <code>mini_main.b2c_ali_my_order</code>.
     */
    public static final AliMyOrder ALI_MY_ORDER = AliMyOrder.ALI_MY_ORDER;

    /**
     * The table <code>mini_main.b2c_ali_user_token</code>.
     */
    public static final AliUserToken ALI_USER_TOKEN = AliUserToken.ALI_USER_TOKEN;

    /**
     * The table <code>mini_main.b2c_app</code>.
     */
    public static final App APP = App.APP;

    /**
     * The table <code>mini_main.b2c_app_auth</code>.
     */
    public static final AppAuth APP_AUTH = AppAuth.APP_AUTH;

    /**
     * The table <code>mini_main.b2c_article</code>.
     */
    public static final Article ARTICLE = Article.ARTICLE;

    /**
     * The table <code>mini_main.b2c_article_category</code>.
     */
    public static final ArticleCategory ARTICLE_CATEGORY = ArticleCategory.ARTICLE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_article_record</code>.
     */
    public static final ArticleRecord ARTICLE_RECORD = ArticleRecord.ARTICLE_RECORD;

    /**
     * The table <code>mini_main.b2c_back_process</code>.
     */
    public static final BackProcess BACK_PROCESS = BackProcess.BACK_PROCESS;

    /**
     * The table <code>mini_main.b2c_cache</code>.
     */
    public static final Cache CACHE = Cache.CACHE;

    /**
     * The table <code>mini_main.b2c_category</code>.
     */
    public static final Category CATEGORY = Category.CATEGORY;

    /**
     * The table <code>mini_main.b2c_charge_renew</code>.
     */
    public static final ChargeRenew CHARGE_RENEW = ChargeRenew.CHARGE_RENEW;

    /**
     * The table <code>mini_main.b2c_db_option_record</code>.
     */
    public static final DbOptionRecord DB_OPTION_RECORD = DbOptionRecord.DB_OPTION_RECORD;

    /**
     * The table <code>mini_main.b2c_decoration_template</code>.
     */
    public static final DecorationTemplate DECORATION_TEMPLATE = DecorationTemplate.DECORATION_TEMPLATE;

    /**
     * The table <code>mini_main.b2c_dict_city</code>.
     */
    public static final DictCity DICT_CITY = DictCity.DICT_CITY;

    /**
     * The table <code>mini_main.b2c_dict_country</code>.
     */
    public static final DictCountry DICT_COUNTRY = DictCountry.DICT_COUNTRY;

    /**
     * The table <code>mini_main.b2c_dict_district</code>.
     */
    public static final DictDistrict DICT_DISTRICT = DictDistrict.DICT_DISTRICT;

    /**
     * 省份字典
     */
    public static final DictProvince DICT_PROVINCE = DictProvince.DICT_PROVINCE;

    /**
     * The table <code>mini_main.b2c_failed_jobs</code>.
     */
    public static final FailedJobs FAILED_JOBS = FailedJobs.FAILED_JOBS;

    /**
     * The table <code>mini_main.b2c_goods</code>.
     */
    public static final Goods GOODS = Goods.GOODS;

    /**
     * The table <code>mini_main.b2c_goods_brand</code>.
     */
    public static final GoodsBrand GOODS_BRAND = GoodsBrand.GOODS_BRAND;

    /**
     * The table <code>mini_main.b2c_goods_img</code>.
     */
    public static final GoodsImg GOODS_IMG = GoodsImg.GOODS_IMG;

    /**
     * The table <code>mini_main.b2c_goods_spec_product</code>.
     */
    public static final GoodsSpecProduct GOODS_SPEC_PRODUCT = GoodsSpecProduct.GOODS_SPEC_PRODUCT;

    /**
     * The table <code>mini_main.b2c_grasp_goods</code>.
     */
    public static final GraspGoods GRASP_GOODS = GraspGoods.GRASP_GOODS;

    /**
     * The table <code>mini_main.b2c_jobs</code>.
     */
    public static final Jobs JOBS = Jobs.JOBS;

    /**
     * The table <code>mini_main.b2c_log_manage</code>.
     */
    public static final LogManage LOG_MANAGE = LogManage.LOG_MANAGE;

    /**
     * The table <code>mini_main.b2c_mp_auth_shop</code>.
     */
    public static final MpAuthShop MP_AUTH_SHOP = MpAuthShop.MP_AUTH_SHOP;

    /**
     * The table <code>mini_main.b2c_mp_daily_retain</code>.
     */
    public static final MpDailyRetain MP_DAILY_RETAIN = MpDailyRetain.MP_DAILY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_daily_visit</code>.
     */
    public static final MpDailyVisit MP_DAILY_VISIT = MpDailyVisit.MP_DAILY_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_deploy_history</code>.
     */
    public static final MpDeployHistory MP_DEPLOY_HISTORY = MpDeployHistory.MP_DEPLOY_HISTORY;

    /**
     * The table <code>mini_main.b2c_mp_distribution_visit</code>.
     */
    public static final MpDistributionVisit MP_DISTRIBUTION_VISIT = MpDistributionVisit.MP_DISTRIBUTION_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_jump_version</code>.
     */
    public static final MpJumpVersion MP_JUMP_VERSION = MpJumpVersion.MP_JUMP_VERSION;

    /**
     * The table <code>mini_main.b2c_mp_monthly_retain</code>.
     */
    public static final MpMonthlyRetain MP_MONTHLY_RETAIN = MpMonthlyRetain.MP_MONTHLY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_monthly_visit</code>.
     */
    public static final MpMonthlyVisit MP_MONTHLY_VISIT = MpMonthlyVisit.MP_MONTHLY_VISIT;

    /**
     * The table <code>mini_main.b2c_mp_official_account</code>.
     */
    public static final MpOfficialAccount MP_OFFICIAL_ACCOUNT = MpOfficialAccount.MP_OFFICIAL_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_mp_official_account_user</code>.
     */
    public static final MpOfficialAccountUser MP_OFFICIAL_ACCOUNT_USER = MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;

    /**
     * The table <code>mini_main.b2c_mp_operate_log</code>.
     */
    public static final MpOperateLog MP_OPERATE_LOG = MpOperateLog.MP_OPERATE_LOG;

    /**
     * The table <code>mini_main.b2c_mp_summary_trend</code>.
     */
    public static final MpSummaryTrend MP_SUMMARY_TREND = MpSummaryTrend.MP_SUMMARY_TREND;

    /**
     * The table <code>mini_main.b2c_mp_summary_trend_shop</code>.
     */
    public static final MpSummaryTrendShop MP_SUMMARY_TREND_SHOP = MpSummaryTrendShop.MP_SUMMARY_TREND_SHOP;

    /**
     * The table <code>mini_main.b2c_mp_user_portrait</code>.
     */
    public static final MpUserPortrait MP_USER_PORTRAIT = MpUserPortrait.MP_USER_PORTRAIT;

    /**
     * The table <code>mini_main.b2c_mp_version</code>.
     */
    public static final MpVersion MP_VERSION = MpVersion.MP_VERSION;

    /**
     * The table <code>mini_main.b2c_mp_visit_page</code>.
     */
    public static final MpVisitPage MP_VISIT_PAGE = MpVisitPage.MP_VISIT_PAGE;

    /**
     * The table <code>mini_main.b2c_mp_weekly_retain</code>.
     */
    public static final MpWeeklyRetain MP_WEEKLY_RETAIN = MpWeeklyRetain.MP_WEEKLY_RETAIN;

    /**
     * The table <code>mini_main.b2c_mp_weekly_visit</code>.
     */
    public static final MpWeeklyVisit MP_WEEKLY_VISIT = MpWeeklyVisit.MP_WEEKLY_VISIT;

    /**
     * The table <code>mini_main.b2c_order_goods</code>.
     */
    public static final OrderGoods ORDER_GOODS = OrderGoods.ORDER_GOODS;

    /**
     * The table <code>mini_main.b2c_order_info</code>.
     */
    public static final OrderInfo ORDER_INFO = OrderInfo.ORDER_INFO;

    /**
     * The table <code>mini_main.b2c_qf_img</code>.
     */
    public static final QfImg QF_IMG = QfImg.QF_IMG;

    /**
     * The table <code>mini_main.b2c_shop</code>.
     */
    public static final Shop SHOP = Shop.SHOP;

    /**
     * The table <code>mini_main.b2c_shop_account</code>.
     */
    public static final ShopAccount SHOP_ACCOUNT = ShopAccount.SHOP_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_shop_activity</code>.
     */
    public static final ShopActivity SHOP_ACTIVITY = ShopActivity.SHOP_ACTIVITY;

    /**
     * The table <code>mini_main.b2c_shop_child_account</code>.
     */
    public static final ShopChildAccount SHOP_CHILD_ACCOUNT = ShopChildAccount.SHOP_CHILD_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_shop_child_role</code>.
     */
    public static final ShopChildRole SHOP_CHILD_ROLE = ShopChildRole.SHOP_CHILD_ROLE;

    /**
     * The table <code>mini_main.b2c_shop_free_experience</code>.
     */
    public static final ShopFreeExperience SHOP_FREE_EXPERIENCE = ShopFreeExperience.SHOP_FREE_EXPERIENCE;

    /**
     * The table <code>mini_main.b2c_shop_grade</code>.
     */
    public static final ShopGrade SHOP_GRADE = ShopGrade.SHOP_GRADE;

    /**
     * The table <code>mini_main.b2c_shop_grade_log</code>.
     */
    public static final ShopGradeLog SHOP_GRADE_LOG = ShopGradeLog.SHOP_GRADE_LOG;

    /**
     * The table <code>mini_main.b2c_shop_operation</code>.
     */
    public static final ShopOperation SHOP_OPERATION = ShopOperation.SHOP_OPERATION;

    /**
     * The table <code>mini_main.b2c_shop_question_feedback</code>.
     */
    public static final ShopQuestionFeedback SHOP_QUESTION_FEEDBACK = ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK;

    /**
     * The table <code>mini_main.b2c_shop_renew</code>.
     */
    public static final ShopRenew SHOP_RENEW = ShopRenew.SHOP_RENEW;

    /**
     * The table <code>mini_main.b2c_shop_role</code>.
     */
    public static final ShopRole SHOP_ROLE = ShopRole.SHOP_ROLE;

    /**
     * The table <code>mini_main.b2c_shop_uploaded_image</code>.
     */
    public static final ShopUploadedImage SHOP_UPLOADED_IMAGE = ShopUploadedImage.SHOP_UPLOADED_IMAGE;

    /**
     * The table <code>mini_main.b2c_shop_uploaded_image_category</code>.
     */
    public static final ShopUploadedImageCategory SHOP_UPLOADED_IMAGE_CATEGORY = ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_shop_version</code>.
     */
    public static final ShopVersion SHOP_VERSION = ShopVersion.SHOP_VERSION;

    /**
     * The table <code>mini_main.b2c_sms</code>.
     */
    public static final Sms SMS = Sms.SMS;

    /**
     * The table <code>mini_main.b2c_sort</code>.
     */
    public static final Sort SORT = Sort.SORT;

    /**
     * The table <code>mini_main.b2c_spec</code>.
     */
    public static final Spec SPEC = Spec.SPEC;

    /**
     * The table <code>mini_main.b2c_spec_vals</code>.
     */
    public static final SpecVals SPEC_VALS = SpecVals.SPEC_VALS;

    /**
     * The table <code>mini_main.b2c_statistics_shop</code>.
     */
    public static final StatisticsShop STATISTICS_SHOP = StatisticsShop.STATISTICS_SHOP;

    /**
     * The table <code>mini_main.b2c_system_cfg</code>.
     */
    public static final SystemCfg SYSTEM_CFG = SystemCfg.SYSTEM_CFG;

    /**
     * The table <code>mini_main.b2c_system_child_account</code>.
     */
    public static final SystemChildAccount SYSTEM_CHILD_ACCOUNT = SystemChildAccount.SYSTEM_CHILD_ACCOUNT;

    /**
     * The table <code>mini_main.b2c_system_role</code>.
     */
    public static final SystemRole SYSTEM_ROLE = SystemRole.SYSTEM_ROLE;

    /**
     * The table <code>mini_main.b2c_system_user</code>.
     */
    public static final SystemUser SYSTEM_USER = SystemUser.SYSTEM_USER;

    /**
     * The table <code>mini_main.b2c_third_party_services</code>.
     */
    public static final ThirdPartyServices THIRD_PARTY_SERVICES = ThirdPartyServices.THIRD_PARTY_SERVICES;

    /**
     * The table <code>mini_main.b2c_uploaded_image</code>.
     */
    public static final UploadedImage UPLOADED_IMAGE = UploadedImage.UPLOADED_IMAGE;

    /**
     * The table <code>mini_main.b2c_uploaded_image_category</code>.
     */
    public static final UploadedImageCategory UPLOADED_IMAGE_CATEGORY = UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

    /**
     * The table <code>mini_main.b2c_upload_uyun_record</code>.
     */
    public static final UploadUyunRecord UPLOAD_UYUN_RECORD = UploadUyunRecord.UPLOAD_UYUN_RECORD;

    /**
     * The table <code>mini_main.b2c_user</code>.
     */
    public static final User USER = User.USER;

    /**
     * The table <code>mini_main.b2c_user_detail</code>.
     */
    public static final UserDetail USER_DETAIL = UserDetail.USER_DETAIL;

    /**
     * The table <code>mini_main.b2c_user_login_record</code>.
     */
    public static final UserLoginRecord USER_LOGIN_RECORD = UserLoginRecord.USER_LOGIN_RECORD;

    /**
     * The table <code>mini_main.b2c_user_summary_trend</code>.
     */
    public static final UserSummaryTrend USER_SUMMARY_TREND = UserSummaryTrend.USER_SUMMARY_TREND;

    public static final TaskJobContent TASK_JOB_CONTENT = TaskJobContent.TASK_JOB_CONTENT;

    public static final TaskJobMain TASK_JOB_MAIN = TaskJobMain.TASK_JOB_MAIN;

    public static final OrderInfoNew ORDER_INFO_NEW = OrderInfoNew.ORDER_INFO_NEW;
    /**
     * The table <code>mini_main.b2c_tag</code>.
     */
    public static final Tag TAG = Tag.TAG;
    /**
     * The table <code>mini_main.b2c_shop_tag</code>.
     */
    public static final ShopTag SHOP_TAG = ShopTag.SHOP_TAG;
}
