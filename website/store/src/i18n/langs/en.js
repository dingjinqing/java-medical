import {
  en as router
} from './views/admin_new/en/routerEn'

// 门店管理模块国际化
// 门店通用
import {
  en as storeCommon
} from './views/admin_new/en/index/storeManagement/storeCommonEn'
// 门店列表
import {
  en as storeList
} from './views/admin_new/en/index/storeManagement/store/storeListEn'
import {
  en as addStore
} from './views/admin_new/en/index/storeManagement/store/addStoreEn'
// 门店列表-商品管理
import {
  en as storeGoodsList
} from './views/admin_new/en/index/storeManagement/store/storeGoodsListEn'
// 门店列表-核销员管理
import {
  en as verifierManage
} from './views/admin_new/en/index/storeManagement/store/verifierManageEn'
// 门店列表-门店管理
import {
  en as storeManage
} from './views/admin_new/en/index/storeManagement/storeManage/storeManageEn'
// 门店列表-门店管理-预约管理
import {
  en as reservationManage
} from './views/admin_new/en/index/storeManagement/storeManage/reservation/reservationEn'
// 门店列表-门店管理-服务管理
import {
  en as serviceManage
} from './views/admin_new/en/index/storeManagement/storeManage/service/serviceManageEn'
import {
  en as serviceList
} from './views/admin_new/en/index/storeManagement/storeManage/service/serviceListEn'
import {
  en as serviceAdd
} from './views/admin_new/en/index/storeManagement/storeManage/service/serviceAddEn'
import {
  en as serviceClassify
} from './views/admin_new/en/index/storeManagement/storeManage/service/serviceClassifyEn'
// 门店列表-技师管理
import {
  en as technicianManage
} from './views/admin_new/en/index/storeManagement/storeManage/technician/technicianManageEn'
// 门店列表-技师列表
import {
  en as technicianList
} from './views/admin_new/en/index/storeManagement/storeManage/technician/technicianListEn'
// 门店列表-技师添加
import {
  en as technicianClassify
} from './views/admin_new/en/index/storeManagement/storeManage/technician/technicianClassifyEn'
// 门店列表-技师添加
import {
  en as technicianAdd
} from './views/admin_new/en/index/storeManagement/storeManage/technician/technicianAddEn'
// 门店列表-排班管理
import {
  en as schedulingManage
} from './views/admin_new/en/index/storeManagement/storeManage/technician/schedulingManageEn'
// 门店管理-门店服务配置
import {
  en as serviceConfig
} from './views/admin_new/en/index/storeManagement/storeService/serviceConfigEn'
// 门店权限
import {
  en as storePermission
} from './views/admin_new/en/index/storeManagement/storePermission/storePermissionEn'
// 门店公告
import {
  en as storeAnnouncement
} from './views/admin_new/en/index/storeManagement/storeAnnouncement/storeAnnouncementEn'

const en = {
  messages: {
    lang: 'English'
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
  // 登录页面数据
  login_reg: {
    login: 'Sign in',
    rej: 'register'
  },
  login_page: {
    login_main: 'Sign in',
    login_f: 'Subaccount login',
    main_name: 'Main account number',
    password: 'Password',
    z_phone: 'Subaccount username/cell phone number',
    subAccount: 'Sub account'
  },
  Recommend: 'Recommend',
  environment: 'Office environment',
  contact: {
    title: 'Contact us',
    adress: 'Fifth Floor of Yitai Building, 54 Xizhimen North Street, Haidian District, Beijing'
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
  changePassword: 'Change Password',
  // admin选择店铺页面contact组件数据
  adminContact: {
    phoneNum: 'Customer Service Telephone: 400-010-1039',
    onLine: 'Online consultation'
  },
  // admin选择店铺页面数据
  selectShop: {
    allShop: 'All stores',
    data: 'Term of validity',
    prohibited: 'Prohibited',
    notopen: 'Not Open',
    expired: 'Expired',
    trial: 'Trial Version',
    basic: 'Basic Version',
    advanced: 'Advanced Version',
    ultimate: 'Ultimate Version'
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
    modifyPasswordtitle: 'Change Password',
    oldPaawd: 'old password',
    newPaawd: 'new password',
    comfNewPaawd: 'Confirm new password',
    sure: 'confirm',
    back: 'return'
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
export default en
