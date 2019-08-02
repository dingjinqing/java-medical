const routes = [
  // {
  //   path: '/system/home',
  //   name: 'systemHome',
  //   component: r => require.ensure([], () => r(require('@/view/system/home')), 'systemHome'),
  //   children: [
  //     {
  //       path: '/system/home/main',
  //       name: 'systemMain',
  //       component: r => require.ensure([], () => r(require('@/view/system/index/main')), 'systemMain'),
  //       children: [
  //         // 概览系列子路由
  //         {
  //           path: '/system/home/main/overview',
  //           name: 'overview',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/overview')), 'overview')
  //         },
  //         {
  //           path: '/system/home/main/upgrade_renewal',
  //           name: 'upgrade_renewal',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/upgrade_renewal')), 'upgrade_renewal')
  //         },
  //         {
  //           path: '/system/home/main/web_apply',
  //           name: 'web_apply',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/web_apply')), 'web_apply')
  //         },
  //         {
  //           path: '/system/home/main/problem_feedback',
  //           name: 'problem_feedback',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/problem_feedback')), 'problem_feedback')
  //         },
  //         {
  //           path: '/system/home/main/login_log',
  //           name: 'login_log',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/login_log')), 'login_log')
  //         },
  //         {
  //           path: '/system/home/main/log_manage',
  //           name: 'log_manage',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/log_manage')), 'log_manage')
  //         },
  //         {
  //           path: '/system/home/main/article_manage',
  //           name: 'article_manage',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/article_manage')), 'article_manage')
  //         },
  //         {
  //           path: '/system/home/main/picture_space',
  //           name: 'picture_space',
  //           meta: 'overview',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/overview/picture_space')), 'picture_space')
  //         },
  //         // 店铺管理系列子路由
  //         {
  //           path: '/system/home/main/account_list',
  //           name: 'account_list',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/account_list')), 'account_list')
  //         },
  //         {
  //           path: '/system/home/main/shop_list',
  //           name: 'shop_list',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/shop_list')), 'shop_list')
  //         },
  //         {
  //           path: '/system/home/main/publish_list',
  //           name: 'publish_list',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/publish_list')), 'publish_list')
  //         },
  //         {
  //           path: '/system/home/main/small_program',
  //           name: 'small_program',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/small_program')), 'small_program')
  //         },
  //         {
  //           path: '/system/home/main/versition_list',
  //           name: 'versition_list',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/versition_list')), 'versition_list')
  //         },
  //         {
  //           path: '/system/home/main/wechat_link',
  //           name: 'wechat_link',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/wechat_link')), 'wechat_link')
  //         },
  //         {
  //           path: '/system/home/main/apply_public',
  //           name: 'apply_public',
  //           meta: 'shop_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/shop_manage/apply_public')), 'apply_public')
  //         },
  //         // 数据统计系列子路由
  //         {
  //           path: '/system/home/data_overview',
  //           name: 'data_overview',
  //           meta: 'data_statistics',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/data_statistics/data_overview')), 'data_overview')
  //         },
  //         // 商品管理系列子路由
  //         {
  //           path: '/system/home/shop_number',
  //           name: 'shop_number',
  //           meta: 'goods_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/goods_manage/shop_number')), 'shop_number')
  //         },
  //         // 会员管理系列子路由
  //         {
  //           path: '/system/home/member_statistics',
  //           name: 'member_statistics',
  //           meta: 'member_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/member_manage/member_statistics')), 'member_statistics')
  //         },
  //         // 订单管理系列子路由
  //         {
  //           path: '/system/home/order_statistics',
  //           name: 'order_statistics',
  //           meta: 'order_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/order_manage/order_statistics')), 'order_statistics')
  //         },
  //         {
  //           path: '/system/home/order_list',
  //           name: 'order_list',
  //           meta: 'order_manage',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/order_manage/order_list')), 'order_list')
  //         },
  //         // 设置系列子路由
  //         {
  //           path: '/system/home/decorate_template',
  //           name: 'decorate_template',
  //           meta: 'setting',
  //           component: r => require.ensure([], () => r(require('@/view/system/index/leftNavComponents/setting/decorate_template')), 'decorate_template')
  //         }
  //       ]
  //     }
  //   ]
  // },
  {
    path: '/system/overview',
    name: 'overviewMain',
    meta: {
      title: '概览'
    },
    component: () => import('@/view/system_new/layout/index'),
    redirect: '/system/overview/overview',
    children: [
      // 概述子路由
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
        component: () => import('@/view/system_new/views/store_management/accountList'),
        name: 'accountList',
        meta: {
          title: '店铺账户列表'
        }
      },
      {
        path: 'program_manage',
        component: () => import('@/view/system_new/views/store_management/programManage'),
        name: 'programManage',
        meta: {
          title: '小程序版本'
        }
      }
    ]
  }
]
export default routes
