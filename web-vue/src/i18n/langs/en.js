// en.js
import enLocale from 'element-ui/lib/locale/lang/en'
import {
  en as programVersion
} from './views/programVersion'
import {
  en as shopAccountList
} from './views/shopAccountList'
import {
  en as shopList
} from './views/shopList'
import {
  en as publishList
} from './views/publishList'
import {
  en as versionList
} from './views/versionList'
import {
  en as router
} from './views/router'
import {
  en as membershipIntroduction
} from './views/membershipIntroduction'
import {
  en as marketManage
} from './views/marketManage'
import {
  en as ordinaryCoupon
} from './views/ordinaryCoupon'
import {
  en as ShopConfiguration
} from './views/ShopConfiguration'
import {
  en as adminPageFramework
} from './views/adminPageFramework'
import {
  en as goodsAddEditInfo
} from './views/goodsManage/goodsAddEditInfo'
// 商品管理系列国际化
// 商品管理/全部商品
import {
  en as allGoods
} from './views/goodsManage/allGoods'
import {
  en as goodsImport
} from './views/goodsManage/goodsImport'

// 营销管理
// 营销管理通用词汇
import {
  en as marketCommon
} from './views/admin/index/leftNavComponents/first_market_manage/marketCommon'
// 多人拼团
import {
  en as groupBuy
} from './views/admin/index/leftNavComponents/first_market_manage/spellGroup/groupBuy'
// 营销活动状态 tabs
import {
  en as statusTab
} from './views/components/status/statusTab'
// 分享有礼
import {
  en as sharePolite
} from './views/admin/index/leftNavComponents/first_market_manage/sharePolite/sharePolite'
// 分享有礼-领取明细
import {
  en as receiveDetails
} from './views/admin/index/leftNavComponents/first_market_manage/sharePolite/receiveDetails'
// 优惠券礼包
import {
  en as couponPackage
} from './views/admin/index/leftNavComponents/first_market_manage/couponPackage/couponPackage'
// 好友助力
import {
  en as promoteList
} from './views/admin/index/leftNavComponents/first_market_manage/friendPromote/promoteList'

// 小程序装修
import {
  en as pageDecoration
} from './views/smallProgramManagement/pageDecoration'
// 底部导航配置
import {
  en as bottomNavigation
} from './views/smallProgramManagement/bottomNavigation'
// 分享有礼-添加
import {
  en as adSharePolite
} from './views/admin/index/leftNavComponents/first_market_manage/sharePolite/adSharePolite'

