const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: r => require.ensure([], () => r(require('@/view/admin/home')), 'adminHome'),
    children: [
      {
        path: '/admin/home/shop_main',
        name: 'shopMain',
        component: r => require.ensure([], () => r(require('@/view/admin/shop_main')), 'shopMain')
      },
      {
        path: '/admin/home/main',
        name: 'adminMain',
        component: r => require.ensure([], () => r(require('@/view/admin/index/main')), 'adminMain'),
        children: [
          // 概况系列子路由
          {
            path: '/admin/home/main/overviewOfMall',
            name: 'overviewOfMall',
            meta: 'first_web_manage',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_manage/overviewOfMall')), 'overviewOfMall')
          },
          {
            path: '/admin/home/main/overviewStatistics',
            name: 'overviewStatistics',
            meta: 'first_web_manage',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_manage/overviewStatistics')), 'overviewStatistics')
          },
          // 小程序管理系列子路由
          {
            path: '/admin/home/main/first_web_decoration',
            name: 'first_web_decoration',
            meta: 'first_web_decoration',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_decoration/first_web_decoration')), 'first_web_decoration')
          },
          {
            path: '/admin/home/main/page_classification',
            name: 'page_classification',
            meta: 'first_web_decoration',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_decoration/page_classification')), 'page_classification')
          },
          {
            path: '/admin/home/main/freight_template',
            name: 'freight_template',
            meta: 'first_web_decoration',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_decoration/PictureSpace/freight_template')), 'freight_template')
          },
          {
            path: '/admin/home/main/shopStyle',
            name: 'shopStyle',
            meta: 'first_web_decoration',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_decoration/shopStyle/shopStyle')), 'shopStyle')
          },
          {
            path: '/admin/home/main/bottomNavigation',
            name: 'bottomNavigation',
            meta: 'first_web_decoration',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_web_decoration/bottomNavigation/bottomNavigation')), 'bottomNavigation'),
            // 选择链接弹窗子组件路由
            children: [
              // 常用链接
              {
                path: '/admin/home/main/bottomNavigation/commonLinks',
                name: 'commonLinks',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/commonLinks')), 'commonLinks')
              },
              // 商品链接
              {
                path: '/admin/home/main/bottomNavigation/commodityLinks',
                name: 'commodityLinks',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/commodityLinks')), 'commodityLinks')
              },
              // 自定义页面
              {
                path: '/admin/home/main/bottomNavigation/customPage',
                name: 'customPage',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/customPage')), 'customPage')
              },
              // 营销活动
              {
                path: '/admin/home/main/bottomNavigation/groupDrawing',
                name: 'groupDrawing',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/groupDrawing')), 'groupDrawing')
              },
              // 商品分类
              {
                path: '/admin/home/main/bottomNavigation/classificationOfCommodities',
                name: 'classificationOfCommodities',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/classificationOfCommodities')), 'classificationOfCommodities')
              },
              // 网页跳转
              {
                path: '/admin/home/main/bottomNavigation/pageJump',
                name: 'pageJump',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/pageJump')), 'pageJump')
              },
              // 小程序跳转
              {
                path: '/admin/home/main/bottomNavigation/smallProgramJump',
                name: 'smallProgramJump',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/smallProgramJump')), 'smallProgramJump')
              },
              // 表单页面&&门店
              {
                path: '/admin/home/main/bottomNavigation/formPage',
                name: 'formPage',
                meta: 'first_web_decoration',
                component: r => require.ensure([], () => r(require('@/components/admin/selectLinksComponents/formPage')), 'formPage')
              }
            ]
          },
          // 商品管理系列子路由
          {
            path: '/admin/home/main/goods_manage',
            name: 'goods_manage',
            meta: 'goods_manage',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/goods_manage/goods_manage')), 'goods_manage')
          },
          // 订单管理系列子路由
          {
            path: '/admin/home/main/first_trade_manageL',
            name: 'first_trade_manageL',
            meta: 'first_trade_manageL',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_trade_manageL/first_trade_manageL')), 'first_trade_manageL')
          },
          // 营销管理系列子路由
          {
            path: '/admin/home/main/first_market_manage',
            name: 'first_market_manage',
            meta: 'first_market_manage',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/first_market_manage/first_market_manage')), 'first_market_manage')
          },
          // 会员管理系列子路由
          {
            path: '/admin/home/main/user_manger',
            name: 'user_manger',
            meta: 'user_manger',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/user_manger/user_manger')), 'user_manger')
          },
          // 门店管理系列子路由
          {
            path: '/admin/home/main/store_manage',
            name: 'store_manage',
            meta: 'store_manage',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/store_manage/store_manage')), 'store_manage')
          },
          // 基础配置系列子路由
          {
            path: '/admin/home/main/base_manger',
            name: 'base_manger',
            meta: 'base_manger',
            component: r => require.ensure([], () => r(require('@/view/admin/index/leftNavComponents/base_manger/base_manger')), 'base_manger')
          }
        ]
      }
    ]
  },
  {
    path: '/admin/selectlinks',
    name: 'selectLinks',
    component: r => require.ensure([], () => r(require('@/components/admin/selectLinks')), 'selectLinks')
  }

]
export default routes
