const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: () => import('@/view/admin/home'),
    children: [
      {
        path: '/admin/home/main',
        name: 'adminMain',
        component: () => import('@/view/admin/index/main'),
        children: [
          // 门店概况
          {
            path: '/admin/store/shopView',
            name: 'overView',
            meta: {
              crumbTitle: '',
              meta: 'overView'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/overview/shopView'
              )
          },
          // 门店列表
          {
            path: '/admin/store/list',
            name: 'storeList',
            meta: {
              crumbTitle: 'router.storeList',
              meta: 'storeList'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/storeList/storeList'
              )
          },
          // 门店商品
          {
            path: '/admin/store/goods',
            name: 'storeGoods',
            meta: {
              crumbTitle: 'router.goodsList',
              meta: 'storeGoods'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/goods/goodsList'
              )
          },
          // 售罄商品
          {
            path: '/admin/store/goods/saleOut',
            name: 'saleOutGoods',
            meta: {
              crumbTitle: 'router.saleOutGoods',
              meta: 'storeGoods'
            },
            component: () =>
                import(
                  '@/view/admin/index/leftNavComponents/store/goods/saleOutGoodsList'
                )
          },
          // 门店订单
          {
            path: '/admin/store/order',
            name: 'storeOrder',
            meta: {
              crumbTitle: 'router.storeOrder',
              meta: 'storeOrder'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/order/orderList'
              )
          },
          // 自提订单
          {
            path: '/admin/store/order/selfPickUpOrder',
            name: 'selfPickUpOrder',
            meta: {
              crumbTitle: 'router.selfPickUpOrder',
              meta: 'storeOrder'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/order/selfPickUpOrder'
              )
          },
          // 同城配送
          {
            path: '/admin/store/order/intraCityOrder',
            name: 'intraCityOrder',
            meta: {
              crumbTitle: 'router.intraCityOrder',
              meta: 'storeOrder'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/order/intraCityOrder'
              )
          },
          // 售后订单
          {
            path: '/admin/store/order/afterSale',
            name: 'afterSale',
            meta: {
              crumbTitle: 'router.afterSale',
              meta: 'storeOrder'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/order/afterSaleOrder'
              )
          },
          // 店员列表
          {
            path: '/admin/store/member',
            name: 'storeMember',
            meta: {
              crumbTitle: 'router.storeMember',
              meta: 'storeMember'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/member/storeMember'
              )
          },
          // 店员权限
          {
            path: '/admin/store/member/permission',
            name: 'memberPermission',
            meta: {
              crumbTitle: 'router.memberPermission',
              meta: 'storeMember'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/member/memberPermission'
              )
          },
          // 分组管理
          {
            path: '/admin/store/group/config',
            name: 'storeGroupConfig',
            meta: {
              crumbTitle: 'router.storeGroupConfig',
              meta: 'storeList'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store/storeList/storeGroupConfig'
              )
          }
        ]
      }
    ]
  }
]
export default routes