const en = {
  messages: {
    lang: 'English',
    index_nav_main: 'home page',
    index_nav_new: 'News and Information',
    index_nav_forum: 'Community Forum',
    index_nav_link: 'About us',
    index_nav_apply: 'Apply for probation',
    index_nav_test: 'Test 1',
    index_application_title_1: 'Application of diversified marketing functions',
    index_application_content_1: 'Rich marketing tools, diversified marketing, to help enjoy 100 billion-grade flow',
    index_application_title_2: 'reservation service',
    index_application_content_2: 'Various booking modes can help you improve the customer experience and solve the problems of difficult and long waiting time automatically',
    index_application_title_3: 'Bargaining Activities',
    index_application_content_3: 'High diffusion, low price to benefit consumers, so as to publicize the brand, improve popularity, a large number of drainage, improve conversion rate',
    index_application_title_4: 'Distribution model',
    index_application_content_4: 'One-click sharing, invite subordinates, into a single commission. Massive User Forwarding and Drainage Traffic to Enhance Shop Exposure',
    index_application_title_5: 'Collaboration activities',
    index_application_content_5: 'Low price, limited time, multi-person group purchase, fast order, mass sharing. Help you accumulate membership, promote sales volume, make business easier',
    index_advantage_top_title: 'Core Functional Advantages of Small Programs',
    index_advantage_top_content: 'Numerous core functions, one key application, advance layout of Weixin new ecology, seize the dividend',
    index_advantage_title_1: 'Marketing activities',
    index_advantage_content_1: 'Split Coupons: Order to get coupons, share to groups, many people to get preferential treatment',
    index_advantage_content_2: 'Form Statistics: Questionnaire Survey, Simple Reservation Information Collection, Getting Customer Preferences',
    index_advantage_content_3: 'Full Discount: Stimulating Consumption, Promoting Consumption and Promoting Sales',
    index_advantage_content_4: 'Message template: Customize active message template to send widget message information to selected customers',
    index_advantage_content_5: 'Include: group, bargaining, second killing, distribution, lottery, coupons, points mall, activities',
    index_advantage_content_6: 'Rich marketing activities such as courtesy, payment courtesy, etc',
    index_advantage_title_2: 'Immediate use',
    index_advantage_2_title_1: 'Membership management',
    index_advantage_2_content_1: 'Membership grade, membership points, membership balance, membership card, limit card (the number of times required card)',
    index_advantage_2_content_2: 'Provide members with accurate marketing activities',
    index_advantage_2_content_3: 'Online and offline membership, full-time and full-time continuous service for users',
    index_advantage_2_title_2: 'Immediate use',
    index_advantage_3_title_1: 'Store management',
    index_advantage_3_content_1: 'Supporting multi-stores chain operation and increasing the exposure rate of stores',
    index_advantage_3_content_2: 'Segmentation of customer acquisition and clerk distribution in stores to reduce the cost of customer acquisition',
    index_advantage_3_content_3: 'Store reservation service is set separately to improve customer experience and reduce management costs',
    index_advantage_3_title_2: 'Immediate use',
    index_advantage_4_title_1: 'Formwork decoration',
    index_advantage_4_content_1: 'Various industry templates can be selected and used on demand, and the module can be dragged to complete the decoration',
    index_advantage_4_content_2: 'Navigation menu is set flexibly. No code editing is required',
    index_advantage_4_content_3: 'Photo Advertising, Membership Card, Coupon, Map Location Panel, One-click Add Display',
    index_advantage_4_title_2: 'Immediate use',
    index_advantage_5_title_1: 'data statistics',
    index_advantage_5_content_1: 'Comprehensive Statistics of Sales, Flow and Conversion Rate',
    index_advantage_5_content_2: 'Traffic page source, accurate statistics',
    index_advantage_5_content_3: 'User Portrait Image Display, Provide the Basis for Your Precision Analysis',
    index_advantage_5_title_2: 'Immediate use',
    index_advantage_6_title_1: 'Online Trading',
    index_advantage_6_content_1: 'Commodity Display, Online Ordering, Online Wechat Payment, Balance Payment, Points Cash',
    index_advantage_6_content_2: 'Complete online trading process; one-click access to stores and online stores, reduce operating costs, enhance user experience',
    index_advantage_6_title_2: 'Immediate use'
  },
  industry: {
    title_top: 'Covering multiple industries',
    content: 'More vertical industry solutions to meet a wide range of business needs and serve the public'
  },
  entrance: {
    title: 'Ten Flow Entrances for Small Programs',
    content: 'Numerous traffic entrances increase the stickiness of consumers and brands and share the whole ecosystem of Wechat'
  },
  case: {
    title: 'Industry case',
    content: 'Drive Products with Creativity, Win-win Business Opportunities and Work Together'
  },
  information: {
    title: 'News and Information',
    content: 'Insight into the new trends of the industry and grasp the new direction of the industry',
    more: 'See more'
  },
  // 底部footer组件内数据
  footer: {
    title: 'Beijing Handheld Pioneer Network Technology Co., Ltd',
    link: 'Contact us',
    server: '7*24-hour service hotline',
    adress: 'Company address',
    detail_adress: 'Fifth Floor of Yitai Building, 54 Xizhimen North Street, Haidian District, Beijing',
    content: 'Pay attention to the selection of micro-shop treasure, understand the dynamics of small programs',
    footer_con_1: 'Friendship Links',
    footer_con_2: 'Handheld First Opportunity',
    footer_con_3: 'B2C Mall',
    footer_con_4: 'B2B2C Mall',
    footer_con_5: 'POS Store System',
    footer_con_6: 'Free microshop',
    footer_con_7: 'Cloud computing data',
    footer_con_8: 'Beijing Weipubao Network Technology Co., Ltd.',
    footer_con_9: 'Copyright',
    footer_con_10: 'Beijing ICP No. 14046261'
  },
  // 申请试用页面数据
  apply: {
    title: 'Free trial application',
    name: 'name',
    submit: 'Submission',
    mobile: 'Tel',
    content: 'Provide enterprise scale, multi-system new retail solutions',
    content_bottom: 'Multi-system seamless docking',
    placeholder_name: 'Please fill in your name',
    placeholder_tel: 'Please fill in your cell phone number'
  },
  // 登录页面数据
  login_reg: {
    login: 'Sign in',
    rej: 'register'
  },
  login_page: {
    login_main: 'Sign in',
    login_f: 'Subaccount login',
    main_name: 'Main Account User Name',
    password: 'Password',
    z_phone: 'Subaccount username/cell phone number'
  },
  Recommend: 'Recommend',
  aboutUs: {
    title: 'Company Profile',
    tiliang: 'Service Store Volume',
    zengzhang: 'Customer growth rate for five consecutive years',
    kehu: 'Crown and above customers',
    xufei: 'Customer renewal rate',
    renzheng: 'International certification',
    p_1: 'Beijing Handheld Pioneer Network Technology Co., Ltd., a retail cloud service provider, based on cloud computing SaaS service mode, helps retail enterprises to upgrade their digital and intelligent management, and achieves the road of large-scale development.',
    p_2: "In 2012, Wangdian Tong was founded in Beijing. After that, it set up branches in 25 provinces and municipalities, including Tianjin, Shanghai, Guangzhou, Hangzhou and Yiwu, with nearly 1,000 employees. Its service scope radiates to the whole country and extends overseas. With technological innovation, product innovation, service innovation and market innovation, Wangdian Tong has achieved more than 100% annual customer growth; 100,000 + customers cover the top 500 companies in the world, such as COFCO, Johnson & Johnson, 3M, Budweiser, Zhou Heiya, MG elephant, water code, listed companies, well-known brands, TOP merchants, etc. The total volume of transactions in 2017 is nearly trillion. The advantages of large-scale customers and head customers give Wangdian Tong a higher and more complete industry perspective, market sensitivity and strong execution. Wangdian Tong's founding team is from the technical management of Internet listed companies.The product and R& D team account for more than 35%.The core members are 985 graduate students from Tsinghua University, Peking University and Beiyu University.Wangdian Tong has won awards in ACM, Mathematical Modeling and other heavyweight competitions, and has tens of millions of user operating experience.Over the years, Wangdian Tong has built internal and external resource pools such as 'Internet, e-commerce, management' and 'technology flow' industry.",
    p_3: "Strong technical foundation has enabled Wangdian Tong to pass CMMI3 international certification, ISO27001 information security certification, national high-tech enterprise certification, double soft certification and other certification, and won many honors: China's top 50 e-commerce service providers, China's top 10 SaaS service providers in the field of industrial innovation, e-commerce Olympics. Scarlett Golden Mac Award for Best Technical Service, China International E-Commerce Expo Award for Best E-Commerce Service Enterprise, Alibaba CCO AG Best Enabling Partner Award, Taobao Gold Medal Takeout Partner, Jingdong 'Beijing Excellence' Award, Suning Easy-to-buy 'Gold Medal Easy Partner' and so on, have been with Tianmao, Taobao, Jingdong and other 80 + mainstream. E-commerce, takeaway and other platforms have established strategic cooperative relations.",
    p_4: 'In the future, Wangdian Tong will continue to adhere to the development concept of people-oriented, customer-oriented, continuous innovation and win-win industrial development, and build the "ERP+" integrated retail enterprise service ecosystem.'
  },
  development: {
    title: 'development history',
    p_1_1: "In 2017, Wangdian Tong's flagship edition was launched, creating the SaaS era in the field of retail information services",
    p_1_2: 'In 2017, the small program SaaS platform was officially launched, and its services covered many industries.',
    p_1_3: 'In 2017, more than 800 employees and 25 branches were set up to serve the whole country and overseas.',
    p_1_4: 'In 2017, customers grew by more than 100% for five consecutive years. The total number of Shuang11 orders was 289 million, and the total volume of transactions was 40.2 billion, achieving five consecutive increases.',
    p_2_1: 'Over 100,000 online and offline stores in October 2016',
    p_2_2: 'In November 2016, the total transaction volume of Shuang11 customers was 23.77 billion yuan and the order volume was 168 million yuan.',
    p_2_3: '2016 E Fast Band, Wangdian Tong WMS, POS store management system on-line, service coverage of large, medium and small businesses',
    p_2_4: 'In 2016, Taobao was awarded the title of Gold Medal Taopai Partner in Taobao Service Market',
    p_3_1: 'In 2015, China Food and Agriculture Corporation, Johnson & Johnson, I miss you very much, Lai Yifen, Jun Lebao, Xiao Yiyi, October Mami, RIO Ruiao, Tongrentang, Jianqian and other top 500, listed companies, well-known brands and other heavyweight customers have reached cooperation.',
    p_3_2: 'In November 2015, the total transaction volume of Shuang11 customers exceeded 10 billion yuan, with a maximum order volume of 970,000 orders per customer.',
    p_3_3: 'In 2015, 15 branches were set up to serve all over the country.',
    p_4_1: 'In 2014, Wangdian Tong ERP Enterprise Edition, Micro Mall, B2B2C Mall and other products were launched, and began to lay out the service areas of e-commerce enterprises.',
    p_4_2: 'In 2014, Wangdian Tong and Zhou Hei Duck reached strategic cooperation to create a full-channel solution for orders.',
    p_4_3: 'In 2014, he was awarded the title of "Top 100 Enterprises of E-Commerce in China" by China Electronic Commerce Association.',
    p_5_1: 'In July 2013, the first hardware crown merchant in the whole network - Doudouyun signed Wangdian Tong',
    p_5_2: 'In 2013, Wangdian won many awards, such as Best E-commerce ERP Emerging Service Provider, Tomorrow Star of E-commerce Service, etc.',
    p_5_3: 'In 2013, more than 100 members of the enterprise set up branches in Shanghai, Guangzhou and Hangzhou.',
    p_6_1: 'SaaS Products Wangdian Tong ERP Professional Edition launched in November 2012',
    p_6_2: 'Beijing Handheld Pioneer Network Technology Co., Ltd. was formally established in Beijing in 2012.'
  },
  environment: 'Office environment',
  contact: {
    title: 'Contact us',
    adress: 'Fifth Floor of Yitai Building, 54 Xizhimen North Street, Haidian District, Beijing'
  },
  systemLogin: {
    login: 'Sign in',
    username: 'User Name/Mobile Number',
    password: 'Password'
  },
  contact_right: {
    service: 'Customer Service Telephone: 400-010-1039',
    s_title: 'customer service telephone numbers',
    QQ: 'Consultation QQ: 3003715029',
    Q_title: 'QQ Consultation'
  },
  // admin选择店铺
  shopData: [
    'Account Settings',
    'Subaccount management',
    'Subaccount Authority',
    'Authorized Public Number',
    'Choose Shops',
    'Sign out'
  ],
  // admin选择店铺页面contact组件数据
  adminContact: {
    phoneNum: 'Customer Service Telephone: 400-010-1039',
    onLine: 'Online consultation'
  },
  // admin选择店铺页面数据
  selectShop: {
    allShop: 'All stores',
    data: 'Term of validity'
  },
  // admin  账户设置数据
  accountSetting: {
    title: 'Account Settings',
    account: 'Login Account:',
    modifyPassword: 'Modify login password',
    phone: 'Contact number:',
    name: 'Account nickname:',
    namePlaceholder: 'Please enter a nickname',
    head: 'Account avatar:',
    modify: 'Modify the Avatar',
    s_modify: 'Confirmation of modifications',
    to_shop_list: 'Return to the store list',
    modifyPasswordtitle: 'Change Password'
  },
  // 浏览图片dialog数据
  imgageDalog: {
    title: 'Browse pictures',
    upload: 'Upload pictures',
    tip: 'Uploaded pictures support jpeg, jpg, PNG and BMP formats. In order to ensure the smooth loading of the front-end, the size of a single picture can not exceed 5M.',
    imagePlaceholder: 'Please enter the name of the picture.',
    search: 'search',
    OriginalImg: 'Original graph',
    delImg: 'delete',
    currentPage: 'Current page',
    totalPage: 'Total Records',
    strip: 'strip',
    cancel: 'cancel',
    Determine: 'Determine'
  },
  // admin 浏览图片弹窗 selectoptions数据
  options: [{
    value: 0,
    label: 'Upload from late to early'
  },
  {
    value: 1,
    label: 'Upload time from morning to night'
  },
  {
    value: 2,
    label: 'From big to small by picture'
  },
  {
    value: 3,
    label: 'From small to large by picture'
  },
  {
    value: 4,
    label: 'Descend by image name'
  },
  {
    value: 5,
    label: 'Upgrade by image name'
  }
  ],
  // 图片空间数据
  imgsSpace: {
    tipTitle: 'The current version is the flagship version, with 9845.36M of memory remaining',
    hiddleTitle: 'Experience version 100M memory space, base version 500M memory space, advanced version 2048M memory space, flagship version 10240M memory space',
    modeText: 'Learn more',
    allCheckedText: 'All election',
    deleteImgsText: 'Batch deletion',
    moveImgsText: 'Batch movement',
    noneImgsText: 'The current folder did not find the required picture'
  },
  // 店铺风格模块数据
  shopStyle: {
    title: 'Shop color scheme:',
    exampleTitle: 'Current color scheme example:',
    left_title_1: 'Full 200 minus 10',
    left_title_2: 'Full 300 minus 20',
    addCarText: 'Add to cart',
    buyText: 'Buy immediately',
    middle_Text_1: 'black',
    right_time: '0 days 0:29:35',
    right_kd: 'express',
    right_count_1: '10 yuan less',
    right_count_2: 'Total payable: $0',
    right_submit: 'place order',
    saveText: 'Preservation',
    topTitleList: [{
      title: 'Colour Matching 1',
      colorLeft: 'background: #ff6666;',
      colorRight: 'background: #fee7e7',
      id: '',
      choiseId: ''
    },
    {
      title: 'Colour Matching 2',
      colorLeft: 'background: #e53e24;',
      colorRight: 'background: #f2ad3c',
      id: '',
      choiseId: ''
    },
    {
      title: 'Colour Matching 3',
      colorLeft: 'background: #7e56c5;',
      colorRight: 'background: #333333',
      id: '',
      choiseId: ''
    },
    {
      title: 'Colour Matching 4',
      colorLeft: 'background: #09bb07;',
      colorRight: 'background: #333333',
      id: '',
      choiseId: ''
    },
    {
      title: 'Colour Matching 5',
      colorLeft: 'background: #4a90e2;',
      colorRight: 'background: #dbe9f9',
      id: '',
      choiseId: ''
    },
    {
      title: 'Colour Matching 6',
      colorLeft: 'background: #feb609;',
      colorRight: 'background: #333333',
      id: '',
      choiseId: ''
    },
    {
      title: 'custom',
      colorLeft: 'background: #fff;',
      colorRight: 'background: #fff',
      id: 6,
      choiseId: ''
    }
    ]
  },
  // system 列表首页页面
  sys_message: {
    lang: 'English',
    system_nav_overview: 'Overview',
    system_nav_shopManagement: 'Store Management',
    system_nav_data: 'Data statistics',
    system_nav_storeManagement: 'Commodity management',
    system_nav_member: 'Member management',
    system_nav_order: 'Order management',
    system_nav_setting: 'Setting',
    system_leftNav_title1: 'Merchant account list',
    system_leftNav_title2: 'Shop list',
    system_leftNav_title3: 'Release list',
    system_leftNav_title4: 'Applet version',
    system_leftNav_title5: 'Version list',
    system_leftNav_title6: 'WeChat full link',
    system_leftNav_title7: 'Application release list'
  },

  // system 导航右侧用户名hover选项
  useNameOption: {
    pasModify: 'change password',
    accountMange: 'sub-account management',
    exit: 'exit'
  },
  router, // 路由信息
  membershipIntroduction, // 会员列表
  programVersion, // system 后台小程序版本
  shopAccountList, // system 店铺账户列表
  shopList, // system 店铺列表
  publishList, // system 发布列表
  versionList, // system 版本列表
  marketManage, // 营销管理
  ordinaryCoupon, // 普通优惠券
  ShopConfiguration, // admin 店铺基础配置
  adminPageFramework, // admin 页面框架
  allGoods, // 商品管理/全部商品
  goodsImport,
  marketCommon, // 营销管理通用词汇
  goodsAddEditInfo,
  groupBuy,
  statusTab,
  sharePolite,
  receiveDetails,
  couponPackage,
  pageDecoration,
  bottomNavigation,
  promoteList,
  adSharePolite,
  ...enLocale
}
export default en
