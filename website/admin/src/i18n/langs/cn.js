// cn.js
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
// 全局消息国际化配置
import {
  cn as messageHint
} from './views/admin_new/cn/messageHintCn'
// 选择链接弹窗国际化
import {
  cn as selectLinks
} from './views/admin_new/cn/components/selectLinks/selectLinksCn'
import {
  cn as router
} from './views/admin_new/cn/routerCn'
import {
  cn as ShopConfiguration
} from './views/ShopConfiguration'

// 基础信息-店铺基础信息
import {
  cn as storeBasicInformation
} from './views/admin_new/cn/index/basicSettings/base_manger/store_basic_informationCn'
// 基础信息-店铺配置
import {
  cn as storeCommonSettings
} from './views/admin_new/cn/index/basicSettings/base_manger/store_common_settingsCn'
import {
  cn as adminPageFramework
} from './views/admin_new/cn/adminPageFrameworkCn'
// 基础信息 第三方对接配置
import {
  cn as thirdPartyConfig
} from './views/admin_new/cn/index/basicSettings/thirdPartyConfig/thirdPartyConfigCn'

import {
  cn as videoSpace
} from './views/admin_new/cn/index/videoSpace/videoSpaceCn'

// 概览系列国际化
// 概览 -> 概览
import {
  cn as overview
} from './views/admin_new/cn/index/overview/overviewCn'
// 商城概况 - 店铺助手
import {
  cn as taskList
} from './views/admin_new/cn/index/overview/taskListCn'
// 交易统计
import {
  cn as tradesStatistics
} from './views/admin_new/cn/index/overview/tradesStatistics/tradesStatisticsCn'
import {
  cn as userStatistics
} from './views/admin_new/cn/index/overview/userStatistics/userStatisticsCn'
// 资产管理
import {
  cn as assetsManage
} from './views/admin_new/cn/index/overview/assetsManage/assetsManageCn'
// 商品统计
import {
  cn as goodsStaticties
} from './views/admin_new/cn/index/overview/goodsStaticties/goodsStatictiesCn'

// 用户画像
import {
  cn as userportrait
} from './views/admin_new/cn/index/overview/userportrait/userportraitCn'
// 访问分析
import {
  cn as visitAnalysis
} from './views/admin_new/cn/index/overview/visitAnalysis/visitAnalysisCn'

