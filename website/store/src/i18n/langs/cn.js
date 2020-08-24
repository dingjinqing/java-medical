// cn.js
import {
  cn as router
} from './views/admin_new/cn/routerCn'

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
  router, // 路由信息
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
  storeAnnouncement, // 门店公告
  storePermission, // 门店权限
  serviceConfig, // 门店服务
  reservationManage // 预约管理
}

export default cn
