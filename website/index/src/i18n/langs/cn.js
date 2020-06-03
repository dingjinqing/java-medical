// cn.js
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
// 官网
// 申请试用国际化
import {
  cn as indexApply
} from './views/index/cn/applyCn'
// 官网首页移动端国际化
import {
  cn as indexMobile
} from './views/index/cn/indexMobileCn'
const cn = {
  messages: {
    lang: '中文',
    index_nav_main: '首页',
    index_nav_new: '新闻资讯',
    index_nav_forum: '社区论坛',
    index_nav_link: '关于我们',
    index_nav_apply: '申请试用',
    index_nav_test: '测试1',
    index_application_title_1: '多样营销功能应用',
    index_application_content_1: '丰富的营销工具，多样营销，助力畅享千亿级流量',
    index_application_title_2: '预约服务',
    index_application_content_2: '多种预约模式，帮您提高顾客体验，自动化解决顾客预约难、等位久的问题',
    index_application_title_3: '砍价活动',
    index_application_content_3: '高扩散、低价让利消费者，从而宣传品牌、提高人气、大量引流，提高转化率',
    index_application_title_4: '分销模式',
    index_application_content_4: '一键分享，邀请下级，成单分佣。海量用户转发引流量，提高店铺曝光',
    index_application_title_5: '拼团活动',
    index_application_content_5: '低价限时多人团购，快速下单，海量分享。助您累积会员、走量促销，成交更轻松',
    index_advantage_top_title: '小程序核心功能优势',
    index_advantage_top_content: '众多核心功能，一键应用，提前布局微信新生态，抢占红利',
    index_advantage_title_1: '营销活动',
    index_advantage_content_1: '分裂优惠券：下单获得优惠券，分享到群、多人领取享优惠',
    index_advantage_content_2: '表单统计：问卷调查、简单预约信息收集，获取顾客喜好',
    index_advantage_content_3: '满折满减：实现刺激消费、促进消费、走量促销',
    index_advantage_content_4: '消息模板：自定义活动消息模板，定向选择顾客发送小程序消息信息',
    index_advantage_content_5: '更包含：拼团、砍价、秒杀、分销、抽奖、优惠券、积分商城、活动',
    index_advantage_content_6: '有礼、支付有礼等丰富营销活动',
    index_advantage_title_2: '立即使用',
    index_advantage_2_title_1: '会员管理',
    index_advantage_2_content_1: '会员等级、会员积分、会员余额、会员卡、限次卡（规定使用次数的卡）',
    index_advantage_2_content_2: '为会员提供精准的营销活动',
    index_advantage_2_content_3: '线上线下的会员打通，全时全地的为用户提供持续服务',
    index_advantage_2_title_2: '立即使用',
    index_advantage_3_title_1: '门店管理',
    index_advantage_3_content_1: '支持多门店连锁经营，增加门店曝光率',
    index_advantage_3_content_2: '门店细分获客、店员分销，降低门店获客成本',
    index_advantage_3_content_3: '门店预约服务单独设定，提高顾客体验，降低管理成本',
    index_advantage_3_title_2: '立即使用',
    index_advantage_4_title_1: '模板装修',
    index_advantage_4_content_1: '多种行业模板按需选择使用，模块拖拽即可完成装修',
    index_advantage_4_content_2: '导航菜单灵活设置。无需代码编辑',
    index_advantage_4_content_3: '图片广告、会员卡、优惠券、地图定位板块，一键添加展示',
    index_advantage_4_title_2: '立即使用',
    index_advantage_5_title_1: '数据统计',
    index_advantage_5_content_1: '销量、流量、转化率全面统计',
    index_advantage_5_content_2: '流量页面来源，精准统计',
    index_advantage_5_content_3: '用户画像形象展示，为您精准分析提供依据',
    index_advantage_5_title_2: '立即使用',
    index_advantage_6_title_1: '在线交易',
    index_advantage_6_content_1: '商品展示、在线下单、在线微信支付、余额支付、积分抵现',
    index_advantage_6_content_2: '完整线上交易流程；门店网店一键打通，降低运营成本，提升用户体验',
    index_advantage_6_title_2: '立即使用'
  },
  industry: {
    title_top: '覆盖多行业',
    content: '更加垂直的行业解决方案，满足广泛的业务需求，服务大众'
  },
  entrance: {
    title: '小程序十大流量入口',
    content: '众多的流量入口增加了消费者与品牌的黏性，共享微信全生态'
  },
  case: {
    title: '行业案例',
    content: '用创造力驱动产品，共赢商机、携手共进'
  },
  information: {
    title: '新闻资讯',
    content: '洞察行业新动态，把握行业新方向',
    more: '查看更多'
  },
  // 底部footer组件内数据
  footer: {
    title: '北京掌上先机网络科技有限公司',
    link: '联系我们',
    server: '7*24小时服务热线',
    adress: '公司地址',
    detail_adress: '北京市海淀区西直门北大街54号伊泰大厦5层',
    content: '关注微铺宝精选，了解小程序动态',
    footer_con_1: '友情链接',
    footer_con_2: '掌上先机',
    footer_con_3: 'B2C商城',
    footer_con_4: 'B2B2C商城',
    footer_con_5: 'POS门店系统',
    footer_con_6: '免费微店',
    footer_con_7: '云筹大数据',
    footer_con_8: '天津掌商先机网络科技有限公司',
    footer_con_9: '版权所有',
    footer_con_10: '京ICP备14046261号'
  },
  // 申请试用页面数据
  apply: {
    title: '免费申请试用',
    name: '姓名',
    submit: '提交',
    mobile: '电话',
    content: '提供企业规模化、多体系新零售解决方案',
    content_bottom: '多系统无缝对接',
    placeholder_name: '请填写您的姓名',
    placeholder_tel: '请填写手机号'
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
  aboutUs: {
    title: '公司简介',
    tiliang: '服务店铺体量',
    zengzhang: '连续5年客户增长率',
    kehu: '皇冠以上客户',
    xufei: '客户续费率',
    renzheng: '国际认证',
    p_1: '北京掌上先机网络科技有限公司，零售云服务提供商，基于云计算SaaS服务模式，以体系化解决方案，助力零售企业数字化智能化管理升级，成就企业规模化发展之路。',
    p_2: '2012年，旺店通成立于北京，之后在天津、上海、广州、杭州、义乌等25个省市设立了分支机构，员工近1000人，服务范围辐射全国并延伸至海外。 凭借技术创新、产品创新、服务创新和市场创新，旺店通实现了每年100%以上的客户增长；10万+客户涵盖了中粮、强生、3M、百威、周黑鸭、MG小象、水密码等世界500强、上市公司、知名品牌、TOP商家……2017全年交易额近万亿。规模化客户及头部客户优势给予了旺店通更高更全的行业视角，市场敏感度及强执行力。旺店通创始团队来自互联网上市公司技术管理层，产品及研发团队占比35% 以上，核心成员皆为清华、北大、北邮等985大学研究生，拥有ACM、数学建模等重量级竞赛获奖经历，具有千万用户运营经验。多年来旺店通构建了内外部“互联网、电商、管理”等资源池，“技术流”行业大咖云集。',
    p_3: '扎实的技术根基，使旺店通通过了CMMI3级国际认证、ISO27001信息安全认证、国家级高新技术企业认证、双软认证等多项资质认证，并获得了多项荣誉 ：中国电子商务服务商五十强企业、中国产业创新领域十佳SaaS服务商、电商奥斯卡金麦奖“最佳技术服务奖”、中国国际电商博览会“最佳电商服务企业奖”、阿里巴巴CCO“AG最佳赋能合作伙伴奖”、淘宝金牌淘拍档、京东“京卓越”奖项、苏宁易购“金牌易伙伴”等，现已与天猫、淘宝、京东等80+主流电商、外卖等平台建立了战略合作关系。',
    p_4: '未来，旺店通仍将继续秉持以人为本、客户至上、持续创新、产业共赢的发展理念，打造“ERP+”一体化零售企业服务生态。'
  },
  development: {
    title: '发展历程',
    p_1_1: '2017年 旺店通旗舰版上线，开创零售信息服务领域SaaS时代',
    p_1_2: '2017年 小程序SaaS平台正式上线，服务覆盖多行业',
    p_1_3: '2017年 员工超过800人，设立25家分支机构，服务范围辐射全国，延伸至海外',
    p_1_4: '2017年 连续5年客户超100%增长，双11订单总量2.89亿单，交易总额402亿，实现5连增',
    p_2_1: '2016年10月 服务超过100000家线上线下店铺',
    p_2_2: '2016年11月 双11客户总交易额237.7亿，订单量16800万',
    p_2_3: '2016年 E快帮、旺店通WMS、POS门店管理系统上线，服务覆盖大中小商家',
    p_2_4: '2016年 荣获淘宝服务市场金牌淘拍档称号',
    p_3_1: '2015年 与中粮、强生、好想你、来伊份、君乐宝、云集小也、十月妈咪、RIO锐澳、同仁堂、洽洽等世界500强、上市企业、知名品牌等重量级客户达成合作',
    p_3_2: '2015年11月 双11客户总交易额超过100亿元，单客户最大订单量97万单',
    p_3_3: '2015年 设立15家分支机构，服务遍及全国',
    p_4_1: '2014年 旺店通ERP企业版、微商城、B2B2C商城等多个产品上线，开始布局电商企业服务领域',
    p_4_2: '2014年 旺店通与周黑鸭达成战略合作，打造订单全渠道解决方案',
    p_4_3: '2014年 荣获中国电子商务协会“中国电子商务百强企业”称号',
    p_5_1: '2013年7月 全网第一家五金冠商家——朵朵云签约旺店通',
    p_5_2: '2013年 旺店通斩获最佳电商ERP新锐服务商、电商服务明日之星等多项殊荣',
    p_5_3: '2013年 企业成员过百，在上海、广州、杭州设立分支机构',
    p_6_1: '2012年11月 SaaS产品旺店通ERP专业版上线',
    p_6_2: '2012年 北京掌上先机网络科技有限公司在北京正式成立'
  },
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
  ...zhLocale,
  indexApply, // 申请试用国际化
  indexMobile // 官网首页移动端国际化

}

export default cn
