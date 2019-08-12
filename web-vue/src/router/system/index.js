const routes = [
  {
    path: '/system/overview',
    name: 'overviewMain',
    meta: {
      title: 'welcome'
    },
    component: () => import('@/view/system_new/layout/index'),
    redirect: '/system/overview/welcome',
    children: [
      // 概述子路由
      {
        path: 'welcome',
        component: () => import('@/view/system_new/views/overview/welcome'),
        name: 'welcome',
        meta: {
          title: 'welcome'
        }
      },
      {
        path: 'overview',
        component: () => import('@/view/system_new/views/overview/overview'),
        name: 'overview',
        meta: {
          title: '概览',
          imgUrl: '/static/image/system/icon_left/shop_view.png',
          imgUrl_h: '/static/image/system/icon_left/shop_view_h.png'
        }
      },
      {
        path: 'upgrade_renewal',
        component: () => import('@/view/system_new/views/overview/upgradeRenewal'),
        name: 'upgrade_renewal',
        meta: {
          title: '升级续费申请'
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
          title: '店铺账户列表'
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
    path: '/system/program_manage_details/:page',
    component: () => import('@/view/system_new/views/store_management/program_management/mpProgramManage'),
    name: 'programDetails',
    meta: {
      title: '小程序详情'
    }
  }
]
export default routes
