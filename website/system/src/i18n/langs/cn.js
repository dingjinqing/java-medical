// cn.js
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
// system 店铺管理系列国际化
// 小程序版本
import {
  cn as programVersion
} from './views/system/cn/smallProgramVersion/programVersionCn'
// 店铺账户列表
import {
  cn as shopAccountList
} from './views/system/cn/shopAccountList/shopAccountListCn'
// 店铺列表
import {
  cn as shopList
} from './views/system/cn/shopList/shopListCn'
// 发布列表
import {
  cn as publishList
} from './views/system/cn/publishList/publishListCn'
// 版本列表
import {
  cn as versionList
} from './views/system/cn/smallProgramVersion/versionListCn'

const cn = {
  systemLogin: {
    login: '登录',
    username: '用户名 / 手机号',
    password: '密码'
  },
  // system 列表首页页面
  sys_message: {
    lang: '中文',
    system_nav_overview: '概览',
    system_nav_shopManagement: '店铺管理',
    system_nav_data: '数据统计',
    system_nav_storeManagement: '商品管理',
    system_nav_member: '会员管理',
    system_nav_order: '订单管理',
    system_nav_setting: '设置',
    system_leftNav_title1: '商家账户列表',
    system_leftNav_title2: '店铺列表',
    system_leftNav_title3: '发布列表',
    system_leftNav_title4: '小程序版本',
    system_leftNav_title5: '版本列表',
    system_leftNav_title6: '微信全链路',
    system_leftNav_title7: '申请发布列表'
  },

  // system 导航右侧用户名hover选项
  useNameOption: {
    pasModify: '密码修改',
    accountMange: '子账号管理',
    exit: '退出'
  },
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
  },
  programVersion, // system 后台小程序版本
  shopAccountList, // system 店铺账户列表
  shopList, // sysytem 店铺列表
  publishList, // system 发布列表
  versionList, // system 版本列表
  ...zhLocale
}

export default cn
