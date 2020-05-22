// en.js
import enLocale from 'element-ui/lib/locale/lang/en'
// 全局消息国际化配置
// system 店铺管理系列国际化
// 小程序版本
import {
  en as programVersion
} from './views/system/en/smallProgramVersion/programVersionEn'
// 店铺账户列表
import {
  en as shopAccountList
} from './views/system/en/shopAccountList/shopAccountListEn'
// 店铺列表
import {
  en as shopList
} from './views/system/en/shopList/shopListEn'
// 发布列表
import {
  en as publishList
} from './views/system/en/publishList/publishListEn'
// 版本列表
import {
  en as versionList
} from './views/system/en/smallProgramVersion/versionListEn'

const en = {
  systemLogin: {
    login: 'Sign in',
    username: 'User Name/Mobile Number',
    password: 'Password'
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
  // 授权公众号
  serviceAuth: {
    addButton: 'Add Authorization',
    tipsOne: 'Note: Only the authentication service number can be authorized. Authorization completes the authentication service number. Users who pay attention to the service number receive the priority through the applet when receiving the notification of the applet message.',
    tipsTop1: 'Because of the requirements of WeChat platform, after the authorization of the public number is completed, it is necessary to manage the small program in the store - the small program authorization binding and the store applet are the same as the public number of the main body. After binding, use the binding public number to log in. The WeChat public platform adds a related applet to the public platform applet applet management page. After the small program is associated, the user who pays attention to the public number can receive the public number when receiving the small program message notification. The applet receives a large message limit, see',
    tipsTop2: 'Template message delivery condition description',
    tipsTop3: 'This feature makes it easy for merchants to notify small users about small programs. ',
    nickName: 'service number name',
    principalName: 'Principal name',
    alias: 'public micro-signal',
    lastAuthTime: 'authorization time',
    mpNickName: 'Bounded Mini Programs',
    isAuthOk: 'status',
    operation: 'operation',
    payLook: 'View',
    payManage: 'configuration',
    payStoreName: 'Business number:',
    payKey: 'Payment key:',
    payCertContent: 'Payment certificate:',
    payKeyContent: 'Pay private key:',
    sure: 'confirm',
    cancel: 'cancel',
    haveAuth: 'authorized',
    haveCancle: 'Cancelled',
    title1: 'authorization list',
    title2: 'Service number details',
    qurl: 'public number two-dimensional code',
    type: 'public number type',
    newButton: 're-authorize',
    tipsTop4: ' If your public account has been successfully upgraded (uncertified to an authentication number, or from a subscription number to a service number), click Reauthorize',
    tipsTop5: 'How to upgrade:',
    tipsTop6: 'If you need to perform WeChat authentication on the public number, please log in to the "WeChat public platform -> public number setting", in the "Certification status" column, click Apply for WeChat authentication',
    authStats: 'authorization status',
    Wechat0: 'Subscription number',
    Wechat1: 'Wechat Authentication Subscription Number',
    Wechat2: 'Service number',
    Wechat3: 'Wechat Certification Service Number'
  },
  programVersion, // system 后台小程序版本
  shopAccountList, // system 店铺账户列表
  shopList, // sysytem 店铺列表
  publishList, // system 发布列表
  versionList, // system 版本列表
  ...enLocale
}
export default en
