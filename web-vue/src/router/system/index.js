const routes = [

  // {
  //   path: '/system/welcome',
  //   name: 'welcome',
  //   meta: {
  //     title: 'welcome'
  //   },
  //   component: () => import('@/view/system_new/layout/index'),
  //   redirect: '/system/welcome'
  // },
  {
    path: '/system/overview',
    name: 'overviewMain',
    meta: {
      title: '概览'
    },
    component: () => import('@/view/system_new/layout/index'),
    redirect: '/system/overview/overview_index',
    children: [
      // 概述子路由
      {
        path: 'overview_index',
        component: () => import('@/view/system_new/views/overview/overview_index/overview'),
        name: 'overview',
        meta: {
          title: '概览',
          imgUrl: '/static/image/system/icon_left/shop_view.png',
          imgUrl_h: '/static/image/system/icon_left/shop_view_h.png'
        }
      },
      {
        path: 'upgrade_renewal',
        component: () => import('@/view/system_new/views/overview/upgrade_renewal/upgradeRenewal'),
        name: 'upgradeRenewal',
        meta: {
          title: '升级续费申请'
        }
      },
      {
        path: 'pic_space',
        component: () => import('@/view/system_new/views/overview/pic_space/picSpace'),
        name: 'picSpace',
        meta: {
          title: '图片空间'
        }
      }
    ]
  },

  {
    path: '/system/store_management',
    component: () => import('@/view/system_new/layout/index'),
    name: 'storeManagementMain',
    meta: {
      title: '店铺管理'
    },
    redirect: '/system/store_management/account_list',
    children: [
      {
        path: 'account_list',
        component: () => import('@/view/system_new/views/store_management/account_list/accountList'),
        name: 'accountList',
        meta: {
          title: '店铺账户列表'
        }
      },
      {
        path: 'shop_list',
        component: () => import('@/view/system_new/views/store_management/shop_list/shopList'),
        name: 'shopList',
        meta: {
          title: '店铺列表'
        }
      },
      {
        path: 'publish_list',
        component: () => import('@/view/system_new/views/store_management/publish_list/pshopList'),
        name: 'pshopList',
        meta: {
          title: '发布列表'
        }
      },
      {
        path: 'program_manage/:page/:appId',
        component: () => import('@/view/system_new/views/store_management/program_management/programManage'),
        name: 'programManage',
        meta: {
          title: '小程序版本'
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
    component: () => import('@/view/system_new/layout/index'),
    redirect: '/system/order_management/order_statistics',
    children: [
      // 订单管理子路由
      {
        path: 'order_statistics',
        component: () => import('@/view/system_new/views/order_management/order_statistics/orderStatistics'),
        name: 'orderStatistics',
        meta: {
          title: '订单统计'
        }
      },
      {
        path: 'order_list',
        component: () => import('@/view/system_new/views/order_management/order_list/orderList'),
        name: 'orderList',
        meta: {
          title: '订单列表'
        }
      }
    ]
  }
]
export default routes
