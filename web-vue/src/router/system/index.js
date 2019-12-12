const routes = [{
  path: '/system/overview',
  name: 'overviewMain',
  meta: {
    title: '概览'
  },
  component: () => import('@/view/system/layout/index'),
  // redirect: '/system/overview/overview_index',
  children: [
    // 概述子路由
    {
      path: 'overview_index',
      component: () => import('@/view/system/index/leftNavComponents/overview/overview_index/overview'),
      name: 'overview',
      meta: {
        title: '概览',
        imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
        imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png'
      }
    },
    {
      path: 'login_log',
      component: () => import('@/view/system/index/leftNavComponents/overview/loginLog/loginLog'),
      name: 'loginLog',
      meta: {
        title: '登录日志'
      }
    },
    {
      path: 'upgrade_renewal',
      component: () => import('@/view/system/index/leftNavComponents/overview/upgrade_renewal/upgradeRenewal'),
      name: 'upgradeRenewal',
      meta: {
        title: '升级续费申请'
      }
    },
    {
      path: 'essay_admin',
      component: () => import('@/view/system/index/leftNavComponents/overview/essay_admin/essayAdmin'),
      name: 'essayAdmin',
      meta: {
        title: '文章管理'
      }
    },
    {
      path: 'pic_space',
      component: () => import('@/view/system/index/leftNavComponents/overview/pic_space/picSpace'),
      name: 'picSpace',
      meta: {
        title: '图片空间'
      }
    }
  ]
},

{
  path: '/system/store_management',
  component: () => import('@/view/system/layout/index'),
  name: 'storeManagementMain',
  meta: {
    title: '店铺管理'
  },
  redirect: '/system/store_management/account_list',
  children: [{
    path: 'account_list',
    component: () => import('@/view/system/index/leftNavComponents/store_management/account_list/accountList'),
    name: 'accountList',
    meta: {
      title: '店铺账户列表'
    }
  },
  {
    path: 'shop_list',
    component: () => import('@/view/system/index/leftNavComponents/store_management/shop_list/shopList'),
    name: 'shopList',
    meta: {
      title: '店铺列表'
    }
  },
  {
    path: 'publish_list',
    component: () => import('@/view/system/index/leftNavComponents/store_management/publish_list/pshopList'),
    name: 'pshopList',
    meta: {
      title: '发布列表'
    }
  },
  {
    path: 'program_manage/:page/:appId',
    component: () => import('@/view/system/index/leftNavComponents/store_management/program_management/programManage'),
    name: 'programManage',
    meta: {
      title: '小程序版本'
    }
  },
  {
    path: 'versionList',
    component: () => import('@/view/system/index/leftNavComponents/store_management/version_list/versionList'),
    name: 'versionList',
    meta: {
      title: '版本列表'
    }
  },
  {
    path: 'backgroundTaskList',
    component: () => import('@/view/system/index/leftNavComponents/store_management/program_management/backgroundTaskList'),
    name: 'backgroundTaskList',
    meta: {
      title: '后台任务列表',
      parentName: '小程序版本'
    }
  }
  ]
},

{
  path: '/system/order_management',
  name: 'orderMain',
  meta: {
    title: '订单管理'
  },
  component: () => import('@/view/system/layout/index'),
  redirect: '/system/order_management/order_statistics',
  children: [
    // 订单管理子路由
    {
      path: 'order_statistics',
      component: () => import('@/view/system/index/leftNavComponents/order_management/order_statistics/orderStatistics'),
      name: 'orderStatistics',
      meta: {
        title: '订单统计'
      }
    },
    {
      path: 'order_list',
      component: () => import('@/view/system/index/leftNavComponents/order_management/order_list/orderList'),
      name: 'orderList',
      meta: {
        title: '订单列表'
      }
    }
  ]
}
]
export default routes