// 小程序管理系列国际化
// 小程序管理 -> 页面装修
import {
  cn as pictureSetting
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/pictureSettingCn'
// 底部导航配置
import {
  cn as bottomNavigation
} from './views/admin_new/cn/index/miniProgramManagement/bottomNavigation/bottomNavigationCn'
// 个人中心配置
import {
  cn as personalCenter
} from './views/admin_new/cn/index/miniProgramManagement/personalCenter/personalCenterCn'
// 搜索配置
import {
  cn as searchConfig
} from './views/admin_new/cn/index/miniProgramManagement/searchConfig/searchConfigCn'
// 页面分类
import {
  cn as pageClassification
} from './views/admin_new/cn/index/miniProgramManagement/pageClassification/pageClassificationCn'
// 小程序页面装修home页国际化
import {
  cn as decorationHome
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationHomeCn'
// 小程序页面装修页面设置模块国际化
import {
  cn as pageSetUp
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/pageSetUpCn'
// 小程序页面装修图片导航模块国际化
import {
  cn as pictureNavigation
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/pictureNavigationCn'
// 小程序页面装修商品搜索模块国际化
import {
  cn as commoditySearch
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/commodityComponents/commoditySearchCn'
// 小程序页面装修商品分组模块国际化
import {
  cn as commodityGrouping
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/commodityComponents/commodityGroupingCn'
// 小程序页面装修会员卡
import {
  cn as membershipCard
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/membershipCardCn'
// 小程序装修砍价
import {
  cn as bargain
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/bargainCn'
import {
  cn as addSeckillDialog
} from './views/admin_new/cn/components/picture_setting/addSeckillDialogCn'
// 小程序装修秒杀
import {
  cn as spike
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/spikeCn'
// 营销管理  积分兑换
import {
  cn as mintegralExchange
} from './views/admin_new/cn/index/marketManagement/integralExchange/mintegralExchangeCn'
// 积分兑换
import {
  cn as integralExchange
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/integralExchangeCn'
// 瓜分积分
import {
  cn as divideScorePoints
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/divideScorePointsCn'
// 图片热区
import {
  cn as pictureHotSpot
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/pictureHotSpotCn'
// 小程序装修拼团抽奖
import {
  cn as fightGroup
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/fightGroupCn'
// 小程序页面装修优惠券模块国际化
import {
  cn as coupon
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/couponCn'
// 小程序页面装修商品模块国际化
import {
  cn as commodity
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/commodityComponents/commodityCn'
// 小程序页面装修图片广告模块国际化
import {
  cn as pictureAds
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/pictureAdsCn'
// 小程序页面装修图片导航模块国际化
import {
  cn as shopRecruit
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/shopRecruitCn'
// 小程序页面装修地图模块国际化
import {
  cn as mapModule
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/marketingComponents/mapModuleCn'
// 小程序页面装修魔方多图国际化
import {
  cn as magicMap
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/magicMapCn'
// 小程序页面装修左文右图国际化
import {
  cn as leftWingRightPicture
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/leftWingRightPictureCn'
// 小程序页面装修文本模块国际化
import {
  cn as textModule
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/textModuleCn'
// 轮播图模块
import {
  cn as carouselPicture
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/carouselPictureCn'
// 小程序页面装修标题模块国际化
import {
  cn as titleModule
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/titleModuleCn'
// 小程序页面装修店铺公告、公众号、客服模块、电话模块国际化
import {
  cn as shopNotices
} from './views/admin_new/cn/index/miniProgramManagement/pictureSetting/decorationModules/graphicAndTextComponents/shopNoticesCn'

// 商品管理系列国际化
// 商品管理/全部商品
import {
  cn as allGoods
} from './views/admin_new/cn/index/goodsManagement/allGoodsCn'
import {
  cn as goodsAddEditInfo
} from './views/admin_new/cn/index/goodsManagement/goodsAddEditInfoCn'
import {
  cn as allGoodsLabel
} from './views/admin_new/cn/index/goodsManagement/allGoodsLabelCn'
import {
  cn as goodsImport
} from './views/admin_new/cn/index/goodsManagement/goodsImportCn'
import {
  cn as addAndUpdateGoodsLabel
} from './views/admin_new/cn/index/goodsManagement/addAndUpdateGoodsLabelCn'
import {
  cn as goodsSorts
} from './views/admin_new/cn/index/goodsManagement/goodsSortsCn'
import {
  cn as goodsRecommendSorts
} from './views/admin_new/cn/index/goodsManagement/goodsRecommendSortsCn'
import {
  cn as evaluation
} from './views/admin_new/cn/index/goodsManagement/evaluationCn'
import {
  cn as recommend
} from './views/admin_new/cn/index/goodsManagement/recommendCn'
import {
  cn as brandManagement
} from './views/admin_new/cn/index/goodsManagement/brandManagementCn'
// 营销管理系列国际化
// 营销管理通用词汇
import {
  cn as marketCommon
} from './views/admin_new/cn/index/marketManagement/marketCommonCn'
import {
  cn as marketManage
} from './views/admin_new/cn/index/marketManagement/marketManageCn'
// 多人拼团
import {
  cn as groupBuy
} from './views/admin_new/cn/index/marketManagement/spellGroup/groupBuy'
// 营销活动状态 tabs
import {
  cn as statusTab
} from './views/admin_new/cn/components/status/statusTabCn'
// 拼团抽奖
import {
  cn as lotteryDraw
} from './views/admin_new/cn/index/marketManagement/lotteryDraw/lotteryDrawCn'
// 分享有礼
import {
  cn as sharePolite
} from './views/admin_new/cn/index/marketManagement/sharePolite/sharePolite'
// 分享有礼-领取明细
import {
  cn as receiveDetails
} from './views/admin_new/cn/index/marketManagement/sharePolite/receiveDetails'
// 优惠券礼包
import {
  cn as couponPackage
} from './views/admin_new/cn/index/marketManagement/couponPackage/couponPackageCn'
// 优惠券礼包-活动创建页
import {
  cn as addCouponPackage
} from './views/admin_new/cn/index/marketManagement/couponPackage/addCouponPackageCn'
// 砍价列表
import {
  cn as bargainList
} from './views/admin_new/cn/index/marketManagement/bargain/bargainListCn'
// 添加砍价活动
import {
  cn as addBargainAct
} from './views/admin_new/cn/index/marketManagement/bargain/addBargainActCn'
// 普通优惠券
import {
  cn as ordinaryCoupon
} from './views/admin_new/cn/index/marketManagement/ordinaryCoupon/ordinaryCouponCn'
// 普通优惠券-普通优惠券列表
import {
  cn as ordinaryCouponList
} from './views/admin_new/cn/index/marketManagement/ordinaryCoupon/ordinaryCouponListCn'
// 普通优惠券-领取明细
import {
  cn as couponReceive
} from './views/admin_new/cn/index/marketManagement/ordinaryCoupon/couponReceiveCn'
// 好友助力
import {
  cn as promoteList
} from './views/admin_new/cn/index/marketManagement/friendPromote/promoteListCn'
// 幸运大抽奖
import {
  cn as luckyDraw
} from './views/admin_new/cn/index/marketManagement/luckyDraw/luckyDraw'
// 满折满减
import {
  cn as fullCuti18n
} from './views/admin_new/cn/index/marketManagement/fullCut/fullCutCn'
// 小程序装修
import {
  cn as pageDecoration
} from './views/admin_new/cn/components/pageDecoration/pageDecorationCn'
// 分享有礼-添加
import {
  cn as adSharePolite
} from './views/admin_new/cn/index/marketManagement/sharePolite/adSharePolite'
// 限时降价列表
import {
  cn as reducePriceList
} from './views/admin_new/cn/index/marketManagement/reducePrice/reducePriceListCn'
// 我要送礼
import {
  cn as giveGift
} from './views/admin_new/cn/index/marketManagement/giveGift/giveGift'
// 营销管理/消息推送
import {
  cn as messagePush
} from './views/admin_new/cn/index/marketManagement/messagePush/messagePushCn'
// 分销
import {
  cn as distribution
} from './views/admin_new/cn/index/marketManagement/distribution/distributionCn'
// 表单统计home页
import {
  cn as formStatisticsHome
} from './views/admin_new/cn/index/marketManagement/formStatistics/formStatisticsHomeCn'
// 表单统计所有模块
import {
  cn as formDecorationModel
} from './views/admin_new/cn/index/marketManagement/formStatistics/formDecoration/formDecorationModelCn'
// 定向发券
import {
  cn as couponGive
} from './views/admin_new/cn/index/marketManagement/couponGive/couponGiveCn'
// 秒杀
import {
  cn as seckill
} from './views/admin_new/cn/index/marketManagement/seckill/seckillCn'
// 满包邮
import {
  cn as shipping
} from './views/admin_new/cn/index/marketManagement/shipping/shippingCn'
// 赠品
import {
  cn as gift
} from './views/admin_new/cn/index/marketManagement/gift/giftCn'
// 微信好物圈
import {
  cn as wechateGoodsCicle
} from './views/admin_new/cn/index/marketManagement/wechateGoodsCicle/wechateGoodsCicleCn'
// 支付有礼
import {
  cn as payReward
} from './views/admin_new/cn/index/marketManagement/payReward/payRewardCn'
// 首单特惠
import {
  cn as firstSpecial
} from './views/admin_new/cn/index/marketManagement/firstSpecial/firstSpecialCn'
// 首单特惠新增
import {
  cn as firstSpecialAdd
} from './views/admin_new/cn/index/marketManagement/firstSpecial/firstSpecialAddCn'
// 开屏有礼列表
import {
  cn as openScreen
} from './views/admin_new/cn/index/marketManagement/openScreen/openScreenListCn'
// 开屏有礼添加
import {
  cn as openScreenAdd
} from './views/admin_new/cn/index/marketManagement/openScreen/openScreenAddCn'
// 开屏有礼活动详情
import {
  cn as openScreenDetail
} from './views/admin_new/cn/index/marketManagement/openScreen/openScreenDetailCn'
// 评价有礼列表
import {
  cn as evaluationGiftList
} from './views/admin_new/cn/index/marketManagement/evaluationGift/evaluationGiftListCn'
import {
  cn as evaluationGiftAdd
} from './views/admin_new/cn/index/marketManagement/evaluationGift/evaluationGiftAddCn'
// 表单装修主页
import {
  cn as formDecorationHome
} from './views/admin_new/cn/index/marketManagement/formStatistics/formDecoration/formDecorationHomeCn'
// 打包一口价
import {
  cn as packagePrice
} from './views/admin_new/cn/index/marketManagement/packagePrice/packagePriceList'
// 组团瓜分积分
import {
  cn as groupIntegration
} from './views/admin_new/cn/index/marketManagement/groupIntegration/groupIntegrationCn'
// 直播
import {
  cn as live
} from './views/admin_new/cn/index/marketManagement/live/liveCn'
import {
  cn as preSale
} from './views/admin_new/cn/index/marketManagement/preSale/preSaleCn'

// 订单管理系列模块化
// 订单通用
import {
  cn as orderCommon
} from './views/admin_new/cn/index/orderManagement/orderCommonCn'
// 订单列表
import {
  cn as order
} from './views/admin_new/cn/index/orderManagement/orderListCn'
// 订单搜索
import {
  cn as orderSearch
} from './views/admin_new/cn/index/orderManagement/orderListSearchCn'
// 虚拟商品订单-会员卡订单
import {
  cn as memberCardOrder
} from './views/admin_new/cn/index/orderManagement/virtualGoodsOrder/memberCardOrderCn'
// 虚拟商品订单-优惠券包订单
import {
  cn as couponPackageOrder
} from './views/admin_new/cn/index/orderManagement/virtualGoodsOrder/couponPackageOrderCn'
// 虚拟商品订单-手动退款弹窗
import {
  cn as refundDialog
} from './views/admin_new/cn/index/orderManagement/virtualGoodsOrder/refundDialogCn'
// 批量发货
import {
  cn as bulkShipment
} from './views/admin_new/cn/index/orderManagement/bulkShipmentCn'

// 会员管理系列国际化
// 会员列表
import {
  cn as membershipIntroduction
} from './views/admin_new/cn/index/userManagement/member/membershipIntroductionCn'
// 会员导入
import {
  cn as memberIntroductionList
} from './views/admin_new/cn/index/userManagement/member/memberIntroductionListCn'
import {
  cn as memberIntroductionDialog
} from './views/admin_new/cn/index/userManagement/member/memberIntroductionDialogCn'
// 设置激活通知
import {
  cn as activationNotificationDialog
} from './views/admin_new/cn/index/userManagement/member/activationNotificationDialogCn'
// 会员管理-会员卡
import {
  cn as memberCard
} from './views/admin_new/cn/index/userManagement/card/memberCardCn'
// 会员管理-积分配置
import {
  cn as scoreCfg
} from './views/admin_new/cn/index/userManagement/score/scoreCfgCn'
// 会员管理-标签管理
import {
  cn as tag
} from './views/admin_new/cn/index/userManagement/tag/tagCn'

// 门店管理模块化
// 门店通用
import {
  cn as storeCommon
} from './views/admin_new/cn/index/storeManagement/storeCommonCn'
// 门店列表
import {
  cn as storeList
} from './views/admin_new/cn/index/storeManagement/store/storeListCn'
// 门店列表-添加门店
import {
  cn as addStore
} from './views/admin_new/cn/index/storeManagement/store/addStoreCn'
// 门店列表-商品管理
import {
  cn as storeGoodsList
} from './views/admin_new/cn/index/storeManagement/store/storeGoodsListCn'
// 门店列表-核销员管理
import {
  cn as verifierManage
} from './views/admin_new/cn/index/storeManagement/store/verifierManageCn'
// 门店列表-门店管理
import {
  cn as storeManage
} from './views/admin_new/cn/index/storeManagement/storeManage/storeManageCn'
// 门店列表-服务管理
import {
  cn as serviceManage
} from './views/admin_new/cn/index/storeManagement/storeManage/service/serviceManageCn'
// 门店列表-服务列表
import {
  cn as serviceList
} from './views/admin_new/cn/index/storeManagement/storeManage/service/serviceListCn'
// 门店列表-服务分类
import {
  cn as serviceClassify
} from './views/admin_new/cn/index/storeManagement/storeManage/service/serviceClassifyCn'
// 门店列表-服务添加
import {
  cn as serviceAdd
} from './views/admin_new/cn/index/storeManagement/storeManage/service/serviceAddCn'
// 门店列表-预约管理
import {
  cn as reservationManage
} from './views/admin_new/cn/index/storeManagement/storeManage/reservation/reservationCn'
// 门店列表-技师管理
import {
  cn as technicianManage
} from './views/admin_new/cn/index/storeManagement/storeManage/technician/technicianManageCn'
// 门店列表-技师列表
import {
  cn as technicianList
} from './views/admin_new/cn/index/storeManagement/storeManage/technician/technicianListCn'
// 门店列表-技师列表
import {
  cn as technicianAdd
} from './views/admin_new/cn/index/storeManagement/storeManage/technician/technicianAddCn'
// 门店列表-技师分类
import {
  cn as technicianClassify
} from './views/admin_new/cn/index/storeManagement/storeManage/technician/technicianClassifyCn'
// 门店列表-排班管理
import {
  cn as schedulingManage
} from './views/admin_new/cn/index/storeManagement/storeManage/technician/schedulingManageCn'
// 门店管理-门店服务配置
import {
  cn as serviceConfig
} from './views/admin_new/cn/index/storeManagement/storeService/serviceConfigCn'
// 门店权限
import {
  cn as storePermission
} from './views/admin_new/cn/index/storeManagement/storePermission/storePermissionCn'
// 门店公告
import {
  cn as storeAnnouncement
} from './views/admin_new/cn/index/storeManagement/storeAnnouncement/storeAnnouncementCn'

// admin>公共组件
// 选择时间
import {
  cn as dateTimePicker
} from './views/admin_new/cn/components/dateTimePicker/dateTimePickerCn'

// 快递公司
// import { cn as expressList } from './views/admin/index/leftNavComponents/order/expressList'
import {
  cn as expressList
} from './views/admin_new/cn/index/orderManagement/expressListCn'

// 加价购-列表展示页
import {
  cn as purchase
} from './views/admin_new/cn/index/marketManagement/increasePurchase/purchaseCn'

import {
  cn as vTree
} from './views/admin_new/cn/components/vTree/vTreeCn'

// 基础配置系列模块化
// 服务承诺
import {
  cn as pledge
} from './views/admin_new/cn/index/basicSettings/servicePledge/pledgeCn'
// 支付配置
import {
  cn as payConfiguration
} from './views/admin_new/cn/index/basicSettings/tradeConfiguration/payConfigurationCn'
// 交易配置
import {
  cn as tradeConfiguration
} from './views/admin_new/cn/index/basicSettings/tradeConfiguration/tradeProcessConfigCn'
// 退换货配置
import {
  cn as returnconfiguration
} from './views/admin_new/cn/index/basicSettings/tradeConfiguration/returnGoodsConfigurationCn'
// 模板消息
import {
  cn as templateMessage
} from './views/admin_new/cn/index/basicSettings/templateMessage/templateMessageCn'
// 店铺权限
import {
  cn as authRoleList
} from './views/admin_new/cn/index/basicSettings/shopAuthority/authRoleListCn'
// 操作记录
import {
  cn as actionRecord
} from './views/admin_new/cn/index/basicSettings/actionRecord/actionRecordCn'
// 版本升级与续费
import {
  cn as versionUpgrade
} from './views/admin_new/cn/index/basicSettings/versionUpgrade/versionUpgradeCn'

const cn = {
  messages: {
    lang: '中文'
  },
  // 登录页面数据
  login_reg: {
    login: '登录',
    rej: '注册'
  },
  login_page: {
    login_main: '登录',
    login_f: '子账号登录',
    main_name: '账户名',
    password: '密码',
    z_phone: '子账号用户名/手机号',
    subAccount: '子账号'
  },
  Recommend: '推荐',
  environment: '办公环境',
  contact: {
    title: '联系我们',
    adress: '北京市海淀区西直门北大街54号伊泰大厦5层'
  },
  contact_right: {
    service: '客服电话:400-010-1039',
    s_title: '客服电话',
    QQ: '咨询QQ:3003715029',
    Q_title: 'QQ咨询'
  },
  // admin选择店铺数据
  shopData: [
    '账户设置',
    '子账号管理',
    '子账号权限管理',
    '授权公众号',
    '选择店铺',
    '退出'
  ],
  changePassword: '修改密码',
  // admin选择店铺页面contact组件数据
  adminContact: {
    phoneNum: '客服电话：400-010-1039',
    onLine: '在线咨询'
  },
  // admin选择店铺页面数据
  selectShop: {
    allShop: '全部店铺',
    data: '有效期',
    prohibited: '已禁止',
    notopen: '未营业',
    expired: '已过期',
    trial: '体验版',
    basic: '基础版',
    advanced: '高级版',
    ultimate: '旗舰版'
  },
  // admin  账户设置数据
  accountSetting: {
    title: '账户设置',
    account: '登录账户：',
    modifyPassword: '修改登录密码',
    phone: '联系电话：',
    name: '账户昵称：',
    namePlaceholder: '请输入昵称',
    head: '账户头像：',
    modify: '修改头像',
    s_modify: '确认修改',
    to_shop_list: '返回店铺列表',
    modifyPasswordtitle: '修改密码',
    oldPaawd: '旧密码',
    newPaawd: '新密码',
    comfNewPaawd: '确认新密码',
    sure: '确认修改',
    back: '返回'
  },
  // 浏览图片dialog数据
  imgageDalog: {
    title: '浏览图片',
    upload: '上传图片',
    tip: '上传图片支持jpeg、jpg、png、bmp、gif格式，为保障前端加载顺利，单张图片大小不能超过5M',
    imagePlaceholder: '请输入图片名称',
    search: '搜索',
    OriginalImg: '原图',
    tailoring: '裁剪',
    delImg: '删除',
    currentPage: '当前页面',
    totalPage: '总记录',
    strip: '条',
    cancel: '取消',
    Determine: '确定',
    displayOriginalGraph: '显示原图',
    cutPictures: '裁剪图片',
    deletePictures: '删除图片'
  },
  // admin 浏览图片弹窗 selectoptions数据
  options: [{
    value: 0,
    label: '按上传时间从晚到早'
  },
  {
    value: 1,
    label: '按上传时间从早到晚'
  },
  {
    value: 2,
    label: '按图片从大到小'
  },
  {
    value: 3,
    label: '按图片从小到大'
  },
  {
    value: 4,
    label: '按图片名降序'
  },
  {
    value: 5,
    label: '按图片名升序'
  }
  ],
  // 图片空间数据
  imgsSpace: {
    tipTitle: '当前版本为旗舰版，剩余9845.36M内存空间',
    hiddleTitle: '体验版100M内存空间，基础版500M内存空间，高级版2048M内存空间，旗舰版10240M内存空间',
    modeText: '了解更多',
    allCheckedText: '全选',
    deleteImgsText: '批量删除',
    moveImgsText: '批量移动',
    noneImgsText: '当前文件夹未找到符合要求的图片'
  },
  // 店铺风格模块数据
  shopStyle: {
    title: '店铺配色方案：',
    exampleTitle: '当前配色方案示例：',
    left_title_1: '满200减10',
    left_title_2: '满300减20',
    addCarText: '加入购物车',
    buyText: '立即购买',
    middle_Text_1: '黑色',
    right_time: '0天0时29分35',
    right_kd: '快递',
    right_count_1: '减10元',
    right_count_2: '应付总额：0元',
    right_submit: '提交订单',
    saveText: '保存',
    topTitleList: [
      {
        title: '配色1',
        colorLeft: 'background: #26C4BC;',
        colorRight: 'background:#FF9F15',
        id: '',
        choiseId: ''
      }, {
        title: '配色2',
        colorLeft: 'background: #ff6666;',
        colorRight: 'background: #fee7e7',
        id: '',
        choiseId: ''
      },
      {
        title: '配色3',
        colorLeft: 'background: #e53e24;',
        colorRight: 'background: #f2ad3c',
        id: '',
        choiseId: ''
      },
      {
        title: '配色4',
        colorLeft: 'background: #7e56c5;',
        colorRight: 'background: #333333',
        id: '',
        choiseId: ''
      },
      {
        title: '配色5',
        colorLeft: 'background: #09bb07;',
        colorRight: 'background: #333333',
        id: '',
        choiseId: ''
      },
      {
        title: '配色6',
        colorLeft: 'background: #4a90e2;',
        colorRight: 'background: #dbe9f9',
        id: '',
        choiseId: ''
      },
      {
        title: '自定义',
        colorLeft: 'background: #fff;',
        colorRight: 'background: #fff',
        id: 6,
        choiseId: ''
      }
    ]
  },
  router, // 路由信息

  membershipIntroduction, // 会员列表
  memberIntroductionList, // 会员导入列表
  memberIntroductionDialog, // 会员导入
  activationNotificationDialog, // 设置激活通知
  ShopConfiguration, // admin 店铺基础配置
  adminPageFramework, // admin页面框架
  thirdPartyConfig, // 第三方对接

  // 概览
  overview, // 商城概览
  tradesStatistics, // 交易统计
  // 商城概况 - 店铺助手
  taskList,
  // 用户画像
  userportrait,
  userStatistics, // 用户统计
  assetsManage, // 资产管理
  visitAnalysis, // 访问分析

  // 小程序管理
  pictureSetting,
  pageDecoration, // 页面装修
  bottomNavigation, // 底部导航
  personalCenter,
  searchConfig, // 搜索配置
  pageClassification, // 页面分类

  // 商品
  allGoods, // 商品管理/全部商品
  goodsAddEditInfo,
  allGoodsLabel, // 全部标签
  goodsImport, // 商品导入
  addAndUpdateGoodsLabel, // 标签添加和修改
  goodsSorts, // 商家分类全部，修改，新增
  goodsRecommendSorts, // 商家推荐分类全部，修改，新增
  evaluation, // 评价相关
  recommend, // 商品推荐

  // 订单
  order,
  orderCommon, // 订单通用
  orderSearch, // 订单搜索部分
  memberCardOrder, // 虚拟商品订单-会员卡订单
  couponPackageOrder, // 虚拟商品订单- 优惠券包订单
  refundDialog, // 虚拟商品订单- 手动退款弹窗
  bulkShipment, // 批量发货

  // 营销
  marketManage, // 营销管理
  marketCommon, // 营销管理通用词汇
  ordinaryCoupon, // 普通优惠券
  ordinaryCouponList, // 普通优惠券列表页面
  couponReceive, // 普通优惠券获取明细
  groupBuy, // 多人拼团
  lotteryDraw, // 拼团抽奖
  couponPackage, // 优惠券礼包
  addCouponPackage, // 优惠券礼包-活动创建页
  bargainList, // 砍价活动列表
  addBargainAct, // 创建砍价活动
  statusTab, // 营销活动状态 tabs
  sharePolite, // 分享有礼
  receiveDetails,
  promoteList, // 好友助力
  adSharePolite, // 添加分享有礼
  luckyDraw, // 幸运大抽奖
  fullCuti18n, // 满折满减
  reducePriceList, // 限时降价活动列表
  distribution, // 分销
  messagePush, // 营销管理/消息推送
  dateTimePicker, // 选择时间
  giveGift, // 我要送礼
  couponGive, // 定向发券
  seckill, // 秒杀
  shipping, // 满包邮
  gift, // 赠品
  ...zhLocale,
  expressList,
  wechateGoodsCicle, // 微信好物圈
  payReward, // 支付有礼
  firstSpecial, // 首单特惠
  firstSpecialAdd, // 首单特惠新增
  openScreen, // 开屏有礼
  openScreenAdd, // 开屏有礼添加
  openScreenDetail, // 开屏有礼活动详情
  evaluationGiftList, // 评价有礼列表
  evaluationGiftAdd,
  packagePrice, // 打包一口价
  groupIntegration, // 组团瓜分积分
  live, // 直播
  preSale, // 定金膨胀

  // 会员管理
  memberCard, // 会员管理-会员卡
  // 积分配置
  scoreCfg,
  tag, // 会员管理-标签管理
  // 门店
  storeCommon, // 门店通用
  storeList, // 门店列表
  addStore, // 门店列表-添加门店
  storeGoodsList, // 门店列表-商品管理
  verifierManage, // 门店列表-核销员管理
  storeManage, // 门店列表-门店管理
  serviceManage, // 门店列表-服务管理
  serviceList, // 门店列表-服务列表
  serviceClassify, // 门店列表-服务分类
  serviceAdd, // 门店列表-服务添加
  technicianManage, // 门店列表-技师管理
  technicianList, // 门店列表-技师列表
  technicianClassify, // 门店列表-技师分类
  technicianAdd, // 门店列表-技师添加
  schedulingManage, // 门店列表-排班管理
  purchase, // 加价购
  videoSpace,
  vTree,

  storeBasicInformation, // 基础配置
  storeCommonSettings, // 店铺配置
  pledge, // 服务承诺
  payConfiguration, // 支付配置
  tradeConfiguration, // 交易流程配置
  returnconfiguration, // 退换货配置
  templateMessage, // 运费模板
  serviceConfig, // 门店服务配置
  storePermission, // 门店权限
  storeAnnouncement, // 门店公告
  authRoleList, // 店铺权限
  actionRecord, // 操作记录
  versionUpgrade, // 版本升级
  decorationHome, // 页面装修部分
  pageSetUp, // 页面装修页面设置
  pictureNavigation, // 页面装修图片导航模块
  commoditySearch, // 小程序页面装修搜索模块
  membershipCard, // 小程序页面装修会员卡模块
  bargain, // 小程序装修砍价模块
  addSeckillDialog, // 添加砍价活动商品弹窗
  spike, // 小程序装修秒杀模块
  fightGroup, // 小程序装修拼团抽奖模块
  coupon, // 小程序页面装修优惠券模块
  commodity, //  小程序页面装修商品模块
  pictureAds, //  小程序页面装修图片广告模块
  shopRecruit, // 小程序页面装修店招设置模块
  mapModule, // 小程序页面装修地图模块
  magicMap, // 小程序页面装修魔方多图模块
  leftWingRightPicture, // 小程序页面装修左文右图国际化
  textModule, // 小程序文本模块国际化 以及下面简单模块的国际化
  titleModule, // 小程序标题模块国际化
  shopNotices, // 小程序店铺公告系列国际化
  messageHint, // 全局消息国际化
  brandManagement, // 商品品牌国际化
  selectLinks, // 选择链接弹窗国际化
  reservationManage, // 预约管理
  commodityGrouping, //  商品分组
  integralExchange, // 积分兑换
  divideScorePoints, // 瓜分积分
  pictureHotSpot, //  图片热区
  carouselPicture, // 轮播图模块
  goodsStaticties, // 商品统计
  formDecorationHome, //  表单装修主页
  formStatisticsHome, // 表单统计列表主页
  formDecorationModel, // 表单统计装修所有模块
  mintegralExchange, // 营销管理积分兑换
  // 授权公众号
  serviceAuth: {
    addButton: '添加授权',
    tipsOne: '注意：仅可以授权认证服务号.授权完成认证服务号，关注服务号的用户在接收小程序消息通知时优先通过小程序接收',
    tipsTop1: '由于微信平台相关要求，公众号授权完成后，需要在店铺内小程序管理-小程序授权绑定和该店铺小程序同主体的公众号，绑定后，使用该绑定公众号登录微信公众平台，在公众平台小程序小程序管理页面添加关联小程序，关联该小程序后，关注公众号的用户在接收小程序消息通知时即可通过公众号接收。小程序接收消息限制较大，详情查看',
    tipsTop2: '模板消息下发条件说明',
    tipsTop3: '此功能方便商家针对用户进行小程序消息通知。',
    nickName: '服务号名称',
    principalName: '主体名称',
    alias: '公众微信号',
    lastAuthTime: '授权时间',
    mpNickName: '已绑定小程序',
    isAuthOk: '授权状态',
    operation: '操作',
    payLook: '查看',
    payManage: '提现配置',
    payStoreName: '商户号:',
    payKey: '支付秘钥:',
    payCertContent: '支付证书:',
    payKeyContent: '支付私钥:',
    sure: '确 定',
    cancel: '取 消',
    haveAuth: '已授权',
    haveCancle: '已取消',
    title1: '授权列表',
    title2: '服务号详情',
    qurl: '公众号二维码',
    type: '公众号类型',
    newButton: '重新授权',
    tipsTop4: ' 如果你的公众号已成功升级（从未认证升为认证号，或从订阅号升为服务号），请点击重新授权',
    tipsTop5: '如何升级：',
    tipsTop6: '如需对公众号进行微信认证，请登录“微信公众平台-> 公众号设置”，在“认证情况”栏目，点击申请微信认证',
    authStats: '授权状态',
    Wechat0: '订阅号',
    Wechat1: '微信认证订阅号',
    Wechat2: '服务号',
    Wechat3: '微信认证服务号'
  }
}

export default cn
